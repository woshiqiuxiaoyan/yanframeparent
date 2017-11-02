
var isDebugger = false;//开发者模式
/**
 * 格式化
 * @param datetime
 * @param fmt
 * @returns {*}
 * @constructor
 */
function Format(datetime,fmt) {
    if(datetime==undefined){
        return "无";
    }

    if (parseInt(datetime)==datetime) {
        if (datetime.length==10) {
            datetime=parseInt(datetime)*1000;
        } else if(datetime.length==13) {
            datetime=parseInt(datetime);
        }
    }
    datetime=new Date(datetime);
    var o = {
        "M+" : datetime.getMonth()+1,                 //月份
        "d+" : datetime.getDate(),                    //日
        "h+" : datetime.getHours(),                   //小时
        "m+" : datetime.getMinutes(),                 //分
        "s+" : datetime.getSeconds(),                 //秒
        "q+" : Math.floor((datetime.getMonth()+3)/3), //季度
        "S"  : datetime.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (datetime.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}



/**
 * 宣染图片
 */
function carousel_manager(carousel) {
    carousel.render({
        elem: '#test1'
        ,width: '100%'
        , interval: 5000
    });

    carousel.render({
        elem: '#goodsInfoEditImg'
        ,width: '100%'
        , interval: 5000
    });
}


/**
 * 渲染日期控件
 * @param laydate
 */
function laydate_manager(laydate){
    //单控件
    laydate.render({
        elem: '#condictionstart'
    })
    laydate.render({
        elem: '#condictionend'
    })
}


/**
 * 取尺寸
 * @param goods_size
 * @returns {string}
 */
function getGoodsSize(goods_size){
    var goods_size_name='';
    switch (goods_size){
        case 0:
            goods_size_name='S';
            break;
        case 1:
            goods_size_name='M';
            break;
        case 2:
            goods_size_name='L';
            break;
        case 3:
            goods_size_name='XL';
            break;
        case 4:
            goods_size_name='XXL';
            break;
        case 5:
            goods_size_name='XXXL';
            break;
        default:
            goods_size_name='无';
    }

    return goods_size_name;
}



//构造尺寸html
function goodsSizeHmtl(goods_size){
    var tmpdata = '      <select name="goods_size"    lay-verify="required">\n' ;

    for(var i= 0;i<=6;i++){
        console.log("i:"+i+" goods_size:"+goods_size+"  " +(i==goods_size)+"   "+getGoodsSize(i));
        if(i!=goods_size){
            tmpdata+=' <option value="'+i+'">'+getGoodsSize(i)+'</option>\n ' ;
        }else{
            tmpdata+=' <option value="'+i+'" selected>'+getGoodsSize(i)+'</option>\n ' ;
        }
    }
    tmpdata+='      </select>\n' ;


    return tmpdata;
}


/**
 * 拼接成轮播图片html
 */
function imgShowHtml(img_url_show,id){
    var tmpdataimg= '';
    if(img_url_show!=null&&img_url_show!=undefined&&img_url_show.length>0&&img_url_show[0]!='#'){
        tmpdataimg= '<div class="layui-carousel" id="test1">\n' +
            '  <div carousel-item>\n';
        for(var i=0;i<img_url_show.length;i++){
            tmpdataimg +=   '    <div>  <img style="width:100%;height:100%;"   src="'+img_url_show[i]+'"></div>\n' ;
        }
        tmpdataimg+='  </div>\n' +
            '</div>';
    }

    return tmpdataimg;
}


//拼接商品信息编辑
function  goodsInfoEditDataPingjie(obj){

    //图片
    var tmpdataimg=  imgShowHtml(obj.img_url_show,'goodsInfoEditImg');

    var pingjiedata='<form class="layui-form editGoodsInfoForm"  lay-filter="customcardlistform" method="post" action="CtuManagerController/updateCtUserInfo">\n' +
        '<input type="hidden" name="id" value="'+obj.id+'">'+
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label requiredcontent">商品货号</label>\n' +
        '            <div class="layui-input-block">\n' +
        '                <input type="text" name="goods_id"    readonly required  \n' +
        '      value="'+obj.goods_id+'"        class="layui-input normalinputwidth">\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label requiredcontent">商品名称</label>\n' +
        '            <div class="layui-input-block">\n' +
        '                <input type="text" name="goods_name" lay-verify="required" required placeholder="请输入商品名称"\n' +
        '     value="'+obj.goods_name+'"         autocomplete="on" class="layui-input normalinputwidth">\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label">进货价格</label>\n' +
        '            <div class="layui-input-block">\n' +
        '                <input type="tel" name="goods_instock_price" autocomplete="on" placeholder="请输入进货价格"\n' +
        '    value="'+obj.goods_instock_price+'"  class="layui-input normalinputwidth">\n' +
        '            </div>\n' +
        '\n' +
        '        </div>\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label">售价</label>\n' +
        '            <div class="layui-input-block">\n' +
        '                <input type="text" name="goods_sale_price" placeholder="请输入售价" autocomplete="on"\n' +
        '     value="'+obj.goods_sale_price+'"        class="layui-input normalinputwidth">\n' +
        '            </div>\n' +
        '        </div>\n' +
        '\n' +
        '        <div class="layui-form-item">\n' +
        '            <label class="layui-form-label">商品颜色</label>\n' +
        '            <div class="layui-input-block">\n' +
        '                <input type="text" name="goods_color" autocomplete="on" placeholder="请输入商品颜色"\n' +
        '   value="'+obj.goods_color+'"  class="layui-input normalinputwidth">\n' +
        '            </div>\n' +
        '\n' +
        '        </div>\n' +
        '\n' +
        '<div class="layui-form-item">\n' +
        '    <label class="layui-form-label">商品尺码</label>\n' +
        '    <div class="layui-input-block">\n'
        +goodsSizeHmtl(obj.goods_size)+
        '    </div>\n' +
        '  </div>'+
        '        <div class="layui-form-item">\n' +
        '\n' + tmpdataimg   +
        '        </div>\n' +
        '        <div class="layui-form-item layui-form-text">\n' +
        '            <label class="layui-form-label">备注</label>\n' +
        '            <div class="layui-input-block">\n' +
        '                <textarea name="remark" placeholder="请输入内容"' +
        '    class="layui-textarea normalinputwidth">'+obj.remark+'</textarea>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <button type="reset" style="display:none"  class="layui-btn layui-btn-primary resetbtn">重置</button>\n' +
        '    </form>'
    return pingjiedata;
};


/**
 * 商品详情弹出层
 */
var detailView = function(){
    //   拼接轮播图片html
    var tmpdataimg=  imgShowHtml(this.img_url_show,'test1');

    var contenttmp = '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">'
        +'商品货号:'+this.goods_id +'<br>'
        +'商品名称:'+this.goods_name +'<br>'
        +'进货价格    :'+this.goods_instock_price +'<br>'
        +'售价   :'+this.goods_sale_price +'<br>'
        +'商品颜色     :'+this.goods_color+'<br>'
        +'商品尺码     :'+getGoodsSize(this.goods_size)+'<br>'
        +'创建人  :'+this.user_name+'<br>'
        +'备注    :'+this.remark+'<br>'
        +'创建时间 :'+Format(this.creat_time,"yyyy-M-d")+'<br>'
        +tmpdataimg;
    layer.open({
        type: 1
        ,offset: '100px'
        ,title: false //不显示标题栏
        ,closeBtn: false
        ,area: '300px;'
        ,shade: 0.8
        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
        ,btn: ['看完了']
        ,btnAlign: 'c'
        ,moveType: 1 //拖拽模式，0或者1
        ,content: contenttmp
        ,success: function(layero){
            carousel_manager(carouseltest);
            if(isDebugger){
                console.log(layero);
            }
        }
    });
}
