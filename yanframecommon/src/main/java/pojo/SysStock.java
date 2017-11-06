package pojo;


import lombok.Data;

@Data
public class SysStock   {

	private java.lang.Integer id;//   
	private java.lang.Integer goods_info_id;//   商品ID(对应 sys_goods_info 的id)
	private java.lang.Integer total;//   库存数量
	private java.lang.Integer store_id;//   所属店铺
	private java.util.Date create_time;//   
	private java.util.Date update_time;//   
	private java.lang.String remark;//   备注
	private java.lang.Integer deleted;//   1 正常 0 删除
}

