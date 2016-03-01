package service

class Banner {

    Long                    banner_fileid = 0                        //文件id
    Integer                 banner_valid = 1                         //是否有效,1为无效
    String                  banner_url                               //图片url

    static constraints = {
        banner_url(nullable: true, maxSize: 100)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Banner_seq'])
    }
}
