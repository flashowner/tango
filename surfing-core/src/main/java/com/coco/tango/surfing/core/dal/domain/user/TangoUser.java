package com.coco.tango.surfing.core.dal.domain.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.coco.tango.surfing.core.dal.domain.BaseDomain;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Tango用户信息
 *
 * @author ckli01
 * @date 2019-06-06
 */
@Data
@TableName("t_user_tango")
public class TangoUser extends BaseDomain implements Serializable {


    private static final long serialVersionUID = -8299199586280375819L;


    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
     * 编码
     */
    private String code;


    /**
     * 昵称
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 生日
     */
    private Date birthDay;

    /**
     * 数据逻辑状态
     */
    @TableLogic
    private Integer deleted;


    /**
     * 学校
     */
    private String school;

    /**
     * 专业
     */
    private String professional;

    /**
     * 头像地址
     */
    private String avatarUrl;


//    /**
//     * 上线时间
//     */
//    private Date onLineTime;
//
//    /**
//     * 下线时间
//     */
//    private Date offLineTime;


}

    
    
  