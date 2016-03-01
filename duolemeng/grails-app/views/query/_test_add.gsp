<form data-dojo-type="dijit/form/Form" id="frm_test" onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_test_save').set('disabled', !isValid);}">

    <input data-dojo-type="dijit/form/TextBox"
           id="test_id" name="id" style="display: none;"
           value="${testins.id}"/>

    %{--<br/>--}%

    <span data-dojo-type="dojo/data/ItemFileReadStore" data-dojo-id="ds_subject_list" data-dojo-props="url:'appgw/subjectlist'"></span>

    <span class="mleft">课程名称</span>
    <select data-dojo-type="dijit/form/Select" data-dojo-props="store:ds_subject_list"  maxHeight="200" style="width: 15em"
            name="test_subjectid"  value="${testins.test_subjectid}" required="required">
    </select>

    <span class="mright">题目类型</span>
    <select  data-dojo-type="dijit/form/Select" name="test_type" maxHeight="180"
             value="${testins.test_type}" style="width: 15em">
        <g:each in="${roomsale.Enum_Testtype.values()}">
            <option value="${it.code}">${it.name}</option>
        </g:each>
    </select>

    <br/><br/>

    <span class="mleft">题目标题</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50"
               required="true" trim="true" name="test_title" value="${testins.test_title}" class="mlwidth"
               missingMessage="标题不能为空"/>

    <br/><br/><br/><br/>

    <span class="mleft">选择项A</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="30"
       required="true" trim="true" name="test_optiona" value="${testins.test_optiona}" placeholder="判断题(正确或者错误)"
       missingMessage="选项不能为空"/>

    <span class="mright">选择项B</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="30"
       required="true" trim="true" name="test_optionb" value="${testins.test_optionb}" placeholder="判断题(正确或者错误)"
       missingMessage="选项不能为空"/>

    <br/><br/>

    <span class="mleft">选择项C</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="30" placeholder="判断题此项不需要填写"
       trim="true" name="test_optionc" value="${testins.test_optionc}"
       missingMessage="选项不能为空"/>

    <span class="mright">选择项D</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="30"  placeholder="判断题此项不需要填写"
       trim="true" name="test_optiond" value="${testins.test_optiond}"
       missingMessage="选项不能为空"/>

    <br/><br/>

    <span class="mleft">选择项E</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="30"  placeholder="判断题此项不需要填写"
       trim="true" name="test_optione" value="${testins.test_optione}"
       missingMessage="选项不能为空"/>

    <span class="mright">选择项F</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="30"  placeholder="判断题此项不需要填写"
       trim="true" name="test_optionf" value="${testins.test_optionf}"
       missingMessage="选项不能为空"/>

    <br/><br/><br/>

    <span class="mleft">正确答案</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="6" class="mlwidth"  placeholder="abcd"
               required="true" trim="true" name="test_answer"  value="${testins.test_answer}" placeholder="AB"
               missingMessage="答案不能为空"/>
    </div>

    <br/><br/><br/><br/>
    <button data-dojo-type="dijit/form/Button" id="btn_test_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 7em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                func_dlgsavegrid("test_id", "query", "test", "grid_test");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_test_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_test').hide();
        </script>
    </button>

    <button data-dojo-type="dijit/form/Button" id="btn_test_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_test',false);
            dijit.byId('btn_test_edit').set('disabled', true);
        </script>

    </button>

</form>


