<script>
    var global_bd_id = null;

    function fmt_event_opt(fileds) {
        var fmtstr ="<a href='javascript:void(0)' onclick='func_bd_view(" + fileds[0] + ", \"查看楼盘\"); return false;'>查看</a>";
        fmtstr +="<a href='javascript:void(0)' onclick='func_bduser_view(" + fileds[0] + ", \"grid_bd\"); return false;' style='margin-left:1em;'>人员管理</a>";
        //fmtstr +="<a href='javascript:void(0)' onclick='func_bd_del(" + fileds[0] + ", \"grid_bd\"); return false;' style='margin-left:1em;'>删除</a>";
        return fmtstr;
    }

    function func_bd_view(id) {
        func_ajaxdlgbdview("building/bd_viewlog", "bd", id, "查看楼盘");
    }

    function func_bd_del(id) {
        func_ajaxdel_new("building/bd_dellog", "id", id,"grid_bd");
    }

    function func_bduser_view(id){
        global_bd_id = id;

        func_ajaxdlgview("building/bduser_viewlog", 'bd', id, "人员管理", true);
    }


    function getarealist(city,selitem){
        if(city){
            dojo.xhrPost({
                url:'/query/arealist',
                handleAs:'json',
                content: {
                    reqnum:0,
                    city:city
                },
                load:function (data) {
                    var tmpdata = [];
                    for( var i =0;i<data.items.length;i++){
                        tmpdata.push({label:data.items[i].area,id:data.items[i].area});
                    }

                    var store = new dojo.store.Memory({
                        data: tmpdata
                    });

                    var os = new dojo.data.ObjectStore({ objectStore: store });

                    var s = dijit.byId('sel_area');

                    s.setStore(os);
                    s.startup();
                    if(selitem){
                        s.attr( 'value', selitem);
                    }
                }
            });
        }
    }


    function fmt_user_opt(fields){

        var fmtstr ="<a href='javascript:void(0)' onclick='func_user_view(" + fields[0] + "); return false;'>查看</a>";

        switch (fields[1]){
            case ${sysuser.Enum_userstatus.US_bd_ok.code}:
                fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"停用\", \"${sysuser.Enum_userstatus.US_bd_no.code}\"); return false;' style='margin-left:1em;'>停用</a>";
                break;
            case ${sysuser.Enum_userstatus.US_bd_no.code}:
                fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"启用\", \"${sysuser.Enum_userstatus.US_bd_ok.code}\"); return false;' style='margin-left:1em;'>启用</a>";
                break;
                %{--case ${sysuser.Enum_userstatus.US_car_ok.code}:--}%
                %{--fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"停用\", \"${sysuser.Enum_userstatus.US_car_no.code}\"); return false;' style='margin-left:1em;'>停用</a>";--}%
                %{--break;--}%
                %{--case ${sysuser.Enum_userstatus.US_car_no.code}:--}%
                %{--fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"启用\", \"${sysuser.Enum_userstatus.US_car_ok.code}\"); return false;' style='margin-left:1em;'>启用</a>";--}%
                %{--break;--}%
        }


        return fmtstr;

    }


    function func_bduser_sch(value){
        ds_bduser_list.url = "back_user/ds_user_list?status=-2&kword="+value+"&orgid="+global_bd_id;
        dijit.byId('grid_bduser')._refresh();
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

    function func_user_add(controller, domain, args, title, hidedlg){
        dojo.xhrPut({
            url: controller+'/'+domain+ '_add?'+ args,
            load: function(data) {
                if(hidedlg){
                    dijit.byId(hidedlg).hide();
                }
                dijit.byId('dlg_'+domain).destroyDescendants();
                dijit.byId('dlg_'+domain).set('content', data);
                dijit.byId('dlg_'+ domain).set('title', title);
                dijit.byId('dlg_'+domain).show();
            }
        });
    }

    function func_user_view(id){
        func_ajax_userview("back_user/user_viewlog", "user", id+"&gridname=grid_bduser", "查看用户信息");
    }

    function func_ajax_userview(url, domainname, value, title, isvoid) {
        dojo.xhrPut({
            url: url + "?" + domainname + "_id=" + value,
            load: function(data) {
                dijit.byId("dlg_" + domainname).destroyDescendants();
                dijit.byId("dlg_" + domainname).set('content', data);
                if(!isvoid) {
                    disableall("frm_" + domainname, true);
                    dijit.byId("btn_" + domainname + "_edit").set('disabled', false);
                    dijit.byId("btn_" + domainname + "_reset").set('disabled', false);
                }
                dijit.byId("dlg_" + domainname).set("title", title);

                dijit.byId("dlg_bd").hide();
                dijit.byId("dlg_" + domainname).show();
            }
        });
    }

    function func_user_set(id, msg, value){
        func_ajaxdel_set("back_user/user_setlog", "id", id, 'grid_bduser', msg, value);
    }

</script>
<div>
    <label class="mlabel">楼盘列表</label>
    <button data-dojo-type="dijit/form/Button"
            data-dojo-props="iconClass:'dijitIconNewTask'"
            class="mleft">新增楼盘
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            func_grid_add('building', 'bd', '', '', '新增楼盘');
        </script>
    </button>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_bd_list"
     data-dojo-props="url:'building/ds_bd_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_building" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_bd_list,
                structure:[
                {name:'名称', field:'bd_name', width:'7em'},
                {name:'开发商', field:'bd_developer', width:'5em'},
                {name:'城市', field:'bd_city', width:'5em'},
                {name:'区', field:'bd_area', width:'5em'},
                {name:'地址', field:'bd_address', width:'100%'},

                {name:'服务电话', field:'bd_phone', width:'7em'},
                {name:'产权年限', field:'bd_year', width:'5em'},
                {name:'绿化率', field:'bd_greenrate', width:'5em'},
                {name:'容积率', field:'bd_volumnrate', width:'5em'},
                {name:'开盘时间', field:'bd_opentime', width:'5em'},

                {name:'均价', field:'bd_price', width:'5em'},

                {name:'在售房源', field:'bd_house', width:'5em'},
                {name:'案场电话', field:'bd_agenttel', width:'8em'},
                {name:'户型图', field:'bd_typenum', fields:['bd_typenum', 'bd_id'], width:'3.5em', formatter:fmt_file_bdtype},
                {name:'楼盘图', field:'bd_picnum', fields:['bd_picnum', 'bd_id'], width:'3.5em', formatter:fmt_file_bdpic},

                {name:'关注度', field:'bd_focus', width:'3.5em'},
                {name:'浏览量', field:'bd_view', width:'3.5em'},
                {name:'报备量', field:'bd_submit', width:'3.5em'},


                {name:'操作',fields:['bd_id'],width:'10em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_bd" title="查看楼盘" style="width: 70em; height: 40em;">
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_user" title="查看楼盘" style="width: 70em; height: 40em;">
    <script type="dojo/on" data-dojo-event="hide" data-dojo-args="evt">
       dijit.byId('dlg_bd').show();
    </script>
</div>
