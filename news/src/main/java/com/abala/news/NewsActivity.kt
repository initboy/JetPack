package com.abala.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abala.news.views.NewsFragment
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        supportFragmentManager.beginTransaction().replace(R.id.news_container, NewsFragment())
            .commit()
    }
}