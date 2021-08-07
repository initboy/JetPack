package com.abala.rx.bean

data class ProjectList(
    private var data: ListData? = null,
    private val errorCode: Int = 0,
    private val errorMsg: String? = null
)

data class ListData(
    private var curPage: Int = 0,
    private val offset: Int = 0,
    private val over: Boolean = false,
    private val pageCount: Int = 0,
    private val size: Int = 0,
    private val total: Int = 0,
    private val datas: List<ItemData>? = null
)

data class ItemData(
    private var apkLink: String? = null,
    private val author: String? = null,
    private val chapterId: Int = 0,
    private val chapterName: String? = null,
    private val collect: Boolean = false,
    private val courseId: Int = 0,
    private val desc: String? = null,
    private val envelopePic: String? = null,
    private val fresh: Boolean = false,
    private val id: Int = 0,
    private val link: String? = null,
    private val niceDate: String? = null,
    private val origin: String? = null,
    private val prefix: String? = null,
    private val projectLink: String? = null,
    private val publishTime: Long = 0,
    private val superChapterId: Int = 0,
    private val superChapterName: String? = null,
    private val title: String? = null,
    private val type: Int = 0,
    private val userId: Int = 0,
    private val visible: Int = 0,
    private val zan: Int = 0,
    private val tags: List<Tags>? = null
)

data class Tags(
    private var name: String? = null,
    private val url: String? = null
)