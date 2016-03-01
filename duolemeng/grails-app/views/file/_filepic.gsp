<form method="post" style="width: 100%; height: 10em" action="file/single_upload"
      enctype="multipart/form-data" >
    <input type="text" data-dojo-type="dijit/form/TextBox" value="${fileins?.file_refclass}"
           name="file_refclass" style="display: none;"/>

    <input type="text" data-dojo-type="dijit/form/TextBox" value="${fileins?.file_refid}"
           name="file_refid" style="display: none;"/>

    <input type="text" data-dojo-type="dijit/form/TextBox" value="${fileins?.file_refdomain}"
           id="file_refdomain" style="display: none;"/>

    <br />

    <div data-dojo-type="dojox/form/uploader/FileList" uploaderId="picselect" lang="zh-cn"></div>

    <br />

    <input class="mleft" name="uploadfile" type="file" multiple="false"
           data-dojo-type="dojox.form.Uploader" label="选择文件" id="picselect"
           data-dojo-props="iconClass:'dijitIconFolderOpen'"
           onchange="{
               var filename = this.getFileList()[0].name;
               var filetype = filename.substring(filename.lastIndexOf('.')+1);
               switch(filetype) {
                   case 'png':
                   case 'bmp':
                   case 'jpg':
                   case 'jpeg':
                   case 'gif':
                       pic_upload.set('disabled', false);
                       break;
                   default :
                       pic_upload.set('disabled', true);
                       alert('请选择png、bmp、jpg、jpeg、gif格式的图片文件');
                       break;
               }
           }"
           oncomplete="{
               var domain = dijit.byId('file_refdomain').get('value');
               dijit.byId('grid_'+domain).store.close();
               dijit.byId('grid_'+domain)._refresh();
               dijit.byId('dlg_'+domain).hide();
               alert('文件上传成功！');
           }"
    />
    <button type="submit" class="mleft" disabled="disabled" data-dojo-id="pic_upload"
            data-dojo-type="dijit/form/Button" data-dojo-props="iconClass:'dijitIconSearch'">上传文件
    </button>

    <button type="button" data-dojo-type="dijit/form/Button"
            data-dojo-props="iconClass:'dijitIconDelete'"
            class="mleft" >关闭
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
        var domain = dijit.byId('file_refdomain').get('value');
        dijit.byId('dlg_'+domain).hide();
        </script>
    </button>


</form>
