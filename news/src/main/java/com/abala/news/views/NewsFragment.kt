package com.abala.news.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.abala.base.model.IBaseModelListener
import com.abala.base.model.PagingResult
import com.abala.base.viewmodel.BaseViewModel
import com.abala.news.R
import com.abala.news.databinding.FragmentNewsBinding

class NewsFragment : Fragment(), IBaseModelListener {

    private val mData: ArrayList<BaseViewModel> = ArrayList()
    private val mModel: NewsModel = NewsModel(this)
    private var mAdapter: NewsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNewsBinding? =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        binding?.content?.run {
            mAdapter = NewsAdapter()
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            mModel.loadNextPage()
        }
        return binding?.root
    }

    override fun onDataFetched(data: List<BaseViewModel>, vararg results: PagingResult?) {
        if (results.isNotEmpty() && (results[0]?.isFirstPage == true)) {
            mData.clear()
        }
        mData.addAll(data)
        mAdapter?.setData(mData)
    }
}