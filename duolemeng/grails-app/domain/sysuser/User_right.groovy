package sysuser

class User_right {

    Integer     r_bdself = 0         // 楼盘自身的操作 本楼盘报备情况， 处理客户报备, 优惠编辑, 公告编辑
    Integer     r_bdcorp = 0         // 楼盘合作管理的 1 合作楼盘管理(增，启用，停用，修改基本信息)  2 楼盘操作人员管理: (增加，启用，停用，初始密码/重置密码） 3 楼盘类型，楼盘卖点
    Integer     r_bdop = 0           // 楼盘运营审核相关的 楼盘公告/优惠审核

    Integer     r_carself = 0         // 车行自身操作  1 本车行报备情况，  处理客户报备 2 优惠编辑车行
    Integer     r_carcorp = 0        //车行合作管理的 1 合作车行管理(增，启用，停用，修改基本信息),  2 车行操作人员管理: (增加，启用，停用，初始密码/重置密码） 3 汽车类型，汽车卖点
    Integer     r_carop = 0          //车行优惠审核

    Integer     r_back = 0          //平台自身运营相关的   1 课程和题库操作[增删改查]， 节日维护， 银行卡号与银行的对应关系, banner维护   2 平台公告、优惠
    Integer     r_backop = 0       //平台自身审核相关  1 平台公告、优惠审核

    Integer     r_agentop = 0      //经纪人审核相关的  1 经纪人审核，启用、停用   2 经纪人业绩及客户查询、排名等

    Integer     r_money = 0          // 钱有关的   1 佣金发放与解冻
    Integer     r_user = 0          //后台人员。群组, 权限

    Integer     r_query = 0          // 查询权限
    Integer     r_stat = 0          // 统计权限

    static constraints = {
    }

    static mapping = {
        id(generator:'assigned')
    }

    public Integer bySum() {
        int ret = 0

        this.properties.each {  ret += it.value  }

        return ret
    }

    public Integer byAdd() {
        Integer addrights = 0

        this.properties.each {
            if (it.value > 0) {
                addrights +=1
            }
        }

        return addrights
    }

    public Integer byDel() {
        Integer addrights = 0

        this.properties.each {
            if (it.value < 0) {
                addrights +=1
            }
        }

        return addrights
    }

    public Integer byZero() {
        r_bdself = 0         // 楼盘自身的操作 本楼盘报备情况， 处理客户报备, 优惠编辑, 公告编辑
        r_bdcorp = 0         // 楼盘合作管理的 1 合作楼盘管理(增，启用，停用，修改基本信息)  2 楼盘操作人员管理: (增加，启用，停用，初始密码/重置密码） 3 楼盘类型，楼盘卖点
        r_bdop = 0           // 楼盘运营审核相关的 楼盘公告/优惠审核

        r_carself = 0         // 车行自身操作  1 本车行报备情况，  处理客户报备 2 优惠编辑车行
        r_carcorp = 0        //车行合作管理的 1 合作车行管理(增，启用，停用，修改基本信息),  2 车行操作人员管理: (增加，启用，停用，初始密码/重置密码） 3 汽车类型，汽车卖点
        r_carop = 0          //车行优惠审核

        r_back = 0          //平台自身运营相关的   1 课程和题库操作[增删改查]， 节日维护， 银行卡号与银行的对应关系, banner维护   2 平台公告、优惠
        r_backop = 0       //平台自身审核相关  1 平台公告、优惠审核

        r_agentop = 0      //经纪人审核相关的  1 经纪人审核，启用、停用   2 经纪人业绩及客户查询、排名等

        r_money = 0          // 钱有关的   1 佣金发放与解冻
        r_user = 0          //后台人员。群组, 权限

        r_query = 0          // 查询权限
        r_stat = 0

    }

    public byView() {
        def tmpbr = []

        this.properties.each {
            if (it.value) {
                tmpbr += it.key.toString()
            }
        }

        println(tmpbr)

        return tmpbr
    }


}
