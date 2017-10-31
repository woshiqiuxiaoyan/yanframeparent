
function minestocktableManager(table){

    table.render({ //其它参数在此省略
        elem: '#inStockTableId' //或 elem: document.getElementById('test') 等
        ,data: [{
            "id": "10001"
            ,"username": "杜甫"
            ,"email": "xianxin@layui.com"
            ,"sex": "男"
            ,"city": "浙江杭州"
            ,"sign": "人生恰似一场修行"
            ,"experience": "116"
            ,"ip": "192.168.0.8"
            ,"logins": "108"
            ,"joinTime": "2016-10-14"
        }, {
            "id": "10002"
            ,"username": "李白"
            ,"email": "xianxin@layui.com"
            ,"sex": "男"
            ,"city": "浙江杭州"
            ,"sign": "人生恰似一场修行"
            ,"experience": "12"
            ,"ip": "192.168.0.8"
            ,"logins": "106"
            ,"joinTime": "2016-10-14"
            ,"LAY_CHECKED": true
        }, {
            "id": "10003"
            ,"username": "王勃"
            ,"email": "xianxin@layui.com"
            ,"sex": "男"
            ,"city": "浙江杭州"
            ,"sign": "人生恰似一场修行"
            ,"experience": "65"
            ,"ip": "192.168.0.8"
            ,"logins": "106"
            ,"joinTime": "2016-10-14"
        }, {
            "id": "10004"
            ,"username": "贤心"
            ,"email": "xianxin@layui.com"
            ,"sex": "男"
            ,"city": "浙江杭州"
            ,"sign": "人生恰似一场修行"
            ,"experience": "666"
            ,"ip": "192.168.0.8"
            ,"logins": "106"
            ,"joinTime": "2016-10-14"
        }, {
            "id": "10005"
            ,"username": "贤心"
            ,"email": "xianxin@layui.com"
            ,"sex": "男"
            ,"city": "浙江杭州"
            ,"sign": "人生恰似一场修行"
            ,"experience": "86"
            ,"ip": "192.168.0.8"
            ,"logins": "106"
            ,"joinTime": "2016-10-14"
        }, {
            "id": "10006"
            ,"username": "贤心"
            ,"email": "xianxin@layui.com"
            ,"sex": "男"
            ,"city": "浙江杭州"
            ,"sign": "人生恰似一场修行"
            ,"experience": "12"
            ,"ip": "192.168.0.8"
            ,"logins": "106"
            ,"joinTime": "2016-10-14"
        }, {
            "id": "10007"
            ,"username": "贤心"
            ,"email": "xianxin@layui.com"
            ,"sex": "男"
            ,"city": "浙江杭州"
            ,"sign": "人生恰似一场修行"
            ,"experience": "16"
            ,"ip": "192.168.0.8"
            ,"logins": "106"
            ,"joinTime": "2016-10-14"
        }, {
            "id": "10008"
            ,"username": "贤心"
            ,"email": "xianxin@layui.com"
            ,"sex": "男"
            ,"city": "浙江杭州"
            ,"sign": "人生恰似一场修行"
            ,"experience": "106"
            ,"ip": "192.168.0.8"
            ,"logins": "106"
            ,"joinTime": "2016-10-14"
        }]
        ,height: 272
        ,cols: [[ //标题栏
            {field: 'goods_id', title: '商品货号', width: 120 }
            ,{field: 'goods_name', title: '商品名称', width: 120}
            ,{field: 'num', title: '进货数量', width: 120 ,edit:'text'}
            ,{fixed: 'right', width:150, align:'center', toolbar: '#instockinfotabletoolbar'} //这里的toolbar值是模板元素的选择器
        ]]
        ,skin: 'row' //表格风格
        ,even: true
        ,size: 'sm' //小尺寸的表格
    });
}

