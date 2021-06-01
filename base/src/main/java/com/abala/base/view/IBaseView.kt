package com.abala.base.view

import com.abala.base.viewmodel.BaseViewModel

interface IBaseView<VM : BaseViewModel> {
    fun <VM> setViewModel(viewModel: VM)
}