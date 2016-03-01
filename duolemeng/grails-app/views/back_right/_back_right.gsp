
<div data-dojo-type="dijit/layout/TabContainer" style="height: 50em; width: 100%" tabPosition="top">

%{--
    <div   data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"后台人员配置"'  >
        <g:render template="/user/risker_setting" />
    </div>
--}%

    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"群组与用户权限管理"' >
        <script type="dojo/on" data-dojo-event="show" data-dojo-args="evt">
            func_delayed_load('grid_groupbright',"ds_group_list");
            func_delayed_load('grid_useringroup',"ds_useringroup");
        </script>
        <g:render template="/back_right/useringroup_list" />
    </div>

    <div  data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"用户权限查看"' >
        <script type="dojo/on" data-dojo-event="show" data-dojo-args="evt">
            func_delayed_load('grid_userbright',"ds_ubuserlist");
        </script>
        <g:render template="/back_right/userbright_list" />
    </div>

</div>