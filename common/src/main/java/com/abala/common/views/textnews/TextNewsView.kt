package com.abala.common.views.textnews

import android.content.Context
import android.view.View
import com.abala.base.view.AbsBaseView
import com.abala.common.R
import com.abala.common.databinding.TextNewsViewBinding

class TextNewsView(context: Context) :
    AbsBaseView<TextNewsViewBinding, TextNewsItemData>(context) {
    override fun layoutId(): Int = R.layout.text_news_view

    override fun onRootViewClick(v: View?) {

    }

    override fun <D> bind(data: D) {
        binding.textNews = data as TextNewsItemData
    }
}