<script>

    function fmt_event_opt(fields) {
        var fmtstr ="";
        if(fields[1] == ${roomsale.Enum_Regstatus.RS_wishyes.code}){  //待判客
            fmtstr +="<a href='javascript:void(0)' onclick='func_regbd_set(" + fields[0] + ",\"设置为判客成功\", \"${roomsale.Enum_Regstatus.RS_regyes.code}\"); return false;'>判客成功</a>";
            fmtstr +="<a href='javascript:void(0)' onclick='func_regbd_set(" + fields[0] + ",\"设置为判客失败\", \"${roomsale.Enum_Regstatus.RS_regno.code}\"); return false;' style='margin-left:1em;'>判客失败</a>"
            %{--fmtstr +="<a href='javascript:void(0)' onclick='func_regbd_set(" + fields[0] + ",\"取消\", \"${roomsale.Enum_Regstatus.RS_cancel.code}\"); return false;' style='margin-left:1em;'>取消</a>";;--}%
            %{--fmtstr +="<a href='javascript:void(0)' onclick='func_regbd_set(" + fields[0] + ",\"成交\", \"${roomsale.Enum_Regstatus.RS_ok.code}\"); return false;' style='margin-left:1em;'>成交</a>";--}%
        }

        if(fields[1] == ${roomsale.Enum_Regstatus.RS_regyes.code}){  //判客成功
            fmtstr +="<a href='javascript:void(0)' onclick='func_regbd_set2(" + fields[0] + ",\"成交\", \"${roomsale.Enum_Regstatus.RS_ok.code}\"); return false;'>成交</a>";
            fmtstr +="<a href='javascript:void(0)' onclick='func_regbd_set2(" + fields[0] + ",\"取消\", \"${roomsale.Enum_Regstatus.RS_cancel.code}\"); return false;' style='margin-left:1em;'>不买了</a>";
        }


        return fmtstr;
    }

    function func_regbd_set(id,msg,value){
        func_ajaxdel_set("query/bdsub_setlog", "id", id,"grid_bdsub",msg,value);
    }

    function func_regbd_set2(id,msg,value){
        func_ajaxdel_set("query/bdsub_setlog", "id", id,"grid_bdsub2",msg,value);
    }

    function fmt_ret_status(code){
        if(code == ${roomsale.Enum_Regstatus.RS_wishyes.code}){
            return "待判客"
        }else{
            return fmt_regstatus(code);
        }
    }

    function func_regbd_sel(value){
        ds_bdsub_list2.url = "query/ds_bdsub_list?status="+value;
        dijit.byId('grid_bdsub2')._refresh();
    }

    function func_sch(value){
        ds_bdsub_list.url = "query/ds_bdsub_list?status=${roomsale.Enum_Regstatus.RS_wishyes.code}&kword="+value;
        dijit.byId('grid_bdsub')._refresh();
    }

    function func_sch2(value){
        ds_bdsub_list2.url = "query/ds_bdsub_list?status="+regbd_sel.value + "&kword="+value;
        dijit.byId('grid_bdsub2')._refresh();
    }


</script>

<div data-dojo-type="dijit/layout/TabContainer" style="height: 24em; width: 100%" tabPosition="top">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"待判客"'>
        <div>
            <label class="mlabel">楼盘报备列表</label>

            <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em; width: 25em"
                   trim="true" class="mright" onchange="func_sch(this.value);" intermediateChanges="true"
                   placeholder="要查询经纪人的手机号码或者姓名或者楼盘名称"/>

        </div>

        <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_bdsub_list"
             data-dojo-props="url:'query/ds_bdsub_list?status=${roomsale.Enum_Regstatus.RS_wishyes.code}'">
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
            <div id="grid_bdsub" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
         store:ds_bdsub_list,
                structure:[
                {name:'经纪人', field:'agent_name', width:'10em'},
                {name:'经纪人电话', field:'agent_mobile', width:'10em'},

                {name:'客户', field:'cst_name', width:'5em'},
                {name:'客户电话', field:'cst_mobile', width:'10em'},
                {name:'楼盘', field:'bd_name', width:'100%'},

                {name:'状态', field:'reg_status', width:'6em', formatter:fmt_ret_status},

                {name:'操作',fields:['id', 'reg_status'],width:'16em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},",
                 canSort="{return false;
               }"
            >
            </div>
        </div>

    </div>


    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"待成交"'>
        <div>
            <label class="mlabel">楼盘报备列表</label>

            <select  data-dojo-type="dijit/form/Select" maxHeight="180"  data-dojo-id="regbd_sel"
            style="width: 12em" onchange="func_regbd_sel(this.value)">
            <option value="-2">全部</option>
            <g:each in="${roomsale.Enum_Regstatus.values()}">
                <g:if test="${it.code != roomsale.Enum_Regstatus.RS_wishno.code && it.code != roomsale.Enum_Regstatus.RS_wishyes.code }">
                    <option value="${it.code}">${it.name}</option>
                </g:if>
            </g:each>
            </select>

            <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-right: 2em; width: 25em"
                   trim="true" class="mright" onchange="func_sch2(this.value);" intermediateChanges="true"
                   placeholder="要查询经纪人的手机号码或者姓名或者楼盘名称"/>

        </div>

        <div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_bdsub_list2"
             data-dojo-props="url:'query/ds_bdsub_list?status=-2'">
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
            <div id="grid_bdsub2" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
         store:ds_bdsub_list2,
                structure:[
                {name:'经纪人', field:'agent_name', width:'10em'},
                {name:'经纪人电话', field:'agent_mobile', width:'10em'},

                {name:'客户', field:'cst_name', width:'5em'},
                {name:'客户电话', field:'cst_mobile', width:'10em'},
                {name:'楼盘', field:'bd_name', width:'100%'},

                {name:'状态', field:'reg_status', width:'6em', formatter:fmt_ret_status},

                {name:'操作',fields:['id', 'reg_status'],width:'16em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},",
                 canSort="{var idx = Math.abs(arguments[0]);
                  if(idx==5) return true;
                  else return false;
               }"
            >
            </div>
        </div>

    </div>

</div>

%{--
<div data-dojo-type="dijit/Dialog" id="dlg_bdsub" title="查看" style="display: none; width: 70em; height: 40em;">
</div>
--}%
