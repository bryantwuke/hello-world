<script>
    var gloabal_change_symbol = 0;

    function fmt_event_opt(fields) {

        var fmtstr ="<a href='javascript:void(0)' onclick='func_dc_view2(" + fields[0] + ", \"查看\"); return false;'>查看</a>";

        if(fields[1] == ${roomsale.Enum_Discounttype.DT_news_wait.code}){ //公告
            fmtstr ="<a href='javascript:void(0)' onclick='func_dc_view(" + fields[0] + ", \"查看\"); return false;'>查看</a>";
            fmtstr +="<a href='javascript:void(0)' onclick='func_dc_set(" + fields[0] + ",\"设置通过审核\", \"${roomsale.Enum_Discounttype.DT_news_ok.code}\","+fields[2] +"," + fields[3] + "); return false;' style='margin-left:1em;'>审核通过</a>";
            fmtstr +="<a href='javascript:void(0)' onclick='func_dc_set(" + fields[0] + ",\"设置不通过审核\", \"${roomsale.Enum_Discounttype.DT_news_no.code}\","+fields[2] +"," + fields[3] + "); return false;' style='margin-left:1em;'>审核不通过</a>";
        }


        return fmtstr;
    }

    function func_dc_set(id, msg, value, refid, reftype){
        gloabal_change_symbol = 1;

        func_ajaxdel_set("building/discount_setlog", "id", id+"&refid="+refid+"&reftype="+reftype,"grid_discount_todo",msg,value);
    }

    function func_dc_view(id) {
        func_ajaxdlgview("building/discount_viewlog", "discount", id, "查看公告");
    }

    function func_dc_view2(id) {
        func_ajaxdlgview2("building/discount_viewlog", "discount", id, "查看公告");
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

<div data-dojo-type="dijit/layout/TabContainer" style="height: 24em; width: 100%" tabPosition="top" id="dctabs">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"待审核"' id="todo" onshow="tablistener();" >
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

                {name:'操作',fields:['id', 'dc_type', 'dc_refid', 'dc_reftype'], width:'14em',formatter:fmt_event_opt}
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


    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"审核通过"' id="ok">
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

                {name:'操作',fields:['id', 'dc_type'], width:'4em',formatter:fmt_event_opt}
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


    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"审核失败"' id="no">

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

                {name:'操作',fields:['id', 'dc_type'], width:'4em',formatter:fmt_event_opt}
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


<script>

    var global_symbol = 0;
    var global_last_id = "";

    function tablistener() {
        if(global_symbol == 0) {
            global_symbol = 1

            require(["dijit/registry", "dojo/on", "dojo/ready", "dojo/domReady!"], function (registry, on, ready) {
                ready(function () { //wait till dom is parsed into dijits
                    var mytabs = dijit.byId('dctabs');
                    on(mytabs, "Click", function (event) {   //for some reason onClick event doesn't work
                        if(gloabal_change_symbol != 0) {
                            var tmpid = mytabs.selectedChildWidget.id
                            if (global_last_id != tmpid) {
                                global_last_id = tmpid;
                                dijit.byId("grid_discount_" + global_last_id)._refresh();
                            }
                        }
                    });
                });
            });
        }
    }
</script>
