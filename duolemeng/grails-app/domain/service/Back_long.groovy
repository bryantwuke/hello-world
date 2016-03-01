package service

class Back_long {
    Integer     id
    Long        value = 0

    static constraints = {
    }

    static mapping = {
        id(column:'id', name: 'id', generator:'assigned', type:'integer')
    }

}

public enum Enum_backlong {

    BL_sms(1, '短信发送开关'),


    BL_end(9999, 'BL_end')

    private final Integer code
    private final String name

    Enum_backlong(Integer code, String name){
        this.code = code
        this.name = name
    }

    static byCode(Integer code){
        values().find{it.code == code}
    }
}
