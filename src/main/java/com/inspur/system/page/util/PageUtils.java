package com.inspur.system.page.util;

import com.github.pagehelper.PageInfo;
import com.inspur.system.page.PO.PageRequest;
import com.inspur.system.page.PO.PageResult;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhuanghuan
 * Date: 2020/2/12   14:12
 * Description:
 */
public class PageUtils {
    /**
     * 将分页信息封装到统一的接口
     *
     * @param pageRequest
     * @param pageInfo
     * @return
     */
    public static PageResult getPageResult(PageRequest pageRequest, PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }
}