package roomsale

class City_area {
    String                  province                           //省，直辖市
    String                  city                                //市
    String                  area                                //区，县

    static constraints = {
        province(nullable: false, maxSize: 20)
        city(nullable: false, maxSize: 20)
        area(nullable: false, maxSize: 20)
    }

    static mapping = {
        province(index: 'province_Idx')
        city(index: 'city_Idx')
        id(generator: 'sequence', params: [sequence: 'City_area_seq'])
    }
}
