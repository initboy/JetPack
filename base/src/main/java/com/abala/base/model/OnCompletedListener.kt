package com.abala.base.model

interface OnCompletedListener<D> {
    fun onCompleted(data: List<D>)
}