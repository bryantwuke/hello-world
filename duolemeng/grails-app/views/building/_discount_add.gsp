<form data-dojo-type="dijit/form/Form" id="frm_discount"
%{--
      onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_discount_save').set('disabled', !isValid);}"
--}%
>

    <input data-dojo-type="dijit/form/TextBox"
           id="discount_id" name="id" style="display: none;"
           value="${disins.id}"/>

    <input data-dojo-type="dijit/form/TextBox"
           name="dc_reftype" style="display: none;"
           value="${disins.dc_reftype}"/>

    <input data-dojo-type="dijit/form/TextBox"
           name="dc_type" style="display: none;"
           value="${disins.dc_type}"/>



    <span class="mleft">活动标题</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="100" name="dc_title" data-dojo-id="dc_title"
           required="true" trim="true"  class="mlwidth" value="${disins.dc_title}"
           missingMessage="优惠标题不能为空"/>
    <br/><br/>


    <span class="mleft">起始时间</span>
    <input data-dojo-type="dijit/form/DateTextBox" name="dc_fromtime" value="${disins.dc_fromtime?.format('yyyy-MM-dd')}"
           lang="zh-cn" hasDownArrow="false" constraints="{datePattern:'yyyy-MM-dd'}"
    />

    <span class="mright" >结束时间</span>
    <input name="dc_endtime" data-dojo-type="dijit/form/DateTextBox" value="${disins.dc_endtime?.format('yyyy-MM-dd')}"
           lang="zh-cn" hasDownArrow="false" constraints="{datePattern:'yyyy-MM-dd'}"
    />
    <br/><br/>

    <g:if test="${!disins.refid}">

        <span data-dojo-type="dojo/data/ItemFileReadStore" data-dojo-id="ds_bdname_list" data-dojo-props="url:'${disins.dc_ds_url}'"></span>
        <span class="mleft">发起对象</span>
        <select data-dojo-type="dijit/form/Select" data-dojo-props="store:ds_bdname_list"  maxHeight="200" style="width: 15em"
            name="dc_refid"  value="${disins.dc_refid}" required="required">
        </select>
    </g:if>
    <g:else>
        <input data-dojo-type="dijit/form/TextBox"
               name="dc_refid" style="display: none;"
               value="${disins.refid}"/>
    </g:else>


    <input type="hidden" data-dojo-type="dijit/form/TextBox" name="dc_content" id="dc_content" required="false" />
    <div data-dojo-type="dijit/Editor" id="myeditor"
               data-dojo-props="extraPlugins:['createLink', 'unlink', 'insertImage', 'foreColor', 'hiliteColor', 'fontSize', 'viewsource', 'autourllink',
             {name: 'LocalImage', uploadable: true, uploadUrl: '/file/single_upload?file_refid=0&file_refclass=${roomsale.Enum_Fileclass.FC_conimg.code}', baseImageUrl: '/file/load/', fileMask: '*.jpg;*.jpeg;*.gif;*.png;*.bmp'}  ]">
    ${raw(disins.dc_content)}
</div>


    <br/><br/><br/>
    <button data-dojo-type="dijit/form/Button" id="btn_discount_save"
            data-dojo-props="iconClass:'dijitIconSave'"
            style="margin-left: 7em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                if(!dc_title.value){
                    alert("标题不能为空");
                    dc_title.focus();
                    return;
                }

                dojo.byId("dc_content").value = dijit.byId("myeditor").getValue();

                func_dlgsavegrid("discount_id", "building", "discount", "grid_discount_todo");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_discount_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_discount').hide();
        </script>
    </button>


    <button data-dojo-type="dijit/form/Button" id="btn_discount_edit"
                data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
                class="mleft">修改
            <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_discount',false);
            dijit.byId('btn_discount_edit').set('disabled', true);
        </script>

        </button>


</form>


