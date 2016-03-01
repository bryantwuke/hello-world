<meta name="layout" content="blank"/>

<style>
body {
    background:#ECEDF1;
    color: black;
    /*font-weight: bold;*/
}

.msize1 {
    /*margin-left:16px;*/
    width: 210px;
    height: 30px;
    font-size: 15px;
    font-weight:300;
}

.fcolor{
    color:#414141;
}

.input{
    /*border: #CCC solid 1px;*/
    border-radius: 3px;
    padding: 3px;
    font-weight:300;
    /*font-size:0.74em;*/
}



.tdcss{
    border-left:none;
    border-bottom:none;
}

.mp{
    width:11em;
}

/*   my style */
select,
input[type=text]{
    border:1px solid #c7c7c7;
    outline:1px solid rgba(0, 0, 0, 0.1);
    padding:3px 5px;
}

/* 获得焦点 */
select:focus,
select:active,
input[type=text]:focus,
input[type=text]:active{
    outline:1px solid rgba(0, 0, 0, 0.2);
    padding:3px 5px;
}



/*START:remove_default_styling*/
table{
    border-collapse: collapse;
}

th, td{
    border: none;
    height: 2em;
}
/*END:remove_default_styling*/

/*START:color_header */
th{
    background-color: #0BB8F4;
    color: #fff;
}
/*END:color_header */

/*START:table_stripe*/
tr:nth-of-type(even){
    background-color: #F3F3F3;
}
tr:nth-of-type(odd) {
    background-color:#ddd;
}
/*END:table_stripe*/

/* button */

.mbutton {
    border: 0;
    width: 16em;
    padding: 0.5em;
    background-color: #0BB8F4;
    color: #fff;
}

/*  end button */

/****** end my style *****/
</style>

<script src="/js/jquery-2.2.0.min.js"></script>

<div id="wrapper" class="wrapper bgc_white mt05" style="min-height: 500px;">
    <div class="right">
        <br/>
        <form id="frm_calculator">
            <span class="sp">理财金额：</span>
            <input type="text" class="input" id="cap" name="loan_cap" maxlength="8" style="width: 14em"
                   />&nbsp;元
            <span id="capMsg" style="color: red"></span>
            <br/><br/>

            <span class="sp">理财期限：</span>
            <input type="text" class="input" id="period" name="loan_days" maxlength="3" style="width: 14em"
                   />&nbsp;月
            <span id="daysMsg" style="color: red"></span>
            <br/><br/>

            <span class="sp">年化利率：</span>
            <input type="text" class="input" id="rate" name="loan_rate" maxlength="4" style="width: 14em" />&nbsp;%
            <span id="rateMsg" style="color: red"></span>
            <br/><br/>

            <span class="sp">还款方式：</span>
            <select id="w_class" class="msize1 input" style="padding-top: 5px;width: 13em">
                    <option value="1">等额本息</option>
                    <option value="2">等额本金</option>
                    <option value="3">按月结息,到期还本</option>
                    <option value="4">到期一次还本付息</option>
            </select>
            <br/><br/>
            <input type="button" onclick="calculate()" class="mbutton" style="font-size: 16px;margin-left: 50px;" id="cash_btn" value="开始计算" />
        </form>

        <br/><hr><br/>

        <span>本息分期表</span>
        <span style="margin-left: 6em;">总利息：<span id="tot_interest"></span>元</span>

        <table id="detail" class="tablediv" style="width:100%;text-align: center;display:none"></table>
    </div><br/>
</div>

<script>

    function calculate()
    {
        var param = new Array()  //参数数组
        var cap = $('#cap');
        var rate = $('#rate');
        var period = $('#period');

        param[0] = parseInt(cap.val() * 1200);
        param[1] = parseInt(rate.val()*10);
        param[2] = parseInt(period.val() );

        if(isNaN(param[0]) || param[0] <= 0){
            $("#capMsg").html("<br/>金额不正确");
            cap.val("");
            cap.focus();
            return;
        }else{
            $("#capMsg").html("");
        }

        if(isNaN(param[2]) || param[2] <= 0){
            $("#daysMsg").html("<br/>期限不正确");
            period.val("");
            period.focus();
            return;
        }else{
            $("#daysMsg").html("");
        }

        if(isNaN(param[1]) || param[1] <= 0){
            $("#rateMsg").html("<br/>利率不正确");
            rate.val("");
            rate.focus();
            return;
        }else{
            $("#rateMsg").html("");
        }

        var type = parseInt($('#w_class').val());

        //月收本息--月收利息--月收本金--剩余本息--总利息
        var data = new Array([],[],[],[],[])

        param[1] = param[1] * 0.01 / 120
        var tot_interest = 0;

        switch (type){
            case 1:
                tot_interest = aver_cap_interest(param,data)
                break
            case 2:
                tot_interest = aver_capital(param,data)
                break
            case 3:
                tot_interest = oncecap_averint(param,data)
                break
            case 4:
                tot_interest = once_cap_interest(param,data)
                break
            default:
                break
        }
        createTR(data)
        document.getElementById('tot_interest').innerHTML = (tot_interest / 1200).toFixed(2)
    }

    //等额本息
    function aver_cap_interest(param,data)
    {
        var cap = param[0]
        var rate = param[1]
        var period = param[2]

        var month_total = data[0]      //月收本息
        var month_cap = data[1]        //月收本金
        var month_int = data[2]        //月收利息
        var left_total = data[3]       //剩余本息
        var left_cap = data[4]    //剩余本金

        var mt, i, tot_interest = 0;
        // 等额本息月还本息 = [ 本金 x 月利率 x(1+月利率)贷款月数 ] / [(1+月利率)还款月数 - 1]
        mt = cap * rate * Math.pow((1 + rate),period) / (Math.pow((1 + rate),period)-1);
        tot_interest =  mt * period - cap;

        for(i = 0 ; i < period ; i++)
        {
            month_total[i] = mt
            left_cap[i] = cap * Math.pow((1+rate),i)-mt * (Math.pow((1+rate),i)-1)/rate
            left_total[i] = cap + tot_interest - mt * (i+1)
            month_int[i] = left_cap[i] * rate
            month_cap[i] = mt - month_int[i]
        }
        return tot_interest;
    }

    //等额本金
    function aver_capital(param,data)
    {
        var cap = param[0]
        var rate = param[1]
        var period = param[2]

        var month_total = data[0]
        var month_cap = data[1]
        var month_int = data[2]
        var left_total = data[3]

        var mp, i, tot_interest = 0;

        // 总利息 = 总贷款数×月利率×（还款次数＋1）÷2
        tot_interest = cap * rate * (period + 1) / 2

        // 月还本金 = 总贷款数÷还款次数
        mp = cap / period

        for(i = 0 ; i < period ; i++)
        {
            month_cap[i] = mp
            // 月还利息 = 上月剩余本金×月利率
            // 当月利息＝总贷款致×(1÷还款次数＋(1－（还款月数－1）÷还款次数）×月利率)
            month_total[i] = cap * (1 / period + (1-i/period) * rate)
            month_int[i] = month_total[i] - mp
//            left_total[i] = (cap - month_cap[i] * (i+1)) * (1 + rate)
            if(i == 0)
            {
                left_total[i] = cap + tot_interest - month_total[i]
            }
            else
            {
                left_total[i] = (cap - mp * (i+1)) * (1 + rate)
            }
        }
        return tot_interest;
    }

    //到期还本，按月付息
    function oncecap_averint(param,data)
    {
        var cap = param[0]
        var rate = param[1]
        var period = param[2]
        var month_total = data[0]
        var month_cap = data[1]
        var month_int = data[2]
        var left_total = data[3]

        var i, tot_interest = 0;

        // 每次（不包含最后一次）还款金额为：出借总额×月利率 ； 最后一次还款金额为：出借总额×月利率 + 借款总额
        tot_interest = cap * rate * period

        for(i = 0 ; i < period ; i++)
        {
            month_int[i] = cap * rate
            month_cap[i] = 0
            month_total[i] = month_int[i]
            left_total[i] = cap + tot_interest - month_total[i] * (i+1)
            if(i == (period-1))
            {
                month_total[i] = cap + month_int[i]
                month_cap[i] = cap
                left_total[i] = 0
            }
        }
        return tot_interest;
    }

    //到期还本付息
    function once_cap_interest(param,data)
    {
        var cap = param[0]
        var rate = param[1]
        var period = param[2]
        var month_total = data[0]
        var month_cap = data[1]
        var month_int = data[2]
        var left_total = data[3]

        var tot_interest = cap * rate * period
        var zero = 0

        month_total[0] = tot_interest + cap
        month_cap[0] = zero + cap
        month_int[0] = tot_interest
        left_total[0] = zero

        return tot_interest;
    }

    function createTR(data)
    {
        var tableStr = "<tr><th class=\"sp\">期数</th><th class=\"mp\">本金</th><th class=\"mp\">利息</th>" +
                "<th class=\"mp\">本息合计</th><th class=\"mp\" style=\"border-right:none;\">剩余本息</th></tr>"

        var j = 0;
        for(j=0 ; j < data[0].length ; j++)
        {
            tableStr = tableStr + "<tr><td class=\"tdcss\">" + (j+1) +"</td>"
                    +"<td class=\"tdcss\">" + (data[1][j]/1200).toFixed(2) + "</td>"
                    +"<td class=\"tdcss\">" + (data[2][j]/1200).toFixed(2) + "</td>"
                    +"<td class=\"tdcss\">" + (data[0][j]/1200).toFixed(2) + "</td>"
                    +"<td class=\"tdcss\" style=\"border-right:none;\">"
                    + (data[3][j]/1200).toFixed(2) + "</td></tr>";
        }

        $("#detail").html(tableStr);
        document.getElementById("detail").style.display = "block"
    }
</script>
