package com.abala.base.view

import com.abala.base.data.BaseItemData

interface IBaseView<D : BaseItemData> {
    fun <D> setItemData(itemData: D)
}