package com.github.xinlc.eshlc.core.domain;

import java.util.Collections;
import java.util.List;

/**
 * 分页实现
 *
 * @author Richard
 * @since 2021-03-27
 */
public class EsPageImpl<T> implements IEsPage<T> {

    private final long total;
    private final List<T> content;
    private final IEsPageable pageable;
    private final int totalPage;

    public EsPageImpl(long total, List<T> content, IEsPageable pageable) {
        this.total = total;
        this.content = Collections.unmodifiableList(content);
        this.pageable = pageable;
        this.totalPage = total % pageable.getSize() == 0 ? (int) (total / pageable.getSize()) : (int) (total / pageable.getSize() + 1);
    }

    @Override
    public int getTotalPage() {
        return totalPage;
    }

    @Override
    public long getTotal() {
        return total;
    }

    @Override
    public IEsPageable getPageable() {
        return pageable;
    }

    @Override
    public List<T> getContent() {
        return content;
    }
}
