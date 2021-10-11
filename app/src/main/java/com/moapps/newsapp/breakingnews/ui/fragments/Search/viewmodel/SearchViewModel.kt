package com.moapps.newsapp.breakingnews.ui.fragments.Search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.models.sites.Site
import com.moapps.newsapp.breakingnews.data.repository.SearchRepo
import com.moapps.newsapp.breakingnews.utils.Coroutines
import com.moapps.newsapp.breakingnews.utils.NoInternetException
import com.moapps.newsapp.breakingnews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
        private val searchRepo: SearchRepo
) : ViewModel() {

    private val sitesMutableLiveData = MutableLiveData<Resource<List<Site>>>()

    private val searchMutableLiveData = MutableLiveData<Resource<List<Article>>>()


    init {
        getSites()
    }

    fun queryForNews(query: String): LiveData<Resource<List<Article>>> {
        searchMutableLiveData.value = Resource.loading(null)

        Coroutines.io {

            try {
                val response = searchRepo.queryForNews(query)
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    // Success
                    Coroutines.withContextMain {
                        searchMutableLiveData.value = Resource.success(result.articles)
                    }
                }

            } catch (noInternet: NoInternetException) {
                Coroutines.withContextMain {
                    searchMutableLiveData.value = Resource.error(noInternet.message
                            ?: "No Internet Connection", null)
                }
            } catch (e: Exception) {
                Coroutines.withContextMain {
                    searchMutableLiveData.value = Resource.error("An error occurred", null)
                }
            }


        }


        return searchMutableLiveData
    }

    private fun getSites() {

        val cacheSites = searchRepo.getAllSitesCache()
        sitesMutableLiveData.value = Resource.loading(cacheSites)

        Coroutines.io {

            try {
                val response = searchRepo.getSites()
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    //Success
                    searchRepo.deleteAllCacheSites()
                    result.sites.forEach {
                        searchRepo.insertAllCacheSites(it)
                    }

                    withContext(Dispatchers.Main) {
                        sitesMutableLiveData.value = Resource.success(searchRepo.getAllSitesCache())
                    }

                }
            } catch (internet: NoInternetException) {
                withContext(Dispatchers.Main) {
                    sitesMutableLiveData.value = Resource.error(internet.message
                            ?: "No Internet connection", cacheSites)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    sitesMutableLiveData.value = Resource.error("An error occurred check connection", cacheSites)
                }
            }

        }

    }

    fun getLiveDataSites(): LiveData<Resource<List<Site>>> {
        return sitesMutableLiveData
    }


}