package com.example.cde.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.cde.R
import com.example.cde.network.Article
import okhttp3.Response
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Utils {
    companion object {
        fun getTagList(articleList: ArrayList<Article>): List<String> {
            val tagList = ArrayList<String>()
            for(article in articleList) {
                val tag = article.tag!!.trim()
                if(!tagList.contains(tag))
                    tagList.add(tag)
            }
            return tagList
        }

        private fun splitCamelCase(s:String):String {
             return s.replace(String.format("%s|%s|%s",
                 "(?<=[A-Z])(?=[A-Z][a-z])",
                 "(?<=[^A-Z])(?=[A-Z])",
                 "(?<=[A-Za-z])(?=[^A-Za-z])"
             ).toRegex(), " ")
        }

        fun formatTagName(tag: String): String {
            return splitCamelCase(tag).toLowerCase().capitalize()
        }

        fun getFormattedDate(dateString: String): String {
            val oldFormat = SimpleDateFormat("yyyy-dd-MM'T'hh:mm:ss'Z'", Locale.getDefault())
            val newFormat = SimpleDateFormat("MM/dd/yyy", Locale.getDefault())
            var date: Date? = null
            try {
                date = oldFormat.parse(dateString)
            } catch (e: ParseException) {
                Log.e("TAG_NEWS", e.localizedMessage)
            }

            return newFormat.format(date)
        }

        fun hasErrors(context: Context, response: Response?, exception: IOException?): Boolean {
            if (exception != null) {
                AlertDialog.Builder(context).setTitle(context.getString(R.string.no_internet))
                    .setMessage(context.getString(R.string.check_connection))
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
            }
            else
                return !response!!.isSuccessful

            return true
        }
    }
}