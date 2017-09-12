package user.mapper;


import org.springframework.stereotype.Repository;
import user.dto.TDemo;

import java.util.List;

/**
 * Created by t on 2017/8/16.
 */
@Repository
public interface TDemoMapper {

    List<TDemo> getUser();


    int insertTdemo(TDemo td);
}
