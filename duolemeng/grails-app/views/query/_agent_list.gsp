<script>
    var gloabal_change_symbol = 0;

    function fmt_event_opt(fields) {

        var fmtstr ="<a href='javascript:void(0)' onclick='func_agent_view(" + fields[0] + ", \"查看注册信息\"); return false;'>查看</a>";

        //fmtstr += "<a href='javascript:void(0)' onclick='func_agent_del(" + fields[0] + "); return false;' style='margin-left:1em;'>删除</a>";

        if(fields[1] == ${roomsale.Enum_Agentstatus.AS_idcard_going.code}){
            fmtstr +="<a href='javascript:void(0)' onclick='func_agent_set(" + fields[0] + ",\"设置通过审核\", \"${roomsale.Enum_Agentstatus.AS_idcard_ok.code}\","+fields[1] +"); return false;' style='margin-left:1em;'>审核通过</a>";
            fmtstr +="<a href='javascript:void(0)' onclick='func_agent_set(" + fields[0] + ",\"设置不通过审核\", \"${roomsale.Enum_Agentstatus.AS_idcard_fail.code}\","+fields[1] +"); return false;' style='margin-left:1em;'>审核不通过</a>";
        }else  if(fields[1] == ${roomsale.Enum_Agentstatus.AS_stop.code}){
            fmtstr +="<a href='javascript:void(0)' onclick='func_agent_set(" + fields[0] + ",\"启用\", \"${roomsale.Enum_Agentstatus.AS_idcard_ok.code}\","+fields[1] +"); return false;' style='margin-left:1em;'>启用</a>";
        } else if(fields[1] != ${roomsale.Enum_Agentstatus.AS_idcard_fail.code}) {
            fmtstr +="<a href='javascript:void(0)' onclick='func_agent_set(" + fields[0] + ",\"停用\", \"${roomsale.Enum_Agentstatus.AS_stop.code}\","+fields[1] +"); return false;' style='margin-left:1em;'>停用</a>";
        }

//        fmtstr +="<a href='javascript:void(0)' onclick='func_icon_upload(" + fields[0] + ", \"grid_agent\"); return false;' style='margin-left:1em;'>上传头像</a>";
//
//        fmtstr +="<a href='javascript:void(0)' onclick='func_idcard_upload(" + fields[0] + ", \"grid_agent\"); return false;' style='margin-left:1em;'>上传身份证照</a>";

        return fmtstr;
    }

    function func_agent_set(id,msg,value,status){
        var grid = "grid_agent_pass";

        if(status == ${roomsale.Enum_Agentstatus.AS_idcard_going.code}){
            grid = "grid_agent";
        }else if(status == ${roomsale.Enum_Agentstatus.AS_idcard_fail.code}){
            grid = "grid_agent_fail";
        }else if(status == ${roomsale.Enum_Agentstatus.AS_stop.code}){
            grid = "grid_agent_stop";
        }

        gloabal_change_symbol = 1;

        func_ajaxdel_set("query/agent_setlog", "id", id, grid, msg, value);
    }

    function fmt_icon_down(field){
        var src="/file/load/"+field;
        return "<a href='"+src +"' target='下载'><img src=\"" + src + "\" width=\"32\" height=\"40\"/></a>";
    }

    function func_icon_upload(uid) {
        func_grid_add("file", "agent", "icon_agtid", uid, "上传Logo文件");
    }

    function func_idcard_upload(uid) {
        func_grid_add("file", "agent", "idicon_agtid", uid, "上传Logo文件");
    }

    function func_agent_view(id) {
        func_ajaxdlgview("query/agent_viewlog", "agent", id, "查看注册信息");
    }


    function func_agent_del(id) {
        func_ajaxdel_new("query/agent_dellog", "id", id,"grid_agent");
    }


    function func_agent_sel(value){
        ds_agent_list.url = "query/ds_agent_list?status="+value;
        dijit.byId('grid_agent')._refresh();
    }

    function func_agent_sch(value){
        ds_agent_list.url = "query/ds_agent_list?status=${roomsale.Enum_Agentstatus.AS_idcard_going.code}&kword="+value;
        dijit.byId('grid_agent')._refresh();
    }

    function func_agent_sch_pass(value){
        ds_agent_list_pass.url = "query/ds_agent_list?status=-2&kword="+value;
        dijit.byId('grid_agent_pass')._refresh();
    }

    function func_agent_sch_fail(value){
        ds_agent_list_fail.url = "query/ds_agent_list?status=${roomsale.Enum_Agentstatus.AS_idcard_fail.code}&kword="+value;
        dijit.byId('grid_agent_fail')._refresh();
    }

    function func_agent_sch_stop(value){
        ds_agent_list_stop.url = "query/ds_agent_list?status=${roomsale.Enum_Agentstatus.AS_stop.code}&kword="+value;
        dijit.byId('grid_agent_stop')._refresh();
    }


</script>


<div data-dojo-type="dijit/layout/TabContainer" style="height: 24em; width: 100%" tabPosition="top" id="agenttabs" >
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"待审核"' id="going" onshow="tablistener();">
        <div>
            <label class="mlabel">经纪人列表</label>
            <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em"
                   trim="true" data-dojo-id="user_search" class="mright" onchange="func_agent_sch(this.value);" intermediateChanges="true"
                   placeholder="要查询的人的手机号码或者姓名"/>
        </div>

        <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_agent_list"
             data-dojo-props="url:'query/ds_agent_list?status=${roomsale.Enum_Agentstatus.AS_idcard_going.code}'">
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
            <div id="grid_agent" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
         store:ds_agent_list,
                structure:[
                {name:'头像', field:'agent_iconid', width:'32px', formatter:fmt_icon_down},

                {name:'姓名', field:'agent_name', width:'4em'},
                {name:'手机号', field:'agent_mobile', width:'8em'},
                {name:'身份证', field:'agent_idcard', width:'11em'},

                {name:'地址', field:'agent_address',  width:'100%'},
                {name:'身份证照', field:'agent_idcardid', width:'5em', formatter:fmt_icon_down},

                {name:'操作',fields:['id', 'agent_status'],width:'14em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
            </div>
        </div>
    </div>

    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"审核通过"' id="pass">
        <div>
            <label class="mlabel">经纪人列表</label>
            <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em"
                   trim="true" data-dojo-id="user_search" class="mright" onchange="func_agent_sch_pass(this.value);" intermediateChanges="true"
                   placeholder="要查询的人的手机号码或者姓名"/>
        </div>

        <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_agent_list_pass"
             data-dojo-props="url:'query/ds_agent_list?status=-2'">
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
            <div id="grid_agent_pass" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
         store:ds_agent_list_pass,
                structure:[
                {name:'头像', field:'agent_iconid', width:'32px', formatter:fmt_icon_down},

                {name:'姓名', field:'agent_name', width:'4em'},
                {name:'手机号', field:'agent_mobile', width:'8em'},
                {name:'身份证', field:'agent_idcard', width:'11em'},

                {name:'地址', field:'agent_address',  width:'100%'},
                {name:'身份证照', field:'agent_idcardid', width:'32px', formatter:fmt_icon_down},

                {name:'操作',fields:['id', 'agent_status'],width:'28em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
            </div>
        </div>
    </div>


    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"审核失败"' id="fail">
        <div>
            <label class="mlabel">经纪人列表</label>
            <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em"
                   trim="true" data-dojo-id="user_search" class="mright" onchange="func_agent_sch_fail(this.value);" intermediateChanges="true"
                   placeholder="要查询的人的手机号码或者姓名"/>
        </div>

        <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_agent_list_fail"
             data-dojo-props="url:'query/ds_agent_list?status=${roomsale.Enum_Agentstatus.AS_idcard_fail.code}'">
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
            <div id="grid_agent_fail" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
         store:ds_agent_list_fail,
                structure:[
                {name:'头像', field:'agent_iconid', width:'32px', formatter:fmt_icon_down},

                {name:'姓名', field:'agent_name', width:'4em'},
                {name:'手机号', field:'agent_mobile', width:'8em'},
                {name:'身份证', field:'agent_idcard', width:'11em'},

                {name:'地址', field:'agent_address',  width:'100%'},
                {name:'身份证照', field:'agent_idcardid', width:'32px', formatter:fmt_icon_down},

                {name:'操作',fields:['id', 'agent_status'],width:'28em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
            </div>
        </div>
    </div>


    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"停用"' id="stop">
        <div>
            <label class="mlabel">经纪人列表</label>
            <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em"
                   trim="true" data-dojo-id="user_search" class="mright" onchange="func_agent_sch_stop(this.value);" intermediateChanges="true"
                   placeholder="要查询的人的手机号码或者姓名"/>
        </div>

        <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_agent_list_stop"
             data-dojo-props="url:'query/ds_agent_list?status=${roomsale.Enum_Agentstatus.AS_stop.code}'">
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
            <div id="grid_agent_stop" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
         store:ds_agent_list_stop,
                structure:[
                {name:'头像', field:'agent_iconid', width:'32px', formatter:fmt_icon_down},

                {name:'姓名', field:'agent_name', width:'4em'},
                {name:'手机号', field:'agent_mobile', width:'8em'},
                {name:'身份证', field:'agent_idcard', width:'11em'},

                {name:'地址', field:'agent_address',  width:'100%'},
                {name:'身份证照', field:'agent_idcardid', width:'32px', formatter:fmt_icon_down},

                {name:'操作',fields:['id', 'agent_status'],width:'28em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
            </div>
        </div>
    </div>

</div>





<div data-dojo-type="dijit/Dialog" id="dlg_agent" title="查看注册用户信息" style="display: none; height: 24em; width: 60em;">
</div>


<script>

var global_symbol = 0;
var global_last_id = "";

function tablistener() {
    if(global_symbol == 0) {
        global_symbol = 1

        require(["dijit/registry", "dojo/on", "dojo/ready", "dojo/domReady!"], function (registry, on, ready) {
            ready(function () { //wait till dom is parsed into dijits
                var mytabs = dijit.byId('agenttabs');
                on(mytabs, "Click", function (event) {   //for some reason onClick event doesn't work
                    if(gloabal_change_symbol != 0) {
                        var tmpid = mytabs.selectedChildWidget.id
                        if (global_last_id != tmpid) {
                            global_last_id = tmpid;
                            if (global_last_id == "going") {
                                dijit.byId("grid_agent")._refresh();
                            } else {
                                dijit.byId("grid_agent_" + global_last_id)._refresh();
                            }
                        }
                    }
                });
            });
        });
    }
}
</script>
