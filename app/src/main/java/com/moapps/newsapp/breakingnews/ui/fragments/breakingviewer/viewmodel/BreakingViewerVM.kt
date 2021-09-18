package com.moapps.newsapp.breakingnews.ui.fragments.breakingviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moapps.newsapp.breakingnews.data.repository.BreakingViewerRepo
import com.moapps.newsapp.breakingnews.utils.Coroutines
import com.moapps.newsapp.breakingnews.utils.NoInternetException
import com.moapps.newsapp.breakingnews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreakingViewerVM @Inject constructor(
    private val breakingViewerRepo: BreakingViewerRepo
) : ViewModel() {

    private val convertLinkMutableLiveData = MutableLiveData<Resource<String>>()


    fun convertNewsNowLink(url: String): LiveData<Resource<String>> {
        convertLinkMutableLiveData.value = Resource.loading(null)
        Coroutines.io {
            try {
                val response = breakingViewerRepo.convertNewsNowLink(url)
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    Coroutines.withContextMain {
                        convertLinkMutableLiveData.value = Resource.success(result.link)
                    }
                }
            } catch (noInternet: NoInternetException) {
                Coroutines.withContextMain {
                    convertLinkMutableLiveData.value =
                        Resource.error(noInternet.message ?: "No Internet Connection", null)
                }
            } catch (e: Exception) {
                Coroutines.withContextMain {
                    convertLinkMutableLiveData.value = Resource.error("an error occurred", null)
                }
            }

        }
        return convertLinkMutableLiveData
    }

}