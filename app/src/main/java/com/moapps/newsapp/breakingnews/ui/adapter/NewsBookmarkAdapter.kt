package com.moapps.newsapp.breakingnews.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.moapps.newsapp.breakingnews.R
import com.moapps.newsapp.breakingnews.data.models.news.Article
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent
import android.net.Uri
import android.widget.*
import androidx.core.content.ContextCompat


class NewsBookmarkAdapter(var context: Context) :
    RecyclerView.Adapter<NewsBookmarkAdapter.ArticleViewHolder>() {

    private var articlesList = listOf<Article?>()

    fun addData(data: List<Article?>) {
        articlesList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_layout_bookmarrk, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articlesList[position]
        if (article?.title?.length!! > 90) {
            holder.title.text = article.title.substring(0, 86) + ".."
        } else {
            holder.title.text = article.title
        }

        if (article.source.length > 10) {
            holder.source.text = article.source.substring(0, 10) + ".."
        } else {
            holder.source.text = article.source
        }

        holder.date.text = article.date

        Glide.with(context).load(article.img).listener(
            object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progress.visibility = View.GONE
                    return false
                }

            }).into(holder.img)

        holder.itemView.apply {
            setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }

        holder.deleteBookamrk.apply {
            setOnClickListener {
                onItemDeleteClickListener?.let { it(article) }
            }
        }

    }

    private var onItemDeleteClickListener: ((Article) -> Unit)? = null

    fun setOnItemDeleteClickListener(listener: ((Article) -> Unit)?) {
        onItemDeleteClickListener = listener
    }


    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Article) -> Unit)?) {
        onItemClickListener = listener
    }

    override fun getItemCount() = articlesList.size

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.image_row)
        var title: TextView = itemView.findViewById(R.id.title_row)
        var progress: ProgressBar = itemView.findViewById(R.id.progress_row)
        var layout: LinearLayout = itemView.findViewById(R.id.layout_row)
        var date: TextView = itemView.findViewById(R.id.date_row)
        var source: TextView = itemView.findViewById(R.id.source_row)
        var deleteBookamrk: ImageButton = itemView.findViewById(R.id.delete_bookmak_btn)


    }

}