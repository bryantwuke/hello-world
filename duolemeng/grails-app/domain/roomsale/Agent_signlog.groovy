package roomsale

class Agent_signlog {
    Long        sign_agentid = 0
    Date        sign_time = new Date()

    Integer     sign_coin = 1

    static constraints = {
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_signlog_seq'])
    }

}
