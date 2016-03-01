import grails.converters.JSON

class BootStrap {

    def grailsApplication

    def init = { servletContext ->

        grailsApplication.config.cfgdir = System.getenv().get("HUATAI_CONFIG").toString()
        grailsApplication.config.mydir = grailsApplication.config.cfgdir + File.separator + "upfiles"

        grailsApplication.config.mydojo = "1.10.4"

        Long.metaClass.mypage = { pagerow=8 ->
            if (delegate) {
                return (int) Math.ceil(delegate / pagerow)?: 1
            }else {
                return 1
            }
        }

        String.metaClass.myshort = {len = 20, pad="..." ->
            if (delegate == null) {return ''}
            if (delegate.length() > len) {return delegate[0..<[len-4,len].max()] + pad}
            return delegate
        }

        JSON.registerObjectMarshaller(Date) {
            if (it == null) {return ''}
            if (it.hours + it.minutes + it.seconds) {
                return it.format("yyyy-MM-dd HH:mm:ss")
            }else {
                return it.format("yyyy-MM-dd")
            }
        }

    }
    def destroy = {
    }
}
