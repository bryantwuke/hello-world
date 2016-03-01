package appgw

import com.taobao.api.DefaultTaobaoClient
import com.taobao.api.TaobaoClient
import com.taobao.api.request.OpenSmsSendvercodeRequest
import com.taobao.api.response.OpenSmsSendvercodeResponse

class BaichuanController {

    def sms_vercode() {
        TaobaoClient client = new DefaultTaobaoClient(grailsApplication.config.baichuan.url,
                grailsApplication.config.baichuan.appkey, grailsApplication.config.baichuan.secret)

        OpenSmsSendvercodeRequest req = new OpenSmsSendvercodeRequest();
        OpenSmsSendvercodeRequest.SendVerCodeRequest objSendVerCodeRequest = new OpenSmsSendvercodeRequest.SendVerCodeRequest()
        // objSendVerCodeRequest.setExpireTime(123L);
        // objSendVerCodeRequest.setSessionLimit(123L);
        // objSendVerCodeRequest.setDeviceLimit(123L);
        // objSendVerCodeRequest.setDeviceLimitInTime(123L);
        // objSendVerCodeRequest.setMobileLimit(123L);
        // objSendVerCodeRequest.setSessionLimitInTime(123L);
        // objSendVerCodeRequest.setExternalId("12345");
        // objSendVerCodeRequest.setMobileLimitInTime(123L);
        objSendVerCodeRequest.setTemplateId(290902781L)
        objSendVerCodeRequest.setSignatureId(1378L)
        // objSendVerCodeRequest.setSessionId("demo");
        // objSendVerCodeRequest.setDomain("demo");
        // objSendVerCodeRequest.setDeviceId("demo");
        objSendVerCodeRequest.setMobile("13530829600")
        //objSendVerCodeRequest.setContext("125796")
        objSendVerCodeRequest.setContextString("{\"code\":\"125796\"}")
        // objSendVerCodeRequest.setVerCodeLength(4L);
        //objSendVerCodeRequest.setSignature("多乐檬")
        req.setSendVerCodeRequest(objSendVerCodeRequest)
        OpenSmsSendvercodeResponse rsp = client.execute(req)
        println(rsp.body)
        //System.out.println(rsp.getBody());

        render(0)
    }

}
