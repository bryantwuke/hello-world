package roomsale

class Test_log {

    Long        test_agentid  = 0                          //经纪人ID

    Date        test_time                                   //考试时间
    Integer     test_score = 0                                 //成绩

    Long        test_subjectid  = 0                                //科目

    static constraints = {
        test_time(nullable: false)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Test_log_seq'])
    }
}
