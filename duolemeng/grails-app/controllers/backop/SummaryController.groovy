package backop
import basic.BaseController
import grails.converters.JSON
import groovy.time.TimeCategory
import roomsale.*
//后台统计报报表
class SummaryController extends BaseController{

    def index() {}

    /*  团队  */
    def teamlist(){
        render(template: "/summary/team_list")
    }

    def ds_team_list(){
        def tsql = "select count(*) "
        def vsql = """select t.id, t.team_no, t.team_name, t.team_iconid, a.agent_name, a.agent_mobile"""

        def sql = """ from Agent_team t, Agent a where t.team_agtid = a.id"""

        if(params.kword){
            sql +=""" and (cast(team_no as string) like '%${params.kword}%' or t.team_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%')"""
        }


        def osql = sqlorder("t", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Agent_team.executeQuery(tsql + sql)[0].toLong()

        def items = Agent_team.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], team_no:it[1], team_name: it[2], team_iconid:it[3], agent_name:it[4], agent_mobile:it[5]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }

    //团队业绩
    def memberlist(){
        def ret = [status: 0]

        def tmpteam = Agent_team.read(params.long('id', 0))
        if(!tmpteam){
            ret.status = 1
            render(ret as JSON)
            return
        }

        def team_member = Agent.executeQuery("select agent_name, agent_mobile, id from Agent where agent_teamid = ${tmpteam.team_no} and agent_status in (${Enum_Agentstatus.AS_team_leader.code}, ${Enum_Agentstatus.AS_team_member.code})").collect {
            [agent_name: it[0], agent_mobile: it[1], agent_id: it[2]]
        }

        team_member.each {
            it.cst_num = Cst_basic.countByCst_agtid(it.agent_id)
            it.bdregyes_num = Cst_basic.countByCst_agtidAndCst_regbd(it.agent_id, Enum_Regstatus.RS_regyes.code)
            it.bdregok_num = Cst_basic.countByCst_agtidAndCst_regbd(it.agent_id, Enum_Regstatus.RS_ok.code)

            it.cbregyes_num = Cst_basic.countByCst_agtidAndCst_regcar(it.agent_id, Enum_Regstatus.RS_regyes.code)
            it.cbregok_num = Cst_basic.countByCst_agtidAndCst_regcar(it.agent_id, Enum_Regstatus.RS_ok.code)
        }

        ret.team_mate = team_member

        render(ret as JSON )

    }

    /*   团队 结束 */


    /*  分享记录  */

    def sharelist(){
        render(template: "/summary/share_list")
    }

    def ds_share_list(){
        def tsql = "select count(*) "
        def vsql = """select s.id, s.share_readnum, s.share_date, a.agent_name, a.agent_mobile, dc.id, dc.dc_title"""

        def sql = """ from Agent_share s, Agent a, Discount dc where s.share_agentid = a.id and s.share_discountid = dc.id"""

        if(params.kword){
            sql +=""" and (a.agent_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%')"""
        }


        def osql = sqlorder("s", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Agent_share.executeQuery(tsql + sql)[0].toLong()

        def items = Agent_share.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], share_readnum:it[1], share_date:it[2], agent_name:it[3], agent_mobile: it[4], dc_id:it[5], dc_title:it[6]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }

    /*   分享记录 结束 */


    /*  经纪人统计查询  */
    def agtsumlist(){
        def end = new Date()
        def start = null

        use(TimeCategory) {
            start = end - 7.days
        }

        def tmpins = [
                start_time :start,
                end_time : end
        ]
        render(template: "/summary/agtsum_list", model: [timeins : tmpins])
    }

    def ds_agtsum_list(){
        def tsql = "select count(*) "
        def vsql = """select a.id, a.agent_name, a.agent_mobile, a.agent_iconid, a.agent_address, a.agent_idcard, a.agent_idcardid, a.agent_teamid, a.agent_checkall, a.agent_checking, a.agent_time"""

        def sql = """ from Agent a where a.agent_status in (${Enum_Agentstatus.AS_idcard_ok.code}, ${Enum_Agentstatus.AS_team_member.code}, ${Enum_Agentstatus.AS_team_leader.code})"""

        if(params.kword){
            sql +=""" and (a.agent_name like '%${params.kword}%' or a.agent_mobile like '%${params.kword}%')"""
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
                    id:it[0], agent_name: it[1], agent_mobile: it[2], agent_iconid:it[3], agent_address:it[4], agent_idcard:it[5], agent_idcardid:it[6],
                    agent_teamid:it[7], agent_checkall:it[8], agent_checking:it[9], agent_time:it[10]
            ]
        }

        items.each {
            // 业绩
            it.cst_num = Cst_basic.countByCst_agtid(it.id)
            it.bdregyes_num = Cst_basic.countByCst_agtidAndCst_regbd(it.id, Enum_Regstatus.RS_regyes.code)
            it.bdregok_num = Cst_basic.countByCst_agtidAndCst_regbd(it.id, Enum_Regstatus.RS_ok.code)

            it.cbregyes_num = Cst_basic.countByCst_agtidAndCst_regcar(it.id, Enum_Regstatus.RS_regyes.code)
            it.cbregok_num = Cst_basic.countByCst_agtidAndCst_regcar(it.id, Enum_Regstatus.RS_ok.code)

            //账户
            def tmpac = Agent_account.read(it.id)
            if(!tmpac){
                it.at_usable = 0
                it.at_fixed = 0
            }else{
                it.at_usable = tmpac.at_usable
                it.at_fixed = tmpac.at_fixed
            }
        }


        render([numRows: total, items: items] as JSON)
    }

    /*  经纪人统计查询  结束*/

    def checklist(){
        def agent_id = params.long('agent_id', 0)
        def end_time = params.date('end_time', 'yyyy-MM-dd')
        if(!end_time){
            end_time = new Date()
            end_time.clearTime()
        }

        use(TimeCategory) {
            end_time = end_time + 1.days
        }

        def start_time = params.date('start_time', 'yyyy-MM-dd')
        if(!start_time){
            use(TimeCategory) {
                start_time = end_time - 8.days
            }
        }

        def vsql = """select c.check_place, c.check_time, c.check_note, c.check_fileid """

        def sql = """ from Agent_check c where c.check_agentid = ${agent_id} and (c.check_time between '${start_time.format('yyyy-MM-dd')}' and '${end_time.format('yyyy-MM-dd')}')"""

        def osql = sqlorder("c", "check_time", "desc")

        def items = Agent_check.executeQuery(vsql + sql + osql).collect {
            [
                    check_place:it[0], check_time:it[1], check_note:it[2], check_fileid:it[3]
            ]
        }

        render(items as JSON)

    }



    /*  用户反馈  */

    def feedbacklist(){
        render(template: "/summary/feedback_list")
    }

    def ds_feedback_list(){
        def tsql = "select count(*) "
        def vsql = """select f.id, f.fb_content, f.fb_time"""

        def sql = """ from Agent_feedback f"""


        def osql = sqlorder("f", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Agent_feedback.executeQuery(tsql + sql)[0].toLong()

        def items = Agent_feedback.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    id:it[0], fb_content:it[1], fb_time:it[2]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }

    /*   分享记录 结束 */

}
