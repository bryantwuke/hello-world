<div id="user_lg">
    <a href='javascript:void(0)' onclick="func_grid_add('file', 'startpic', '', '', '上传启动图');" style='margin-left:1em;'>
        <img src="/file/load/${picins.fileid}" align="center" alt="上传启动图"/>
    </a>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_startpic" title="查看用户信息" style="display: none; height: 24em; width: 60em;">
    <script type="dojo/on" data-dojo-event="hide" data-dojo-args="evt">
                func_ajaxget('back_setting/startpic');
            </script>
</div>

%{-- invisible div --}%
<div style="display: none">
    <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_pic_list">
    </div>

    <div data-dojo-type="dijit/layout/ContentPane">
        <div id="grid_startpic" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
             data-dojo-props="
         store:ds_pic_list,
                structure:[]">
        </div>
    </div>

</div>
