package io.github.openguava.jvtool.spring.web.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 实体基础类
 * @author openguava
 *
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/** 创建者 */
	@TableField(fill = FieldFill.INSERT)
	private String createBy;
	
	public String getCreateBy() {
		return this.createBy;
	}
	
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	/** 更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	
	public Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	/** 更新者 */
	private String updateBy;
	
	public String getUpdateBy() {
		return this.updateBy;
	}
	
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	
	/** 备注 */
	private String remark;
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/** 搜索值 */
	@JsonIgnore
	@TableField(exist = false)
	private String searchValue;
	
	public String getSearchValue() {
		return this.searchValue;
	}
	
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	/** 请求参数 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@TableField(exist = false)
	private Map<String, Object> params;
	
	public Map<String, Object> getParams() {
		if(this.params == null) {
			this.params = new HashMap<>();
		}
		return this.params;
	}
	
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
