package sysuser

class Sys_group {
    String              group_name
    String              group_rkey = ""

    static constraints = {
        group_name(blank: false, maxSize: 20)
        group_rkey(blank: false, maxSize: 160)

    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Sys_group_seq'])
    }
}
