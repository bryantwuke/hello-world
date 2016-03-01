<form data-dojo-type="dijit/form/Form" id="frm_bank" onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_bank_save').set('disabled', !isValid);}">

    <input data-dojo-type="dijit/form/TextBox" name="id"
           id="bank_id" value="${bankins.id}" style="display: none"
    />


    <span style="margin-left: 3em">标识前缀</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="10"
           trim="true" name="bt_prefix" value="${bankins.bt_prefix}" required="true" style="width: 24em"
    />

    <br/><br/><br/>

    <span style="margin-left: 3em">银行名称</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50"
           trim="true" name="bt_name" value="${bankins.bt_name}" required="true" style="width: 24em"
    />

    <br/><br/><br/><br/>

    <button data-dojo-type="dijit/form/Button" id="btn_bank_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 4em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                func_dlgsavegrid("bank_id", "back_setting", "bank", "grid_bank");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_bank_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_bank').hide();
        </script>
    </button>

    <button data-dojo-type="dijit/form/Button" id="btn_bank_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_bank',false);
            dijit.byId('btn_bank_edit').set('disabled', true);
        </script>

    </button>

</form>
