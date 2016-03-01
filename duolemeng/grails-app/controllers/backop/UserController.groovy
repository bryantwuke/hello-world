package backop
import service.Back_str
import service.Enum_backstr
import sysuser.Enum_userstatus
import sysuser.Sys_user
import sysuser.User_right

class UserController {

    static defaultAction = "login"

/*
    //经纪人登陆，仅用于测试
    def signin() {
        render(view: 'signin')
    }

    def auth() {
        if ((!params.loginname)||(!params.loginpass)) {
            render(1)
            return
        }

        def tmpuser = Agent.findByAgent_mobile(params.loginname)
        if (!tmpuser) {
            render(1)
            return
        }




        if (tmpuser.agent_passwd != (tmpuser.agent_salt + params.loginpass).encodeAsSHA1()) {
            render(2)
            return
        }

        session.agentname = tmpuser.agent_name
        session.agentid = tmpuser.id
        session.agentstatus = tmpuser.agent_status
        session.agentmobile = tmpuser.agent_mobile
        session.agentteamid = tmpuser.agent_teamid

        render(0)
    }

    // 短信验证登陆
    def vcsignin(){
        render(view: 'vcsignin')
    }

    // 后台重置密码
    def resetpwd(){
        render(view: 'resetpwd')
    }

*/

    def signout() {
        session.invalidate()
        render(status: 204)
    }

    //后台人员登陆
    def login() {
        render(view: 'login')
    }

    def backauth() {
        if ((!params.loginname)||(!params.loginpass)) {
            render(1)
            return
        }

        def tmpuser = Sys_user.findByUser_mobileOrUser_loginname(params.loginname, params.loginname)

        if (tmpuser) {
            if (tmpuser.user_status in [Enum_userstatus.US_dlm_no.code, Enum_userstatus.US_bd_no.code, Enum_userstatus.US_car_no.code]) {
                render(1)
                return
            }

            if (tmpuser.user_pwd != (tmpuser.user_pwdsalt + params.loginpass).encodeAsSHA1()) {
                render(2)
                return
            }

            session.username = tmpuser.user_name
            session.userid = tmpuser.id
            session.userstatus = tmpuser.user_status
            session.usermobile = tmpuser.user_mobile
            session.userorgid  = tmpuser.user_orgid

            session.rights = User_right.read(tmpuser.id).properties.findAll {it.value>0}.keySet()

        }else {
            def admin_name = Back_str.read(Enum_backstr.BS_admin.code).value
            def admin_salt = Back_str.read(Enum_backstr.BS_adsalt.code).value
            def admin_pass = Back_str.read(Enum_backstr.BS_adpwd.code).value

            if ((admin_name == params.loginname) &&(admin_pass == (admin_salt + params.loginpass).encodeAsSHA1())) {
                session.username = '后台管理员'
                session.userid = -1
                session.rights = ['r_user']

                def admin_mobile = new String(Back_str.read(Enum_backstr.BS_admobile.code).value.decodeHex())
                session.usermobile = admin_mobile

            }else {
                render(2)
                return
            }
        }


        render(0)
    }


    def welcome() {
        render(template: "/user/welcome")
    }

}
