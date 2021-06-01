package com.abala.jetpack.mvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.abala.jetpack.R
import com.abala.jetpack.databinding.ActivityNinePalaceGameBinding
import com.abala.jetpack.mvvm.viewmodel.PalaceViewModel

class NinePalaceGameActivity : AppCompatActivity() {
    val palaceViewModel = PalaceViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBinding: ActivityNinePalaceGameBinding = DataBindingUtil.setContentView(this, R.layout.activity_nine_palace_game)
        dataBinding.palaceViewModel = palaceViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.reset, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                palaceViewModel.onResetClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}