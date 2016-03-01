<form data-dojo-type="dijit/form/Form" id="frm_subject" onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_subject_save').set('disabled', !isValid);}">

    <input data-dojo-type="dijit/form/TextBox" name="id"
           id="subject_id" value="${subins.id}" style="display: none"
    />

    <span class="mleft">课程名称:</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"
           trim="true" name="subject_name" value="${subins.subject_name}" required="true" style="width: 16em"
    />

    <br/><br/><br/>

    <button data-dojo-type="dijit/form/Button" id="btn_subject_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 4em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                func_dlgsavegrid("subject_id", "query", "subject", "grid_subject");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_subject_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_subject').hide();
        </script>
    </button>

    <button data-dojo-type="dijit/form/Button" id="btn_subject_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_subject',false);
            dijit.byId('btn_subject_edit').set('disabled', true);
        </script>

    </button>

</form>


