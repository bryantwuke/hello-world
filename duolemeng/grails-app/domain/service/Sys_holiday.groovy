package service

class Sys_holiday {

    String          holiday_name        // 节日名称
    Date            holiday_day         // 节日的具体日期

    String          holiday_greet       // 节日常用祝福语

    static constraints = {
        holiday_name(nullable: false, maxSize: 10)
        holiday_day(nullable: false)

        holiday_greet(nullable: true, maxSize: 200)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Sys_holiday_seq'])
    }

}
