package io.github.openguava.jvtool.spring.web.form;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页列表响应基础类
 * 
 * @author openguava
 */
public class ListRespBody<T> extends RespBody {
	
	private static final long serialVersionUID = 1L;

	/** 列表数据 */
	private List<T> rows;
	
	public List<T> getRows() {
		if(this.rows == null) {
			this.rows = new ArrayList<>();
		}
		return this.rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	/** 总记录数 */
	private long total;
	
	public long getTotal() {
		return this.total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * 表格数据对象
	 */
	public ListRespBody() {
	}

	/**
	 * 分页
	 * 
	 * @param list  列表数据
	 * @param total 总记录数
	 */
	public ListRespBody(List<T> list, int total) {
		this.rows = list;
		this.total = total;
	}
}