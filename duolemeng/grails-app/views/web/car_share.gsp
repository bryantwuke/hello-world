<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title>车行信息</title>

    <style>
    body {
        background-color: #c9c9c9;
        font-size: 1em;
    }

    .car-container {
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

    .car-container:hover {
        cursor: pointer;
        position: relative;
        top: -10px;
        -webkit-transition: top 1s;
        transition: top 1s;
    }
    .car-container .car-header {
        padding: 7px 0;
        border-radius: 5px 5px 0 0;
        background-color: #54ccb5;
        /*background-color: #f4f4f4;*/
        text-align: center;
    }

    .car-container .car-img {
        text-align: center;
    }

    .car-container .car-header h2 {
        color: #000;
        font-weight: lighter;
        margin: 0;
        padding-top: 0.6em;
    }
    .car-container .car-header p {
        word-wrap: break-word;
        margin: 0.5em;
        color: #447F71;
    }
    .car-container .car-details {
        margin: 0;
        margin-left: 60px;
    }
    .car-container .car-details ul {
        padding-left: 0;
        list-style: none;
    }
    .car-container .car-details ul li {
        border-top: 2px solid #e8edef;
        padding: 20px 0;
        /*font-weight: bold;*/
        color: green;
    }

    .car-container .car-details ul li:first-child {
        border-top: none;
    }

    .car-container .cardetails ul li:last-child {
        border-bottom: 3px solid #e8edef;
    }
    .car-container .car-details ul li span {
        margin-left: 1em;
        color: black;
    }

    .car-container .car-details p {
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


<div class="car-container">
    <div class="car-header">
        <h2>${carins.carinfo.cb_name}</h2>
    </div>
    <div class="car-img">
        <img src="/file/load/${carins.pic}">
    </div>
    <div class="car-details">
        <ul>
            <li>所在城市 <span>${carins.carinfo.cb_city}</span></li>
            <li>所在区域 <span>${carins.carinfo.cb_area}</span></li>
            <li>车行地址 <span>${carins.carinfo.cb_address}</span></li>
            <li>平均佣金 <span>${carins.carinfo.cb_avgcash}元</span></li>


            <li>主营品牌<span>${carins.brands}</span></li>
            <li>车行简介 <p>${carins.carinfo.cb_brief}</p></li>
            <li>售后服务 <p>${carins.carinfo.cb_aftersale}</p></li>
            <li>车贷说明 <p>${carins.carinfo.cb_loan}</p></li>

        </ul>
    </div>
</div>





</body>
</html>
