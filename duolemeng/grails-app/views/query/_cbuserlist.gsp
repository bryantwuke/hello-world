<div>
    <label class="mlabel">用户查找</label>
    <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em"
           trim="true" data-dojo-id="cbuser_search" class="mright" onchange="func_cbuser_sch(this.value);" intermediateChanges="true"
           placeholder="登录名/姓名/电话"/>


    <button data-dojo-type="dijit/form/Button"
            data-dojo-props="iconClass:'dijitIconNewTask'" style="margin-left: 4em">新增用户
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            func_user_add('back_user', 'user','gridname=grid_cbuser&status=${sysuser.Enum_userstatus.US_car_ok.code}&orgid=${cbins.cbid}', '新增用户', 'dlg_cb');
        </script>
    </button>


</div>


<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_cbuser_list"
     data-dojo-props="url:'back_user/ds_user_list?status=-3&orgid=${cbins.cbid}'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 30em; width: 67em;padding: 0px">
    <div id="grid_cbuser" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
                     store:ds_cbuser_list,
                     structure:[
                         {name:'登录名', field:'user_loginname', width:'7em'},
                         {name:'姓名', field:'user_name', width:'7em'},
                         {name:'手机号', field:'user_mobile', width:'8em'},
                         {name:'身份证', field:'user_idcard', width:'11em'},

                         {name:'职务', field:'user_title', width:'5em'},
                         {name:'部门', field:'user_depname', width:'100%'},
                         {name:'状态', field:'user_status',  width:'4em', formatter:fmt_userstatus},

                         {name:'操作',fields:['id', 'user_status', 'user_orgid'],width:'6em',formatter:fmt_user_opt}
                     ],
         plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
    </div>
</div>

