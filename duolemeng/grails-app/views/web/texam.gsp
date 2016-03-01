<meta name="layout" content="blank" />

<style>
img {
    max-width: 100%;
}

h1, h2, h3, h4, h5, h6 {
    line-height: normal;
}

section {
    font-size:2em;
    word-wrap:break-word;
}
    .timu {
        font-weight: bold;
        margin-bottom: 0.5em;
    }

    .datibox {
        margin: 0.5em 0 1em 0;
    }

    .dati {
        border-left: 0;
        border-top: 0;
        border-right: 0;
        padding-left: 0.2em;
    }

    .tijiao {
        border: 0;
        width: 90%;
        padding: 0.5em;
        background-color: #ff9500;
        color: #ffffff;
        font-size: large;
        /*font-weight: bold;*/
    }

</style>

<form action="/web/sexam" method="post">

    <g:each var="test" status="i" in="${testins}">
        <div class="timu">第${i+1}题：${test.test_title}${test.test_answer}</div>
        <g:if test="${test.test_optiona}">
            <div>A：${test.test_optiona}</div>
        </g:if>

        <g:if test="${test.test_optionb}">
            <div>B：${test.test_optionb}</div>
        </g:if>

        <g:if test="${test.test_optionc}">
            <div>C：${test.test_optionc}</div>
        </g:if>

        <g:if test="${test.test_optiond}">
            <div>D：${test.test_optiond}</div>
        </g:if>

        <g:if test="${test.test_optione}">
            <div>E：${test.test_optione}</div>
        </g:if>

        <g:if test="${test.test_optionf}">
            <div>F：${test.test_optionf}</div>
        </g:if>

        <div class="datibox">
            <span>答案：</span><input class="dati" name="${test.id}" />
        </div>

    </g:each>

    <div style="text-align: center">
        <button type="submit" class="tijiao">提交答卷</button>
    </div>

</form>