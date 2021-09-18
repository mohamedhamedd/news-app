package com.moapps.newsapp.breakingnews.ui.fragments.breaking.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moapps.newsapp.breakingnews.data.models.breaking.Breaking
import com.moapps.newsapp.breakingnews.data.repository.BreakingRepo
import com.moapps.newsapp.breakingnews.utils.Coroutines
import com.moapps.newsapp.breakingnews.utils.NoInternetException
import com.moapps.newsapp.breakingnews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BreakingViewModel @Inject constructor(
    private val breakingRepo: BreakingRepo
) : ViewModel() {

    private val breakingMutableLiveData = MutableLiveData<Resource<List<Breaking>>>()


    fun getBreakingNews(): LiveData<Resource<List<Breaking>>> {
        val cacheLocal = breakingRepo.getLocalBreakingNews()
        breakingMutableLiveData.value = Resource.loading(cacheLocal)

        Coroutines.io {

            try {
                val response = breakingRepo.getBreakingNews()
                val result = response.body()

                if (response.isSuccessful && result != null) {

                    breakingRepo.deleteLocalBreakingNews()
                    result.breaking.forEach {
                        breakingRepo.insertLocalBreakingNews(it)
                    }

                    Coroutines.withContextMain {
                        breakingMutableLiveData.value =
                            Resource.success(breakingRepo.getLocalBreakingNews())
                    }
                } else {
                    Coroutines.withContextMain {
                        breakingMutableLiveData.value =
                            Resource.error("An error occurred", cacheLocal)
                    }
                }

            } catch (noInternet: NoInternetException) {
                Coroutines.withContextMain {
                    breakingMutableLiveData.value =
                        Resource.error(noInternet.message ?: "An error occurred", cacheLocal)
                }
            } catch (e: Exception) {
                Coroutines.withContextMain {
                    breakingMutableLiveData.value =
                        Resource.error(e.message ?: "An error occurred", cacheLocal)
                }
            }

        }

        return breakingMutableLiveData
    }
}