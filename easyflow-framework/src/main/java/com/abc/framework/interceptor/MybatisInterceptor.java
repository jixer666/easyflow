package com.abc.framework.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.abc.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];

        String methodId = ms.getId();

        try {
            if (methodId.matches(".*insert.*") && !methodId.matches(".*insertSelective.*")) {
                handleInsertParameter(parameter);
            } else if (methodId.matches(".*update.*") && !methodId.matches(".*updateSelective.*")) {
                handleUpdateParameter(parameter);
            }
        } catch (Exception e) {
            // 日志记录错误，但不中断流程
            log.error("MybatisInterceptor处理参数失败: " + e.getMessage());
        }

        return invocation.proceed();
    }

    private void handleInsertParameter(Object parameter) {
        if (parameter == null) return;

        Date now = new Date();

        if (parameter instanceof Map) {
            // 处理Map参数
            Map<String, Object> paramMap = (Map<String, Object>) parameter;
            // 检查是否是MyBatis Plus的参数结构
            Object entity = paramMap.get("et");
            if (entity != null) {
                setEntityFieldValue(entity, "createTime", now);
                setEntityFieldValue(entity, "updateTime", now);
                setEntityFieldValue(entity, "status", Constants.YES);
                setEntityFieldValue(entity, "ver", Constants.DEFAULT_VER);
            } else {
                // 普通Map参数
                paramMap.putIfAbsent("createTime", now);
                paramMap.putIfAbsent("updateTime", now);
                paramMap.putIfAbsent("status", Constants.YES);
                paramMap.putIfAbsent("ver", Constants.DEFAULT_VER);
            }
        } else {
            // 处理实体对象参数
            setEntityFieldValue(parameter, "createTime", now);
            setEntityFieldValue(parameter, "updateTime", now);
            setEntityFieldValue(parameter, "status", Constants.YES);
            setEntityFieldValue(parameter, "ver", Constants.DEFAULT_VER);
        }
    }

    private void handleUpdateParameter(Object parameter) {
        if (parameter == null) return;

        Date now = new Date();

        if (parameter instanceof Map) {
            Map<String, Object> paramMap = (Map<String, Object>) parameter;
            Object entity = paramMap.get("et");

            if (entity != null) {
                // MyBatis Plus的update操作
                setEntityFieldValue(entity, "updateTime", now);
                incrementEntityFieldValue(entity, "ver");
            } else {
                // 普通Map参数，尝试更新字段
                paramMap.put("updateTime", now);
                if (paramMap.containsKey("ver")) {
                    paramMap.put("ver", (Integer) paramMap.get("ver") + 1);
                }
            }
        } else {
            // 实体对象参数
            setEntityFieldValue(parameter, "updateTime", now);
            incrementEntityFieldValue(parameter, "ver");
        }
    }

    private void setEntityFieldValue(Object obj, String fieldName, Object value) {
        if (obj == null) return;

        try {
            Field field = getField(obj.getClass(), fieldName);
            if (field != null) {
                field.setAccessible(true);
                // 只设置值为null的字段或特定条件的字段
                Object currentValue = field.get(obj);
                if (currentValue == null || "ver".equals(fieldName)) {
                    field.set(obj, value);
                }
            }
        } catch (IllegalAccessException e) {
            // 忽略无法访问的字段
        }
    }

    private void incrementEntityFieldValue(Object obj, String fieldName) {
        if (obj == null) return;

        try {
            Field field = getField(obj.getClass(), fieldName);
            if (field != null && field.getType() == Integer.class || field.getType() == int.class) {
                field.setAccessible(true);
                Object currentValue = field.get(obj);
                if (currentValue != null) {
                    int newValue = (Integer) currentValue + 1;
                    field.set(obj, newValue);
                }
            }
        } catch (IllegalAccessException e) {
            // 忽略无法访问的字段
        }
    }

    private Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // 尝试从父类查找
            if (clazz.getSuperclass() != null) {
                return getField(clazz.getSuperclass(), fieldName);
            }
            return null;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 设置额外属性
    }
}