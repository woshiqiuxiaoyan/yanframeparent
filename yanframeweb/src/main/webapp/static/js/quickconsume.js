var isDebugger = true;//开发者模式



//1、渲染库存

function tableSetting(table, datastring) {

    orderTableData  = table.render({ //其它参数在此省略
        id:"sysorder_pro_id"
        ,elem: '#sysorder_pro_id' //或 elem: document.getElementById('test') 等
        , data: datastring
        , height: 'full-350'
        , cols: [  [{align: 'center', title: '订单列表', colspan: 6}],
            [ //标题栏
                {align: 'center', checkbox: true,fixed:true, LAY_CHECKED: true}
                , {field: 'goods_id', align: 'center', title: '商品货号', width: 150}
                , {field: 'goods_name', align: 'center', title: '商品名称', width: 150}
                , {field: 'num', align: 'center', title: '进货数量', width: 110, edit: 'text'}
                , {
                fixed: 'right',
                align: 'center',
                title: '操作',
                width: 150,
                align: 'center',
                toolbar: '#sysorder_protoolbar'
            } //这里的toolbar值是模板元素的选择器
            ]]
        , skin: 'row' //表格风格
        , even: true
        , size: 'sm' //小尺寸的表格
        , limits: [5, 10, 15, 20, 25]
        , limit: 5 //默认采用60
        , page: true
    });
}


//2 将左边表格选中的数据拼接成右边表格的数据
function settingData(obj) {
    if (obj == '') {
        return false;
    }
    var dataString = inStockTableData.config.data;
    //通过goods_id去除重复添加
    if (dataString.length > 0) {
        for (var i = 0; i < dataString.length; i++) {
            if (dataString[i].id == obj.id) {
                layer.msg(obj.goods_name + "已经添加过了");
                return false;
            }
        }
    }
    obj.num= 0;
    obj.remark="无";
    dataString.push(obj);  return dataString;
}

Array.prototype.del = function (n) {　//n表示第几项，从0开始算起。
    if (n < 0)　//如果n<0，则不进行任何操作。
        return this;
    else
        return this.slice(0, n).concat(this.slice(n + 1, this.length));
}

//del data删除元素
function delSettingData(obj) {
    if (obj == '') {
        return false;
    }
    var dataString = inStockTableData.config.data;
    //通过goods_id去除重复添加
    if (dataString.length > 0) {
        for (var i = 0; i < dataString.length; i++) {
            if (dataString[i].id == obj.id) {
                return dataString.del(i);
            }
        }
    }
    return false;
}


//增加一行
var addInstock = function () {
    var dataString = settingData(this);
    if (!dataString) {
        return;
    }
    tableSetting(this.table, dataString);
}


//删除一行
var delInstock = function () {
    var rowdata = this;
    this.layer.confirm('真的删除行么', function (index) {
        var dataString = delSettingData(rowdata);
        if (!dataString) {
            return;
        }
        tableSetting(rowdata.table, dataString);
        rowdata.layer.close(index);
    });
}

//保存库存相应操作
var  instockbtnActive = {
    getCheckData: function(table){ //获取选中数据

        var checkStatus = table.checkStatus('inStockTableId');
        var data = checkStatus.data;
        if(data.length==0){
            layer.alert("未选中任何商品");
            return ;
        }

        var subData = [];//提交的数据

        for(tmp in data){
            var subTmp = new Object();
            subTmp.goods_info_id = data[tmp].id;
            subTmp.num = data[tmp].num;
            subTmp.remark = data[tmp].remark;
            subData[tmp] = (subTmp);
        }

        var url = static_path+"SysStockController/inStock";

        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(subData)
            ,success: function (result) {
                layer.msg(result.msg)
                if (result.code == '1000') {
                    layer.open({
                        content: '是否继续入库？'
                        ,btn: ['继续', '不加了']
                        ,yes: function(index, layero){
                            location.reload();
                        }
                        ,btn2: function(index, layero){
                            location.href=instockInfoListurl;
                        }
                    });
                }
            }
            ,contentType: "application/json; charset=UTF-8"
            ,dataType:"json"
        });

        // layer.alert(JSON.stringify(data));
    }
    ,getCheckLength: function(table){ //获取选中数目
        var checkStatus = table.checkStatus('inStockTableId')
            ,data = checkStatus.data;
        layer.msg('选中了：'+ data.length + ' 个');
    }
    ,isAll: function(table){ //验证是否全选
        var checkStatus = table.checkStatus('inStockTableId');
        layer.msg(checkStatus.isAll ? '全选': '未全选')
    }
};





/**
 * 选择会员弹出层
 */
var selectCtuUser = function(){

    var contenttmp =  ' <table class="layui-table" id="customcardlisttableid" lay-filter="customcardlisttablefilter"' +
        ' lay-size="sm"></table>';
    layer.open({
        type: 1
        ,offset: '100px'
        ,area: '300px;'
        ,shade: 0.8
        ,id: 'selectCtuUser' //设定一个id，防止重复弹出
        ,btn: ['看完了']
        ,btnAlign: 'c'
        ,moveType: 1 //拖拽模式，0或者1
        ,content: contenttmp
        ,success: function(layero){

            selectCtuUserPingjie();

        }
    });
}


function selectCtuUserPingjie() {

    var dataString = '';
    table.render({ //其它参数在此省略
        id:"customcardlisttableid"
        ,elem: '#customcardlisttableid' //或 elem: document.getElementById('test') 等
        , data: dataString
        , height: 'full-350'
        , cols: [
            [ //标题栏
                {align: 'center', checkbox: true,fixed:true, LAY_CHECKED: true}
                , {field: 'card_no', align: 'center', title: '卡号', width: 150}
                , {field: 'real_name', align: 'center', title: '用户名', width: 150}
                , {field: 'mobile_phone', align: 'center', title: '手机', width: 110, edit: 'text'}
                , {
                fixed: 'right',
                align: 'center',
                title: '操作',
                width: 150,
                align: 'center',
                toolbar: '#sysorder_protoolbar'
            } //这里的toolbar值是模板元素的选择器
            ]]
        , skin: 'row' //表格风格
        , even: true
        , size: 'sm' //小尺寸的表格
        , limits: [5, 10, 15, 20, 25]
        , limit: 5 //默认采用60
        , page: true
    });
    
}
