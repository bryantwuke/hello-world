<meta name="layout" content="blank"/>

<style>
body {
    font-size: 1.2em;
}

.timu {
    font-weight: bold;
}

.pagenation {
    border: 0;
    width: 30%;
    padding: 0.5em;
    background-color: #ff9500;
    color: #ffffff;
    font-size: large;
}

.disabled {
    border: 0;
    width: 30%;
    padding: 0.5em;
    background-color: grey;
    color: #ffffff;
    font-size: large;
}

.msel {
    padding-top: 0.3em;
}

</style>

<script>
    function func_page(op){
        var page = ${learnins.page};
        var pagenum = ${learnins.pagenum};

        if(op == 1 && page > 1){  //上一页面
            page = page - 1;
        }else if(op == 2 && page < pagenum){    //下一页面
            page = page + 1;
        }else{
            return;
        }

        window.location.href = "/web/tlearn?id=${learnins.subid}&page="+page;
    }
</script>

%{--如果没有题目--}%
<g:if test="${learnins.total == 0}">
    <div style="text-align: center; color: #ffffff; font-size: large; color: red">此科目暂时没有题目</div>
</g:if>
<g:else>
    <g:each var="test" status="i" in="${learnins.items}">
        <div class="timu">第${learnins.start + i + 1}题：${test.test_title}</div>
        <g:if test="${test.test_optiona}">
            <div class="msel"><input type="checkbox" <g:if test="${test.test_answer.contains('a')}"> checked="true"</g:if>  disabled="true"/><label>A ${test.test_optiona}</label></div>
        </g:if>

        <g:if test="${test.test_optionb}">
            <div class="msel"><input type="checkbox" <g:if test="${test.test_answer.contains('b')}"> checked="checked"</g:if>  disabled="true"/><label>B ${test.test_optionb}</label></div>
        </g:if>

        <g:if test="${test.test_optionc}">
            <div class="msel"><input type="checkbox" <g:if test="${test.test_answer.contains('c')}"> checked="checked"</g:if>  disabled="true"/><label>C ${test.test_optionc}</label></div>
        </g:if>

        <g:if test="${test.test_optiond}">
            <div class="msel"><input type="checkbox" <g:if test="${test.test_answer.contains('d')}"> checked="checked"</g:if>  disabled="true"/><label>D ${test.test_optiond}</label></div>
        </g:if>

        <g:if test="${test.test_optione}">
            <div class="msel"><input type="checkbox" <g:if test="${test.test_answer.contains('e')}"> checked="checked"</g:if>  disabled="true"/><label>E ${test.test_optione}</label></div>
        </g:if>

        <g:if test="${test.test_optionf}">
            <div class="msel"><input type="checkbox" <g:if test="${test.test_answer.contains('f')}"> checked="checked"</g:if>  disabled="true"/><label>F ${test.test_optionf}</label></div>
        </g:if>

        <br/>
    </g:each>

    <br/><br/>

    <div style="text-align: center">
        <button type="button" style="text-align: center; margin-left: 0px" onclick="func_page(1); return;"  <g:if test="${learnins.page <= 1}">disabled="disabled" class="disabled"</g:if><g:else>class="pagenation"</g:else>>上一页</button>
        <span style="margin-left: 10%; margin-right: 10%">${learnins.page}/${learnins.pagenum}</span>
        <button type="button" style="text-align: center; margin-right: 0px" onclick="func_page(2); return;" <g:if test="${learnins.page >= learnins.pagenum}">disabled="disabled" class="disabled"</g:if><g:else>class="pagenation"</g:else>  >下一页</button>
    </div>

</g:else>
