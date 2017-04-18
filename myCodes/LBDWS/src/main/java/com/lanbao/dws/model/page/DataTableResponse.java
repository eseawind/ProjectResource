package com.lanbao.dws.model.page;

import java.util.List;

import com.ibm.framework.dal.pagination.PaginationResult;

/**
 * datatable Ajax返回公共类 <br>
 * 〈功能详细描述〉
 *
 * @param <T> list数据类型
 * @author zhangerwei
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DataTableResponse<T> {
    /**
     * 返回数据
     */
    private List<T> aaData;
    /**
     * 请求标识
     */
    private int sEcho;
    /**
     * 数据库中总共有多少条
     */
    private int iTotalRecords;
    /**
     * 数据库中查询过滤后有多少条记录,和iTotalRecords设置为相同
     */
    private int iTotalDisplayRecords;

    public DataTableResponse() {
    }

    public DataTableResponse(int sEcho) {
        this.sEcho = sEcho;
    }

    public DataTableResponse(int sEcho, PaginationResult<List<T>> prs) {
        this.sEcho = sEcho;
        this.aaData = prs.getR();// 设置列表数据
        this.iTotalDisplayRecords = prs.getPagination().getTotalRows();// 设置总记录条数
        this.iTotalRecords = prs.getPagination().getTotalRows();// 设置总记录条数
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

    public int getsEcho() {
        return sEcho;
    }

    public void setsEcho(int sEcho) {
        this.sEcho = sEcho;
    }

    public int getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(int iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public int getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

}
