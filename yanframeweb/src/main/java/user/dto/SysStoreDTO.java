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
    private int page;

    private int limit;

}
