package roomsale

class Cst_wishcar {
    Integer         car_wishlevel = Enum_Wishlevel.WL_ordinary.code                 //意向等级

    Integer         car_price = Enum_CarPrice.CP_all.code
    Integer         car_level = Enum_CarLevel.CL_all.code

    Integer         car_country = Enum_CarCountry.CC_all.code
    Integer         car_gear = Enum_CarGear.CG_all.code

    Integer         car_frame = Enum_CarFrame.CF_all.code
    Integer         car_volume = Enum_CarVolume.CV_all.code

    Integer         car_drive = Enum_CarDrive.CD_all.code
    Integer         car_fuel = Enum_CarFuel.CF_all.code

    Integer         car_attach = Enum_CarAttach.CA_all.code

    Integer         car_point_tagid = 0                    //卖点需求
    Integer         car_brand_tagid = 0                    //品牌需求

    String          car_note = ""

    static constraints = {
        car_note(nullable: true, maxSize: 100)
    }

    static mapping = {
        id(generator: 'assigned')
    }

}
