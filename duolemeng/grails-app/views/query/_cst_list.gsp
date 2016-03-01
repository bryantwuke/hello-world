<script>

    function func_agent_sch(value){
        ds_cst_list.url = "query/ds_cst_list?&kword="+value;
        dijit.byId('grid_cst')._refresh();
    }


</script>
<div>
    <label class="mlabel">客户列表</label>

    <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em;width: 25em"
           trim="true"  class="mright" onchange="func_agent_sch(this.value);" intermediateChanges="true"
           placeholder="要查询客户,经纪人的手机号码或者姓名"/>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_cst_list"
     data-dojo-props="url:'query/ds_cst_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_cst" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_cst_list,
                structure:[
                {name:'经纪人', field:'agent_name', width:'8em'},

                {name:'客户', field:'cst_name', width:'8em'},
                {name:'客户手机号', field:'cst_mobile', width:'8em'},
                {name:'客户身份证', field:'cst_idcard', width:'12em'},
                {name:'客户地址', field:'cst_address', width:'100%'},

                {name:'短信', field:'cst_smsnum', width:'3em'},
                {name:'电话', field:'cst_phonenum', width:'3em'},

                {name:'楼盘状态', field:'cst_regbd', width:'6em'},
                {name:'车行状态', field:'cst_regcar', width:'6em'},

                {name:'创建时间', field:'cst_time', width:'10em'}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},",
         canSort="{return false;
               }">
    </div>
</div>

%{--
<div data-dojo-type="dijit/Dialog" id="dlg_agtcomm" title="新增佣金发放" style="display: none; height: 14em; width: 60em;">
</div>
--}%
