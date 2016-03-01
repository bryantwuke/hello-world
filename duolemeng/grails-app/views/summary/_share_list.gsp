<script>
    function func_share_sch(value){
        ds_share_list.url = "summary/ds_share_list?&kword="+value;
        dijit.byId('grid_share')._refresh();
    }


</script>
<div>
    <label class="mlabel">分享列表</label>
    <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em; width: 25em"
           trim="true"  class="mright" onchange="func_share_sch(this.value);" intermediateChanges="true"
           placeholder="经纪人姓名/经纪人电话"/>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_share_list"
     data-dojo-props="url:'summary/ds_share_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_share" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_share_list,
                structure:[

                {name:'姓名', field:'agent_name', width:'8em'},
                {name:'电话', field:'agent_mobile', width:'8em'},

                {name:'分享标题', field:'dc_title', width:'100%'},
                {name:'阅读量', field:'share_readnum', width:'5em'},

                {name:'分享时间', field:'share_date', width:'14em'},


                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},",
         canSort="{var idx = Math.abs(arguments[0]);
                  if(idx==4 || idx==5 ) return true;
                  else return false;
               }">
    </div>
</div>


