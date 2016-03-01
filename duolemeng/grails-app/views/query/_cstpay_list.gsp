<script>

    function func_agent_sch(value){
        ds_cstpay_list.url = "query/ds_cstpay_list?&kword="+value;
        dijit.byId('grid_cstpay')._refresh();
    }

    function fmt_icon_view(field){
        var src="/file/load/"+field;
        return "<a href='"+src +"' target='下载'><img src=\"" + src + "\" width=\"32\" height=\"40\"/></a>";
    }

</script>
<div>
    <label class="mlabel">收款列表</label>

    <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em; width: 25em"
           trim="true"  class="mright" onchange="func_agent_sch(this.value);" intermediateChanges="true"
           placeholder="要查询客户,经纪人的姓名或者收款流水号"/>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_cstpay_list"
     data-dojo-props="url:'query/ds_cstpay_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_cstpay" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_cstpay_list,
                structure:[
                {name:'收款经纪人', field:'agent_name', width:'8em'},
                {name:'付款客户', field:'cst_name', width:'8em'},

                {name:'流水号', field:'pay_sn', width:'14em'},
                {name:'类型', field:'pay_type', width:'4em'},
                {name:'付款金额', field:'pay_money', width:'6em'},

                {name:'付款时间', field:'pay_time', width:'12em'},
                {name:'付款备注', field:'pay_note', width:'100%'},
                {name:'付款图片', field:'pay_fileid', width:'4em', formatter: fmt_icon_view}

                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},",
         canSort="{var idx = Math.abs(arguments[0]);
                  if(idx==1 || idx == 2) return false;
                  else return false;
               }">
    </div>
</div>

