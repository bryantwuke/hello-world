package roomsale

class Agent_token {
    Date        token_time = new Date()
    String      token_code         // 设备ID

    String      token_devid         // 设备ID
    Long        token_agentid = 0

    static constraints = {
        token_code(nullable: false, maxSize: 48)
        token_devid(nullable: false, maxSize: 48)
    }

    static mapping = {
        token_code(index: 'token_code_Idx')
        id(generator: 'sequence', params: [sequence: 'Agent_token_seq'])
    }

}
