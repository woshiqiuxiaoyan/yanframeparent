package user.dto;import lombok.Data;import pojo.CtOrders;/** *  * <br> * <b>功能：</b>CtOrdersEntity<br> * <b>作者：</b>qxy<br> * <b>日期：</b> ${date} <br> * <b>版权所有：<b>版权所有(C) 2017，芊朵恋<br> */@Datapublic class CtOrdersDTO extends CtOrders{   CtOrderDetailDTO [] ctOrderDetailDTOS;   private String real_name;   private String card_no;   private String mobile_phone;   private Integer accumulate_integral;   private String user_name;   private int page;   private int limit;}