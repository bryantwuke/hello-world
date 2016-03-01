<form data-dojo-type="dijit/form/Form" id="frm_tag" onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_tag_save').set('disabled', !isValid);}">

    <input data-dojo-type="dijit/form/TextBox" name="id"
          id="tag_id" value="${tagins.id}" style="display: none"
    />

    <input data-dojo-type="dijit/form/TextBox" name="tag_type"
            value="${tagins.tag_type}" style="display: none"
    />

    <span class="mleft">${tagins.tag_label}:</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="256"
           trim="true" name="tag_name" value="${tagins.tag_name}" required="true" style="width: 16em"
    />

    <br/><br/><br/>

    <button data-dojo-type="dijit/form/Button" id="btn_tag_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 4em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                func_dlgsavegrid("tag_id", "building", "tag", "grid_tag");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_tag_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_tag').hide();
        </script>
    </button>

    <button data-dojo-type="dijit/form/Button" id="btn_tag_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_tag',false);
            dijit.byId('btn_tag_edit').set('disabled', true);
        </script>

    </button>

</form>


