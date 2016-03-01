package appgw

import groovy.time.TimeCategory
import roomsale.*

class WebController {

    def reg_agree() {
        render(view: "reg_agree")
    }

    def share(){
        def tmpdc = Discount.read(params.long('id',0))
        long    agentid = params.long('agentid', 0)

        if(!tmpdc){
            render ("<div style=\"color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold\">啊哈，您访问的页面飞走了!</div>" )
            return
        }

        if( !(tmpdc.dc_type  in [Enum_Discounttype.DT_discount_ok.code, Enum_Discounttype.DT_news_ok.code])){
            render ("<div style=\"color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold\">此活动未通过审核</div>" )
            return
        }

        //按周更新分享统计数据
        def now = new Date()
        Date week = null
        use(TimeCategory) {
            week = now - (now[Calendar.DAY_OF_WEEK] - 1).days
        }
        week.clearTime()

        def share_week = Agent_share.findOrCreateByShare_agentidAndShare_discountidAndShare_date(agentid, tmpdc.id, week)
        share_week.with {
            share_readnum++
            save(flush: true)
        }


        render(view: "/web/share", model: [disins: [dcinfo: tmpdc, agentid: agentid]])
    }

    def open(){
        def tmpdc = Discount.read(params.long('id',0))

        if(!tmpdc){
            render ("<div style=\"color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold\">啊哈，您访问的页面飞走了!</div>" )
            return
        }

        render(view: "/web/open", model: [disins: tmpdc])

    }

    def tlearn() {
        def tmp_subject = Test_subject.read(params.long("id", 0))

        if(!tmp_subject){
            render ("<div style=\"color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold\">啊哈，您访问的页面飞走了!</div>" )
            return
        }

        def ret = [:]

        def reqnum = 10

        def page = params.int('page', 1)

        def tsql = "select count(*) "
        def vsql = """select test_type, test_title, test_optiona, test_optionb,
                            test_optionc, test_optiond, test_optione, test_optionf, test_answer """

        def sql = """ from Test_bank where test_subjectid=${tmp_subject.id} """

        def osql = """ order by test_title desc """

        def mypage = [readOnly: true, max: reqnum]
        mypage.put("offset", (page - 1).multiply(reqnum))

        ret.page = page
        ret.subid = tmp_subject.id
        ret.start = (page - 1) * reqnum

        ret.total = Test_bank.executeQuery(tsql + sql)[0].toLong()
        ret.pagenum =  ret.total.mypage(reqnum)


        ret.items = Test_bank.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    test_title: "(${Enum_Testtype.byCode(it[0]).name})${it[1]}", test_optiona:it[2], test_optionb:it[3], test_optionc:it[4],
                    test_optiond:it[5],  test_optione:it[6], test_optionf:it[7], test_answer: it[8].toLowerCase().toCharArray()
            ]
        }

        render(view: "/web/learn", model: [learnins: ret])
    }

    def texam() {

        if(!session.agentid){
            render ("<div style=\"color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold\">你还未登录系统!</div>" )
            return
        }

        def tmp_subject = Test_subject.read(params.long("id", 0))

        if(!tmp_subject){
            render ("<div style=\"color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold\">啊哈，您访问的页面飞走了!</div>" )
            return
        }

        def mypage = [readOnly: true, max: 20]

        def testret = [subid: tmp_subject.id]

        def total = Test_bank.countByTest_subjectid(tmp_subject.id)

        if(total == 0){
            render ("<div style=\"color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold\">此科目暂时没有题目!</div>" )
            return
        }

        testret.items = Test_bank.executeQuery("""select id, test_type, test_title, test_optiona, test_optionb,
                            test_optionc, test_optiond, test_optione, test_optionf, test_answer from Test_bank
                            where test_subjectid=${tmp_subject.id} order by random() """, mypage).collect {
            [id: it[0], test_type: Enum_Testtype.byCode(it[1])?.name, test_title: it[2], test_optiona: it[3], test_optionb: it[4], test_optionc: it[5],
             test_optiond: it[6], test_optione: it[7], test_optionf: it[8], test_answer: it[9]]
        }

        // Set-Cookie	 JSESSIONID=2A6FB01A323A75ACA17F9138B11440E3; Path=/; HttpOnly
        response.addHeader("Set-Cookie", "${request.cookies[0].name}=${request.cookies[0].value}; Path=/; HttpOnly")
        render(view: "/web/tcheck", model: [testins: testret])
    }

    def scheck() {
        def sub_id = params.int('subid', 0)

        params.remove('subid')
        params.remove("controller")
        params.remove("format")
        params.remove("action")

        def torg = null
        def tdo = null

        Long titem = 0
        int torglen = 0
        int score = 0

        params.each {
            titem = it.key.isLong()? it.key.toLong():0
            torg = Test_bank.read(titem)?.test_answer

            torglen = torg.size()

            if (torglen == it.value.size()) {
                if (torglen == 1) {
                    if (torg.compareToIgnoreCase(it.value) == 0) {
                        score += 5
                    }
                }else {
                    tdo = it.value.join()
                    if (torg.compareToIgnoreCase(tdo) == 0) {
                        score += 5
                    }
                }
            }
        }

        if(session.agentid){ //记录考试分数
            def tmpsub = Test_subject.read(sub_id)
            if(tmpsub){
                def tmptest = new Test_log()
                tmptest.with {
                    test_agentid = session.agentid
                    test_time = new Date()
                    test_score = score
                    test_subjectid = sub_id

                    save(flush: true)
                }
            }
        }

        // println(score)
        //render(score)
        render(view: "/web/tscore", model: [scoreins: score])
    }


    //楼盘参数
    def bdbasic(){
        def tmpbd = Building.get(params.long('id',0))
        if(!tmpbd){
            render ("<div style=\"color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold\">啊哈，您访问的页面飞走了!</div>" )
            return
        }

        def typetag = Tag.read(tmpbd.bd_type_tagid)?.tag_name?.replaceAll("_", " ")
        def bdtag = Tag.read(tmpbd.bd_tag_tagid)?.tag_name?.replaceAll("_", " ")

        def tmpins = [
                bdinfo: tmpbd,
                type: typetag,
                tag: bdtag
        ]

        render(view: "/web/bd_basic", model: [bdins: tmpins])
    }

    def bdshare(){
        def tmpbd = Building.get(params.long('id',0))
        if(!tmpbd){
            render ("<div style=\"color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold\">啊哈，您访问的页面飞走了!</div>" )
            return
        }

        def typetag = Tag.read(tmpbd.bd_type_tagid)?.tag_name?.replaceAll("_", " ")
        def bdtag = Tag.read(tmpbd.bd_tag_tagid)?.tag_name?.replaceAll("_", " ")

        def tmpba = Building_around.read(tmpbd.id)

        def pic = Sys_file.executeQuery("select min(id) from Sys_file where file_refclass = ${Enum_Fileclass.FC_apartment.code} and file_refid = ${tmpbd.id}")[0]

        if(!pic){
            pic = 0
        }

        def tmpins = [
                bdinfo: tmpbd,
                type: typetag,
                tag: bdtag,
                bainfo: tmpba,
                bdpic:pic
        ]

        render(view: "/web/bd_share", model: [bdins: tmpins])
    }

    def carshare(){
        def tmpcar = Car_bank.get(params.long('id',0))
        if(!tmpcar){
            render ("<div style=\"color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold\">啊哈，您访问的页面飞走了!</div>" )
            return
        }

        def carbrand = Tag.read(tmpcar.cb_brand_tagid)?.tag_name?.replaceAll("_", " ")

        def cb_pic = Sys_file.executeQuery("select min(id) from Sys_file where file_refclass = ${Enum_Fileclass.FC_car.code} and file_refid = ${tmpcar.id}")[0]

        if(!cb_pic){
            cb_pic = -1
        }

        def tmpins = [
                brands: carbrand,
                carinfo: tmpcar,
                pic: cb_pic
        ]

        render(view: "/web/car_share", model: [carins: tmpins])

    }




    def regdiscount(){
        final String FS_ismobile = "^1[1-9]\\d{9}\$"

        def mobile = params.reg_mobile
        def name = params.reg_name
        def discountid = params.long('reg_discountid', 0)
        def agentid = params.long('reg_agentid', 0)

        if(!name || name.trim() == ''){
            render("<div style=\"text-align: center;color: red\">无效的名字</div>")

            return
        }

        if(!(mobile ==~ ~FS_ismobile)){
            render("<div style=\"text-align: center;color: red\">无效的手机</div>")
            return
        }


        def tmpregdc = Discount_reg.findByReg_discountidAndReg_mobile(discountid, mobile)
        if(tmpregdc){
            render("<div style=\"text-align: center;color: red\">您已经于${tmpregdc.reg_time.format('yyyy-MM-dd')} 提交了此申请</div>")
            return
        }

        //insert a new record
        new Discount_reg().with {
            reg_name = name
            reg_mobile = mobile
            reg_discountid = discountid
            reg_note = params.reg_note
            reg_agentid = agentid

            def tmpcst = Cst_basic.findByCst_mobile(mobile)
            if(tmpcst){
                reg_agentid = tmpcst.cst_agtid
                reg_cstid = tmpcst.id
                reg_status = Enum_DiscountRegStatus.RS_yes_wait.code
            }

            save(flush: true)
        }

        render("<div style=\"text-align: center;color: green\">保存成功</div>")
    }

    def calc() {
        render(view: "/web/calc")
    }

    def mycal(){
        render(view: "/web/calculator")
    }
}
