package roomsale

//经纪人计划提醒
class Agent_todo {

    Long            todo_agentid = 0                              //所对应经济人id

    Long            todo_refbdid = 0                              // 关联的楼盘ID
    Long            todo_refcstid = 0                             // 关联的客户ID

    String          todo_title                                     //提醒消息标题
    String          todo_note                                      //提醒消息备注

    Date            todo_create                              // 消息生成时间
    Date            todo_planfrom                            // 计划开始时间
    Date            todo_planend                            // 计划结束时间

    Date            todo_logfrom                             // 实际开始时间
    Date            todo_logend                              // 实际完成时间

    Integer         todo_status                              // 提醒消息状态

    static constraints = {
        todo_agentid(nullable: false)

        todo_refbdid(nullable: false)
        todo_refcstid(nullable: false)

        todo_title(nullable: false, maxSize: 30)
        todo_note(nullable: true, maxSize: 200)

        todo_create(nullable: false)
        todo_planfrom(nullable: false)
        todo_planend(nullable: false)

        todo_logfrom(nullable: true)
        todo_logend(nullable: true)

        todo_status(nullable: false)
    }


    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_todo_seq'])
    }
}

enum Enum_Todostatus{
    TS_waiting(0, '未开始'),
    TS_late(1, '延迟'),

    TS_going(10, '进行中'),

    TS_end(20, '正常完成'),
    TS_lateend(21, '延期完成')

    private final Integer code
    private final String name

    Enum_Todostatus(Integer code,String name){
        this.code = code
        this.name = name
    }
}
