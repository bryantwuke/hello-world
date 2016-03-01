<div>
    <label class="mlabel">反馈列表</label>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_feedback_list"
     data-dojo-props="url:'summary/ds_feedback_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_feedback" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_feedback_list,
                structure:[

                {name:'反馈内容', field:'fb_content', width:'100%'},
                {name:'反馈时间', field:'fb_time', width:'14em'}

                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},"
            >
    </div>
</div>


