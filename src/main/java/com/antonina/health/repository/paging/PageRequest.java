package com.antonina.health.repository.paging;

public class PageRequest {

    private final int page;
    private final int size;
    private final Sort sort;

    private PageRequest(int page, int size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public Sort getSort() {
        return sort;
    }

    public int getOffset() {
        return size * page;
    }

    public static PageRequest of(int page, int size, Sort sort) {
        return new PageRequest(page, size, sort);
    }
}