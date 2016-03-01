<div>
    <label class="mlabel">短信发送列表</label>
</div>

<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_sms_list"
     data-dojo-props="url:'back_setting/ds_sms_list'">
</div>

<div data-dojo-type="dijit/layout/ContentPane" style="height: 94%; width: 100%;padding: 0px">
    <div id="grid_score" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
         data-dojo-props="
         store:ds_sms_list,
                structure:[
                {name:'发送时间', field:'sms_time', width:'10em'},
                {name:'发送手机号', field:'sms_mobile', width:'8em'},
                {name:'发送内容', field:'sms_content', width:'100%'}
                ],
               plugins:{pagination:{pageSizes:[16,32,'all'],defaultPageSize:16}},">
    </div>
</div>
