package user.service;


import user.dto.TDemo;

import java.util.List;

/**
 * Created by t on 2017/8/16.
 */
public interface TestService {

     List<TDemo> queryList();

    /**
     * 插入
     * @param td
     * @return
     */
    int insertTdemo(TDemo td);
}
