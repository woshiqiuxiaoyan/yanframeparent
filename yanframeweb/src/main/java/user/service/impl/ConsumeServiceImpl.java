package user.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.mapper.SysStoreMapper;
import user.service.IConsumeService;

/**
 * <p>Title:AccountService </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/9
 * Time: 9:54
 */
@Service
public class ConsumeServiceImpl implements IConsumeService {

    @Autowired
    private SysStoreMapper sysStoreMapper;


    private Logger log = LoggerFactory.getLogger(this.getClass());




}
