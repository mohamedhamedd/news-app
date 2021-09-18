package com.moapps.newsapp.breakingnews.ui.fragments.settings.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moapps.newsapp.breakingnews.data.models.interests.Interests
import com.moapps.newsapp.breakingnews.data.repository.SettingsRepo
import com.moapps.newsapp.breakingnews.utils.Coroutines
import com.moapps.newsapp.breakingnews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepo: SettingsRepo
) : ViewModel() {

    private val setInterestsLiveData = MutableLiveData<Resource<String>>()

    init {
        getInterests()
    }

    fun getInterests():LiveData<Interests> {
        return settingsRepo.getInterests()
    }

    fun editInterests(i1: String, i2: String, i3: String, i4: String): LiveData<Resource<String>> {

        setInterestsLiveData.value = Resource.loading(null)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                settingsRepo.insertInterests(Interests(id = 0, i1, i2, i3, i4))
                withContext(Dispatchers.Main) {
                    setInterestsLiveData.value = Resource.success("")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    setInterestsLiveData.value =
                        Resource.error(e.message ?: "An error occurred try again", null)
                }
            }
        }
        return setInterestsLiveData
    }

}