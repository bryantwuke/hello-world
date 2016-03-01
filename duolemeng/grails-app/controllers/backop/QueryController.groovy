package backop
import basic.BaseController
import grails.converters.JSON
import groovy.time.TimeCategory
import roomsale.*
import service.Enum_Smstype

class QueryController extends BaseController{

    def index() {
        render ("完善中...")
    }


    // 全国城市列表
    def citylist(){
        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        def tsql = "select count(distinct city) "
        def vsql = """select distinct city"""

        def sql = """ from City_area where 1 = 1"""
        def osql = """ order by city """


        def total = City_area.executeQuery(tsql + sql)[0].toLong()


        def items = City_area.executeQuery(vsql + sql + osql, mypage).collect {
            [city:it]
        }

        def pagenum
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([total:total, pagenum: pagenum, items: items] as JSON)

    }

    //城市区列表
    def arealist(){
        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        def tsql = "select count(*) "
        def vsql = """select area """

        def sql = """ from City_area where city = '${params.city}'"""
        def osql = """ order by area """


        def total = City_area.executeQuery(tsql + sql)[0].toLong()
        def items = City_area.executeQuery(vsql + sql + osql, mypage).collect {
            [area:it]
        }

        def pagenum
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([total:total, pagenum: pagenum, items: items] as JSON)

    }





    //车行列表
    def ds_carname_list(){
        def itemlist = Car_bank.executeQuery("select cb.id, concat(cb.cb_name, ' —— ', cb.cb_city) as cbname from Car_bank cb").collect {
            [id: it[0], name:it[1]]
        }

        render([identifier: "id", label: "name", items: itemlist] as JSON)
    }




    /*  下面代码仅用于测试 */

    /***** 用户信息    ****/
    def agentlist(){
        render(template: "/query/agent_list",model:[agtins:[:]])
    }

    def ds_agent_list(){
        def status = params.int('status', -1)

        def tsql = "select count(*) "
        def vsql = """select a.id, a.agent_mobile, a.agent_name, a.agent_idcard, a.agent_address, a.agent_usecity, a.agent_status, a.agent_iconid, a.agent_idcardid"""

        def sql = """ from Agent a where 1 = 1 """

        if(status != -1){
            if(status == -2){
                sql += """ and a.agent_status in (${Enum_Agentstatus.AS_idcard_ok.code}, ${Enum_Agentstatus.AS_team_member.code}, ${Enum_Agentstatus.AS_team_leader.code})"""
            }else{
                sql += """ and a.agent_status = ${status}"""
            }

        }

        if(params.kword){
            sql += """ and (a.agent_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%')"""
        }

        def osql = sqlorder("a", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Agent.executeQuery(tsql + sql)[0].toLong()

        def items = Agent.executeQuery(vsql + sql + osql, mypage).collect {
            [
                id:it[0], agent_mobile: it[1], agent_name: it[2], agent_idcard:it[3], agent_address:it[4], agent_usecity: it[5], agent_status:it[6], agent_iconid:it[7], agent_idcardid:it[8]
            ]
        }
        render([numRows: total, items: items] as JSON)
    }


    def agent_add() {
        render(template: "/query/agent_add", model: [agtins:[:]])
    }

    def agent_addlog() {
        def tmpagent = Agent.findByAgent_mobile(params.agent_mobile)
        if(tmpagent){
            render(status:501)
            return
        }

        tmpagent = new Agent()
        def myrand = new Random()

        bindData(tmpagent, params, [exclude:['agent_passwd','agent_salt','agent_iconid','agent_idcardid','agent_cashpwd','agent_status','agent_rank','agent_cashpwd']])

        tmpagent.with {
            agent_salt = (0..15).collect { (('a'..'z') + ('A'..'Z') + (0..9))[myrand.nextInt(62)] }.join()
            agent_passwd = (agent_salt + params.agent_passwd).encodeAsSHA1()
            agent_cashpwd = (agent_salt + params.agent_cashpwd).encodeAsSHA1()

            agent_time = new Date()
        }

        tmpagent.save(flush: true)

        render(status: 204)

    }

    def agent_viewlog() {
        def tmpagent = Agent.read(params.long("agent_id", 0))
        render(template: "/query/agent_add", model: [agtins:tmpagent])
    }

    def agent_editlog() {
        def tmpagent = Agent.findById(params.long('id',0L))
        if(!tmpagent){
            render(status: 501)
            return
        }

        def tmpagent2 = Agent.findByAgent_mobile(params.agent_mobile)
        if(tmpagent2 && tmpagent.id != tmpagent2.id){
            render(status:504)
            return
        }

        bindData(tmpagent, params, [exclude:['agent_passwd', 'agent_cashpwd']])

        if(params.agent_passwd){/* 更改密码 */
            tmpagent.agent_passwd = (tmpagent.agent_salt + params.agent_passwd).encodeAsSHA1()
        }

        if(params.agent_cashpwd){/* 更改提现密码 */
            tmpagent.agent_cashpwd = (tmpagent.agent_salt + params.agent_cashpwd).encodeAsSHA1()
        }

        tmpagent.save(flush: true)

        render(status: 204)

    }

    def agent_dellog() {
        Agent.executeUpdate("delete  from Agent where id=? ",[params.long('id', 0)])
        render(status: 204)
    }

    def agent_setlog(){
        def tmpagt = Agent.get(params.long('id', 0))
        if(!tmpagt){
            render(status: 501)
            return
        }

        def setval = params.int('setvalue', Enum_Agentstatus.AS_idcard_fail.code)

        tmpagt.agent_status = setval

        if(setval == Enum_Agentstatus.AS_idcard_ok.code){  //1.如果为审核通过,并且注册时填写了邀请码 2 如果从停用变成启用

            if(tmpagt.agent_teamid != 0){
                if(Agent_team.findByTeam_no(tmpagt.agent_teamid)?.team_agtid == tmpagt.id){
                    tmpagt.agent_status = Enum_Agentstatus.AS_team_leader.code
                }else{
                    tmpagt.agent_status = Enum_Agentstatus.AS_team_member.code
                }
            }

            //发个短信给用户告诉已经实名认证通过
            sms_sendcode( Enum_Smstype.ST_realname.code, [], tmpagt.agent_mobile)

        }

        tmpagt.save(flush: true)

        render(status: 204)
    }

    /* 经纪人列表结束*/


    /*  车行列表  */
    def cblist(){
        render(template: "/query/cb_list")
    }


    def ds_cb_list(){
        def city = params.city
        def area = params.area
        def name = params.name

        def tsql = "select count(*) "
        def vsql = """select c.id, c.cb_name, c.cb_city, c.cb_area, c.cb_address, c.cb_phone, c.cb_avgcash,
                    c.cb_focus, c.cb_view, c.cb_submit, c.file_num"""

        def sql = """ from Car_bank c where 1=1"""

        if(city){
            sql += """ and c.cb_city = '${city}'"""
        }

        if(area){
            sql += """ and c.cb_area = '${area}'"""
        }

        if(name){
            sql += """ and c.cb_name = '${name}'"""
        }

        def osql = sqlorder("c", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Car_bank.executeQuery(tsql + sql)[0].toLong()

        def items = Car_bank.executeQuery(vsql + sql + osql, mypage).collect {
            [
                   id:it[0], cb_name:it[1], cb_city:it[2], cb_area:it[3], cb_address:it[4],
                   cb_phone:it[5],  cb_avgcash:it[6], cb_focus:it[7], cb_view:it[8], cb_submit:it[9],
                   file_num:it[10]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }


    def cb_add() {
        def typelist = Tag.executeQuery("select tag_name from Tag where tag_isbasic = 0 and tag_type = ${Enum_Tagtype.TT_car_type.code} and tag_name not in('全部', '不限')")

        def taglist = Tag.executeQuery("select tag_name from Tag where tag_isbasic = 0 and tag_type = ${Enum_Tagtype.TT_car_tag.code} and tag_name not in('全部', '不限')")


        def tmpins = [
                typelist:typelist,
                taglist:taglist,
                cb_avgcash:0,
                type_tag:[],
                cb_tag: [],
                cb_level:[]
        ]

        render(template: "/query/cb_add", model: [cbins:tmpins])
    }

    def cb_basic_addlog(){
        def ret = [status: 0, msg:'OK']

        def tmpcb = new Car_bank()
        bindData(tmpcb,params)

        tmpcb.save(flush: true)

        ret.retid = tmpcb.id

        render(ret as JSON)
    }

    def cb_basic_editlog(){
        def ret = [status: 0, msg:'OK']

        def tmpcb = Car_bank.get(params.long('id',0L))
        if(!tmpcb){
            ret.status = 1
            ret.msg = "无效的车行"
            render(ret as JSON)
            return
        }

        bindData(tmpcb,params)

        tmpcb.save(flush: true)

        render(ret as JSON)
    }

    def cb_other_editlog(){
        def ret = [status: 0, msg:'OK']

        def tmpcb = Car_bank.get(params.long('cb_id',0L))
        if(!tmpcb){
            ret.status = 1
            ret.msg = "无效的车行"
            render(ret as JSON)
            return
        }

        bindData(tmpcb,params)

        tmpcb.save(flush: true)

        render(ret as JSON)
    }

    def cb_type_editlog(){
        def ret = [status: 0, msg:'OK']

        def tmpcb = Car_bank.get(params.long('cb_id',0L))
        if(!tmpcb){
            ret.status = 1
            ret.msg = "无效的车行"
            render(ret as JSON)
            return
        }

        long brandid = 0
        long saleptid = 0
        long salelvid = 0

        if(params.brand_content != ''){    //车品牌
            String[] brands = params.brand_content.split('_')
            def brandstr = brands.sort().join('_')
            def tmptag = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_car_type.code, brandstr)
            tmptag.tag_isbasic = brands.length > 1 ? 1 : 0
            brandid = tmptag.save()?.id
        }

        if(params.salepoint_content != ''){ //车行卖点
            String[] points = params.salepoint_content.split('_')
            def pointstr= points.sort().join('_')
            def tmptag = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_car_tag.code, pointstr)
            tmptag.tag_isbasic = points.length > 1? 1 : 0
            saleptid = tmptag.save()?.id
        }

        if(params.salelevel_content != ''){ //车行主营车级别
            String[] levels = params.salelevel_content.split('_')
            def levelstr= levels.sort().join('_')
            def tmptag = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_carlevel_tag.code, levelstr)
            tmptag.tag_isbasic = levels.length > 1? 1 : 0
            salelvid = tmptag.save()?.id
        }

        tmpcb.cb_brand_tagid = brandid
        tmpcb.cb_tag_tagid =  saleptid
        tmpcb.cb_level_tagid = salelvid

        tmpcb.save(flush: true)

        render(ret as JSON)
    }

    /*
    def cb_addlog() {

        long brandid = 0
        long saleptid = 0
        long salelvid = 0

        if(params.brand_content && params.brand_content != ''){    //车品牌
            String[] brands = params.brand_content.split('_')
            def brandstr = brands.sort().join('_')
            def tmptag = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_car_type.code, brandstr)
            tmptag.tag_isbasic = brands.length > 1 ? 1 : 0
            brandid = tmptag.save()?.id
        }

        if(params.salepoint_content && params.salepoint_content != ''){ //车行卖点
            String[] points = params.salepoint_content.split('_')
            def pointstr= points.sort().join('_')
            def tmptag = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_car_tag.code, pointstr)
            tmptag.tag_isbasic = points.length > 1? 1 : 0
            saleptid = tmptag.save()?.id
        }

        if(params.salelevel_content && params.salelevel_content != ''){ //车行主营车级别
            String[] levels = params.salelevel_content.split('_')
            def levelstr= levels.sort().join('_')
            def tmptag = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_carlevel_tag.code, levelstr)
            tmptag.tag_isbasic = levels.length > 1? 1 : 0
            salelvid = tmptag.save()?.id
        }

        def tmpcb = new Car_bank()
        bindData(tmpcb,params,[exclude:['id']])
        tmpcb.cb_brand_tagid = brandid
        tmpcb.cb_tag_tagid =  saleptid
        tmpcb.cb_level_tagid = salelvid

        tmpcb.save(flush: true)



        render(status: 204)

    }
    */

    def cb_viewlog() {
        def tmpcb = Car_bank.read(params.long("cb_id", 0))
        if(!tmpcb) {
            render(template: "/query/cb_add", model: [cbins: [:]])
            return
        }

        def typetag = Tag.read(tmpcb.cb_brand_tagid).tag_name?.split('_')
        def cbtag = Tag.read(tmpcb.cb_tag_tagid).tag_name?.split('_')
        def cblevel = Tag.read(tmpcb.cb_level_tagid).tag_name?.split('_')

        def typelist = Tag.executeQuery("select tag_name from Tag where tag_isbasic = 0 and tag_type = ${Enum_Tagtype.TT_car_type.code} and tag_name not in('全部', '不限')")

        def taglist = Tag.executeQuery("select tag_name from Tag where tag_isbasic = 0 and tag_type = ${Enum_Tagtype.TT_car_tag.code} and tag_name not in('全部', '不限')")


        def tmpins = tmpcb.properties as HashMap
        tmpins.put('id',tmpcb.id)


        tmpins.put('taglist',taglist)
        tmpins.put('typelist',typelist)

        tmpins['type_tag'] = typetag?:[]
        tmpins['cb_tag'] = cbtag?:[]
        tmpins['cb_level'] = cblevel?:[]

        render(template: "/query/cb_add", model: [cbins: tmpins])
    }

    /*
    def cb_editlog() {

        def tmpcb = Car_bank.get(params.long('id',0L))
        if(!tmpcb){
            render(status: 501)
            return
        }

        long brandid = 0
        long saleptid = 0
        long salelvid = 0

        if(params.brand_content && params.brand_content != ''){
            String[] brands = params.brand_content.split('_')
            def brandstr = brands.sort().join('_')
            def tmptag = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_car_type.code, brandstr)
            tmptag.tag_isbasic = brands.length > 1 ? 1 : 0
            brandid = tmptag.save()?.id
        }

        if(params.salepoint_content && params.salepoint_content != ''){
            String[] points = params.salepoint_content.split('_')
            def pointstr= points.sort().join('_')
            def tmptag = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_car_tag.code, pointstr)
            tmptag.tag_isbasic = points.length > 1? 1 : 0
            saleptid = tmptag.save()?.id
        }

        if(params.salelevel_content && params.salelevel_content != ''){ //车行主营车级别
            String[] levels = params.salelevel_content.split('_')
            def levelstr= levels.sort().join('_')
            def tmptag = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_carlevel_tag.code, levelstr)
            tmptag.tag_isbasic = levels.length > 1? 1 : 0
            salelvid = tmptag.save()?.id
        }

        bindData(tmpcb,params)
        tmpcb.cb_brand_tagid = brandid
        tmpcb.cb_tag_tagid =  saleptid
        tmpcb.cb_level_tagid = salelvid

        tmpcb.save(flush: true)

        render(status: 204)

    }

*/
    def cb_dellog() {

        Car_bank.executeUpdate("delete  from Car_bank where id=? ",[params.long('id', 0)])
        render(status: 204)

    }

    def cbuser_viewlog(){
        render(template: "/query/cbuserlist", model: [cbins: [cbid: params.long("cb_id", 0)]])
    }

    /*  车行列表结束  */


    /* 楼盘报备列表*/
    def bdsublist(){
        render(template: "/query/bdsub_list")
    }

    def ds_bdsub_list(){
        def status = params.int('status', -10)

        def tsql = "select count(*) "
        def vsql = """select cr.id, cr.reg_status, c.cst_name, c.cst_mobile, b.bd_name, a.agent_name, a.agent_mobile"""

        def sql = """ from Cst_regbd cr, Cst_basic c, Building b, Agent a where cr.reg_cstid = c.id and cr.reg_bdid = b.id and c.cst_agtid = a.id"""

        if(status != -10){
            if(status == -2){
                sql += """ and cr.reg_status <> ${Enum_Regstatus.RS_wishyes.code}"""
            }else{
                sql += """ and cr.reg_status = ${status}"""
            }

        }

        if(session.userorgid > 0){
            sql += """ and b.id = ${session.userorgid}"""
        }

        if(params.kword){
            sql += """ and (b.bd_name like '%${params.kword}%' or a.agent_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%')"""
        }

        def osql = sqlorder("cr", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Cst_regbd.executeQuery(tsql + sql)[0].toLong()

        def items = Cst_regbd.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id: it[0], reg_status:it[1], cst_name:it[2], cst_mobile:it[3], bd_name: it[4], agent_name:it[5], agent_mobile: it[6]
            ]
        }
        render([numRows: total, items: items] as JSON)
    }

    //楼盘报备状态设置
    def bdsub_setlog(){
        def tmpcr = Cst_regbd.get(params.long('id', 0))
        if(!tmpcr){
            render(status: 504)
            return
        }

        Integer status = params.int('setvalue', Enum_Regstatus.RS_regno.code)

        def tmpcst = Cst_basic.get(tmpcr.reg_cstid)
        tmpcst.with {
            cst_regbd = cst_regbd > status ? cst_regbd : status
            save()
        }

        if(status in [Enum_Regstatus.RS_ok.code, Enum_Regstatus.RS_regyes.code]){//如果设置为成交，更新经纪人的周，月成交计划
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


            if(status == Enum_Regstatus.RS_regyes.code) {
                plan_week.log_roomregok++
                plan_month.log_roomregok++
            }else{
                new Cst_sale().with {
                    sale_refid = tmpcr.reg_bdid
                    sale_reftype = Enum_Reftype.RT_building.code
                    sale_cstid = tmpcr.reg_cstid
                    sale_agentid = Cst_basic.read(tmpcr.reg_cstid)?.cst_agtid
                    sale_time = new Date()

                    save()
                }

                plan_week.log_roomdeal++
                plan_month.log_roomdeal++
            }

            plan_week.save()
            plan_month.save()
        }



        tmpcr.with {
            reg_status = status
            save(flush: true)
        }

        render(status: 204)

    }

    /* 楼盘报备列表结束*/


    /* 车行报备列表*/
    def cbsublist(){
        render(template: "/query/cbsub_list")
    }

    def ds_cbsub_list(){
        def status = params.int('status', -10)

        def tsql = "select count(*) "
        def vsql = """select cr.id, cr.reg_status, c.cst_name, c.cst_mobile, cb.cb_name, a.agent_name, a.agent_mobile"""

        def sql = """ from Cst_regcar cr, Cst_basic c, Car_bank cb, Agent a where cr.reg_cstid = c.id and cr.reg_carbankid = cb.id and c.cst_agtid = a.id"""

        if(status != -10){
            if(status == -2){
                sql += """ and cr.reg_status <> ${Enum_Regstatus.RS_wishyes.code}"""
            }else{
                sql += """ and cr.reg_status = ${status}"""
            }
        }


        if(session.userorgid > 0){
            sql += """ and cb.id = ${session.userorgid}"""
        }

        if(params.kword){
            sql += """ and (cb.cb_name like '%${params.kword}%' or a.agent_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%')"""
        }

        def osql = sqlorder("cr", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Cst_regcar.executeQuery(tsql + sql)[0].toLong()

        def items = Cst_regcar.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id: it[0], reg_status:it[1], cst_name:it[2], cst_mobile:it[3], cb_name: it[4], agent_name:it[5], agent_mobile: it[6]
            ]
        }
        render([numRows: total, items: items] as JSON)
    }

    //车行报备状态设置
    def cbsub_setlog(){
        def tmpcr = Cst_regcar.get(params.long('id', 0))
        if(!tmpcr){
            render(status: 504)
            return
        }

        Integer status = params.int('setvalue', Enum_Regstatus.RS_regno.code)

        def tmpcst = Cst_basic.get(tmpcr.reg_cstid)
        tmpcst.with {
            cst_regcar = cst_regcar > status ? cst_regcar : status
            save()
        }

        if(status in [Enum_Regstatus.RS_ok.code, Enum_Regstatus.RS_regyes.code]){//如果设置为成交，更新经纪人的周，月成交计划
            def now = new Date()
            Date week = null
            use(TimeCategory) {
                week = now - (now[Calendar.DAY_OF_WEEK] - 1).days
            }

            week.clearTime()

            //周计划
            def plan_week = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(tmpcst.cst_agtid, week, Enum_Plantype.PT_week.code)

            //月计划
            def tmptime = now
            tmptime.date = 1
            tmptime.clearTime()
            def plan_month = Agent_plan.findOrCreateByPlan_agentidAndPlan_dateAndPlan_type(tmpcst.cst_agtid, tmptime, Enum_Plantype.PT_month.code)

            if(status == Enum_Regstatus.RS_regyes.code) {
                plan_week.log_carregok++
                plan_month.log_carregok++
            }else{
                plan_week.log_cardeal++
                plan_month.log_cardeal++

                new Cst_sale().with {
                    sale_refid = tmpcr.reg_carbankid
                    sale_reftype = Enum_Reftype.RT_car.code
                    sale_cstid = tmpcr.reg_cstid
                    sale_agentid = Cst_basic.read(tmpcr.reg_cstid)?.cst_agtid
                    sale_time = new Date()

                    save()
                }
            }

            plan_week.save()
            plan_month.save()
        }

        tmpcr.with {
            reg_status = status
            save(flush: true)
        }

        render(status: 204)

    }

    /* 车行报备列表结束*/



    /*  经纪人佣金发放 */
    def agtcommlist(){
        render(template: "/query/agtcomm_list")
    }

    def ds_agtcomm_list(){
        def tsql = "select count(*) "
        def vsql = """select a.id, a.agent_mobile, a.agent_name, a.agent_idcard, a.agent_iconid"""

        def sql = """ from Agent a where a.agent_status in(${Enum_Agentstatus.AS_idcard_ok.code}, ${Enum_Agentstatus.AS_team_member.code}, ${Enum_Agentstatus.AS_team_leader.code})  """

        if(params.kword){
            sql += """ and (a.agent_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%')"""
        }

        def osql = sqlorder("a", "agent_name", "asc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Agent.executeQuery(tsql + sql)[0].toLong()

        def items = Agent.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], agent_mobile: it[1], agent_name: it[2], agent_idcard:it[3], agent_iconid:it[4]
            ]
        }
        render([numRows: total, items: items] as JSON)
    }


    def agtcomm_add(){
        def tmpagt = Agent.read(params.long("agent_id", 0))
        if(!tmpagt) {
            render(template: "/query/agtcomm_add", model: [agtins: [:]])
            return
        }

        def tmpins = [
                agent_id: tmpagt.id
        ]

        render(template: "/query/agtcomm_add", model: [agtins: tmpins])
    }

    def agtcomm_addlog(){
        def tmpagt = Agent.read(params.long("id", 0))
        if(!tmpagt) {
            render(status: 501)
            return
        }

        def tmpac = Agent_account.findOrCreateById(tmpagt.id)
        tmpac.id = tmpagt.id
        int type = params.int('cap_type', Enum_Captype.CT_none.code)
        def pay = false  //类型是否为发放
        def capnum = params.long('cap_num', 0)


        switch (type){
            //可用金额
            case Enum_Captype.CT_inuse_adduser.code:

            case Enum_Captype.CT_inuse_roomok.code:

            case Enum_Captype.CT_inuse_roomdeal.code:

            case  Enum_Captype.CT_inuse_carok.code:

            case Enum_Captype.CT_inuse_cardeal.code:
                tmpac.at_usable = tmpac.at_usable + capnum
                pay = true
                break

            //冻结金额
            case Enum_Captype.CT_infixed_adduser.code:

            case Enum_Captype.CT_infixed_roomok.code:

            case Enum_Captype.CT_infixed_roomdeal.code:

            case  Enum_Captype.CT_infixed_carok.code:

            case Enum_Captype.CT_infixed_cardeal.code:
                tmpac.at_fixed = tmpac.at_fixed + capnum
                pay = true
                break

            case Enum_Captype.CT_none.code:
                pay = true
                break;
        }

        if(!pay){
            render(status: 504)
            return
        }

        def tmpcap = new Agent_caplog()
        tmpcap.with {
            cap_time = new Date()
            cap_num = capnum
            cap_left = tmpac.at_usable + tmpac.at_fixed
            cap_type = type
            cap_agentid = tmpagt.id

            save()
        }

        tmpac.save(flush: true)

        render(status: 204)
    }

    /* 经纪人佣金发放结束 */


    /*  经纪人佣金解冻 */
    def agtfixedlist(){
        render(template: "/query/agtfixed_list")
    }

    def ds_agtfixed_list(){
        def tsql = "select count(*) "
        def vsql = """select a.id, a.agent_mobile, a.agent_name,a.agent_iconid, ac.id, ac.cap_time, ac.cap_num, ac.cap_type"""

        def sql = """ from Agent a, Agent_caplog ac  where  ac.cap_agentid = a.id and ac.cap_type in (${Enum_Captype.CT_infixed_adduser.code}, ${Enum_Captype.CT_infixed_roomok.code}, ${Enum_Captype.CT_infixed_roomdeal.code}, ${Enum_Captype.CT_infixed_carok.code}, ${Enum_Captype.CT_infixed_cardeal.code})"""

        if(params.kword){
            sql += """ and (a.agent_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%')"""
        }

        def osql = sqlorder("a", "agent_name", "asc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Agent.executeQuery(tsql + sql)[0].toLong()

        def items = Agent.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], agent_mobile: it[1], agent_name: it[2], agent_iconid:it[3], ac_id:it[4], cap_time:it[5], cap_num:it[6], cap_type:Enum_Captype.byCode(it[7]).name
            ]
        }
        render([numRows: total, items: items] as JSON)
    }

    def agtfiexed_setlog(){
        def tmpcap = Agent_caplog.get(params.long('id', 0))
        if(!tmpcap){
            render(status: 501)
            return
        }

        def typelist = [Enum_Captype.CT_infixed_adduser.code, Enum_Captype.CT_infixed_roomok.code, Enum_Captype.CT_infixed_roomdeal.code, Enum_Captype.CT_infixed_carok.code, Enum_Captype.CT_infixed_cardeal.code]
        if(!(tmpcap.cap_type in typelist)){
            render(status: 503)
            return
        }

        def tmpac = Agent_account.findOrCreateById(tmpcap.cap_agentid)
        tmpac.with {
            at_fixed = at_fixed - tmpcap.cap_num
            at_usable = at_usable + tmpcap.cap_num
            save()
        }

        tmpcap.with {
            cap_left = tmpac.at_fixed + tmpac.at_usable
            cap_time = new Date()
            cap_type = cap_type + 1

            save(flush: true)
        }


        render(status: 204)
    }

    /*  经纪人佣金解冻结束*/


    /*  客户查询与统计  */
    def cstlist(){
        render(template: "/query/cst_list")
    }

    def ds_cst_list(){
        def tsql = "select count(*) "
        def vsql = """select c.id, c.cst_name, c.cst_mobile,c.cst_idcard, c.cst_phonenum, c.cst_smsnum, c.cst_time, c.cst_address, c.cst_regbd, c.cst_regcar, a.agent_name, a.agent_mobile"""

        def sql = """ from Agent a, Cst_basic c  where  c.cst_agtid = a.id"""
        if(params.kword){
            sql += """ and (a.agent_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%' or c.cst_mobile like '%${params.kword}%' or c.cst_name like '%${params.kword}%')"""
        }

        def osql = sqlorder("a", "agent_name", "asc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Cst_basic.executeQuery(tsql + sql)[0].toLong()

        def items = Cst_basic.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], cst_name:it[1], cst_mobile: it[2], cst_idcard:it[3], cst_phonenum:it[4], cst_smsnum: it[5],
                    cst_time:it[6], cst_address:it[7], cst_regbd:Enum_Regstatus.byCode(it[8])?.name, cst_regcar:Enum_Regstatus.byCode(it[9])?.name,
                    agent_name:it[10], agent_mobile: it[11]
            ]
        }
        render([numRows: total, items: items] as JSON)
    }
    /*  客户查询与统计结束  */



    /*  考试科目  */
    def subjectlist(){
        render(template: "/query/subject_list")
    }

    def ds_subject_list(){
        def tsql = "select count(*) "
        def vsql = """select s.id, s.subject_name """

        def sql = """ from Test_subject s """


        def osql = sqlorder("s", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Test_subject.executeQuery(tsql + sql)[0].toLong()

        def items = Test_subject.executeQuery(vsql + sql + osql, mypage).collect {
            [
                   id:it[0], subject_name: it[1]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }


    def subject_add() {
        render(template: "/query/subject_add", model: [subins:[:]])
    }

    def subject_addlog() {
        Test_subject.findOrCreateBySubject_name(params.subject_name).save(flush: true)
        render(status: 204)
    }

    def subject_viewlog() {
        def tmpsub = Test_subject.read(params.long("subject_id", 0))

        def tmpins = [
                id:tmpsub.id,
                subject_name: tmpsub.subject_name
        ]
        render(template: "/query/subject_add", model: [subins:tmpins])
    }

    def subject_editlog() {
        def tmpsub = Test_subject.get(params.long('id',0L))
        if(!tmpsub){
            render(status: 501)
            return
        }

        bindData(tmpsub,params)
        tmpsub.save(flush: true)

        render(status: 204)
    }

    def subject_dellog() {
        Test_subject.executeUpdate("delete  from Test_subject where id=? ",[params.long('id', 0)])
        render(status: 204)
    }



    /* 考试科目 结束*/



    /*  考试题目  */
    def testlist(){
        render(template:  "/query/test_list")
    }

    def ds_test_list(){
        def tsql = "select count(*) "
        def vsql = """select t.id, t.test_type, t.test_title, s.subject_name """

        def sql = """ from Test_bank t, Test_subject s where t.test_subjectid = s.id """


        def osql = sqlorder("s", "subject_name", "asc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Test_bank.executeQuery(tsql + sql)[0].toLong()

        def items = Test_bank.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], test_type: Enum_Testtype.byCode(it[1])?.name, test_title:it[2], test_subjectid: it[3]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }


    def test_add() {
        render(template: "/query/test_add", model: [testins:[:]])
    }

    def test_addlog() {
        def tmpt = new Test_bank()

        bindData(tmpt, params, [exclude:['test_answer']])
        tmpt.test_answer = params.test_answer.toCharArray().toList().sort().join()

        tmpt.save(flush: true)
        render(status: 204)
    }

    def test_viewlog() {
        def tmpt = Test_bank.read(params.long("test_id", 0))

        render(template: "/query/test_add", model: [testins:tmpt])
    }

    def test_editlog() {
        def tmpt = Test_bank.get(params.long('id',0L))
        if(!tmpt){
            render(status: 501)
            return
        }

        bindData(tmpt, params, [exclude:['test_answer']])
        tmpt.test_answer = params.test_answer.toLowerCase().toCharArray().toList().sort().join()

        tmpt.save(flush: true)

        render(status: 204)
    }

    def test_dellog() {
        Test_bank.executeUpdate("delete  from Test_bank where id=? ",[params.long('id', 0)])
        render(status: 204)
    }



    /* 考试题目 结束*/


    /*  收款记录  */

    def cstpaylist(){
        render(template: "/query/cstpay_list")
    }

    def ds_cstpay_list(){
        def tsql = "select count(*) "
        def vsql = """select cp.id, cp.pay_money, cp.pay_type, cp.pay_time, cp.pay_sn, cp.pay_note, cp.pay_fileid, a.agent_name, c.cst_name"""

        def sql = """ from Cst_pay cp, Agent a, Cst_basic c where cp.pay_cstid = c.id and cp.pay_agentid = a.id """

        if(params.kword){
            sql +=""" and (cp.pay_sn like '%${params.kword}%' or a.agent_name like '%${params.kword}%' or cst_name like '%${params.kword}%')"""
        }


        def osql = sqlorder("cp", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Cst_pay.executeQuery(tsql + sql)[0].toLong()

        def items = Cst_pay.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], pay_money:it[1], pay_type:Enum_Reftype.byCode(it[2])?.name, pay_time:it[3], pay_sn:it[4], pay_note:it[5],
                    pay_fileid:it[6], agent_name:it[7], cst_name:it[8]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }

    /*   收款记录 结束 */

    /*  发放记录  */

    def issuelist(){
        render(template: "/query/issue_list")
    }

    def ds_issue_list(){
        def tsql = "select count(*) "
        def vsql = """select ac.id, ac.cap_time, ac.cap_num, ac.cap_left, ac.cap_type, ac.cap_regid, a.agent_name, a.agent_mobile"""

        def sql = """ from Agent_caplog ac, Agent a where ac.cap_type <> ${Enum_Captype.CT_out_cash.code} and ac.cap_agentid = a.id"""

        if(params.kword){
            sql +=""" and (a.agent_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%')"""
        }


        def osql = sqlorder("ac", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Cst_pay.executeQuery(tsql + sql)[0].toLong()

        def items = Cst_pay.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], cap_time:it[1], cap_num:it[2], cap_left:it[3], cap_type:Enum_Captype.byCode(it[4])?.name, cap_regid:it[5],
                    agent_name:it[6], agent_mobile:it[7]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }

    /*   发放记录 结束 */


    /*  提现记录  */

    def cashlist(){
        render(template: "/query/cash_list")
    }

    def ds_cash_list(){
        def tsql = "select count(*) "
        def vsql = """select ac.id, ac.cap_time, ac.cap_num, ac.cap_left, a.agent_name, a.agent_mobile"""

        def sql = """ from Agent_caplog ac, Agent a where ac.cap_type = ${Enum_Captype.CT_out_cash.code} and ac.cap_agentid = a.id"""

        if(params.kword){
            sql +=""" and (a.agent_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%')"""
        }


        def osql = sqlorder("ac", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Cst_pay.executeQuery(tsql + sql)[0].toLong()

        def items = Cst_pay.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], cap_time:it[1], cap_num:it[2], cap_left:it[3],
                    agent_name:it[4], agent_mobile:it[5]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }

    /*   发放记录 结束 */
}
