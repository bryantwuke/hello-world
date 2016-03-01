package roomsale

class Building_around {

    String      bd_transport  = ""                //周边交通说明
    String      bd_commercial = ""               //周边商业说明
    String      bd_education  = ""               //周边教育说明
    String      bd_entertain  = ""               //周边娱乐说明

    static constraints = {
        bd_transport(nullable: true, maxSize: 200)
        bd_commercial(nullable: true, maxSize: 200)
        bd_education(nullable: true, maxSize: 200)
        bd_entertain(nullable: true, maxSize: 200)
    }

    static mapping = {
        id(generator: 'assigned')
    }

}
