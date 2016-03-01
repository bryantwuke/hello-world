package appgw
import basic.BaseController
import grails.converters.JSON
import groovy.time.TimeCategory
import roomsale.*
import service.Enum_Smstype
import service.Sys_holiday

class ReggwController extends BaseController{

    def test() {
        /*
        def now = new Date()
        def week = null

        println(now)
        now.date = 1

        println(now)

        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] -1).days
        }

        week.clearTime()

        println(week)

        now = Date.parse("yyyy-MM-dd", "2015-12-05")
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] -1).days
        }
        week.clearTime()
        println(week)

        now = Date.parse("yyyy-MM-dd", "2015-12-07")
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] -1).days
        }
        week.clearTime()
        println(week)
        */


        /*
        println("ffffff")
        def now = new Date()
        def week = null

        render(now.format('yyyy-MM-dd HH:mm:ss'))

        println(now)
        render("<br/>")
        //now.date = 1

        //println(now)

        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] -1).days + 7.days
        }

        week.clearTime()

        println(week)



        render(week.format('yyyy-MM-dd HH:mm:ss'))

        render("<br/>")

        now = Date.parse("yyyy-MM-dd HH:mm:ss", "2015-12-5 23:59:59")
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] -1).days + 7.days
        }
        week.clearTime()
        println(week)
        render(week.format('yyyy-MM-dd HH:mm:ss'))

        render("<br/>")
        Date date = new Date()
        date.date = 1
        render(date.format('yyyy-MM-dd HH:mm:ss'))
        render(" -- ")
        date.month = date.month + 1
        date.clearTime()
        render(date.format('yyyy-MM-dd HH:mm:ss'))

        render("<br/>")
        now = new Date()

        render(now.format('yyyy-MM-dd HH:mm:ss'))
        render(" -- ")
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] -1).days - 7.days
        }
        //week.clearTime()
        render(week.format('yyyy-MM-dd HH:mm:ss'))
        */

        /*
        Date tt = Date.parse('yyyy-MM-dd','2015-12-12')
        def now = new Date()
        if(now.before(tt)){
            render("Yes")
        }else{
            render("No")
        }
        */
        /*
        try {
            Date date = Date.parse('yyyy-MM','xxyy-ss')
            println("yyyy")
            render(date.format('yyyy-MM-dd'))
        }catch (Exception e){
            println("NNNN")
            render("NNNN")
        }
        */

        /*
        def now = new Date()
        println(now.format('yyyy-MM-dd'))

        def tmptime = now
        tmptime.month = tmptime.month + 1


        println(now.format('yyyy-MM-dd'))
        */



    }

    /*  楼盘项目收藏操作  */
    def bdfavorop(){

        def ret = [status:0, msg:"OK"]

        def tmpbd = Building.read(params.long('bdid',0))
        if(!tmpbd){
            ret.status = 3
            ret.msg = "无效的楼盘"
            render (ret as JSON)
            return
        }

        def tmpfavor = Agent_favor.findByFavor_agentidAndFavor_refidAndFavor_reftype(session.agentid, tmpbd.id, Enum_Reftype.RT_building.code)

        if (params.op == "1") {
            ret.msg = "收藏成功"
            if(!tmpfavor){
                new Agent_favor().with {
                    favor_agentid = session.agentid
                    favor_refid =  tmpbd.id
                    favor_reftype = Enum_Reftype.RT_building.code
                    save(flush: true)
                }

                Building.executeUpdate("update Building set bd_focus = bd_focus + 1 where id = ${tmpbd.id}")
            }

        }else if (params.op == "2") {
            ret.msg = "取消收藏成功"
            if(tmpfavor){
                // tmpfavor.delete(flush: true)
                Agent_favor.executeUpdate("delete from Agent_favor where id = ${tmpfavor.id}")
                Building.executeUpdate("update Building set bd_focus = bd_focus - 1 where id = ${tmpbd.id}")
            }

        }

        render (ret as JSON)
    }

    /*  车行项目收藏操作  */
    def cbfavorop(){

        def ret = [status:0, msg:"OK"]

        def tmpcb = Car_bank.read(params.long('cbid',0))
        if(!tmpcb){
            ret.status = 3
            ret.msg = "无效的车行"
            render (ret as JSON)
            return
        }

        def tmpaf = Agent_favor.findByFavor_agentidAndFavor_refidAndFavor_reftype(session.agentid, tmpcb.id, Enum_Reftype.RT_car.code)

        if (params.op == "1") {
            ret.msg = "收藏成功"
            if(!tmpaf){
                new Agent_favor().with {
                    favor_agentid = session.agentid
                    favor_refid =  tmpcb.id
                    favor_reftype = Enum_Reftype.RT_car.code
                    save(flush: true)
                }

                Car_bank.executeUpdate("update Car_bank set cb_focus = cb_focus + 1 where id = ${tmpcb.id}")
            }

        }else if (params.op == "2") {
            ret.msg = "取消收藏成功"
            if(tmpaf){
                Agent_favor.executeUpdate("delete from Agent_favor where id = ${tmpaf.id}")
                Car_bank.executeUpdate("update Car_bank set cb_focus = cb_focus - 1 where id = ${tmpcb.id}")
            }
        }

        render (ret as JSON)
    }

    //更新经纪人信息
    def agtupdate(){
        def ret = [status:0, msg:"更新成功"]

        if(!params.agent_id){
            ret.status = 1
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        if(params.long('agent_id',0L) != session.agentid){
            ret.status = 2
            ret.msg = "无操作权限"
            render(ret as JSON)
            return
        }

        def tmpagent = Agent.get(params.long('agent_id',0L))

        if((params.agent_name || params.agent_idcard) && (tmpagent.agent_status >= Enum_Agentstatus.AS_idcard_ok.code)){ //更换名字或者身份证
            ret.status = 3
            ret.msg = "用户已认证，不可再修改名字或者身份证"
            render(ret as JSON)
            return
        }

        bindData(tmpagent, params, [exclude:['agent_passwd','agent_salt','agent_iconid','agent_idcardid','agent_mobile','agent_cashpwd','agent_status','agent_rank', 'agent_idcard']])
        if(params.agent_idcard){
            tmpagent.agent_idcard = hextostr(params.agent_idcard)
        }

        tmpagent.save(flush: true)

        render(ret as JSON)
    }

    //登录后修改密码
    def modifypwd(){
        def ret = [status:0, msg:"修改成功"]

        if ((!params.newpwd)||(!params.oldpwd)) {
            ret.status = 1
            ret.msg = "参数错误"
            render (ret as JSON)
            return
        }


        def tmpagent = Agent.get(session.agentid)
        if (!tmpagent) {
            ret.status = 3
            ret.msg = "用户不存在"
            render (ret as JSON)
            return
        }


        if (tmpagent.agent_passwd != (tmpagent.agent_salt + params.oldpwd).encodeAsSHA1()) {
            ret.status = 4
            ret.msg = "原始密码不正确"
            render (ret as JSON)
            return
        }

        tmpagent.with {
            agent_passwd = (agent_salt + params.newpwd).encodeAsSHA1()

            save(flush: true)
        }

        render(ret as JSON)
    }


    /*  设置提现密码  */
    def setcspwd(){
        def ret = [status:0, msg:"设置成功"]

        if(!params.pwd){
            ret.status = 1
            ret.msg = "参数错误"
            render (ret as JSON)
            return
        }

        def tmpagent = Agent.get(session.agentid)
        if(!tmpagent){
            ret.status = 2
            ret.msg = "无效的用户"
            render (ret as JSON)
            return
        }

        if(tmpagent.agent_cashpwd){
            ret.status = 3
            ret.msg = "已有提现密码，只能重置"
            render (ret as JSON)
            return
        }

        tmpagent.with {
            agent_cashpwd = (agent_salt + params.pwd).encodeAsSHA1()
            save(flush: true)
        }

        render(ret as JSON)

    }


    /*  重置提现密码  */
    def resetcspwd(){
        def ret = [status:0, msg:"设置成功"]
        if(!params.opwd || !params.npwd){
            ret.status = 1
            ret.msg = "参数错误"
            render (ret as JSON)
            return
        }

        def tmpagent = Agent.get(session.agentid)
        if(!tmpagent){
            ret.status = 2
            ret.msg = "无效的用户"
            render (ret as JSON)
            return
        }

        if(!tmpagent.agent_cashpwd){
            ret.status = 3
            ret.msg = "未设置提现密码"
            render (ret as JSON)
            return
        }

        if(tmpagent.agent_cashpwd != (tmpagent.agent_salt + params.opwd).encodeAsSHA1()){
            ret.status = 4
            ret.msg = "原始密码错误"
            render (ret as JSON)
            return
        }

        tmpagent.with {
            agent_cashpwd = (agent_salt + params.npwd).encodeAsSHA1()
            save(flush: true)
        }

        render(ret as JSON)

    }


    /*  我的签到*/
    def mycheck(){
        def ret = [status: 0, msg:'OK']

        def tmpagent = Agent.read(session.agentid)
        if(!tmpagent){
            ret.status = 2
            ret.msg = "无效的用户"
            render (ret as JSON)
            return
        }

        /*  查看今天是否已做签到  */
        def today = new Date()
        def tomorrow = null
        use(TimeCategory) {
            tomorrow = (today + 1.days).format('yyyy-MM-dd')
            today = today.format('yyyy-MM-dd')
        }

        def count = Agent_check.executeQuery("select count(id) from Agent_check where check_agentid = ${tmpagent.id} and check_time >= '${today}' and check_time < '${tomorrow}'", [max:1])[0]

        ret.checked = count > 0? true : false

        ret.allchkdays = tmpagent.agent_checkall
        ret.chkdays = tmpagent.agent_checking

        ret.iconid = tmpagent.agent_iconid

        render(ret as JSON)


    }

    // 签到
    def docheck(){
        def ret = [status: 0, msg:'签到成功']

        def tmpagent = Agent.read(session.agentid)
        if(!tmpagent){
            ret.status = 2
            ret.msg = "无效的用户"
            render (ret as JSON)
            return
        }

        /*  查看今天是否已做签到  */
        def today = new Date()
        def tomorrow = null
        def lastday = null

        use(TimeCategory) {
            tomorrow = (today + 1.days).format('yyyy-MM-dd')
            lastday = (today - 1.days).format('yyyy-MM-dd')
            today = today.format('yyyy-MM-dd')
        }

        //今天是否已经签到
        def count = Agent_check.executeQuery("select count(id) from Agent_check where check_agentid = ${tmpagent.id} and check_time >= '${today}' and check_time < '${tomorrow}'", [max:1])[0]

        //昨天是否已签到
        def lastcount = Agent_check.executeQuery("select count(id) from Agent_check where check_agentid = ${tmpagent.id} and check_time >= '${lastday}' and check_time < '${today}'", [max:1])[0]


        if(count == 0){ //如果今天没有签到，则更新已签到总天数和连续签到天数
            tmpagent.with {
                agent_checkall = agent_checkall + 1
                agent_checking = lastcount > 0 ? (agent_checking + 1) : 1
                save()
            }
        }

        def tmpac = new Agent_check()
        tmpac.with {
            check_agentid = tmpagent.id
            check_time = new Date()
            check_place = params.place
            check_note = params.note

            save(flush: true)
        }

        ret.check_id = tmpac.id

        render(ret as JSON)

    }

    //我的签到列表
    def mychklist(){
        def ret = [status: 0, msg:'OK']

        def tsql = "select count(*) "
        def vsql = """select ac.check_time, ac.check_place, ac.check_note """

        def sql = """ from Agent_check ac where ac.check_agentid = ${session.agentid}"""

        def osql = """ order by ac.id desc """

        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        ret.total = Agent_check.executeQuery(tsql + sql)[0].toLong()

        def pagenum = 0
        if(reqnum){
            pagenum =  ret.total.mypage(reqnum)
        }else{
            pagenum =  ret.total > 0? 1 : 0
        }

        ret.pagenum =  pagenum

        ret.items = Agent_check.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    check_time:it[0], check_place:it[1], check_note:it[2]
            ]
        }

        render(ret as JSON)

    }

    //新增客户
    def addcustom(){
        def ret = [status: 0, msg:'新增客户成功']

        if(!params.cst_name || !params.cst_mobile){
            ret.status = 1
            ret.msg = "参数错误"
            render (ret as JSON)
            return
        }

        if (!(params.cst_mobile ==~ ~FS_ismobile)) {
            ret.status = 2
            ret.msg = "无效的客户手机号码"
            render (ret as JSON)
            return
        }

        //查看是否已经存在新增的用户(以手机号)
        def tmpcst = Cst_basic.findByCst_mobile(params.cst_mobile)
        if(tmpcst){
            ret.status = 4
            ret.msg = "客户已存在"
            render (ret as JSON)
            return
        }

        def roomwish = params.int('room_wishlevel', Enum_Wishlevel.WL_none.code)
        def carwish = params.int('car_wishlevel', Enum_Wishlevel.WL_none.code)

        tmpcst = new Cst_basic()
        tmpcst.with {
            cst_agtid = session.agentid
            cst_name = params.cst_name
            cst_mobile = params.cst_mobile
            if(params.cst_idcard){
                cst_idcard = hextostr(params.cst_idcard)
            }
//            cst_idcard = params.cst_idcard
            cst_address = params.cst_address
            cst_time = new Date()

            if(roomwish != Enum_Wishlevel.WL_none.code){ //如果有购房意向
                cst_regbd = Enum_Regstatus.RS_wishyes.code
            }

            if(carwish != Enum_Wishlevel.WL_none.code){  //有购车意向
                cst_regcar = Enum_Regstatus.RS_wishyes.code
            }

            save(flush: true)
        }

        //更新周计划新增客户数
        def now = new Date()
        Date week = null
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] - 1).days
        }

        week.clearTime()

        def plan_week = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(tmpcst.cst_agtid, week, Enum_Plantype.PT_week.code)
        plan_week.with {
            log_cst++
            save()
        }

        //更新月计划新增客户数
        now.date = 1
        now.clearTime()
        def plan_month = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(tmpcst.cst_agtid, now, Enum_Plantype.PT_month.code)
        plan_month.with {
            log_cst++
            save()
        }

        def tmproomwh = new Cst_wishroom()
        bindData(tmproomwh, params, [exclude:['id', 'room_tagid', 'room_bd_type']])

        tmproomwh.with {
            id = tmpcst.id

            if(params.bd_point && params.bd_point.trim() != ''){      /* 卖点需求*/
                String[] points = params.bd_point.split('_')
                def pointstr = points.sort().join('_')
                def tmpbt = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_bd_tag.code, pointstr)
                tmpbt.tag_isbasic = points.length > 1 ? 1 : 0
                room_tagid = tmpbt.save()?.id
            }


            if(params.bd_type && params.bd_type.trim() != ''){      /* 业态需求*/
                String[] types = params.bd_type.split('_')
                def typestr = types.sort().join('_')
                def tmpbt = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_bd_type.code, typestr)
                tmpbt.tag_isbasic = types.length > 1 ? 1 : 0
                room_bd_type = tmpbt.save()?.id
            }

            save()
        }

        def tmpcarwh = new Cst_wishcar()
        bindData(tmpcarwh, params, [exclude:['id', 'car_point_tagid', 'car_brand_tagid']])

        tmpcarwh.with {
            id = tmpcst.id

            if(params.car_point && params.car_point.trim() != ''){        /* 汽车卖点*/
                String[] points = params.car_point.split('_')
                def pointstr = points.sort().join('_')

                def tmpct = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_car_tag.code, pointstr)
                tmpct.tag_isbasic = points.length > 1 ? 1 : 0
                car_point_tagid = tmpct.save()?.id
            }

            if(params.car_type && params.car_type.trim() != ''){    /*  汽车品牌  */
                String[] types = params.car_type.split('_')
                def typestr = types.sort().join('_')

                def tmpct = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_car_type.code, typestr)
                tmpct.tag_isbasic = types.length > 1 ? 1 : 0
                car_brand_tagid = tmpct.save()?.id
            }

            save(flush: true)
        }

        // tmpcst.save(flush: true)

        render(ret as JSON)

    }


    //更新客户资料
    def upcustom(){
        def ret = [status: 0, msg:'更新成功']

        if(!params.cst_name || !params.cst_mobile || !params.cst_id){
            ret.status = 1
            ret.msg = "参数错误"
            render (ret as JSON)
            return
        }

        if (!(params.cst_mobile ==~ ~FS_ismobile)) {
            ret.status = 2
            ret.msg = "无效的客户手机号码"
            render (ret as JSON)
            return
        }

        def tmpagent = Agent.read(session.agentid)
        if(!tmpagent){
            ret.status = 3
            ret.msg = "无效的经纪人"
            render (ret as JSON)
            return
        }



        //查看是客户是否存在
        def tmpcst = Cst_basic.get(params.long('cst_id', 0))
        if(!tmpcst){
            ret.status = 4
            ret.msg = "无效的客户"
            render (ret as JSON)
            return
        }

        //查看是否已经存在新增的用户(以手机号)
        def tmpcst2 = Cst_basic.findByCst_mobile(params.cst_mobile)
        if(tmpcst2 && tmpcst2.id != tmpcst.id){
            ret.status = 5
            ret.msg = "新手机客户已存在"
            render (ret as JSON)
            return
        }

        if(tmpcst.cst_agtid != session.agentid){
            ret.status = 6
            ret.msg = "无更新权限"
            render (ret as JSON)
            return
        }

        def roomwish = params.int('room_wishlevel', Enum_Wishlevel.WL_none.code)
        def carwish = params.int('car_wishlevel', Enum_Wishlevel.WL_none.code)

        tmpcst.with {
            cst_agtid = session.agentid
            cst_name = params.cst_name
            cst_mobile = params.cst_mobile
            //cst_idcard = params.cst_idcard
            if(params.cst_idcard){
                cst_idcard = hextostr(params.cst_idcard)
            }else{
                cst_idcard = null
            }
            cst_address = params.cst_address

            if(roomwish != Enum_Wishlevel.WL_none.code){ //如果有购房意向
                if(cst_regbd == Enum_Regstatus.RS_wishno.code) {  //如果之前为无意向
                    cst_regbd = Enum_Regstatus.RS_wishyes.code
                }
            }

            if(carwish != Enum_Wishlevel.WL_none.code){  //有购车意向
                if(cst_regcar == Enum_Regstatus.RS_wishno.code){ //如果之前为无意向
                    cst_regcar = Enum_Regstatus.RS_wishyes.code
                }
            }

        }

        def tmproomwh = Cst_wishroom.get(tmpcst.id)
        bindData(tmproomwh, params, [exclude:['id', 'room_tagid', 'room_bd_type']])

        tmproomwh.with {
            id = tmpcst.id

            if(params.bd_point && params.bd_point.trim() != ''){      /* 卖点需求*/
                String[] points = params.bd_point.split('_')
                def pointstr = points.sort().join('_')
                def tmpbt = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_bd_tag.code, pointstr)
                tmpbt.tag_isbasic = points.length > 1 ? 1 : 0
                room_tagid = tmpbt.save()?.id
            }else{
                room_tagid = 0
            }


            if(params.bd_type && params.bd_type.trim() != ''){      /* 业态需求*/
                String[] types = params.bd_type.split('_')
                def typestr = types.sort().join('_')
                def tmpbt = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_bd_type.code, typestr)
                tmpbt.tag_isbasic = types.length > 1 ? 1 : 0
                room_bd_type = tmpbt.save()?.id
            }else{
                room_bd_type = 0
            }

            save()
        }

        def tmpcarwh = Cst_wishcar.get(tmpcst.id)
        bindData(tmpcarwh, params, [exclude:['id', 'car_point_tagid', 'car_brand_tagid']])

        tmpcarwh.with {
            id = tmpcst.id

            if(params.car_point && params.car_point.trim() != ''){        /* 汽车卖点*/
                String[] points = params.car_point.split('_')
                def pointstr = points.sort().join('_')

                def tmpct = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_car_tag.code, pointstr)
                tmpct.tag_isbasic = points.length > 1 ? 1 : 0
                car_point_tagid = tmpct.save()?.id
            }else{
                car_point_tagid = 0
            }

            if(params.car_type && params.car_type.trim() != ''){    /*  汽车品牌  */
                String[] types = params.car_type.split('_')
                def typestr = types.sort().join('_')

                def tmpct = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_car_type.code, typestr)
                tmpct.tag_isbasic = types.length > 1 ? 1 : 0
                car_brand_tagid = tmpct.save()?.id
            }else{
                car_brand_tagid = 0
            }

            save()
        }

        tmpcst.save(flush: true)

        render(ret as JSON)

    }

    //更新给客户打电话，发短信次数
    def upcontactlog(){
        def ret = [status: 0, msg:'更新成功']

        def tmpcst = Cst_basic.get(params.long('cst_id', 0))
        if(!tmpcst){
            ret.status = 2
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(tmpcst.cst_agtid != session.agentid){
            ret.status = 3
            ret.msg = "客户不属于当前经纪人"
            render(ret as JSON)
            return
        }

        def now = new Date()
        Date week = null
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] - 1).days
        }

        week.clearTime()

        def plan_week = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(tmpcst.cst_agtid, week, Enum_Plantype.PT_week.code)

        def tmptime = now
        tmptime.date = 1
        tmptime.clearTime()
        def plan_month = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(tmpcst.cst_agtid, tmptime, Enum_Plantype.PT_month.code)

        //增加打电话次数记录
        if (params.op == "1") {
            tmpcst.cst_phonenum++
            tmpcst.cst_phonetime = now

            plan_week.log_phone++
            plan_month.log_phone++
        }
        //增加发短信次数记录
        else {
            tmpcst.cst_smsnum++
            tmpcst.cst_smstime = now

            plan_week.log_sms++
            plan_month.log_sms++
        }

        plan_week.save()
        plan_month.save()

        tmpcst.save(flush: true)

        render(ret as JSON)
    }


    //我的客户列表
    def cstlist(){
        def tsql = "select count(*) "
        def vsql = """select c.id, c.cst_name, c.cst_mobile, c.cst_phonenum, c.cst_smsnum, c.cst_regbd, c.cst_regcar"""

        def sql = """ from Cst_basic c where c.cst_agtid = ${session.agentid}"""

        if(params.keyword){
            sql += """ and (c.cst_mobile like '%${params.keyword}%' or c.cst_name like '%${params.keyword}%')"""
        }

        int sorttype = params.int('stype', 0)


        def osql = """ order by c.id desc """

        switch (sorttype){
            case 1:    //名字升序
                osql = """ order by c.cst_name asc """
                break
            case 2:    //名字降序
                osql = """ order by c.cst_name desc """
                break

            case 3:    //电话升序
                osql = """ order by c.cst_phonenum asc """
                break
            case 4:    //电话降序
                osql = """ order by c.cst_phonenum desc """
                break

            case 5:    //短信升序
                osql = """ order by c.cst_smsnum asc """
                break
            case 6:    //短信降序
                osql = """ order by c.cst_smsnum desc """
                break

            case 7:    //楼盘状态升序
                osql = """ order by c.cst_regbd asc """
                break
            case 8:    //楼盘状态降序
                osql = """ order by c.cst_regbd desc """
                break

            case 9:    //车行状态升序
                osql = """ order by c.cst_regcar asc """
                break
            case 10:    //车行状态降序
                osql = """ order by c.cst_regcar desc """
                break
        }


        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        def total = Cst_basic.executeQuery(tsql + sql)[0].toLong()

        def items = Cst_basic.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    cst_id:it[0], cst_name: it[1], cst_mobile:it[2], cst_phonenum:it[3], cst_smsnum:it[4], cst_regbd: it[5], cst_regcar:it[6]
            ]
        }

        def dc_sql = """select count(*) from Discount_reg where reg_agentid = ${session.agentid} and reg_status in (${Enum_DiscountRegStatus.RS_no_wait.code}, ${Enum_DiscountRegStatus.RS_yes_wait.code})"""
        def dc_num = Discount_reg.executeQuery(dc_sql)[0]

        def pagenum = 0
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([total:total, pagenum: pagenum, dc_num: dc_num, items: items] as JSON)

    }

    //客户详情
    def cstinfo(){
        def ret = [status: 0, msg:'OK']

        def tmpcst = Cst_basic.read(params.long('id', 0))
        if(!tmpcst){
            ret.status = 2
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(session.agentid != tmpcst.cst_agtid){
            ret.status = 3
            ret.msg = "无查看权限"
            render(ret as JSON)
            return
        }

        ret.cst_name = tmpcst.cst_name
        ret.cst_id = tmpcst.id
        ret.cst_mobile = tmpcst.cst_mobile
        ret.cst_idcard = tmpcst.cst_idcard?.encodeAsHex()
        ret.cst_address = tmpcst.cst_address

        ret.cst_phonetime = tmpcst.cst_phonetime
        ret.cst_phonenum = tmpcst.cst_phonenum
        ret.cst_smsnum = tmpcst.cst_smsnum

        ret.cst_age = 0
        ret.cst_sex = "未知"

        if(tmpcst.cst_idcard?.length() >= 18){
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int birthyear = tmpcst.cst_idcard[6..9].toInteger()
            ret.cst_age = year - birthyear

            ret.cst_sex = "男"
            int sex = tmpcst.cst_idcard[16].toInteger()
            if(sex % 2 == 0){
                ret.cst_sex = "女"
            }
        }

        ret.roomwish = null
        ret.carwish = null

        //客户的楼盘需求

        def tmproomwh = Cst_wishroom.read(tmpcst.id)
        if(tmproomwh){
            def roomwh = [room_wishlevel: Enum_Wishlevel.WL_none.code]

            def level_enum = Enum_Wishlevel.byCode(tmproomwh.room_wishlevel)

            roomwh.room_wishlevel = level_enum? level_enum.code : Enum_Wishlevel.WL_none.code

            def area_enum = Enum_RoomArea.byCode(tmproomwh.room_area)
            roomwh.room_area = area_enum? area_enum.code : Enum_RoomArea.RA_all.code

            def rootype_enum = Enum_RoomType.byCode(tmproomwh.room_type)
            roomwh.room_type = rootype_enum? rootype_enum.code : Enum_RoomType.RT_all.code

            def price_enum = Enum_RoomPrice.byCode(tmproomwh.room_price)
            roomwh.room_price = price_enum? price_enum.code : Enum_RoomPrice.RP_all.code

            roomwh.room_place = tmproomwh.room_place
            roomwh.room_note = tmproomwh.room_note
            roomwh.room_usage = tmproomwh.room_usage

            roomwh.bd_type = Tag.read(tmproomwh.room_bd_type)?.tag_name
            roomwh.bd_point = Tag.read(tmproomwh.room_tagid)?.tag_name

            ret.roomwish = roomwh
        }

        //客户购车需求
        def tmpcarwh = Cst_wishcar.read(tmpcst.id)
        if(tmpcarwh){
            def carwh = [car_price:  Enum_CarPrice.CP_all.code]

            def carlevel_enum = Enum_Wishlevel.byCode(tmpcarwh.car_wishlevel)

            carwh.car_wishlevel = carlevel_enum? carlevel_enum.code : Enum_Wishlevel.WL_none.code

            def price_enum = Enum_CarPrice.byCode(tmpcarwh.car_price)
            carwh.car_price =  price_enum? price_enum.code : Enum_CarPrice.CP_all.code

            def level_enum = Enum_CarLevel.byCode(tmpcarwh.car_level)
            carwh.car_level =  level_enum? level_enum.code : Enum_CarLevel.CL_all.code

            def country_enum = Enum_CarCountry.byCode(tmpcarwh.car_country)
            carwh.car_country =  country_enum? country_enum.code : Enum_CarCountry.CC_all.code

            def gear_enum = Enum_CarGear.byCode(tmpcarwh.car_gear)
            carwh.car_gear =  gear_enum? gear_enum.code : Enum_CarGear.CG_all.code

            def frame_enum = Enum_CarFrame.byCode(tmpcarwh.car_frame)
            carwh.car_frame =  frame_enum? frame_enum.code : Enum_CarFrame.CF_all.code

            def volume_enum = Enum_CarVolume.byCode(tmpcarwh.car_volume)
            carwh.car_volume =  volume_enum? volume_enum.code : Enum_CarVolume.CV_all.code

            def drive_enum = Enum_CarDrive.byCode(tmpcarwh.car_drive)
            carwh.car_drive =  drive_enum? drive_enum.code : Enum_CarDrive.CD_all.code

            def fuel_enum = Enum_CarFuel.byCode(tmpcarwh.car_fuel)
            carwh.car_fuel =  fuel_enum? fuel_enum.code : Enum_CarFuel.CF_all.code

            def attach_enum = Enum_CarAttach.byCode(tmpcarwh.car_attach)
            carwh.car_attach =  attach_enum? attach_enum.code : Enum_CarAttach.CA_all.code

            carwh.car_point = Tag.read(tmpcarwh.car_point_tagid)?.tag_name
            carwh.car_type = Tag.read(tmpcarwh.car_brand_tagid)?.tag_name

            carwh.car_note = tmpcarwh.car_note

            ret.carwish = carwh
        }

        //查看客户关注的楼盘和车行优惠数目
        def bd_sql = """select count(*) from Discount_reg dr, Discount d where dr.reg_cstid = ${tmpcst.id} and dr.reg_discountid = d.id and d.dc_type = ${Enum_Discounttype.DT_discount_ok.code} and d.dc_reftype = ${Enum_Reftype.RT_building.code}"""

        def cb_sql = """select count(*) from Discount_reg dr, Discount d where dr.reg_cstid = ${tmpcst.id} and dr.reg_discountid = d.id and d.dc_type = ${Enum_Discounttype.DT_discount_ok.code} and d.dc_reftype = ${Enum_Reftype.RT_car.code}"""

        ret.bd_dc_num = Discount_reg.executeQuery(bd_sql)[0]

        ret.cb_dc_num = Discount_reg.executeQuery(cb_sql)[0]

        render(ret as JSON)
    }


    //客户楼盘报备状态列表
    def cst_bd_status(){
        def ret = [status: 0, msg:'OK']

        def tmpcst = Cst_basic.read(params.long('id', 0))
        if(!tmpcst){
            ret.status = 2
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(session.agentid != tmpcst.cst_agtid){ //如果不是自己的客户
            ret.status = 3
            ret.msg = "无查看权限"
            render(ret as JSON)
            return
        }

        def focusbd = Cst_favor.executeQuery("select favor_refid from Cst_favor where favor_cstid = ${tmpcst.id} and favor_reftype = ${Enum_Reftype.RT_building.code}")

        def tsql = "select count(*) "
        def vsql = """select c.reg_status, c.reg_bdid, bd.bd_name, bd.bd_phone, bd.bd_agenttel"""

        def sql = """ from Cst_regbd c, Building bd where c.reg_bdid = bd.id and c.reg_cstid = ${tmpcst.id}"""

        def stype = params.int('stype', -2)
        if(stype != -2){
            sql += """ and c.reg_status = ${stype}"""
        }

        def osql = """ order by c.id desc """




        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        def total = Cst_regbd.executeQuery(tsql + sql)[0].toLong()

        def items = Cst_regbd.executeQuery(vsql + sql + osql, mypage).collect {
            def bd_focus = false

            if(focusbd.contains(it[1])){
                bd_focus = true
            }

            [
                    reg_status:it[0], reg_id:it[1], reg_name:it[2], reg_phone:it[3], reg_agenttel:it[4], reg_focus: bd_focus
            ]
        }

        def pagenum = 0
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([status: 0, total:total, pagenum: pagenum, items: items] as JSON)
    }

    //客户车行报备状态列表
    def cst_cb_status(){
        def ret = [status: 0, msg:'OK']

        def tmpcst = Cst_basic.read(params.long('id', 0))
        if(!tmpcst){
            ret.status = 2
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(session.agentid != tmpcst.cst_agtid){  //如果不是自己的客户
            ret.status = 3
            ret.msg = "无查看权限"
            render(ret as JSON)
            return
        }

        def focuscb = Cst_favor.executeQuery("select favor_refid from Cst_favor where favor_cstid = ${tmpcst.id} and favor_reftype = ${Enum_Reftype.RT_car.code}")

        def tsql = "select count(*) "
        def vsql = """select cr.reg_status, cb.id, cb.cb_name, cb.cb_phone"""

        def sql = """ from Cst_regcar cr, Car_bank cb where cr.reg_carbankid = cb.id and cr.reg_cstid = ${tmpcst.id}"""

        def stype = params.int('stype', -2)
        if(stype != -2){
            sql += """ and cr.reg_status = ${stype}"""
        }


        def osql = """ order by cr.id desc """




        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        def total = Cst_regcar.executeQuery(tsql + sql)[0].toLong()

        def items = Cst_regcar.executeQuery(vsql + sql + osql, mypage).collect {
            def cb_focus = false

            if(focuscb.contains(it[1])){
                cb_focus = true
            }

            [
                    reg_status:it[0], reg_id:it[1], reg_name:it[2], reg_phone:it[3], reg_focus: cb_focus
            ]
        }

        def pagenum = 0
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([status: 0, total:total, pagenum: pagenum, items: items] as JSON)
    }

    //增加楼盘报备
    def addbdsub(){
        def ret = [status:0, msg:'新增报备成功']

        def tmpcst = Cst_basic.read(params.long('cst_id', 0))
        if(!tmpcst){
            ret.status = 2
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(session.agentid != tmpcst.cst_agtid){
            ret.status = 3
            ret.msg = "无报备权限"
            render(ret as JSON)
            return
        }

        def tmpbd = Building.get(params.long('bd_id', 0))
        if(!tmpbd){
            ret.status = 4
            ret.msg = "无效的楼盘"
            render(ret as JSON)
            return
        }

        //查看是否已报备
        def tmpcr = Cst_regbd.findByReg_bdidAndReg_cstid(tmpbd.id, tmpcst.id)
        if(tmpcr){
            ret.status = 5
            ret.msg = "已报备"
            render(ret as JSON)
            return
        }

        tmpcr = new Cst_regbd()
        tmpcr.with {
            reg_cstid = tmpcst.id
            reg_bdid = tmpbd.id
            reg_status = Enum_Regstatus.RS_wishyes.code

            save(flush: true)
        }

        Cst_basic.executeUpdate("update Cst_basic set cst_regbd = ${Enum_Regstatus.RS_wishyes.code} where id = ${tmpcst.id} and cst_regbd < ${Enum_Regstatus.RS_wishyes.code}")

        Building.executeUpdate("update Building set bd_submit = bd_submit + 1 where id = ${tmpbd.id}")

        render(ret as JSON)

    }

    //增加车行报备
    def addcbsub(){
        def ret = [status:0, msg:'增加报备成功']

        def tmpcst = Cst_basic.read(params.long('cst_id', 0))
        if(!tmpcst){
            ret.status = 2
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(session.agentid != tmpcst.cst_agtid){
            ret.status = 3
            ret.msg = "无报备权限"
            render(ret as JSON)
            return
        }

        def tmpcb= Car_bank.read(params.long('cb_id', 0))
        if(!tmpcb){
            ret.status = 4
            ret.msg = "无效的车行"
            render(ret as JSON)
            return
        }

        //查看是否已报备
        def tmpcr = Cst_regcar.findByReg_carbankidAndReg_cstid(tmpcb.id, tmpcst.id)
        if(tmpcr){
            ret.status = 5
            ret.msg = "已报备"
            render(ret as JSON)
            return
        }

        tmpcr = new Cst_regcar()
        tmpcr.with {
            reg_cstid = tmpcst.id
            reg_carbankid = tmpcb.id
            reg_status = Enum_Regstatus.RS_wishyes.code

            save(flush: true)
        }


        Cst_basic.executeUpdate("update Cst_basic set cst_regcar = ${Enum_Regstatus.RS_wishyes.code} where id = ${tmpcst.id} and cst_regcar < ${Enum_Regstatus.RS_wishyes.code}")

        //更新报备数
        Car_bank.executeUpdate("update Car_bank set cb_submit = cb_submit + 1 where id = ${tmpcb.id}")

        render(ret as JSON)

    }


    //客户可报备和已报备楼盘列表
    def bdsublist(){
        def ret = [status: 0, msg:'OK']

        def tmpcst = Cst_basic.read(params.long('cst_id', 0))
        if(!tmpcst){
            ret.status = 2
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(tmpcst.cst_agtid != session.agentid){
            ret.status = 3
            ret.msg = "客户不属于当前经纪人"
            render(ret as JSON)
            return
        }

        // 已报备楼盘状态列表
        def vsql = """select c.reg_status, bd.bd_name, bd.bd_area"""

        def sql = """ from Cst_regbd c, Building bd where c.reg_cstid = ${tmpcst.id} and c.reg_bdid = bd.id"""

        def subyes = Cst_regbd.executeQuery(vsql+sql).collect{
            [
                    reg_status:it[0] == Enum_Regstatus.RS_wishyes.code ? "待判客" : Enum_Regstatus.byCode(it[0])?.name,
                    reg_name: it[1], reg_area:it[2]
            ]
        }


        // 可报备楼盘列表
        vsql = """select bd.bd_name, bd.bd_area, bd.id"""

        sql = """ from Building bd where bd.id not in ( select c.reg_bdid from Cst_regbd c where  c.reg_cstid = ${tmpcst.id})"""

        def subno = Building.executeQuery(vsql+sql).collect{
            [
                    reg_name: it[0], reg_area:it[1], reg_id:it[2]
            ]
        }

        ret.subyes = subyes
        ret.subno =  subno

        render(ret as JSON)
    }

    //客户可报备和已报备车行列表
    def cbsublist(){
        def ret = [status: 0, msg:'OK']

        def tmpcst = Cst_basic.read(params.long('cst_id', 0))
        if(!tmpcst){
            ret.status = 2
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(tmpcst.cst_agtid != session.agentid){
            ret.status = 3
            ret.msg = "客户不属于当前经纪人"
            render(ret as JSON)
            return
        }

        // 已报备楼盘状态列表
        def vsql = """select c.reg_status, cb.cb_name, cb.cb_area"""

        def sql = """ from Cst_regcar c, Car_bank cb where c.reg_cstid = ${tmpcst.id} and c.reg_carbankid = cb.id"""

        def subyes = Cst_regcar.executeQuery(vsql+sql).collect{
            [
                    reg_status:it[0] == Enum_Regstatus.RS_wishyes.code ? "待判客" : Enum_Regstatus.byCode(it[0])?.name,
                    reg_name: it[1], reg_area:it[2]
            ]
        }

        // 可报备车行列表
        vsql = """select cb.cb_name, cb.cb_area, cb.id"""

        sql = """ from Car_bank cb where cb.id not in ( select c.reg_carbankid from Cst_regcar c where  c.reg_cstid = ${tmpcst.id})"""

        def subno = Building.executeQuery(vsql+sql).collect{
            [
                    reg_name: it[0], reg_area:it[1], reg_id: it[2]
            ]
        }

        ret.subyes = subyes
        ret.subno =  subno

        render(ret as JSON)
    }

    //单个楼盘中，我的可报备与已报备客户列表
    def bdsubcst(){
        def ret = [status: 0, msg:'OK']

        def tmpbd = Building.read(params.long('bd_id', 0))
        if(!tmpbd){
            ret.status = 2
            ret.msg = "无效的楼盘"
            render(ret as JSON)
            return
        }

        //可报备客户列表
        def vsql = """select c.id, c.cst_name, c.cst_mobile"""

        def sql = """ from Cst_basic c where c.cst_agtid = ${session.agentid} and c.cst_regbd <> ${Enum_Regstatus.RS_wishno.code} and c.id not in(select reg_cstid from Cst_regbd where reg_bdid = ${tmpbd.id}) order by c.cst_name"""

        def subno = Cst_basic.executeQuery(vsql+sql).collect{
            [
                    cst_id: it[0], cst_name:it[1], cst_mobile: it[2]
            ]
        }

        // 已报备客户状态列表
        vsql = """select cr.reg_status, c.cst_name"""

        sql = """ from Cst_regbd cr, Cst_basic c where cr.reg_bdid = ${tmpbd.id} and cr.reg_cstid = c.id and c.cst_agtid = ${session.agentid} order by c.cst_name"""

        def subyes = Cst_regcar.executeQuery(vsql+sql).collect{
            [
                    reg_status:it[0] == Enum_Regstatus.RS_wishyes.code ? "待判客" : Enum_Regstatus.byCode(it[0])?.name,
                    cst_name: it[1]
            ]
        }

        ret.subno = subno
        ret.subyes = subyes

        render(ret as JSON)

    }

    //单个车行中，我的可报备与已报备客户列表
    def cbsubcst(){
        def ret = [status: 0, msg:'OK']

        def tmpcb = Car_bank.read(params.long('cb_id', 0))
        if(!tmpcb){
            ret.status = 2
            ret.msg = "无效的车行"
            render(ret as JSON)
            return
        }

        //可报备客户列表
        def vsql = """select c.id, c.cst_name, c.cst_mobile"""

        def sql = """ from Cst_basic c where c.cst_agtid = ${session.agentid} and c.cst_regcar <> ${Enum_Regstatus.RS_wishno.code} and c.id not in(select reg_cstid from Cst_regcar where reg_carbankid = ${tmpcb.id}) order by c.cst_name"""

        def subno = Cst_basic.executeQuery(vsql+sql).collect{
            [
                    cst_id: it[0], cst_name:it[1], cst_mobile: it[2]
            ]
        }

        // 已报备客户状态列表
        vsql = """select cr.reg_status, c.cst_name"""

        sql = """ from Cst_regcar cr, Cst_basic c where cr.reg_carbankid = ${tmpcb.id} and cr.reg_cstid = c.id and c.cst_agtid = ${session.agentid} order by c.cst_name"""

        def subyes = Cst_regcar.executeQuery(vsql+sql).collect{
            [
                    reg_status:it[0] == Enum_Regstatus.RS_wishyes.code ? "待判客" : Enum_Regstatus.byCode(it[0])?.name,
                    cst_name: it[1]
            ]
        }

        ret.subno = subno
        ret.subyes = subyes

        render(ret as JSON)

    }


    //组团
    def createteam(){
        def ret = [status: 0, msg: '组团成功']

        if(session.agentstatus in [Enum_Agentstatus.AS_team_member.code, Enum_Agentstatus.AS_team_leader.code]){
            ret.status = 2
            ret.msg = "已是团员"
            render(ret as JSON)
            return
        }

        if(session.agentstatus != Enum_Agentstatus.AS_idcard_ok.code){
            ret.status = 3
            ret.msg = "经纪人未实名审核,不能组团"
            render(ret as JSON)
            return
        }

        def tmpagt = Agent.get(session.agentid)
        if(!tmpagt){
            ret.status = 4
            ret.msg = "无效的经纪人"
            render(ret as JSON)
            return
        }

        def teamno = generate_uid()

        def tmpteam = new Agent_team()
        tmpteam.with {
            team_no = teamno
            team_agtid = tmpagt.id
            team_name = params.tname? params.tname : "${tmpagt.agent_name}的团队"
            save()
        }

        tmpagt.with{
            agent_teamid = tmpteam.team_no
            agent_status = Enum_Agentstatus.AS_team_leader.code
            save(flush: true)
        }

        //更新session
        session.agentstatus = Enum_Agentstatus.AS_team_leader.code
        session.agentteamid = tmpagt.agent_teamid

        ret.agent_status = Enum_Agentstatus.AS_team_leader.code
        ret.team_no = teamno

        render(ret as JSON)


    }

    //加入团
    def jointeam(){
        def ret = [status: 0, msg: '入团成功']

        if(session.agentstatus in [Enum_Agentstatus.AS_team_member.code, Enum_Agentstatus.AS_team_leader.code]){
            ret.status = 2
            ret.msg = "已经是团员，只能加入一个团"
            render(ret as JSON)
            return
        }

        if(session.agentstatus != Enum_Agentstatus.AS_idcard_ok.code){
            ret.status = 3
            ret.msg = "经纪人未实名审核,不能入团"
            render(ret as JSON)
            return
        }

        def tmpteam = Agent_team.findByTeam_no(params.long('tno', 0))
        if(!tmpteam){
            ret.status = 4
            ret.msg = "团队不存在"
            render(ret as JSON)
            return
        }

        if(Agent.countByAgent_teamid(tmpteam.team_no) >= 15){
            ret.status = 5
            ret.msg = "已达到人数上限"
            render(ret as JSON)
            return
        }

        def tmpagt = Agent.get(session.agentid)
        if(!tmpagt){
            ret.status = 6
            ret.msg = "无效的经纪人"
            render(ret as JSON)
            return
        }

        tmpagt.with {
            agent_teamid = tmpteam.team_no
            agent_status = Enum_Agentstatus.AS_team_member.code
            save(flush: true)
        }

        //更新session
        session.agentstatus = Enum_Agentstatus.AS_team_member.code
        session.agentteamid = tmpagt.agent_teamid

        ret.agent_status = Enum_Agentstatus.AS_team_member.code

        render(ret as JSON)
    }

    //退团
    def quitteam(){
        def ret = [status: 0, msg: '退团成功']

        if(session.agentstatus != Enum_Agentstatus.AS_team_member.code){
            ret.status = 2
            ret.msg = "不是团员"
            render(ret as JSON)
            return
        }

        def tmpagt = Agent.get(session.agentid)
        if(!tmpagt){
            ret.status = 3
            ret.msg = "经纪人不存在"
            render(ret as JSON)
            return
        }

        def tmpteam = Agent_team.findByTeam_no(tmpagt.agent_teamid)
        if(!tmpteam){
            ret.status = 4
            ret.msg = "团队不存在"
            render(ret as JSON)
            return
        }

        tmpagt.with {
            agent_teamid = 0
            agent_status = Enum_Agentstatus.AS_idcard_ok.code
            save(flush: true)
        }

        //更新session
        session.agentstatus = Enum_Agentstatus.AS_idcard_ok.code
        session.agentteamid = tmpagt.agent_teamid

        ret.agent_status = Enum_Agentstatus.AS_idcard_ok.code

        render(ret as JSON)

    }

    //踢团员
    def kickmember(){
        def ret = [status: 0, msg: '成功']

        if(!params.memid){
            ret.status = 1
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        if(session.agentstatus != Enum_Agentstatus.AS_team_leader.code){
            ret.status = 3
            ret.msg = "不是团长，无权限"
            render(ret as JSON)
            return
        }

        def leader = Agent.read(session.agentid)
        if(!leader){
            ret.status = 4
            ret.msg = "无效的团长身份"
            render(ret as JSON)
            return
        }

        def tmpteam = Agent_team.findByTeam_no(leader.agent_teamid)
        if(!tmpteam){
            ret.status = 5
            ret.msg = "团队不存在"
            render(ret as JSON)
            return
        }

        if(tmpteam.team_agtid != leader.id){
            ret.status = 6
            ret.msg = "非此团队的团长，无踢人权限"
            render(ret as JSON)
            return
        }

        def member = Agent.get(params.long('memid', 0))
        if(!member){
            ret.status = 7
            ret.msg = "无效的团员"
            render(ret as JSON)
            return
        }

        if(member.agent_status != Enum_Agentstatus.AS_team_member.code){
            ret.status = 7
            ret.msg = "要踢的经纪人非团员身份"
            render(ret as JSON)
            return
        }

        if(member.agent_teamid != tmpteam.team_no){
            ret.status = 7
            ret.msg = "要踢的经纪人非此团队成员"
            render(ret as JSON)
            return
        }

        member.with {
            agent_teamid = 0
            agent_status = Enum_Agentstatus.AS_idcard_ok.code
            save(flush: true)
        }

        render(ret as JSON)

    }

    //更新团队名称
    def upteamname(){
        def ret = [status: 0, msg: '更新成功']

        if(!params.tname){
            ret.status = 1
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        if(session.agentstatus != Enum_Agentstatus.AS_team_leader.code){
            ret.status = 3
            ret.msg = "不是团长，无权限"
            render(ret as JSON)
            return
        }

        def leader = Agent.read(session.agentid)
        if(!leader){
            ret.status = 4
            ret.msg = "无效的团长身份"
            render(ret as JSON)
            return
        }

        def tmpteam = Agent_team.findByTeam_no(leader.agent_teamid)
        if(!tmpteam){
            ret.status = 5
            ret.msg = "团队不存在"
            render(ret as JSON)
            return
        }

        if(tmpteam.team_agtid != leader.id){
            ret.status = 6
            ret.msg = "非此团队的团长，无设置权限"
            render(ret as JSON)
            return
        }

        tmpteam.with {
            team_name = params.tname
            save(flush: true)
        }

        render(ret as JSON)
    }

    //我的团队
    def myteam(){
        def ret = [status: 0, msg: 'OK']

        if(! (session.agentstatus in [Enum_Agentstatus.AS_team_member.code, Enum_Agentstatus.AS_team_leader.code]) ){
            ret.status = 2
            ret.msg = "你没有加入任何团队"
            render(ret as JSON)
            return
        }

        def tmpagt = Agent.read(session.agentid)
        if(!tmpagt){
            ret.status = 3
            ret.msg = "无效的经纪人"
            render(ret as JSON)
            return
        }

        def tmpteam = Agent_team.findByTeam_no(tmpagt.agent_teamid)
        if(!tmpteam){
            ret.status = 4
            ret.msg = "所属团队不存在"
            render(ret as JSON)
            return
        }

        ret.team_id = tmpteam.id
        ret.team_name = tmpteam.team_name
        ret.team_no = tmpteam.team_no
        ret.team_num = Agent.executeQuery("""select count(*) from Agent where agent_teamid = ${tmpteam.team_no} and agent_status in (${Enum_Agentstatus.AS_team_member.code}, ${Enum_Agentstatus.AS_team_leader.code})""")[0]
        ret.team_iconid = tmpteam.team_iconid


        //团长的数据
        def teammate = []
        teammate += Agent.get(tmpteam.team_agtid).getcstsum()

        if(ret.team_num > 1){
            def team_member = Agent.executeQuery("select agent_name, agent_mobile, id from Agent where agent_teamid = ${tmpteam.team_no} and id <> ${tmpteam.team_agtid} and agent_status = ${Enum_Agentstatus.AS_team_member.code}").collect {
                [agent_name: it[0], agent_mobile: it[1], agent_id: it[2]]
            }

            team_member.each {
                it.cst_num = Cst_basic.countByCst_agtid(it.agent_id)
                it.bdregyes_num = Cst_basic.countByCst_agtidAndCst_regbd(it.agent_id, Enum_Regstatus.RS_regyes.code)
                it.bdregok_num = Cst_basic.countByCst_agtidAndCst_regbd(it.agent_id, Enum_Regstatus.RS_ok.code)

                it.cbregyes_num = Cst_basic.countByCst_agtidAndCst_regcar(it.agent_id, Enum_Regstatus.RS_regyes.code)
                it.cbregok_num = Cst_basic.countByCst_agtidAndCst_regcar(it.agent_id, Enum_Regstatus.RS_ok.code)
            }

            teammate += team_member

        }

        ret.team_mate = teammate
        render(ret as JSON )
    }

    //团队成员列表
    def memlist(){
        def ret = [status: 0, msg: 'OK']

        if(!(session.agentstatus in [Enum_Agentstatus.AS_team_member.code, Enum_Agentstatus.AS_team_leader.code])){
            ret.status = 2
            ret.msg = "非团队成员"
            render(ret as JSON)
            return
        }

        def tsql = "select count(*) "
        def vsql = """select a.id, a.agent_name"""

        def sql = """ from Agent a where a.agent_teamid = ${session.agentteamid} and agent_status in (${Enum_Agentstatus.AS_team_member.code}, ${Enum_Agentstatus.AS_team_leader.code})"""

        def osql = """ order by a.id desc """

        ret.items = Agent.executeQuery(vsql + sql + osql).collect {
            [
                    agent_id:it[0], agent_name:it[1]
            ]
        }

        render(ret as JSON)

    }

    //我的团队某个楼盘的业绩
    def team_bd_list(){
        def ret = [status: 0, msg:'OK']

        if(!(session.agentstatus in [Enum_Agentstatus.AS_team_member.code, Enum_Agentstatus.AS_team_leader.code])){
            ret.status = 2
            ret.msg = "非团队成员"
            render(ret as JSON)
            return
        }

        if(!params.bd_id){
            ret.status = 3
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def sorttype = params.int('stype', 0)


        def tsql = "select count(*) "
        def vsql = """select cr.reg_status, c.cst_name, a.agent_name, a.agent_mobile"""

        def sql = """ from Cst_regbd cr, Cst_basic c, Agent a where cr.reg_bdid = ${params.int('bd_id', 0)} and  c.id = cr.reg_cstid and c.cst_agtid = a.id and a.agent_teamid = ${session.agentteamid}"""


        def osql = """ order by cr.id desc """

        switch (sorttype){
            case 1:
                osql = """ order by a.agent_name desc """
                break
            case 2:
                osql = """ order by a.agent_name asc """
                break

            case 3:
                osql = """ order by c.cst_name desc """
                break
            case 4:
                osql = """ order by c.cst_name asc """
                break
            case 5:
                osql = """ order by cr.reg_status desc """
                break
            case 6:
                osql = """ order by cr.reg_status asc """
                break
        }

        ret.total = Discount.executeQuery(tsql + sql)[0].toLong()

        ret.items = Discount.executeQuery(vsql + sql + osql).collect {
            [
                    reg_status:it[0], cst_name:it[1], agent_name:it[2], agent_mobile: it[3]
            ]
        }

        render(ret as JSON)
    }

    //我的团队某个车行的业绩
    def team_cb_list(){
        def ret = [status: 0, msg:'OK']

        if(!(session.agentstatus in [Enum_Agentstatus.AS_team_member.code, Enum_Agentstatus.AS_team_leader.code])){
            ret.status = 2
            ret.msg = "非团队成员"
            render(ret as JSON)
            return
        }

        if(!params.cb_id){
            ret.status = 3
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def sorttype = params.int('stype', 0)

        def tsql = "select count(*) "
        def vsql = """select cr.reg_status, c.cst_name, a.agent_name, a.agent_mobile"""

        def sql = """ from Cst_regcar cr, Cst_basic c, Agent a where cr.reg_carbankid = ${params.int('cb_id', 0)} and  c.id = cr.reg_cstid and c.cst_agtid = a.id and a.agent_teamid = ${session.agentteamid}"""

        def osql = """ order by cr.id desc """

        switch (sorttype){
            case 1:
                osql = """ order by a.agent_name desc """
                break
            case 2:
                osql = """ order by a.agent_name asc """
                break

            case 3:
                osql = """ order by c.cst_name desc """
                break
            case 4:
                osql = """ order by c.cst_name asc """
                break
            case 5:
                osql = """ order by cr.reg_status desc """
                break
            case 6:
                osql = """ order by cr.reg_status asc """
                break
        }

        ret.total = Discount.executeQuery(tsql + sql)[0].toLong()

        ret.items = Discount.executeQuery(vsql + sql + osql).collect {
            [
                    reg_status:it[0], cst_name:it[1], agent_name:it[2], agent_mobile: it[3]
            ]
        }

        render(ret as JSON)
    }

    //我的团队某个经纪人楼盘业绩
    def team_agt_bdlist(){
        def ret = [status: 0, msg:'OK']

        if(!(session.agentstatus in [Enum_Agentstatus.AS_team_member.code, Enum_Agentstatus.AS_team_leader.code])){
            ret.status = 2
            ret.msg = "非团队成员"
            render(ret as JSON)
            return
        }

        if(!params.agent_id){
            ret.status = 3
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def tmpagt = Agent.read(params.long('agent_id', 0))
        if(!tmpagt || tmpagt.agent_teamid != session.agentteamid){
            ret.status = 4
            ret.msg = "无效的团队成员"
            render(ret as JSON)
            return
        }

        def sorttype = params.int('stype', 0)

        def tsql = "select count(*) "
        def vsql = """select cr.reg_status, c.cst_name, b.bd_name"""

        def sql = """ from Cst_regbd cr, Cst_basic c, Building b where c.id = cr.reg_cstid and c.cst_agtid = ${params.long('agent_id', 0)} and cr.reg_bdid = b.id"""

        def osql = """ order by cr.id desc """

        switch (sorttype){
            case 1:
                osql = """ order by b.bd_name desc """
                break
            case 2:
                osql = """ order by b.bd_name asc """
                break

            case 3:
                osql = """ order by c.cst_name desc """
                break
            case 4:
                osql = """ order by c.cst_name asc """
                break
            case 5:
                osql = """ order by cr.reg_status desc """
                break
            case 6:
                osql = """ order by cr.reg_status asc """
                break
        }

        ret.total = Discount.executeQuery(tsql + sql)[0].toLong()

        ret.items = Discount.executeQuery(vsql + sql + osql).collect {
            [
                    reg_status:it[0], cst_name:it[1], shop_name:it[2]
            ]
        }

        render(ret as JSON)
    }

    //我的团队某个经纪人的车行业绩
    def team_agt_cblist(){
        def ret = [status: 0, msg:'OK']

        if(!(session.agentstatus in [Enum_Agentstatus.AS_team_member.code, Enum_Agentstatus.AS_team_leader.code])){
            ret.status = 2
            ret.msg = "非团队成员"
            render(ret as JSON)
            return
        }

        if(!params.agent_id){
            ret.status = 3
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def tmpagt = Agent.read(params.long('agent_id', 0))
        if(!tmpagt || tmpagt.agent_teamid != session.agentteamid){
            ret.status = 4
            ret.msg = "无效的团队成员"
            render(ret as JSON)
            return
        }

        def sorttype = params.int('stype', 0)

        def tsql = "select count(*) "
        def vsql = """select cr.reg_status, c.cst_name, cb.cb_name"""

        def sql = """ from Cst_regcar cr, Cst_basic c, Car_bank cb where c.id = cr.reg_cstid and c.cst_agtid = ${params.long('agent_id', 0)} and cr.reg_carbankid = cb.id"""

        def osql = """ order by cr.id desc """

        switch (sorttype){
            case 1:
                osql = """ order by cb.cb_name desc """
                break
            case 2:
                osql = """ order by cb.cb_name asc """
                break

            case 3:
                osql = """ order by c.cst_name desc """
                break
            case 4:
                osql = """ order by c.cst_name asc """
                break
            case 5:
                osql = """ order by cr.reg_status desc """
                break
            case 6:
                osql = """ order by cr.reg_status asc """
                break
        }

        ret.total = Discount.executeQuery(tsql + sql)[0].toLong()

        ret.items = Discount.executeQuery(vsql + sql + osql).collect {
            [
                    reg_status:it[0], cst_name:it[1], shop_name:it[2]
            ]
        }

        render(ret as JSON)
    }


    //我的银行卡
    def mybclist(){
        def ret = [status: 0, msg: 'OK']

        def tsql = "select count(*) "
        def vsql = """select b.id, b.bank_id, b.bank_account """

        def sql = """ from Agent_bank b where bank_agentid = ${session.agentid}"""

        def osql = """ order by b.id desc """

        ret.total = Agent_bank.executeQuery(tsql + sql)[0].toLong()

        ret.items = Agent_bank.executeQuery(vsql + sql + osql).collect {
            def bk_name = "未知银行"
            if(it[1] != 0){
                bk_name = Bank_type.findById(it[1])?.bt_name
            }

            [
                    bc_id: it[0], bank_name: bk_name, bank_account:it[2][-4..-1]
            ]
        }

        render(ret as JSON)

    }

    //新增银行卡
    def addbc(){
        def ret = [status: 0, msg: '增加成功']

        if(!params.account || params.account.length() < 16){
            ret.status = 1
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }


        if(!(session.agentstatus in [Enum_Agentstatus.AS_idcard_ok.code, Enum_Agentstatus.AS_team_member.code, Enum_Agentstatus.AS_team_leader.code])){
            ret.status = 3
            ret.msg = "未实名认证"
            render(ret as JSON)
            return
        }

        def tmpbc = Agent_bank.findByBank_account(params.account)
        if(tmpbc){
            ret.status = 4
            ret.msg = "账号已存在"
            render(ret as JSON)
            return
        }

        def prefix = params.account[0..5]

        def tmpbt = Bank_type.findByBt_prefix(prefix)

        tmpbc = new Agent_bank()
        tmpbc.with {
            bank_id = tmpbt? tmpbt.id : 0
            bank_account = params.account
            bank_agentid = session.agentid
            save(flush: true)
        }

        render(ret as JSON)
    }

    //删除我的银行卡
    def delbc(){
        def ret = [status: 0, msg: '删除成功']

        if(!params.bc_id){
            ret.status = 1
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def tmpbc = Agent_bank.read(params.long('bc_id', 0))
        if(!tmpbc){
            ret.status = 3
            ret.msg = "无效的银行卡"
            render(ret as JSON)
            return
        }

        if(tmpbc.bank_agentid != session.agentid){
            ret.status = 4
            ret.msg = "无删除权限"
            render(ret as JSON)
            return
        }

        tmpbc.delete(flush: true)

        render(ret as JSON)
    }

    //我的资产
    def myassets(){
        def ret = [status: 0, msg:'OK']

        def tmpac = Agent_account.read(session.agentid)
        if(!tmpac){
            ret.at_usable = 0
            ret.at_fixed = 0
            render(ret as JSON)
            return
        }

        ret.at_usable = tmpac.at_usable
        ret.at_fixed = tmpac.at_fixed

        render(ret as JSON)
    }

    //提现记录
    def mycashlog(){
        def ret = [status: 0, msg:'OK']

        def tsql = "select count(*) "
        def vsql = """select ac.cap_time, ac.cap_num, ac.cap_left """

        def sql = """ from Agent_caplog ac where cap_agentid = ${session.agentid} and cap_type = ${Enum_Captype.CT_out_cash.code}"""

        def osql = """ order by ac.id desc """

        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        ret.total = Agent_caplog.executeQuery(tsql + sql)[0].toLong()

        def pagenum = 0
        if(reqnum){
            pagenum = ret.total.mypage(reqnum)
        }else{
            pagenum = ret.total > 0? 1 : 0
        }

        ret.pagenum =  pagenum
//////////////////////////////////
        def tmpac = Agent_account.read(session.agentid)
        ret.at_usable = tmpac?.at_usable?:0
        ret.at_fixed = tmpac?.at_fixed?:0

        ret.items = Agent_caplog.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    cap_time:it[0], cap_num:it[1], cap_left:it[2]
            ]
        }

        render(ret as JSON)
    }

    //我的发放记录
    def mypaylog(){
        def ret = [status: 0, msg:'OK']

        def tsql = "select count(*) "
        def vsql = """select ac.cap_time, ac.cap_num, ac.cap_type, ac.cap_left"""

        def sql = """ from Agent_caplog ac where cap_agentid = ${session.agentid} and cap_type <> ${Enum_Captype.CT_out_cash.code}"""

        def osql = """ order by ac.cap_time desc """

        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        ret.total = Agent_caplog.executeQuery(tsql + sql)[0].toLong()

        def pagenum = 0
        if(reqnum){
            pagenum = ret.total.mypage(reqnum)
        }else{
            pagenum = ret.total > 0? 1 : 0
        }

        ret.pagenum = pagenum


        def tmpac = Agent_account.read(session.agentid)
        ret.at_usable = tmpac?.at_usable?:0
        ret.at_fixed = tmpac?.at_fixed?:0


        ret.items = Agent_caplog.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    cap_time:it[0], cap_num:it[1], cap_type: Enum_Captype.byCode(it[2])?.name, cap_left:it[3]
            ]
        }

        render(ret as JSON)
    }

    //提现
    def withdraw(){
        def ret = [status: 0, msg:'提现成功']

        if(!params.cspwd || !params.csnum){
            ret.status = 1
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def tmpac = Agent_account.get(session.agentid)
        if(!tmpac){
            ret.status = 3
            ret.msg = "你还没任何收入"
            render(ret as JSON)
            return
        }

        def tmpagt = Agent.read(session.agentid)
        if(!tmpagt){
            ret.status = 4
            ret.msg = "你是无效的经纪人"
            render(ret as JSON)
            return
        }

        if(!tmpagt.agent_cashpwd){
            ret.status = 5
            ret.msg = "未设置提现密码"
            render (ret as JSON)
            return
        }

        if(tmpagt.agent_cashpwd != (tmpagt.agent_salt + params.cspwd).encodeAsSHA1()){
            ret.status = 6
            ret.msg = "提现密码不正确"
            render (ret as JSON)
            return
        }

        def cs_num = params.long('csnum', 0)
        if(cs_num < 1){
            ret.status = 7
            ret.msg = "提现金额必须大于1"
            render (ret as JSON)
            return
        }


        if(cs_num > tmpac.at_usable){
            ret.status = 8
            ret.msg = "提现金额必须不大于可用金额"
            render (ret as JSON)
            return
        }

        //更新可用金额,提现总额
        tmpac.with {
            at_usable = at_usable - cs_num
            at_sumcash = at_sumcash + cs_num
            save()
        }

        //生成新的提现记录
        def tmpcap = new Agent_caplog()
        tmpcap.with {
            cap_time = new Date()
            cap_num = cs_num
            cap_left = tmpac.at_usable + tmpac.at_fixed
            cap_type = Enum_Captype.CT_out_cash.code
            cap_agentid = session.agentid
            save(flush: true)
        }

        render(ret as JSON)

    }

    //新增收款记录
    def addcsrcv(){
        def ret = [status: 0, msg:'收款记录成功增加']

        if(!params.cstid || !params.csnum || !params.type || !params.sn){
            ret.status = 2
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }


        def cs_num = params.long('csnum', 0)
        if(cs_num < 1){
            ret.status = 3
            ret.msg = "收款金额不正确"
            render (ret as JSON)
            return
        }

        def cstid = params.long('cstid', 0)
        if(!Cst_basic.read(cstid)){
            ret.status = 4
            ret.msg = "无效的客户"
            render (ret as JSON)
            return
        }




        //生成新的收款记录
        def tmpcp = new Cst_pay()
        tmpcp.with {
            pay_cstid = cstid
            pay_agentid = session.agentid
            pay_money = cs_num
            pay_type = params.int('type', Enum_Reftype.RT_building.code)
            pay_time = new Date()
            pay_sn = params.sn
            pay_note = params.note

            save(flush: true)
        }

        ret.rcv_id = tmpcp.id

        render(ret as JSON)
    }

/*
    //新增周计划
    def addwplan(){
        def ret = [status: 0, msg:'OK']

        //每周的第一天是从星期天算起，新增周计划只能是下周

        def now = new Date()
        Date week = null
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] - 1).days + 7.days
        }

        week.clearTime()

        //查看是否已有本周(也就是下周)计划
        def plan_week = Agent_plan.findByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, week, Enum_Plantype.PT_week.code)
        if(plan_week){
            ret.status = 2
            ret.msg = "已有下周计划，只能修改"
            render(ret as JSON)
            return
        }

        //新增周计划
        plan_week = new Agent_plan()
        plan_week.with {
            plan_agentid = session.agentid
            plan_date = week
            plan_type = Enum_Plantype.PT_week.code

            plan_roomdeal = params.int('roomdeal', 0)
            plan_roomregok = params.int('roomregok', 0)

            plan_cardeal = params.int('cardeal', 0)
            plan_carregok = params.int('carregok', 0)

            plan_sms = params.int('sms', 0)
            plan_phone = params.int('phone', 0)

            plan_cst = params.int('cst', 0)

            save(flush: true)
        }

        render(ret as JSON)
    }

    //新增月计划
    def addmplan(){
        def ret = [status: 0, msg:'OK']

        def now = new Date()
        now.date = 1
        now.month = now.month + 1
        now.clearTime()

        //查看是否已有本周(也就是下周)计划
        def plan_month = Agent_plan.findByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, now, Enum_Plantype.PT_month.code)
        if(plan_month){
            ret.status = 2
            ret.msg = "已有下月计划，只能修改"
            render(ret as JSON)
            return
        }

        //新增月计划
        plan_month = new Agent_plan()
        plan_month.with {
            plan_agentid = session.agentid
            plan_date = now
            plan_type = Enum_Plantype.PT_month.code

            plan_roomdeal = params.int('roomdeal', 0)
            plan_roomregok = params.int('roomregok', 0)

            plan_cardeal = params.int('cardeal', 0)
            plan_carregok = params.int('carregok', 0)

            plan_sms = params.int('sms', 0)
            plan_phone = params.int('phone', 0)

            plan_cst = params.int('cst', 0)

            save(flush: true)
        }

        render(ret as JSON)
    }
*/

    //修改周计划
    def updwplan(){
        def ret = [status: 0, msg:'修改成功']

        def now = new Date()
        Date week = null
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] - 1).days + 7.days
        }

        week.clearTime()

        //查看是否已有本周(也就是下周)计划
        def plan_week = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, week, Enum_Plantype.PT_week.code)

        //更新下周计划
        plan_week.with {
            plan_roomdeal = params.int('roomdeal', 0)
            plan_roomregok = params.int('roomregok', 0)

            plan_cardeal = params.int('cardeal', 0)
            plan_carregok = params.int('carregok', 0)

            plan_sms = params.int('sms', 0)
            plan_phone = params.int('phone', 0)

            plan_cst = params.int('cst', 0)

            save(flush: true)
        }

        render(ret as JSON)
    }

    //修改月计划
    def updmplan(){
        def ret = [status: 0, msg:'修改成功']

        def now = new Date()
        now.date = 1
        now.month = now.month + 1
        now.clearTime()

        //查看是否已有本周(也就是下周)计划
        def plan_month = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, now, Enum_Plantype.PT_month.code)

        //更新下月计划
        plan_month.with {
            plan_roomdeal = params.int('roomdeal', 0)
            plan_roomregok = params.int('roomregok', 0)

            plan_cardeal = params.int('cardeal', 0)
            plan_carregok = params.int('carregok', 0)

            plan_sms = params.int('sms', 0)
            plan_phone = params.int('phone', 0)

            plan_cst = params.int('cst', 0)

            save(flush: true)
        }

        render(ret as JSON)
    }

    //周计划
    def weekplan(){
        def ret = [status: 0, msg:'OK']

        int week_offset = params.int('offset', 0)  //查看第几周的计划 0 表示本周, -n 表示之前几周，1表示下一周

        if (week_offset > 1) {
            ret.status = 1
            ret.msg = "往后最多查看下周计划"
            render(ret as JSON)
            return
        }

        def now = new Date()
        Date week = null
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] - 1).days + (week_offset * 7).days
        }

        week.clearTime()

        //查看是否周计划
        def plan_week = Agent_plan.findByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, week, Enum_Plantype.PT_week.code)

        //如果查看的是下个周的计划
        if (!plan_week && (week_offset == 1)) {
            use(TimeCategory) {
                week = week - 7.days
            }

            plan_week = Agent_plan.findByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, week, Enum_Plantype.PT_week.code)
        }

        if(!plan_week){
            ret.plan = [:]
        }else {
            ret.plan = plan_week.planinfo()
        }

        render(ret as JSON)
    }


    //月计划
    def monthplan(){
        def ret = [status: 0, msg:'OK']

        int month_offset = params.int('offset', 0)  //查看第几月的计划 0 表示本月, -n 表示之前几月，1表示下一月

        if (month_offset > 1) {
            ret.status = 1
            ret.msg = "往后最多查看下月计划"
            render(ret as JSON)
            return
        }

        def now = new Date()
        now.date = 1
        now.month = now.month + month_offset
        now.clearTime()

        //查看是否已有下月计划
        def plan_month = Agent_plan.findByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, now, Enum_Plantype.PT_month.code)

        //如果查看的是下个周的计划
        if (!plan_month && (month_offset == 1)) {
            now.month = now.month - 1

            plan_month = Agent_plan.findByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, now, Enum_Plantype.PT_month.code)
        }

        if(!plan_month){
            ret.plan = [:]
        }else {
            ret.plan = plan_month.planinfo()
        }

        render(ret as JSON)
    }

/*
    //查看我的本周计划和下周计划详情
    def mywplans(){
        def ret = [status: 0, msg:'OK']

        //返回本周计划

        def now = new Date()
        Date week = null
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] - 1).days
        }
        week.clearTime()

        //查看是否有上周计划
        ret.thisplan = [:]

        def thiswplan = Agent_plan.findByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, week, Enum_Plantype.PT_week.code)
        if(thiswplan){
            ret.thisplan = thiswplan.planinfo()
        }

        //返回上周计划
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] - 1).days - 7.days
        }
        week.clearTime()

        //查看是否有上周计划
        ret.lastplan = [:]

        def lastwplan = Agent_plan.findByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, week, Enum_Plantype.PT_week.code)
        if(lastwplan){
            ret.lastplan = lastwplan.planinfo()
        }

        render(ret as JSON)
    }

    //查看我的本月计划和下月计划详情
    def mymplans(){
        def ret = [status: 0, msg:'OK']

        //返回本月计划

        def now = new Date()
        now.date = 1
        now.clearTime()

        //查看是否有本月计划
        ret.thisplan = [:]

        def thismplan = Agent_plan.findByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, now, Enum_Plantype.PT_month.code)
        if(thismplan){
            ret.thisplan = thismplan.planinfo()
        }

        //返回上月计划
        now.month = now.month + 1

        //查看是否有上月计划
        ret.lastplan = [:]

        def lastmplan = Agent_plan.findByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, now, Enum_Plantype.PT_month.code)
        if(lastmplan){
            ret.lastplan = lastmplan.planinfo()
        }

        render(ret as JSON)
    }
*/


    //生日关怀列表
    def cstbthgreet(){
        def ret = [status: 0, msg:'OK']

        if(!params.date){
            ret.status = 2
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def tmpdate = params.date("date", "yyyy-MM")
        if(!tmpdate){
            ret.status = 3
            ret.msg = "无效的日期"
            render(ret as JSON)
            return
        }


        def before_date = null

        use(TimeCategory) {
            before_date = (tmpdate - 7.days).format('yyyy-MM-dd')
        }

        def month = tmpdate.format('MM')

        tmpdate.month = tmpdate.month + 1

        //得到我在每个月初7天之内发送的所有生日关怀的记录
        def birthsent = Cst_greet.executeQuery("select cg.greet_cstid, cg.greet_way, cg.greet_date from Cst_greet cg where greet_type = ${Enum_Greettype.GT_birth.code} and greet_agentid = ${session.agentid} and cg.greet_date > '${before_date}' and  cg.greet_date < '${tmpdate.format('yyyy-MM-dd')}'")


        ret.sendyes = []  //已经发送的
        ret.sendno = []   //未发送的

        def vsql = """select c.id, c.cst_name, c.cst_mobile, substring(c.cst_idcard,11,2) as cst_brithmonth, substring(c.cst_idcard,13,2) as cst_birthday """

        def sql = """ from Cst_basic c where c.cst_agtid = ${session.agentid} and char_length(c.cst_idcard) > 17 and substring(c.cst_idcard,11,2) = '${month}'"""

        def osql = """ order by substring(c.cst_idcard,13,2) """

        Cst_basic.executeQuery(vsql + sql + osql).collect {
            def find = false

            for(item in birthsent) {
                if (item[0] == it[0]) { //如果找到，则已发送增加
                    ret.sendyes.add([
                            cst_id: it[0], cst_name: it[1], cst_mobile: it[2], cst_birthmonth: it[3], cst_birthday: it[4], greet_way:item[1], greet_date:item[2]?.format('MM-dd HH:mm:ss')
                    ])

                    find = true
                    break
                }
            }

            if(!find){ //如果没找到，则未发送增加
                ret.sendno.add([
                        cst_id: it[0], cst_name: it[1], cst_mobile: it[2], cst_birthmonth: it[3], cst_birthday: it[4]
                ])
            }

        }

        render(ret as JSON)

    }



    //节日关怀列表
    def cstdaygreet(){
        def ret = [status: 0, msg:'OK']

        def tmph = Sys_holiday.read(params.long('id', 0))
        if(!tmph){
            ret.status = 2
            ret.msg = "无效的节日"
            render(ret as JSON)
            return
        }

        def before_date = null
        def tomorrow = null

        use(TimeCategory) {
            before_date = (tmph.holiday_day - 7.days).format('yyyy-MM-dd')
            tomorrow = (tmph.holiday_day + 1.days).format('yyyy-MM-dd')
        }

        //得到所有在节日日期7天之前到前天的所有节日的记录
        def holidaysent = Cst_greet.executeQuery("select cg.greet_cstid, cg.greet_way, cg.greet_date from Cst_greet cg where greet_type = ${Enum_Greettype.GT_holiday.code} and greet_agentid = ${session.agentid} and cg.greet_date > '${before_date}' and cg.greet_date < '${tomorrow}'")


        ret.sendyes = []  //已经发送的
        ret.sendno = []   //未发送的

        def vsql = """select c.id, c.cst_name, c.cst_mobile"""

        def sql = """ from Cst_basic c where c.cst_agtid = ${session.agentid}"""

        def osql = """ order by c.cst_name """

        Cst_basic.executeQuery(vsql + sql + osql).collect {
            def find = false

            for(item in holidaysent) {
                if (item[0] == it[0]) { //如果找到，则已发送增加
                    ret.sendyes.add([
                            cst_id: it[0], cst_name: it[1], cst_mobile: it[2], greet_way:item[1], greet_date: item[2]?.format('MM-dd HH:mm:ss')
                    ])

                    find = true
                    break
                }
            }

            if(!find){ //如果没找到，则未发送增加
                ret.sendno.add([
                        cst_id: it[0], cst_name: it[1], cst_mobile: it[2]
                ])
            }

        }

        render(ret as JSON)

    }


    //增加客户关怀
    def addgreet(){
        def ret = [status: 0, msg: 'OK']

        if(!params.cstid || !params.way || !params.type){
            ret.status = 2
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def tmpcst = Cst_basic.get(params.long('cstid', 0))
        if(!tmpcst){
            ret.status = 3
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(tmpcst.cst_agtid != session.agentid){
            ret.status = 3
            ret.msg = "客户不属于当前经纪人"
            render(ret as JSON)
            return
        }

        def now = new Date()

        def tmpcg = new Cst_greet()
        tmpcg.with {
            greet_cstid = params.long('cstid', 0)
            greet_agentid = session.agentid
            greet_date = now

            greet_way = params.int('way', Enum_Greetway.GW_sms.code)
            greet_type = params.int('type', Enum_Greettype.GT_holiday.code)

            save()
        }

        //更新与客户联系的次数
        Date week = null
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] - 1).days
        }

        week.clearTime()

        def plan_week = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(tmpcst.cst_agtid, week, Enum_Plantype.PT_week.code)

        def tmptime = new Date()
        tmptime.date = 1
        tmptime.clearTime()
        def plan_month = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(tmpcst.cst_agtid, tmptime, Enum_Plantype.PT_month.code)


        //增加打电话次数记录
        if (tmpcg.greet_way == Enum_Greetway.GW_phone.code) {
            tmpcst.cst_phonenum++
            tmpcst.cst_phonetime = now

            plan_week.log_phone++
            plan_month.log_phone++
        }
        //增加发短信次数记录
        else if(tmpcg.greet_way == Enum_Greetway.GW_sms.code){
            tmpcst.cst_smsnum++
            tmpcst.cst_smstime = now

            plan_week.log_sms++
            plan_month.log_sms++
        }

        plan_week.save()
        plan_month.save()

        tmpcst.save(flush: true)

        render(ret as JSON)
    }

    //考试列表
    def testlogs(){
        def ret = [status: 0, msg: 'OK']

        def tmplist = Test_subject.executeQuery("select s.id, s.subject_name from Test_subject s").collect {
            [id: it[0], name:it[1]]
        }

        tmplist.each{
            it.testlog = Test_log.executeQuery("select test_score, test_time from Test_log where test_agentid = ${session.agentid} and test_subjectid = ${it.id} order by test_time desc", [max: 1]).collect {
                 [
                     test_score:it[0], test_time:it[1]
                 ]
            }[0]
        }

        ret.items = tmplist


        render(ret as JSON)
    }

    //客户优惠列表
    def cstdclist(){
        def ret = [status: 0, msg: 'OK']

        def tmpcst = Cst_basic.read(params.long('id', 0))
        if(!tmpcst){
            ret.status = 1
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(tmpcst.cst_agtid != session.agentid){
            ret.status = 2
            ret.msg = "无查看权限"
            render(ret as JSON)
            return
        }

        def tsql = "select count(*) "
        def vsql = """select dc.dc_title, dc.dc_fromtime, dc.dc_endtime, dc.id"""

        def sql = """ from Discount dc, Discount_reg dr where dr.reg_cstid = ${tmpcst.id} and dr.reg_discountid = dc.id and dc.dc_type = ${Enum_Discounttype.DT_discount_ok.code} and dc.dc_reftype = ${params.int('reftype', Enum_Reftype.RT_building.code)}"""

        def osql = """ order by dr.id desc """


        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        ret.total = Discount.executeQuery(tsql + sql)[0].toLong()

        ret.items = Discount.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    dc_title: it[0], dc_fromtime:it[1],
                    dc_endtime:it[2], dc_id:it[3]
            ]
        }

        ret.pagenum = 0
        if(reqnum){
            ret.pagenum = ret.total.mypage(reqnum)
        }else{
            ret.pagenum = ret.total > 0? 1 : 0
        }

        render(ret as JSON)


    }

    //客户对楼盘关注处理
    def cstbdfavorop(){
        def ret = [status:0, msg:"OK"]

        if(!params.cst_id || !params.ref_id){
            ret.status = 1
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def tmpcst = Cst_basic.read(params.long('cst_id', 0))
        if(!tmpcst){
            ret.status = 2
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(tmpcst.cst_agtid != session.agentid){
            ret.status = 3
            ret.msg = "无权限"
            render(ret as JSON)
            return
        }

        def tmpfavor = Cst_favor.findByFavor_cstidAndFavor_refidAndFavor_reftype(tmpcst.id, params.long('ref_id', 0), Enum_Reftype.RT_building.code)


        if (params.op == "1") {
            ret.msg = "增加关注成功"
            if(!tmpfavor){
                new Cst_favor().with {
                    favor_cstid = tmpcst.id
                    favor_reftype = Enum_Reftype.RT_building.code
                    favor_refid =  params.long('ref_id', 0)

                    save(flush: true)
                }
            }

        }else if (params.op == "2") {
            ret.msg = "取消关注成功"
            if(tmpfavor){
                Cst_favor.executeUpdate("delete from Cst_favor where id = ${tmpfavor.id}")
            }

        }

        render (ret as JSON)
    }


    //客户对车行关注处理
    def cstcbfavorop(){
        def ret = [status:0, msg:"OK"]

        if(!params.cst_id || !params.ref_id){
            ret.status = 1
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def tmpcst = Cst_basic.read(params.long('cst_id', 0))
        if(!tmpcst){
            ret.status = 2
            ret.msg = "无效的客户"
            render(ret as JSON)
            return
        }

        if(tmpcst.cst_agtid != session.agentid){
            ret.status = 3
            ret.msg = "无权限"
            render(ret as JSON)
            return
        }
        def tmpfavor = Cst_favor.findByFavor_cstidAndFavor_refidAndFavor_reftype(tmpcst.id, params.long('ref_id', 0), Enum_Reftype.RT_car.code)


        if (params.op == "1") {
            ret.msg = "新增关注成功"
            if(!tmpfavor){
                new Cst_favor().with {
                    favor_cstid = tmpcst.id
                    favor_reftype = Enum_Reftype.RT_car.code
                    favor_refid =  params.long('ref_id', 0)

                    save(flush: true)
                }
            }

        }else if (params.op == "2") {
            ret.msg = "取消关注成功"
            if(tmpfavor){
                Cst_favor.executeUpdate("delete from Cst_favor where id = ${tmpfavor.id}")
            }

        }

        render (ret as JSON)
    }

    //新增用户反馈
    def feedback(){
        def ret = [status:0, msg:"反馈成功，谢谢您的宝贵建议"]

        new Agent_feedback().with {
            fb_content = params.fb_content
            fb_agentid = session.agentid
            save(flush: true)
        }

        render (ret as JSON)
    }


    //登录后修改手机号，验证新手机是否已经存在
    def checkmobile(){
        def ret = [status: 0,msg: '验证通过']

        def mobile = params.mobile
        if (!(mobile ==~ ~FS_ismobile)) {
            ret.status = 1
            ret.msg = "新手机号码不正确"
            render (ret as JSON)
            return
        }

        def tmpagent = Agent.findByAgent_mobile(mobile)
        if(tmpagent){
            ret.status = 2
            ret.msg = "新手机号码已存在"
            render (ret as JSON)
            return
        }

        tmpagent = Agent.read(session.agentid)

        if (tmpagent.agent_passwd != (tmpagent.agent_salt + params.chkpwd).encodeAsSHA1()) {
            ret.status = 3
            ret.msg = "原始密码不正确"
            render (ret as JSON)
            return
        }

        // sned msg to old mobile
        Random myrand = new Random()
        String randcode = (0..5).collect { (0..9)[myrand.nextInt(10)] }.join()
        session.smscode = tmpagent.agent_mobile + randcode

        sms_sendcode(Enum_Smstype.ST_setmobile.code, [randcode], tmpagent.agent_mobile)

        render (ret as JSON)

    }

    //登录后，修改手机号
    def resetmobile(){
        def ret=[status:0,msg:'修改成功']

        if ((!session.smscode)||(!params.chkcode)) {
            ret.status = 1
            ret.msg = '参数错误'
            render (ret as JSON)
            return
        }

        if (session.smscode != params.chkcode) {
            ret.status = 2
            ret.msg = '验证码不正确'
            render (ret as JSON)
            return
        }

        def new_mobile = params.mobile

        if (!(new_mobile ==~ ~FS_ismobile)) {
            ret.status = 3
            ret.msg = "新手机号码格式不正确"
            render (ret as JSON)
            return
        }

        String old_mobile = params.chkcode[0..10]

        def tmpagent = Agent.findByAgent_mobile(old_mobile)

        if (!tmpagent) {
            ret.status = 4
            ret.msg = '用户不存在'
            render (ret as JSON)
            return
        }

        tmpagent.with {
            agent_mobile = new_mobile
            save(flush: true)
        }

        session.agentmobile = new_mobile

        render (ret as JSON)
    }


    //登录后忘记提现密码，短信重置提现密码
    def smsrstcspwd(){
        def ret=[status:0,msg:'设置成功']

        if ((!session.smscode)||(!params.chkcode)) {
            ret.status = 1
            ret.msg = '参数错误'
            render (ret as JSON)
            return
        }
        if (session.smscode != params.chkcode) {
            ret.status = 2
            ret.msg = '验证码不正确'
            render (ret as JSON)
            return
        }

        String mobile = params.chkcode[0..10]

        Agent tmpagent = Agent.findByAgent_mobile(mobile)

        if (!tmpagent) {
            ret.status = 3
            ret.msg = '用户不存在'
            render (ret as JSON)
            return
        }

        tmpagent.with {
            agent_cashpwd = (agent_salt + params.passwd).encodeAsSHA1()

            save(flush: true)
        }

        render (ret as JSON)
    }


    //未处理的客户优惠列表
    def dcreglist(){

        def ncst_sql = """select dr.id, dr.reg_discountid, dr.reg_name, dr.reg_mobile, dc.dc_title, dc.dc_endtime from Discount_reg dr, Discount dc where dr.reg_discountid = dc.id and dr.reg_agentid = ${session.agentid} and  reg_cstid = 0"""
        def ocst_sql = """select dr.id, dr.reg_discountid, dr.reg_name, dr.reg_mobile, dc.dc_title, dc.dc_endtime from Discount_reg dr, Discount dc where dr.reg_discountid = dc.id and dr.reg_agentid = ${session.agentid} and  reg_cstid <> 0"""

        def ret = [:]
        ret.newcst = Discount_reg.executeQuery(ncst_sql).collect {
            [
                dr_id:it[0], dc_id:it[1], reg_name:it[2], reg_mobile:it[3],dc_title: it[4].toString().length() > 4? it[4][0..4] : it[4],
                dc_endtime: it[5]
            ]
        }

        ret.oldcst = Discount_reg.executeQuery(ocst_sql).collect {
            [
                    dr_id:it[0], dc_id:it[1], reg_name:it[2], reg_mobile:it[3],dc_title: it[4].toString().length() > 4? it[4][0..4] : it[4],
                    dc_endtime: it[5]
            ]
        }

        render(ret as JSON)

    }


    //处理客户优惠列表
    def setregdc(){
        def ret = [status: 0, msg: '处理成功']
        def tmpdr = Discount_reg.read(params.long('id', 0))
        if(!tmpdr){
            ret.status = 1
            ret.msg = "无效的优惠记录"
            render(ret as JSON)
            return
        }


        if(tmpdr.reg_agentid != session.agentid){
            ret.status = 2
            ret.msg = "无权限"
            render(ret as JSON)
            return
        }

        if(! (tmpdr.reg_status in [Enum_DiscountRegStatus.RS_no_wait.code, Enum_DiscountRegStatus.RS_yes_wait.code])){
            ret.status = 3
            ret.msg = "此记录已经处理"
            render(ret as JSON)
            return
        }

        def tmpcst = Cst_basic.findByCst_mobile(tmpdr.reg_mobile)


        //如果为潜在客户，则加为我的客户
        if(tmpdr.reg_cstid == 0 && !tmpcst){
            def cst = new Cst_basic()

            cst.with {
                cst_name = tmpdr.reg_name
                cst_mobile = tmpdr.reg_mobile
                cst_agtid = session.agentid
                cst_time = new Date()
            }
            tmpdr.reg_cstid = cst.save()?.id

            new Cst_wishcar().with {
                id = tmpdr.reg_cstid
                save()
            }

            new Cst_wishroom().with {
                id = tmpdr.reg_cstid
                save()
            }

            //更新周计划新增客户数
            def now = new Date()
            Date week = null
            use(TimeCategory) {
                week = now - (now[Calendar.DAY_OF_WEEK] - 1).days
            }

            week.clearTime()

            def plan_week = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, week, Enum_Plantype.PT_week.code)
            plan_week.with {
                log_cst++
                save()
            }

            //更新月计划新增客户数
            now.date = 1
            now.clearTime()
            def plan_month = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(session.agentid, now, Enum_Plantype.PT_month.code)
            plan_month.with {
                log_cst++
                save(flush: true)
            }

            //把discount_reg 表中的reg_mobile 一样的客户修改为我的客户
            Discount_reg.executeUpdate("update Discount_reg set reg_agentid = ${session.agentid} where reg_mobile = '${tmpdr.reg_mobile}'")
            Discount_reg.executeUpdate("update Discount_reg set reg_cstid = ${cst.id} where reg_mobile = '${tmpdr.reg_mobile}'")
        }


        if(tmpdr.reg_status == Enum_DiscountRegStatus.RS_no_wait.code){
            tmpdr.reg_status = Enum_DiscountRegStatus.RS_no_addin.code
        }else{
            tmpdr.reg_status = Enum_DiscountRegStatus.RS_yes_link.code
        }

        tmpdr.save(flush: true)

        render(ret as JSON)

    }


    //打卡界面
    def mysign(){
        def ret = [status: 0, msg:'OK']

        def tmpagent = Agent.read(session.agentid)
        if(!tmpagent){
            ret.status = 2
            ret.msg = "无效的用户"
            render (ret as JSON)
            return
        }

        /*  查看今天是否已做签到  */
        def today = new Date()
        today.clearTime()

        def tomorrow = null
        def startdate = null

        use(TimeCategory) {
            tomorrow = (today + 1.days)
            startdate = (today - 6.days)
        }

        def sign_log = Agent_signlog.executeQuery("select sign_time from Agent_signlog where sign_agentid = ${tmpagent.id} and sign_time >= '${startdate.format('yyyy-MM-dd')}' and sign_time < '${tomorrow.format('yyyy-MM-dd')}' order by sign_time").collect {
            it.format("MM.dd")
        }

        def sign_ret = []

        int i = 7
        Date tmpdate = startdate
        String date_key = ""
        int value = 0
        while (i > 0) {
            date_key = tmpdate.format("MM.dd")
            if (date_key in sign_log) {
                value = 1
            }else {
                value = 0
            }

            sign_ret.add([sign_date:date_key, value:value])
            tmpdate = tmpdate.next()
            i--;
        }

        ret.signlog = sign_ret
        ret.signdays = tmpagent.agent_signdays
        ret.signcoins = tmpagent.agent_coin

        render(ret as JSON)

    }

    def dosign(){
        def ret = [status: 0, msg:'签到成功']

        def tmpagent = Agent.read(session.agentid)
        if(!tmpagent){
            ret.status = 1
            ret.msg = "无效的用户"
            render (ret as JSON)
            return
        }

        /*  查看今天是否已做签到  */
        def today = new Date()
        def tomorrow = today.next()

        //今天是否已经签到
        def count = Agent_signlog.executeQuery("select count(id) from Agent_signlog where sign_agentid = ${tmpagent.id} and sign_time >= '${today.format('yyyy-MM-dd')}' and sign_time < '${tomorrow.format('yyyy-MM-dd')}'", [max:1])[0]

        if(count > 0) { //如果今天已经签到
            ret.status = 2
            ret.msg = "今天已经签到"
            render(ret as JSON)
            return
        }

        def tmpsign = new Agent_signlog()
        tmpsign.with {
            sign_agentid = session.agentid
            sign_coin = 1
            save()
        }

        tmpagent.with {
            agent_signdays++
            agent_coin = agent_coin + tmpsign.sign_coin
            save(flush: true)
        }

        ret.signcoin = tmpsign.sign_coin

        render(ret as JSON)

    }

    def signlist(){
        def ret = [status: 0, msg:'OK']

        def tsql = "select count(*) "
        def vsql = """select s.sign_time, s.sign_coin"""

        def sql = """ from Agent_signlog s where s.sign_agentid = ${session.agentid}"""

        def osql = """ order by s.id desc """

        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        ret.total = Agent_signlog.executeQuery(tsql + sql)[0].toLong()

        def pagenum = 0
        if(reqnum){
            pagenum =  ret.total.mypage(reqnum)
        }else{
            pagenum =  ret.total > 0? 1 : 0
        }

        ret.pagenum =  pagenum

        ret.items = Agent_signlog.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    sign_time:it[0], sign_coin:it[1]
            ]
        }

        render(ret as JSON)

    }

}
