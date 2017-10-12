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



var layerObj, $, laytpl, form, laypage, element, laydate;
layui.use(['layer', 'jquery', 'laytpl', 'form', 'laypage', 'element', 'laydate', 'tree', 'layedit', 'upload'], function () {
    layerObj = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        form = layui.form,
        laypage = layui.laypage,
        element = layui.element,
        laydate = layui.laydate,
        layedit = layui.layedit;

    onComplete();
});


