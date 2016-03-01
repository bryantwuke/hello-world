<script>
    function func_cash_sch(value){
        ds_cash_list.url = "query/ds_cash_list?&kword="+value;
        dijit.byId('grid_cash')._refresh();
    }


</script>
<div>
    <label class="mlabel">提现列表</label>
    <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em; width: 25em"
           trim="true"  class="mright" onchange="func_cash_sch(this.value);" intermediateChanges="true"
           placeholder="经纪人姓名/经纪人电话"/>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_cash_list"
     data-dojo-props="url:'query/ds_cash_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_cash" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_cash_list,
                structure:[

                {name:'提现人', field:'agent_name', width:'8em'},
                {name:'电话', field:'agent_mobile', width:'10em'},


                {name:'提现金额', field:'cap_num', width:'6em'},

                {name:'剩余总金额', field:'cap_left', width:'6em'},
                {name:'提现时间', field:'cap_time', width:'100%'},

                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},",
         canSort="{ return false; }">
    </div>
</div>


