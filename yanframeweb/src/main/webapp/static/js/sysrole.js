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
    }
    return true;
}

/**
 *  渲染查询按钮
 */
function initSearch_btn(table) {
    $(".search_btn").on('click',function () {
        table.reload('getSysRoleListId', {
            url: '/SysUserInfoManagerController/getSysRoleList'
            ,where: $(".search_getSysRoleList_form").serializeObject()
        });
    })
}



function table_manger(table,layer,laydate,layform){
    //监听工具条
    table.on('tool(getSysRoleListFilter)', function (obj) {
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值

        if (layEvent === 'detail' || layEvent === 'add') { //查看
            var othis = $(this), method = layEvent;
            active[method] ? active[method].call(data,layui) : '';
        } else if (layEvent === 'del'){//删除
            layer.confirm('真的删除行么', function (index) {
                //更新url
                var posturl  = static_path+"SysUserInfoManagerController/delSysRole"
                    ,objTmp = {role_id:obj.data.role_id};
                $.post(posturl, objTmp, function (result) {
                    if (result.code == '1000') {
                        obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
                    }else{
                        layer.msg(result.msg)
                    }
                }, "json");
                layer.close(index);
            });
        }
    });



    //触发事件
    var active = {
        detail: function( ){
            var contenttmp = '<ul id="permission_tree_id"    class="ztree"></ul>';
            var activethis =this;
            //示范一个公告层
            layer.open({
                type: 1
                ,title: false //不显示标题栏
                ,offset: '100px'
                ,area: '300px'
                ,id: 'LAY_layuiprodetail' //设定一个id，防止重复弹出
                ,btnAlign: 'c'
                ,moveType: 1 //拖拽模式，0或者1
                ,content: contenttmp
                ,success: function(layero){
                    tree_permission( activethis.role_id);
                }
            });
        },
        add:function () {
            layer.open({
                type: 1
                ,title: false //不显示标题栏
                ,offset: '100px'
                ,area: '500px'
                ,id: 'LAY_layuiproadd' //设定一个id，防止重复弹出
                ,btn: ['确认','重置','关闭']
                ,btnAlign: 'c'
                ,moveType: 1 //拖拽模式，0或者1
                ,content: getContentTmp()
                ,yes: function(){
                    //更新url
                    var posturl  = static_path+"SysUserInfoManagerController/addSysRole"
                        ,objdata = $(".addSysRoleForm").serializeObject();

                    if(!(isNotBlank(objdata.role_value,"角色名称",layer)&&isNotBlank(objdata.description,"描述",layer))){
                        return ;
                    }

                    $.post(posturl, $(".addSysRoleForm").serializeObject(), function (result) {
                        if (result.code == '1000') {
                            location.reload();
                        }else{
                            layer.msg(result.msg)
                        }
                    }, "json");
                },btn2: function(index, layero){
                    $(".resetbtn").click();
                    return false;
                },btn3: function(index, layero){
                    layer.closeAll();

                },success: function(layero){
                    layform.render();
                    $(".requiredcontent").append("<span style='color:red;'>*</span>");
                    $(".layui-form-select").addClass('normalinputwidth');
                    //打开成功后的回调
                    $(".normalinputwidth").css('width','350px');
                }
            });

        }

    };
}

/**
 * 取权限
 */
function tree_permission( role_id){
    var posturl = static_path + "SysUserInfoManagerController/queryPerssionByRoleId"
        ,objTmp = {"role_id": role_id};

    $.post(posturl, objTmp, function (result) {
        console.log(result);
        var tmpdata =  result.result;
        var arr=[];
//            for(var i=tmpdata.length-1;i>=0;i--){
        for(var i=0;i<tmpdata.length;i++){
            arr.push({id:tmpdata[i].menu_code, pId:tmpdata[i].parent_menucode, checked:tmpdata[i].checked
                ,name:tmpdata[i].menu_name,open:true,myid:tmpdata[i].id,role_id:tmpdata[i].role_id
                ,menu_type:tmpdata[i].menu_type})
        }
        createmineztree(arr);
    }, "json");

}

/**
 * 生成 ztree
 */
function createmineztree(arr){

    var setting = {
        check: {
            enable: true
            ,autoCheckTrigger: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback:{
            onCheck:function(event, treeId, treeNode){
                var objTmp = {role_id:treeNode.role_id,checked:treeNode.checked
                    ,menu_type:treeNode.menu_type,menu_code:treeNode.id}
                    ,posturl= static_path +"SysUserInfoManagerController/updateRolePerssion"
                $.post(posturl, objTmp, function (result) {
                    console.log(result);

                }, "json");

            }
        }
    };


    $.fn.zTree.init($("#permission_tree_id"), setting, arr);

};




/**
 * 拼接弹出层
 */
function getContentTmp() {
    var contenttmp = '<form class="layui-form addSysRoleForm"  style="margin:30px auto;"  lay-filter="addSysRoleFormFilter" method="post" ' +
        'action="">\n' +
        '       <div class="layui-form-item">\n' +
        '             <label class="layui-form-label requiredcontent">角色名称</label>\n' +
        '             <div class="layui-input-block">\n' +
        '                <input type="text" name="role_value"    lay-verify="required"  class="layui-input normalinputwidth">\n' +
        '             </div>\n' +
        '        </div>\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label requiredcontent">描述</label>\n' +
        '            <div class="layui-input-block"> ' +
        '                <input type="text" name="description" class="layui-input normalinputwidth"    >\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <button type="reset" style="display:none"  class="layui-btn layui-btn-primary resetbtn">重置</button>\n' +
        '    </form>';
    return contenttmp;
}
