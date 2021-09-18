package com.moapps.newsapp.breakingnews.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.moapps.newsapp.breakingnews.R
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.models.sites.Site


class NewsSitesAdapter(var context: Context) :
    RecyclerView.Adapter<NewsSitesAdapter.ArticleViewHolder>() {

    private var articlesList = listOf<Site?>()

    fun addData(data: List<Site?>) {
        articlesList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_layout_sites, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val site = articlesList[position]

        Glide.with(context).load(site?.img).listener(
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
                onItemClickListener?.let { it(site!!) }
            }
        }


    }

    private var onItemClickListener: ((Site) -> Unit)? = null

    fun setOnItemClickListener(listener: ((Site) -> Unit)?) {
        onItemClickListener = listener
    }

    override fun getItemCount() = articlesList.size

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.row_img_sites)
        var progress: ProgressBar = itemView.findViewById(R.id.row_progress_sites)
        var layout: RelativeLayout = itemView.findViewById(R.id.row_layout_sites)
    }

}