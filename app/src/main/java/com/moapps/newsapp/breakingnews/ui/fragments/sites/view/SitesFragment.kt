package com.moapps.newsapp.breakingnews.ui.fragments.sites.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.moapps.newsapp.breakingnews.R
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.databinding.FragmentSitesBinding
import com.moapps.newsapp.breakingnews.ui.adapter.NewsAdapter
import com.moapps.newsapp.breakingnews.ui.fragments.sites.viewmodel.SitesViewModel
import com.moapps.newsapp.breakingnews.ui.fragments.webview.view.WebviewFragmentArgs
import com.moapps.newsapp.breakingnews.utils.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@WithFragmentBindings
@AndroidEntryPoint
class SitesFragment : Fragment() {

    private val viewModel: SitesViewModel by viewModels()
    private lateinit var binding: FragmentSitesBinding
    private lateinit var adapter: NewsAdapter
    private val args: SitesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSitesBinding.inflate(layoutInflater)
        val site = args.site
        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.site.name
        setupUI()
        setUpObserver(site.name)
        return binding.root
    }

    private fun setupUI() {
        adapter = NewsAdapter(requireContext())
        binding.sitesPreviewRv.layoutManager = LinearLayoutManager(context)
        binding.sitesPreviewRv.adapter = adapter
    }

    private fun setUpObserver(query_site: String) {
        //Observer and get the Articles

        viewModel.getSiteResult(query_site).observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    binding.loadingView.hideLoading()
                    binding.errorText.hideError()
                    renderList(it.Data!!)
                }
                Status.LOADING -> {
                    binding.loadingView.showLoading()
                    binding.errorText.hideError()
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.errorText.showError(it.message)
                    binding.loadingView.hideLoading()
                }
            }

        })
    }

    private fun renderList(articles: List<Article>) {
        adapter.addData(articles)
        adapter.notifyDataSetChanged()
    }

}