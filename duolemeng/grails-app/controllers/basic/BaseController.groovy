package basic
import com.taobao.api.DefaultTaobaoClient
import com.taobao.api.TaobaoClient
import com.taobao.api.request.OpenSmsSendmsgRequest
import com.taobao.api.response.OpenSmsSendmsgResponse
import grails.converters.JSON
import service.Back_str
import service.Enum_Smstype
import service.Enum_backstr
import service.Sms_send

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BaseController {

    final String FS_ismobile = "^1[1-9]\\d{9}\$"

    private chkfile_etag(HttpServletRequest request, HttpServletResponse respons, fileid) {
        def etag = "ht${fileid}"
        def ifNoneMatchHeader = request.getHeader('If-None-Match')
        if(ifNoneMatchHeader && ifNoneMatchHeader == etag) {
            response.status = 304
            response.flushBuffer()
            return false
        }
        response.setHeader('ETag',etag)
        return true
    }

    private paramsort(String defsort, Integer defmax=16, String deforder=null) {
        if (params.start?.isInteger()){
            params.put("offset", params.int('start'))
        }

        params.put("max", params.int('count')?:defmax)
        if (params.sort?.startsWith("-")){
            params.put("sort", params.sort.substring(1))
            params.put("order", "desc")
        }
        if (params.sort==null){
            params.put("sort", defsort)
            if (deforder) {
                params.put("order", deforder)
            }
        }
    }

    private sqlorder(String deftable, String deffield, String deforder=null) {
        if (params.sort?.startsWith("-")){
            return " order by ${deftable}.${params.sort.substring(1)} desc "
        }else if(params.sort) {
            return " order by ${deftable}.${params.sort.substring(0)} "
        }else {
            if (deforder) {
                return " order by ${deftable}.${deffield} ${deforder} "
            }else {
                return " order by ${deftable}.${deffield} "
            }
        }

    }

    private sms_sendcode(Integer type, List data, String mobile) {
        def content = ""
        Long sms_tempid = 0
        def sms_data = [:]

        switch(type) {
            case Enum_Smstype.ST_chkmobile.code:
//                手机号验证
                content = "尊敬的用户，验证码为：${data[0]} ，验证码有效时间30分钟（请妥善保管好您的信息，勿将机密信息告知他人）。"
            sms_tempid = 2345
            sms_data = ["data0" : data[0]]

                break
            case Enum_Smstype.ST_findpwd.code:
//                找回密码
                content = "尊敬的用户，您本次找回密码的验证码为：${data[0]} ，验证码有效时间30分钟（请妥善保管好您的信息，勿将机密信息告知他人）。"
                sms_tempid = 2346
                sms_data = ["data0" : data[0]]

                break
            case Enum_Smstype.ST_invite.code:
//                发送邀请短信
                content = "您的朋友${data[0]}，邀请您加入多乐檬，猛戳下面的地址${data[1]}，现在就加入吧！"
                sms_tempid = 2347
                sms_data = ["data0" : data[0], "data1": data[1]]

                break
            case Enum_Smstype.ST_setmobile.code:
//                更换手机号码
                content = "尊敬的用户，您本次更改手机号码的验证码为：${data[0]} ，验证码有效时间30分钟（请妥善保管好您的信息，勿将机密信息告知他人）。"
                sms_tempid = 2348
                sms_data = ["data0" : data[0]]

                break
            case Enum_Smstype.ST_findcspwd.code:
                content = "尊敬的用户，您本次重置提现密码的验证码为：${data[0]} ，验证码有效时间30分钟（请妥善保管好您的信息，勿将机密信息告知他人）。"
                sms_tempid = 2349
                sms_data = ["data0" : data[0]]

                break

            case Enum_Smstype.ST_bddiscount.code:
                content = "尊敬的用户，您关注的楼盘(${data[0]})有新的活动拉， 点击下面的地址: ${data[1]} 查看吧!"
                sms_tempid = 2350
                sms_data = ["data0": data[0], "data1": data[1]]

                break

            case Enum_Smstype.ST_cardiscount.code:
                content = "尊敬的用户，您关注的车行(${data[0]})有新的活动拉， 点击下面的地址: ${data[1]} 查看吧!"
                sms_tempid = 2351
                sms_data = ["data0": data[0], "data1": data[1]]

                break

            case Enum_Smstype.ST_realname.code:
                content = "尊敬的用户，您已通过实名认证。 请重新登录，开启新的体验吧!"
                sms_tempid = 2353

                break
        }

        // content = "【多乐檬】" + content

        def now = new Date()

        def tnow = now.format("MM月dd日HH:mm")
        def sendtime = now.format("yyyyMMddHHmmss")

        def tmsg = new Sms_send()
        tmsg.with {
            sms_mobile = mobile
            sms_time = now
            sms_type = type
            sms_content = content
            save(flush: true)
        }

        //判断发送短信途径
        def smsopen = Back_str.read(Enum_backstr.BS_sms.code).value

        if (smsopen == "1") {
            // def smsurl = "http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SDK-SKY-010-02477&pwd=07CC71DF32744F66A3CEEAD70F44D637&mobile=${mobile}&content=${URLEncoder.encode(content, "UTF-8")}&ext=&stime=&rrid=&msgfmt="
            // def smsurl = "http://sdk.entinfo.cn:8060/z_mdsmssend.aspx?sn=SDK-YIB-010-00050&pwd=C07D7956478036E00B5C282FD9C53651&mobile=${mobile}&content=${URLEncoder.encode(content, "GB2312")}&ext=&rrid=&stime="
            // MysmsJob.triggerNow([smsurl:smsurl])
            def jsondata = new JSON(sms_data).toString()
            // println(jsondata)

            sms_baichuan(sms_tempid, mobile, jsondata)
        }


/*
        switch (sendway) {
            case Enum_smsoper.SO_bf.code:
                //百分
                smsurl = "http://sdk.entinfo.cn:8060/z_mdsmssend.aspx?sn=SDK-YIB-010-00050&pwd=C07D7956478036E00B5C282FD9C53651&mobile=${mobile}&content=${URLEncoder.encode(content, "GB2312")}&ext=&rrid=&stime="
                break
            case Enum_smsoper.SO_kyt.code:
                //快易通
                smsurl = "http://sdk2.entinfo.cn:8061/mdsmssend.ashx?sn=SDK-SKY-010-02477&pwd=07CC71DF32744F66A3CEEAD70F44D637&mobile=${mobile}&content=${URLEncoder.encode(content, "UTF-8")}&ext=&stime=&rrid=&msgfmt="
                break
        }
*/

    }

    // 生成唯一团码
    private generate_uid(){
        return (new Date().getTime() * 17)
    }

    //hex 解码
    private hextostr(String hexstr){
        def hexarr = hexstr.decodeHex()
        def sb = new StringBuilder()

        for(def item in hexarr){
            sb.append((char)item)
        }

        return sb.toString()
    }

    private sms_baichuan(Long templatedid, String mobile, String data) {
        TaobaoClient client = new DefaultTaobaoClient(grailsApplication.config.baichuan.url,
                grailsApplication.config.baichuan.appkey, grailsApplication.config.baichuan.secret)

        OpenSmsSendmsgRequest  req = new OpenSmsSendmsgRequest ()
        OpenSmsSendmsgRequest.SendMessageRequest objSendMessageRequest = new OpenSmsSendmsgRequest.SendMessageRequest()

        objSendMessageRequest.setTemplateId(templatedid)
        objSendMessageRequest.setSignatureId(grailsApplication.config.baichuan.signid)
        objSendMessageRequest.setMobile(mobile)
        objSendMessageRequest.setContext(data)
        // objSendMessageRequest.setExternalId("2211111122")
        // objSendMessageRequest.setDeviceLimit(123L);
        // objSendMessageRequest.setSessionLimit(123L);
        // objSendMessageRequest.setDeviceLimitInTime(123L);
        // objSendMessageRequest.setMobileLimit(123L);
        // objSendMessageRequest.setSessionLimitInTime(123L);
        // objSendMessageRequest.setMobileLimitInTime(123L);
        // objSendMessageRequest.setSessionId("demo");
        // objSendMessageRequest.setDomain("demo");
        // objSendMessageRequest.setDeviceId("demo");
        // objSendMessageRequest.setDelayTime(123L);

        req.setSendMessageRequest(objSendMessageRequest)
        OpenSmsSendmsgResponse rsp = client.execute(req)

        if (rsp.result.successful == false) {
            println("sms send failed. ${rsp.body}")
        }

        // println(rsp.body)
    }

}
