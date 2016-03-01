package sysuser

class Sys_useringroup {
    Long        uig_groupid = 0
    Long        uig_userid = 0

    static constraints = {
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Sys_useringroup_seq'])
    }

}
