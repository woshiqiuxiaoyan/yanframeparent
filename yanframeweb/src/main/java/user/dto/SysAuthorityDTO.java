package user.dto;import lombok.Data;import pojo.SysAuthority;@Datapublic class SysAuthorityDTO extends SysAuthority {    //按钮权限    private String permissions;    private Integer role_id;    private String role_key;    private boolean checked;}