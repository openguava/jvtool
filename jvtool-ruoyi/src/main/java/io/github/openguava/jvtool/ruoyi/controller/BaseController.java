package io.github.openguava.jvtool.ruoyi.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.github.openguava.jvtool.lang.auth.AuthUser;
import io.github.openguava.jvtool.lang.constant.HttpConstants;
import io.github.openguava.jvtool.lang.result.AjaxResult;
import io.github.openguava.jvtool.lang.util.AuthUtils;
import io.github.openguava.jvtool.lang.util.DateUtils;
import io.github.openguava.jvtool.lang.util.SqlUtils;
import io.github.openguava.jvtool.lang.util.StringUtils;
import io.github.openguava.jvtool.spring.web.page.PageDomain;
import io.github.openguava.jvtool.spring.web.page.TableDataInfo;
import io.github.openguava.jvtool.spring.web.page.TableSupport;

/**
 * web层通用数据处理
 * 
 * @author openguava
 */
public class BaseController {

	/**
	 * 将前台传递过来的日期格式的字符串，自动转化为Date类型
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		this.onInitBinder(binder);
	}

	protected void onInitBinder(WebDataBinder binder) {
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parse(text));
			}
		});
	}

	/**
	 * 设置请求分页数据
	 */
	protected void startPage() {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		Integer pageNum = pageDomain.getPageNum();
		Integer pageSize = pageDomain.getPageSize();
		String orderBy = SqlUtils.checkOrderBySql(pageDomain.getOrderBy());
		Boolean reasonable = pageDomain.getReasonable();
		PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
	}

	/**
	 * 设置请求排序数据
	 */
	protected void startOrderBy() {
		PageDomain pageDomain = TableSupport.buildPageRequest();
		if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
			String orderBy = SqlUtils.checkOrderBySql(pageDomain.getOrderBy());
			PageHelper.orderBy(orderBy);
		}
	}

	/**
	 * 清理分页的线程变量
	 */
	protected void clearPage() {
		PageHelper.clearPage();
	}

	/**
	 * 响应请求分页数据
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected TableDataInfo getDataTable(List<?> list) {
		TableDataInfo rspData = new TableDataInfo();
		rspData.setCode(HttpConstants.HTTP_STATUSCODE_OK);
		rspData.setMsg("查询成功");
		rspData.setRows(list);
		rspData.setTotal(new PageInfo(list).getTotal());
		return rspData;
	}

	/**
	 * 返回成功
	 */
	public AjaxResult success() {
		return AjaxResult.setOk();
	}

	/**
	 * 返回失败消息
	 */
	public AjaxResult error() {
		return AjaxResult.setFail();
	}

	/**
	 * 返回成功消息
	 */
	public AjaxResult success(String message) {
		return AjaxResult.setOk(null, message);
	}

	/**
	 * 返回成功消息
	 */
	public AjaxResult success(Object data) {
		return AjaxResult.setOk(data);
	}

	/**
	 * 返回失败消息
	 */
	public AjaxResult error(String message) {
		return AjaxResult.setFail(message);
	}

	/**
	 * 返回警告消息
	 */
	public AjaxResult warn(String message) {
		return AjaxResult.setWarn(message);
	}

	/**
	 * 响应返回结果
	 * 
	 * @param rows 影响行数
	 * @return 操作结果
	 */
	protected AjaxResult toAjax(int rows) {
		return rows > 0 ? AjaxResult.setOk() : AjaxResult.setFail();
	}

	/**
	 * 响应返回结果
	 * 
	 * @param result 结果
	 * @return 操作结果
	 */
	protected AjaxResult toAjax(boolean result) {
		return result ? success() : error();
	}

	/**
	 * 页面跳转
	 */
	public String redirect(String url) {
		return StringUtils.format("redirect:{}", url);
	}

	/**
	 * 获取用户缓存信息
	 */
	public AuthUser getLoginUser() {
		return AuthUtils.getLoginUser(true);
	}

	/**
	 * 获取登录用户id
	 */
	public String getUserId() {
		return getLoginUser().getUserId();
	}

	/**
	 * 获取登录用户名
	 */
	public String getUsername() {
		return getLoginUser().getUsername();
	}

	/**
	 * 获取登录部门id
	 */
	public String getDeptId() {
		return getLoginUser().getDeptId();
	}
}
