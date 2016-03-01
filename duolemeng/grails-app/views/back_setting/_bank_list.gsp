<script>
    function fmt_event_opt(id) {
        var fmtstr ="<a href='javascript:void(0)' onclick='func_bank_view(" + id + ", \"查看\"); return false;'>查看</a>";
        fmtstr +="<a href='javascript:void(0)' onclick='func_bank_del(" + id + ", \"grid_bank\"); return false;' style='margin-left:1em;'>删除</a>";
        return fmtstr;
    }

    function func_bank_view(id) {
        func_ajaxdlgview("back_setting/bank_viewlog", "bank", id, "查看信息");
    }


    function func_bank_del(id) {
        func_ajaxdel_new("back_setting/bank_dellog", "id", id,"grid_bank");
    }



    function func_sch(value){
        ds_bank_list.url = "back_setting/ds_bank_list?kword="+value;
        dijit.byId('grid_bank')._refresh();
    }

</script>
<div>
    <label class="mlabel">银行列表</label>
    <button data-dojo-type="dijit/form/Button"
            data-dojo-props="iconClass:'dijitIconNewTask'"
            class="mleft">新增银行
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            func_grid_add('back_setting', 'bank', '', '', '新增银行');
        </script>
    </button>

    <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 5em; width: 25em"
           trim="true" class="mright" onchange="func_sch(this.value);" intermediateChanges="true"
           placeholder="银行标识前缀或者银行名称"/>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_bank_list"
     data-dojo-props="url:'back_setting/ds_bank_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_bank" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_bank_list,
                structure:[

                {name:'标识码', field:'bt_prefix', width:'10em'},
                {name:'银行', field:'bt_name', width:'100%'},

                {name:'操作',field:'id',width:'7em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_bank" title="查看银行数据" style="display: none; height: 20em; width: 40em;">
</div>
