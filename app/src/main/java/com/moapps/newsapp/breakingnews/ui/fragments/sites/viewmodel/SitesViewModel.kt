package com.moapps.newsapp.breakingnews.ui.fragments.sites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.repository.SitesRepo
import com.moapps.newsapp.breakingnews.utils.Coroutines
import com.moapps.newsapp.breakingnews.utils.NoInternetException
import com.moapps.newsapp.breakingnews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SitesViewModel @Inject constructor(
    private val sitesRepo: SitesRepo
) : ViewModel() {

    private val siteResultMutableLiveData = MutableLiveData<Resource<List<Article>>>()

    fun getSiteResult(query_site: String): LiveData<Resource<List<Article>>> {
        siteResultMutableLiveData.value = Resource.loading(null)

        Coroutines.io {
            try {
                val response = sitesRepo.getSiteResult(query_site)
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Coroutines.withContextMain {
                        siteResultMutableLiveData.value = Resource.success(result.articles)
                    }
                } else {
                    Coroutines.withContextMain {
                        siteResultMutableLiveData.value = Resource.error("An error occurred", null)
                    }
                }
            } catch (noInternet: NoInternetException) {
                Coroutines.withContextMain {
                    siteResultMutableLiveData.value =
                        Resource.error(noInternet.message ?: "No Internet connection", null)
                }
            } catch (e: Exception) {
                Coroutines.withContextMain {
                    siteResultMutableLiveData.value =
                        Resource.error("An error occurred", null)
                }
            }

        }

        return siteResultMutableLiveData
    }


}