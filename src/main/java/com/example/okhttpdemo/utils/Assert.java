package com.example.okhttpdemo.utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * @author mick
 * 2018/8/23
 */
public class Assert {
    /**
     * map不为空
     *
     * @param map
     * @return
     */
    public static boolean isMapNotEmpty(Map<?, ?> map) {
        return !isMapEmpty(map);
    }

    /**
     * map为空
     *
     * @param map
     * @return
     */
    public static boolean isMapEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty() || map.size() == 0;
    }

    /**
     * 是否-任何一个参数 不为空-(可传入多个参数,但不支持验证对象类型)
     *
     * @param objs
     * @return
     */
    public static boolean isAnyoneNotEmpty(Object... objs) {
        return !isAllNull(objs);
    }

    /**
     * 是否-任何一个参数 为空-(可传入多个参数,但不支持验证对象类型)
     *
     * @param objs
     * @return
     */
    public static boolean isAnyoneEmpty(Object... objs) {
        for (Object o : objs) {
            if (null == o || "".equals(o.toString().trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否-所有参数都 不为空 (可传入多个参数,但不支持验证对象类型)
     *
     * @param objs
     * @return
     */
    public static boolean isAllNotNull(Object... objs) {
        return !isAnyoneEmpty(objs);
    }

    /**
     * 是否-所有参数都 为空(可传入多个参数,但不支持验证对象类型)
     *
     * @param objs
     * @return
     */
    public static boolean isAllNull(Object... objs) {
        for (Object o : objs) {
            if (null != o && !"".equals(o.toString().trim())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Collection 集合为空
     *
     * @param colls
     * @return
     */
    public static boolean isAllEmepty(Collection<?> colls) {
        return null == colls || colls.isEmpty() || colls.size() == 0;
    }

    /**
     * Collection 集合不为空
     *
     * @param colls
     * @return
     */
    public static boolean isAllNotEmepty(Collection<?> colls) {
        return !isAllEmepty(colls);
    }

    /**
     * 是否 所有属性都 不为空(可验证对象类型,但不可传入多个参数)
     *
     * @param bean
     * @return
     */
    public static boolean isAllPropertyNotEmtpy(Object bean) {
        return !isAnyonePropertyEmtpy(bean);
    }

    /**
     * 是否 所有属性都 为空(可验证对象类型,但不可传入多个参数)
     *
     * @param bean
     * @return
     */
    public static boolean isAllPropertyEmtpy(Object bean) {
        if (bean == null) {
            return true;
        }
        Class<? extends Object> clz = bean.getClass();
        try {
            Field[] fileds = clz.getDeclaredFields();
            for (Field f : fileds) {
                f.setAccessible(true);
                Object value = f.get(bean);
                if (value != null && !"".equals(value.toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 是否 任何一个属性 不为空(可验证对象类型,但不可传入多个参数)
     *
     * @param bean
     * @return
     */
    public static boolean isAnyonePropertyNotEmtpy(Object bean) {
        return !isAllPropertyEmtpy(bean);
    }

    /**
     * 是否 任何一个属性 为空(可验证对象类型,但不可传入多个参数)
     *
     * @param bean
     * @return
     */
    public static boolean isAnyonePropertyEmtpy(Object bean) {
        if (bean == null) {
            return true;
        }
        Class<? extends Object> clz = bean.getClass();
        try {
            Field[] fileds = clz.getDeclaredFields();
            for (Field f : fileds) {
                f.setAccessible(true);
                Object value = f.get(bean);
                if (value == null || "".equals(value.toString())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
