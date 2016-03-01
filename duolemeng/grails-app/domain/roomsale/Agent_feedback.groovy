package roomsale

class Agent_feedback {
    Long        fb_agentid = 0
    Date        fb_time = new Date()

    String      fb_content = ""

    static constraints = {
        fb_content(nullable: true, maxSize: 128)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_feedback_seq'])
    }

}
