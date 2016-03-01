<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="renderer" content="webkit">
    <title>多乐檬经纪人版后台系统</title>

    <asset:link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
    <asset:link rel="icon" href="favicon.ico" type="image/x-icon"/>
    <asset:link rel="Bookmark" href="favicon.ico" type="image/x-icon"/>

        <link rel="stylesheet" href="/js/dojo/${grailsApplication.config.mydojo}/dijit/themes/claro/claro.css" />
        <link rel="stylesheet" href="/js/dojo/${grailsApplication.config.mydojo}/dojo/resources/dojo.css" />
        <script data-dojo-config="async:1, parseOnLoad:1, isDebug:0, showSpinner:1, locale:'zh-cn'"
                      src="/js/dojo/${grailsApplication.config.mydojo}/dojo/dojo.js"></script>

    <style>
    html {
        height: 100%;
        width: 100%;
        padding: 0;
        border: 0;
        margin: 0;
    }

    body {
        height: 100%;
        width: 100%;
        padding: 0;
        border: 0;
        margin: 0;
        background: url('/images/bg.jpg') repeat;
        background-size: contain;
    }
    </style>

</head>

<body  class="claro">
    <div style="position: fixed;left: 50%;margin-left: -20em;top: 50%;margin-top: -11em;
        background: #DDF1FE;width: 40em;height: 22em;border: 1px solid #000;">

    <div style="background: url('/images/login_bg.jpg') repeat-x;
        width: 100%; height: 7em; line-height: 7em; text-align: center;">
        <span style="font-family: '楷体'; font-weight: bold; font-size: 36px; color: #ffffff; ">多乐檬经纪人版</span>
    </div>

    <label style="position:absolute; left: 4em;top: 10.5em;">用户名：</label>
    <input data-dojo-type="dijit/form/TextBox" trim=true
           style="position:absolute;top: 10em; left: 8em; height:2em; line-height: 2em; width: 12em;"
           maxlength="20"  data-dojo-id="login_name"  selectOnClick="true"
    />

        <label style="position:absolute; left: 5em;top: 14.5em;">密码：</label>
    <input type="password" data-dojo-type="dijit/form/TextBox"
           style="position:absolute;top: 14em; left: 8em; height:2em; line-height: 2em; width: 12em;"
           data-dojo-id="loginpass" trim=true  maxlength="20"   selectOnClick="true"
    />


    <button data-dojo-id="btn_login" data-dojo-type="dijit/form/Button"
            style="position:absolute; left: 24em;top: 11em;border:none;">
        <script type="dojo/on" data-dojo-event="click" data-dojo-args="evt">
            var md5 = require('dojox/encoding/digests/MD5');
            var  h2 = md5(loginpass.value + 'duolemeng', 1);

            dojo.xhrPost({
                url:'/user/auth',
                handleAs:'json',
                content: {
                    loginname:login_name.value,
                    loginpass:h2
                },
                load:function (data) {
                    switch(data) {
                        case 0:
                            window.location.replace('/');
                            break
                        case 1:
                            alert("用户名或密码不正确，请重试！");
                            break
                        case 2:
                            alert("用户名或密码不正确，请重试！");
                            break
                        case 3:
                            alert("管理员正在审核您的身份，请耐心等待");
                            break;
                        case 4:
                            alert("您的身份审核未通过，请联系小区管理员");
                            break;
                    }
                        login_name.focus();
                        login_name.textbox.select();
                }
            });

        </script>

        <img src="/images/login_button.gif" alt="登录"/>
    </button>
    %{--<br>--}%
        %{--<span style="position:absolute; left: 8em;top: 17em;"><a href="/user/resetpwd">忘记密码?</a></span>--}%
        %{--<span style="position:absolute; left: 17em;top: 17em;"><a href="/user/vcsignin">短信验证登陆</a></span>--}%
</div>

</body>


<script type="text/javascript">
    require([
        "dojo/parser",
        "dojo/ready",
        "dojo/on",
        "dojox/encoding/digests/MD5",
        "dojo/_base/xhr"
    ], function(parser, ready, on){
        ready(function(){
            parser.parse();
            login_name.focus();

            on(login_name, "keypress", function(evt) {
                if(evt.charOrCode==13) {
                    loginpass.focus();
                    loginpass.textbox.select();
                }
            });

            on(loginpass, "keypress", function(evt) {
                if(evt.charOrCode==13) {
                    btn_login.focus();
                }
            });

        });
    });
</script>


</html>
