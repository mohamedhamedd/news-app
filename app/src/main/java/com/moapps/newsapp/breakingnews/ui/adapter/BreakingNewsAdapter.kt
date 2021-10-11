package com.moapps.newsapp.breakingnews.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.moapps.newsapp.breakingnews.R
import com.moapps.newsapp.breakingnews.data.models.breaking.Breaking


class BreakingNewsAdapter(var context: Context) :
    RecyclerView.Adapter<BreakingNewsAdapter.ArticleViewHolder>() {

    private var breakingNewsList = listOf<Breaking?>()

    fun addData(data: List<Breaking?>) {
        breakingNewsList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_layout_breaking, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val breaking = breakingNewsList[position]

        holder.title.text = breaking?.title
        holder.source.text = breaking?.source

        Glide.with(context).load(breaking?.img).listener(
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
                onItemClickListener?.let { it(breaking!!) }
            }
        }

    }

    private var onItemClickListener: ((Breaking) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Breaking) -> Unit)?) {
        onItemClickListener = listener
    }


    override fun getItemCount() = breakingNewsList.size

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.row_img_breaking)
        var title: TextView = itemView.findViewById(R.id.row_title_breaking)
        var source: TextView = itemView.findViewById(R.id.row_source_breaking)
        var progress: ProgressBar = itemView.findViewById(R.id.row_progress_breaking)

    }

}