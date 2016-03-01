<script type="text/javascript">
    function fmt_userbright_view(user_id){
        return "<a href='javascript:void(0)' onclick='func_userbright_view(" + user_id +"); return false;'>查看权限</a>";
    }

    function func_userbright_view(user_id){
        func_ajax_view("back_right/userbright_viewlog?user_id=", "dlg_userbright", user_id, 'btn_userbright_close');
    }

</script>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_ubuserlist"
     data-dojo-props="url:'back_right/ds_userbright_list'"></div>

<div data-dojo-type="dojox/grid/EnhancedGrid"  id="grid_userbright" selectable="true"
     data-dojo-props="
            structure:[
            {name:'姓名', field:'user_name', width: '30%'},
            {name:'职务', field:'user_title', width: '30%'},
            {name:'部门', field:'user_depname', width:'40%'},
            {name:'操作', field:'user_id', width:'6em', formatter:fmt_userbright_view}
            ],
           plugins:{pagination:{pageSizes:[16, 32, 'all'], defaultPageSize: 16}}"
     canSort="{var idx = Math.abs(arguments[0]);
        if(idx>3) return false;
        else return true;
        }"
>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_userbright" title="查看用户权限" style="display: none; height: 24em; width: 60em;">
    <script type="dojo/on" data-dojo-event="hide" data-dojo-args="evt">
        dijit.byId('dlg_userbright').destroyDescendants();
    </script>

</div>
