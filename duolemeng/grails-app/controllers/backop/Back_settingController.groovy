package backop
import basic.BaseController
import grails.converters.JSON
import roomsale.Agent_bank
import roomsale.Bank_type
import service.Back_str
import service.Banner
import service.Enum_backstr
import service.Sms_send
import service.Sys_holiday

class Back_settingController extends BaseController{

    def bannerlist(){
        render(template: "/back_setting/banner_list")
    }

    def ds_banner_list(){
        def tsql = "select count(*) "
        def vsql = """select bn.id, bn.banner_title, bn.banner_content, bn.file_num, bn.banner_valid """

        def sql = """ from Banner bn where 1 = 1"""

        def osql = sqlorder("bn", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Banner.executeQuery(tsql + sql)[0].toLong()

        def items = Banner.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], banner_title:it[1], banner_content:it[2], file_num:it[3], banner_valid:it[4]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }


    def banner_add() {
        render(template: "/back_setting/banner_add", model: [bnins:[:]])
    }

    def banner_addlog() {
        def tmpbn = new Banner()
        bindData(tmpbn,params)
        tmpbn.save(flush: true)

        render(status: 204)
    }

    def banner_viewlog() {
        def tmpbn = Banner.read(params.long("banner_id", 0))
        render(template: "/back_setting/banner_add", model: [bnins:tmpbn])
    }

    def banner_editlog() {
        def tmpbn = Banner.get(params.long('id',0L))
        if(!tmpbn){
            render(status: 501)
            return
        }

        bindData(tmpbn,params)
        tmpbn.save(flush: true)

        render(status: 204)
    }

    def banner_dellog() {
        Banner.executeUpdate("delete  from Banner where id=? ",[params.long('id', 0)])
        render(status: 204)
    }

    def ds_bn_list(){
        def valid_type = params.int('vtype', 0)

        def tsql = "select count(*) "
        def vsql = """select bn.banner_fileid, bn.banner_valid, bn.banner_url, bn.id """

        def sql = """ from Banner bn where 1 = 1 """

        if(valid_type != 2){
            sql += " and banner_valid = ${valid_type}"
        }

        def osql = sqlorder("bn", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Banner.executeQuery(tsql + sql)[0].toLong()

        def items = Banner.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    file_id:it[0], banner_valid: it[1], banner_url:it[2], id:it[3]
            ]
        }

        render([numRows: total, items: items] as JSON)

    }

    def banner_setlog(){
        def tmpbn = Banner.findByBanner_fileid(params.long('banner_id',0))
        if(!tmpbn){
            render(status: 501)
            return
        }
        tmpbn.banner_valid = params.int('setvalue',1)
        tmpbn.save(flush: true)

        render(status: 204)
    }

    /* banner 结束 */


    /*  平台公告  */

    /*
    def notelist(){
        String notelabel = "平台公告"
        int ntype = params.int('ntype',-1)
        def tmptype= Enum_Reftype.byCode(ntype)
        if(tmptype){
            ntype = tmptype.code
            notelabel = tmptype.name
        }

        def tmpins = [
                note_label: notelabel,
                note_type: ntype,
        ]

        render(view: "/back_setting/note_list",model:[noteins:tmpins])
    }

    def ds_note_list(){
        def ntype = params.int('ntype', -1)

        def tsql = "select count(*) "
        def vsql = """select n.note_title, n.note_content, n.file_num, n.note_fromtime, n.note_endtime, n.id"""

        def sql = """ from Note n where 1 = 1"""

        if(ntype != -1){
            sql += """ and n.note_type = ${ntype}"""
        }

        def osql = sqlorder("n", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Note.executeQuery(tsql + sql)[0].toLong()

        def items = Note.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    note_title:it[0], note_content:it[1], file_num:it[2], note_fromtime: it[3],
                    note_endtime:it[4], id:it[5]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }


    def note_add() {
        def now = new Date()
        def tmpnote = [
                note_fromtime: now,
        ]

        use(TimeCategory) {
            tmpnote.note_endtime = now + 15.days
        }
        render(template: "/back_setting/note_add", model: [noteins:tmpnote])
    }

    def note_addlog() {

        def tmpnote = new Note()
        bindData(tmpnote,params)
        tmpnote.save(flush: true)


        render(status: 204)
    }

    def note_viewlog() {
        def tmpnote = Note.read(params.long("note_id", 0))
        render(template: "/back_setting/note_add", model: [noteins:tmpnote])
    }

    def note_editlog() {
        def tmpnote = Note.get(params.long('id',0L))
        if(!tmpnote){
            render(status: 501)
            return
        }

        bindData(tmpnote,params)
        tmpnote.save(flush: true)

        render(status: 204)
    }

    def note_dellog() {
        Note.executeUpdate("delete  from Note where id=? ",[params.long('id', 0)])
        render(status: 204)
    }

*/
    /* 平台公告结束  */


    def smslist(){
        render(template: "/back_setting/sms_list")
    }

    /*  短信列表  */
    def ds_sms_list() {
        def tsql = "select count(*) "
        def vsql = """select ss.sms_time, ss.sms_mobile, ss.sms_content """

        def sql = """ from Sms_send ss """

        def osql = sqlorder("ss", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Sms_send.executeQuery(tsql + sql)[0].toLong()

        def items = Sms_send.executeQuery(vsql + sql + osql, mypage).collect {
            [       sms_time:it[0].format("yyyy-MM-dd HH:mm:ss"),
                    sms_mobile:it[1],
                    sms_content:it[2]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }



    /*  节日 */
    def holidaylist(){
        render(template: "/back_setting/holiday_list")
    }

    def ds_holiday_list(){
        def tsql = "select count(*) "
        def vsql = """select h.id, h.holiday_name, h.holiday_day, h.holiday_greet """

        def sql = """ from Sys_holiday h where 1 = 1 """

        def osql = sqlorder("h", "holiday_day", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Sys_holiday.executeQuery(tsql + sql)[0].toLong()

        def items = Sys_holiday.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], holiday_name:it[1], holiday_day:it[2], holiday_greet:it[3]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }


    def holiday_add() {

        def tmpins = [
                holiday_day: new Date()
        ]
        render(template: "/back_setting/holiday_add", model: [hdayins:tmpins])
    }

    def holiday_addlog() {
        //同一天不能有多个节日
        if(!params.holiday_name || !params.holiday_day){
            render(status: 501)
            return
        }

        def date = Date.parse('yyyy-MM-dd', params.holiday_day)

        def tmph = Sys_holiday.findByHoliday_day(date)
        if(tmph){
            render(status: 502)
            return
        }

        tmph = new Sys_holiday()
        tmph.with {
            holiday_name = params.holiday_name
            holiday_day = date
            holiday_greet = params.holiday_greet

            save(flush: true)
        }

        render(status: 204)
    }

    def holiday_viewlog() {
        def tmph = Sys_holiday.read(params.long("holiday_id", 0))

        render(template: "/back_setting/holiday_add", model: [hdayins:tmph])
    }

    def holiday_editlog() {
        def tmph = Sys_holiday.get(params.long('id',0L))
        if(!tmph){
            render(status: 501)
            return
        }

        if(!params.holiday_name || !params.holiday_day){
            render(status: 501)
            return
        }

        def date = Date.parse('yyyy-MM-dd', params.holiday_day)

        def tmph2 = Sys_holiday.findByHoliday_day(date)
        //同一天不能有多个节日
        if(tmph2 && tmph2.id != tmph.id){
            render(status: 502)
            return
        }

        tmph.with {
            holiday_name = params.holiday_name
            holiday_day = date
            holiday_greet = params.holiday_greet

            save(flush: true)
        }

        tmph.save(flush: true)

        render(status: 204)
    }

    def holiday_dellog() {
        Sys_holiday.executeUpdate("delete  from Sys_holiday where id=? ",[params.long('id', 0)])
        render(status: 204)
    }

    /* 节日结束  */



    /*  银行列表  */
    def banklist(){
        render(template: "/back_setting/bank_list")
    }

    def ds_bank_list(){
        def tsql = "select count(*) "
        def vsql = """select b.id, b.bt_prefix, b.bt_name"""

        def sql = """ from Bank_type b where 1 = 1 """

        if(params.kword){
            sql += """ and (b.bt_prefix like '%${params.kword}%' or b.bt_name like '%${params.kword}%')"""
        }

        def osql = sqlorder("b", "bt_prefix", "asc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Bank_type.executeQuery(tsql + sql)[0].toLong()

        def items = Bank_type.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], bt_prefix:it[1], bt_name:it[2]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }


    def bank_add() {
        render(template: "/back_setting/bank_add", model: [bankins:[:]])
    }

    def bank_addlog() {
        def tmpbt = Bank_type.findByBt_prefix(params.bt_prefix)
        if(tmpbt){
            render(status: 504)
            return
        }

        tmpbt = new Bank_type()

        bindData(tmpbt, params)

        tmpbt.save(flush: true)

        Agent_bank.executeUpdate("""update Agent_bank set bank_id = ${tmpbt.id} where bank_id = 0 and substring(bank_account, 1, ${params.bt_prefix.length()}) = '${params.bt_prefix}'""")

        render(status: 204)
    }

    def bank_viewlog() {
        def tmpbt = Bank_type.read(params.long("bank_id", 0))

        render(template: "/back_setting/bank_add", model: [bankins:tmpbt])
    }

    def bank_editlog() {
        def tmpbt = Bank_type.get(params.long('id',0L))
        if(!tmpbt){
            render(status: 501)
            return
        }

        def tmpbt2 = Bank_type.findByBt_prefix(params.bt_prefix)

        if(tmpbt2 && (tmpbt2.id != tmpbt.id)){
            render(status: 504)
            return
        }

        bindData(tmpbt, params)

        tmpbt.save(flush: true)

        Agent_bank.executeUpdate("""update Agent_bank set bank_id = ${tmpbt.id} where bank_id = 0 and substring(bank_account, 1, ${params.bt_prefix.length()}) = '${params.bt_prefix}'""")

        render(status: 204)
    }

    def bank_dellog() {
        Bank_type.executeUpdate("delete  from Bank_type where id=? ",[params.long('id', 0)])
        render(status: 204)
    }

    /*  银行列表 结束  */

    def startpic(){
        def tmppic = Back_str.read(Enum_backstr.RS_startpic.code)?.value
        Long file_id = 0
        if(tmppic){
            file_id = Long.parseLong(tmppic)
        }

        println("fileid: ${file_id}")
        render(template: "/back_setting/startup_pic", model: [picins: [fileid: file_id]])
    }
}
