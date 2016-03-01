
<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_file_list"
     data-dojo-props="url:'file/ds_file_list?file_refclass=${fileins.file_refclass}&file_refid=${fileins.file_refid}'"></div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 20em; width: 98%">
    <div id="grid_filelist" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="store:ds_file_list,
                structure:[
                {name:'文件ID', field:'file_id', width:'4em'},
                {name:'文件名', fields:['file_orgname', 'file_id'], width:'100%', formatter:fmt_file_down},
                {name:'文件类型', field:'file_type', width:'6em'},
                {name:'文件大小(KB)', field:'file_size',  width:'8em'},
                {name:'操作', field:'file_id',  width:'4em', formatter:fmt_comfile_del}
                ],
                plugins:{pagination:{pageSizes:[8, 16, 'all'], defaultPageSize: 8}}"
         canSort="return false;"
    >
    </div>
</div>

<br />

<form data-dojo-type="dijit/form/Form" method="post" style="width: 100%; height: 10em" action="file/fileupload" enctype="multipart/form-data" >
%{--
    <script type="dojo/on" data-dojo-event="show" data-dojo-args="evt">
        <g:if test="${fileins.file_canopt == 0}">
            file_disupload(4);
        </g:if>
    </script>
--}%

    <input data-dojo-type="dijit/form/TextBox" value="${fileins.file_refclass}"
           name="file_refclass"  style="display: none;"  />

    <input type="text" data-dojo-type="dijit/form/TextBox" value="${fileins.file_refid}"
           name="file_refid" style="display: none;"/>

    %{--
        <input type="text" data-dojo-type="dijit/form/TextBox" value="${fileins.file_bindgrid}"
               id="file_bindgrid" style="display: none;"/>
    --}%

    <input type="text" data-dojo-type="dijit/form/TextBox"
           id="file_ondelete" style="display: none;"
           onchange="{
               dijit.byId('${fileins.file_refgrid}')._refresh();
           };"
    />

    <input class="mleft" name="uploadfile" type="file"    multiple="true" contenteditable="false"
           data-dojo-type="dojox.form.Uploader" label="选择文件" id="uploadfile"
           data-dojo-props="iconClass:'dijitIconFolderOpen'"
           onchange="smt_upload.set('disabled', false);"
           oncomplete="{
               dijit.byId('grid_filelist')._refresh();
               smt_upload.set('disabled', true); this.reset();
               dijit.byId('${fileins?.file_refgrid}')._refresh();
               alert('文件上传成功！');
           }"
    />
    <input type="submit" label="上传文件" class="mleft" disabled="disabled" data-dojo-id="smt_upload"
           data-dojo-type="dijit/form/Button" data-dojo-props="iconClass:'dijitIconSearch'"/>


    <button type="button" data-dojo-type="dijit/form/Button" data-dojo-props="iconClass:'dijitIconDelete'"
            class="mleft" >关闭
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            dijit.byId('dlg_file').hide();
        </script>
    </button>

        <div data-dojo-type="dojox/form/uploader/FileList" uploaderId="uploadfile"></div>

</form>
