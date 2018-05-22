package com.antonina.health.repository.paging;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Page<T> implements Iterable<T> {
    private final List<T> content;
    private final long totalElements;
    private final PageRequest pageRequest;

    public Page(List<T> content, long totalElements, PageRequest pageRequest) {
        this.content = content;
        this.totalElements = totalElements;
        this.pageRequest = pageRequest;
    }

    public List<T> getContent() {
        return content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        int size = pageRequest.getSize();
        return size == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) size);
    }

    public int getNumber() {
        return pageRequest.getPage();
    }

    public boolean hasNext() {
        return totalElements > (pageRequest.getOffset() + pageRequest.getSize());
    }

    public boolean hasPrevious() {
        return pageRequest.getPage() > 0;
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        content.forEach(action);
    }

    @Override
    public Spliterator<T> spliterator() {
        return content.spliterator();
    }
}
