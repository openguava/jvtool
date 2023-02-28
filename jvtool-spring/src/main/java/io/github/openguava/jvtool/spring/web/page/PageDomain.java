package io.github.openguava.jvtool.spring.web.page;

import io.github.openguava.jvtool.lang.util.StringUtils;

/**
 * 分页数据
 * 
 * @author ruoyi
 */
public class PageDomain {
	
	/** 当前记录起始索引 */
	private Integer pageNum;
	
	public Integer getPageNum() {
		return this.pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/** 每页显示记录数 */
	private Integer pageSize;
	
	public Integer getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/** 排序列 */
	private String orderByColumn;
	
	public String getOrderByColumn() {
		return this.orderByColumn;
	}

	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	/** 排序的方向desc或者asc */
	private String isAsc = "asc";
	
	public String getIsAsc() {
		return this.isAsc;
	}

	public void setIsAsc(String isAsc) {
		if (StringUtils.isNotEmpty(isAsc)) {
			// 兼容前端排序类型
			if ("ascending".equals(isAsc)) {
				isAsc = "asc";
			} else if ("descending".equals(isAsc)) {
				isAsc = "desc";
			}
			this.isAsc = isAsc;
		}
	}
	
	public String getOrderBy() {
		if (StringUtils.isEmpty(this.orderByColumn)) {
			return "";
		}
		return StringUtils.camelToUnderlineCase(this.orderByColumn) + " " + isAsc;
	}

	/** 分页参数合理化 */
	private Boolean reasonable = true;

	public Boolean getReasonable() {
		if (this.reasonable == null) {
			return Boolean.TRUE;
		}
		return this.reasonable;
	}

	public void setReasonable(Boolean reasonable) {
		this.reasonable = reasonable;
	}
}
