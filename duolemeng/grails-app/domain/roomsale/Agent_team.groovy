package roomsale

class Agent_team {
    Long            team_no = 0                 // 团码
    String          team_name               // 团队名称
    Long            team_agtid = 0              // 团队Leader
//    Integer         team_num = 1            // 团队当前人数

    Long            team_iconid = 0        //团队图标id

    static constraints = {
        team_name(nullable: false, maxSize: 20)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Agent_team_seq'])
    }

}
