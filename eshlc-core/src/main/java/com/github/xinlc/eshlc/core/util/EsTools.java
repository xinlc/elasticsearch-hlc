package com.github.xinlc.eshlc.core.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * ES 工具类
 *
 * @author Richard
 * @since 2021-03-31
 */
public final class EsTools {
    private EsTools() {
    }

    /**
     * 批量操作，分割集合
     *
     * @param oriList    要分割的集合
     * @param size       每批处理大小
     * @param isParallel 是否启用并行流
     * @param <T>        数据类型
     * @return 分割后的集合
     */
    public static <T> List<List<T>> splitList(List<T> oriList, int size, boolean isParallel) {
        // 未达到批量上限，不做分割处理
        if (oriList.size() <= size) {
            List<List<T>> splitList = new ArrayList<>();
            splitList.add(oriList);
            return splitList;
        }

        // 计算分割数量
        int limit = (oriList.size() + size - 1) / size;

        // 并行流处理分割
        if (isParallel) {
            return Stream.iterate(0, n -> n + 1)
                .limit(limit)
                .parallel()
                .map(a -> oriList.stream()
                    .skip(a * size)
                    .limit(size)
                    .parallel()
                    .collect(Collectors.toList()))
                .collect(Collectors.toList());
        } else {
            final List<List<T>> splitList = new ArrayList<>();
            Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
                splitList.add(oriList.stream().skip(i * size).limit(size).collect(Collectors.toList()));
            });
            return splitList;
        }
    }

    /**
     * forEach 增强
     *
     * @param elements 遍历元素
     * @param action   动作
     * @param <E>      元素类型
     */
    public static <E> void forEach(Iterable<? extends E> elements, BiConsumer<Integer, ? super E> action) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);

        int index = 0;
        for (E element : elements) {
            action.accept(index++, element);
        }
    }
}
