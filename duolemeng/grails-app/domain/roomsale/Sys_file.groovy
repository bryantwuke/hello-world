package roomsale

class Sys_file {
    String          file_orgname                                                                    //文件名
    String          file_type                                                                        //文件类型
    Float           file_size = 1.0                                                                  //文件大小
    String          file_dir                                                                         //文件路径

    Integer         file_refclass = 0                                                               //文件所对应类别
    Long            file_refid = 0                                                                   //文件所对应的id

    static constraints = {
        file_orgname(nullable: false, maxSize: 50)
        file_type(nullable: true, maxSize: 20)
        file_size(nullable: true)
        file_dir(nullable: false, maxSize: 200)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Sys_file_seq'])
    }

    void delit() {
        new File(this.file_dir + File.separator + this.id.toString()).delete()
        this.delete(flush: true)
    }

}

public enum Enum_Fileclass {
    FC_none(0, '不定','','',''),
    FC_usericon(10, '用户头像','grid_user','roomsale.Agent','agent_iconid'),
    FC_useridcard(20, '身份证','grid_user','roomsale.Agent','agent_idcardid'),

    FC_apartment(30, '楼盘介绍','grid_building','roomsale.Building','bd_picnum'),
    FC_housetype(40, '户型','grid_building','roomsale.Building','bd_typenum'),

    FC_contract(50, '成交合同','grid_sale','roomsale.Cst_sale','file_num'),

    FC_banner(60, 'Banner图片', 'grid_banner', 'service.Banner', 'file_num'),
    FC_conimg(70, '详情图片', '', '', ''),
    FC_car(80, '车行图片', 'grid_cb', 'roomsale.Car_bank', 'file_num'),

    FC_check(90, '签到头像','grid_check','roomsale.Agent_check','check_fileid'),
    FC_certificate(100, '收款凭证','grid_pay','roomsale.Cst_pay','pay_fileid'),

    FC_teamicon(120, '团队头像','grid_team','roomsale.Agent_team','team_iconid'),

    FC_startpic(140, '启动图','grid_start','service.Back_str','value'),


    private final Integer code
    private final String name
    private final String refgrid
    private final String refdomain
    private final String ref_fieldname

    Enum_Fileclass(Integer code, String name, String refgrid, String refdomain, String ref_fieldname){
        this.code = code
        this.name = name
        this.refgrid = refgrid
        this.refdomain = refdomain
        this.ref_fieldname = ref_fieldname
    }

    String getKey() { name()}

    static byCode(Integer code) {
        values().find { it.code == code }
    }

}
