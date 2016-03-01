
<form data-dojo-type="dijit/form/Form" id="frm_admin" onshow="disableall('frm_admin',true);dijit.byId('btn_admin_edit').set('disabled', false);"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_admin_save').set('disabled', !isValid);}" style="text-align: center">

    <br/><br/><br/><br/>

    <span class="mleft">管理员名字</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="10"
           required="true" trim="true" name="admin_name" value="${userins.admin_name}"
           missingMessage="用户名不能为空"/>

    <br/><br/><br/><br/>

    <span class="mleft">管理员手机</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"  regExp="^1[1-9]\d{9}$" invalidMessage="请输入正确的手机号码"
           required="true" trim="true" name="admin_mobile" value="${userins.admin_mobile}"
           missingMessage="手机号不能为空"/>

    <br/><br/><br/><br/>
    <span class="mleft">管理员密码</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50" data-dojo-id="admin_passwd" type="password"
           trim="true" name="admin_passwd"
           missingMessage="密码不能为空"/>


    <br/><br/><br/><br/>

    <button data-dojo-type="dijit/form/Button" id="btn_admin_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 7em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                var h2 = ''

                var md5 = require('dojox/encoding/digests/MD5');

                if(admin_passwd.value != ''){
                   h2 = md5(admin_passwd.value + 'duolemeng', 1);
                }

                admin_passwd.set("value", h2);

                dojo.xhrPost({
                    url:'/back_user/admin_edit',
                    handleAs:'json',
                    form: 'frm_admin',
                    load:function (data) {
                        switch(data) {
                            case 0:
                                alert('保存成功');
                                disableall('frm_admin',true);
                                dijit.byId('btn_admin_edit').set('disabled', false);
                                break
                            case 1:
                                alert("用户名或者手机号为空");
                                break

                        }
                        admin_passwd.set("value", '');
                    }
                });

        </script>

    </button>


    <button data-dojo-type="dijit/form/Button" id="btn_admin_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
              disableall('frm_admin',false);
              dijit.byId('frm_admin').validate();
              dijit.byId('btn_admin_edit').set('disabled', true);
         </script>

    </button>

</form>


<div data-dojo-type="dijit/Dialog" id="dlg_user" title="查看用户信息" style="display: none; height: 34em; width: 70em;">
</div>


