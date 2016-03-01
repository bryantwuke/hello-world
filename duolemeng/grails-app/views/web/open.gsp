<%@ page import="roomsale.Enum_Discounttype" %>
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

</style>

<div>
    <br/>

    <div style="text-align: center;font-size: 2.5em; font-weight: bold; color: red">
        ${disins.dc_title}
    </div><br/>

    <div style="text-align: center;">
        <g:if test="${disins.dc_type == roomsale.Enum_Discounttype.DT_discount_ok.code}">
            活动期间：${disins.dc_fromtime.format('yyyy-MM-dd')} ——　<span style="color: red">${disins.dc_endtime.format('yyyy-MM-dd')}</span>
        </g:if>
        <g:else>
            发布时间：${disins.dc_fromtime.format('yyyy-MM-dd')}
        </g:else>
    </div>

    <div style="margin:1em 0;">
        <section>${raw(disins.dc_content)}</section>
    </div>

</div>