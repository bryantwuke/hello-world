<script>
    var global_cb_id = null;

    function fmt_event_opt(fileds) {
        var fmtstr ="<a href='javascript:void(0)' onclick='func_cb_view(" + fileds[0] + ", \"查看\"); return false;'>查看</a>";
        fmtstr +="<a href='javascript:void(0)' onclick='func_cbuser_view(" + fileds[0] + ", \"grid_cb\"); return false;' style='margin-left:1em;'>人员管理</a>";
        //fmtstr +="<a href='javascript:void(0)' onclick='func_cb_del(" + fileds[0] + ", \"grid_cb\"); return false;' style='margin-left:1em;'>删除</a>";
        return fmtstr;
    }

    function func_cb_view(id) {
        func_ajaxdlgcbview("query/cb_viewlog", "cb", id, "查看车行");
    }

    function func_cb_del(id) {
        func_ajaxdel_new("query/cb_dellog", "id", id,"grid_bd");
    }

    function func_cbuser_view(id){
        global_cb_id = id;

        func_ajaxdlgview("query/cbuser_viewlog", 'cb', id, "人员管理", true);
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
                case ${sysuser.Enum_userstatus.US_car_ok.code}:
                fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"停用\", \"${sysuser.Enum_userstatus.US_car_no.code}\"); return false;' style='margin-left:1em;'>停用</a>";
                break;
                case ${sysuser.Enum_userstatus.US_car_no.code}:
                fmtstr +="<a href='javascript:void(0)' onclick='func_user_set(" + fields[0] + ",\"启用\", \"${sysuser.Enum_userstatus.US_car_ok.code}\"); return false;' style='margin-left:1em;'>启用</a>";
                break;
        }


        return fmtstr;

    }


    function func_cbuser_sch(value){
        ds_cbuser_list.url = "back_user/ds_user_list?status=-3&kword="+value+"&orgid="+global_cb_id;
        dijit.byId('grid_cbuser')._refresh();
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
        func_ajax_userview("back_user/user_viewlog", "user", id+"&gridname=grid_cbuser", "查看用户信息");
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

                dijit.byId("dlg_cb").hide();
                dijit.byId("dlg_" + domainname).show();
            }
        });
    }

    function func_user_set(id, msg, value){
        func_ajaxdel_set("back_user/user_setlog", "id", id, 'grid_cbuser', msg, value);
    }

</script>
<div>
    <label class="mlabel">车行列表</label>
    <button data-dojo-type="dijit/form/Button"
            data-dojo-props="iconClass:'dijitIconNewTask'"
            class="mleft">新增车行
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            func_grid_add('query', 'cb', '', '', '新增车行');
        </script>
    </button>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_cb_list"
     data-dojo-props="url:'query/ds_cb_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_cb" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_cb_list,
                structure:[
                {name:'车行名称', field:'cb_name', width:'10em'},
                {name:'所在城市', field:'cb_city', width:'5em'},
                {name:'所在区', field:'cb_area', width:'5em'},
                {name:'地址', field:'cb_address', width:'100%'},

                {name:'车行电话', field:'cb_phone', width:'10em'},
                {name:'平均佣金', field:'cb_avgcash', width:'5em'},

                {name:'关注度', field:'cb_focus', width:'4em'},
                {name:'浏览量', field:'cb_view', width:'4em'},
                {name:'报备量', field:'cb_submit', width:'4em'},

                {name:'车行图片', field:'file_num', fields:['file_num', 'id'], width:'3em', formatter:fmt_file_cbpic},


                {name:'操作',fields:['id'],width:'10em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_cb" title="查看" style="display: none; width: 70em; height: 40em;">
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_user" title="查看" style="width: 70em; height: 40em;">
    <script type="dojo/on" data-dojo-event="hide" data-dojo-args="evt">
       dijit.byId('dlg_cb').show();
    </script>
</div>
