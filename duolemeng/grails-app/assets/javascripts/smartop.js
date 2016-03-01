function menuclick(thisobj) {
    var orgid = menu_selected.value;
    if (orgid != thisobj.id) {
        var orgtitle = null;
        if (orgid) {
            orgtitle = (parseInt(orgid.substr(5)) / 100 | 0) * 100;
            dojo.setAttr(orgid, 'style', 'background-color:none; color:none;'); // 恢复旧选择项
        }
        var newtitle = (parseInt(thisobj.id.substr(5)) / 100 | 0) * 100;
        if (orgtitle != newtitle) {
            if (orgtitle) {
                // dojo.setAttr("menu_" + orgtitle + "_button", 'style', 'color:none;'); // 恢复旧标题色
                dojo.setAttr("menu_" + orgtitle + "_button", 'style', 'background-image:none;color:none;'); // 恢复旧标题色
            }
            // dojo.setAttr("menu_" + newtitle + "_button", 'style', 'color:red;'); // 设置新标题色
            dojo.setAttr("menu_" + newtitle + "_button", 'style', 'background-color:#0033CC;color:white;'); // 设置新标题色
        }
        // dojo.setAttr(thisobj.id, 'style', 'background-image:url(images/m_selected.png); color:white;'); // 设置新选择项
        dojo.setAttr(thisobj.id, 'style', 'background-color:#0083bb; color:white;'); // 设置新选择项
        menu_selected.set('value', thisobj.id);
    }
}

function func_ajaxget(desturl) {
    dojo.xhrPut({
        url:desturl,
        load:function (data) {
            dijit.byId('centerbd').destroyDescendants();
            dijit.byId('centerbd').set('content', data);
            // dijit.byId('centerbd').innerHTML = data;
            // dojo.parser.parse('centerbd')
        }
    });
}

function func_ajaxdel_new(url, idname, value, grid) {
    if (confirm("确定要删除选中的记录吗？")) {
        dojo.xhrDelete({
            url:url + "?" + idname + "=" + value,
            load:function (data) {
                alert("删除成功");
                dijit.byId(grid)._refresh();
            }
        });
    }
}

function func_ajaxdel_set(url, idname, value, grid,msg,setvalue) {
    if (confirm("确定要"+msg+"吗？")) {
        var urlval = url + "?" + idname + "=" + value;
        if(setvalue != null){
            urlval +=("&setvalue="+setvalue);
        }
        dojo.xhrDelete({
            url:urlval,
            load:function (data) {
                alert(msg+"成功");
                dijit.byId(grid)._refresh();
            }
        });
    }
}


function fmt_onlydate(datum) {
    if (datum) {
        var d = dojo.date.stamp.fromISOString(datum);
        return dojo.date.locale.format(d, {selector:'date', datePattern:'yyyy-MM-dd'});
    }
    return '-';
}

function fmt_datetime(datum) {
    if (datum) {
        var d = dojo.date.stamp.fromISOString(datum);
        return dojo.date.locale.format(d, {datePattern:"yyyy-MM-dd", timePattern:"HH:mm:ss"});
    }
    return '-';
}

function fmt_shorttime(date) {
    if (date) {
        var d = dojo.date.stamp.fromISOString(date);
        return dojo.date.locale.format(d, {datePattern:"MM-dd", timePattern:"HH:mm"});
    }
    return '-';
}

function fmt_money(money) {
    if(money) {
        return (money/100).toFixed(2).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
    return "-";
}

function fmt_money2(money) {
    if(money) {
        return money.toFixed(2).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
    return "-";
}

function fmt_rate(rate) {
    if (rate) {
        return rate/10 + '%';
    }
    return "-";
}

function fmt_percent(rate) {
    if (rate) {
        return (rate*100).toFixed(2) + '%';
    }
    return "-";
}

function fmt_ec_view(fields) {
    return  "<a href='javascript:void(0)' onclick='func_ec_view(" + fields[1] + "); return false;'>" + fields[0] + "</a>";
}

function func_ec_view(ecid){
    func_ajax_view("oppub/regec_view?regec_id=", "dlg_regec", ecid, "btn_regec_reset");
}

function fmt_file_bdtype(fields) {
    fields[2] = 40;
    return fmt_file_opt(fields);
}

function fmt_file_bdpic(fields) {
    fields[2] = 30;
    return fmt_file_opt(fields);
}

function fmt_file_banner(fields) {
    fields[2] = 60;
    return fmt_file_opt(fields);
}

function fmt_file_cbpic(fields) {
    fields[2] = 80;
    return fmt_file_opt(fields);
}

function fmt_file_opt(fields) {
    return "<a href='javascript:void(0)' onclick='func_file_list(" + fields[1] +"," + fields[2] + "); return false;'>[&nbsp;"+fields[0]+"&nbsp;]</a>";
}

function fmt_comfile_opt(fields) {
    return "<a href='javascript:void(0)' onclick='func_comfile_list(" + fields[1] +"," + fields[2] + ", 1); return false;'>[&nbsp;"+fields[0]+"&nbsp;]</a>";
}

function fmt_comfile_view(fields) {
    return "<a href='javascript:void(0)' onclick='func_comfile_list(" + fields[1] +"," + fields[2] + ", 0); return false;'>[&nbsp;"+fields[0]+"&nbsp;]</a>";
}

function fmt_file_view(fields) {
    return "<a href='javascript:void(0)' onclick='func_file_list(" + fields[1] +", 0); return false;'>[&nbsp;"+fields[0]+"&nbsp;]</a>";
}

function file_disupload(colid) {
    dijit.byId('uploadfile').set('disabled', true);
    smt_upload.set('disabled', true);
    dijit.byId('grid_filelist').layout.setColumnVisibility(colid, false);
}

function fmt_file_down(fields){
    if(fields[0]) {
        return "<a href='/file/load?id="+ fields[1]+"' target='下载'>"+fields[0]+"</a>";
    }else {
        return '-';
    }
}

function func_file_list(refid, refclass) {
    dojo.xhrPut({
        url: "file/filelist?refid=" + refid + "&refclass=" + refclass,
        load: function(data) {
            dijit.byId("dlg_file").destroyDescendants();
            dijit.byId("dlg_file").set('content', data);
            dijit.byId("dlg_file").show();
        }
    });
}


function func_file_list_bak(bindid, canopt) {
    dojo.xhrPut({
        url: "file/loan_filelist?id=" + bindid +"&canopt=" + canopt,
        load: function(data) {
            dijit.byId("dlg_file").destroyDescendants();
            dijit.byId("dlg_file").set('content', data);
            dijit.byId("dlg_file").show();
        }
    });
}

function func_comfile_list(bindid, fileclass, canopt) {
    dojo.xhrPut({
        url: "file/com_filelist?bindid=" + bindid + "&fileclass=" + fileclass + "&canopt=" + canopt,
        load: function(data) {
            dijit.byId("dlg_file").destroyDescendants();
            dijit.byId("dlg_file").set('content', data);
            dijit.byId("dlg_file").show();
        }
    });
}

function fmt_file_del(fields) {
    if(fields[1] == g_fileclass.value) {
        return "<a href='javascript:void(0)' onclick='func_file_del(" + fields[0] +"); return false;'>删除</a>";
    }else {
        return "-";
    }
}

function fmt_comfile_del(id) {
    return "<a href='javascript:void(0)' onclick='func_file_del(" + id +"); return false;'>删除</a>";
}

function func_file_del(file_id) {
    if(confirm("确定要删除选中的文件吗？")){
        dojo.xhrDelete({
            url:"file/file_dellog?id=" +file_id,
            handleAs:'json',
            load:function(data){
                if(data.status == 0) {
                    dijit.byId('grid_filelist')._refresh();
                    dijit.byId('file_ondelete').set('value', file_id);
                }else{
                    alert(data.msg);
                }
            }
        });
    }
}

function func_file_delgrid(file_id, grid) {
    if(confirm("确定要删除选中的文件吗？")){
        dojo.xhrDelete({
            url:"file/file_dellog?file_id=" +file_id,
            handleAs:'json',
            load:function(data){
                if(data.status == 0) {
                    dijit.byId(grid)._refresh();
                }else{
                    alert(data.msg);
                }
            }
        });
    }
}

function func_delayed_load(grid,gridstore){
    if(!dijit.byId(grid).get('store')) {
        dijit.byId(grid).set("store", dojo.getObject(gridstore));
        dijit.byId(grid)._refresh();
    }
}

function disableall(dijitid, bflag) {
    dojo.forEach(dijit.byId(dijitid).getDescendants(), function (w) {
        switch (w.declaredClass) {
            case 'dijit.form.Button':
            case 'dijit.form.ComboBox':
            case 'dojox.form.CheckedMultiSelect':
            case 'dijit.form.TextBox':
            case 'dijit.form.DateTextBox':
            case 'dijit.form.TimeTextBox':
            case 'dijit.form.NumberTextBox':
            case 'dijit.form.ValidationTextBox':
            case 'dijit.form.Select':
            case 'dijit.form.FilteringSelect':
            case 'dijit.form.SimpleTextarea':
            case 'dojox.form.Uploader':
                w.set('disabled', bflag);
                break;
        }
    });
}

function func_ajaxdlgview(url, domainname, value, title, isvoid) {
    dojo.xhrPut({
        url: url + "?" + domainname + "_id=" + value,
        load: function(data) {
            dijit.byId("dlg_" + domainname).destroyDescendants();
            dijit.byId("dlg_" + domainname).set('content', data);
            if(!isvoid) {
                disableall("frm_" + domainname, true);
                dijit.byId("btn_" + domainname + "_edit").set('disabled', false);
                dijit.byId("btn_" + domainname + "_reset").set('disabled', false);
            }
            dijit.byId("dlg_" + domainname).set("title", title);
            dijit.byId("dlg_" + domainname).show();
        }
    });
}




function func_ajaxdlgview2(url, domainname, value, title, isvoid) {
    dojo.xhrPut({
        url: url + "?" + domainname + "_id=" + value,
        load: function(data) {
            dijit.byId("dlg_" + domainname).destroyDescendants();
            dijit.byId("dlg_" + domainname).set('content', data);
            if(!isvoid) {
                disableall("frm_" + domainname, true);
                //dijit.byId("btn_" + domainname + "_edit").set('disabled', false);
                dijit.byId("btn_" + domainname + "_reset").set('disabled', false);
            }
            dijit.byId("dlg_" + domainname).set("title", title);
            dijit.byId("dlg_" + domainname).show();
        }
    });
}



function func_ajaxdlgbdview(url, domainname, value, title, isvoid) {
    dojo.xhrPut({
        url: url + "?" + domainname + "_id=" + value,
        load: function(data) {
            dijit.byId("dlg_" + domainname).destroyDescendants();
            dijit.byId("dlg_" + domainname).set('content', data);
            if(!isvoid) {
                disableall("frm_bd_basic", true);
                disableall("frm_bd_other", true);
                disableall("frm_bd_pro", true);
                disableall("frm_bd_type", true);
                disableall("frm_bd_around", true);
                disableall("frm_bd_rule", true);

                dijit.byId("btn_" + domainname + "_basic_edit").set('disabled', false);
                dijit.byId("btn_" + domainname + "_other_edit").set('disabled', false);
                dijit.byId("btn_" + domainname + "_pro_edit").set('disabled', false);
                dijit.byId("btn_" + domainname + "_type_edit").set('disabled', false);
                dijit.byId("btn_" + domainname + "_around_edit").set('disabled', false);
                dijit.byId("btn_" + domainname + "_rule_edit").set('disabled', false);

                dijit.byId("btn_" + domainname + "_basic_reset").set('disabled', false);
                dijit.byId("btn_" + domainname + "_other_reset").set('disabled', false);
                dijit.byId("btn_" + domainname + "_pro_reset").set('disabled', false);
                dijit.byId("btn_" + domainname + "_type_reset").set('disabled', false);
                dijit.byId("btn_" + domainname + "_around_reset").set('disabled', false);
                dijit.byId("btn_" + domainname + "_rule_reset").set('disabled', false);
            }
            dijit.byId("dlg_" + domainname).set("title", title);
            dijit.byId("dlg_" + domainname).show();
        }
    });
}

function func_ajaxdlgcbview(url, domainname, value, title, isvoid) {
    dojo.xhrPut({
        url: url + "?" + domainname + "_id=" + value,
        load: function(data) {
            dijit.byId("dlg_" + domainname).destroyDescendants();
            dijit.byId("dlg_" + domainname).set('content', data);
            if(!isvoid) {
                disableall("frm_cb_basic", true);
                disableall("frm_cb_other", true);
                disableall("frm_cb_type", true);

                dijit.byId("btn_" + domainname + "_basic_edit").set('disabled', false);
                dijit.byId("btn_" + domainname + "_other_edit").set('disabled', false);
                dijit.byId("btn_" + domainname + "_type_edit").set('disabled', false);


                dijit.byId("btn_" + domainname + "_basic_reset").set('disabled', false);
                dijit.byId("btn_" + domainname + "_other_reset").set('disabled', false);
                dijit.byId("btn_" + domainname + "_type_reset").set('disabled', false);
            }
            dijit.byId("dlg_" + domainname).set("title", title);
            dijit.byId("dlg_" + domainname).show();
        }
    });
}


function func_dlgsave(idname, ctrlname, domainname) {
    func_dlgsavegrid(idname, ctrlname, domainname, "grid_"+domainname);
}

function func_dlgsavegrid(idname, ctrlname, domainname, gridname) {
    dijit.byId('btn_'+domainname+'_save').set('disabled', true);
    if(dijit.byId(idname).get('value')) {
        dojo.xhrPost({
            url: ctrlname + "/" + domainname +"_editlog",
            form: "frm_" + domainname,
            load: function(response, ioArgs) {
                dijit.byId(gridname)._refresh();
                dijit.byId("dlg_" + domainname).hide();
                alert("保存成功！");
            },
            error: function() {
                alert("保存失败！");
            }
        });

    }
    else {          //没有数据就增加
        dojo.xhrPost({
            url: ctrlname + "/" + domainname +"_addlog",
            form: "frm_" + domainname,
            load: function(response, ioArgs) {
                dijit.byId(gridname)._refresh();
                dijit.byId("dlg_" + domainname).hide();
                alert("保存成功！");
            },
            error: function() {
                alert("保存失败！");
            }
        });
    }
}




//楼盘与车行保存
function func_dlgsavegrid_multiform(idname, ctrlname, domainname, gridname) {
    var arr = domainname.split('_');
    if(arr[1] == 'basic' && !dijit.byId(idname).get('value')){
        dojo.xhrPost({
            url: ctrlname + "/" + domainname +"_addlog",
            form: "frm_" + domainname,
            handleAs:'json',

            load: function(response, ioArgs) {
                if(response.status == 0){
                    dijit.byId('btn_'+domainname+'_save').set('disabled', true);
                    dijit.byId(idname).set('value', response.retid);
                    dijit.byId(gridname)._refresh();
                    //dijit.byId("dlg_" + domainname).hide();
                    alert("保存成功！");
                }else{
                    alert(response.msg);
                }
            },
            error: function() {
                alert("保存失败！");
            }
        });

    }else{
        if(!dijit.byId(idname).get('value')){
            var mainTab = dijit.byId(arr[0]+"_main_panel");
            var subTab = dijit.byId(arr[0]+"_basic_panel");
            mainTab.selectChild(subTab);
            alert('请先填写基本信息并保存');
            return;
        }

        dojo.xhrPost({
            url: ctrlname + "/" + domainname +"_editlog",
            form: "frm_" + domainname,
            content: {
                bd_id: dijit.byId(idname).get('value'),
                cb_id: dijit.byId(idname).get('value')
            },
            handleAs:'json',
            load: function(response, ioArgs) {
                if(response.status == 0) {
                    dijit.byId('btn_' + domainname + '_save').set('disabled', true);
                    dijit.byId(gridname)._refresh();
                    //dijit.byId("dlg_" + domainname).hide();
                    alert("保存成功！");
                }else{
                    alert(response.msg);
                }
            },
            error: function() {
                alert("保存失败！");
            }
        });

    }

}


function func_pwd_reset(idname, domainname) {
    dijit.byId('btn_pwd_save').set('disabled', true);
    if(dijit.byId(idname).get('value')) {
        dojo.xhrPost({
            url: "appgw/modifypwd",
            form: "frm_" + domainname,
            handleAs:'json',
            load: function (data) {
                if(data.status == 0){
                    alert("保存成功！");
                }else{
                    alert(data.msg);
                }

            },
            error: function () {
                alert("保存失败！");
            }
        });
    }else{
        alert("无效的用户");
    }
}



function func_dlgsave_nogrid(idname, ctrlname, domainname) {
    dijit.byId('btn_'+domainname+'_save').set('disabled', true);
    if(dijit.byId(idname).get('value')) {
        dojo.xhrPost({
            url: ctrlname + "/" + domainname +"_editlog",
            form: "frm_" + domainname,
            load: function(response, ioArgs) {
                disableall('frm_'+domainname, true);
                dijit.byId('btn_'+domainname+'_edit').set('disabled', false);
                alert("保存成功！");
                return response;
            },
            error: function() {
                alert("保存失败！");
            }
        });
    }
    else {          //没有数据就增加
        dojo.xhrPost({
            url: ctrlname + "/" + domainname +"_addlog",
            form: "frm_" + domainname,
            load: function(response, ioArgs) {
                dijit.byId(idname).set('value', response);
                disableall('frm_'+domainname, true);
                dijit.byId('btn_'+domainname+'_edit').set('disabled', false);
                alert("保存成功！");
            },
            error: function() {
                alert("保存失败！");
            }
        });
    }
}

function func_ajax_view(url, dlgid, idvalue, closeid){
    dojo.xhrPut({
        url: url + idvalue,
        load: function(data) {
            dijit.byId(dlgid).destroyDescendants();
            dijit.byId(dlgid).set('content', data);

            if(closeid) {
                disableall(dlgid, true);
                dijit.byId(closeid).set('disabled', false);
            }

            dijit.byId(dlgid).show();
        }
    });
}

function func_grid_add2(controller, domain, args, title){
    dojo.xhrPut({
        url: controller+'/'+domain+ '_add?'+ args,
        load: function(data) {
            dijit.byId('dlg_'+domain).destroyDescendants();
            dijit.byId('dlg_'+domain).set('content', data);
            dijit.byId('dlg_'+ domain).set('title', title);
            dijit.byId('dlg_'+domain).show();
        }
    });

}

// func_grid_add('building', 'tag', '', '', '新增标签');
function func_grid_add(controller, domain, idname, idvalue, title){
    dojo.xhrPut({
        url: controller+'/'+domain+ '_add?'+idname+'='+idvalue,
        load: function(data) {
            dijit.byId('dlg_'+domain).destroyDescendants();
            dijit.byId('dlg_'+domain).set('content', data);
            dijit.byId('dlg_'+ domain).set('title', title);
            dijit.byId('dlg_'+domain).show();
        }
    });

}

function func_grid_add_noid(controller, domain, title){
    dojo.xhrPut({
        url: controller+'/'+domain+'_add',
        load: function(data) {
            dijit.byId('dlg_'+domain).destroyDescendants();
            dijit.byId('dlg_'+domain).set('content', data);
            dijit.byId('dlg_'+ domain).set('title', title);
            dijit.byId('dlg_'+domain).show();
        }
    });

}

function func_pc_updlock(pid, plock, pg) {
    var strName = '锁定';
    if (plock) {
        strName = '解锁';
    }
    if (!confirm("注意：确定要" + strName + "该用户吗？")) {
        return;
    }

    dojo.xhrPut({
        url: 'oppub/pc_updlock?id=' + pid + '&accountLocked=' + !plock,
        load: function (data) {
            dijit.byId(pg)._refresh();
            alert("已成功" + strName + "该用户");
        }
    });
}


function fmt_aprnote(note) {
    if(note) {
        return "<span title='" + note + "'>详情</span>";
    }
    return "-";
}


function fmt_paysum(fields) {
    var strsum = 0;
    for (i = 0; i < fields.length; i++) {
        strsum += fields[0];
    }
    return strsum;
}


function func_vld_common(value, winame, url){
    var vldID = dijit.byId(winame);

    var bNoNameFound =  ( "Error" == vldID.get( "state" ) ) ? false:true;

    if( "" == vldID.value ) {
        // for some required=true is not kicking in, so we are forcing it.
        bNoNameFound = false;
    }
    else {
        if( false == vldID._focused ) {
            if(vldID.myorg != value) {
                dojo.xhrPut({
                    url: url + winame + '=' + value,
                    load: function( data ) {
                        if (1==data ) {
                            // setting the state to error since the username is already taken
                            bNoNameFound = false;
                            vldID.set( "state", "Error" );
//                        vldID.set('invalidMessage', invmsg);
//                        vldID._refreshState();
                            // vldID._setStateClass();
                        }
                        else
                        {
                            bNoNameFound = true;
                            vldID.set( "state", "" );
                            // vldID.set('promptMessage', "该输入名称可用" );
                            // vldID.set('invalidMessage', "该输入名称可用" );
                            // vldID._setStateClass();
                        }
                    }
                });
            }
        }
        else { bNoNameFound = true; }
    }
    return bNoNameFound;
}

function func_export(desturl, grid_id) {
    var grid = dijit.byId(grid_id);
    var plugin = grid.plugin('pagination');
    var start = 0;
    var count = 25;

    if(plugin) {
        count= plugin._currentPageSize;
        start = (plugin._currentPage - 1) * count;
    }

    var mysort ="";
    var sort = grid.getSortProps();
    if(sort) {
        mysort = sort[0].attribute;
        if(sort[0].descending) {
            mysort = "-" + mysort;
        }
    }

    var a3 = arguments[2]?arguments[2]:"";


    window.open(desturl + "start=" + start + "&count=" + count + "&sort=" + mysort + a3);
}

function send_sms(mobile,btn_id_name,span_id_name,sms_type,cmb){
    dijit.byId(btn_id_name).set('disabled', true);
    var count=59;
    var timer;
    //60秒内不能重发
    timer = setInterval(function(){
            document.getElementById(span_id_name).innerHTML= "重发("+count+"秒)";
            if(count == 0){
                window.clearInterval(timer);
                document.getElementById(span_id_name).innerHTML= "重发验证码";
                dijit.byId(btn_id_name).set('disabled', false);
            }
            count = count-1;
        },
        1000);

    dojo.xhrPost({
        url:'/appgw/sms_send',
        handleAs:'json',
        content: {
            mobile:mobile,
            type:sms_type,
            cmb:cmb
        },
        load:function (data) {
            if(data.status != 0){
                alert(data.msg);
            }else{
                alert("短信已发出");
            }
        }
    });

    return timer;
}




function extfunc(){
    if(global_func_name && global_last_evt){
        eval(global_func_name);
    }
    global_func_name = null;
}
