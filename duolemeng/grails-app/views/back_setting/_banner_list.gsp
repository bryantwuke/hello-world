<script>
    function func_zoomer(id){
        require(["dojo/dom-attr"], function(domAttr){
            domAttr.set('banner_pic_zoomer', 'src', "/file/load/"+id);
        });
        dijit.byId("dlg_pic").show();
    }
    function func_show_pic(id){
        var src="/file/load/"+id;
        return "<a href='javascript:void(0)' onclick='func_zoomer(" + id + "); return false;'><img src=\"" + src + "\" width=\"40\" height=\"40\"/></a>";
    }

    function fmt_event_opt (fields){
        var label = "启用";
        var setval = 0;

        if(fields[1] == 0){
            setval = 1;
            label = "停用";
        }


        var fmtstr ="<a href='javascript:void(0)' onclick='func_url_set(" + fields[2] + ", \"grid_discount\"); return false;'>设置URL</a>";
        fmtstr +="<a href='javascript:void(0)' onclick='func_file_del(" + fields[0] + "); return false;' style='margin-left:1em;' >删除</a>";
        fmtstr +="<a href='javascript:void(0)' onclick='func_banner_set(" + fields[0] + ",\""+ label+"\"," + setval +"); return false;' style='margin-left:1em;'>"+label+"</a>";


        return fmtstr;

    }

    function func_url_set(id) {
        func_ajaxdlgview("back_setting/banner_viewlog", "banner", id, "设置URL");
    }

    function func_banner_set(value,msg,setvalue){
        func_ajaxdel_set("back_setting/banner_setlog", 'banner_id', value, 'grid_filelist',msg,setvalue);
    }

    function func_banner_sel(value){
        ds_file_list.url = "back_setting/ds_bn_list?vtype="+value;
        dijit.byId('grid_filelist')._refresh();
    }

</script>


<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_banner_list"
     data-dojo-props="url:'back_setting/index'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px;display: none">
    <div id="grid_banner" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_banner_list,
                structure:[
                ]">
    </div>
</div>


%{-- br --}%
<label class="mlabel">banner图片列表</label>

    <select  data-dojo-type="dijit/form/Select" maxHeight="180"
              style="width: 12em" onchange="func_banner_sel(this.value)">
        <option value="0">正在使用</option>
        <option value="1">已停用</option>
        <option value="2">全部</option>
    </select>


<div id="pic_gallery" style="width: 100%;">

    <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_file_list"
         data-dojo-props="url:'back_setting/ds_bn_list'"></div>

    <div data-dojo-type="dijit/layout/ContentPane" style="height: 60em; width: 98%">
        <div id="grid_filelist" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
             data-dojo-props="store:ds_file_list,
                structure:[
                {name:'图片', fields:['file_id'], width:'10em', formatter:func_show_pic},
                {name:'跳转的URL', fields:['banner_url'], width:'100%'},
                {name:'操作',fields:['file_id', 'banner_valid', 'id'],width:'11em',formatter:fmt_event_opt}
                ],
                plugins:{pagination:{pageSizes:[16, 32, 'all'], defaultPageSize: 16}}"
             canSort="return false;"
        >
        </div>
    </div>

    <div data-dojo-type="dijit/Dialog" id="dlg_pic"  style="display: none; height: 445px; width:715px;border: none;padding: 0px" title="查看大图片">
        <div style="width: 100%;height: 100%;border: none">
            <img src="/file/load/" id="banner_pic_zoomer"  width="700px" height="400px" style="padding: 0px;margin: 0px"/>
        </div>
    </div>

    <br />

    <form data-dojo-type="dijit/form/Form" method="post" style="width: 100%; height: 10em" action="file/fileupload" enctype="multipart/form-data" >
        <input data-dojo-type="dijit/form/TextBox" value="${roomsale.Enum_Fileclass.FC_banner.code}"
               name="file_refclass"  style="display: none;"  />

        <input type="text" data-dojo-type="dijit/form/TextBox" value="0"
               name="file_refid" style="display: none;"/>


        <input type="text" data-dojo-type="dijit/form/TextBox"
               id="file_ondelete" style="display: none;"
               onchange="{
                   dijit.byId('grid_banner')._refresh();
               };"
        />


        <input class="mleft" name="uploadfile" type="file"    multiple="true"
               data-dojo-type="dojox.form.Uploader" label="选择文件" id="uploadfile"
               data-dojo-props="iconClass:'dijitIconFolderOpen'"
               onchange="smt_upload.set('disabled', false);"
               oncomplete="{
               dijit.byId('grid_filelist')._refresh();
               smt_upload.set('disabled', true); this.reset();
               dijit.byId('grid_banner')._refresh();
               alert('文件上传成功！');
           }"
        />


        <input type="submit" label="上传文件" class="mleft" disabled="disabled" data-dojo-id="smt_upload"
               data-dojo-type="dijit/form/Button" data-dojo-props="iconClass:'dijitIconSearch'"/>

        <div data-dojo-type="dojox/form/uploader/FileList" uploaderId="uploadfile"></div>
    </form>


</div>

<div data-dojo-type="dijit/Dialog" id="dlg_banner" title="查看banner" style="display: none; height: 15em; width: 40em;">
</div>
