package com.hzx.tools.copy;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Hzx
 *
 */
public class CopyPojoUtil extends BeanUtils {

    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }

    /**
     * 复制集合属性
     * @param sources
     * @param target
     * @param callBack
     * @param <S>
     * @param <T>
     * @return
     * 使用场景：Entity、Bo、Vo层数据的复制，因为BeanUtils.copyProperties只能给目标对象的属性赋值，却不能在List集合下循环赋值，因此添加该方法
     * 如：List<AdminEntity> 赋值到 List<AdminVo> ，List<AdminVo>中的 AdminVo 属性都会被赋予到值
     * S: 数据源类 ，T: 目标类::new(eg: AdminVo::new)
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target, CopyPojoUtilCallBack<S, T> callBack) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            copyProperties(source, t);
            if (callBack != null) {
                // 回调
                callBack.callBack(source, t);
            }
            list.add(t);
        }
        return list;
    }

    /**
     * 复制类属性
     * @param object    被复制类
     * @param target    目标类
     * @param <T>       目标类::new(object,AdminVo::new)
     * @return          返回目标类对象
     */
    public static <T> T myCopyProperties(Object object, Supplier<T> target) {
        T entity = target.get();
        CopyPojoUtil.copyProperties(object,entity);
        return entity;
    }

}