package backop
import basic.BaseController
import grails.converters.JSON
import groovy.time.TimeCategory
import roomsale.*
import service.Enum_Smstype

class BuildingController extends BaseController{

    def index() {
        render("完善中...")
    }

    /*  楼盘类型  */
    def taglist(){
        def type = params.int('tag_type', Enum_Tagtype.TT_bd_type.code)
        def tmpins = [
            tag_type: type,
            tag_label:Enum_Tagtype.byCode(type)?.name
        ]
        render(template: "/building/tag_list", model: [tagins:tmpins])
    }

    def ds_tag_list(){
        def tag_type = params.int('tag_type',-1)

        def tsql = "select count(*) "
        def vsql = """select t.tag_name, t.tag_isbasic, t.id """

        def sql = """ from Tag t where t.tag_isbasic = 0  and t.tag_name IS NOT NULL and t.tag_name not in ('全部', '不限') """

        if(tag_type != -1){
            sql += """ and t.tag_type = ${tag_type}"""
        }

        def osql = sqlorder("t", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Tag.executeQuery(tsql + sql)[0].toLong()

        def items = Tag.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    bd_tag:it[0], isbasic:it[1], id:it[2]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }


    def tag_add() {
        def type = params.int('tag_type', Enum_Tagtype.TT_bd_type.code)
        def tmpins = [
                tag_type: type,
                tag_label:Enum_Tagtype.byCode(type)?.name
        ]
        render(template: "/building/tag_add", model: [tagins:tmpins])
    }

    def tag_addlog() {
        Tag.findOrCreateByTag_nameAndTag_type(params.tag_name, params.tag_type).save(flush: true)
        render(status: 204)
    }

    def tag_viewlog() {
        def tmptag = Tag.read(params.long("tag_id", 0))

        def tmpins = [
                tag_label:Enum_Tagtype.byCode(tmptag.tag_type)?.name,
                id:tmptag.id,
                tag_name: tmptag.tag_name
        ]
        render(template: "/building/tag_add", model: [tagins:tmpins])
    }

    def tag_editlog() {
        def tmptag = Tag.get(params.long('id',0L))
        if(!tmptag){
            render(status: 501)
            return
        }

        bindData(tmptag,params)
        tmptag.save(flush: true)

        render(status: 204)
    }

    def tag_dellog() {
        Tag.executeUpdate("delete  from Tag where id=? ",[params.long('id', 0)])
        render(status: 204)
    }



    /* 标签信息 结束*/


    /*  楼盘列表  */

    def bdlist(){
        render(template: "/building/building_list")
    }


    def ds_bd_list(){
        def city = params.city
        def area = params.area

        def tsql = "select count(*) "
        def vsql = """select b.bd_name, b.bd_developer, b.bd_city, b.bd_area, b.bd_address, b.bd_phone, b.bd_year, b.bd_greenrate, b.bd_volumnrate, b.bd_opentime, b.bd_type_tagid, b.bd_price,
                    b.bd_avgcash,b.bd_house, b.bd_agenttel, b.bd_typenum, b.bd_picnum, b.bd_focus, b.bd_view, b.bd_submit, b.id"""

        def sql = """ from Building b"""

        if(city){
            sql += """ and b.bd_city = '${city}'"""
        }

        if(area){
            sql += """ and b.bd_area = '${area}'"""
        }


        def osql = sqlorder("b", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Building.executeQuery(tsql + sql)[0].toLong()

        def items = Building.executeQuery(vsql + sql + osql, mypage).collect {
            [
                    bd_name:it[0], bd_developer:it[1], bd_city:it[2], bd_area:it[3], bd_address:it[4], bd_phone:it[5], bd_year:it[6], bd_greenrate:it[7], bd_volumnrate:it[8], bd_opentime:it[9], bd_type_tagid:it[10],
                    bd_price:it[11], bd_avgcash:it[12], bd_house:it[13], bd_agenttel:it[14], bd_typenum:it[15], bd_picnum:it[16], bd_focus:it[17], bd_view:it[18], bd_submit:it[19], bd_id:it[20]
            ]
        }

        render([numRows: total, items: items] as JSON)
    }


    def bd_add() {
        def typelist = Tag.executeQuery("select tag_name from Tag where tag_isbasic = 0 and tag_type = ${Enum_Tagtype.TT_bd_type.code} and tag_name not in('全部', '不限')")

        def taglist = Tag.executeQuery("select tag_name from Tag where tag_isbasic = 0 and tag_type = ${Enum_Tagtype.TT_bd_tag.code} and tag_name not in('全部', '不限')")

        def tmpins = [
                typelist:typelist,
                taglist:taglist,
                bd_opentime: new Date(),
                bd_avgcash:0,
                bd_price: 0,
                bd_greenrate: 50,
                bd_volumnrate: 50,
                bd_year: 70,
                bd_propcost:0,
                type_tag:[],
                bd_tag: []
        ]

        render(template: "/building/building_add", model: [bdins:tmpins])
    }

    def bd_basic_addlog(){
        def ret = [status: 0, msg: 'OK']
        def tmpbd = new Building()
        bindData(tmpbd,params,[exclude:['id']])
        tmpbd.save()

        def tmpba = new Building_around()
        tmpba.id = tmpbd.id

        tmpba.save(flush: true)

        ret.retid = tmpbd.id

        render(ret as JSON)
    }

    def bd_basic_editlog(){
        def ret = [status: 0, msg: 'OK']
        def tmpbd = Building.get(params.long('id',0L))
        if(!tmpbd){
            ret.status = 1
            ret.msg = "无效的楼盘"
            render(ret as JSON)
            return
        }

        bindData(tmpbd,params)
        tmpbd.save(flush: true)

        render(ret as JSON)
    }


    def bd_other_editlog(){
        def ret = [status: 0, msg: 'OK']
        def tmpbd = Building.get(params.long('bd_id',0L))
        if(!tmpbd){
            ret.status = 1
            ret.msg = "无效的楼盘"
            render(ret as JSON)
            return
        }

        bindData(tmpbd,params)
        tmpbd.save(flush: true)

        render(ret as JSON)
    }

    def bd_pro_editlog(){
        def ret = [status: 0, msg: 'OK']
        def tmpbd = Building.get(params.long('bd_id',0L))
        if(!tmpbd){
            ret.status = 1
            ret.msg = "无效的楼盘"
            render(ret as JSON)
            return
        }

        bindData(tmpbd,params)
        tmpbd.save(flush: true)

        render(ret as JSON)
    }

    def bd_rule_editlog(){
        def ret = [status: 0, msg: 'OK']
        def tmpbd = Building.get(params.long('bd_id',0L))
        if(!tmpbd){
            ret.status = 1
            ret.msg = "无效的楼盘"
            render(ret as JSON)
            return
        }

        bindData(tmpbd,params)
        tmpbd.save(flush: true)

        render(ret as JSON)
    }

    def bd_around_editlog(){
        def ret = [status: 0, msg: 'OK']
        def bdid = params.long('bd_id',0L)

        def tmpbd = Building_around.get(bdid)
        if(!tmpbd){
            if(!Building.read(bdid)){
                ret.status = 1
                ret.msg = "无效的楼盘"
                render(ret as JSON)
                return
            }

            tmpbd = new Building_around()
            tmpbd.id = bdid
        }

        bindData(tmpbd,params)
        tmpbd.save(flush: true)

        render(ret as JSON)
    }

    def bd_type_editlog(){
        def ret = [status: 0, msg: 'OK']
        def tmpbd = Building.get(params.long('bd_id',0L))
        if(!tmpbd){
            ret.status = 1
            ret.msg = "无效的楼盘"
            render(ret as JSON)
            return
        }

        long type_tagid = 0
        long bdtag_id = 0

        if(params.type_tag_content != ''){
            String[] typetags = params.type_tag_content.split('_')
            def typetag = typetags.sort().join('_')
            def tmp_typetag = Tag.findByTag_typeAndTag_name(Enum_Tagtype.TT_bd_type.code, typetag)
            if (!tmp_typetag) {
                tmp_typetag = new Tag()
                tmp_typetag.with {
                    tag_type = Enum_Tagtype.TT_bd_type.code
                    tag_name = typetag
                    tag_isbasic = typetags.length > 1 ? 1 : 0
                    save(flush: true)
                }
            }

            type_tagid = tmp_typetag.id
        }

        if(params.bd_tag_content != ''){
            String[] bdtags = params.bd_tag_content.split('_')
            def bdtag = bdtags.sort().join('_')
            def tmp_tag = Tag.findByTag_typeAndTag_name(Enum_Tagtype.TT_bd_tag.code, bdtag)
            if (!tmp_tag) {
                tmp_tag = new Tag()
                tmp_tag.with {
                    tag_type = Enum_Tagtype.TT_bd_tag.code
                    tag_name = bdtag
                    tag_isbasic = bdtags.length > 1 ? 1 : 0
                    save(flush: true)
                }
            }

            bdtag_id = tmp_tag.id
        }


        tmpbd.bd_type_tagid = type_tagid
        tmpbd.bd_tag_tagid =  bdtag_id
        tmpbd.save(flush: true)

        render(ret as JSON)
    }

    /*
    def bd_addlog() {
        long type_tagid = 0
        long bdtag_id = 0

        if(params.type_tag_content != ''){
            String[] typetags = params.type_tag_content.split('_')
            def typetag = typetags.sort().join('_')
            def tmp_typetag = Tag.findByTag_typeAndTag_name(Enum_Tagtype.TT_bd_type.code, typetag)
            if (!tmp_typetag) {
                tmp_typetag = new Tag()
                tmp_typetag.with {
                    tag_type = Enum_Tagtype.TT_bd_type.code
                    tag_name = typetag
                    tag_isbasic = typetags.length > 1 ? 1 : 0
                    save(flush: true)
                }
            }

            type_tagid = tmp_typetag.id
        }

        if(params.bd_tag_content != ''){
            String[] bdtags = params.bd_tag_content.split('_')
            def bdtag = bdtags.sort().join('_')
            def tmp_tag = Tag.findByTag_typeAndTag_name(Enum_Tagtype.TT_bd_tag.code, bdtag)
            if (!tmp_tag) {
                tmp_tag = new Tag()
                tmp_tag.with {
                    tag_type = Enum_Tagtype.TT_bd_tag.code
                    tag_name = bdtag
                    tag_isbasic = bdtags.length > 1 ? 1 : 0
                    save(flush: true)
                }
            }

            bdtag_id = tmp_tag.id
        }

        def tmpbd = new Building()
        bindData(tmpbd,params,[exclude:['id']])
        tmpbd.bd_type_tagid = type_tagid
        tmpbd.bd_tag_tagid =  bdtag_id
        tmpbd.save(flush: true)

        def tmpba = new Building_around()
        bindData(tmpba,params,[exclude:['id']])
        tmpba.id = tmpbd.id

        tmpba.save(flush: true)

        render(status: 204)
    }
    */

    def bd_viewlog() {
        def tmpbd = Building.read(params.long("bd_id", 0))
        if(!tmpbd) {
            render(template: "/building/building_add", model: [bdins: [:]])
            return
        }

        def tmpba = Building_around.read(tmpbd.id)


        def typetag = Tag.read(tmpbd.bd_type_tagid).tag_name?.split('_')
        def bdtag = Tag.read(tmpbd.bd_tag_tagid).tag_name?.split('_')

        def typelist = Tag.executeQuery("select tag_name from Tag where tag_isbasic = 0 and tag_type = ${Enum_Tagtype.TT_bd_type.code} and tag_name not in('全部', '不限')")

        def taglist = Tag.executeQuery("select tag_name from Tag where tag_isbasic = 0 and tag_type = ${Enum_Tagtype.TT_bd_tag.code} and tag_name not in('全部', '不限')")


        def tmpins = tmpbd.properties as HashMap
        tmpins.put('id',tmpbd.id)
        tmpins.put('bd_transport',tmpba?.bd_transport)
        tmpins.put('bd_commercial',tmpba?.bd_commercial)
        tmpins.put('bd_education',tmpba?.bd_education)
        tmpins.put('bd_entertain',tmpba?.bd_entertain)
        tmpins.put('baid', tmpba?.id)

        tmpins.put('taglist',taglist)
        tmpins.put('typelist',typelist)


        tmpins['type_tag'] = typetag?:[]
        tmpins['bd_tag'] = bdtag?:[]

        render(template: "/building/building_add", model: [bdins: tmpins])

    }

    def bduser_viewlog(){
        render(template: "/building/bduserlist", model: [bdins: [bdid: params.long("bd_id", 0)]])
    }

    /*

    def bd_editlog() {
        def tmpbd = Building.get(params.long('id',0L))
        if(!tmpbd){
            render(status: 501)
            return
        }

        long type_tagid = 0
        long bdtag_id = 0

        if(params.type_tag_content && params.type_tag_content != ''){
            String[] typetags = params.type_tag_content.split('_')
            def typetag = typetags.sort().join('_')
            def tmpbt = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_bd_type.code, typetag)
            tmpbt.tag_isbasic = typetags.length > 1 ? 1 : 0
            type_tagid = tmpbt.save()?.id
        }

        if(params.bd_tag_content && params.bd_tag_content != ''){
            String[] bdtags = params.bd_tag_content.split('_')
            def bdtag = bdtags.sort().join('_')
            def tmpbt = Tag.findOrCreateByTag_typeAndTag_name(Enum_Tagtype.TT_bd_tag.code, bdtag)
            tmpbt.tag_isbasic = bdtags.length > 1? 1 : 0
            bdtag_id = tmpbt.save()?.id
        }

        bindData(tmpbd,params)
        tmpbd.bd_type_tagid = type_tagid
        tmpbd.bd_tag_tagid =  bdtag_id

        long bd_id = tmpbd.save()?.id

        def tmpba = Building_around.read(bd_id)
        bindData(tmpba,params)

        tmpba.save(flush: true)

        render(status: 204)
    }
    */

    def bd_dellog() {
        Building.executeUpdate("delete  from Building where id=? ",[params.long('id', 0)])
        render(status: 204)
    }

    /*  楼盘列表  结束  */



    /*  优惠和公告    */

    def discountlist(){
        Integer reftype = params.int('reftype', -1)

        def reflabel = Enum_Reftype.byCode(reftype)?.name

        def dclabel = "优惠"


        def tmpins = [
                reftype: params.reftype,
                dctype:Enum_Discounttype.DT_discount_wait.code,
                refid: params.refid,
                reflabel: reflabel? reflabel : "平台",
                dclabel:  dclabel
        ]
        render(template: "/building/discount_list", model: [dcins:tmpins])
    }

    def newslist(){
        Integer reftype = params.int('reftype', -1)

        def reflabel = Enum_Reftype.byCode(reftype)?.name

        def dclabel = "公告"

        def tmpins = [
                reftype: params.reftype,
                dctype:Enum_Discounttype.DT_news_wait.code,
                refid: params.refid,
                reflabel: reflabel? reflabel : "平台",
                dclabel:  dclabel
        ]
        render(template: "/building/news_list", model: [dcins:tmpins])
    }


    def ds_discount_list(){
        def dc_reftype = params.int('reftype', -1)
        def dc_type = params.int('dc_type', -1)

        def refid = params.int('refid', -1)

        if(session.userorgid > 0){
            refid = session.userorgid
        }

        def tsql = "select count(*) "
        def vsql = """select d.dc_refid, d.dc_title, d.dc_fromtime, d.dc_endtime, d.id, d.dc_reftype, d.dc_type
                    """

        def sql = """ from Discount d where 1 = 1 """

        if(dc_reftype != -1){
            sql += """ and d.dc_reftype = ${dc_reftype}"""
        }

        if(dc_type != -1){
            sql += """ and d.dc_type = ${dc_type}"""
        }

        if(refid != -1){
            sql += """ and d.dc_refid = ${refid}"""
        }else{
            sql += """ and d.dc_refid <> 0"""
        }

        def osql = sqlorder("d", "id", "desc")

        def mypage = [readOnly: true]
        if (params.start?.isInteger()){
            mypage.put("offset", params.int('start'))
        }
        mypage.put("max", params.int('count')?:16)

        def total = Discount.executeQuery(tsql + sql)[0].toLong()

        def items = Discount.executeQuery(vsql + sql + osql, mypage).collect {
            def sponsor = "多乐檬"

            if(it[0] != 0){
                def tmpenum = Enum_Reftype.byCode(it[5])
                Class destdomain = grailsApplication.getClassForName(tmpenum?.ref_domain)

                sponsor = destdomain.read(it[0])?."${tmpenum.ref_field}"
            }

            [
                    dc_refid:it[0], dc_title:it[1], dc_fromtime:it[2],  dc_endtime:it[3],
                    id:it[4], dc_reftype:it[5], dc_type:it[6], ref_sponsor:sponsor
            ]
        }

        render([numRows: total, items: items] as JSON)
    }


    def discount_add() {
        Integer dctype = params.int('dctype', Enum_Discounttype.DT_discount_wait.code)

        def dclabel = "优惠"

        if(dctype != Enum_Discounttype.DT_discount_wait.code){
            dclabel = "公告"
        }

        Integer reftype = params.int('reftype', Enum_Reftype.RT_building.code)

        def refid = params.refid
        if(session.userorgid > 0){
            refid = session.userorgid
        }

        def now = new Date()

        def tmpdis = [
                dc_fromtime: now,
                dc_type:dctype,
                refid: refid,
                dc_reftype: reftype,
                dclabel: dclabel
        ]

        switch (reftype){
            case Enum_Reftype.RT_building.code:
                tmpdis.dc_ds_url = "/building/ds_bdname_list"
                break
            case Enum_Reftype.RT_car.code:
                tmpdis.dc_ds_url = "/query/ds_carname_list"
                break
        }

        use(TimeCategory) {
            tmpdis.dc_endtime = now + 30.days
        }


        render(template: "/building/discount_add", model: [disins:tmpdis])
    }

    def discount_addlog() {
        def tmpdc = new Discount(params)

        if (tmpdc.validate()) {
            tmpdc.save(flush: true)
            render(status: 204)
        }else {
            render(status: 500)
        }

    }

    def discount_viewlog() {
        def tmpdis = Discount.read(params.long("discount_id", 0))
        def tmpins = tmpdis.properties as HashMap

        tmpins.put('dclabel', Enum_Reftype.byCode(tmpdis.dc_reftype).name)
        def itemurl = "/building/ds_bdname_list"

        switch (tmpdis.dc_reftype){
            case Enum_Reftype.RT_building.code:
                itemurl = "/building/ds_bdname_list"
                break
            case Enum_Reftype.RT_car.code:
                itemurl = "/query/ds_carname_list"
                break
        }
        tmpins.put('dc_ds_url', itemurl)
        tmpins.put('id', tmpdis.id)
        tmpins.put('refid', tmpdis.dc_refid.toString())

        render(template: "/building/discount_add", model: [disins:tmpins])
    }

    def discount_editlog() {

        def tmpdis = Discount.get(params.long('id',0L))
        if(!tmpdis){
            render(status: 501)
            return
        }

        bindData(tmpdis, params)
        tmpdis.save(flush: true)

        render(status: 204)
    }

    def discount_dellog() {
        Discount.executeUpdate("delete  from Discount where id=? ",[params.long('id', 0)])
        render(status: 204)
    }

    def discount_setlog(){
        def tmpdc = Discount.get(params.long('id', 0))
        if(!tmpdc){
            render(status: 501)
            return
        }

        def setval = params.int('setvalue', Enum_Discounttype.DT_news_no.code)
        tmpdc.dc_type = setval

        tmpdc.save(flush: true)

        render(status: 204)

        //发送短信给关注此楼盘车行的客户
        def reftype = params.int('reftype', Enum_Reftype.RT_building.code)
        def refid = params.long('refid', -1)

        def shop_name = null
        def sms_type = 0

        if(reftype == Enum_Reftype.RT_building.code){
            shop_name = Building.read(refid)?.bd_name
            sms_type = Enum_Smstype.ST_bddiscount.code
        }

        if(reftype == Enum_Reftype.RT_car.code){
            shop_name = Car_bank.read(refid)?.cb_name
            sms_type = Enum_Smstype.ST_cardiscount.code
        }

        def shar_url = grailsApplication.config.domain +"/web/open/${tmpdc.id}"

        if(shop_name){
            if(setval in [Enum_Discounttype.DT_discount_ok.code, Enum_Discounttype.DT_news_ok.code]){
                Cst_favor.findAllByFavor_refidAndFavor_reftype(refid, reftype).collect {
                    sms_sendcode(sms_type, [shop_name, shar_url], Cst_basic.read(it.favor_cstid).cst_mobile)
                }
            }
        }
    }

    /*    楼盘优惠结束    */

    /*  优惠 审核  */
    def dcchecklist(){
        //Integer dctype = params.int('dctype', -1)
        Integer reftype = params.int('reftype', -1)

        def reflabel = Enum_Reftype.byCode(reftype)?.name

        def dclabel = "优惠"

        def tmpins = [
                reftype: params.reftype,
          //      dctype:params.dctype,
                refid: params.refid,
                reflabel: reflabel? reflabel : "平台",
                dclabel:  dclabel
        ]
        render(template: "/building/dccheck_list", model: [dcins:tmpins])
    }

    /*  公告 审核  */
    def newschecklist(){
        //Integer dctype = params.int('dctype', -1)
        Integer reftype = params.int('reftype', -1)

        def reflabel = Enum_Reftype.byCode(reftype)?.name

        def dclabel = "公告"

        def tmpins = [
                reftype: params.reftype,
                //dctype:params.dctype,
                refid: params.refid,
                reflabel: reflabel? reflabel : "平台",
                dclabel:  dclabel
        ]
        render(template: "/building/newscheck_list", model: [dcins:tmpins])
    }

    def ds_bdname_list() {
        def itemlist = Building.executeQuery("select b.id, concat(b.bd_name, ' —— ', b.bd_city) as bd_cname from Building b").collect {
            [id: it[0], name:it[1]]
        }

        render([identifier: "id", label: "name", items: itemlist] as JSON)

    }

}
