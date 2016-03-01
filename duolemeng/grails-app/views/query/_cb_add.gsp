<div data-dojo-type="dijit/layout/TabContainer" style="height: 36em; width: 100%" tabPosition="top" id="cb_main_panel">

    <div  data-dojo-type="dijit/layout/ContentPane"  data-dojo-props='title:"车行基本信息"' id="cb_basic_panel">

        <form data-dojo-type="dijit/form/Form" id="frm_cb_basic" onshow="this.validate();"
                  data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_cb_basic_save').set('disabled', !isValid);}">

            <br/><br/>

            <input data-dojo-type="dijit/form/TextBox" name="id"
                       id="cb_id" value="${cbins.id}" style="display: none"
            />

            <div data-dojo-type="dojo/data/ItemFileReadStore" data-dojo-id="ds_city_list" data-dojo-props="url:'/query/citylist?reqnum=0'" >
            </div>


            <span class="mleft">车行名称:</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20" missingMessage="车行名称不能为空" trim="true" name="cb_name" value="${cbins.cb_name}" required="true"
            />

            <span class="mright">车行简称:</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="8" missingMessage="车行简称不能为空" trim="true" name="cb_shortname" value="${cbins.cb_shortname}" required="true"
            />

            <br/><br/><br/>


            <span class="mleft">所在城市: </span>
            <select data-dojo-type="dijit/form/ComboBox" name="cb_city" placeHolder="选择所在城市"  searchAttr="city" store="ds_city_list" autoComplete="true" maxHeight="200" missingMessage="请选择楼盘所在城市" value="${cbins.cb_city}" required="required" onchange="getarealist(this.value);" >
            </select>

            <span class="mright">所在区域:</span>
            <select data-dojo-type="dijit/form/Select" name="cb_area" placeHolder="选择所在区" autoComplete="true"  mzxHeight="150" id="sel_area" missingMessage="请选择所在的区" required="required" style="width:15em">
                <g:if test="${cbins.cb_area}">
                    <option value="${cbins.cb_area}">${cbins.cb_area}</option>
                </g:if>
            </select>

            <br/><br/><br/>


            <span class="mleft">车行电话: </span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20" trim="true" name="cb_phone" value="${cbins.cb_phone}"
            />

            <span class="mright">平均佣金:</span>
            <input data-dojo-type="dijit/form/NumberTextBox" name="cb_avgcash" value="${cbins.cb_avgcash}"
                   required="true" invalidMessage="平均佣金为数字" constraints="{ min:0, places:0}"
            />

            <br/><br/><br/>

            <span class="mleft">车行地址:</span>
            <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50" missingMessage="车行地址不能为空" trim="true" name="cb_address" value="${cbins.cb_address}"
                   required="true" style="width: 50em"
            />


            <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

            <button data-dojo-type="dijit/form/Button" id="btn_cb_basic_save"
                    data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
                    style="margin-left: 15em">保存
                <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                    func_dlgsavegrid_multiform("cb_id", "query", "cb_basic", "grid_cb");
                </script>

            </button>

            <button type="button" data-dojo-type="dijit/form/Button" id="btn_cb_basic_reset"
                    data-dojo-props="iconClass:'dijitIconUndo'"
                    class="mleft">取消
                <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                    dijit.byId('dlg_cb').hide();
                </script>
            </button>

            <button data-dojo-type="dijit/form/Button" id="btn_cb_basic_edit"
                    data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
                    class="mleft">修改
                <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                    disableall('frm_cb_basic',false);
                    getarealist('${cbins.cb_city}','${cbins.cb_area}');
                    dijit.byId('btn_cb_basic_edit').set('disabled', true);
                </script>

            </button>

        </form>

    </div>

    %{--  --}%
    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"品牌与卖点"'>
        <form data-dojo-type="dijit/form/Form" id="frm_cb_type" onshow="this.validate();"
          data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_cb_type_save').set('disabled', !isValid);}">

            <input data-dojo-type="dijit/form/TextBox" name="brand_content" data-dojo-id="brand_content"
               style="display: none"
            />

           <input data-dojo-type="dijit/form/TextBox" name="salepoint_content"   data-dojo-id="salepoint_content"
               style="display: none"
           />

           <input data-dojo-type="dijit/form/TextBox" name="salelevel_content"   data-dojo-id="salelevel_content"
               style="display: none"
           />

            <div data-dojo-type="dijit/layout/TabContainer" style="height: 28em; width: 100%" tabPosition="left">
                <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"主营品牌"' id="brand_tag">
                    <g:each var="item" status="si" in="${cbins.typelist}" >
                        <input data-dojo-type="dijit/form/CheckBox"  value="${item}" id="type${si}"
                               <g:if test="${cbins.type_tag.contains(item)}">data-dojo-props="checked: true"</g:if>
                        >
                        <label class="ml1 mw10 mdib" for="type${si}">${item}</label>
                        <g:if test="${si%4==3}"><br /></g:if>
                    </g:each>
                </div>

                <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"卖点"' id="sale_point">
                    <g:each var="item" status="si" in="${cbins.taglist}">
                        <input data-dojo-type="dijit/form/CheckBox"  value="${item}" id="cbtag${si}"
                               <g:if test="${cbins.cb_tag.contains(item)}">data-dojo-props="checked: true"</g:if>
                        >
                        <label class="ml1 mw10 mdib" for="cbtag${si}">${item}</label>
                        <g:if test="${si%4==3}"><br /></g:if>
                    </g:each>
                </div>

                <g:set var="count" value="${0}"/>

                <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"车级别"' id="sale_level">
                    <g:each var="item" status="si" in="${roomsale.Enum_CarLevel.values()}">
                        <g:if test="${item.code != roomsale.Enum_CarLevel.CL_all.code}">
                            <input  data-dojo-type="dijit/form/CheckBox"  value="${item.name}" id="cblevel${si}"
                               <g:if test="${cbins.cb_level.contains(item.name)}">data-dojo-props="checked: true"</g:if>
                            >
                            <label class="ml1 mw10 mdib" for="cblevel${si}">${item.name}</label>

                            <g:if test="${count%4==3}"><br/></g:if>

                            <g:set var="count" value="${count + 1 }"/>
                        </g:if>

                    </g:each>
                </div>

            </div>


            <br/>
            <button data-dojo-type="dijit/form/Button" id="btn_cb_type_save"
                    data-dojo-props="iconClass:'dijitIconSave'"
                    style="margin-left: 15em">保存
                <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                    var brands = dojo.query("#brand_tag input[type=checkbox]:checked");
                    var points = dojo.query("#sale_point input[type=checkbox]:checked");
                    var levels = dojo.query("#sale_level input[type=checkbox]:checked");

                    var cb_brands = "";
                    var cb_points = "";
                    var cb_levels = "";

                    for(i=0;i< brands.length;i++){
                        if(i == 0){
                            cb_brands = brands[i].value;
                        }else{
                            cb_brands = cb_brands + "_" + brands[i].value;
                        }
                    }

                    for(i=0;i< points.length;i++){
                        if(i == 0){
                            cb_points = points[i].value;
                        }else{
                            cb_points = cb_points + "_" + points[i].value;
                        }
                    }

                    for(i=0;i< levels.length;i++){
                        if(i == 0){
                            cb_levels = levels[i].value;
                        }else{
                            cb_levels = cb_levels + "_" + levels[i].value;
                        }
                    }

                    brand_content.set('value',cb_brands);
                    salepoint_content.set('value',cb_points);
                    salelevel_content.set('value',cb_levels);


                    func_dlgsavegrid_multiform("cb_id", "query", "cb_type", "grid_cb");

                </script>

            </button>

            <button type="button" data-dojo-type="dijit/form/Button" id="btn_cb_type_reset"
                    data-dojo-props="iconClass:'dijitIconUndo'"
                    class="mleft">取消
                <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                    dijit.byId('dlg_cb').hide();
                </script>
            </button>

            <button data-dojo-type="dijit/form/Button" id="btn_cb_type_edit"
                    data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
                    class="mleft">修改
                <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                    disableall('frm_cb_type',false);
                    dijit.byId('btn_cb_type_edit').set('disabled', true);
                </script>

            </button>

        </form>
    </div>



    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"其它信息"'>
        <form data-dojo-type="dijit/form/Form" id="frm_cb_other" onshow="this.validate();"
              data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_cb_other_save').set('disabled', !isValid);}">
            <div data-dojo-type="dijit/layout/TabContainer" style="height: 28em; width: 100%" tabPosition="left">

                <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"车行简介"'>
                    <textArea data-dojo-type="dijit/form/SimpleTextarea" name="cb_brief" style="height: 95%;width: 99%" maxlength="200" trim="true">
                        ${cbins.cb_brief}
                    </textArea>
                </div>

                <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"售后服务"'>
                    <textArea data-dojo-type="dijit/form/SimpleTextarea" name="cb_aftersale" style="height: 95%;width: 99%" maxlength="100" trim="true">
                        ${cbins.cb_aftersale}
                    </textArea>

                </div>

                <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"车贷介绍"'>
                    <textArea data-dojo-type="dijit/form/SimpleTextarea" name="cb_loan" style="height: 95%;width: 99%" maxlength="100" trim="true">
                        ${cbins.cb_loan}
                    </textArea>

                </div>

                <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"佣金说明"'>
                    <textArea data-dojo-type="dijit/form/SimpleTextarea" name="cb_cashrule" style="height: 95%;width: 99%" maxlength="200" trim="true">
                        ${cbins.cb_cashrule}
                    </textArea>
                </div>


            </div>

            <br/>
            <button data-dojo-type="dijit/form/Button" id="btn_cb_other_save"
                    data-dojo-props="iconClass:'dijitIconSave'"
                    style="margin-left: 15em">保存
                <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                    func_dlgsavegrid_multiform("cb_id", "query", "cb_other", "grid_cb");
                </script>

            </button>

            <button type="button" data-dojo-type="dijit/form/Button" id="btn_cb_other_reset"
                    data-dojo-props="iconClass:'dijitIconUndo'"
                    class="mleft">取消
                <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                    dijit.byId('dlg_cb').hide();
                </script>
            </button>

            <button data-dojo-type="dijit/form/Button" id="btn_cb_other_edit"
                    data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
                    class="mleft">修改
                <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                    disableall('frm_cb_other',false);
                    dijit.byId('btn_cb_other_edit').set('disabled', true);
                </script>

            </button>

        </form>
    </div>
</div>


