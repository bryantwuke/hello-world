package sysuser
import basic.BaseController
import grails.converters.JSON

class Back_rightController extends BaseController {

    private ds_rightorg_common= { qty, gid ->
        def tsql = "select  count(*) "
        def vsql = "select su.id, su.user_name, su.user_depname, su.user_title  "

        def sql
        switch (qty) {
            case "luig": // list all user in group
                sql = "from Sys_user su, Sys_useringroup uig where su.id=uig.uig_userid and uig.uig_groupid='${gid}' "
                break
            case "lnuig": // list all user not in group
                sql = "from Sys_user su where su.user_orgid = 0 and su.id not in " +
                        "(select uig.uig_userid from Sys_useringroup uig " +
                        "where su.id=uig.uig_userid and uig.uig_groupid='${gid}' ) "
                break
        }

        def osql = "order by su.user_depname "

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:12)

        def userlist = Sys_user.executeQuery(vsql+sql+osql, mypage).collect { row ->
            [user_id: row[0], user_name: row[1], user_depname: row[2], user_title: row[3]]
        }

        def total = Sys_user.executeQuery(tsql+sql, [readOnly: true])
        return [numRows: total, items: userlist]
    }


    def right_manage() {
        render(template: '/back_right/back_right')
    }

    def ds_group_list() {
        paramsort('group_name')

        def total = Sys_group.count();
        def results = Sys_group.list(params)
        def jsondata = [numRows: total, items: results]

        render jsondata as JSON
    }

    def ds_useringroup_list() {
        def groupid = params.long("group_id", 0)
        def itemlist = ds_rightorg_common("luig", groupid)
        render(itemlist as JSON)
    }

    def ds_usernotingroup_list() {
        def groupid = params.long("group_id", 0)
        def itemlist = ds_rightorg_common("lnuig", groupid)
        render(itemlist as JSON)
    }

    def groupbright_add() {
        render(template: '/back_right/groupbright_add')
    }

    def groupname_exist() {
        if (Sys_group.findByGroup_name(params.group_name)){
            render(1)
            return
        }
        render(0)
    }

    def groupbright_addlog() {

        def grouptmp = new Sys_group()
        grouptmp.group_name = params.group_name
        grouptmp.group_rkey = params.findAll {it.value == "on"}.keySet().join(",")
        grouptmp.save(flush: true)

        render(grouptmp.id)
    }

    def groupbright_dellog() {
        def grouptmp = Sys_group.read(params.int('group_id', 0))
        if (!grouptmp) {
            render(status: 204)
        }

        def uiglist = Sys_useringroup.findAllByUig_groupid(grouptmp.id)
        def desubright = new User_right()

        ubright_set(desubright, grouptmp.group_rkey, 1)

        def tmpubright = null
        def hasrights = desubright.bySum()
        if (hasrights) {
            uiglist.uig_userid.each { row ->
                tmpubright = User_right.lock(row)
                ubright_add(desubright, tmpubright, -1)
                tmpubright.save()
            }
        }

        uiglist.each { uigtmp ->
            uigtmp.delete()
        }

        grouptmp.delete(flush: true)

        render  status: 204
    }

    def groupbright_viewlog() {
        def tmpgroup = Sys_group.read(params.int('groupbright_id', 0));
        if (!tmpgroup) {
            render(status: 204)
        }

        def tmpgright = [:]
        tmpgright.group_name = tmpgroup.group_name
        tmpgright.group_id = tmpgroup.id

        def group_right = tmpgroup.group_rkey.tokenize(",")

        /////////////
/*
        def tmpub = new User_right()
        for (key in group_right) {
            tmpub.setProperty(key, 1)
            def kv = tmpub.getProperty(key)
            println(kv)
        }

        for (key in group_right) {
            tmpub.setProperty(key, tmpub.getProperty(key) + 3)
        }

        println(tmpub.properties)
*/

        //////////////

        render(template: '/back_right/groupbright_add', model:[gbins:tmpgright, brightins:group_right])
    }

    def groupbright_editlog() {
        def grouptmp = Sys_group.read(params.int('group_id', 0))

        // 变更权限
        // 先统一去掉用户权限
        def desubright = new User_right()
        ubright_set(desubright, grouptmp.group_rkey, -1)


        grouptmp.group_name = params.group_name
        grouptmp.group_rkey = params.findAll {it.value == "on"}.keySet().join(",")

        // 重新添加权限
        def tmpubref = new User_right()
        ubright_set(tmpubref,  grouptmp.group_rkey, 1)

        ubright_add(tmpubref,  desubright, 1)

        // 检查修改后的权限变化
        def modrights = desubright.byAdd() + desubright.byDel()

        def user_right = null
        if (modrights) {
            Sys_useringroup.findAllByUig_groupid(grouptmp.id).uig_userid.each { row ->
                user_right = User_right.lock(row)

                ubright_plus(desubright, user_right)
                user_right.save()
            }
        }

        grouptmp.save(flush: true)

        render(status: 204)
    }

    private ubright_set = { User_right ub, String rkey, Integer desvalue ->
        def tmpright = rkey.tokenize(",")

        for (key in tmpright) {
            ub.setProperty(key, desvalue)
        }

    }

    private ubright_add = { User_right ubref, User_right ubdes, Integer ubvalue ->

        def refkey = ubref.properties.findAll {it.value > 0}.keySet()

        for (key in refkey) {
            ubdes.setProperty(key, ubdes.getProperty(key) + ubvalue)
        }
    }

    private ubright_plus = { User_right ubref, User_right ubdes ->

        def refkey = ubref.properties.findAll {it.value != 0}.keySet()

        for (key in refkey) {
            ubdes.setProperty(key, ubdes.getProperty(key) + ubref.getProperty(key))
        }
    }

    def useringroup_add() {
        render(template: '/back_right/useringroup_add', model: [groupidins: params.group_id])
    }

    def sessionFactory

    def useringroup_addlog() {
        long group_id = params.long("group_id", 0)
        def user_idlist = JSON.parse(params.user_idlist)

        def tmpgroup = Sys_group.read(group_id)

        def desubright = new User_right()

        ubright_set(desubright, tmpgroup.group_rkey, 1)

        def hasrights = desubright.bySum()

        long uid = 0
        for(userid in user_idlist) {
            uid = userid.toLong()
            new Sys_useringroup().with {
                uig_groupid = group_id
                uig_userid = uid
                save()
            }

            if (hasrights) {
                def tmpright = User_right.lock(uid)
                ubright_add(desubright,tmpright, 1)
                tmpright.save()
            }
        }

        sessionFactory.getCurrentSession()?.flush()

        render(status: 204)
    }

    def useringroup_dellog() {
        def group_id = params.long("group_id", 0)
        def user_idlist = JSON.parse(params.user_idlist)

        def tmpgroup = Sys_group.read(group_id)
        def desubright = new User_right()
        ubright_set(desubright, tmpgroup.group_rkey, 1)

        def hasrights = desubright.bySum()

        def tmpuig = null
        def tmpubright = null

        for(userid in user_idlist) {
            tmpuig = Sys_useringroup.findByUig_groupidAndUig_userid(group_id, userid)

            if (hasrights) {
                tmpubright = User_right.lock(tmpuig.uig_userid)
                ubright_add(desubright, tmpubright, -1)
                tmpubright.save()
            }

            tmpuig.delete()
        }

        sessionFactory.getCurrentSession()?.flush()

        render(status: 204)
    }

    def ds_userbright_list() {
        paramsort('user_depname')

        def total = Sys_user.countByUser_orgid(0);
        def results = Sys_user.findAllByUser_orgid(0, params).collect { row ->
            [user_id:row.id, user_name:row.user_name,
             user_depname:row.user_depname, user_title:row.user_title]
        }

        def jsondata = [numRows: total, items: results]

        render jsondata as JSON

    }

    def userbright_viewlog() {
        def userid = params.int('user_id', 0)
        def tmpbr = User_right.read(userid)?.byView()

        render(template: '/back_right/userbright_view', model:[brightins: tmpbr] )

    }


}
