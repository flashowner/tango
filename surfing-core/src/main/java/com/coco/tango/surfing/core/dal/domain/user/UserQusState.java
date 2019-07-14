package com.coco.tango.surfing.core.dal.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.coco.tango.surfing.core.dal.domain.BaseDomain;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户答题状态
 *
 * @author ckli01
 * @date 2019-07-14
 */
@Data
@TableName("t_user_test_state")
public class UserQusState extends BaseDomain implements Serializable {


    private static final long serialVersionUID = -5394709598142185498L;


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * t_user_tango 主键
     */
    private Long userId;

    /**
     * 测试题组 即 create_type
     */
    private Integer qusGroup;

    /**
     * 状态
     */
    private boolean state;


    /**
     * 数据逻辑状态
     */
    @TableLogic
    private Integer deleted;


}

    
    
  