package com.moapps.newsapp.breakingnews.ui.fragments.breakingviewer.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.moapps.newsapp.breakingnews.databinding.FragmentBreakingNewsViewerBinding
import com.moapps.newsapp.breakingnews.ui.fragments.breakingviewer.viewmodel.BreakingViewerVM
import com.moapps.newsapp.breakingnews.utils.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@WithFragmentBindings
@AndroidEntryPoint
class BreakingNewsViewerFragment : Fragment() {


    private lateinit var binding: FragmentBreakingNewsViewerBinding
    private val args: BreakingNewsViewerFragmentArgs by navArgs()
    private val viewModel: BreakingViewerVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBreakingNewsViewerBinding.inflate(layoutInflater)

        val breaking = args.breaking
        setUpConvertLinkObserver(breaking.url)

        binding.openBrowserFloat.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(breaking.url)
            startActivity(i)
        }

        return binding.root
    }

    private fun setUpWebView(url: String) {
        binding.webViewBreakingNews.webViewClient = WebViewClient()
        binding.webViewBreakingNews.settings.javaScriptEnabled = true
        binding.webViewBreakingNews.loadUrl(url)
        binding.webViewBreakingNews.visibility = View.VISIBLE
        binding.webViewBreakingNews.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressWebView.hideBar()
                binding.errorText.hideError()
            }
        }
    }

    private fun setUpConvertLinkObserver(url: String) {
        viewModel.convertNewsNowLink(url).observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Status.LOADING -> {
                    binding.progressWebView.showBar()
                }
                Status.SUCCESS -> {
                    setUpWebView(it.Data!!)
                }
                Status.ERROR -> {
                    binding.progressWebView.hideBar()
                    binding.errorText.showError(it.message)

                }
            }
        })
    }

}