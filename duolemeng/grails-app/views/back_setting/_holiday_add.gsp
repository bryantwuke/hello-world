<form data-dojo-type="dijit/form/Form" id="frm_holiday" onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_holiday_save').set('disabled', !isValid);}">

    <input data-dojo-type="dijit/form/TextBox"
           id="holiday_id" name="id" style="display: none;"
           value="${hdayins.id}"/>

    %{--<br/>--}%

    <span class="mleft">节日名称</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50" name="holiday_name"
           required="true" trim="true" value="${hdayins.holiday_name}"
           missingMessage="节日名称不能为空"/>


    <span class="mright">节日日期</span>
    <input data-dojo-type="dijit/form/DateTextBox" name="holiday_day" value="${hdayins.holiday_day?.format('yyyy-MM-dd')}" required="required"
           lang="zh-cn" hasDownArrow="false" constraints="{datePattern:'yyyy-MM-dd'}"
    />

    <br/><br/>

    <span class="mleft">参考祝福</span>
    <textArea data-dojo-type="dijit/form/SimpleTextarea" maxlength="200" name="holiday_greet"  trim="true"
              style="resize: none; width: 45.5em; height: 10em;">${hdayins.holiday_greet}</textArea>

    <br/><br/><br/>
    <button data-dojo-type="dijit/form/Button" id="btn_holiday_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 7em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                func_dlgsavegrid("holiday_id", "back_setting", "holiday", "grid_holiday");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_holiday_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_holiday').hide();
        </script>
    </button>

    <button data-dojo-type="dijit/form/Button" id="btn_holiday_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_holiday',false);
            dijit.byId('btn_holiday_edit').set('disabled', true);
        </script>

    </button>

</form>


