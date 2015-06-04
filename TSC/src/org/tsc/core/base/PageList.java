package org.tsc.core.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * 分页工具
 * */
public class PageList {

	private int rowCount;
	private int pages;
	private int currentPage;
	private int pageSize;
	private List<Map<String, Object>> result;
	private IBaseDao dao;

	
	public List<Map<String, Object>> getResult() {
		return this.result;
	}
	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}
	public int getPages() {
		return this.pages;
	}

	public int getRowCount() {
		return this.rowCount;
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public int getPageSize() {
		return this.pageSize;
	}
 
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public IBaseDao getDao() {
		return dao;
	}
	public void setDao(IBaseDao dao) {
		this.dao = dao;
	}
	
	public int getNextPage() {
		int p = this.currentPage + 1;
		if (p > this.pages) {
			p = this.pages;
		}
		return p;
	}

	public int getPreviousPage() {
		int p = this.currentPage - 1;
		if (p < 1) {
			p = 1;
		}
		return p;
	}

	@Override
	public String toString() {
		return "rowCount:" + rowCount + ";pages: " + pages + " currentPage:" + currentPage + ";pageSize:"
				+ pageSize;
	}

	/**
	 * 通过行数，页面大小，页数构造一个 PageTools(注意此不包含 result)
	 * @param rows
	 * @param pageSize
	 * @param pageNo
	 */
	public PageList(int rows,int pageSize,int pageNo){
		this.rowCount = rows;
		this.pages = rowCount%pageSize !=0?rowCount/pageSize+1:rowCount/pageSize;
		if(pageNo > this.pages){
			pageNo = this.pages;
		}
		this.currentPage = pageNo;
		this.pageSize = pageSize;
		this.result = null;
	}
	
	public PageList(IBaseDao dao) {
		this.dao = dao;
	}
	
	@SuppressWarnings("deprecation")
	public PageList getPageList(StringBuilder sql, int pageNo,
			int pageSize) {
		String tatalSql = "select count(1) from( " + sql+ " ) as newtable";
		this.rowCount = dao.getJdbcTemplate().queryForInt(tatalSql);
		this.pages = rowCount%pageSize !=0?rowCount/pageSize+1:rowCount/pageSize;
		if(pageNo > this.pages){
			pageNo = this.pages;
		}
		
		this.currentPage = pageNo;
		this.pageSize = pageSize;
		sql.append(" limit ");
		if (pageNo > 0) {
			int first = (pageNo - 1)*pageSize;
			sql.append( first + ",");
		}
		if (pageSize > 0) {
			sql.append(pageSize);
		}else {
			sql.append(10);
		}
		result = dao.getJdbcTemplate().queryForList(sql.toString());
		return this;
	}
	
	/**
	 * 分页查询
	 * @param tableOrView	表名或视图名
	 * @param properties	字段名
	 * @param objs	字段值
	 * @param pageNo	查询哪一一页
	 * @param pageSize	每一页记录的数量
	 * @param orderColumn	排序的字段
	 * @param orderType	排序的类型（desc 或 desc)
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public PageList getPageList(String tableOrView, String[] properties, Object[] objs,String[] expressions, int pageNo,
			int pageSize, String orderColumn, String orderType) {
		StringBuilder sql = new StringBuilder("select * from  " + tableOrView);
		StringBuilder totalSql = new StringBuilder("select count(*) from  " + tableOrView);
		if (properties != null) {
			sql.append(" where ");
			totalSql.append(" where ");
			int len = properties.length - 1;
			for (int i = 0; i < len; i++) {
				sql.append(properties[i] + expressions[i]+"? and ");
				totalSql.append(properties[i] + expressions[i]+"? and ");
			}
			sql.append(properties[len] + expressions[len]+"?");
			totalSql.append(properties[len] + expressions[len]+"?");
		}
		
		this.rowCount = dao.getJdbcTemplate().queryForInt(totalSql.toString(),objs);
		System.out.println("totalSql:" + totalSql);
		System.out.println("rows " + rowCount);
		this.pages = (rowCount - pageSize)/pageSize + 1; //最少有一页
		if(pageNo > this.pages){
			pageNo = this.pages;
		}
		this.currentPage = pageNo;
		this.pageSize = pageSize;
		
		sql.append(" order by " + orderColumn +" " + orderType + " limit ");
		if (pageNo > 0) {
			int first = (pageNo - 1)*pageSize;
			sql.append( first + ",");
		}
		if (pageSize > 0) {
			sql.append(pageSize);
		}else {
			sql.append(10);
		}
		result = dao.getJdbcTemplate().queryForList(sql.toString(), objs);
		System.out.println(sql.toString());
		return this;
	}
	
}
