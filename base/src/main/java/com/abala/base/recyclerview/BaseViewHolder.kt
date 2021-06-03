package com.abala.base.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.abala.base.data.BaseItemData
import com.abala.base.view.IBaseView

class BaseViewHolder(private val iBaseView: IBaseView<out BaseItemData>) :
    RecyclerView.ViewHolder(iBaseView as View) {

    fun bind(itemData: BaseItemData) {
        iBaseView.setItemData(itemData)
    }

}