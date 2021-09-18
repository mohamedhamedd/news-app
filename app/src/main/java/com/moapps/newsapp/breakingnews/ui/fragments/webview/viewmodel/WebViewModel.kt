package com.moapps.newsapp.breakingnews.ui.fragments.webview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moapps.newsapp.breakingnews.data.models.convertlink.ConvertLink
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.repository.WebviewRepo
import com.moapps.newsapp.breakingnews.utils.Coroutines
import com.moapps.newsapp.breakingnews.utils.NoInternetException
import com.moapps.newsapp.breakingnews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    private val webviewRepo: WebviewRepo
) : ViewModel() {

    private val convertLinkMutableLiveData = MutableLiveData<Resource<String>>()

    fun insertArticle(article: Article) {
        Coroutines.io {
            webviewRepo.insertArticle(article)
        }
    }


    fun convertGoogleNewsLink(url: String): LiveData<Resource<String>> {
        convertLinkMutableLiveData.value = Resource.loading(null)
        Coroutines.io {
            try {
                val response = webviewRepo.convertGoogleNewsLink(url)
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    //Success
                    Coroutines.withContextMain {
                        convertLinkMutableLiveData.value = Resource.success(result.link)
                    }
                }
            } catch (noInternet: NoInternetException) {
                Coroutines.withContextMain {
                    convertLinkMutableLiveData.value =
                        Resource.error(noInternet.message ?: "No Internet connection", null)
                }
            } catch (e: Exception) {
                Coroutines.withContextMain {
                    convertLinkMutableLiveData.value =
                        Resource.error("an error occurred", null)
                }
            }

        }
        return convertLinkMutableLiveData
    }


}