<meta name="layout" content="blank"/>

<style>

.mc {
    text-align: center;
    font-size: 1.5em;
}

</style>

<div style="color:red;font-size:2.5em;text-align:center;font-weight:bold">${scoreins}</div>

<g:if test="${scoreins < 60}">
    <div class="mc">很遗憾，本次考试未通过。<br />请再接再厉！</div>
</g:if>
<g:elseif test="${scoreins < 80}">
    <div class="mc">恭喜您，成功通过考试！</div>
</g:elseif>
<g:else>
    <div class="mc">恭喜您，本次考试高分通过！</div>
</g:else>