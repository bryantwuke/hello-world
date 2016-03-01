<script>
    function fmt_event_opt(fields) {

        var fmtstr ="<a href='javascript:void(0)' onclick='func_dc_view2(" + fields[0] + ", \"查看\"); return false;'>查看</a>";

        if(fields[1] == ${roomsale.Enum_Discounttype.DT_news_wait.code}) { //公告
            fmtstr = "<a href='javascript:void(0)' onclick='func_dc_view(" + fields[0] + ", \"查看\"); return false;'>查看</a>";
        }

        fmtstr +="<a href='javascript:void(0)' onclick='func_dc_del(" + fields[0] + "," + fields[1] + "); return false;' style='margin-left:1em;'>删除</a>";
        return fmtstr;
    }

    function func_dc_set(id,msg,value){
        func_ajaxdel_set("building/discount_setlog", "id", id,"grid_discount_todo",msg,value);
    }

    function func_dc_view(id) {
        func_ajaxdlgview("building/discount_viewlog", "discount", id, "查看公告");
    }

    function func_dc_view2(id) {
        func_ajaxdlgview2("building/discount_viewlog", "discount", id, "查看公告");
    }

    function func_dc_del(id, status) {
        var grid = "grid_discount_todo";
        if(status == ${roomsale.Enum_Discounttype.DT_news_no.code}){
            grid = "grid_discount_no"
        }

        if(status == ${roomsale.Enum_Discounttype.DT_news_ok.code}){
            grid = "grid_discount_ok"
        }

        func_ajaxdel_new("building/discount_dellog", "id", id, grid);
    }

    require(["dijit/Editor",
        "dijit/_editor/plugins/LinkDialog",
        "dijit/_editor/plugins/FontChoice",
        "dijit/_editor/plugins/TextColor",
        "dijit/_editor/plugins/ViewSource",
        "dojox/editor/plugins/AutoUrlLink",
        "dojox/editor/plugins/LocalImage"
    ]);

</script>



<div data-dojo-type="dijit/layout/TabContainer" style="height: 90%; width: 100%" tabPosition="top">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"待审核"'>
        <div style="margin-left: auto">
            <button data-dojo-type="dijit/form/Button"
                    data-dojo-props="iconClass:'dijitIconNewTask'"
                    class="mleft">新增${dcins.reflabel}${dcins.dclabel}
                <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
             func_grid_add2('building', 'discount','dctype=${dcins.dctype}&reftype=${dcins.reftype}&refid=${dcins.refid}', '新增${dcins.dclabel}');
        </script>
            </button>
        </div>

        <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_discount_list_todo"
             data-dojo-props="url:'building/ds_discount_list?dc_type=${roomsale.Enum_Discounttype.DT_news_wait.code}&reftype=${dcins.reftype}&refid=${dcins.refid}'">
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
            <div id="grid_discount_todo" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
         store:ds_discount_list_todo,
                structure:[
                {name:'ID', field:'id', width:'4em'},
                {name:'发起人', field:'ref_sponsor', width:'14em'},
                {name:'标题', field:'dc_title', width:'100%'},

                {name:'开始时间', field:'dc_fromtime', width:'6em'},
                {name:'结束时间', field:'dc_endtime', width:'6em'},

                {name:'操作',fields:['id', 'dc_type'], width:'6em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},"
                 canSort="{return false;
        }"

            >
            </div>
        </div>

    </div>


    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"审核通过"'>
        <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_discount_list_ok"
            data-dojo-props="url:'building/ds_discount_list?dc_type=${roomsale.Enum_Discounttype.DT_news_ok.code}&reftype=${dcins.reftype}&refid=${dcins.refid}'">
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
            <div id="grid_discount_ok" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
         store:ds_discount_list_ok,
                structure:[
                {name:'ID', field:'id', width:'4em'},
                {name:'发起人', field:'ref_sponsor', width:'14em'},
                {name:'标题', field:'dc_title', width:'100%'},

                {name:'开始时间', field:'dc_fromtime', width:'6em'},
                {name:'结束时间', field:'dc_endtime', width:'6em'},

                {name:'操作',fields:['id', 'dc_type'], width:'6em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},"
                 canSort="{var idx = Math.abs(arguments[0]);
        if(idx==2) return false;
        else return true;
        }"

            >
            </div>
        </div>

    </div>


    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"审核失败"'>

        <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_discount_list_no"
             data-dojo-props="url:'building/ds_discount_list?dc_type=${roomsale.Enum_Discounttype.DT_news_no.code}&reftype=${dcins.reftype}&refid=${dcins.refid}'">
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
            <div id="grid_discount_no" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
         store:ds_discount_list_no,
                structure:[
                {name:'ID', field:'id', width:'4em'},
                {name:'发起人', field:'ref_sponsor', width:'14em'},
                {name:'标题', field:'dc_title', width:'100%'},

                {name:'开始时间', field:'dc_fromtime', width:'6em'},
                {name:'结束时间', field:'dc_endtime', width:'6em'},

                {name:'操作',fields:['id', 'dc_type'], width:'6em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},"
                 canSort="{var idx = Math.abs(arguments[0]);
        if(idx==2) return false;
        else return true;
        }"

            >
            </div>
        </div>

    </div>

</div>



<div data-dojo-type="dijit/Dialog" id="dlg_discount" title="查看" style="display: none;width: 80em">
</div>
