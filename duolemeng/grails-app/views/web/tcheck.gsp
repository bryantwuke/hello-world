<meta name="layout" content="blank"/>

<style>
body {
    font-size: 1.2em;
}

.timu {
    font-weight: bold;
}

.tijiao {
    border: 0;
    width: 90%;
    padding: 0.5em;
    background-color: #ff9500;
    color: #ffffff;
    font-size: large;
}

.msel {
    padding-top: 0.3em;
}

</style>

<g:if test="${!session.agentid}">
    <div style="color:red;font-size:2em;word-wrap:break-word;text-align:center;font-weight:bold">你还未登录系统!</div>
</g:if>

<form action="/web/scheck" method="post">

    <input type="text" name="subid" value="${testins.subid}" style="display: none">

    <g:each var="test" status="i" in="${testins.items}">
        <div class="timu">第${i + 1}题：${test.test_title}[${test.test_type}]</div>
        <g:if test="${test.test_optiona}">
            <div class="msel"><input type="checkbox" id="${test.id}A" name="${test.id}" value="A"/><label
                    for="${test.id}A">${test.test_optiona}</label></div>
        </g:if>

        <g:if test="${test.test_optionb}">
            <div class="msel"><input type="checkbox" id="${test.id}B" name="${test.id}" value="B"/><label
                    for="${test.id}B">${test.test_optionb}</label></div>
        </g:if>

        <g:if test="${test.test_optionc}">
            <div class="msel"><input type="checkbox" id="${test.id}C" name="${test.id}" value="C"/><label
                    for="${test.id}C">${test.test_optionc}</label></div>
        </g:if>

        <g:if test="${test.test_optiond}">
            <div class="msel"><input type="checkbox" id="${test.id}D" name="${test.id}" value="D"/><label
                    for="${test.id}D">${test.test_optiond}</label></div>
        </g:if>

        <g:if test="${test.test_optione}">
            <div class="msel"><input type="checkbox" id="${test.id}E" name="${test.id}" value="E"/><label
                    for="${test.id}E">${test.test_optione}</label></div>
        </g:if>

        <g:if test="${test.test_optionf}">
            <div class="msel"><input type="checkbox" id="${test.id}F" name="${test.id}" value="F"/><label
                    for="${test.id}F">${test.test_optionf}</label></div>
        </g:if>

        <br/>
    </g:each>

    <br/><br/>

    <div style="text-align: center">
        <button type="submit" class="tijiao">提交答卷</button>
    </div>

</form>
