<div style="height: 3em; padding: 0px; margin: 0px;">
<div style="height: 3em; float: left;">

    <img src='${resource(dir:"images",file:"toplogo.png")}' alt="公司Logo"
         style="height:2.9em; max-width: 12em; vertical-align: middle;" />

    <label style="margin-left: 1em; vertical-align: middle; color: #ffffff; font-size: 24px;
        font-family: '黑体','微软雅黑','Arial Unicode MS';font-weight: bold;">多乐檬经纪人版后台运营系统
        <span id="g_loading" style="color: #ffff00">正在努力加载页面，请稍候
            <asset:image src="loading.gif" alt="加载中"
                 style="vertical-align: middle;"  />
        </span>
    </label>

    <g:if test="${fdiskins<500}">
        <label style="vertical-align: middle; color:#ffff00;">【警告：服务器空间不足：还有${fdiskins}MB，请尽快扩容】</label>
    </g:if>

</div>

<div style="height: 100%; float: right; text-align: right;">

    <div>

    %{--<span style="height: 2em; line-height: 2em; margin-right: 2em; color:#dbdbdb;">${grailsApplication.config.org}</span>--}%

    <span style="height: 2em; line-height: 2em; color:#dbdbdb;" id="header_user_mobile">
        用户：${session.username}
    </span>

    </div>

    <span id="thetime" style="height: 2em; margin-right: 2em; color:#dbdbdb;"></span>

%{--
    <a href="javascript:void(0)" onclick="func_ajaxget('myspace/first');" style="text-decoration:none">
        <asset:image src="home2.png" style="height: 2em; vertical-align: bottom; color:#dbdbdb; display: inline" />
        <span style="height: 2em; line-height: 2em; margin-right: 2em; color:#dbdbdb;">首页</span>
    </a>
--}%

        <a href="javascript:void(0)" onclick="glogout(<g:if test="${!session.userid}">'/user/signin'</g:if>);" style="text-decoration:none">
            <asset:image src="Exit32.png" style="height: 2em; vertical-align: bottom; color:#dbdbdb;"/>
            <span style="height: 2em; line-height: 2em; margin-right: 2em; color:#dbdbdb;">退出</span>
        </a>

</div>

<input type="text" data-dojo-type="dijit/form/DateTextBox" constraints="{datePattern:'yyyy-MM-dd'}" lang="zh-cn"
       data-dojo-id="date_now" style="display: none;" value="now" />

%{--
<span data-dojo-type="dojo/data/ItemFileReadStore" data-dojo-id="ds_selectbank"
      data-dojo-props="url:'back_bank/ds_selectbank'"></span>

<span data-dojo-type="dojo/data/ItemFileReadStore" data-dojo-id="ds_selectbranch"
      data-dojo-props="url:'back_bank/ds_selectbranch', clearOnClose:true"></span>

<span data-dojo-type="dojo/data/ItemFileReadStore" data-dojo-id="gds_assettype"
      data-dojo-props="url:'back_dataconf/ds_asttype_list'"></span>
--}%

</div>

<div data-dojo-type="dijit/Dialog" id="dlg_timeout" title="自动退出提醒" style="display: none; height: 28em; width: 28em;">
        <asset:image src="warning.png"
             style="height:5em; width:5em; margin-top: 1em; margin-left: 1em;" alt="warning"/>
        <label style="position:absolute; left: 5em; top: 2em; font-size: 20px; color: #f17f23">系统即将在"${rmdtoutins}"秒内退出</label>

        <br /><br />

        <span style="margin-left: 1em;">
            因您在"${session.maxInactiveInterval/60}"分钟内未做任何操作，系统将超时退出。
        </span>

        <br />

        <ul>
            <li>
                点<span style="color: green">继续会话</span>按钮，将继续当前的会话；
            </li>

            <br />

            <li>
                点<span style="color: red">退出系统</span>按钮，将退出系统。
            </li>
        </ul>

        <br />

        <span style="margin-left: 1em;">
            不做任何操作，系统将在"${rmdtoutins}"秒内自动退出。
        </span>
        <br /><br /><br />

    <button dojoType="dijit/form/Button"   style="margin-left: 4em;"
            data-dojo-props="iconClass:'dijitIconUsers'"
            type="button" onclick="clearTimeout(window.gtimeout); dijit.byId('dlg_timeout').hide();" >
        <span style="color: green">继续会话</span>
            </button>

    <button dojoType="dijit/form/Button"   style="margin-left: 4em;"
            data-dojo-props="iconClass:'dijitIconDelete'"
            type="button" onclick="glogout(<g:if test="${!session.userid}">'/user/signin'</g:if>);" >
        <span style="color: red">退出系统</span>
        </button>
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_loan" title="查看借款信息" style="display: none; height: 55em; width: 88em;">
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_oplog" title="查看流程信息" style="display: none; height: 41em; width: 60em;">
</div>

<div data-dojo-type="dijit/Dialog" id="dlg_file" title="" style="display: none; height: 40em; width: 50em">

    <script type="dojo/on" data-dojo-event="hide" data-dojo-args="evt">
        %{--extfunc();

            dijit.byId('dlg_file').destroyDescendants();
        --}%
    </script>

</div>

<div data-dojo-type="dijit/Dialog" id="dlg_regec" title="查看企业信息" style="display: none; height: 45em; width: 65em;">
</div>

<script type="text/javascript">
    function showtime() {
        var now = new Date();
        var minutes = now.getMinutes();
        var seconds = now.getSeconds();
        var timeValue = now.getFullYear() + "-";
        timeValue += now.getMonth()+1 + "-";
        timeValue += now.getDate() + " ";
        timeValue += '  星期' + '日一二三四五六'.charAt(now.getDay());
        timeValue += "   " + now.getHours();
        timeValue += ((minutes < 10) ? ":0" : ":") + minutes;
        timeValue += ((seconds < 10) ? ":0" : ":") + seconds;
        document.getElementById("thetime").innerHTML = timeValue;
    }

    function gcheck() {
        dijit.byId('dlg_timeout').show();
        window.gtimeout=setTimeout(glogout, ${rmdtoutins*1000});
    }

    function glogout(url) {
        dojo.xhrPut({
            url:"user/signout",
            load:function (data) {
                if(url){
                    window.location.replace(url);
                }else{
                    window.location.replace("/user/login");
                }

            }
        });
    }

    window.onload = function() {
        setInterval(showtime, 1000);
        window.gvar_check = setTimeout(gcheck, ${(session.maxInactiveInterval-rmdtoutins)*1000});
    }

    require(["dojo/on"], function(on){
        on(document, "click", function(){
            clearTimeout(window.gvar_check);
            window.gvar_check = setTimeout(gcheck, ${(session.maxInactiveInterval-rmdtoutins)*1000});
        });
    });

/*
     require(["dojo", "dojo/aspect"], function(dojo, aspect){
     aspect.after(dojo, "xhr", function(){
     console.debug("xhr called. will clear timeout:"+window.gtimeout);
     clearTimeout(window.gtimeout);
     //  return deferred;
     });

     aspect.before(dojo, "xhr", function(method, args){
     // this is called before any dojo.xhr call
     console.debug("xhr before.");
     });

     aspect.around(dojo, "xhr", function(originalXhr){
     console.debug("xhr around.");
     });
     });
*/

</script>




