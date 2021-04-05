package com.github.xinlc.eshlc.core.domain;

/**
 * 分页请求
 *
 * @author Richard
 * @since 2021-03-27
 */
public class EsPageRequest implements IEsPageable {

    public static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 首页页码，默认0
     */
    private int firstPageNo = 0;

    /**
     * 页码，从0开始
     */
    private int page;

    /**
     * 分页大小
     */
    private int size;

    /**
     * 页面偏移量，即忽略多少条数据后，开始分页
     */
    private int offset;

    public EsPageRequest() {
        this(0, DEFAULT_PAGE_SIZE, 0);
    }

    public EsPageRequest(int page, int size) {
        this(page, size, 0);
    }

    public EsPageRequest(int page, int size, int offset) {
        this.page = page;
        this.size = size;
        this.offset = offset;
    }

    public EsPageRequest(int page, int size, int offset, int firstPageNo) {
        this.page = page;
        this.size = size;
        this.offset = offset;
        this.firstPageNo = firstPageNo;
    }

    @Override
    public int getFirstPageNo() {
        return firstPageNo;
    }

    @Override
    public void setFirstPageNo(int customFirstPageNo) {
        firstPageNo = customFirstPageNo;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public IEsPageable next() {
        return IEsPageable.of(page + 1, size, offset, firstPageNo);
    }

    @Override
    public IEsPageable previous() {
        return page == firstPageNo ? IEsPageable.of(firstPageNo, size, offset, firstPageNo) : IEsPageable.of(page - 1, size, offset, firstPageNo);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "EsPageRequest{" +
            "firstPageNo=" + firstPageNo +
            ", page=" + page +
            ", size=" + size +
            ", offset=" + offset +
            '}';
    }
}
