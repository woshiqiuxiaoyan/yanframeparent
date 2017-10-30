package user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import user.dto.SysStockDTO;
import user.mapper.SysStockMapper;
import user.service.ISysStockService;

/** 
 * @Author qxy
 * @Date: 2017/10/30 10:00
 */
@Service
public class SysStockServiceImpl implements ISysStockService {


    @Autowired
    private SysStockMapper sysStockMapper;



    @Transactional(propagation= Propagation.REQUIRED,rollbackFor=Exception.class)
    public int saveSysStock(SysStockDTO sysStockDTO){


        return 1;
    }


}
