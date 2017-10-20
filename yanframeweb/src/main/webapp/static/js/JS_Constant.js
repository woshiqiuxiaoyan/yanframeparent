var static_path = "http://localhost:8080/";
var menu_code_ctuserList = "22";  //会员列表 权限id
var index_page_main_content=static_path+"IndexController/index";//首页
var ctuserListurl = static_path+"CtuManagerController/ctuserList/"+menu_code_ctuserList//会员列表url


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
