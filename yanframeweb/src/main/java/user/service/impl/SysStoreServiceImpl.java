package user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.tools.internal.jxc.ap.Const;
import constant.Constant;
import constant.ErrorCode;
import enums.IsShopKeeper;
import exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pojo.SysStore;
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
        if(sysUser.getRole_id().equals(Constant.ADMIN.role_id)){
            sysStoreDTO.setStore_id(null);
        }else{
            sysStoreDTO.setStore_id(sysUser.getStore_id());
        }

        Page<SysStoreDTO> sysStoreDTOPage = PageHelper.startPage(sysStoreDTO.getPage(), sysStoreDTO.getLimit())
                .doSelectPage(() -> sysStoreMapper.getSysStoreList(sysStoreDTO));
        return sysStoreDTOPage;
    }

    @Override
    public List<SysUserDTO> getSysUserList(String store_user_id) {
        return sysStoreMapper.queryAllSysUser(store_user_id);
    }

    /**
     * 添加店铺
     * @param sysStoreDTO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addSysStore(SysStoreDTO sysStoreDTO) {
        log.info("-----------------添加店铺-------------------");

        int effect = sysStoreMapper.insertSysStore(sysStoreDTO);

        if(effect==0){
            throw new CustomException(Constant.sys_store.ADD_STORE_FAIL);
        }

        //设置为店长
        sysStoreDTO.setIs_shop_keeper(IsShopKeeper.SHOP_KEEPER.getValue());
        updateUserForSysStore(sysStoreDTO);

        return 1;
    }

    /**
     * 更新用户为店长/或者普通人
     * @param sysStoreDTO
     */
    private void updateUserForSysStore(SysStoreDTO sysStoreDTO) {
        int effect=0;
        SysUserDTO sysUserDTO = new SysUserDTO();
        sysUserDTO.setStore_id(sysStoreDTO.getStore_id());
        sysUserDTO.setUser_id(sysStoreDTO.getStore_user_id());
        sysUserDTO.setIs_shop_keeper(sysStoreDTO.getIs_shop_keeper());
        effect = sysStoreMapper.updateUserForSysStore(sysUserDTO);

        if(effect==0){
            throw new CustomException(Constant.sys_store.ADD_STORE_FAIL);
        }
    }

    /**
     * 更新店铺
     * @param sysStoreDTO
     * @return
     */
    @Override
    public int updateSysStore(SysStoreDTO sysStoreDTO) {

        log.info("-----------------更新店铺-------------------");

        SysStoreDTO sysStoreDTOTMP = sysStoreMapper.selectSysStoreByStoreId(sysStoreDTO);

        if(null == sysStoreDTOTMP){
            throw new CustomException(Constant.sys_store.UPDATE_STORE_FAIL);
        }

        //把原来的店长设置为普通员工
        sysStoreDTOTMP.setIs_shop_keeper(IsShopKeeper.STAFF.getValue());
        updateUserForSysStore(sysStoreDTOTMP);

        int effect =  sysStoreMapper.updateSysStore(sysStoreDTO);

        if(effect==0){
            throw new CustomException(Constant.sys_store.UPDATE_STORE_FAIL);
        }

        //设置为店长
        sysStoreDTO.setIs_shop_keeper(IsShopKeeper.SHOP_KEEPER.getValue());
        updateUserForSysStore(sysStoreDTO);

        return 1;
    }

    /**
     * 取店铺列表作为下拉数据
     * @return
     */
    @Override
    public List<SysStoreDTO> getgetStoreListForSelect() {
        return sysStoreMapper.getSysStoreList(new SysStoreDTO());
    }

    /**
     * 取店铺信息
     * @param store_id
     * @return
     */
    @Override
    public SysStoreDTO queryByStoreId(Integer store_id) {
        SysStoreDTO sysStoreDTO = new SysStoreDTO();
        sysStoreDTO.setStore_id(store_id);

        return sysStoreMapper.selectSysStoreByStoreId(sysStoreDTO);
    }


}
