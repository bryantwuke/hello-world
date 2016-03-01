<form data-dojo-type="dijit/form/Form" id="frm_banner" onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_banner_save').set('disabled', !isValid);}">

    <input data-dojo-type="dijit/form/TextBox"
           id="banner_id" name="id" style="display: none;"
           value="${bnins.id}"/>

    %{--<br/>--}%

    <span class="mleft">URL</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="100" name="banner_url" style="width: 25em"
           trim="true"   value="${bnins.banner_url}"
           />
    <br/><br/>

    <br/><br/><br/>
    <button data-dojo-type="dijit/form/Button" id="btn_banner_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="false"
            style="margin-left: 7em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">

                func_dlgsavegrid("banner_id", "back_setting", "banner", "grid_filelist");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_banner_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_banner').hide();
        </script>
    </button>

    <button data-dojo-type="dijit/form/Button" id="btn_banner_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_banner',false);
            dijit.byId('btn_banner_edit').set('disabled', true);
        </script>

    </button>

</form>


