package user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.dto.SysStoreDTO;
import user.dto.SysUserDTO;
import user.mapper.SysStoreMapper;
import user.service.ISysStoreService;

import java.util.List;

/**
 * <p>Title:AccountService </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/9
 * Time: 9:54
 */
@Service
public class SysStoreServiceImpl implements ISysStoreService {

    @Autowired
    private SysStoreMapper sysStoreMapper;


    private Logger log = LoggerFactory.getLogger(this.getClass());


    /**
     * 查询店铺信息
     *
     * @param sysUser
     * @param sysStoreDTO
     * @return
     */
    @Override
    public Page<SysStoreDTO> getSysStoreList(SysUserDTO sysUser, SysStoreDTO sysStoreDTO) {

        //管理员查询所有 店铺
        if(sysUser.getRole_id().intValue()==2){
            sysStoreDTO.setStore_id(null);
        }else{
            sysStoreDTO.setStore_id(sysUser.getStore_id());
        }

        Page<SysStoreDTO> sysStoreDTOPage = PageHelper.startPage(sysStoreDTO.getPage(), sysStoreDTO.getLimit())
                .doSelectPage(() -> sysStoreMapper.getSysStoreList(sysStoreDTO));
        return sysStoreDTOPage;
    }

    @Override
    public List<SysUserDTO> getSysUserList() {
        return sysStoreMapper.queryAllSysUser();
    }

    /**
     * 添加店铺
     * @param sysStoreDTO
     * @return
     */
    @Override
    public int addSysStore(SysStoreDTO sysStoreDTO) {

        return 0;
    }
}
