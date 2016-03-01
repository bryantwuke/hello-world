    <div data-dojo-type="dijit/layout/TabContainer" style="height: 36em; width: 100%; border: none" tabPosition="top" id="bd_main_panel">

        <div  data-dojo-type="dijit/layout/ContentPane"  data-dojo-props='title:"楼盘基本信息"'  id="bd_basic_panel">
            <form data-dojo-type="dijit/form/Form" id="frm_bd_basic" onshow="this.validate();"
                  data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_bd_basic_save').set('disabled', !isValid);}">

                <br/><br/>

                <input data-dojo-type="dijit/form/TextBox" name="id"
                       id="bd_id" value="${bdins.id}" style="display: none"
                />

                <div data-dojo-type="dojo/data/ItemFileReadStore" data-dojo-id="ds_city_list" data-dojo-props="url:'/query/citylist?reqnum=0'" >

                </div>


                <span class="mleft">楼盘名称:</span>
                <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20" missingMessage="楼盘名称不能为空"
                   trim="true" name="bd_name" value="${bdins.bd_name}" required="true"
                />

                <span class="mright">开发商名: </span>
                <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20" missingMessage="开发商不能为空"
                       trim="true" name="bd_developer" value="${bdins.bd_developer}" required="true"
                />
                <br/><br/><br/>


                <span class="mleft">所在城市: </span>
                <select data-dojo-type="dijit/form/ComboBox" name="bd_city" placeHolder="选择所在城市"  searchAttr="city" store="ds_city_list" autoComplete="true" maxHeight="200" missingMessage="请选择楼盘所在城市"
                    value="${bdins.bd_city}" required="required" onchange="getarealist(this.value);" >
                </select>

                <span class="mright">所在区域:</span>
                <select data-dojo-type="dijit/form/Select" name="bd_area" placeHolder="选择所在小区" autoComplete="true"  mzxHeight="150" id="sel_area" missingMessage="请选择楼盘所在的区"
                    required="required" style="width:15em">
                    <g:if test="${bdins.bd_area}">
                        <option value="${bdins.bd_area}">${bdins.bd_area}</option>
                    </g:if>
                </select>

                <br/><br/><br/>


                <span class="mleft">开盘时间:</span>
                <input data-dojo-type="dijit/form/DateTextBox" name="bd_opentime" value="${bdins.bd_opentime?.format('yyyy-MM-dd')}"
                       required="true" lang="zh-cn" hasDownArrow="false" constraints="{datePattern:'yyyy-MM-dd'}"
                />

                <span class="mright">楼盘地址:</span>
                <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50" missingMessage="楼盘地址不能为空"
                   trim="true" name="bd_address" value="${bdins.bd_address}" required="true"
                />
                <br/><br/><br/>

                <span class="mleft">产权年限:</span>
                <input data-dojo-type="dijit/form/NumberTextBox"
                   trim="true" name="bd_year" value="${bdins.bd_year}" required="true" missingMessage="产权年限不能为空"  invalidMessage="年限为大于20的整数" constraints="{ min:20, places:0}"
                />

                <span class="mright">在售房源:</span>
                <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="50"
                   trim="true" name="bd_house" value="${bdins.bd_house}"
                />


                <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
                <button data-dojo-type="dijit/form/Button" id="btn_bd_basic_save"
                        data-dojo-props="iconClass:'dijitIconSave'" disabled="disabled"
                        style="margin-left: 15em">保存
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        func_dlgsavegrid_multiform("bd_id", "building", "bd_basic", "grid_building");
                    </script>

                </button>

                <button type="button" data-dojo-type="dijit/form/Button" id="btn_bd_basic_reset"
                        data-dojo-props="iconClass:'dijitIconUndo'"
                        class="mleft">取消
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        dijit.byId('dlg_bd').hide();
                    </script>
                </button>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_basic_edit"
                        data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
                        class="mleft">修改
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                          disableall('frm_bd_basic',false);
                          getarealist('${bdins.bd_city}','${bdins.bd_area}');
                          dijit.byId('btn_bd_basic_edit').set('disabled', true);
                    </script>

                </button>

            </form>

        </div>



        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"楼盘其它信息"'>
            <form data-dojo-type="dijit/form/Form" id="frm_bd_other" onshow="this.validate();"
                  data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_bd_other_save').set('disabled', !isValid);}">
                <br/><br/>

                <span class="mleft">楼盘电话:</span>
                <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"
                   trim="true" name="bd_phone" value="${bdins.bd_phone}"
                />

                <span class="mright">案场电话:</span>
                <input data-dojo-type="dijit/form/ValidationTextBox" maxlength="20"
                   trim="true" name="bd_agenttel" value="${bdins.bd_agenttel}"
                />
                <br/><br/><br/>


                <span class="mleft">绿化比率:</span>
                <input data-dojo-type="dijit/form/NumberTextBox" placeholder="填写百分比分之值，默认为50"
                   trim="true" name="bd_greenrate" value="${bdins.bd_greenrate}" invalidMessage="绿化率为大于0的整数" constraints="{ min:0, places:0}"
                />

                <span class="mright">容积比率:</span>
                <input data-dojo-type="dijit/form/NumberTextBox" placeholder="填写百分比分子值,默认为50"
                   trim="true" name="bd_volumnrate" value="${bdins.bd_volumnrate}" invalidMessage="容积率为大于0的整数" constraints="{ min:0, places:0}"
                />
                <br/><br/><br/>

                <span class="mleft">楼盘均价:</span>
                <input data-dojo-type="dijit/form/NumberTextBox" invalidMessage="填入正确的正整数值" constraints="{ min:0, places:0}" missingMessage="楼盘均价不能为空"
                   trim="true" name="bd_price" value="${bdins.bd_price}"
                />

                <span class="mright">平均佣金:</span>
                <input data-dojo-type="dijit/form/NumberTextBox" placeholder="以万分之一为单位"
                   trim="true" name="bd_avgcash" value="${bdins.bd_avgcash}" required="true" invalidMessage="佣金比分子为大于0的整数" constraints="{ min:0, places:0}"
                />

                <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_other_save"
                        data-dojo-props="iconClass:'dijitIconSave'"
                        style="margin-left: 15em">保存
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        func_dlgsavegrid_multiform("bd_id", "building", "bd_other", "grid_building");
                    </script>

                </button>

                <button type="button" data-dojo-type="dijit/form/Button" id="btn_bd_other_reset"
                        data-dojo-props="iconClass:'dijitIconUndo'"
                        class="mleft">取消
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        dijit.byId('dlg_bd').hide();
                    </script>
                </button>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_other_edit"
                        data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
                        class="mleft">修改
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                          disableall('frm_bd_other',false);

                          dijit.byId('btn_bd_other_edit').set('disabled', true);
                    </script>

                </button>

            </form>


        </div>



        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"项目与家装"'>
            <form data-dojo-type="dijit/form/Form" id="frm_bd_pro" onshow="this.validate();"
                  data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_bd_pro_save').set('disabled', !isValid);}">

                <br/><br/>

                <span class="mleft">项目特色:</span>
                <input data-dojo-type="dijit/form/TextBox"
                   trim="true" name="bd_feature" value="${bdins.bd_feature}"
                />

                <span class="mright">建材家装:</span>
                <input data-dojo-type="dijit/form/TextBox"
                   trim="true" name="bd_decoration" value="${bdins.bd_decoration}"
                />
                <br/><br/><br/>

               <span class="mleft">项目结构:</span>
                <input data-dojo-type="dijit/form/TextBox"
                   trim="true" name="bd_structure" value="${bdins.bd_structure}"
                />

                <span class="mright">物业费用:</span>
                <input data-dojo-type="dijit/form/NumberTextBox"
                   trim="true" name="bd_propcost" value="${bdins.bd_propcost}" required="true" invalidMessage="物业费为数字" constraints="{ min:0, places:0}"
                />

                <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_pro_save"
                        data-dojo-props="iconClass:'dijitIconSave'"
                        style="margin-left: 15em">保存
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        func_dlgsavegrid_multiform("bd_id", "building", "bd_pro", "grid_building");
                    </script>

                </button>

                <button type="button" data-dojo-type="dijit/form/Button" id="btn_bd_pro_reset"
                        data-dojo-props="iconClass:'dijitIconUndo'"
                        class="mleft">取消
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        dijit.byId('dlg_bd').hide();
                    </script>
                </button>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_pro_edit"
                        data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
                        class="mleft">修改
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                          disableall('frm_bd_pro',false);

                          dijit.byId('btn_bd_pro_edit').set('disabled', true);
                    </script>

                </button>

            </form>

        </div>



        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"类型与卖点"'>
            <form data-dojo-type="dijit/form/Form" id="frm_bd_type" onshow="this.validate();"
                  data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_bd_type_save').set('disabled', !isValid);}">

                <input data-dojo-type="dijit/form/TextBox" name="type_tag_content" data-dojo-id="type_tag_id"
                       id="type_tag_id" style="display: none"
                />

                <input data-dojo-type="dijit/form/TextBox" name="bd_tag_content"   data-dojo-id="bd_tag_id"
                       id="bd_tag_id" style="display: none"
                />

                <div data-dojo-type="dijit/layout/TabContainer" style="height: 26em; width: 100%" tabPosition="left">
                    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"楼盘类型"' id="type_tag">
                        <g:each var="item" status="si" in="${bdins.typelist}">
                            <input data-dojo-type="dijit.form.CheckBox" value="${item}" id="bdtype${si}"
                               <g:if test="${bdins.type_tag.contains(item)}">data-dojo-props="checked: true"</g:if>
                            >
                            <label class="ml1 mw10 mdib" for="bdtype${si}">${item}</label>
                            <g:if test="${si%4==3}"><br /></g:if>
                        </g:each>
                    </div>

                    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"楼盘卖点"' id="bd_tag">
                        <g:each var="item" status="si" in="${bdins.taglist}">
                            <input data-dojo-type="dijit.form.CheckBox" value="${item}" id="bdtag${si}"
                               <g:if test="${bdins.bd_tag.contains(item)}">data-dojo-props="checked: true"</g:if>
                            >
                            <label class="ml1 mw10 mdib" for="bdtag${si}">${item}</label>
                            <g:if test="${si%4==3}"><br /></g:if>
                        </g:each>
                    </div>

                </div>

                <br/><br/>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_type_save"
                        data-dojo-props="iconClass:'dijitIconSave'"
                        style="margin-left: 15em">保存
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        var typetags = dojo.query("#type_tag input[type=checkbox]:checked");
                        var bdtags = dojo.query("#bd_tag input[type=checkbox]:checked");

                        var bd_tag = "";
                        var type_tag = "";

                        for(i=0;i< typetags.length;i++){
                            if(i == 0){
                                type_tag = typetags[i].value;
                            }else{
                                type_tag = type_tag + "_" + typetags[i].value;
                            }
                        }

                        for(i=0;i< bdtags.length;i++){
                            if(i == 0){
                                bd_tag = bdtags[i].value;
                            }else{
                                bd_tag = bd_tag + "_" + bdtags[i].value;
                            }
                        }

                        bd_tag_id.set('value',bd_tag);
                        type_tag_id.set('value',type_tag);


                        func_dlgsavegrid_multiform("bd_id", "building", "bd_type", "grid_building");
                    </script>

                </button>

                <button type="button" data-dojo-type="dijit/form/Button" id="btn_bd_type_reset"
                        data-dojo-props="iconClass:'dijitIconUndo'"
                        class="mleft">取消
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        dijit.byId('dlg_bd').hide();
                    </script>
                </button>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_type_edit"
                        data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
                        class="mleft">修改
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                          disableall('frm_bd_type',false);

                          dijit.byId('btn_bd_type_edit').set('disabled', true);
                    </script>

                </button>

            </form>

        </div>



        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"楼盘周边"'>

            <form data-dojo-type="dijit/form/Form" id="frm_bd_around" onshow="this.validate();"
                  data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_bd_around_save').set('disabled', !isValid);}">

                <div data-dojo-type="dijit/layout/TabContainer" style="height: 26em; width: 100%" tabPosition="left">

                    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"周边交通"'>
                        <textArea data-dojo-type="dijit/form/SimpleTextarea" name="bd_transport" style="height: 95%;width: 99%" maxlength="200" trim="true">
                            ${bdins.bd_transport}
                        </textArea>
                    </div>


                    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"周边教育"'>
                        <textArea data-dojo-type="dijit/form/SimpleTextarea" name="bd_education" style="height: 95%;width: 99%" maxlength="200" trim="true">
                            ${bdins.bd_education}
                        </textArea>

                    </div>

                    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"周边商业"'>
                        <textArea data-dojo-type="dijit/form/SimpleTextarea" name="bd_commercial" style="height: 95%;width: 99%" maxlength="200" trim="true">
                            ${bdins.bd_commercial}
                        </textArea>

                    </div>

                    <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"周边娱乐"'>
                        <textArea data-dojo-type="dijit/form/SimpleTextarea" name="bd_entertain" style="height: 95%;width: 99%" maxlength="200" trim="true">
                            ${bdins.bd_entertain}
                        </textArea>
                    </div>

                </div>

                <br/><br/>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_around_save"
                        data-dojo-props="iconClass:'dijitIconSave'"
                        style="margin-left: 15em">保存
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        func_dlgsavegrid_multiform("bd_id", "building", "bd_around", "grid_building");
                    </script>

                </button>

                <button type="button" data-dojo-type="dijit/form/Button" id="btn_bd_around_reset"
                        data-dojo-props="iconClass:'dijitIconUndo'"
                        class="mleft">取消
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        dijit.byId('dlg_bd').hide();
                    </script>
                </button>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_around_edit"
                        data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
                        class="mleft">修改
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                          disableall('frm_bd_around',false);

                          dijit.byId('btn_bd_around_edit').set('disabled', true);
                    </script>

                </button>

            </form>
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props='title:"佣金说明"'>
            <form data-dojo-type="dijit/form/Form" id="frm_bd_rule" onshow="this.validate();"
                  data-dojo-props="onValidStateChange:function(isValid){dijit.byId('btn_bd_rule_save').set('disabled', !isValid);}">

                <textArea data-dojo-type="dijit/form/SimpleTextarea" name="bd_cashrule" style="height: 85%;width: 99%" maxlength="200" trim="true">
                    ${bdins.bd_cashrule}
                </textArea>

                <br/><br/>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_rule_save"
                        data-dojo-props="iconClass:'dijitIconSave'"
                        style="margin-left: 15em">保存
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        func_dlgsavegrid_multiform("bd_id", "building", "bd_rule", "grid_building");
                    </script>

                </button>

                <button type="button" data-dojo-type="dijit/form/Button" id="btn_bd_rule_reset"
                        data-dojo-props="iconClass:'dijitIconUndo'"
                        class="mleft">取消
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                        dijit.byId('dlg_bd').hide();
                    </script>
                </button>

                <button data-dojo-type="dijit/form/Button" id="btn_bd_rule_edit"
                        data-dojo-props="iconClass:'dijitIconEdit'" disabled="disabled"
                        class="mleft">修改
                    <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
                          disableall('frm_bd_rule',false);

                          dijit.byId('btn_bd_rule_edit').set('disabled', true);
                    </script>

                </button>

            </form>
        </div>

    </div>
