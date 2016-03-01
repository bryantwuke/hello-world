<script>
    function fmt_event_opt(id) {
        var fmtstr ="<a href='javascript:void(0)' onclick='func_note_view(" + id + ", \"查看公告\"); return false;'>查看</a>";
        fmtstr +="<a href='javascript:void(0)' onclick='func_note_del(" + id + ", \"grid_note\"); return false;' style='margin-left:1em;'>删除</a>";
        return fmtstr;
    }

    function func_note_view(id) {
        func_ajaxdlgview("back_setting/note_viewlog", "note", id, "查看公告");
    }

    function func_note_del(id) {
        func_ajaxdel_new("back_setting/note_dellog", "id", id,"grid_note");
    }

    function func_note_sel(value){
        ds_note_list.url = "back_setting/ds_note_list?ntype="+value;
        dijit.byId('grid_note')._refresh();
    }

</script>
<div>
    <label class="mlabel">${noteins.note_label}列表</label>

    <select  data-dojo-type="dijit/form/Select" maxHeight="180"
             value="${noteins.note_type}" style="width: 12em" onchange="func_note_sel(this.value)">
        <option value="-1">全部</option>
        <g:each in="${roomsale.Enum_Reftype.values()}">
            <option value="${it.code}">${it.name}公告</option>
        </g:each>
    </select>

    <button data-dojo-type="dijit/form/Button"
            data-dojo-props="iconClass:'dijitIconNewTask'"
            class="mleft">新增公告
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            func_grid_add('back_setting', 'note', 'ntype', '${noteins.note_type}', '新增公告');
        </script>
    </button>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_note_list"
     data-dojo-props="url:'back_setting/ds_note_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_note" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_note_list,
                structure:[
                {name:'公告标题', field:'note_title', width:'11em'},
                {name:'公告内容', field:'note_content', width:'100%'},

                {name:'开始时间', field:'note_fromtime', width:'6em'},
                {name:'结束时间', field:'note_endtime', width:'6em'},

                {name:'操作',field:'id',width:'7em',formatter:fmt_event_opt}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},"

    >
    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_note" title="查看公告" style="display: none;width: 60em">
</div>
