package com.abala.jetpack.mvvm.viewmodel

import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableField
import com.abala.jetpack.mvvm.model.Checkerboard
import com.abala.jetpack.mvvm.model.Player

class PalaceViewModel {
    var checkerboard: Checkerboard? = null
    val palaces = ObservableArrayMap<String, String>()
    val winner = ObservableField<String>()

    init {
        checkerboard = Checkerboard()
    }

    fun onResetClick() {
        checkerboard?.reset()
        winner.set(null)
        palaces.clear()
    }


    fun onPalaceClick(x: Int, y: Int) {
        val player: Player? = checkerboard?.done(x, y)
        if (player != null) {
            palaces["$x$y"] = player.toString()
            val result = "对弈胜方： " + (checkerboard?.winner?.toString() ?: "未分胜负")
            winner.set(result)
        }
    }
}