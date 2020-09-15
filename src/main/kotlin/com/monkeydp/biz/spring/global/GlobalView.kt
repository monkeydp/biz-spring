package com.monkeydp.biz.spring.global

/**
 * 通用视图
 *
 * @author iPotato-Work
 * @date 2020/5/30
 */
// #### 控制器使用 ####

/**
 * 查询视图
 */
interface QueryView

/**
 * 列表视图
 */
interface ListView

/**
 * 分页列表视图
 */
interface PagingView : ListView

/**
 * 创建视图
 */
interface CreateView

/**
 * 编辑视图
 */
interface EditView
