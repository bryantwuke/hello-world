<style type="text/css">
    #appLayout {
        height: 91%;
    }

    .teamimg{
        width:100px;
        height:100px;
        border: none;

    }

    .circle {
        -moz-border-radius: 50px;   /* 圆的半径为边长的一半，即100px */
        -webkit-border-radius: 50px;
        border-radius: 50px;
    }

</style>

<script>
    function func_team_sch(value){
        ds_team_list.url = "summary/ds_team_list?&kword="+value;
        dijit.byId('grid_team')._refresh();
    }

    function getpercent(a, b){
        if(b == 0){
            return "0"
        }else{
            return Math.ceil(a * 100 / b)
        }
    }

    function setteam(items){
        var html = "";
        for(i in items){
            var tmpstr = "<div>";
            tmpstr += "<span style='color: #0099CC; font-size: 1.3em'>" + items[i].agent_name;
            tmpstr += "(" + items[i].agent_mobile + ") 共" + items[i].cst_num +"个客户</span><br/><br/>";
            tmpstr += "<span style='color:green; font-size: 1.2em'>房</span><span style='margin-left: 4em;margin-right: 4em;'>报备成功" + items[i].bdregyes_num + "人(" + getpercent(items[i].bdregyes_num, items[i].cst_num) + "%)</span>   <span> 成交: " + items[i].bdregok_num + "人(" +  getpercent(items[i].bdregok_num, items[i].cst_num) + "%)</span><br/><br/>";
            tmpstr += "<span style='color:green; font-size: 1.2em'>车</span><span style='margin-left: 4em;margin-right: 4em;'>报备成功" + items[i].cbregyes_num + "人(" + getpercent(items[i].cbregyes_num, items[i].cst_num) + "%)</span>    <span>成交: " + items[i].cbregok_num + "人(" +  getpercent(items[i].cbregok_num, items[i].cst_num) + "%)</span>";
            tmpstr += "<br/><br/><hr/> ";
            tmpstr += "</div>";

            html += tmpstr;
        }

        return html;
    }
</script>


<div data-dojo-type="dojox/data/QueryReadStore" data-dojo-id="ds_team_list"
     data-dojo-props="url:'summary/ds_team_list'"></div>

<div data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design:'sidebar', gutters:false, liveSplitters:false">
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="splitter:false, region:'leading'" style="width: 60%">
        <label class="mlabel">团队列表</label>

        <input data-dojo-type="dijit/form/TextBox" maxlength="50" style="margin-left: 0em; width: 25em"
               trim="true"  class="mright" onchange="func_team_sch(this.value);" intermediateChanges="true"
               placeholder="团号/团队名称/团长电话"/>

        <div data-dojo-type="dijit/layout/ContentPane" style="height: 90%; width: 98%;padding: 0">
            <div id="grid_team" data-dojo-type="dojox/grid/EnhancedGrid" selectable="true"
                 data-dojo-props="
                store:ds_team_list,
                structure:[
                    {name:'团队名称', field:'team_name', width:'8em'},
                    {name:'团号', field:'team_no',  width:'100%'},
                    {name:'团长', field:'agent_name',  width:'6em'},
                    {name:'团长电话', field:'agent_mobile',  width:'12em'}
                ],
                plugins:{indirectSelection: {name:'选择', width:'3em'},
                pagination:{pageSizes:[16, 32, 'all'], defaultPageSize: 16}},
                selectionMode:'single', rowSelector:'0px'"
                 canSort="{ return false; }"
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

                    var team_id = this.store.getValue(item, 'id');

                    var team_iconid = this.store.getValue(item, 'team_iconid');
                    var team_name = this.store.getValue(item, 'team_name');
                    var team_no = this.store.getValue(item, 'team_no');

                    require(["dojo/dom-attr"], function(domAttr){
                        if(team_iconid == 0){
                            domAttr.set('team_icon', 'class', "teamimg");
                        }else{
                            domAttr.set('team_icon', 'class', "teamimg circle");
                        }

                         domAttr.set('team_icon', 'src', "/file/load/"+ team_iconid);
                         pics_div.hidden = false;

                        domAttr.set('team_name', 'innerHTML', team_name + "<br/>(" +  team_no+ ")");
                         team_info.hidden = false;
                    });


                    dojo.xhrGet({
                         url:"summary/memberlist?id="+team_id,
                         handleAs: "json",
                         load:function (data) {
                             require(["dojo/dom-attr"], function(domAttr){
                             domAttr.set('tmemlist', 'innerHTML', setteam(data.team_mate));
                             domAttr.set('tmemlist', 'style', 'text-align:center');
                             });
                         }
                   });

                </script>
            </div>
        </div>

        <input type="text" data-dojo-type="dijit/form/TextBox"
               data-dojo-id="selidx_eventid" style="display: none;" />

    </div>

    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="splitter:false, region:'center'">
        <label class="mlabel">团队业绩</label>

        <div id="appLayout" class="demoLayout" data-dojo-type="dijit/layout/BorderContainer" data-dojo-props="design: 'headline'">
            <div class="edgePanel" data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region: 'center'" id="content_area" contenteditable="false" aria-disabled="true"
                 style="background-color: white;border-color: #0083BB;">
                <div>

                    <div style="text-align: center" hidden="hidden" data-dojo-id="pics_div" id="pics_div">
                        <div id="pic_gallery" align="center">
                            <img src="/file/load/" id="team_icon" style="padding: 0px;margin: 0px" class="teamimg circle"/>
                        </div>
                        <br/>

                    </div>

                    <div style="text-align: center" hidden="hidden" data-dojo-id="team_info" id="team_info">
                        <span readonly="true" id="team_name"
                              style="border: none; background: none">

                        </span>

                        <hr color="#0083BB"/>
                    </div>


                    <div id="tmemlist" style="display: none">

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

