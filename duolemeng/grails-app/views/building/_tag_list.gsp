<script>
    function fmt_event_opt(id) {
        var fmtstr ="<a href='javascript:void(0)' onclick='func_tag_view(" + id + ", \"查看\"); return false;'>查看</a>";
        fmtstr +="<a href='javascript:void(0)' onclick='func_tag_del(" + id + ", \"grid_tag\"); return false;' style='margin-left:1em;'>删除</a>";
        return fmtstr;
    }

    function func_tag_view(id) {
        func_ajaxdlgview("building/tag_viewlog", "tag", id, "${tagins.tag_label}");
    }

    function func_tag_del(id) {
        func_ajaxdel_new("building/tag_dellog", "id", id,"grid_tag");
    }

</script>
<div>
    <label class="mlabel">${tagins.tag_label}列表</label>
    <button data-dojo-type="dijit/form/Button"
            data-dojo-props="iconClass:'dijitIconNewTask'"
            class="mleft">新增${tagins.tag_label}
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            func_grid_add('building', 'tag', 'tag_type', '${tagins.tag_type}', '新增${tagins.tag_label}');
        </script>
    </button>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_tag_list"
     data-dojo-props="url:'building/ds_tag_list?tag_type=${tagins.tag_type}'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_tag" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_tag_list,
                structure:[

                {name:'${tagins.tag_label}', field:'bd_tag', width:'100%'},

                {name:'操作',field:'id',width:'6em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},",
         canSort="{return false;}"
                 >
    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_tag" title="查看标签" style="display: none; height: 12em; width: 27em;">
</div>
