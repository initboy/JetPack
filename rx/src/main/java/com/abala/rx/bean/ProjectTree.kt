package com.abala.rx.bean

data class ProjectTree(
    private var errorCode: Int = 0,
    private val errorMsg: String? = null,
    val data: List<Tree>? = null
)

data class Tree(
    private var courseId: Int = 0,
    var id: Int = 0,
    private val name: String? = null,
    private val order: Int = 0,
    private val parentChapterId: Int = 0,
    private val userControlSetTop: Boolean = false,
    private val visible: Int = 0,
    private val children: List<*>? = null
)
