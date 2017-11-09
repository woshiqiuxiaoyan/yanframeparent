/**
 *  渲染查询按钮
 */
function initSearch_btn(table) {
    $(".search_btn").on('click', function () {
        table.reload('IdSysUserInfo', {
            url: '/SysUserInfoManagerController/getSysUserInfoList'
            , where: $(".search_getSysUserInfoList_form").serializeObject()
        });
    })
}

/**
 * 渲染查询开始时间和结束时间
 */
function laydate_manager(laydate) {
    //单控件
    laydate.render({
        elem: '#condictionstart'
    })
    laydate.render({
        elem: '#condictionend'
    })
}

function table_manger(table, layer, laydate, layform) {
    //监听工具条
    table.on('tool(SysUserInfoFilter)', function (obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值
        active[layEvent] ? active[layEvent].call(data, layer, laydate, layform) : "";
    });
}

var active = {
    add: function (layer, laydate, layform) {
        layer.open({
            type: 1
            , offset: '200px'
            , area: '500px'
            , id: 'layerDemo' + this.id //防止重复弹出
            , content: getContentTmp()
            , btn: ['确认', '重置', '关闭']
            , btnAlign: 'c' //按钮居中
            , shade: 0 //不显示遮罩
            , yes: function () {
                addSysUserInfoList(layer);
            }, btn2: function (index, layero) {
                //重置
                $(".resetbtn").click();
                return false;
            }, btn3: function (index, layero) {
                //关闭
                layer.closeAll();
            }, success: function (layero, index) {
                InitLayOpen(laydate, layform);
            }
        });
    },
    del: function (layer) {
        var tmpdata = this;
        layer.confirm('真的删除行么', function (index) {
            var posturl = static_path + "SysUserInfoManagerController/delSysUser";
            var objTmp = {user_id: tmpdata.user_id};
            console.log(objTmp)
            $.post(posturl, objTmp, function (result) {
                console.log(result)
                if (result.code == '1000') {
                    location.reload();
                } else {
                    layer.msg(result.msg)
                }
            }, "json");
        });
    }
}

//    渲染弹出层
function InitLayOpen(laydate, layform) {
    $(".normalinputwidth").css('width', '350px');
    $(".requiredcontent").append("<span style='color:red;'>*</span>");

    laydate.render({
        elem: '#user_birthdayid'
    });
    layform.render();
    //初始化select
    initSelect(layform);
}

/**
 * 增加用户
 */
function addSysUserInfoList(layer) {
    var posturl = static_path +
        $(".addSysUserForm").attr("action")
        , tmp = $(".addSysUserForm").serializeObject();

    if (!(isNotBlank(tmp.user_phone, "手机号", layer) && isNotBlank(tmp.user_name, "昵称", layer)
            && isNotBlank(tmp.role_id, "角色", layer))) {
        return;
    }


    $.post(posturl, tmp, function (result) {
        if (result.code == '1000') {
            location.reload();
        } else {
            layer.msg(result.msg)
        }
    }, "json");
}

/**
 * 取下拉框数据
 */
function initSelect(form) {
    var posturl = static_path + "/SysUserInfoManagerController/getSysRoleListNoPage";
    $.post(posturl, {}, function (result) {
        if (result.code == '0' && result.data.length > 0) {
            createOption(result.data);
            form.render('select');
            $(".layui-form-select").addClass('normalinputwidth');
        } else {
            layer.msg("请先创建角色");
        }
    }, "json");
}

/**
 * 绑定角色下拉框的拼接
 * @param data
 */
function createOption(data) {
    var html = '';
    for (var i = data.length - 1; i >= 0; i--) {
        html += ' <option value="' + data[i].role_id + '">' + data[i].role_value + '</option>\n ';
    }
    $("#role_id_select_id").html(html);
}

/**
 * 拼接弹出层
 */
function getContentTmp() {
    var contenttmp = '<form class="layui-form addSysUserForm"  style="margin:30px auto;"  lay-filter="addSysUserFormFilter" method="post" ' +
        'action="SysUserInfoManagerController/addSysUser">\n' +
        '       <div class="layui-form-item">\n' +
        '             <label class="layui-form-label requiredcontent">昵称</label>\n' +
        '             <div class="layui-input-block">\n' +
        '                <input type="text" name="user_name"    lay-verify="required"  class="layui-input normalinputwidth">\n' +
        '             </div>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label requiredcontent">手机</label>\n' +
        '            <div class="layui-input-block"> ' +
        '                <input type="number" name="user_phone" class="layui-input normalinputwidth"    >\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label requiredcontent">邮箱</label>\n' +
        '            <div class="layui-input-block">\n' +
        '                <input type="text" name="user_email" lay-verify="required"   \n' +
        '       class="layui-input normalinputwidth">\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="layui-form-item" pane>\n' +
        '            <label class="layui-form-label">性别</label>\n' +
        '            <div class="layui-input-block">\n' +
        '                <input type="radio" name="user_sex" value="1" title="男" checked  >\n' +
        '                <input type="radio" name="user_sex" value="2" title="女"   >\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label ">生日</label>\n' +
        '            <div class="layui-input-block">\n' +
        '                <input type="text" name="user_birthday_form" id="user_birthdayid" class="layui-input normalinputwidth" readonly   >\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label">真实姓名</label>\n' +
        '            <div class="layui-input-block">\n' +
        '                <input type="text" name="real_name" class="layui-input normalinputwidth">\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label requiredcontent">角色</label>\n' +
        '            <div class="layui-input-block">\n' +
        '               <select name="role_id" id="role_id_select_id" lay-filter="role_id_filter">\n' +
        '               </select>     \n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <button type="reset" style="display:none"  class="layui-btn layui-btn-primary resetbtn">重置</button>\n' +
        '    </form>';
    return contenttmp;
}

/**
 * 判断不为空
 * @param obj
 * @param errorMsg
 * @param layer
 * @returns {boolean}
 */
function isNotBlank(obj, errorMsg, layer) {
    if (obj == undefined || obj == null || obj == '') {
        layer.msg(errorMsg + "不能为空");
        return false;
        s
    }
    return true;
}