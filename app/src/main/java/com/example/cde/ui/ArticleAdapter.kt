package com.example.cde.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.cde.R
import com.example.cde.network.Article
import com.example.cde.utils.Utils
import kotlinx.android.synthetic.main.article_item.view.*

class ArticleAdapter(private val articleList: ArrayList<Article>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private lateinit var context: Context
    private var originalArticleList = ArrayList<Article>()

    init {
        originalArticleList.addAll(articleList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        this.context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, articleList[position])
    }

    override fun getItemCount(): Int = articleList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(context: Context, article: Article) {
            Glide.with(context).load(article.thumbnail)
                .transition(DrawableTransitionOptions.withCrossFade()).into(itemView.articleImage)
            itemView.articleDate.text = Utils.getFormattedDate(article.date.orEmpty())
            itemView.articleTitle.text = article.title.orEmpty()
            itemView.articleDescription.text = article.description.orEmpty()
        }
    }

    // Clears the current adapter list, while using the original list of
    // articles (originalArticleList) to filter through

    fun filterByTag(tag: String?) {
        articleList.clear()

        if(tag == null) {
            articleList.addAll(originalArticleList)
        }
        else {
            for (article in originalArticleList) {
                if (article.tag.equals(tag))
                    articleList.add(article)
            }
        }
        notifyDataSetChanged()
    }
}