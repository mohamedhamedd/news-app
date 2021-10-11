package com.moapps.newsapp.breakingnews.ui.fragments.Search.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.moapps.newsapp.breakingnews.R
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.data.models.sites.Site
import com.moapps.newsapp.breakingnews.databinding.FragmentSearchBinding
import com.moapps.newsapp.breakingnews.ui.adapter.NewsAdapter
import com.moapps.newsapp.breakingnews.ui.adapter.NewsSitesAdapter
import com.moapps.newsapp.breakingnews.ui.fragments.Search.viewmodel.SearchViewModel
import com.moapps.newsapp.breakingnews.utils.*
import com.moapps.newsapp.breakingnews.utils.Constants.ARGS_ARTICLE
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings


@WithFragmentBindings
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewmodel: SearchViewModel by viewModels()
    private lateinit var adapter: NewsSitesAdapter
    private lateinit var adapterSearch: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater)

        setupUI()
        setUpObserverSites()
        searchEditText()
        handleRecyclerViewSearchItemClick()
        handleRecyclerViewSitesItemClick()
        return binding.root
    }

    private fun setupUI() {
        adapter = NewsSitesAdapter(requireContext())
        val gridLayoutManager = GridLayoutManager(context, 5)
        binding.sitesRv.layoutManager = gridLayoutManager
        binding.sitesRv.adapter = adapter

        adapterSearch = NewsAdapter(requireContext())
        binding.searchRv.layoutManager = LinearLayoutManager(context)
        binding.searchRv.adapter = adapterSearch
    }

    private fun setUpObserverSites() {

        viewmodel.getLiveDataSites().observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Status.LOADING -> {
                    renderListSites(it.Data!!)
                }
                Status.SUCCESS -> {
                    binding.errorText.hideError()
                    renderListSites(it.Data!!)
                }
                Status.ERROR -> {
                    renderListSites(it.Data!!)
                    binding.errorText.showError(it.message)
                }
            }

        })

    }

    private fun setUpObserverSearch(query: String) {

        viewmodel.queryForNews(query).observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Status.LOADING -> {
                    binding.loadingView.showLoading()
                }
                Status.SUCCESS -> {
                    binding.errorText.hideError()
                    binding.loadingView.hideLoading()
                    binding.searchRv.visibility = View.VISIBLE
                    renderListSearch(it.Data!!)
                }
                Status.ERROR -> {
                    binding.loadingView.hideLoading()
                    binding.errorText.showError(it.message)
                }
            }

        })

    }

    private fun renderListSites(sites: List<Site>) {
        adapter.addData(sites)
        adapter.notifyDataSetChanged()
    }

    private fun renderListSearch(articles: List<Article>) {
        adapterSearch.addData(articles)
        adapterSearch.notifyDataSetChanged()
    }


    private fun searchEditText() {

        val searchEditText = binding.searchEt
        searchEditText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // If the event is a key-down event on the "enter" button
                if (event.action === KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // Perform action on key press
                    val query = searchEditText.text.toString()
                    if (query.isNotEmpty()) {
                        binding.popularSites.visibility = View.GONE
                        setUpObserverSearch(query)
                    }
                    return true
                }
                return false
            }
        })

    }

    private fun handleRecyclerViewSearchItemClick() {
        adapterSearch.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(Constants.ARGS_ARTICLE, it)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_webviewFragment,
                bundle
            )
        }
    }

    private fun handleRecyclerViewSitesItemClick() {
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(Constants.ARGS_SITE, it)
            }
            findNavController().navigate(
                R.id.action_searchFragment_to_sitesFragment,
                bundle
            )
        }
    }

}