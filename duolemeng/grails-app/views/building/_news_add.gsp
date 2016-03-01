<form data-dojo-type="dijit/form/Form" id="frm_news" onshow="this.validate();"
      data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_news_save').set('disabled', !isValid);}">

    <input data-dojo-type="dijit/form/TextBox"
           id="news_id" name="id" style="display: none;"
           value="${newsins.id}"/>

    <input data-dojo-type="dijit/form/TextBox"
           name="news_reftype" style="display: none;"
           value="${newsins.news_reftype}"/>

    %{--<br/>--}%

    <span class="mleft">动态标题</span>
    <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="100" name="news_title"
           required="true" trim="true" value="${newsins.news_title}"
           missingMessage="动态标题不能为空"/>

    <span data-dojo-type="dojo/data/ItemFileReadStore" data-dojo-id="ds_bdname_list" data-dojo-props="url:'${newsins.news_ds_url}'"></span>
    <span class="mleft">发起对象: </span>
    <select data-dojo-type="dijit/form/Select" data-dojo-props="store:ds_bdname_list"  maxHeight="200" style="width: 15em"
            name="news_refid"  value="${newsins.news_refid}" required="required">
    </select>
    <br/><br/>

    <input type="hidden" data-dojo-type="dijit/form/TextBox" name="news_content" id="news_content" required="false" />
    <div data-dojo-type="dijit/Editor" id="myeditor"
         data-dojo-props="extraPlugins:['createLink', 'unlink', 'insertImage', 'foreColor', 'hiliteColor', 'fontSize', 'viewsource', 'autourllink',
         {name: 'LocalImage', uploadable: true, uploadUrl: '/file/single_upload?file_refid=0&file_refclass=${roomsale.Enum_Fileclass.FC_conimg.code}', baseImageUrl: '/file/load/', fileMask: '*.jpg;*.jpeg;*.gif;*.png;*.bmp'}]">
        ${raw(newsins.news_content)}
    </div>

    <br/><br/><br/>
    <button data-dojo-type="dijit/form/Button" id="btn_news_save"
            data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
            style="margin-left: 7em">保存
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                dojo.byId("news_content").value = dijit.byId("myeditor").getValue();

                func_dlgsavegrid("news_id", "building", "news", "grid_news");
        </script>

    </button>

    <button type="button" data-dojo-type="dijit/form/Button" id="btn_news_reset"
            data-dojo-props="iconClass:'dijitIconUndo'"
            class="mleft">取消
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_news').hide();
        </script>
    </button>

    <button data-dojo-type="dijit/form/Button" id="btn_news_edit"
            data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
            class="mleft">修改
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            disableall('frm_news',false);
            dijit.byId('btn_news_edit').set('disabled', true);
        </script>

    </button>

</form>


