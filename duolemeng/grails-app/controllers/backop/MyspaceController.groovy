package backop

class MyspaceController {
    def index() {

        def stimeout = 1800
        def rmdtimeout = 60
        def fdisk=new File(grailsApplication.config.mydir).getFreeSpace().intdiv(1048576)

        session.setMaxInactiveInterval(stimeout);

        render(view: '/layouts/main', model: [rmdtoutins: rmdtimeout, fdiskins:fdisk])

    }
}
