<form data-dojo-type="dijit/form/Form" id="frm_agent" onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_agent_save').set('disabled', !isValid);}">


    <input data-dojo-type="dijit/form/TextBox"
           id="user_id" name="id" style="display: none;"
           value="${agtins.id}"/>

    %{--<br/>--}%
    <span class="mleft">用户名字</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50"
        required="true" trim="true" name="agent_name" value="${agtins.agent_name}"
        missingMessage="名字不能为空"/>

    <span class="mright">用户手机</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"  regExp="^1[1-9]\d{9}$" invalidMessage="请输入正确的手机号码"
           required="true" trim="true" name="agent_mobile" placeholder="用于登录" value="${agtins.agent_mobile}" data-dojo-id="agent_mobile"
           missingMessage="用于登录不能为空"/>

    <br/><br/><br/>


    <span class="mleft">用户密码</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50" data-dojo-id="agent_passwd"
           required="true" trim="true" name="agent_passwd" type="password"
           missingMessage="密码不能为空"/>

    <span class="mright">提现密码</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50" data-dojo-id="agent_cashpwd"
           trim="true" name="agent_cashpwd" type="password"
           missingMessage="提现密码不能为空"/>


    <br/><br/><br/>

    <span class="mleft">身份证号</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"
           trim="true"   name="agent_idcard" value="${agtins.agent_idcard}"/>

    <span class="mright">用户地址</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50"
           trim="true"   name="agent_address" value="${agtins.agent_address}"/>


    <br/><br/><br/><br/>

    <button data-dojo-type="dijit/form/Button" id="btn_agent_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 7em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                var h2 = ''
                var cspwd = ''

                var md5 = require('dojox/encoding/digests/MD5');

                if(agent_passwd.value == ''){
                    if(!confirm("你还没有设置密码,确定不需要设置密码吗？")){
                         return;
                     }

                }else{
                    h2 = md5(agent_passwd.value + 'duolemeng', 1);
                }

                if(agent_cashpwd.value == ''){
                    if(!confirm("你还没有设置提现密码,确定不需要设置吗？")){
                         return;
                     }
                }else{
                    cspwd = md5(agent_cashpwd.value + 'duolemeng', 1);
                }

                agent_passwd.set("value", h2);
                agent_cashpwd.set('value', cspwd);

                func_dlgsavegrid("user_id", "query", "agent", "grid_agent");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_agent_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_agent').hide();
        </script>
    </button>

    <button data-dojo-type="dijit/form/Button" id="btn_agent_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_agent',false);
            dijit.byId('frm_agent').validate();
            dijit.byId('btn_agent_edit').set('disabled', true);
        </script>

    </button>

</form>


