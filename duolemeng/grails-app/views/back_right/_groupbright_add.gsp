<div data-dojo-type="dijit/layout/ContentPane"  style="overflow-y: auto;">
<form data-dojo-type="dijit/form/Form" id="frm_groupbright"
      data-dojo-props="onValidStateChange:function(formIsValid){dijit.byId('btn_groupbright_save').set('disabled', !formIsValid);}">
    <script type='dojo/on' data-dojo-event="show" data-dojo-args="evt">
        dijit.byId('frm_groupbright').validate();
    </script>

    <input type="text" data-dojo-type="dijit/form/TextBox" value="${gbins?.group_id}"
            data-dojo-id="group_id" name="group_id" style="display: none;"/>

    <label class="mleft">群组名称：</label>
    <input type="text" data-dojo-type="dijit/form/ValidationTextBox"
           id="group_name" name="group_name"
           data-dojo-props="myorg:'${gbins?.group_name}'"
           value="${gbins?.group_name}"
           required="true" trim="true" maxlength="20"
           invalidMessage=''
           missingMessage=''
           validator='return func_vld_common(this.value, "group_name", "back_right/groupname_exist?");'
    />

    <br/><br/>

%{--
    <label for="group_note" class="mleft">群组备注：</label>
    <textarea  data-dojo-type="dijit/form/SimpleTextarea" style="resize: none;width: 44em"
              id="group_note" name="group_note"  class="mheight"
               maxlength="200" >${gbins?.group_note}</textarea>
--}%

    <g:render template="/back_right/basicright" />

<button type="button" data-dojo-type="dijit/form/Button" data-dojo-props="iconClass:'dijitIconSave'"
        id="btn_groupbright_save"  class="mleft" style="margin-left: 3em">保存
    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
        this.set("disabled", true);
        func_dlgsave(group_id, "back_right", "groupbright");
    </script>
</button>


<button type="button" data-dojo-type="dijit/form/Button" data-dojo-props="iconClass:'dijitIconUndo'"
        id="btn_groupbright_reset" class="mleft" >取消
    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
        dijit.byId('dlg_groupbright').hide();
    </script>
</button>


<button type="button" data-dojo-type="dijit/form/Button" id="btn_groupbright_edit"  class="mleft" data-dojo-props="iconClass:'dijitIconEdit'"
        disabled="disabled">修改
    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
        disableall('frm_groupbright',false);
        dijit.byId('btn_groupbright_edit').set('disabled', true);
    </script>
</button>

</form>
</div>