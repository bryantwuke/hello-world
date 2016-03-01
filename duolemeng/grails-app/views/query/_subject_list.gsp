<script>
    function fmt_event_opt(id) {
        var fmtstr ="<a href='javascript:void(0)' onclick='func_subject_view(" + id + ", \"查看\"); return false;'>查看</a>";
        fmtstr +="<a href='javascript:void(0)' onclick='func_subject_del(" + id + ", \"grid_subject\"); return false;' style='margin-left:1em;'>删除</a>";
        return fmtstr;
    }

    function func_subject_view(id) {
        func_ajaxdlgview("query/subject_viewlog", "subject", id, "新增课程");
    }

    function func_subject_del(id) {
        func_ajaxdel_new("query/subject_dellog", "id", id,"grid_subject");
    }

</script>
<div>
    <label class="mlabel">课程列表</label>
    <button data-dojo-type="dijit/form/Button"
            data-dojo-props="iconClass:'dijitIconNewTask'"
            class="mleft">新增课程
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            func_grid_add('query', 'subject', '', '', '新增课程');
        </script>
    </button>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_subject_list"
     data-dojo-props="url:'query/ds_subject_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_subject" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_subject_list,
                structure:[

                {name:'课程', field:'subject_name', width:'100%'},

                {name:'操作',field:'id',width:'6em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_subject" title="查看课程" style="display: none; height: 12em; width: 27em;">
</div>
