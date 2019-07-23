package com.coco.tango.surfing.core.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coco.tango.surfing.common.redis.biz.CommonRedisCache;
import com.coco.tango.surfing.core.constants.Constant;
import com.coco.tango.surfing.core.constants.RedisConstant;
import com.coco.tango.surfing.core.dal.domain.user.TangoUser;
import com.coco.tango.surfing.core.dal.mapper.user.TangoUserMapper;
import com.coco.tango.surfing.core.enums.YesOrNoEnum;
import com.coco.tango.surfing.core.service.user.TangoUserIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Tango 用户 服务接口 实现
 *
 * @author ckli01
 * @date 2019-06-27
 */
@Service
public class TangoUserIServiceImpl extends ServiceImpl<TangoUserMapper, TangoUser> implements TangoUserIService {


    @Autowired
    private CommonRedisCache commonRedisCache;


    /**
     * 生成自增id 先判断redis 中是否存在自增key ，若没有建立自增key
     */
    @PostConstruct
    public void init() {
        if (!commonRedisCache.isExist(RedisConstant.REDIS_KEY_TANGO_USER_INCR)) {
            Long num = getLastCodeNum();
            if (num != null) {
                commonRedisCache.setNx(RedisConstant.REDIS_KEY_TANGO_USER_INCR, num);
            } else {
                commonRedisCache.setNx(RedisConstant.REDIS_KEY_TANGO_USER_INCR, 0L);
            }
        }
    }


    @Override
    public boolean save(TangoUser entity) {
        Long num = commonRedisCache.increment(RedisConstant.REDIS_KEY_TANGO_USER_INCR, 1L);
        entity.setCode(custCodeGenerator(num));
        return super.save(entity);
    }


    @Override
    public int count(Wrapper<TangoUser> queryWrapper) {
        return super.count(queryWrapper);
    }


    @Override
    public List<TangoUser> listByPages(Integer currentPage, int size) {
        Integer startRow = currentPage * size;
        return super.baseMapper.listByPages(startRow, size);
    }

    /**
     * 获取数据库中最大的code 所对应的num
     *
     * @return
     */
    private Long getLastCodeNum() {
        String code = super.baseMapper.getLastCode(Constant.TANGO_USER_CODE_GENERATOR_PREFIX);
        return custCodeParsing(code);
    }

    /**
     * 解析器
     * 统一编码规则，字母“TANGO”+序列号（10位，不足10位前面加0补足）
     *
     * @param code
     * @return
     */
    private Long custCodeParsing(String code) {
        if (!StringUtils.isEmpty(code)) {
            code = code.substring(Constant.TANGO_USER_CODE_GENERATOR_PREFIX.length());
            return Long.valueOf(code);
        } else {
            return null;
        }
    }


    /**
     * 生成器
     * 统一编码规则，字母“TANGO”+序列号（10位，不足10位前面加0补足)
     *
     * @param num
     * @return
     */
    private String custCodeGenerator(Long num) {
        String str = String.format("%010d", num);
        return Constant.TANGO_USER_CODE_GENERATOR_PREFIX + str;
    }


}

    
    
  