package com.monkeydp.biz.spring.global

/**
 * 通用视图
 *
 * @author iPotato-Work
 * @date 2020/5/30
 */

// #### 实体使用 ####
/**
 * 简略信息视图
 */
interface SimpleView

// #### 控制器使用 ####

/**
 * 查询视图
 */
interface QueryView : SimpleView

/**
 * 列表视图
 */
interface ListView : SimpleView

/**
 * 分页列表视图
 */
interface PagingView : ListView

/**
 * 创建视图
 */
interface CreateView : SimpleView

/**
 * 编辑视图
 */
interface EditView : SimpleView
