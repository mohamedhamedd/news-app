package com.moapps.newsapp.breakingnews.ui.fragments.home.view

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moapps.newsapp.breakingnews.R
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.databinding.FragmentHomeBinding
import com.moapps.newsapp.breakingnews.ui.adapter.NewsAdapter
import com.moapps.newsapp.breakingnews.ui.fragments.home.viewmodel.HomeViewModel
import com.moapps.newsapp.breakingnews.utils.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@WithFragmentBindings
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewmodel: HomeViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setupUI()
        setUpObserver()
        pullToRefresh()
        viewmodel.codeFirstTimeOpenApp(requireContext())
        handleRecyclerViewItemClick()
        return binding.root
    }

    private fun setupUI() {
        adapter = NewsAdapter(requireContext())
        binding.countryRv.layoutManager = LinearLayoutManager(context)
        binding.countryRv.adapter = adapter
    }

    private fun setUpObserver() {
        //Observer and get the Articles

        viewmodel.getInterests().observe(viewLifecycleOwner, Observer {


            viewmodel.getArticles(it.interest1, it.interest2, it.interest3, it.interest4)
                .observe(viewLifecycleOwner, Observer {

                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.loadingView.hideLoading()
                            binding.errorText.hideError()
                            renderList(it.Data!!)
                            binding.swipeRefresh.isRefreshing = false
                        }
                        Status.LOADING -> {
                            binding.loadingView.showLoading()
                            binding.errorText.hideError()
                            renderList(it.Data!!)
                        }
                        Status.ERROR -> {
                            //Handle Error
                            binding.errorText.showError(it.message)
                            binding.loadingView.hideLoading()
                            binding.swipeRefresh.isRefreshing = false
                        }
                    }

                })


        })
    }

    private fun renderList(articles: List<Article>) {
        adapter.addData(articles)
        adapter.notifyDataSetChanged()
    }

    private fun pullToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            pullSoundEffect()
            setUpObserver()
        }
    }

    private fun handleRecyclerViewItemClick() {
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(Constants.ARGS_ARTICLE, it)
            }
            findNavController().navigate(
                R.id.action_homeFragment_to_webviewFragment,
                bundle
            )
        }
    }

    private fun pullSoundEffect() {
        MediaPlayer.create(context, R.raw.pull_load).start()
    }

}