package com.moapps.newsapp.breakingnews.ui.fragments.webview.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.moapps.newsapp.breakingnews.databinding.FragmentWebviewBinding
import com.moapps.newsapp.breakingnews.ui.fragments.webview.viewmodel.WebViewModel
import com.moapps.newsapp.breakingnews.utils.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings


@WithFragmentBindings
@AndroidEntryPoint
class WebviewFragment : Fragment() {

    private val viewModel: WebViewModel by viewModels()
    private val args: WebviewFragmentArgs by navArgs()
    private lateinit var binding: FragmentWebviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWebviewBinding.inflate(layoutInflater)
        val article = args.article
        setUpConvertLinkObserver(article.url)


        binding.openBrowserFloat.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(article.url)
            startActivity(i)
        }

        binding.bookmarkFloat.setOnClickListener {
            viewModel.insertArticle(article)
            showTSnackbar(binding.root, "Successfully added.")
        }

        return binding.root
    }


    private fun setUpWebView(url: String) {
        binding.webview.webViewClient = WebViewClient()
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.loadUrl(url)
        binding.webview.visibility = View.VISIBLE
        binding.webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                binding.progressWebView.hideBar()
                binding.errorText.hideError()
            }
        }
    }

    private fun setUpConvertLinkObserver(url: String) {
        viewModel.convertGoogleNewsLink(url).observe(viewLifecycleOwner, Observer {

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