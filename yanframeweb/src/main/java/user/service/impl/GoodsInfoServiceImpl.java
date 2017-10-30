package user.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import constant.Constant;
import constant.ErrorCode;
import exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.dto.SysGoodsInfoDTO;
import user.dto.SysUserDTO;
import user.mapper.GoodsInfoMapper;
import user.service.IGoodsInfoService;
import utils.CommonTools;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Title:AccountService </p>
 * <p>Description:商品管理</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/9
 * Time: 9:54
 */
@Service
public class GoodsInfoServiceImpl implements IGoodsInfoService {


    @Autowired
    private GoodsInfoMapper goodsInfoMapper;


    @Override
    public int saveGoodsInfo(SysUserDTO sysUserDTO, SysGoodsInfoDTO sysGoodsInfoDTO) {

        if (null == sysUserDTO) {
            throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
        }

        if (StringUtils.isBlank(sysGoodsInfoDTO.getGoods_id())) {
            throw new CustomException(ErrorCode.create_GoodsInfo.GOODS_ID_FAIL);
        }

        if (StringUtils.isBlank(sysGoodsInfoDTO.getGoods_name())) {
            throw new CustomException(ErrorCode.create_GoodsInfo.GOODS_NAME_FAIL);
        }

        if (sysGoodsInfoDTO.getGoods_instock_price() <= new Double(0)) {
            throw new CustomException(ErrorCode.create_GoodsInfo.GOODS_INSTOCK_PRICE_FAIL);
        }
        sysGoodsInfoDTO.setCreate_by_user_id(sysUserDTO.getUser_id());
        return saveGoodsInfo(sysGoodsInfoDTO);
    }

    //TODO
    @Override
    public Page<SysGoodsInfoDTO> getGoodsInfoList(SysUserDTO sysUser, SysGoodsInfoDTO sysGoodsInfoDTO) {

        if (null == sysUser || null == sysUser.getUser_id()) {
            //未登录异常
            throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
        }

        Page<SysGoodsInfoDTO> list = PageHelper.startPage(sysGoodsInfoDTO.getPage(), sysGoodsInfoDTO.getLimit())
                .doSelectPage(() -> goodsInfoMapper.queryByCondition(sysGoodsInfoDTO));


        list.stream().map(string -> {
            if (StringUtils.isBlank(string.getGoods_img_url())) {
                string.setImg_url_show(new String[]{"#"});
            } else {

                String img_url_show[] =null;
                List tmpList = Stream.of(string.getGoods_img_url().split(",")).filter(good_img_url_tmp -> StringUtils.isNotBlank(good_img_url_tmp)).map(
                        good_img_url_tmp -> {
                            return Constant.yanFrameParent_url + Constant.PIC_URL_GOODS_UPLOAD + good_img_url_tmp;
                        }
                ).collect(Collectors.toList());

                img_url_show = new String[tmpList.size()];
                tmpList.toArray(img_url_show);
//                Stream.of(img_url_show).forEach(System.out::println);
                string.setImg_url_show(img_url_show);
            }
            return string;
        }).collect(Collectors.toList());

        list.forEach(System.out::println);

        return list;
    }

    @Override
    public int delGoods(SysGoodsInfoDTO sysGoodsInfoDTO) {
        if(null==sysGoodsInfoDTO.getId()){
            throw new CustomException(ErrorCode.sys_error.PARAM_FAIL);
        }
        return goodsInfoMapper.delSysGoodsInfoById(sysGoodsInfoDTO);
    }

    /**
     * 更新产品 信息
     *
     * @param sysUserDTO
     * @param sysGoodsInfoDTO
     * @return
     */

    @Override
    public int updateGoodsInfo(SysUserDTO sysUserDTO, SysGoodsInfoDTO sysGoodsInfoDTO) {

        if (null == sysUserDTO) {
            throw new CustomException(ErrorCode.sys_user.NO_LOGIN_ERROR);
        }

        if (StringUtils.isBlank(sysGoodsInfoDTO.getGoods_id())) {
            throw new CustomException(ErrorCode.create_GoodsInfo.GOODS_ID_FAIL);
        }

        if (StringUtils.isBlank(sysGoodsInfoDTO.getGoods_name())) {
            throw new CustomException(ErrorCode.create_GoodsInfo.GOODS_NAME_FAIL);
        }

        if (sysGoodsInfoDTO.getGoods_instock_price() <= new Double(0)) {
            throw new CustomException(ErrorCode.create_GoodsInfo.GOODS_INSTOCK_PRICE_FAIL);
        }

        sysGoodsInfoDTO.setUpdate_by_user_id(sysUserDTO.getUser_id());

        return goodsInfoMapper.updateGoodsInfoById(sysGoodsInfoDTO);
    }

    /**
     * 保存商品
     *
     * @param sysGoodsInfoDTO
     * @return
     */
    private int saveGoodsInfo(SysGoodsInfoDTO sysGoodsInfoDTO) {

        sysGoodsInfoDTO.setGoods_type(0);


        int effect = goodsInfoMapper.saveGoodsInfo(sysGoodsInfoDTO);

        if (effect == 0) {
            throw new CustomException(ErrorCode.create_GoodsInfo.CREATE_GOODSINFO_FAIL);
        }

        copyPicToRealPath(sysGoodsInfoDTO);
        return effect;
    }

    /**
     * 把图片从tmp的临时路径复制出来，并清空当前tmp的图片
     *
     * @param sysGoodsInfoDTO
     */
    private void copyPicToRealPath(SysGoodsInfoDTO sysGoodsInfoDTO) {

        if (StringUtils.isBlank(sysGoodsInfoDTO.getGoods_img_url())) {
            return;
        }

        String[] tmpPaths = sysGoodsInfoDTO.getGoods_img_url().split(",");

        String tmpPre = sysGoodsInfoDTO.getRequestPath() + "uploadimages/" + sysGoodsInfoDTO.getCreate_by_user_id() + "/";
        String realPre = sysGoodsInfoDTO.getRequestPath() + Constant.PIC_URL_GOODS_UPLOAD;

        Stream.of(tmpPaths).filter((a) -> StringUtils.isNotBlank(a)).forEach((imgName) -> {
            CommonTools.copyFile(tmpPre + imgName, realPre, imgName);
        });

        //清空临时文件
        CommonTools.deleteDirectory(tmpPre);


    }


}
