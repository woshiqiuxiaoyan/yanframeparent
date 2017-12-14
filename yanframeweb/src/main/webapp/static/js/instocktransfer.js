//保存库存/选择分店相应操作
var instockTransferActive = {
    getCheckData: function (table) { //获取选中数据

        var checkStatus = table.checkStatus('inStockTableId');
        var data = checkStatus.data;
        if (data.length == 0) {
            layer.alert("未选中任何商品");
            return;
        }

        var subData = [];//提交的数据

        for (tmp in data) {
            var subTmp = new Object();
            subTmp.goods_info_id = data[tmp].id;
            subTmp.num = data[tmp].num;
            subTmp.remark = data[tmp].remark;
            subData[tmp] = (subTmp);
        }

        var url = static_path + "SysStockController/inStock";

        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(subData)
            , success: function (result) {
                layer.msg(result.msg)
                if (result.code == '1000') {
                    layer.open({
                        content: '是否继续入库？'
                        , btn: ['继续', '不加了']
                        , yes: function (index, layero) {
                            location.reload();
                        }
                        , btn2: function (index, layero) {
                            location.href = instockInfoListurl;
                        }
                    });
                }
            }
            , contentType: "application/json; charset=UTF-8"
            , dataType: "json"
        });

        // layer.alert(JSON.stringify(data));
    }
    , getCheckLength: function (table) { //获取选中数目
        var checkStatus = table.checkStatus('inStockTableId')
            , data = checkStatus.data;
        layer.msg('选中了：' + data.length + ' 个');
    }
    , isAll: function (table) { //验证是否全选
        var checkStatus = table.checkStatus('inStockTableId');
        layer.msg(checkStatus.isAll ? '全选' : '未全选')
    },
    //选择调拨的店铺
    chooseTargerStore: function (table, layform) {
        var contenttmp = createChooseStoreHtml(this);
        layer.open({
            type: 1
            , offset: '100px'
            , area: '500px'
            , id: 'layerDemo' //防止重复弹出
            , content: contenttmp
            , btn: ['确认', "关闭"]
            , btnAlign: 'c' //按钮居中
            , shade: 0.5 //不显示遮罩
            , yes: function () {
                choosedStore_id = $("#store_id_select_id").val()
                layer.closeAll();
            }, closeBtn: function () {
                layer.closeAll();
            }, success: function (layero, index) {
                layform.render();
                $(".layui-form-select").addClass('normalinputwidth');
                //打开成功后的回调
                $(".normalinputwidth").css('width', '350px');

            }
        });
    }
};

//获取商品下拉数据
function getStoreList() {
    var posturl = static_path + "SysStoreController/getStoreListForSelect";
    var return_result = "";
    $.ajax({
        type: "post",
        url: posturl,
        data: {},
        dataType: "json",
        async: false,
        success: function (result) {
            if (result.code == '1000') {
                return_result = result.result;
            } else {
                layer.msg(result.msg);
            }
        }
    });
    return return_result;
}

//拼接弹出选择商店页面的html
function createChooseStoreHtml(obj) {
    //取下拉html
    var tmpdataimg = createUpDownHtml()
    console.log(tmpdataimg)
    var pingjiedata = '<form class="layui-form"    style="height:200px;">\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label requiredcontent">商品货号</label>\n' +
        '            <div class="layui-input-block">\n' +
        tmpdataimg +
        '            </div>\n' +
        '        </div>\n' +
        '    </form>';
    return pingjiedata;
};


//构造下拉html
function createUpDownHtml(goods_size) {
    //取数据
    var selectData = getStoreList();
    var tmpdata = '      <select name="store_id" id="store_id_select_id"  >\n';
    for (var i = 0; i < selectData.length; i++) {
        tmpdata += ' <option value="' + selectData[i].store_id + '">' + selectData[i].store_name + '</option>\n ';
    }
    tmpdata += '      </select>\n';
    return tmpdata;
}