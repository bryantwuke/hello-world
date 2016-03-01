<form data-dojo-type="dijit/form/Form" id="frm_note" onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_note_save').set('disabled', !isValid);}">

    <input data-dojo-type="dijit/form/TextBox"
           id="note_id" name="id" style="display: none;"
           value="${noteins.id}"/>

    %{--<br/>--}%

    <span class="mleft">公告标题</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50" name="note_title"
           required="true" trim="true" value="${noteins.note_title}"
           missingMessage="公告标题不能为空"/>

    <span class="mright">公告类型</span>
    <select  data-dojo-type="dijit/form/Select" name="note_type" maxHeight="180"
             value="${noteins.note_type}" style="width: 12em">
        <g:each in="${roomsale.Enum_Reftype.values()}">
            <option value="${it.code}">${it.name}公告</option>
        </g:each>
    </select>

    <br/><br/>


    <span class="mleft">起始时间</span>
    <input data-dojo-type="dijit/form/DateTextBox" name="note_fromtime" value="${noteins.note_fromtime?.format('yyyy-MM-dd')}"
           lang="zh-cn" hasDownArrow="false" constraints="{datePattern:'yyyy-MM-dd'}"
    />

    <span class="mright" >结束时间</span>
    <input name="note_endtime" data-dojo-type="dijit/form/DateTextBox" value="${noteins.note_endtime?.format('yyyy-MM-dd')}"
           lang="zh-cn" hasDownArrow="false" constraints="{datePattern:'yyyy-MM-dd'}"
    />
    <br/><br/>

    <span class="mleft">公告内容</span>
    <textArea data-dojo-type="dijit/form/SimpleTextarea" maxlength="200" name="note_content"  trim="true"
              style="resize: none; width: 45.5em; height: 10em;">${noteins.note_content}</textArea>

    <br/><br/><br/>
    <button data-dojo-type="dijit/form/Button" id="btn_note_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 7em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                func_dlgsavegrid("note_id", "back_setting", "note", "grid_note");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_note_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_note').hide();
        </script>
    </button>

    <button data-dojo-type="dijit/form/Button" id="btn_note_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_note',false);
            dijit.byId('btn_note_edit').set('disabled', true);
        </script>

    </button>

</form>


