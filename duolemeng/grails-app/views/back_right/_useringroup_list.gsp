<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_group_list"
     data-dojo-props="url:'back_right/ds_group_list'"></div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_useringroup"
     data-dojo-props="url:'back_right/ds_useringroup_list'"></div>

<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design:'sidebar', gutters:false, liveSplitters:false">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="splitter:false, region:'leading'" style="width: 50%;">
        <label class="mlabel">群组表</label>

        <button data-dojo-type="dijit/form/Button"  id='btn_group_delete'
                data-dojo-props="iconClass:'dijitIconDelete'" disabled="disabled"
                class="mbtnright">删除
            <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            this.set("disabled", true);
            dijit.byId("btn_group_view").set("disabled", true);
            dijit.byId("grid_groupbright").selection.clear();
            func_ajaxdel_new("back_right/groupbright_dellog", "group_id", selidx_groupid.value, "grid_groupbright");

            ds_useringroup.url = "back_right/ds_useringroup_list";
            dijit.byId("grid_useringroup")._refresh();
            </script>
        </button>

        <button data-dojo-type="dijit/form/Button"  id='btn_group_view'
                data-dojo-props="iconClass:'dijitIconEditTask'"  disabled="disabled"
                class="mbtnright">查看
            <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            func_ajaxdlgview("back_right/groupbright_viewlog", "groupbright", selidx_groupid.value, "查看群组权限");
            </script>
        </button>

        <button data-dojo-type="dijit/form/Button"
                data-dojo-props="iconClass:'dijitIconNewTask'"
                class="mbtnright">新增
            <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                func_grid_add_noid('back_right', 'groupbright', '新增群组及其关联权限');
            </script>
        </button>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 90%; width: 98%;padding: 0">
            <div id="grid_groupbright" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
                rowsPerPage: 100,
                structure:[
                {name:'群组名称', field:'group_name', width:'100%'}
                %{--{name:'操作', field:'group_id', width:'7em', formatter: fmt_groupbright_view}--}%
                ],
                plugins:{indirectSelection: {name:'选择', width:'4em'},
                pagination:{pageSizes:[16, 32, 'all'], defaultPageSize: 16}},
                selectionMode:'single', rowSelector:'0px'"
                 canSort="{ return false; }"
            >
                <script type='dojo/on' data-dojo-event="rowClick" data-dojo-args="evt">
                var idx = evt.rowIndex;
                var item = this.getItem(idx);
                if (0 != evt.cellIndex) {
                    this.rowSelectCell.toggleRow(idx, true);
                }
                else {
                    this.rowSelectCell.toggleRow(idx, false);
                }

                var group_id = this.store.getValue(item, 'id');

                ds_useringroup.url = 'back_right/ds_useringroup_list?group_id=' + group_id;
                dijit.byId('grid_useringroup')._refresh();
                selidx_groupid.set('value', group_id);

                dijit.byId('btn_group_delete').set('disabled', false);
                dijit.byId('btn_group_view').set('disabled', false);
                dijit.byId('btn_useringroup_add').set('disabled', false);
                dijit.byId('grid_useringroup').selection.clear();
                dijit.byId('btn_useringroup_delete').set('disabled', true);

            </script>

            </div>
        </div>

        <input type="text" data-dojo-type="dijit/form/TextBox"
               data-dojo-id="selidx_groupid" name="selidx_groupid" style="display: none;" />

    </div>

    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="splitter:false, region:'center'">
        <label class="mlabel">群组所包含的用户表</label>

        <button data-dojo-type="dijit/form/Button"  disabled="disabled"
                data-dojo-props="iconClass:'dijitIconDelete'" id='btn_useringroup_delete'
                class="mbtnright">删除用户
            <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                this.set("disabled", true);

                var grid = dijit.byId("grid_useringroup");
                var items = grid.selection.getSelected();
                var myselected = new Array();

                dojo.forEach(items, function(selected){
                var value = grid.store.getValue(selected, 'user_id');
                myselected.push(value);
                });

                dojo.xhrPost({
                url: "back_right/useringroup_dellog",
                content: {
                group_id:selidx_groupid.value,
                user_idlist:dojo.toJson(myselected)
                },
                timeout:5000,
                load: function(response, ioArgs) {
                    dijit.byId('grid_useringroup').rowSelectCell.toggleAllSelection(false);
                    dijit.byId('grid_useringroup')._refresh();
                }
                });

            </script>
        </button>

        <button data-dojo-type="dijit/form/Button" disabled="disabled"
                data-dojo-props="iconClass:'dijitIconNewTask'" id='btn_useringroup_add'
                class="mbtnright">增加用户
            <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                func_grid_add('back_right', 'useringroup', 'group_id', selidx_groupid.value, '增加群组中的用户');
            </script>
        </button>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 90%; width: 98%;padding: 0">
            <div id="grid_useringroup" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
            structure:[
            {name:'用户姓名', field:'user_name', width: '30%'},
            {name:'职务', field:'user_title', width:'30%'},
            {name:'所在部门', field:'user_depname', width:'40%'}
            ],
           plugins:{indirectSelection: {headerSelector:false, name:'多选', width:'4em'}, pagination:{pageSizes:[16, 32, 'all'], defaultPageSize: 16}}"
                 canSort="{ return false; }"
            >
                <script type='dojo/on' data-dojo-event="rowClick" data-dojo-args="evt">
                    var idx = evt.rowIndex;
                    var toggle = this.selection.isSelected(idx)
                    var selectednum = this.selection.getSelected().length;

                    if (0 != evt.cellIndex) {
                        this.rowSelectCell.toggleRow(idx, !toggle);
                    }

                    if(toggle) selectednum--;
                    else       selectednum++;

                    if(selectednum) {
                    dijit.byId('btn_useringroup_delete').set('disabled', false);
                    }else {
                    dijit.byId('btn_useringroup_delete').set('disabled', true);
                    }

                </script>
            </div>
        </div>

    </div>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_useringroup" title="增加群组中的用户" style="display: none; height: 45em; width: 49em;">

</div>

<div data-dojo-type="dijit/Dialog" id="dlg_groupbright" title="群组及其关联权限" style="display: none; height: 29em; width: 60em;">
    <script type="dojo/on" data-dojo-event="hide" data-dojo-args="evt">
        dijit.byId('dlg_groupbright').destroyDescendants();
    </script>

</div>


