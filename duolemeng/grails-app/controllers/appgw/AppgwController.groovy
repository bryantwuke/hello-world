package appgw
import basic.BaseController
import grails.converters.JSON
import groovy.time.TimeCategory
import roomsale.*
import service.Back_str
import service.Banner
import service.Enum_Smstype
import service.Enum_backstr
import service.Sys_holiday

class AppgwController extends BaseController{

    def pingw(){
        //sms_sendcode( Enum_Smstype.ST_realname.code, [], '15599119568')
        render("OK now xx")
    }

    def ping() {
        if(!session.agentid) {
            render(1)
        }else {
            render(0)
        }
    }

    /* 项目列表 */
   def bdlist(){
       def city = params.city
       def bdname = params.name
       def bdtag = params.tag
       def bdarea = params.area
       def type = params.type

       int sorttyhpe = params.int('sorttype', 0) //排序

       Boolean favor = false    //是否已收藏

       def tsql = "select count(*) "
       def vsql = """select b.bd_name,b.bd_price,b.bd_avgcash,
                    t.tag_name,b.id,b.bd_focus,b.bd_view,b.bd_submit,b.bd_area,b.bd_phone,
                    ( select min(id) from Sys_file f where f.file_refid = b.id and f.file_refclass = ${Enum_Fileclass.FC_apartment.code}) as file_id"""

       def sql = """ from Building b,Tag t where b.bd_type_tagid = t.id """

       if(params.int('favor', 0) != 0 && session.agentid){  //显示我的收藏
           sql = """ from Building b,Tag t, Agent_favor af where b.bd_type_tagid = t.id
                   and af.favor_agentid = ${session.agentid} and af.favor_refid = b.id
                   and af.favor_reftype = ${Enum_Reftype.RT_building.code}"""

           favor = true
       }

       if(city && city != "全部" && city != '不限'){
           sql += """ and b.bd_city = '${city}'"""
       }

       if(bdname){
           sql += """ and b.bd_name like '%${bdname}%'"""
       }

       if(type && type != '全部' && type != '不限'){
           sql += """ and t.tag_name like '%${type}%' """
       }

       if(bdtag && bdtag != '全部' && bdtag != '不限'){
           sql += """ and b.bd_tag_tagid in (select id from Tag where tag_name like '%${bdtag}%') """
       }

       if(bdarea && bdarea != "全部" && bdarea != '不限'){
           sql += """ and b.bd_area = '${bdarea}'"""
       }

       def osql = """ order by b.id desc """

       switch (sorttyhpe){
           case 1:
               osql = """ order by b.bd_price desc """
               break
           case 2:
               osql = """ order by b.bd_price asc"""
               break
           case 3:
               osql = """ order by b.bd_avgcash desc """
               break
           case 4:
               osql = """ order by b.bd_avgcash asc """
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

//       def mypage = [readOnly: true, max: 8]
//       mypage.put("offset", (params.int('page', 0) - 1).multiply(8))

       def total = Building.executeQuery(tsql + sql)[0].toLong()

       def items = Building.executeQuery(vsql + sql + osql, mypage).collect {
           def tmpfavor = favor

           def tmpstr = Discount.executeQuery("select concat(dc.id,'#',dc.dc_title) from Discount dc where dc.dc_reftype = ${Enum_Reftype.RT_building.code} and dc.dc_type = ${Enum_Discounttype.DT_discount_ok.code} and dc.dc_refid = ${it[4]} order by dc.id desc", [readOnly: true, max: 1])
           def tmpret = ""
           if (tmpstr.size()) {
               tmpret = tmpstr[0]
           }

           if((!favor && session.agentid) && Agent_favor.findByFavor_agentidAndFavor_refidAndFavor_reftype(session.agentid, it[4], Enum_Reftype.RT_building.code)){
               tmpfavor = true
           }

           [
                bd_name:it[0], bd_price:it[1], bd_avgcash:it[2],
                bd_tag:it[3], bd_id:it[4], bd_focus:it[5], bd_view:it[6], bd_submit:it[7], bd_area:it[8],
                bd_phone:it[9], file_id:it[10], dc_title:tmpret, bd_favor:tmpfavor
           ]
       }

       def pagenum = 0
       if(reqnum){
           pagenum = total.mypage(reqnum)
       }else{
           pagenum = total > 0? 1 : 0
       }

       render([total:total, pagenum: pagenum, items: items] as JSON)

   }





   // 楼盘详情
   def bdinfo(){
       def tmpbd = Building.read(params.long('id',0))
       if(!tmpbd){
           render ([:] as JSON)
           return
       }


       def typetag = Tag.read(tmpbd.bd_type_tagid)?.tag_name
       def bdtag = Tag.read(tmpbd.bd_tag_tagid)?.tag_name


       def typefiles = Sys_file.executeQuery("select id from Sys_file where file_refclass = ${Enum_Fileclass.FC_housetype.code} and file_refid = ${tmpbd.id}")


       def picfiles = Sys_file.executeQuery("select id from Sys_file where file_refclass = ${Enum_Fileclass.FC_apartment.code} and file_refid = ${tmpbd.id}")

       //浏览量+1

       Building.executeUpdate("update Building set bd_view = bd_view+1 where id = ${tmpbd.id}")

       render(
               [
                       bd_name:tmpbd.bd_name, bd_id: tmpbd.id, bd_area: tmpbd.bd_area, bd_address:tmpbd.bd_address, bd_developer:tmpbd.bd_developer,
                       type_tag_content:typetag, tag_content:bdtag, bd_price:tmpbd.bd_price, bd_avgcash:tmpbd.bd_avgcash, bd_greenrate:tmpbd.bd_greenrate, bd_volumnrate:tmpbd.bd_volumnrate,
                       bd_house:tmpbd.bd_house, bd_opentime:tmpbd.bd_opentime, bd_year:tmpbd.bd_year, bd_agenttel:tmpbd.bd_agenttel,
                       bd_structure:tmpbd.bd_structure, bd_featrue:tmpbd.bd_feature, bd_decoration:tmpbd.bd_decoration,
                       bd_propcost:tmpbd.bd_propcost, bd_typefiles:typefiles, bd_picfiles:picfiles
               ] as JSON
       )
   }

    //楼盘周边
    def bdaround(){
        def tmpba = Building_around.read(params.long('bd_id',0))

        if(!tmpba){
            render ([:] as JSON)
            return
        }

        def tmpmap = tmpba.properties as HashMap
        tmpmap.remove('class')
        render(
                [
                        bd_commercial:tmpba.bd_commercial,bd_transport:tmpba.bd_transport, bd_education:tmpba.bd_education,
                        bd_entertain:tmpba.bd_entertain
                ] as JSON
        )
    }

    // 车行列表
    def cblist(){
        def name = params.name
        def city = params.city
        def level = params.level
        def area = params.area
        def brand = params.brand
        def tag = params.tag

        def schbrand = false
        def schlevel = false
        def schtag = false


        if(brand && brand != '全部' &&  brand != '不限'){
            schbrand = true
        }

        if(tag && tag != '全部' &&  tag != '不限'){
            schtag = true
        }

        if(level && level != '全部' && level != '不限'){
            schlevel = true
        }

        Boolean favor = false    //是否已收藏

        int sorttyhpe = params.int('sorttype',0)

        def tsql = "select count(*) "
        def vsql = """select c.id, c.cb_shortname, c.cb_area, c.cb_address, c.cb_avgcash,
                    c.cb_focus, c.cb_view, c.cb_submit, c.cb_phone,
                    ( select min(id) from Sys_file f where f.file_refid = c.id and f.file_refclass = ${Enum_Fileclass.FC_car.code}) as file_id,
                    ( select t.tag_name from Tag t where t.id = c.cb_brand_tagid) as car_brand,
                    ( select t.tag_name from Tag t where t.id = c.cb_tag_tagid) as car_tag"""

        def sql = """ from Car_bank c where 1 = 1 """

        if(schbrand){
            sql += """ and c.cb_brand_tagid in (select t.id from Tag t where t.tag_name like '%${brand}%')"""
        }

        if(schtag){
            sql += """ and c.cb_tag_tagid in (select t.id from Tag t where t.tag_name like '%${tag}%')"""
        }

        if(schlevel){
            sql += """ and c.cb_level_tagid in (select t.id from Tag t where t.tag_name like '%${level}%')"""
        }

        if(params.int('favor',0) != 0 && session.agentid){
            sql = """ from Car_bank c, Agent_favor af where af.favor_agentid = ${session.agentid}
                   and af.favor_refid = c.id
                   and af.favor_reftype = ${Enum_Reftype.RT_car.code}"""

            if(schbrand){
                sql += """ and c.cb_brand_tagid in (select t.id from Tag t where t.tag_name like '%${brand}%')"""
            }

            if(schtag){
                sql += """ and c.cb_tag_tagid in (select t.id from Tag t where t.tag_name like '%${tag}%')"""
            }

            if(schlevel){
                sql += """ and c.cb_level_tagid in (select t.id from Tag t where t.tag_name like '%${level}%')"""
            }

            favor = true
        }

        if(city && city != '全部'){
            sql += """ and c.cb_city = '${city}'"""
        }

        if(name && name != '全部'){
            sql += """ and (c.cb_name like '%${name}%' or c.cb_shortname like '%${name}%')"""
        }

        if(area && area != '全部'){
            sql += """ and c.cb_area = '${area}'"""
        }

        def osql = """ order by c.id desc """

        switch (sorttyhpe){
            case 1:
                osql = """ order by c.cb_avgcash desc """
                break
            case 2:
                osql = """ order by c.cb_avgcash asc"""
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

//        def mypage = [readOnly: true, max: 8]
//        mypage.put("offset", (params.int('page', 0) - 1).multiply(8))

        def total = Car_bank.executeQuery(tsql + sql)[0].toLong()

        def items = Car_bank.executeQuery(vsql + sql + osql, mypage).collect {
            def tmpfavor = favor

            def tmpstr = Discount.executeQuery("select concat(dc.id,'#',dc.dc_title) from Discount dc where dc.dc_reftype = ${Enum_Reftype.RT_car.code} and dc.dc_type = ${Enum_Discounttype.DT_discount_ok.code} and dc.dc_refid = ${it[0]} order by dc.id desc", [readOnly: true, max: 1])
            def tmpret = ""
            if (tmpstr.size()) {
                tmpret = tmpstr[0]
            }

            if((!favor && session.agentid) && Agent_favor.findByFavor_agentidAndFavor_refidAndFavor_reftype(session.agentid, it[0], Enum_Reftype.RT_car.code)){
                tmpfavor = true
            }

            [
                    cb_id:it[0], cb_name:it[1], cb_area:it[2], cb_address:it[3], cb_avgcash:it[4],
                    cb_focus:it[5], cb_view:it[6], cb_submit:it[7],
                    cb_phone: it[8], file_id:it[9], cb_brand: it[10], cb_tag:it[11], dc_title: tmpret, cb_favor: tmpfavor
            ]
        }

        def pagenum = 0
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([total:total, pagenum: pagenum, items: items] as JSON)
    }


    // 车行详情
    def cbinfo(){
        def tmpcar = Car_bank.read(params.long('id',0))
        if(!tmpcar){
            render ([:] as JSON)
            return
        }


        def carbrand = Tag.read(tmpcar.cb_brand_tagid)?.tag_name

        def cb_pics = Sys_file.executeQuery("select id from Sys_file where file_refclass = ${Enum_Fileclass.FC_car.code} and file_refid = ${tmpcar.id}")

        //浏览量+1
        /*
        tmpcar.with {
            cb_view = cb_view + 1
            save(flush: true)
        }
        */
        Car_bank.executeUpdate("update Car_bank set cb_view = cb_view + 1 where id = ${tmpcar.id}")

        render(
                [
                       cb_id: tmpcar.id, car_name: tmpcar.cb_name, cb_phone: tmpcar.cb_phone, cb_avgcash: tmpcar.cb_avgcash,
                       cb_address: tmpcar.cb_address, cb_pics:cb_pics, cb_brief:tmpcar.cb_brief, cb_aftersale:tmpcar.cb_aftersale, cb_loan:tmpcar.cb_loan,
                       cb_brand: carbrand
                ] as JSON
        )

    }



    //开能的城市列表
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

        def tsql = "select count(distinct bd_city) "
        def vsql = """select distinct bd_city"""

        def sql = """ from Building where 1 = 1"""
        def osql = """ order by bd_city """


        def total = Building.executeQuery(tsql + sql)[0].toLong()

        def items = Building.executeQuery(vsql + sql + osql, mypage)
        if(params.int('page', 0) < 2) {
            items.add(0, '全部')
        }

        def pagenum = 0
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([total:total, pagenum: pagenum, items: items] as JSON)

    }

    //开通的城市区列表
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

        def tsql = "select count(distinct bd_area) "
        def vsql = """select distinct bd_area """

        def sql = """ from Building where bd_city = '${params.city}'"""
        def osql = """ order by bd_area """


        def total = Building.executeQuery(tsql + sql)[0].toLong()
        def items =  Building.executeQuery(vsql + sql + osql, mypage)
        if(params.int('page', 0) < 2 && params.city) {
            items.add(0, '全部')
        }

        def pagenum = 0
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([total:total, pagenum: pagenum, items: items] as JSON)

    }

    //开能的车行城市列表
    def cbcitylist(){
        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        def tsql = "select count(distinct cb_city) "
        def vsql = """select distinct cb_city"""

        def sql = """ from Car_bank where 1 = 1"""
        def osql = """ order by cb_city """


        def total = Building.executeQuery(tsql + sql)[0].toLong()

        def items = Building.executeQuery(vsql + sql + osql, mypage)
        if(params.int('page', 0) < 2) {
            items.add(0, '全部')
        }

        def pagenum = 0
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([total:total, pagenum: pagenum, items: items] as JSON)

    }

    //开通的车行城市区列表
    def cbarealist(){
        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        def tsql = "select count(distinct cb_area) "
        def vsql = """select distinct cb_area """

        def sql = """ from Car_bank where cb_city = '${params.city}'"""
        def osql = """ order by cb_area """


        def total = Building.executeQuery(tsql + sql)[0].toLong()
        def items =  Building.executeQuery(vsql + sql + osql, mypage)
        if(params.int('page', 0) < 2 && params.city) {
            items.add(0, '全部')
        }

        def pagenum = 0
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([total:total, pagenum: pagenum, items: items] as JSON)

    }

    // 非组合标签
    def taglist(){
        def tag_type = params.int('type', Enum_Tagtype.TT_bd_type.code)

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
        def vsql = """select tag_name """

        def sql = """ from Tag where tag_isbasic = 0 and tag_type = ${tag_type} and tag_name IS NOT NULL"""
        def osql = """ order by tag_name """

        def total = City_area.executeQuery(tsql + sql)[0].toLong()
        def items = City_area.executeQuery(vsql + sql + osql, mypage)
        if(params.int('page', 0) < 2 && tag_type in [Enum_Tagtype.TT_bd_type.code, Enum_Tagtype.TT_car_type.code]) {  //如果是楼盘业态或者是汽车品牌
            if(!items.contains('全部') && !items.contains('不限')){
                items.add(0, '全部')
            }

        }


        def pagenum = 0

        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([total:total, pagenum: pagenum, items: items] as JSON)

    }

    //楼盘优惠活动列表
    def dclist(){
        def refid = params.long('refid',-1)
        def reftype = params.int('reftype', Enum_Reftype.RT_building.code)
        def dctype = params.int('dctype', Enum_Discounttype.DT_discount_ok.code)

        def tsql = "select count(*) "
        def vsql = """select dc.dc_title, dc.dc_fromtime, dc.dc_endtime, dc.id"""

        def sql = """ from Discount dc where dc_reftype = ${reftype} and dc.dc_type = ${dctype}"""

        if(refid > -1){
            sql += """ and dc.dc_refid = ${refid}"""
        }

        def osql = """ order by dc.id desc """


        int reqnum = params.int('reqnum',-1)
        if(reqnum == -1){
            reqnum = 8
        }

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (params.int('page', 0) - 1).multiply(reqnum))

        if(reqnum == 0){
            mypage = []
        }

        def total = Discount.executeQuery(tsql + sql)[0].toLong()

        def items = Discount.executeQuery(vsql + sql + osql, mypage).collect {
            [
                   dc_title: it[0], dc_fromtime:it[1],
                   dc_endtime:it[2], dc_id:it[3]
            ]
        }

        def pagenum = 0
        if(reqnum){
            pagenum = total.mypage(reqnum)
        }else{
            pagenum = total > 0? 1 : 0
        }

        render([total:total, pagenum: pagenum, items: items] as JSON)

    }

    //优惠详情
    def discount(){
        def tmpdc = Discount.read(params.long('id',0))
        if(!tmpdc){
            render ([:] as JSON)
            return
        }

        render(
                [
                        dc_title:tmpdc.dc_title, dc_content:tmpdc.dc_content, dc_fromtime:tmpdc.dc_fromtime,
                        dc_endtime:tmpdc.dc_endtime
                ] as JSON
        )
    }

    //banner
    def banner(){
        def tsql = "select count(*) "
        def vsql = """select bn.banner_fileid, bn.banner_url """

        def sql = """ from Banner bn where bn.banner_valid = 0"""

        def osql = """ order by bn.id asc """


        def total = Banner.executeQuery(tsql + sql)[0].toLong()


        def items = Banner.executeQuery(vsql + sql + osql, []).collect {
            [
                file_id: it[0],banner_url:it[1]
            ]
        }

        render([total:total,items: items] as JSON)
    }



    /*  以下是用户交互接口  */


    def sms_send(){
        def ret = [status:0, msg:"OK"]

        if(params.int('type', 0) == Enum_Smstype.ST_findpwd.code){
            if(session.agentid){
                ret.status = 1
                ret.msg = "用户已登录"
                render (ret as JSON)
                return
            }
        }

        if(!params.mobile){
            ret.status = 2
            ret.msg = "参数错误"
            render (ret as JSON)
            return
        }

        def agent_mobile = params.mobile
        def isreg = params.int('isreg',0)

        if (!(agent_mobile ==~ ~FS_ismobile)) {
            ret.status = 3
            ret.msg = "无效的手机号码"
            render (ret as JSON)
            return
        }

        if(isreg){
            /*  查看用户是否已存在  */
            def tmpagt = Agent.findByAgent_mobile(agent_mobile)
            if(tmpagt) {
                ret.status = 4
                ret.msg = "手机号码已存在"
                render(ret as JSON)
                return
            }
        }

        Random myrand = new Random()
        String randcode = (0..5).collect { (0..9)[myrand.nextInt(10)] }.join()
        session.smscode = agent_mobile + randcode

        sms_sendcode(params.int('type',0), [randcode], agent_mobile)

        ret.msg = "短信已发送到:${agent_mobile}"

        render (ret as JSON)
    }

    //经纪人注册
    def sign(){
        def ret = [status:0, msg:"注册成功"]

        if(session.agentid){
            ret.status = 1
            ret.msg = "用户已登录"
            render (ret as JSON)
            return
        }

        if(!params.name || !params.mobile || !params.vcode || !params.pwd){
            ret.status = 2
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }


        def agt_mobile = params.mobile

        def tmpagt = Agent.findByAgent_mobile(agt_mobile)
        if(tmpagt) {
            ret.status = 3
            ret.msg = "手机号码已注册"
            render(ret as JSON)
            return
        }

        if ((agt_mobile + params.vcode) != session.smscode) {
            ret.status = 4
            ret.msg = "验证码错误"
            render (ret as JSON)
            return
        }

        tmpagt = new Agent()
        def myrand = new Random()

        tmpagt.with {
            agent_name = params.name
            agent_mobile = agt_mobile
            agent_salt = (0..15).collect { (('a'..'z') + (0..9) + ('A'..'Z'))[myrand.nextInt(62)] }.join()
            agent_passwd = (agent_salt + params.pwd).encodeAsSHA1()
            agent_teamid = params.long('tid', 0)
            agent_time = new Date()
            save(flush: true)
        }

        render (ret as JSON)
    }

    // 登录
    def auth(){
/*
        for (hkey in request.headerNames) {
            println("=header:${hkey}==${request.getHeader(hkey)}")
        }
        for (cookie in request.cookies) {
            println("in auth===${cookie.name}==${cookie.value}")
        }

        request.cookies.each { println "${it.name} == ${it.value}" }

*/

        def ret = [status:0, msg:"登陆成功"]

        if(session.agentid){
            ret.status = 1
            ret.msg = "用户已登录"
            render (ret as JSON)
            return
        }

        if ((!params.loginname)||(!params.loginpass)) {
            ret.status = 2
            ret.msg = "参数错误"
            render (ret as JSON)
            return
        }

        def tmpagent = Agent.findByAgent_mobile(params.loginname)
        if (!tmpagent) {
            ret.status = 3
            ret.msg = "用户名或密码错误"
            render (ret as JSON)
            return
        }


        if (tmpagent.agent_passwd != (tmpagent.agent_salt + params.loginpass).encodeAsSHA1()) {
            ret.status = 3
            ret.msg = "用户名或密码错误"
            render (ret as JSON)
            return
        }

        if(tmpagent.agent_status == Enum_Agentstatus.AS_stop.code){
            ret.status = 4
            ret.msg = "您已经被停用，请联系管理员"
            render(ret as JSON)
            return
        }

        def agent_token = null

        if(params.devid){
            def tmpat = new Agent_token()
            tmpat.with {
                token_devid = params.devid
                token_code = (token_devid + tmpagent.agent_mobile + new Date().getTime()).encodeAsMD5()
                token_agentid = tmpagent.id
                save(flush: true)
            }

            agent_token = tmpat.token_code
        }


        ret.agent = [
                agent_iconid: tmpagent.agent_iconid,
                agent_id: tmpagent.id,
                agent_name:tmpagent.agent_name,
                agent_mobile:tmpagent.agent_mobile,
                agent_status:tmpagent.agent_status,
                agent_setcspwd: tmpagent.agent_cashpwd? true : false,
                agent_token: agent_token,
                agent_address: tmpagent.agent_address,
                agent_usecity: tmpagent.agent_usecity,
                agent_idcard: tmpagent.agent_idcard? tmpagent.agent_idcard.encodeAsHex() : null,
                agent_idcardid: tmpagent.agent_idcardid
        ]


        session.agentname = tmpagent.agent_name
        session.agentid = tmpagent.id
        session.agentstatus = tmpagent.agent_status
        session.agentmobile = tmpagent.agent_mobile
        session.agentteamid = tmpagent.agent_teamid

        if (!session.isNew()) {
            response.addHeader("Set-Cookie", "${request.cookies[0].name}=${request.cookies[0].value}; Path=/; HttpOnly")
        }

/*
        if (request.cookies?.size()) {
            response.addHeader("Set-Cookie", "${request.cookies[0].name}=${request.cookies[0].value}; Path=/; HttpOnly")
        }
*/

        render(ret as JSON)

    }

    //token 隐匿登录
    def tokenlg(){
        def ret = [status: 0, msg: '登陆成功']

        if(session.agentid){
            ret.status = 1
            ret.msg = "用户已登录"
            render (ret as JSON)
            return
        }

        if(!params.devid || !params.token){
            ret.status = 2
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def tmptoken = Agent_token.findByToken_devidAndToken_code(params.devid, params.token)
        if(!tmptoken){
            ret.status = 3
            ret.msg = "无效的token"
            render(ret as JSON)
            return
        }

        def now = new Date()

        Date week = null

        use(TimeCategory) {
            week = now - 7.days
        }

        if(week.after(tmptoken.token_time)){
            ret.status = 4
            ret.msg = "token过期"
            render(ret as JSON)
            return
        }

        def tmpagent = Agent.read(tmptoken.token_agentid)

        ret.agent = [
                agent_iconid: tmpagent.agent_iconid,
                agent_id: tmpagent.id,
                agent_name:tmpagent.agent_name,
                agent_mobile:tmpagent.agent_mobile,
                agent_status:tmpagent.agent_status,
                agent_setcspwd: tmpagent.agent_cashpwd? true : false,
                agent_address: tmpagent.agent_address,
                agent_usecity: tmpagent.agent_usecity,
                agent_idcard: tmpagent.agent_idcard? tmpagent.agent_idcard.encodeAsHex() : null,
                agent_idcardid: tmpagent.agent_idcardid
        ]

        session.agentname = tmpagent.agent_name
        session.agentid = tmpagent.id
        session.agentstatus = tmpagent.agent_status
        session.agentmobile = tmpagent.agent_mobile
        session.agentteamid = tmpagent.agent_teamid


        render(ret as JSON)

    }


    //退出
    def logout() {
        session.invalidate()
        render([status: 0, msg:'退出系统成功'] as JSON)
    }




    //楼盘佣金规则
    def bdcashrule(){
        def ret = [status:0, msg:'OK']

        def tmpbd = Building.read(params.long('id', 0))
        if(!tmpbd){
            ret.status = 1
            ret.msg = "无效的楼盘"
            render(ret as JSON)
            return
        }

        ret.bd_cash_rule = tmpbd.bd_cashrule

        render(ret as JSON)
    }

    //车行佣金规则
    def cbcashrule(){
        def ret = [status:0, msg:'OK']

        def tmpcb = Car_bank.read(params.long('id', 0))
        if(!tmpcb){
            ret.status = 1
            ret.msg = "无效的车行"
            render(ret as JSON)
            return
        }

        ret.cb_cash_rule = tmpcb.cb_cashrule

        render(ret as JSON)
    }



    //通过团码找团队
    def searchteam(){
        def ret = [:]

        def tmpteam = Agent_team.findByTeam_no(params.long('tno', 0))
        if(!tmpteam){
            render([:] as JSON)
            return
        }

        ret.team_name = tmpteam.team_name
        ret.team_leader = Agent.read(tmpteam.team_agtid)?.agent_name
        ret.team_no = tmpteam.team_no

        render(ret as JSON)
    }



    /* 楼盘项目列表 */
    def teambdlist() {
        def city = params.city
        def bdarea = params.area

        def tsql = "select count(*) "
        def vsql = """select b.bd_name, b.id"""

        def sql = """ from Building b where 1 = 1"""

        if (city && city != "全部") {
            sql += """ and b.bd_city = '${city}'"""
        }

        if (bdarea && bdarea != "全部") {
            sql += """ and b.bd_area = '${bdarea}'"""
        }

        def osql = """ order by b.id desc """

        def total = Building.executeQuery(tsql + sql)[0].toLong()

        def items = Building.executeQuery(vsql + sql + osql).collect {

            [
                    bd_name: it[0], bd_id: it[1]
            ]
        }

        render([total: total, items: items] as JSON)
    }



    // 车行列表
    def teamcblist(){
        def city = params.city
        def area = params.area

        def tsql = "select count(*) "
        def vsql = """select c.id, c.cb_shortname"""

        def sql = """ from Car_bank c where 1 = 1 """

        if(city && city != '全部'){
            sql += """ and c.cb_city = '${city}'"""
        }

        if(area && area != '全部'){
            sql += """ and c.cb_area = '${area}'"""
        }

        def osql = """ order by c.id desc """


        def total = Car_bank.executeQuery(tsql + sql)[0].toLong()

        def items = Car_bank.executeQuery(vsql + sql + osql).collect {
            [
                    cb_id:it[0], cb_name:it[1]
            ]
        }

        render([total:total, items: items] as JSON)
    }


    //单月节日列表
    def holidaylist(){
        def ret = [status: 0, msg: 'OK']

        if(!params.date){
            ret.status = 1
            ret.msg = "参数错误"
            render(ret as JSON)
            return
        }

        def tmpmonth = params.date("date", "yyyy-MM")

        if(!tmpmonth){
            ret.status = 2
            ret.msg = "无效的日期"
            render(ret as JSON)
            return
        }

        def thismonth = tmpmonth.format('yyyy-MM-dd')

        tmpmonth.month = tmpmonth.month + 1

        def vsql = """select sh.id, sh.holiday_name, sh.holiday_day"""

        def sql = """ from Sys_holiday sh where sh.holiday_day >= '${thismonth}' and sh.holiday_day < '${tmpmonth.format('yyyy-MM-dd')}'"""

        def osql = """ order by sh.holiday_day """

        ret.items = Sys_holiday.executeQuery(vsql + sql + osql).collect {
            [
                    holiday_id: it[0], holiday_name: it[1], holiday_day:it[2]
            ]
        }


        render(ret as JSON)

    }

    //节日祝福语示例
    def greetdemo(){
        def ret = [status: 0, msg: 'OK']

        def tmph = Sys_holiday.read(params.long('id', 0))
        if(!ret){
            ret.status = 1
            ret.msg = "无效的节日id"
            render(ret as JSON)
            return
        }

        ret.greet = tmph.holiday_greet

        render (ret as JSON)
    }

    //课程列表
    def subjectlist(){
        def itemlist = Test_subject.executeQuery("select s.id, s.subject_name from Test_subject s").collect {
            [id: it[0], name:it[1]]
        }

        render([identifier: "id", label: "name", items: itemlist] as JSON)
    }

    //首页客服电话
    def syscall(){

    }

    //检查验证码
    def chkfind() {
        def ret=[status:0,msg:'验证通过']
        if (session.smscode != params.chkcode) {
            ret.status = 1
            ret.msg = "验证码不正确"
            render (ret as JSON)
            return
        }

        render (ret as JSON)
    }

    //忘记密码之后通过短信找回(重置)密码
    def resetpwd(){
        def ret=[status:0,msg:'设置密码成功']
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
            agent_passwd = (agent_salt + params.passwd).encodeAsSHA1()

            save(flush: true)
        }

        render (ret as JSON)
    }

    def startpic(){
        def ret = [file_id: "", status: 1, msg: '无图片']

        def tmppic = Back_str.read(Enum_backstr.RS_startpic.code)?.value

        if(tmppic){
            ret.msg = ''
            ret.status = 0
            ret.file_id = tmppic
        }

        render(ret as JSON)
    }
}
