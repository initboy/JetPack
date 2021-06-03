package com.abala.news.views

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abala.base.recyclerview.BaseViewHolder
import com.abala.base.data.BaseItemData
import com.abala.common.views.picturenews.PictureNewsView
import com.abala.common.views.picturenews.PictureNewsItemData
import com.abala.common.views.textnews.TextNewsView
import com.abala.common.views.textnews.TextNewsItemData

class NewsAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    companion object {
        const val TYPE_TEXT = 1
        const val TYPE_PICTURE = 2
    }

    private var news: List<BaseItemData>? = null

    fun setData(data: List<BaseItemData>?) {
        news = data
        notifyDataSetChanged()
    }

    //Step 1
    override fun getItemCount(): Int = news?.size ?: 0

    //Step 2
    override fun getItemViewType(position: Int): Int {
        return when (news?.get(position)) {
            is PictureNewsItemData -> TYPE_PICTURE
            is TextNewsItemData -> TYPE_TEXT
            else -> TYPE_TEXT
        }
    }

    //Step 3
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_TEXT -> BaseViewHolder(TextNewsView(parent.context))
            TYPE_PICTURE -> BaseViewHolder(PictureNewsView(parent.context))
            else -> BaseViewHolder(TextNewsView(parent.context))
        }
    }

    //Step 4
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        news?.let {
            holder.bind(it[position])
        }
    }
}