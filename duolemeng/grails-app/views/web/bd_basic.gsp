<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title>楼盘参数</title>

    <style>
    body {
        background-color: #c9c9c9;
        font-size: 1em;
    }

    .bd-container {
        min-width: 30em;
        max-width: 60em;
        width: auto;
        margin: 0 auto;
        border-radius: 5px;
        background-color: #fff;
        position: relative;
        top: 0;
    }
    .bd-container:hover {
        cursor: pointer;
        position: relative;
        top: -10px;
        -webkit-transition: top 1s;
        transition: top 1s;
    }
    .bd-container .bd-header {
        padding: 20px 0;
        border-radius: 5px 5px 0 0;
        background-color: #54ccb5;
        text-align: center;
    }

    .bd-container .bd-header h2 {
        color: #000;
        font-weight: lighter;
        margin: 0;
        padding-top: 0.6em;
    }
    /*.bd-container .bd-header p {*/
        /*margin: 0.5em;*/
        /*color: #447F71;*/
    /*}*/
    .bd-container .bd-details {
        margin: 0;
        margin-left: 60px;
    }
    .bd-container .bd-details ul {
        padding-left: 0;
        list-style: none;
    }
    .bd-container .bd-details ul li {
        border-top: 2px solid #e8edef;
        padding: 20px 0;
        /*font-weight: bold;*/
        color: green;
    }

    .bd-container .bd-details ul li:first-child {
        border-top: none;
    }

    .bd-container .bd-details ul li:last-child {
        border-bottom: 3px solid #e8edef;
    }
    .bd-container .bd-details ul li span {
        margin-left: 4em;
        /*font-weight: bold;*/
        color: black;
    }

    .bd-container .bd-details p {
        background-color: #f4f4f4;
        margin: 2em 0;
        padding: 1.25em;
        font-size: 0.75em;
        line-height: 1.8;
        color: #777777;
    }


    </style>

</head>

<body>


<div class="bd-container">
    <div class="bd-header">
        <h2>${bdins.bdinfo.bd_name}</h2>
        %{--<p>基本参数</p>--}%
    </div>
    <div class="bd-details">
        <ul>
            <li>开发商名 <span>${bdins.bdinfo.bd_developer}</span></li>
            <li>所在城市 <span>${bdins.bdinfo.bd_city}</span></li>
            <li>所在区域 <span>${bdins.bdinfo.bd_area}</span></li>
            <li>楼盘地址 <span>${bdins.bdinfo.bd_address}</span></li>

            <li>开盘时间 <span>${bdins.bdinfo.bd_opentime?.format('yyyy年MM月dd日')}</span></li>
            <li>产权年限 <span>${bdins.bdinfo.bd_year}年</span></li>
            <li>项目特色 <span>${bdins.bdinfo.bd_feature}</span></li>
            <li>建筑类别 <span>${bdins.bdinfo.bd_structure}</span></li>

            <li>建材家装 <span>${bdins.bdinfo.bd_decoration}</span></li>
            <li>物业费用 <span>${bdins.bdinfo.bd_propcost}元</span></li>
            <li>绿化比率 <span>${bdins.bdinfo.bd_greenrate}%</span></li>
            <li>容积比率 <span>${bdins.bdinfo.bd_volumnrate}%</span></li>

            <li>楼盘业态 <span>${bdins.type}</span></li>
            <li>楼盘卖点 <span>${bdins.tag}</span></li>

        </ul>
    </div>
</div>





</body>
</html>
