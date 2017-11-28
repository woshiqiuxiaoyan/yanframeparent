package user.dto;

import lombok.Data;
import pojo.SysStore;

/**
 * <p>Title:SysStoreDTO </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/11/6
 * Time: 15:13
 */
@Data
public class SysStoreDTO extends SysStore {

    private Integer is_shop_keeper;// 1 管理员2店长 3普通员工

    private String store_user_name;//店长名称

    private int page;

    private int limit;

}
