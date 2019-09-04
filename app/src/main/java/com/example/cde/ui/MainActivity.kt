package com.example.cde.ui

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cde.R
import com.example.cde.network.Article
import com.example.cde.network.Callback
import com.example.cde.network.Request
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Response
import java.io.IOException
import com.example.cde.utils.Utils
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    private lateinit var articleAdapter: ArticleAdapter
    private val request: Request = Request()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        articleRecyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            request.getArticles(object : Callback() {
                override fun onResponse(response: Response?, stringBody: String?, exception: IOException?) {
                    progressBar.visibility = View.GONE

                    if(!Utils.hasErrors(this@MainActivity, response, exception)) {
                        val articleListType = object : TypeToken<List<Article>>() { }.type
                        val articleList = Gson().fromJson<ArrayList<Article>>(stringBody, articleListType)

                        if(articleList != null)
                            addTagViews(Utils.getTagList(articleList))

                        articleAdapter = ArticleAdapter(articleList)
                        adapter = articleAdapter
                    }
                }
            })
        }
    }

    private fun addTagViews(tagList: List<String>) {
        for(tag in tagList) {
            val chip = Chip(this)
            chip.text = Utils.formatTagName(tag)
            chip.isCheckable = true
            chip.isCheckedIconVisible= false
            chip.chipBackgroundColor = getColorStateList(R.color.tag_background)
            chip.setTypeface(chip.typeface, Typeface.BOLD)
            chip.setTextColor(getColorStateList(R.color.tag_text_color))
            chip.textSize = 15f
            chipGroup.addView(chip);
            chipGroup.isSingleSelection = true

            setTagAction(chip, tag)
        }
    }

    private fun setTagAction(chip: Chip, tagName: String) {
        chip.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                articleAdapter.filterByTag(tagName)
            }
            else if(noTagsChecked()){
                articleAdapter.filterByTag(null)
            }
        }
    }

    // Checks if there are any tags checked to implement the uncheck functionality of the tag and resetting the list
    private fun noTagsChecked():Boolean {
        for (i in 0 until chipGroup.childCount) {
            val tagChip = chipGroup.getChildAt(i) as Chip
            if(tagChip.isChecked)
                return false
        }
        return true
    }

}
