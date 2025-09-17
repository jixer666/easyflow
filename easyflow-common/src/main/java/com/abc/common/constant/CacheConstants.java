package com.abc.common.constant;

/**
 * 缓存的key 常量
 * 
 * @author ruoyi
 */
public class CacheConstants
{
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 验证码 redis key
     */
    public static final String VERIFICATION_CODE_KEY = "verification_code:";
    public static final Integer VERIFICATION_CODE_KEY_EXPIRE = 5;

    /**
     * 学校缓存
     */
    public static final String SCHOOL_INFO = "school:";
    public static final Long SCHOOL_INFO_EXPIRE = 60 * 5L;
    public static final String SCHOOL_PATTERN = "school:*";


    /**
     * 专业缓存
     */
    public static final String MAJOR_INFO = "major:";
    public static final Long MAJOR_INFO_EXPIRE = 60 * 5L;
    public static final String MAJOR_PATTERN = "major:*";


    /**
     * 帖子缓存
     */
    public static final String POST_INFO = "post:";
    public static final Long POST_INFO_EXPIRE = 60 * 3L;

    /**
     * 用户缓存
     */
    public static final String USER_INFO = "user:";
    public static final Long USER_INFO_EXPIRE = 60L * 30L;
}
