
<fieldset style="padding:1em; margin:1em 2em; border:#b5c0ca solid; ">
    <legend style="margin-left:3em; color:#06c;">项目权限设置</legend>

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox" class="mleft" <g:if test="${'r_bdcorp' in brightins}">checked</g:if>
           id="r_bdcorp" name="r_bdcorp"/>
    <label for="r_bdcorp">楼盘合作管理</label>

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox" class="mleft" <g:if test="${'r_bdop' in brightins}">checked</g:if>
           id="r_bdop" name="r_bdop" />
    <label for="r_bdop">楼盘运营审核</label>

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox" class="mleft" <g:if test="${'r_carcorp' in brightins}">checked</g:if>
           id="r_carcorp" name="r_carcorp"/>
    <label for="r_carcorp">车行合作管理</label>

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox" class="mleft" <g:if test="${'r_carop' in brightins}">checked</g:if>
           id="r_carop" name="r_carop" />
    <label for="r_carop">车行运营审核</label>

    <br /><br />

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox" class="mleft" <g:if test="${'r_agentop' in brightins}">checked</g:if>
           id="r_agentop" name="r_agentop"/>
    <label for="r_agentop">经纪人审核&emsp;</label>

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox" class="mleft" <g:if test="${'r_back' in brightins}">checked</g:if>
           id="r_back" name="r_back"/>
    <label for="r_back">平台自身运营</label>

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox" class="mleft" <g:if test="${'r_backop' in brightins}">checked</g:if>
           id="r_backop" name="r_backop" />
    <label for="r_backop">平台运营审核</label>

</fieldset>

<fieldset style="padding:1em; margin:1em 2em; border:#b5c0ca solid; " >
    <legend style="margin-left:3em; color:#06c;">其它权限设置</legend>

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox"   <g:if test="${'r_query' in brightins}">checked</g:if>
           id="r_query" name="r_query"/>
    <label for="r_query">查询权限&emsp;&emsp;</label>

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox" class="mleft" <g:if test="${'r_stat' in brightins}">checked</g:if>
           id="r_stat" name="r_stat" />
    <label for="r_stat">统计权限&emsp;&emsp;</label>

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox"   <g:if test="${'r_user' in brightins}">checked</g:if>
           id="r_user" name="r_user"/>
    <label for="r_user">后台人员管理</label>

    <label class="mleft"/>
    <input data-dojo-type="dijit/form/CheckBox" class="mleft" <g:if test="${'r_money' in brightins}">checked</g:if>
           id="r_money" name="r_money" />
    <label for="r_money">财务管理</label>

</fieldset>

<br />