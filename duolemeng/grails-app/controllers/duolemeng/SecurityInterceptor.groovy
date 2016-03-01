package duolemeng

import static grails.artefact.Interceptor$Trait$Helper.matchAll


class SecurityInterceptor {

    public SecurityInterceptor(){
        matchAll().excludes(controller:"appgw|web|file")
    }

    boolean before() {

        println("--------${controllerName}/${actionName}-----------")
        println(params)
        println("                                    ")
        println("                                    ")
        println("                                    ")
        switch (controllerName) {
            case "reggw":
                if (!session.agentid)
                {
                    response.contentType = "application/json;charset=UTF-8"
                    render('{"status":-1,"msg":"未登录"}')
                    return false
                }
                break
            case "appgw":
            case "web":
            case "file":
                break
            default:
                if ((!session.userid)&&(!controllerName.equals('user')))
                {
                    redirect controller: 'user'
                    return false
                }
                break

        }

        true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
