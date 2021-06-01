package com.abala.common.views.picturenews

import android.content.Context
import android.view.View
import com.abala.base.view.AbsBaseView
import com.abala.common.R
import com.abala.common.databinding.PictureNewsViewBinding

class PictureNewsView(context: Context) :
    AbsBaseView<PictureNewsViewBinding, PictureNewsViewModel>(context) {
    override fun layoutId(): Int = R.layout.picture_news_view

    override fun onRootViewClick(v: View?) {

    }

    override fun <VM> bind(data: VM) {
        binding.pictureNews = data as PictureNewsViewModel
    }
}