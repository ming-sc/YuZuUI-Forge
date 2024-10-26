package com.img.function;

/**
 * @author : IMG
 * @create : 2024/10/26
 */
@FunctionalInterface
public interface AnimationFunction<T> {

    /**
     * 动画函数
     * @param t 时间 0-1
     * @param now 当前状态
     * @return 动画后的状态
     */
    T apply(float t, T now);
}
