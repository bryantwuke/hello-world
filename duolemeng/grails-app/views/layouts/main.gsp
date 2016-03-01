<%@ page import="sysuser.Enum_userstatus" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>多乐檬经纪人版后台运营系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="Bookmark" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">


    <link rel="stylesheet" href='${resource(dir:"/js/dojo/${grailsApplication.config.mydojo}/dijit/themes/claro/", file:"claro.css")}'/>
    <link rel="stylesheet" href='${resource(dir:"/js/dojo/${grailsApplication.config.mydojo}/dojo/resources/",file:"dojo.css")}'/>
    <link rel="stylesheet"
          href='${resource(dir:"/js/dojo/${grailsApplication.config.mydojo}/dojox/grid/enhanced/resources/", file:"claroEnhancedGrid.css")}'/>
    <link rel="stylesheet"
          href='${resource(dir:"/js/dojo/${grailsApplication.config.mydojo}/dojox/form/resources/",file:"UploaderFileList.css")}'/>
    <link rel="stylesheet"
          href='${resource(dir:"/js/dojo/${grailsApplication.config.mydojo}/dojox/form/resources/", file: "CheckedMultiSelect.css")}'/>
    <link rel="stylesheet"
          href='${resource(dir:"/js/dojo/${grailsApplication.config.mydojo}/dojox/editor/plugins/resources/css/", file:"LocalImage.css")}'/>
    <link rel="stylesheet" href='${resource(dir:"/js/dojo/${grailsApplication.config.mydojo}/dojox/form/resources/" ,file:"FileUploader.css")}'/>

    <script data-dojo-config="async: 1, parseOnLoad: 1, isDebug: 0, showSpinner: true, locale: 'zh-cn'"
            src='${resource(dir:"/js/dojo/${grailsApplication.config.mydojo}/dojo/", file:"dojo.js")}'></script>

    %{--<asset:stylesheet src="application.css"/>--}%
    <asset:javascript src="smartop.js"/>

    <style type="text/css">

    td, th {
        padding: 2px 0.5em;
    }

    html, body {
        height: 100%;
        width: 100%;
        padding: 0;
        border: 0;
        overflow: hidden;
        background: #0083bb;
    }

    #main {
        height: 100%;
        width: 100%;
        border: 0;
    }

    #header {
        margin: 0;
    }

    #leftAccordion {
        width: 25%;
    }

    /* pre-loader specific stuff to prevent unsightly flash of unstyled content */
    #loader {
        padding: 0;
        margin: 0;
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: #ededed;
        z-index: 999;
        vertical-align: middle;
    }

    #loaderInner {
        padding: 5px;
        position: relative;
        left: 0;
        top: 0;
        width: 175px;
        background: #3c3;
        color: #fff;
    }

    .MM li {
        padding: 3px 0px 3px 4px;
    }

    .MM {
        list-style: none;
        text-align: left;
        cursor: pointer;
        margin: 0px;
        padding: 0px;
    }

    .MM li:hover {
        /*font-size: 16px;*/
        color: white;
        background-image: url('${resource(dir:"images/", file: "m_hover.png")}');
    }

    .myhr {
        margin: 5px 2px 5px;
        border: none;
        border-top: 1px solid #d3d3d3;
    }

    .mdib {
        display: inline-block;
    }

    .ml1 {
        margin-left: 1em;
    }

    .mleft {
        margin-left: 2em;
    }

    .ml3em {
        margin-left: 3em;
    }

    .mright {
        margin-left: 15em;
    }

    .mwidth {
        width: 15em;
    }

    .mw10 {
        width: 10em;
    }

    .mlwidth {
        width: 50em;
    }

    %{--财务分析表格控件的宽度--}%
    .fwidth {
        width: 100%;
        border: none;
    }

    .mheight {
        height: 5em;
    }

    .mbox {
        overflow-y: auto;
        border: 1px solid #d3d3d3;
        padding: 0px;
    }

    .mtitle {
        height: 2em;
        line-height: 2em;
        background-color: #0083bb;
        color: #ffffff;
        margin: 1px;
    }

    .mbtitle {
        height: 4em;
        line-height: 4em;
        background-color: #691e55;
        color: #ffffff;
        margin: 1px;
    }

    .mbutton {
        margin-right: 2em;
    }

    .mdisabled {
        color: #757575;
        /*color: #818181;*/
        border-color: #d3d3d3;
        background-color: #efefef;
        background-image: none;
    }

    .mgrid {
        width: 100%;
        min-height: 20em;
        height: 70%;
    }

    .mbtnright {
        float: right;
        margin-right: 3em;
    }

    .m1emright {
        margin-right: 1em;
    }

    .mlabel {
        line-height: 24px;
        height: 24px;
        margin-left: 2em
    }

    .claro .dijitDialog {
        background: #F0FFFF;
    }

    .claro .dijitDialogPaneContent {
        background: #F0FFFF;
    }

    .claro .dijitTabContainerTop-dijitContentPane,
    .claro .dijitAccordionContainer-dijitContentPane {
        background-color: #F0FFFF;
    }

    .claro .dijitSplitContainer-dijitContentPane,
    .claro .dijitBorderContainer-dijitContentPane {
        background-color: #F0FFFF;
    }

    .claro .dijitTabChecked {
        background-color: #0080ff;
    }

    .claro .dojoxGridHeader {
        background-color: #0083bb;
        color: #ffffff;
        font-weight: bolder;
    }

    .claro .dojoxGridRowOdd .dojoxGridRowTable tr {
        background-color: #E4EEFA;
    }

    .claro .dojoxGridRowSelected .dojoxGridRowTable tr {
        background-color: #FFB200;
    }

    .claro .dojoxGrid {
        border: 1px solid #0083bb;
    }

    .dojoxGridCell {
        white-space: nowrap;
        overflow: hidden;
    }

    .claro .dijitSelectDisabled, .claro .dijitTextBoxDisabled, .claro .dijitTextBoxDisabled .dijitInputInner {
        color: rgba(16, 12, 14, 0.99);
    }

    .dj_webkit .claro .dijitTextBoxDisabled input {
        color: rgba(16, 12, 14, 0.99);
    }

    .dj_webkit .claro textarea.dijitTextAreaDisabled {
        color: rgba(16, 12, 14, 0.99);
    }

    .dijitCalendarDisabledDate {
        color: rgba(16, 12, 14, 0.99);
    }


    </style>

    <script>
        function fmt_regstatus(code) {
            switch (code) {
                    <g:each in="${roomsale.Enum_Regstatus.values()}">
                case ${it.code}:
                    return "${it.name}";
                    </g:each>
                default :
                    return '-';
            }
        }


    </script>

</head>


<body class="claro">

<div id="mainbc" data-dojo-type="dijit/layout/BorderContainer"
     style="width: 100%; height: 100%;">
    <div data-dojo-type="dijit/layout/ContentPane" region="top"
         style="height: 3em; width: 100%; overflow: hidden; border: none; background:#0083bb">
        <g:render template="/layouts/header"/>
    </div>

    <div data-dojo-type="dijit/layout/AccordionContainer" region="leading" splitter="true" minSize="1em"
         data-dojo-props="doLayout:true" style="width: 9em;">

        <g:if test="${session.userid && session.userid != -1}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_1500" title="个人信息" style="padding: 0px;">
                <ul class="MM">
                    <li id="menu_1510" onclick="menuclick(this);
                    func_ajaxget('back_user/userinfo');">个人信息</li>

                </ul>
            </div>
        </g:if>


        <g:if test="${'r_bdself' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_100" title="我的楼盘" style="padding: 0px;">
                <ul class="MM">
                    %{-- Tab切换，区分未处理的报备，已处理的报备 --}%
                    <li id="menu_110" onclick="menuclick(this);
                    func_ajaxget('query/bdsublist');">楼盘报备</li>

                    %{-- Tab切换，区分未审核的优惠，已审核的优惠，未审核的，可以修改 --}%
                    <li id="menu_120" onclick="menuclick(this);
                    func_ajaxget('building/discountlist?reftype=${roomsale.Enum_Reftype.RT_building.code}');">楼盘优惠</li>

                    %{-- Tab切换，区分未审核的优惠，已审核的优惠，未审核的，可以修改 --}%
                    <li id="menu_130" onclick="menuclick(this);
                    func_ajaxget('building/newslist?reftype=${roomsale.Enum_Reftype.RT_building.code}');">楼盘公告</li>
                </ul>
            </div>
        </g:if>

        <g:if test="${'r_bdcorp' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_300" title="楼盘合作" style="padding: 0px;">
                <ul class="MM">

                    %{-- 人员管理在楼盘列上 --}%
                    <li id="menu_310" onclick="menuclick(this);
                    func_ajaxget('building/bdlist');">合作楼盘</li>

                    <li id="menu_320" onclick="menuclick(this);
                    func_ajaxget('building/taglist?tag_type=${roomsale.Enum_Tagtype.TT_bd_type.code}');">楼盘类型</li>

                    <li id="menu_330" onclick="menuclick(this);
                    func_ajaxget('building/taglist?tag_type=${roomsale.Enum_Tagtype.TT_bd_tag.code}');">楼盘卖点</li>
                </ul>
            </div>
        </g:if>

        <g:if test="${'r_bdop' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_400" title="楼盘优惠审核" style="padding: 0px;">
                <ul class="MM">

                    %{-- Tab切换，区分未审核，已审核 --}%
                    <li id="menu_410" onclick="menuclick(this);
                    func_ajaxget('building/newschecklist?reftype=${roomsale.Enum_Reftype.RT_building.code}');">公告审核</li>

                    <li id="menu_420" onclick="menuclick(this);
                    func_ajaxget('building/dcchecklist?reftype=${roomsale.Enum_Reftype.RT_building.code}');">优惠审核</li>
                </ul>
            </div>
        </g:if>

        <g:if test="${'r_carself' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_200" title="我的4S" style="padding: 0px;">
                <ul class="MM">
                    %{-- Tab切换，区分未处理的报备，已处理的报备 --}%
                    <li id="menu_210" onclick="menuclick(this);
                    func_ajaxget('query/cbsublist');">车行报备</li>

                    %{-- Tab切换，区分未审核的优惠，已审核的优惠，未审核的，可以修改 --}%
                    <li id="menu_220" onclick="menuclick(this);
                    func_ajaxget('building/discountlist?reftype=${roomsale.Enum_Reftype.RT_car.code}');">车行优惠</li>

                </ul>
            </div>
        </g:if>

        <g:if test="${'r_carcorp' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_600" title="车行合作" style="padding: 0px;">
                <ul class="MM">

                    %{-- 人员管理在楼盘列上 --}%
                    <li id="menu_610" onclick="menuclick(this);
                    func_ajaxget('query/cblist');">合作车行</li>

                    <li id="menu_620" onclick="menuclick(this);
                    func_ajaxget('building/taglist?tag_type=${roomsale.Enum_Tagtype.TT_car_tag.code}');">车行类型</li>

                    <li id="menu_630" onclick="menuclick(this);
                    func_ajaxget('building/taglist?tag_type=${roomsale.Enum_Tagtype.TT_car_type.code}');">汽车品牌</li>
                </ul>
            </div>
        </g:if>

        <g:if test="${'r_carop' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_500" title="车行优惠审核" style="padding: 0px;">
                <ul class="MM">
                    %{-- Tab切换，区分未审核，已审核 --}%
                    <li id="menu_510" onclick="menuclick(this);
                    func_ajaxget('building/dcchecklist?reftype=${roomsale.Enum_Reftype.RT_car.code}');">优惠审核</li>
                </ul>
            </div>
        </g:if>

        <g:if test="${'r_back' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_900" title="平台维护" style="padding: 0px;">
                <ul class="MM">

                    <li id="menu_910" onclick="menuclick(this);
                    func_ajaxget('query/subjectlist');">课程管理</li>
                    <li id="menu_920" onclick="menuclick(this);
                    func_ajaxget('query/testlist');">题库管理</li>

                    <hr class="myhr"/>

                    <li id="menu_930" onclick="menuclick(this);
                    func_ajaxget('back_setting/holidaylist');">节日管理</li>
                    <li id="menu_940" onclick="menuclick(this);
                    func_ajaxget('back_setting/banklist');">银行管理</li>


                    <hr class="myhr"/>

                    <li id="menu_960" onclick="menuclick(this);
                    func_ajaxget('building/discountlist?refid=0&reftype=${roomsale.Enum_Reftype.RT_sys.code}');">平台优惠</li>

                    <li id="menu_970" onclick="menuclick(this);
                    func_ajaxget('building/newslist?refid=0&reftype=${roomsale.Enum_Reftype.RT_sys.code}');">平台公告</li>

                    <hr class="myhr"/>

                    <li id="menu_980" onclick="menuclick(this);
                    func_ajaxget('back_setting/smslist');">短信查询</li>

                    <li id="menu_990" onclick="menuclick(this);
                    func_ajaxget('summary/feedbacklist');">用户反馈</li>

                    <li id="menu_950" onclick="menuclick(this);
                    func_ajaxget('back_setting/bannerlist');">Banner管理</li>

                    <li id="menu_951" onclick="menuclick(this);
                    func_ajaxget('back_setting/startpic');">启动图</li>

                </ul>
            </div>
        </g:if>

        <g:if test="${'r_backop' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_1100" title="平台优惠审核" style="padding: 0px;">
                <ul class="MM">
                    <li id="menu_1110" onclick="menuclick(this);
                    func_ajaxget('building/dcchecklist?refid=0&reftype=${roomsale.Enum_Reftype.RT_sys.code}');">优惠审核</li>

                    <li id="menu_1120" onclick="menuclick(this);
                    func_ajaxget('building/newschecklist?refid=0&reftype=${roomsale.Enum_Reftype.RT_sys.code}');">公告审核</li>
                </ul>
            </div>
        </g:if>

        <g:if test="${'r_agentop' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_700" title="经纪人管理" style="padding: 0px;">
                <ul class="MM">

                    %{-- Tab切换，区分未审核，已审核 --}%
                    <li id="menu_710" onclick="menuclick(this);
                    func_ajaxget('query/agentlist');">经纪人审核</li>

                    <li id="menu_720" onclick="menuclick(this);
                    func_ajaxget('summary/agtsumlist');">经纪人查询</li>

                    <hr class="myhr"/>

                    <li id="menu_730" onclick="menuclick(this);
                    func_ajaxget('summary/teamlist');">团队查询</li>
                    <li id="menu_740" onclick="menuclick(this);
                    func_ajaxget('query/cstlist');">客户查询</li>

                    <li id="menu_750" onclick="menuclick(this);
                    func_ajaxget('summary/sharelist');">分享列表</li>

                </ul>
            </div>
        </g:if>

        <g:if test="${'r_money' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_800" title="佣金发放" style="padding: 0px;">
                <ul class="MM">
                    <li id="menu_810" onclick="menuclick(this);
                    func_ajaxget('query/agtcommlist');">佣金发放</li>

                    <li id="menu_820" onclick="menuclick(this);
                    func_ajaxget('query/agtfixedlist');">佣金解冻</li>

                    <hr class="myhr"/>

                    <li id="menu_830" onclick="menuclick(this);
                    func_ajaxget('query/issuelist');">发放记录</li>
                    <li id="menu_840" onclick="menuclick(this);
                    func_ajaxget('query/cashlist');">提现记录</li>

                    <li id="menu_850" onclick="menuclick(this);
                    func_ajaxget('query/cstpaylist');">收款查询</li>

                </ul>
            </div>
        </g:if>

        <g:if test="${'r_user' in session?.rights}">
            <div data-dojo-type="dijit/layout/ContentPane" id="menu_1000" title="平台人员管理" style="padding: 0px;">
                <ul class="MM">
                    <li id="menu_1010" onclick="menuclick(this);
                    func_ajaxget('back_user/userlist');">人员管理</li>

                    <li id="menu_1020" onclick="menuclick(this);
                    func_ajaxget('back_right/right_manage');">权限管理</li>

                    <g:if test="${session.userid == -1}">

                        <hr class="myhr"/>

                        <li id="menu_1030" onclick="menuclick(this);
                        func_ajaxget('back_user/super_admin');">超级管理员</li>

                        <li id="menu_1040" onclick="menuclick(this);
                        func_ajaxget('back_user/init_city');">初始化城市</li>

                        <li id="menu_1050" onclick="menuclick(this);
                        func_ajaxget('back_user/init_bank');">初始化银行</li>

                    </g:if>
                </ul>
            </div>
        </g:if>

    </div>


    <div id="centerbd" data-dojo-type="dojox/layout/ContentPane" region="center"
         data-dojo-props="refreshOnShow:true" style="overflow: auto">
        %{--<g:render template="/myspace/myfirst"/>--}%
    </div>

</div>

<input type="text" data-dojo-type="dijit/form/TextBox"
       data-dojo-id="menu_selected" style="display:none"/>

</body>


<script type="text/javascript">

    require(["dojo/parser",
                "dojo/ready",
                "dojox/grid/EnhancedGrid",
                "dojox/grid/enhanced/plugins/Pagination",
                "dojox/data/QueryReadStore"
            ],
            function (parser, ready) {
                ready(function () {
                    func_ajaxget("user/welcome");
                    document.getElementById("g_loading").innerHTML = "";
                    require([
                        "dojox/widget/Standby",
                        "dijit/layout/TabContainer",
                        "dijit/Dialog",

                        "dijit/form/Form",
                        "dijit/form/Select",
                        "dijit/form/FilteringSelect",
                        "dijit/form/SimpleTextarea",
                        "dijit/form/ValidationTextBox",

                        "dojo/cldr/nls/en/number",

                        "dojox/grid/enhanced/plugins/IndirectSelection",
                        "dojox/encoding/digests/MD5",
                        "dijit/form/ComboBox",
                        "dojo/data/ItemFileReadStore",
                        "dojo/data/ObjectStore",
                        "dojo/store/Memory",
                        "dojox/form/CheckedMultiSelect",
                        "dijit/form/MultiSelect"
                    ]);

                    require(["dojox/form/Uploader", "dojox/form/uploader/FileList"], function (Uploader) {
                        myUploader = new dojox.form.Uploader();
                    });

                });
            });
</script>

</html>
