package com.coco.tango.surfing.core.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;

import java.util.List;

/**
 * Tango 用户 服务接口
 *
 * @author ckli01
 * @date 2019-06-27
 */
public interface TangoUserIService extends IService<TangoUser> {


    /**
     * 根据页数查询 一定量数据
     * @param currentPage
     * @param size
     * @return
     */
    List<Long> listByPages(Long currentPage, int size);

}
