package com.yzy.emsp.ui.results;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel("Pagination Result")
public class PageResult<T> {
    @ApiModelProperty(value = "Current Page", example = "1")
    private int currentPage;

    @ApiModelProperty(value = "Page Size", example = "10")
    private int pageSize;

    @ApiModelProperty(value = "Total Items", example = "100")
    private long totalItems;

    @ApiModelProperty(value = "Total Pages")
    private int totalPages;

    @ApiModelProperty(value = "Data List")
    private List<T> items;

    public PageResult(List<T> items, long totalItems, int page, int size) {
        this.items = items;
        this.totalItems = totalItems;
        this.currentPage = page;
        this.pageSize = size;
        this.totalPages = (int) Math.ceil((double) totalItems / size);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}