package com.abala.news.views

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abala.base.data.BaseItemData
import com.abala.base.view.BaseFragment
import com.abala.common.views.textnews.TextNewsItemData
import com.abala.news.R
import com.abala.news.databinding.FragmentNewsBinding

class NewsFragment :
    BaseFragment<FragmentNewsBinding, NewsViewModel, TextNewsItemData>() {

    private val mData: ArrayList<BaseItemData> = ArrayList()
    private var mAdapter: NewsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.content?.run {
            mAdapter = NewsAdapter()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }


    override fun layoutId(): Int = R.layout.fragment_news

    override fun createViewModel(): NewsViewModel {
        return ViewModelProvider(this, NewsViewModel.Factory()).get(NewsViewModel::class.java)
    }

    override fun onDataChanged(data: List<TextNewsItemData>) {
        mAdapter?.setData(mData)
    }
}