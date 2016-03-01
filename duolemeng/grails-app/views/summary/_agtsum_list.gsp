<style type="text/css">
#appLayout {
    height: 91%;
}

.myimg{
    width:120px;
    height:120px;
    border: none;

}

.circle {
    -moz-border-radius: 60px;   /* 圆的半径为边长的一半，即100px */
    -webkit-border-radius: 60px;
    border-radius: 60px;
}

.btn {
    border: 0;
    width: 8em;
    padding: 0.3em;
    margin-left: 10%;
    background-color: #ff9500;
    color: #ffffff;
    font-size: medium;
}

</style>

<script>
    //标识位
    var global_symbol = 0;
    var global_agent_id = 0;
    var global_agent_iconid = 0;

    function func_agtsum_sch(value){
        ds_agtsum_list.url = "summary/ds_agtsum_list?&kword="+value;
        dijit.byId('grid_agtsum')._refresh();
    }

    function getpercent(a, b){
        if(b == 0){
            return "0"
        }else{
            return Math.ceil(a * 100 / b)
        }
    }

    function func_check_op(fields){
        var fmtstr ="<a href='javascript:void(0)' onclick='func_check_view(" + fields[1] + "," + fields[2] + "); return false;'>[" + fields[0] +"]&nbsp;&nbsp;查看</a>";
        return fmtstr;
    }

    function getcheckinfo(items){
        var html = "";
        for(i in items){
            var note = "";
            if(items[i].check_note){
                note = items[i].check_note
            }
            var tmpstr = "<div><br/>";
            tmpstr += "<div style='width: 130px;height: 130px;float:left'><img src='/file/load/"+ items[i].check_fileid + "' class='myimg'/></div>";
            tmpstr += "<div style='height: 130px'><br/>签到地点:<span style='margin-left: 3em'> "+ items[i].check_place +"</span><br/><br/>";
            tmpstr += "签到时间:<span style='margin-left: 3em'> "+ items[i].check_time +"</span><br/><br/>";
            tmpstr += "签到说明:<span style='margin-left: 3em'> "+ note +"</span>";
            tmpstr += "</div>"
            tmpstr += "<br/><hr/></div> ";

            html += tmpstr;
        }

        return html;
    }

    function func_check_view(a2, a3){
        global_symbol = 1;
        global_agent_id = a2;
        global_agent_iconid = a3;

        require(["dojo/dom-attr"], function(domAttr) {

            domAttr.set('basic_info', 'hidden', true);

            if (a3 == 0) {
                domAttr.set('agent_iconid', 'class', "myimg");
            } else {
                domAttr.set('agent_iconid', 'class', "myimg circle");
            }

            //头像
            domAttr.set('agent_iconid', 'src', "/file/load/"+ a3);

            var nstart_time = dojo.date.locale.format(start_time.value, {datePattern: "yyyy-MM-dd", selector: "date"});
            var nend_time =   dojo.date.locale.format(end_time.value, {datePattern: "yyyy-MM-dd", selector: "date"});

            //获取签到数据
            dojo.xhrGet({
                url:"summary/checklist?agent_id="+a2 + "&start_time="+nstart_time +"&end_time="+nend_time,
                handleAs: "json",
                load:function (data) {
                    domAttr.set('check_list', 'innerHTML', getcheckinfo(data));
                }
            });



            domAttr.set('check_info', 'hidden', false);
        });

        return true;
    }

</script>


<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_agtsum_list"
     data-dojo-props="url:'summary/ds_agtsum_list'"></div>

<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design:'sidebar', gutters:false, liveSplitters:false">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="splitter:false, region:'leading'" style="width: 60%">
        <label class="mlabel">经纪人列表</label>

        <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em; width: 25em"
               trim="true"  class="mright" onchange="func_agtsum_sch(this.value);" intermediateChanges="true"
               placeholder="经纪人/经纪人电话"/>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 90%; width: 98%;padding: 0">
            <div id="grid_agtsum" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
                store:ds_agtsum_list,
                structure:[
                    {name:'经纪人', field:'agent_name', width:'8em'},
                    {name:'电话', field:'agent_mobile',  width:'9em'},
                    {name:'签到天数', fields:['agent_checkall', 'id', 'agent_iconid'],  width:'6em', formatter:func_check_op},
                    {name:'注册时间', field:'agent_time',  width:'100%'}
                ],
                plugins:{indirectSelection: {name:'选择', width:'3em'},
                pagination:{pageSizes:[16, 32, 'all'], defaultPageSize: 16}},
                selectionMode:'single', rowSelector:'0px'"
                 %{--canSort="{ return false; }"--}%
            >
                <script type="dojo/on" data-dojo-event="rowClick" data-dojo-args="evt">
                    var idx = evt.rowIndex;
                    var item = this.getItem(idx);
                    if (0 != evt.cellIndex) {
                        this.rowSelectCell.toggleRow(idx, true);
                    }
                    else {
                        this.rowSelectCell.toggleRow(idx, false);
                    }

                     if(global_symbol == 1){
                        global_symbol = 0;
                        return;
                    }
                    global_symbol = 0;

                    //基本信息
                    var agent_id = this.store.getValue(item, 'id');

                    var agent_iconid = this.store.getValue(item, 'agent_iconid');
                    var agent_name = this.store.getValue(item, 'agent_name');
                    var agent_address = this.store.getValue(item, 'agent_address');
                    var agent_teamid = this.store.getValue(item, 'agent_teamid');
                    var agent_idcard = this.store.getValue(item, 'agent_idcard');

                     if(agent_teamid == 0){
                        agent_teamid = "不属于任何团队";
                    }

                    if(!agent_address){
                        agent_address = "";
                    }

                    if(!agent_idcard){
                        agent_idcard = "";
                    }

                    //账户信息
                    var at_usable = this.store.getValue(item, 'at_usable');
                    var at_fixed = this.store.getValue(item, 'at_fixed');
                    var total = at_usable + at_fixed;

                    //业绩
                    var cst_num = this.store.getValue(item, 'cst_num');
                    var bdregyes_num = this.store.getValue(item, 'bdregyes_num');
                    var bdregok_num = this.store.getValue(item, 'bdregok_num');
                    var cbregyes_num = this.store.getValue(item, 'cbregyes_num');
                    var cbregok_num = this.store.getValue(item, 'cbregok_num');

                    require(["dojo/dom-attr"], function(domAttr){
                        if(agent_iconid == 0){
                            domAttr.set('agent_icon', 'class', "myimg");
                        }else{
                            domAttr.set('agent_icon', 'class', "myimg circle");
                        }
                         //头像
                         domAttr.set('agent_icon', 'src', "/file/load/"+ agent_iconid);

                         //基本信息
                         var info = "姓名: " + agent_name + "<br/><br/>";
                         info += "身份证:" + agent_idcard + "<br/><br/>";
                         info += "地址:" + agent_address + "<br/><br/>";
                         info += "团队:" + agent_teamid;

                         domAttr.set('agent_info', 'innerHTML', info);

                         //账户信息
                         info = "";
                         info += "<label style='color: blue; font-size: 1.3em; padding-left:30%'>账户(" + total + "元)</label><br/><br/><br/>";
                         info += "<label style='color:green; font-size: 1.1em; padding-left:28%'>可用金额</label><label style='margin-left: 4em'>" + at_usable + "元</label><br/><br/>";
                         info += "<label style='color:green; font-size: 1.1em; padding-left:28%'>冻结金额</label><label style='margin-left: 4em'>" + at_fixed + "元</label>" ;

                         domAttr.set('account_info', 'innerHTML', info);

                          //业绩

                          var info = "";
                          info += "<span style='color: blue; font-size: 1.3em;padding-left:40%'>业绩(共" + cst_num +"个客户)</span><br/><br/>";
                          info += "<span style='color:green; font-size: 1.2em;padding-left:28%'>房</span><span style='margin-left: 4em;margin-right: 4em;'>报备成功" + bdregyes_num + "人(" + getpercent(bdregyes_num, cst_num) + "%)</span>   <span> 成交: " + bdregok_num + "人(" +  getpercent(bdregok_num, cst_num) + "%)</span><br/><br/>";
                          info += "<span style='color:green; font-size: 1.2em;padding-left:28%'>车</span><span style='margin-left: 4em;margin-right: 4em;'>报备成功" + cbregyes_num + "人(" + getpercent(cbregyes_num, cst_num) + "%)</span>    <span>成交: " + cbregok_num + "人(" +  getpercent(cbregok_num, cst_num) + "%)</span>";

                          domAttr.set('peform', 'innerHTML', info);

                          domAttr.set('check_info', 'hidden', true);
                          domAttr.set('basic_info', 'hidden', false);
                    });




                </script>
            </div>
        </div>

        <input type="text" data-dojo-type="dijit/form/TextBox"
               data-dojo-id="selidx_eventid" style="display: none;" />

    </div>

    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="splitter:false, region:'center'">
        <label class="mlabel">详情</label>

        <div id="appLayout" class="demoLayout" data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design: 'headline'">
            <div class="edgePanel" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'center'" id="content_area" contenteditable="false" aria-disabled="true"
                 style="background-color: white;border-color: #0083BB;">

                %{--经纪人基本信息,账户信息,业绩--}%
                <div id="basic_info" hidden="true">

                    <div style="width: 100%;align-content: center">
                        <div style="width: 200px;height: 130px;float:left">
                            <img src="/file/load/" id="agent_icon" style="padding: 0px;margin: 0px" class="myimg circle"/>
                        </div>
                        <div style="width: 400px;height: 130px;">

                            <span readonly="true" id="agent_info" style="border: none; background: none">
                            </span>
                        </div>
                    </div>
                    <br/>

                    <hr color="#0083BB"/>

                    <br/>

                    <div id="account_info">
                    </div>

                    <br/>
                    <hr color="#0083BB"/>

                    <br/><br/>
                    <div id="peform">

                    </div>

                </div>


                %{--经纪人签到记录--}%
                <div id="check_info" hidden="hidden">

                    <div style="text-align: center">
                            <img src="/file/load/" id="agent_iconid" style="padding: 0px;margin: 0px" class="myimg circle"/>
                    </div>

                    <hr color="#0083BB"/>

                    <br/>

                    <div id="time_select">
                        <span class="mleft">起始日期</span>
                        <input data-dojo-type="dijit/form/DateTextBox" data-dojo-id="start_time" value="${timeins.start_time?.format('yyyy-MM-dd')}" style="width: 6em"
                               lang="zh-cn" hasDownArrow="false" constraints="{datePattern:'yyyy-MM-dd'}"
                        />

                        <span style="margin-left: 3em" >结束日期</span>
                        <input name="dc_endtime" data-dojo-type="dijit/form/DateTextBox" value="${timeins.end_time?.format('yyyy-MM-dd')}" style="width: 6em" data-dojo-id="end_time"
                               lang="zh-cn" hasDownArrow="false" constraints="{datePattern:'yyyy-MM-dd'}"
                        />

                        <button type="submit" class="btn" onclick="func_check_view(global_agent_id, global_agent_iconid)">查看</button>
                    </div>

                    <br/><br/>
                    <div id="check_list">

                    </div>

                </div>

            </div>
        </div>
    </div>

</div>

