package com.abala.base.model

import com.abala.base.data.BaseItemData

interface OnFetchedListener<D : BaseItemData> {
    fun onFetched(model: BaseModel<D>, data: List<D>)
}