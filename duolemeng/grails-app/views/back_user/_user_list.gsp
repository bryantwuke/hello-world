<%@ page import="sysuser.Enum_userstatus" %>
<script>
    var gloabal_change_symbol = 0;

    function fmt_event_opt(fields){
        var fmtstr ="<a href='javascript:void(0)' onclick='func_user_view(" + fields[0] + "); return false;'>查看</a>";

        switch (fields[1]){
            case ${sysuser.Enum_userstatus.US_dlm_ok.code}:
                fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"停用\", \"${sysuser.Enum_userstatus.US_dlm_no.code}\"); return false;' style='margin-left:1em;'>停用</a>";
                break;
            case ${sysuser.Enum_userstatus.US_dlm_no.code}:
                fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"启用\", \"${sysuser.Enum_userstatus.US_dlm_ok.code}\"); return false;' style='margin-left:1em;'>启用</a>";
                break;
            case ${sysuser.Enum_userstatus.US_bd_ok.code}:
                fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"停用\", \"${sysuser.Enum_userstatus.US_bd_no.code}\"); return false;' style='margin-left:1em;'>停用</a>";
                break;
            case ${sysuser.Enum_userstatus.US_bd_no.code}:
                fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"启用\", \"${sysuser.Enum_userstatus.US_bd_ok.code}\"); return false;' style='margin-left:1em;'>启用</a>";
                break;
            case ${sysuser.Enum_userstatus.US_car_ok.code}:
                fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"停用\", \"${sysuser.Enum_userstatus.US_car_no.code}\"); return false;' style='margin-left:1em;'>停用</a>";
                break;
            case ${sysuser.Enum_userstatus.US_car_no.code}:
                fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"启用\", \"${sysuser.Enum_userstatus.US_car_ok.code}\"); return false;' style='margin-left:1em;'>启用</a>";
                break;
        }


         return fmtstr;
    }

    function func_user_view(id){
        func_ajaxdlgview("back_user/user_viewlog", "user", id, "查看用户信息");
    }

    function fmt_userstatus(code) {
        switch (code) {
                <g:each in="${sysuser.Enum_userstatus.values()}">
            case ${it.code}:
                return "${it.name}";
                </g:each>
            default :
                return '-';
        }
    }

    function func_user_set(id, msg, value){
        var grid = "grid_user_dlm";

        switch (value){
            case '${sysuser.Enum_userstatus.US_dlm_ok.code}':
                grid = "grid_user_dlm";
                break;
            case '${sysuser.Enum_userstatus.US_dlm_no.code}':
                grid = "grid_user_dlm";
                break;
            case '${sysuser.Enum_userstatus.US_bd_ok.code}':
                grid = "grid_user_bd";
                break;
            case '${sysuser.Enum_userstatus.US_bd_no.code}':
                grid = "grid_user_bd";
                break;
            case '${sysuser.Enum_userstatus.US_car_ok.code}':
                grid = "grid_user_car";
                break;
            case '${sysuser.Enum_userstatus.US_car_no.code}':
                grid = "grid_user_car";
                break;
        }

        gloabal_change_symbol = 1;

        func_ajaxdel_set("back_user/user_setlog", "id", id, grid, msg, value);
    }


    function func_dlm_sch(value){
        ds_user_list_dlm.url = "back_user/ds_user_list?status=-1&kword="+value;
        dijit.byId('grid_user_dlm')._refresh();
    }

    function func_bd_sch(value){
        ds_user_list_bd.url = "back_user/ds_user_list?status=-2&kword="+value;
        dijit.byId('grid_user_bd')._refresh();
    }

    function func_car_sch(value){
        ds_user_list_car.url = "back_user/ds_user_list?status=-3&kword="+value;
        dijit.byId('grid_user_car')._refresh();
    }



</script>


%{--  用户分类列表--}%
<div data-dojo-type="dijit/layout/TabContainer" style="height: 90%; width: 100%" tabPosition="top" id="usertabs" >

    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"平台用户"' id="dlm" onshow="tablistener();">

        <div>
            <label class="mlabel">用户查找</label>
            <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em"
                   trim="true" data-dojo-id="user_search" class="mright" onchange="func_dlm_sch(this.value);" intermediateChanges="true"
                   placeholder="登录名/姓名/电话"/>


                <button data-dojo-type="dijit/form/Button"
                        data-dojo-props="iconClass:'dijitIconNewTask'" style="margin-left: 4em">新增用户
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        func_grid_add('back_user', 'user','gridname', 'grid_user_dlm&status=${sysuser.Enum_userstatus.US_dlm_ok.code}&orgid=0', '新增用户');
                    </script>
                </button>


        </div>

        <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_user_list_dlm"
             data-dojo-props="url:'back_user/ds_user_list?status=-1'">
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
            <div id="grid_user_dlm" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
                     store:ds_user_list_dlm,
                     structure:[
                         {name:'登录名', field:'user_loginname', width:'7em'},
                         {name:'姓名', field:'user_name', width:'7em'},
                         {name:'手机号', field:'user_mobile', width:'8em'},
                         {name:'身份证', field:'user_idcard', width:'11em'},

                         {name:'职务', field:'user_title', width:'5em'},
                         {name:'部门', field:'user_depname', width:'5em'},
                         {name:'地址', field:'user_address',  width:'100%'},
                         {name:'状态', field:'user_status',  width:'4em', formatter:fmt_userstatus},

                         {name:'操作',fields:['id', 'user_status', 'user_orgid'],width:'6em',formatter:fmt_event_opt}
                     ],
                     plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
            </div>
        </div>

    </div>


    %{-- 楼盘人员--}%
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"楼盘用户"' id="bd" >
    <div>
        <label class="mlabel">用户查找</label>
        <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em"
               trim="true" data-dojo-id="user_search" class="mright" onchange="func_bd_sch(this.value);" intermediateChanges="true"
               placeholder="登录名/姓名/电话"/>
    </div>

    <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_user_list_bd"
         data-dojo-props="url:'back_user/ds_user_list?status=-2'">
    </div>

    <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
        <div id="grid_user_bd" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
             data-dojo-props="
                     store:ds_user_list_bd,
                     structure:[
                         {name:'登录名', field:'user_loginname', width:'7em'},
                         {name:'姓名', field:'user_name', width:'7em'},
                         {name:'手机号', field:'user_mobile', width:'8em'},
                         {name:'身份证', field:'user_idcard', width:'11em'},

                         {name:'职务', field:'user_title', width:'5em'},
                         {name:'部门', field:'user_depname', width:'5em'},
                         {name:'地址', field:'user_address',  width:'100%'},
                         {name:'状态', field:'user_status',  width:'4em', formatter:fmt_userstatus},

                         {name:'操作',fields:['id', 'user_status', 'user_orgid'],width:'6em',formatter:fmt_event_opt}
                     ],
                     plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
        </div>
    </div>

    </div>


    %{--车行人员--}%
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"车行用户"' id="car" >
    <div>
        <label class="mlabel">用户查找</label>
        <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em"
               trim="true" data-dojo-id="user_search" class="mright" onchange="func_car_sch(this.value);" intermediateChanges="true"
               placeholder="登录名/姓名/电话"/>
    </div>

    <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_user_list_car"
         data-dojo-props="url:'back_user/ds_user_list?status=-3'">
    </div>

    <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
        <div id="grid_user_car" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
             data-dojo-props="
                     store:ds_user_list_car,
                     structure:[
                         {name:'登录名', field:'user_loginname', width:'7em'},
                         {name:'姓名', field:'user_name', width:'7em'},
                         {name:'手机号', field:'user_mobile', width:'8em'},
                         {name:'身份证', field:'user_idcard', width:'11em'},

                         {name:'职务', field:'user_title', width:'5em'},
                         {name:'部门', field:'user_depname', width:'5em'},
                         {name:'地址', field:'user_address',  width:'100%'},
                         {name:'状态', field:'user_status',  width:'4em', formatter:fmt_userstatus},

                         {name:'操作',fields:['id', 'user_status', 'user_orgid'],width:'6em',formatter:fmt_event_opt}
                     ],
                     plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
        </div>
    </div>

    </div>


</div>




<div data-dojo-type="dijit/Dialog" id="dlg_user" title="查看用户信息" style="display: none; height: 34em; width: 70em;">
</div>


<script>

    var global_symbol = 0;
    var global_last_id = "";

    function tablistener() {
        if(global_symbol == 0) {
            global_symbol = 1

            require(["dijit/registry", "dojo/on", "dojo/ready", "dojo/domReady!"], function (registry, on, ready) {
                ready(function () { //wait till dom is parsed into dijits
                    var mytabs = dijit.byId('usertabs');
                    on(mytabs, "Click", function (event) {   //for some reason onClick event doesn't work
                        if(gloabal_change_symbol != 0) {
                            var tmpid = mytabs.selectedChildWidget.id
                            if (global_last_id != tmpid) {
                                global_last_id = tmpid;

                                dijit.byId("grid_user_" + global_last_id)._refresh();

                            }
                        }
                    });
                });
            });
        }
    }
</script>

