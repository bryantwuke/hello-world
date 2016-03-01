<script>
    function fmt_event_opt(id) {
        var fmtstr ="<a href='javascript:void(0)' onclick='func_holiday_view(" + id + ", \"查看\"); return false;'>查看</a>";
        fmtstr +="<a href='javascript:void(0)' onclick='func_holiday_del(" + id + ", \"grid_holiday\"); return false;' style='margin-left:1em;'>删除</a>";
        return fmtstr;
    }

    function func_holiday_view(id) {
        func_ajaxdlgview("back_setting/holiday_viewlog", "holiday", id, "");
    }

    function func_tag_del(id) {
        func_ajaxdel_new("back_setting/holiday_dellog", "id", id,"grid_holiday");
    }

</script>
<div>
    <label class="mlabel">节日列表</label>
    <button data-dojo-type="dijit/form/Button"
            data-dojo-props="iconClass:'dijitIconNewTask'"
            class="mleft">新增节日
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            func_grid_add('back_setting', 'holiday', '', '', '新增节日');
        </script>
    </button>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_holiday_list"
     data-dojo-props="url:'back_setting/ds_holiday_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_holiday" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_holiday_list,
                structure:[

                {name:'节日名称', field:'holiday_name', width:'7em'},
                {name:'日期', field:'holiday_day', width:'7em'},
                {name:'祝福语', field:'holiday_greet', width:'100%'},

                {name:'操作',field:'id',width:'6em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_holiday" title="查看节日" style="display: none; height: 24em; width: 60em;">
</div>
