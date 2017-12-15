var isDebugger = true;//开发者模式

//1、渲染库存

function tableSetting(table, datastring) {

    var tmp = statictable.render({ //其它参数在此省略
        id:"sysorder_pro_id"
        ,elem: '#sysorder_pro_id' //或 elem: document.getElementById('test') 等
        , data: datastring
        , height: 'full-453'
        , cols: [  [{align: 'center', title: '订单列表', colspan: 5}],
            [  {field: 'goods_id', align: 'center', title: '商品货号', width: 150}
                , {field: 'goods_name', align: 'center', title: '商品名称', width: 150}
                , {field: 'num', align: 'center', title: '进货数量', width: 80, edit: 'text'}
                , {field: 'goods_color', align: 'center', title: '商品颜色', width: 110  }
                , {
                align: 'center',
                title: '操作',
                width: 150,
                align: 'center',
                toolbar: '#sysorder_pro_toolbar'
            } //这里的toolbar值是模板元素的选择器
            ]]
        , skin: 'row' //表格风格
        , even: true
        , size: 'sm' //小尺寸的表格
    });
    orderTableData = tmp.config.data;
    sumMoneySet();
}

var sumMoney=0,         //合计
    littleAccount=0;    //小计


//2 将左边表格选中的数据拼接成右边表格的数据
function settingData(obj) {
    if (obj == '') {
        return false;
    }
    var dataString = orderTableData;
    //通过goods_id去除重复添加
    if (dataString.length > 0) {
        for (var i = 0; i < dataString.length; i++) {
            if (dataString[i].id == obj.id) {
                layer.msg(obj.goods_name + "已经添加过了");
                return false;
            }
        }
    }


    obj.table=null;
    obj.num= 1;
    obj.remark="无";
    dataString.push(obj);
    return dataString;
}

/**
 * 计算价格
 */
function sumMoneySet(){
    sumMoney=0;
    for(var tmp =0 ;tmp<  orderTableData.length;tmp++){
        sumMoney+=orderTableData[tmp].goods_sale_price*orderTableData[tmp].num;
    }

    $(".result span")[0].innerHTML = sumMoney/100+'/元';
    $(".result span")[1].innerHTML = sumMoney/100+'/元';
}

//del data删除元素
function delSettingData(obj) {
    if (obj == '') {
        return false;
    }
    var dataString = orderTableData;
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
var accountResultActive = {
    submit: function(table) { //获取选中数据

        var remark = $(".remarkTextArea").val()
            ,subMitData=new Object();

        subMitData.ctOrderDetailDTOS=[];

        for (tmp in orderTableData) {
            if (isNaN(tmp)) {
                continue;
            }
            var subTmp = new Object();
            subTmp.stock_id=orderTableData[tmp].id;
            subTmp.goods_num=orderTableData[tmp].num;
            subMitData.ctOrderDetailDTOS.push(subTmp);
        }

        subMitData.ct_user_info_id = ct_user_info_id;
        subMitData.remark = remark;

        var url = static_path + "ConsumeManagerController/accountResultActive";

        $.ajax({
            type: 'POST',
            url: url,
            data: JSON.stringify(subMitData)
            , success: function (result) {
                layer.msg(result.msg)
                if (result.code == '1000') {

                    layer.open({
                        content: '是否继续下单？'
                        , btn: ['继续', '打印小票' ]
                        , yes: function (index, layero) {
                            location.reload();
                        }
                        , btn2: function (index, layero) {
                          location.href = orderlisturl+"?orderId="+result.result;
                        }
                    });
                }
            }
            , contentType: "application/json; charset=UTF-8"
            , dataType: "json"
        });
    }


};





/**
 * 选择会员弹出层
 */
var selectCtuUser = function(){

    var contenttmp =
        '<fieldset class="layui-elem-field">\n' +
        '    <legend>选择会员</legend>\n' +
        '    <div class="layui-field-box">\n' +
        '        <form class="layui-form alert_quick_search_form" method="post"\n' +
        '                      <label class="layui-form-label"></label>\n' +
        '            <div class="layui-form-item">\n' +
         '               <div class="layui-inline width100" >\n' +
         '                     <label class="layui-form-label">卡号</label>\n' +
         '                     <div class="layui-input-inline width60"  >\n' +
         '                         <input type="text" name="card_no"  class="layui-input">\n' +
         '                     </div>'+
         '                </div>\n'+
         '                <div class="layui-inline width100"  >\n' +
         '                     <label class="layui-form-label">姓名</label>\n' +
         '                     <div class="layui-input-inline width60">\n' +
         '                         <input type="text" name="real_name"  class="layui-input">\n' +
         '                     </div>'+
         '                </div>\n'+
         '                <div class="layui-inline width100"  >\n' +
         '                     <label class="layui-form-label">手机号</label>\n' +
         '                     <div class="layui-input-inline width60">\n' +
         '                         <input type="number" name="mobile_phone"  class="layui-input">\n' +
         '                     </div>'+
         '                </div>\n'+
         '               <div class="layui-inline width100" style="text-align:center" >\n' +
         '                    <input type="button"  class="layui-btn " onclick="updateQuickSearch()" value="条件查询"></input>\n' +
         '                </div>\n'+
        '            </div>\n' +
        '        </form>\n' +
        '    </div>\n' +
        '</fieldset>' +
        '<table class="layui-table" id="quickSearchId" lay-filter="quickSearchFilter"' +
        ' lay-size="sm"></table>';

    layer.open({
        type: 1
        ,offset: '50px'
        ,area: '500px'
        ,shade: 0.8
        ,id: 'selectCtuUser' //设定一个id，防止重复弹出
        ,moveType: 1 //拖拽模式，0或者1
        ,content: contenttmp
        ,success: function(layero){
            //回调函数
            selectQuickSearch();
        }
    });
}


var ct_user_info_id= "";//会员信息

/**
 * 卡号信息
 */
var cardInfoSearch = function () {
    $.ajax({
        type: 'POST',
        dataType:"json",
        url: static_path+ 'CtuManagerController/getCtuserList',
        data: {card_no:$(".card_no_search input").val()},
        success: function (result) {
            if(result.data.length==1){
                setCtUserInfoId(result.data[0])
            }
        },error:function (error) {
        }
    });

}

/**
 * 弹出层table 渲染
 */
function selectQuickSearch() {
    statictable.render({ //其它参数在此省略
        id:"quickSearchId"
        ,url: static_path+ 'CtuManagerController/getCtuserList'
        ,elem: '#quickSearchId'
        , height: 'full-500'
        , width: 500
        , cols: [
            [    {field: 'card_no', align: 'center', title: '卡号', width: 150}
                , {field: 'real_name', align: 'center', title: '用户名', width: 120}
                , {field: 'mobile_phone', align: 'center', title: '手机', width: 135}
                , {
                align: 'center',
                title: '操作',
                width: 90,
                align: 'center',
                toolbar: '#ctusertoolbar'
            }
            ]]
        , skin: 'row' //表格风格
        , even: true
        , size: 'sm' //小尺寸的表格
        , page: false
    });
}

/**
 * 重新 table 渲染
 */
function updateQuickSearch() {
    statictable.reload('quickSearchId',{ //其它参数在此省略
         height: 'full-500'
        ,url: static_path+ 'CtuManagerController/getCtuserList'
        ,where: $(".alert_quick_search_form").serializeObject()
    });
}


