package user.dto;import constant.Constant;import lombok.Data;import lombok.ToString;import org.joda.time.DateTime;import pojo.CtUserInfo;@Datapublic class CtUserInfoDTO extends CtUserInfo {    private String OperatorName;//经办人姓名    private String referee;//推荐人    private Integer store_id;//所属店铺    private int page;    private int limit;    private DateTime start_time;//开始时间    private DateTime end_time;//结束时间    private String grade_name;//会员等级名称    public int getLimit() {        if(limit<=0){            limit= Constant.DefaultPageSize;        }        return limit;    }    public void setLimit(int limit) {        this.limit = limit;    }    private int pageNo;    private int pageSize;    public int getPageSize() {        if(pageSize<=0){            pageSize= Constant.DefaultPageSize;        }        return pageSize;    }    public void setPageSize(int pageSize) {        this.pageSize = pageSize;    }    public int getPageNo() {        if(pageNo<=0){            pageNo=1;        }        return pageNo;    }    public void setPageNo(int pageNo) {        this.pageNo = pageNo;    }    @Override    public String toString() {       return  super.toString();    }}