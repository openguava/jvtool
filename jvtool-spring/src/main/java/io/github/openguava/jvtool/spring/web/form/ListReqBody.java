package io.github.openguava.jvtool.spring.web.form;

/**
 * 表单列表请求基础类
 * @author openguava
 *
 */
public class ListReqBody extends ReqBody {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前记录起始索引
	 */
	private Integer pageNum;

	public Integer getPageNum() {
		return this.pageNum;
	}
	
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	/**
	 * 每页显示记录数
	 */
	private Integer pageSize;
	
	public Integer getPageSize() {
		return this.pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 是否存在分页请求
	 * @return
	 */
	public boolean hasPage() {
		return this.pageNum != null && this.pageSize != null;
	}
}