package roomsale

class Test_bank {
    Long            test_subjectid = 0            // 课程科目
    Integer         test_type = Enum_Testtype.TT_single.code           //题目类型：判断题、单选题、多选题

    String          test_title                                           //题干

    String          test_answer                                          //正确答案

    String          test_optiona                                         //选项A
    String          test_optionb                                         //选项B
    String          test_optionc                                         //选项C
    String          test_optiond                                         //选项D
    String          test_optione                                         //选项E
    String          test_optionf                                         //选项F

    static constraints = {
        test_title(nullable:  false, maxSize: 50)
        test_answer(nullable: false, maxSize: 10)

        test_optiona(nullable: true, maxSize: 30)
        test_optionb(nullable: true, maxSize: 30)
        test_optionc(nullable: true, maxSize: 30)
        test_optiond(nullable: true, maxSize: 30)
        test_optione(nullable: true, maxSize: 30)
        test_optionf(nullable: true, maxSize: 30)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Test_bank_seq'])
    }
}

public enum Enum_Testtype{
    TT_single(10, '单选'),
    TT_multi(20, '多选'),
    TT_judge(30, '判断')

    private final Integer code
    private final String name

    Enum_Testtype(Integer code,String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code){
        values().find { it.code == code }
    }
}
