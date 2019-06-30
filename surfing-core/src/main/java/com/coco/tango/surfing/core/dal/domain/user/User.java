package com.coco.tango.surfing.core.dal.domain.user;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 *
 * @author ckli01
 * @date 2019-06-06
 */
@Data
public class User implements Serializable {


    private static final long serialVersionUID = -8299199586280375819L;

    /**
     * 编码
     */
    @Id
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
     * 上线时间
     */
    private Date onLineTime;

    /**
     * 下线时间
     */
    private Date offLineTime;


}

    
    
  