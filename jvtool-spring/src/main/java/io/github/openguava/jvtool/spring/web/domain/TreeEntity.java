package io.github.openguava.jvtool.spring.web.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 树结构基础类
 * @author openguava
 *
 */
public class TreeEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/** 父菜单ID */
	private Long parentId;
	
	public Long getParentId() {
		return this.parentId;
	}
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	/** 父菜单名称 */
	private String parentName;
	
	public String getParentName() {
		return this.parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/** 显示顺序 */
	private Integer orderNum;
	
	public Integer getOrderNum() {
		return this.orderNum;
	}
	
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/** 祖级列表 */
	private String ancestors;
	
	public String getAncestors() {
		return this.ancestors;
	}
	
	public void setAncestors(String ancestors) {
		this.ancestors = ancestors;
	}

	/** 子节点 */
	private List<?> children;
	
	public List<?> getChildren() {
		if(this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}
	
	public void setChildren(List<?> children) {
		this.children = children;
	}
}
