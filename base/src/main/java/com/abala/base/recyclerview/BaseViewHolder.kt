package com.abala.base.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.abala.base.viewmodel.BaseViewModel
import com.abala.base.view.IBaseView

class BaseViewHolder(private val iBaseView: IBaseView<out BaseViewModel>) :
    RecyclerView.ViewHolder(iBaseView as View) {

    fun bind(viewModel: BaseViewModel) {
        iBaseView.setViewModel(viewModel)
    }

}