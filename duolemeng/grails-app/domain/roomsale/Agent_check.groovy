package roomsale

// 签到表
class Agent_check {

    Long            check_agentid = 0                 //签到人

    Date            check_time                    //签到时间
    String          check_place = ""                   //签到地点

    String          check_note = ""                    // 签到补充说明

    Long            check_fileid = 0             // 签到自拍照文件ID


    static constraints = {
        check_agentid(nullable: false)

        check_time(nullable: false)
        check_place(nullable: true, maxSize: 30)

        check_note(nullable: true, maxSize: 30)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_check_seq'])
    }
}
