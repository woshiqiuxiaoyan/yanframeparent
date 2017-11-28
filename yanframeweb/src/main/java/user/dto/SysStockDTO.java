package user.dto;


import lombok.Data;
import org.joda.time.DateTime;
import pojo.SysStock;

@Data
public class SysStockDTO extends SysStock{


    private String store_name;//所属店铺

    private String goods_id;

    private String goods_name;

    private String goods_img_url;

    private double goods_instock_price;//进货价

    private double goods_sale_price;//售价

    private String goods_color;

    private java.lang.Integer goods_size;//   商品尺码 6 无 ,0 S  ,1 M ,2 L,3 xl,4 xxl,5 xxxl

    private String[] img_url_show;

    private int page;

    private int limit;

    private DateTime start_time;

    private DateTime end_time;
}

