package com.moapps.newsapp.breakingnews.ui.fragments.bookmark.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.moapps.newsapp.breakingnews.R
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.databinding.FragmentBookmarkBinding
import com.moapps.newsapp.breakingnews.ui.adapter.NewsAdapter
import com.moapps.newsapp.breakingnews.ui.adapter.NewsBookmarkAdapter
import com.moapps.newsapp.breakingnews.ui.fragments.bookmark.viewmodel.BookmarkViewModel
import com.moapps.newsapp.breakingnews.utils.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@WithFragmentBindings
@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val viewmodel: BookmarkViewModel by viewModels()
    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var adapter: NewsBookmarkAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        setupUI()
        setupObserver()
        handleRecyclerViewItemClick()
        handleRecyclerViewItemDeleteClick()
        return binding.root
    }

    private fun setupUI() {
        adapter = NewsBookmarkAdapter(requireContext())
        binding.laterRv.layoutManager = LinearLayoutManager(context)
        binding.laterRv.adapter = adapter
    }

    private fun setupObserver() {

        viewmodel.getAllArticles().observe(viewLifecycleOwner, Observer {

            binding.bookmarkEmpty.hideError()
            renderList(it.reversed())

        })

    }

    private fun renderList(articles: List<Article>) {
        adapter.addData(articles)
        adapter.notifyDataSetChanged()
    }

    private fun handleRecyclerViewItemDeleteClick() {
        adapter.setOnItemDeleteClickListener {
            viewmodel.deleteArticle(it).apply {
                showTSnackbar(binding.root, "Deleted.")
            }
        }
    }


    private fun handleRecyclerViewItemClick() {
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(Constants.ARGS_ARTICLE, it)
            }
            findNavController().navigate(
                R.id.action_bookmarkFragment_to_webviewFragment,
                bundle
            )
        }
    }

}