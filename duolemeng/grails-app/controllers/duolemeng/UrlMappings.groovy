package duolemeng

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/file/load/$id"(controller: "file", action: "load")
        "/file/load/$id/$filename"(controller: "file", action: "load")

        "/web/open/$id"(controller: "web", action: "open")

        "/web/share/$id"(controller: "web", action: "share")
        "/web/share/$id/$agentid"(controller: "web", action: "share")

        "/web/bdshare/$id"(controller: "web", action: "bdshare")
        "/web/carshare/$id"(controller: "web", action: "carshare")

        "/"(controller: 'myspace', action: "index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
