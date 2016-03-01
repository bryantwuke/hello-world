<script>
    function func_issue_sch(value){
        ds_issue_list.url = "query/ds_issue_list?&kword="+value;
        dijit.byId('grid_issue')._refresh();
    }


</script>
<div>
    <label class="mlabel">发放列表</label>
    <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em; width: 25em"
           trim="true"  class="mright" onchange="func_issue_sch(this.value);" intermediateChanges="true"
           placeholder="经纪人姓名/经纪人电话"/>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_issue_list"
     data-dojo-props="url:'query/ds_issue_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_issue" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_issue_list,
                structure:[

                {name:'经纪人姓名', field:'agent_name', width:'6em'},
                {name:'经纪人电话', field:'agent_mobile', width:'10em'},

                {name:'类型', field:'cap_type', width:'14em'},
                {name:'金额', field:'cap_num', width:'5em'},

                {name:'剩余总金额', field:'cap_left', width:'6em'},
                {name:'发放时间', field:'cap_time', width:'100%'},

                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},",
         canSort="{ return false; }">
    </div>
</div>


