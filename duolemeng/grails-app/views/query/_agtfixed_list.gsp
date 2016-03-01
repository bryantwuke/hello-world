<script>
    function fmt_event_opt(fields) {

        var fmtstr ="<a href='javascript:void(0)' onclick='func_release(" + fields[0] + ", \"解冻\"); return false;'>解冻</a>";

        return fmtstr;
    }

    function func_release(id,msg,value){
        func_ajaxdel_set("query/agtfiexed_setlog", "id", id,"grid_agtfixed",msg,value);
    }

    function fmt_icon_down(field){
        var src="/file/load/"+field;
        return "<a href='"+src +"' target='下载'><img src=\"" + src + "\" width=\"32\" height=\"40\"/></a>";
    }


    function func_agent_sch(value){
        ds_agtfixed_list.url = "query/ds_agtfixed_list?&kword="+value;
        dijit.byId('grid_agtfixed')._refresh();
    }


</script>
<div>
    <label class="mlabel">经纪人列表</label>

    <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em"
           trim="true" data-dojo-id="user_search" class="mright" onchange="func_agent_sch(this.value);" intermediateChanges="true"
           placeholder="要查询的人的手机号码或者姓名"/>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_agtfixed_list"
     data-dojo-props="url:'query/ds_agtfixed_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_agtfixed" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_agtfixed_list,
                structure:[
                {name:'头像', field:'agent_iconid', width:'32px', formatter:fmt_icon_down},

                {name:'姓名', field:'agent_name', width:'8em'},
                {name:'手机号', field:'agent_mobile', width:'10em'},

                {name:'金额', field:'cap_num', width:'6em'},
                {name:'发放时间', field:'cap_time', width:'100%'},
                {name:'发放类型', field:'cap_type', width:'14em'},

                {name:'操作',fields:['ac_id'],width:'3em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},",
         canSort="{var idx = Math.abs(arguments[0]);
                  if(idx==4 || idx==5 || idx ==6) return false;
                  else return true;
               }">
    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_agtcomm" title="新增佣金发放" style="display: none; height: 14em; width: 60em;">
</div>
