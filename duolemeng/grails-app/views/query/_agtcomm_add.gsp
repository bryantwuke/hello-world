<form data-dojo-type="dijit/form/Form" id="frm_agtcomm" onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_agtcomm_save').set('disabled', !isValid);}">

    <input data-dojo-type="dijit/form/TextBox" name="id"
           id="agent_id" value="${agtins.agent_id}" style="display: none"
    />

    <input data-dojo-type="dijit/form/TextBox"
           id="tmp_agt_id"  style="display: none"
    />

    <span class="mleft">发放类型:</span>
    <select  data-dojo-type="dijit/form/Select" name="cap_type" maxHeight="180"
             style="width: 16em">
        <g:each in="${roomsale.Enum_Captype.values()}">
            <g:if test="${it.code in [roomsale.Enum_Captype.CT_inrelease_adduser.code, roomsale.Enum_Captype.CT_inrelease_roomok.code, roomsale.Enum_Captype.CT_inrelease_roomdeal.code, roomsale.Enum_Captype.CT_inrelease_carok.code, roomsale.Enum_Captype.CT_inrelease_cardeal.code, roomsale.Enum_Captype.CT_out_cash.code]}">

            </g:if>
            <g:else>
                <option value="${it.code}">${it.name}</option>
            </g:else>

        </g:each>
    </select>

    <span class="mright">发放金额:</span>
    <input data-dojo-type="dijit/form/NumberTextBox" invalidMessage="填入正确的正整数值" constraints="{ min:1, places:0}" missingMessage="发放金额不能为空"
           trim="true" name="cap_num" required="required"
    />

    <br/><br/><br/>

    <button data-dojo-type="dijit/form/Button" id="btn_agtcomm_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 10em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                func_dlgsavegrid("tmp_agt_id", "query", "agtcomm", "grid_agtcomm");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_agtcomm_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_agtcomm').hide();
        </script>
    </button>

    <button data-dojo-type="dijit/form/Button" id="btn_agtcomm_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_agtcomm',false);
            dijit.byId('btn_agtcomm_edit').set('disabled', true);
        </script>

    </button>

</form>


