<script>
    function fmt_event_opt(id) {
        var fmtstr ="<a href='javascript:void(0)' onclick='func_test_view(" + id + ", \"查看\"); return false;'>查看</a>";
        fmtstr +="<a href='javascript:void(0)' onclick='func_test_del(" + id + ", \"grid_test\"); return false;' style='margin-left:1em;'>删除</a>";
        return fmtstr;
    }

    function func_test_view(id) {
        func_ajaxdlgview("query/test_viewlog", "test", id, "新增题目");
    }

    function func_test_del(id) {
        func_ajaxdel_new("query/test_dellog", "id", id,"grid_test");
    }

</script>
<div>
    <label class="mlabel">题目列表</label>
    <button data-dojo-type="dijit/form/Button"
            data-dojo-props="iconClass:'dijitIconNewTask'"
            class="mleft">新增题目
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            func_grid_add('query', 'test', '', '', '新增题目');
        </script>
    </button>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_test_list"
     data-dojo-props="url:'query/ds_test_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_test" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_test_list,
                structure:[

                {name:'课程', field:'test_subjectid', width:'10em'},
                {name:'类型', field:'test_type', width:'5em'},

                {name:'题目', field:'test_title', width:'100%'},

                {name:'操作',field:'id',width:'6em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},"
                  cansort="{return false;}">
    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_test" title="查看题目" style="display: none; height: 32em; width: 60em;">
</div>
