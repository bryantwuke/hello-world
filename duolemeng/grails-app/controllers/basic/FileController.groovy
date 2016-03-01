package basic

import grails.converters.JSON
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.commons.CommonsMultipartFile
import roomsale.Enum_Agentstatus
import roomsale.Enum_Fileclass
import roomsale.Sys_file
import service.Back_str
import service.Banner
import service.Enum_backstr


class FileController extends BaseController {

    def load() {
        def filetmp = Sys_file.read(params.long("id", 0))
        if (!filetmp){
            render(status: 204)
            return
        }

/*
        if (!(filetmp.file_class in [FileClass.HCD_loanicon.code, FileClass.HCD_loanimg.code,
                                     FileClass.HCD_pubimg.code, FileClass.HCD_eventweb.code, FileClass.HCD_eventphone.code,
                                     FileClass.HCD_webbanner.code, FileClass.HCD_phonebanner.code])) {
            render(status: 204)
            return
        }
*/

        def file = new File(filetmp.file_dir + File.separatorChar + filetmp.id.toString())
        if (!file.exists()){
            render(status: 204)
            return
        }

        if (chkfile_etag(request, response, filetmp.id)) {

            /// open the fie save dialog
            String codedfilename = new String(filetmp.file_orgname.getBytes(), "ISO8859-1")
            if (request.getHeader("User-Agent").indexOf("MSIE") >= 0) {
                codedfilename = URLEncoder.encode(filetmp.file_orgname, "UTF-8").replace("+", "%20")
            }
            /// response.setHeader("Content-disposition", "attachment; filename=\"${codedfilename}\"")
            response.setHeader("Content-disposition", "inline; filename=\"${codedfilename}\"")

            if(filetmp.file_type.toLowerCase() in ["jpg", "png", "bmp", "gif", "jpeg"]) {
                response.contentType = 'image/'+filetmp.file_type
            }else {
                response.contentType = 'application/'+filetmp.file_type
            }

            /// response.contentType = 'application/'+filetmp.file_type
            response.setHeader('Cache-Control','public, s-maxage=31536000, max-age=31536000')

            try {
                response.outputStream << file.getBytes()
                response.flushBuffer()
            } catch(e) {
                log.debug("/file/load download failed", e)
            }
        }

    }

    def fileupload() {
        if ((!session.userid)) {
            redirect controller: 'user'
            return false
        }

        def filelog = null
        def desfile = null

        def refclass = params.int('file_refclass', 0)
        def refid = params.long('file_refid', 0)
        def fileenum = Enum_Fileclass.byCode(refclass)
        def typepos = null

        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest)request;
        List<CommonsMultipartFile> files = mpr.multiFileMap.collect { it.value }.flatten()
        def retitem = files.collect {
            if (!it.size) return

            filelog = new Sys_file()
            filelog.file_refid = refid
            filelog.file_refclass = refclass
            filelog.file_size =(it.size/1024).toFloat().round(2)
            typepos = it.originalFilename.lastIndexOf('.')
            if (typepos >= 0){
                filelog.file_type = it.originalFilename.substring(typepos+1, it.originalFilename.length()).toLowerCase()
            }
            if (it.originalFilename.length()>50){
                Integer sublen = 50 - filelog.file_type.length() -1  // 自动截取超长文件名
                filelog.file_orgname = it.originalFilename.substring(0, sublen) + '.' + filelog.file_type
            }else {
                filelog.file_orgname = it.originalFilename
            }

            filelog.file_dir = grailsApplication.config.mydir +
                    File.separator + fileenum.key + File.separator + refid
            filelog.validate()

            filelog.save(flush: true)

            /*  如果上传的图片为banner图片 (refid = 0)  */
            if(refid == 0 && refclass == Enum_Fileclass.FC_banner.code){
                def tmpbn = new Banner()
                tmpbn.with {
                    banner_fileid = filelog.id
                    save(flush: true)
                }
            }


            desfile = new File(filelog.file_dir + File.separator + filelog.id.toString())
            if (!desfile.exists()) {
                desfile.parentFile.mkdirs()
                desfile.createNewFile()
            }

            it.transferTo(desfile)
            [file: it.originalFilename, status:'success']
        }

        if(refid == 0){
            render(retitem as JSON)
            return
        }

        // update file nums value
        if (fileenum?.refdomain) {
            Class destdomain = grailsApplication.getClassForName(fileenum.refdomain)
            def desttmp = destdomain.lock(refid)
            if(fileenum?.ref_fieldname) {
                desttmp."${fileenum.ref_fieldname}" = Sys_file.countByFile_refidAndFile_refclass(refid, refclass)
            }else{
                desttmp.file_num = Sys_file.countByFile_refidAndFile_refclass(refid, refclass)
            }
            desttmp.save(flush: true)
        }

        render(retitem as JSON)
    }

    def single_upload() {
        def retval = [status : 0]

        if ((!session.userid) && !(session.agentid)) {
            retval.status = -1
            retval.msg = "未登陆"
            render(retval as JSON)
            return
        }

        def filelog = null
        def desfile = null
        def refclass = params.int('file_refclass', 0)
        def fileenum = Enum_Fileclass.byCode(refclass)
        def refid = params.long('file_refid', 0)
        def typepos = null

        //  first delete the old file
        if(refclass in [Enum_Fileclass.FC_usericon.code, Enum_Fileclass.FC_useridcard.code, Enum_Fileclass.FC_startpic.code]) {
            Sys_file.findByFile_refclassAndFile_refid(refclass, refid)?.delit()
        }

        MultipartHttpServletRequest mpr = (MultipartHttpServletRequest)request;
        List<CommonsMultipartFile> files = mpr.multiFileMap.collect { it.value }.flatten()

        retval.retitem = files.collect {
            if (!it.size) {
                return
            }

            filelog = new Sys_file()
            filelog.file_refid = refid
            filelog.file_refclass = refclass
            filelog.file_size =(it.size/1024).toFloat().round(2)
            typepos = it.originalFilename.lastIndexOf('.')
            if (typepos >= 0){
                filelog.file_type = it.originalFilename.substring(typepos+1, it.originalFilename.length()).toLowerCase()
            }
            if (it.originalFilename.length()>50){
                Integer sublen = 50 - filelog.file_type.length() -1  // 自动截取超长文件名
                filelog.file_orgname = it.originalFilename.substring(0, sublen) + '.' + filelog.file_type
            }else {
                filelog.file_orgname = it.originalFilename
            }

            filelog.file_dir = grailsApplication.config.mydir +
                    File.separator + fileenum.key + File.separator + refid
            filelog.save(flush: true)

            desfile = new File(filelog.file_dir + File.separator + filelog.id.toString())
            if (!desfile.exists()) {
                desfile.parentFile.mkdirs()
                desfile.createNewFile()
            }

            it.transferTo(desfile)

            [file: it.originalFilename, status:'success',file_id:filelog.id]
        }

        //如果上传的是启动图片
        if(refclass == Enum_Fileclass.FC_startpic.code){
            def tmppic = Back_str.get(Enum_backstr.RS_startpic.code)
            if(!tmppic){
                tmppic = new Back_str()
                tmppic.id = Enum_backstr.RS_startpic.code
            }

            tmppic.value = Long.toString(filelog.id)
            tmppic.save(flush: true)

            render retval as JSON
            return
        }

        // update file id value
        if (fileenum?.refdomain) {
            Class destdomain = grailsApplication.getClassForName(fileenum.refdomain)
            def desttmp = destdomain.lock(refid)
            if(fileenum?.ref_fieldname) {
                desttmp."${fileenum.ref_fieldname}" = filelog.id
            }else {
                desttmp.user_iconid = filelog.id
            }

            /* 如果上传的是身份证照片,则更新用户的状到待审核状态 */
            if(fileenum.code == Enum_Fileclass.FC_useridcard.code){
                desttmp.agent_status = Enum_Agentstatus.AS_idcard_going.code
            }

            desttmp.save(flush: true)
        }

        if(refid == 0 && refclass == Enum_Fileclass.FC_conimg.code){ //如果是后台详情图片
            def ret = [
                    file: filelog.id, name: filelog.file_orgname, width:200, height:160,type:filelog.file_type, size:filelog.file_size
            ] as JSON

            render ("<textarea>"+ret +"</textarea>")
            return
        }
        render retval as JSON

    }


    def filelist() {
        if ((!session.userid)) {
            redirect controller: 'user'
            return false
        }

        def filetmp = [file_refid:params.long('refid', 0)]
        filetmp.file_refclass = params.int('refclass', 0)
        /// filetmp.file_canopt = params.int('canopt', 0)

        filetmp.file_refgrid = Enum_Fileclass.byCode(filetmp.file_refclass)?.refgrid

        render(template: '/file/filecommon', model: [fileins:filetmp])
        /*

        def filetmp = [file_refid:params.long('refid', 0)]
        filetmp.file_refclass = params.int('refclass', 0)
        /// filetmp.file_canopt = params.int('canopt', 0)

        def fileenum = Enum_Fileclass.byCode(filetmp.file_refclass)
        filetmp.file_refgrid = fileenum?.refgrid

        filetmp.file_writable = 1;  //有没有对这些文件进行操作的权限

        if(fileenum.refdomain) {
            //如果是用户头像或者是商铺图片，则看session.userid==tmpfile.file_refid
            Class destdomain = grailsApplication.getClassForName(fileenum.refdomain)

            if ((fileenum.code == Enum_Fileclass.FC_usericon.code || fileenum.code == Enum_Fileclass.FC_userpic.code)) {
                if (session.userid != filetmp.file_refid) {
                    filetmp.file_writable = 0;
                }
            } else { //如果是发布的信息的相关图片，则查看session.userid是否等于发布者id (from_userid)
                def tmpfromid = destdomain.read(filetmp.file_refid)?.from_userid

                if (tmpfromid != session.userid) {
                    filetmp.file_writable = 0;
                }
            }
        }

        render(template: '/file/filecommon', model: [fileins:filetmp])
        */
    }

    def ds_file_list() {
        if ((!session.userid)) {
            redirect controller: 'user'
            return false
        }

        def refclass = params.int('file_refclass', 0)
        def refid = params.long('file_refid', 0)
        int reqfilenum = params.int('reqnum',-1)
        if(reqfilenum < 0){
            reqfilenum = 8
        }

        if(reqfilenum != 0) {
            paramsort("id", reqfilenum)
        }

        def total = Sys_file.countByFile_refclassAndFile_refid(refclass, refid)
        def items = Sys_file.findAllByFile_refclassAndFile_refid(refclass, refid, params).collect {
            [
                    file_id : it.id,
                    file_orgname : it.file_orgname,
                    file_size : it.file_size,
                    file_type : it.file_type
            ]
        }

        render([numRows: total,items: items] as JSON)
    }

    def file_dellog() {
        if ((!session.userid)) {
            redirect controller: 'user'
            return false
        }

        def ret = [status: 0,msg: 'OK']

        def tmpfile = Sys_file.read(params.long('id', 0))

        if (tmpfile) {
            def fileenum = Enum_Fileclass.byCode(tmpfile.file_refclass)

            tmpfile.delit()

            if(tmpfile.file_refid == 0){
                Banner.executeUpdate("delete  from Banner where banner_fileid=? ",[params.long('id', 0)])
                render(ret as JSON)
                return
            }
            // update file nums value
            if (fileenum?.refdomain) {
                Class destdomain = grailsApplication.getClassForName(fileenum.refdomain)
                def desttmp = destdomain.lock(tmpfile.file_refid)
                if(fileenum?.ref_fieldname) {
                    desttmp."${fileenum.ref_fieldname}" = Sys_file.countByFile_refidAndFile_refclass(tmpfile.file_refid, tmpfile.file_refclass)
                }else {
                    desttmp.file_num = Sys_file.countByFile_refidAndFile_refclass(tmpfile.file_refid, tmpfile.file_refclass)
                }
                desttmp.save(flush: true)
            }

        }


        render(ret as JSON)

        /*

        def ret = [status: 0,msg: 'OK']
        /*
        if(!session.userid){
            ret.status = 1;
            ret.msg = "用户未登录"
            render (ret as JSON)
            return
        }

        def tmpfile = Sys_file.read(params.long('id', 0))
        if(!tmpfile){
            ret.status = 2;
            ret.msg = "图片不存在"
            render (ret as JSON)
            return
        }

        //查看用户是否有删除图片的权限，用户只能删除自己的头像，自己的店铺的文件，自己发布的小区公告/社区活动/房屋出租的图片

        def fileenum = Enum_Fileclass.byCode(tmpfile.file_refclass)

        Class destdomain = null

        if(fileenum.refdomain){
            //如果是用户头像或者是商铺图片，则看session.userid==tmpfile.file_refid
            destdomain = grailsApplication.getClassForName(fileenum.refdomain)

            if((fileenum.code == Enum_Fileclass.FC_usericon.code || fileenum.code == Enum_Fileclass.FC_userpic.code)){
                if(session.userid != tmpfile.file_refid) {
                    ret.status = 3;
                    ret.msg = "没有删除权限"
                    render(ret as JSON)
                    return
                }
            }else{ //如果是发布的信息的相关图片，则查看session.userid是否等于发布者id (from_userid)
                def tmpobj = destdomain.read(tmpfile.file_refid)
                def tmpfromid = tmpobj.from_userid
                tmpobj.save();

                if(tmpfromid != session.userid){
                    ret.status = 4;
                    ret.msg = "没有删除权限"
                    render (ret as JSON)
                    return
                }

            }
        }


        tmpfile.delit()

        // update file nums value
        if (fileenum?.refdomain) {
            def desttmp = destdomain.lock(tmpfile.file_refid)
            if(fileenum?.ref_fieldname) {
                desttmp."${fileenum.ref_fieldname}" = Sys_file.countByFile_refidAndFile_refclass(tmpfile.file_refid, tmpfile.file_refclass)
            }else {
                desttmp.file_num = Sys_file.countByFile_refidAndFile_refclass(tmpfile.file_refid, tmpfile.file_refclass)
            }
            desttmp.save(flush: true)
        }

        render(ret as JSON)

        */

    }

    def agent_add() {
        if ((!session.userid)) {
            redirect controller: 'user'
            return false
        }

        def filetmp = [:]

        filetmp.file_refid = params.long('icon_agtid', -1)
        filetmp.file_refclass = Enum_Fileclass.FC_usericon.code

        if(filetmp.file_refid == -1){
            filetmp.file_refid = params.long('idicon_agtid', 0)
            filetmp.file_refclass = Enum_Fileclass.FC_useridcard.code
        }

        filetmp.file_refdomain = "agent"

        render(template: '/file/filepic', model: [fileins:filetmp])
    }


    def startpic_add() {
        if ((!session.userid)) {
            redirect controller: 'user'
            return false
        }

        def filetmp = [:]

        filetmp.file_refid = Enum_backstr.RS_startpic.code
        filetmp.file_refclass = Enum_Fileclass.FC_startpic.code

        filetmp.file_refdomain = "startpic"

        render(template: '/file/filepic', model: [fileins:filetmp])
    }

}
