<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_usernotingroup"
     data-dojo-props="url:'back_right/ds_usernotingroup_list?group_id=${groupidins}'"></div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 35em; width: 97%">
<div id="grid_useringroup_add" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
     canSort="{return false;}"
     data-dojo-props="store:ds_usernotingroup,
            structure:[
            {name:'用户姓名', field:'user_name', width: '30%'},
            {name:'职务', field:'user_title', width: '30%'},
            {name:'所在部门', field:'user_depname', width:'40%'}
            ],
           plugins:{indirectSelection: {headerSelector:true}, pagination:{pageSizes:[12, 24, 'all'], defaultPageSize: 12}}"
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
        dijit.byId('btn_useringroup_save').set('disabled', false);
        }else {
        dijit.byId('btn_useringroup_save').set('disabled', true);
        }
    </script>
    <script type='dojo/on' data-dojo-event="headerCellClick" data-dojo-args="evt">
        if(evt.cell.index == 0 ) {
        if(this.selection.getSelected().length) {
        dijit.byId('btn_useringroup_save').set('disabled', false);
        }else {
        dijit.byId('btn_useringroup_save').set('disabled', true);
        }

        }
    </script>

</div>
</div>

<button type="button" data-dojo-type="dijit/form/Button" data-dojo-props="iconClass:'dijitIconSave'"
        id="btn_useringroup_save"  class="mleft" style="margin-left: 3em" disabled="disabled">保存
    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
        this.set("disabled", true);

        var grid = dijit.byId("grid_useringroup_add");
        var items = grid.selection.getSelected();
        var myselected = new Array();

        dojo.forEach(items, function(selected){
            var value = grid.store.getValue(selected, 'user_id');
            myselected.push(value);
        });
        dojo.xhrPost({
            url: "back_right/useringroup_addlog",
            content: {
                group_id:selidx_groupid.value,
                user_idlist:dojo.toJson(myselected)
            },
            timeout:5000,
            load: function(response, ioArgs) {
                dijit.byId('grid_useringroup')._refresh();
            }
        });


        dijit.byId('dlg_useringroup').hide();

        %{--下面用于测试所获取到的全部值--}%
        %{--dojo.forEach(items, function(selected){--}%
            %{--dojo.forEach(grid.store.getAttributes(selected), function(attribute){--}%
                %{--var value = grid.store.getValues(selected, attribute);--}%
                %{--console.debug('attribute name:' + attribute + '== and value:' + value);--}%
        %{--});--}%
        %{--});--}%

    </script>
</button>


<button type="button" data-dojo-type="dijit/form/Button" data-dojo-props="iconClass:'dijitIconUndo'"
        id="btn_useringroup_reset" class="mleft" >取消
    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
        dijit.byId('dlg_useringroup').hide();
    </script>
</button>

