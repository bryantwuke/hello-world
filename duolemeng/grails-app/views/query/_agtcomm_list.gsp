<script>
    function fmt_event_opt(fields) {

        var fmtstr ="<a href='javascript:void(0)' onclick='func_agent_add(" + fields[0] + ", \"查看注册信息\"); return false;'>佣金发放</a>";

        return fmtstr;
    }

    function func_agent_set(id,msg,value){
        func_ajaxdel_set("query/agent_setlog", "id", id,"grid_agent",msg,value);
    }

    function fmt_icon_down(field){
        var src="/file/load/"+field;
        return "<a href='"+src +"' target='下载'><img src=\"" + src + "\" width=\"32\" height=\"40\"/></a>";
    }

    function func_agent_add(id) {
        func_grid_add('query', 'agtcomm', 'agent_id', id, '新增佣金发放');
    }



    function func_agent_sch(value){
        ds_agtcomm_list.url = "query/ds_agtcomm_list?&kword="+value;
        dijit.byId('grid_agtcomm')._refresh();
    }


</script>
<div>
    <label class="mlabel">经纪人列表</label>

    <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-right: 2em"
           trim="true" data-dojo-id="user_search" class="mright" onchange="func_agent_sch(this.value);" intermediateChanges="true"
           placeholder="要查询的人的手机号码或者姓名"/>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_agtcomm_list"
     data-dojo-props="url:'query/ds_agtcomm_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_agtcomm" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_agtcomm_list,
                structure:[
                {name:'头像', field:'agent_iconid', width:'32px', formatter:fmt_icon_down},

                {name:'姓名', field:'agent_name', width:'8em'},
                {name:'手机号', field:'agent_mobile', width:'100%'},

                {name:'操作',fields:['id'],width:'5em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_agtcomm" title="新增佣金发放" style="display: none; height: 14em; width: 60em;">
</div>
