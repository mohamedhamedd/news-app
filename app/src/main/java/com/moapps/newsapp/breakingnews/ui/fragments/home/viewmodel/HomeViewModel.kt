package com.moapps.newsapp.breakingnews.ui.fragments.home.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Handler
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.models.interests.Interests
import com.moapps.newsapp.breakingnews.data.repository.HomeRepo
import com.moapps.newsapp.breakingnews.utils.Coroutines
import com.moapps.newsapp.breakingnews.utils.NoInternetException
import com.moapps.newsapp.breakingnews.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepo: HomeRepo,
) : ViewModel() {

    private val articlesMutableLiveData = MutableLiveData<Resource<List<Article>>>()

    fun getArticles(
        i1: String,
        i2: String,
        i3: String,
        i4: String
    ): LiveData<Resource<List<Article>>> {

        Coroutines.io {
            val cacheList = homeRepo.getHomeArticles()
            Coroutines.withContextMain {
                articlesMutableLiveData.value = Resource.loading(cacheList)
            }

            try {
                val response =
                    homeRepo.makeInterestsApiCall(i1, i2, i3, i4)
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    homeRepo.deleteHomeArticles()

                    result.articles.forEach {
                        homeRepo.insertHomeArticles(it)
                    }

                    Coroutines.withContextMain {
                        articlesMutableLiveData.value = Resource.success(homeRepo.getHomeArticles())
                    }

                }

            } catch (noInternet: NoInternetException) {
                Coroutines.withContextMain {
                    articlesMutableLiveData.value =
                        Resource.error("${noInternet.message}", cacheList)
                }
            } catch (e: Exception) {
                Coroutines.withContextMain {
                    articlesMutableLiveData.value =
                        Resource.error("An error occurred check connection", cacheList)
                }
            }

        }

        return articlesMutableLiveData
    }

    fun getInterests(): LiveData<Interests> {
        return homeRepo.getInterests()
    }

    private fun insertDefaultInterests(
        context: Context,
    ) {

        //Insert default interests for first time
        Coroutines.io {
            homeRepo.insertDefaultInterests(
                Interests(
                    0,
                    "covid",
                    "football",
                    "politics",
                    "usa"
                )
            )
        }

        val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
        dialog.setCancelable(false)
        dialog.setTitle("Welcome!")
        dialog.setMessage("Go to settings and customize your interests!")
        dialog.setPositiveButton("Great", DialogInterface.OnClickListener { _, _ ->
            //Action for "Delete".
        })
        val alert: AlertDialog = dialog.create()

        Handler().postDelayed(Runnable {
            alert.show()
        }, 2000)


    }

    fun codeFirstTimeOpenApp(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        if (!prefs.getBoolean("firstTime", false)) {
            // <---- run one time code here
            insertDefaultInterests(context)

            // mark first time has ran.
            val editor = prefs.edit()
            editor.putBoolean("firstTime", true)
            editor.apply()
        }
    }

}