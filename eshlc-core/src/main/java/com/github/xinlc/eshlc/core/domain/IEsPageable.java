package com.github.xinlc.eshlc.core.domain;

/**
 * 分页对象
 * <p>
 * 页面从0开始算第一页
 *
 * @author Richard
 * @since 2021-03-27
 */
public interface IEsPageable {

    /**
     * 获取首页页码
     *
     * @return 首页页码
     */
    int getFirstPageNo();

    /**
     * 设置首页页码，可以为0或者1
     *
     * <pre>
     *     当设置为0时，页码0表示第一页，开始位置为0
     *     当设置为1时，页码1表示第一页，开始位置为0
     * </pre>
     *
     * @param customFirstPageNo 自定义的首页页码，为0或者1
     */
    void setFirstPageNo(int customFirstPageNo);

    /**
     * 获取页码
     *
     * @return 页面
     */
    int getPage();

    /**
     * 获取分页大小
     *
     * @return 分页大小
     */
    int getSize();

    /**
     * 偏移量大小，默认0
     *
     * @return 偏移量大大小
     */
    int getOffset();

    /**
     * 向后翻页
     *
     * @return 下一页
     */
    IEsPageable next();

    /**
     * 向前翻页
     *
     * @return 前一页
     */
    IEsPageable previous();

    /**
     * 获取文档偏移量
     *
     * @return 偏移量
     */
    default int getFrom() {
        return (getPage() - getFirstPageNo()) * getSize() + getOffset();
    }

    /**
     * 设置首页页码为1
     *
     * <pre>
     *     当设置为1时，页码1表示第一页，开始位置为0
     * </pre>
     */
    default void setOneAsFirstPageNo() {
        setFirstPageNo(1);
    }

    /**
     * 创建分页信息
     *
     * @param page 页码
     * @param size 页面大小
     * @return 分页信息
     */
    static IEsPageable of(int page, int size) {
        return withOffset(page, size, 0);
    }

    /**
     * 创建分页信息
     *
     * @param page        页码
     * @param size        页面大小
     * @param offset      偏移量
     * @param firstPageNo 首页页码
     * @return 分页信息
     */
    static IEsPageable of(int page, int size, int offset, int firstPageNo) {
        return new EsPageRequest(page, size, offset, firstPageNo);
    }

    /**
     * 创建第一页
     *
     * @param size 页码大小
     * @return 分页信息
     */
    static IEsPageable first(int size) {
        return of(0, size);
    }

    /**
     * 创建分页信息
     *
     * @param page   页码
     * @param size   页面大小
     * @param offset 偏移量
     * @return 分页信息
     */
    static IEsPageable withOffset(int page, int size, int offset) {
        return new EsPageRequest(page, size, offset);
    }

    /**
     * 创建分页信息
     *
     * @param size   页面大小
     * @param offset 偏移量
     * @return 分页信息
     */
    static IEsPageable firstWithOffset(int size, int offset) {
        return withOffset(0, size, offset);
    }
}
