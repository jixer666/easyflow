package com.abc.common.core.domain;

import java.io.Serializable;
import com.abc.common.constant.HttpStatus;

/**
 * 响应信息主体
 *
 * @author LiJunXi
 */
public class ApiResult<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = HttpStatus.SUCCESS;

    /** 失败 */
    public static final int FAIL = HttpStatus.ERROR;

    private int code;

    private String msg;

    private T data;

    public static <T> ApiResult<T> ok()
    {
        return restResult(null, SUCCESS, "操作成功");
    }

    public static <T> ApiResult<T> ok(T data)
    {
        return restResult(data, SUCCESS, "操作成功");
    }

    public static <T> ApiResult<T> ok(T data, String msg)
    {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> ApiResult<T> fail()
    {
        return restResult(null, FAIL, "操作失败");
    }

    public static <T> ApiResult<T> fail(String msg)
    {
        return restResult(null, FAIL, msg);
    }

    public static <T> ApiResult<T> fail(T data)
    {
        return restResult(data, FAIL, "操作失败");
    }

    public static <T> ApiResult<T> fail(T data, String msg)
    {
        return restResult(data, FAIL, msg);
    }

    public static <T> ApiResult<T> fail(int code, String msg)
    {
        return restResult(null, code, msg);
    }

    private static <T> ApiResult<T> restResult(T data, int code, String msg)
    {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public static <T> Boolean isError(ApiResult<T> ret)
    {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(ApiResult<T> ret)
    {
        return ApiResult.SUCCESS == ret.getCode();
    }
}
