var static_path = "http://114.215.116.198/";
var isDebug=true;//开发者模式

if(isDebug){
    static_path = "http://localhost:8091/";
}


var menu_code_ctuserList = "22";  //会员列表 权限id
var menu_code_instock = "42";  //库存列表 权限id
var menu_cod_goodsInfo="32";//产品列表 权限id

// var index_page_main_content=static_path+"IndexController/index";//首页
var ctuserListurl = static_path+"CtuManagerController/ctuserList/"+menu_code_ctuserList//会员列表url
var goodsInfoListurl = static_path+"GoodsInfoManagerController/goodsInfoList/"+menu_cod_goodsInfo//产品列表url
var instockInfoListurl = static_path+"SysStockController/stockListPage/"+menu_code_instock//库存列表url


//表单转对像
$.prototype.serializeObject = function () {
    var a, o, h, i, e;
    a = this.serializeArray();
    o = {};
    h = o.hasOwnProperty;
    for (i = 0; i < a.length; i++) {
        e = a[i];
        if (!h.call(o, e.name)) {
            o[e.name] = e.value;
        }
    }
    return o;
};


Array.prototype.del = function (n) {　//n表示第几项，从0开始算起。
    if (n < 0)　//如果n<0，则不进行任何操作。
        return this;
    else
        return this.slice(0, n).concat(this.slice(n + 1, this.length));
}