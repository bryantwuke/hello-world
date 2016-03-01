
%{-- invisible div --}%
<div style="display: none">
    <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_user_list">
    </div>

    <div data-dojo-type="dijit/layout/ContentPane">
        <div id="grid_user" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
             data-dojo-props="
         store:ds_user_list,
                structure:[]">
        </div>
    </div>

</div>


<form data-dojo-type="dijit/form/Form" id="frm_user" onshow="disableall('frm_user',true);dijit.byId('btn_user_edit').set('disabled', false);"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_user_save').set('disabled', !isValid);}" style="text-align: center">


    <input data-dojo-type="dijit/form/TextBox"
           id="user_id" name="id" style="display: none;"
           value="${userins.id}"/>

    %{--<br/>--}%
    <div data-dojo-type="dijit/layout/TabContainer" style="height: 34em; width: 100%" tabPosition="top"  >

        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"基本信息"'>

            <br/><br/><br/>

            <span class="mleft">用户名字</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="10" readonly="readonly"
                   required="true" trim="true" name="user_name" value="${userins.user_name}"
                   missingMessage="名字不能为空"/>

            <span class="mright">登陆名字</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="10" readonly="readonly"
                   required="true" trim="true" name="user_loginname" value="${userins.user_loginname}"
                   missingMessage="登陆名不能为空"/>

            <br/><br/><br/><br/>

            <span class="mleft">用户手机</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"  regExp="^1[1-9]\d{9}$" invalidMessage="请输入正确的手机号码"
                   required="true" trim="true" name="user_mobile" value="${userins.user_mobile}" readonly="readonly"
                   missingMessage="手机号不能为空"/>

            <span class="mright">身份证号</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength=20"
                   required="true" trim="true" name="user_idcard" value="${userins.user_idcard}"
                   missingMessage="身份证不能为空"/>

            <br/><br/><br/><br/>
            <span class="mleft">用户密码</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50" data-dojo-id="user_pwd" type="password"
                   trim="true" name="user_pwd"
                   missingMessage="密码不能为空"/>

            <span class="mright">用户地址</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50"
                   trim="true" name="user_address" value="${userins.user_address}"/>

        </div>

        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"其它信息"'>
            <br/><br/><br/>
            <span class="mleft">用户职务</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"
                   trim="true" name="user_title" value="${userins.user_title}"/>

            <span class="mright">所在部门</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"
                   trim="true" name="user_depname" value="${userins.user_depname}"/>

            <br/><br/><br/><br/>

            <span class="mleft">QQ/微信</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"
                   trim="true" name="user_im" value="${userins.user_im}"/>

            <span class="mright">用户Email</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"
                   regExp="\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"
                   invalidMessage="无效的Email"
                   trim="true" name="user_email" value="${userins.user_email}"/>

            <br/><br/><br/><br/>

            <span class="mleft">用户备注</span>
            <br/>
            <textarea name="user_note" maxlength="200" style="height: 120px;width: 50em" trim="true" class="mleft">${userins.user_note}</textarea>



        </div>
    </div>

    <br/><br/>

    <button data-dojo-type="dijit/form/Button" id="btn_user_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 7em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                var h2 = ''

                var md5 = require('dojox/encoding/digests/MD5');

                if(user_pwd.value == ''){
                    if(!dijit.byId('user_id').get('value')){
                        alert("请输入密码");
                        user_pwd.focus();
                        return;
                    }

                    %{--if(!confirm("你还没有设置密码,确定不需要修改密码吗？")){--}%
                         %{--return;--}%
                     %{--}--}%

                }else{
                    h2 = md5(user_pwd.value + 'duolemeng', 1);
                }



                user_pwd.set("value", h2);

                func_dlgsavegrid("user_id", "back_user", "user", "grid_user");

                disableall('frm_user',true);
                dijit.byId('btn_user_edit').set('disabled', false);

        </script>

    </button>


    <button data-dojo-type="dijit/form/Button" id="btn_user_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_user',false);
            dijit.byId('frm_user').validate();
            dijit.byId('btn_user_edit').set('disabled', true);
        </script>

    </button>

</form>


<div data-dojo-type="dijit/Dialog" id="dlg_user" title="查看用户信息" style="display: none; height: 34em; width: 70em;">
</div>


