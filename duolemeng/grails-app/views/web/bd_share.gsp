<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title>楼盘信息</title>

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

    img {
        max-width: 100%;
    }

    .bd-container:hover {
        cursor: pointer;
        position: relative;
        top: -10px;
        -webkit-transition: top 1s;
        transition: top 1s;
    }
    .bd-container .bd-header {
        padding: 7px 0;
        border-radius: 5px 5px 0 0;
        background-color: #54ccb5;
        /*background-color: #f4f4f4;*/
        text-align: center;
    }

    .bd-container .bd-img {
        text-align: center;
    }

    .bd-container .bd-header h2 {
        color: #000;
        font-weight: lighter;
        margin: 0;
        padding-top: 0.6em;
    }
    .bd-container .bd-header p {
        word-wrap: break-word;
        margin: 0.5em;
        color: #447F71;
    }
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

    .bd-container .cardetails ul li:last-child {
        border-bottom: 3px solid #e8edef;
    }
    .bd-container .bd-details ul li span {
        margin-left: 1em;
        color: black;
    }

    .bd-container .bd-details p {
        word-wrap: break-word;
        background-color: #f4f4f4;
        margin: 2em 0;
        padding: 1.25em;
        font-size: 0.85em;
        line-height: 2;
        color: #777777;
    }


    </style>

</head>

<body>


<div class="bd-container">
    <div class="bd-header">
        <h2>${bdins.bdinfo.bd_name}</h2>
    </div>
    <div class="bd-img">
        <img src="/file/load/${bdins.bdpic}">
    </div>
    <div class="bd-details">
        <ul>
            <li>开发商名 <span>${bdins.bdinfo.bd_developer}</span></li>
            <li>所在城市 <span>${bdins.bdinfo.bd_city}</span></li>
            <li>所在区域 <span>${bdins.bdinfo.bd_area}</span></li>
            <li>楼盘地址 <span>${bdins.bdinfo.bd_address}</span></li>
            <li>平均佣金 <span>${bdins.bdinfo.bd_avgcash}</span></li>

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

            <li>周边商业 <p>${bdins.bainfo?.bd_commercial}</p></li>
            <li>周边教育 <p>${bdins.bainfo?.bd_education}</p></li>
            <li>周边交通 <p>${bdins.bainfo?.bd_transport}</p></li>
            <li>周边娱乐 <p>${bdins.bainfo?.bd_entertain}</p></li>

        </ul>
    </div>
</div>





</body>
</html>
