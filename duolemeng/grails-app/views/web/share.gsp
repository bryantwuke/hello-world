<%@ page import="roomsale.Enum_Discounttype" %>
<meta name="layout" content="mobile" />

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

/* 表单 */
ul {
    list-style: none;
}

body {
    font-size:87.5%;
    font-weight:300;
    font-family:"HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, sans-serif;
    line-height:1.7em;
}


/*form {*/
    /*width:560px;*/
    /*margin:0px auto;*/
    /*text-align:left;*/

/*}*/


li {
    padding:12px 0px;
    border-top:1px dotted #c7c7c7;
    min-height:17px;
}

/*****************/
/* 表单 */
/*****************/

/*label {*/
    /*float:left;*/
    /*width:180px;*/
/*}*/

input{
    background:#fff;
    font-size:1.2em;
    font-weight:300;
    color:#8c8a8b;
}

button{
    background:#fa765f;
    font-size:1.2em;
    font-weight:300;
    color:#fff;
    padding: 0.5em;

}

textarea,
input[type=text]{
    border:1px solid #c7c7c7;
    outline:1px solid rgba(0, 0, 0, 0.1);
    padding:3px 5px;
}

/* 获得焦点 */
textarea:active,
textarea:focus,
input[type=text]:focus,
input[type=text]:active{
    outline:1px solid rgba(0, 0, 0, 0.2);
}


@media screen and (min-width: 960px) {
    li input[type=text]{
        width: 20em;
    }


    form {
        width:600px;
        margin:0px auto;
        text-align:left;

    }

    label {
        float:left;
        width:180px;
    }

    button{
        min-width: 60%;
    }
}


@media screen and (min-width: 480px) and (max-width: 960px){
    li input[type=text]{
        width: 14em;
    }


    form {
        width:480px;
        margin:0px auto;
        text-align:left;

    }

    label {
        float:left;
        width:140px;
    }

    button{
        min-width:240px;
    }
}

@media screen and (min-width: 380px) and (max-width: 480px){
    li input[type=text]{
        width: 12em;
    }


    form {
        width:380px;
        margin:0px 10px;
        text-align:left;

    }

    label {
        float:left;
        width:80px;
    }

    button{
        min-width: 50%;
    }
}

@media screen and (min-width: 280px) and (max-width: 380px){
    li input[type=text]{
        width: 9.5em;
    }


    form {
        width:320px;
        margin:0px 6px;
        text-align:left;

    }

    label {
        float:left;
        width:65px;
    }

    button{
        min-width: 70%;
    }
}

@media screen and (max-width: 280px) {
    li input[type=text]{
        width: 6em;
    }


    form {
        width:240px;
        margin:0px 2px;
        text-align:left;

    }

    label {
        float:left;
        width:20px;
    }

    button{
        min-width: 70%;
    }
}


button {
    box-shadow: inset 0px 1px 0px 0px rgba(255, 255, 255, 1);
    border-style: none;
    /*-webkit-border-radius: 20px;*/
    /*-moz-border-radius: 20px;*/
    /*border-radius: 20px;*/
    padding:5px 30px;
    font-weight:bold;
    cursor:pointer;
}

button:hover {
    background:#54ccb5;
}

button.disabled{
    background:grey;
}

</style>

<script>


    function validate_required(field,alerttxt)
    {
        with (field)
        {
            if (value == null|| value == "") {
                alert(alerttxt);
                return false;
            } else {
                return true;
            }
        }
    }

    function validate_mobile(field,alerttxt)
    {
        with (field)
        {
            if(/^1[1-9]\d{9}$/.test(value)){
                return true;
            }else{
                alert(alerttxt);
                return false;
            }

        }
    }

    function validate_form(thisform)
    {
        with (thisform)
        {
            if (validate_required(reg_name,"名字不能为空!")==false) {
                cst_name.focus();
                return false
            }

            if (validate_mobile(reg_mobile,"无效的手机号!")==false) {
                cst_mobile.focus();
                return false
            }

            //reg_discount();
            document.getElementById("dcform").submit();
        }

        return false;
    }
</script>


<div>
    <br/>

    <div style="text-align: center;font-size: 2.5em; font-weight: bold; color: red">
        ${disins.dcinfo.dc_title}
    </div><br/>

    <div style="text-align: center;">
        <g:if test="${disins.dcinfo.dc_type == roomsale.Enum_Discounttype.DT_discount_ok.code}">
            活动期间：${disins.dcinfo.dc_fromtime.format('yyyy-MM-dd')} ——　<span style="color: red">${disins.dcinfo.dc_endtime.format('yyyy-MM-dd')}</span>
        </g:if>
        <g:else>
            发布时间：${disins.dcinfo.dc_fromtime.format('yyyy-MM-dd')}
        </g:else>
    </div>

    <div style="margin:1em 0;">
        <section>${raw(disins.dcinfo.dc_content)}</section>
    </div>

</div>
<g:if test="${disins.dcinfo.dc_type == Enum_Discounttype.DT_discount_ok.code && disins.dcinfo.dc_endtime && disins.dcinfo.dc_endtime.after(new Date())}">
    <br/><br/>
    <hr/>
    <div style="word-wrap: break-word; width: 100%; text-align: center">
        <h2>优惠登记表</h2>
        <form method="post"  id="dcform" action="/web/regdiscount">
            <input type="hidden" value="${disins.dcinfo.id}" name="reg_discountid"/>
            <input type="hidden" value="${disins.agentid}" name="reg_agentid"/>
            <ul>
                <li>
                    <label for="name">姓名(<span style="color: red">*</span>)</label>
                    <input type="text"  id="name" name="reg_name" maxlength="15"/>
                </li>
                <li>
                    <label for="phone">电话(<span style="color: red">*</span>)</label>
                    <input type="text"  id="phone" name="reg_mobile" maxlength="20"/>
                </li>

                <li>
                    <label for="note">备注</label>
                    <input type="text" id="note" name="reg_note" maxlength="64"/>
                </li>


            </ul>
            <div style="text-align: center">
                <button type="button" id="btn_sub" onclick="validate_form(document.getElementById('dcform'));">提交</button>
            </div>

        </form>
    </div>
</g:if>



