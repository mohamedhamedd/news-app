package com.moapps.newsapp.breakingnews.ui.fragments.bookmark.viewmodel

import androidx.lifecycle.ViewModel
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.repository.BookmarkRepo
import com.moapps.newsapp.breakingnews.utils.Coroutines
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val bookmarkRepo: BookmarkRepo
) : ViewModel() {


    init {
        getAllArticles()
    }

    fun getAllArticles() = bookmarkRepo.getAllArticles()


    fun deleteArticle(articlesBookmark: Article) {
        Coroutines.io {
            bookmarkRepo.deleteArticle(articlesBookmark)
        }
    }

}