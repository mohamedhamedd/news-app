package com.moapps.newsapp.breakingnews.ui.fragments.breaking.view

import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.moapps.newsapp.breakingnews.R
import com.moapps.newsapp.breakingnews.data.models.breaking.Breaking
import com.moapps.newsapp.breakingnews.data.models.news.Article
import com.moapps.newsapp.breakingnews.databinding.FragmentBreakingBinding
import com.moapps.newsapp.breakingnews.ui.adapter.BreakingNewsAdapter
import com.moapps.newsapp.breakingnews.ui.adapter.NewsAdapter
import com.moapps.newsapp.breakingnews.ui.fragments.breaking.viewmodel.BreakingViewModel
import com.moapps.newsapp.breakingnews.utils.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@WithFragmentBindings
@AndroidEntryPoint
class BreakingFragment : Fragment() {

    private val viewModel: BreakingViewModel by viewModels()
    private lateinit var binding: FragmentBreakingBinding
    private lateinit var adapter: BreakingNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBreakingBinding.inflate(layoutInflater)
        setupUI()
        setUpObserver()
        pullToRefresh()
        handleRecyclerViewItemClick()
        return binding.root
    }

    private fun setupUI() {
        adapter = BreakingNewsAdapter(requireContext())
        binding.breakingNewsRv.layoutManager = LinearLayoutManager(context)
        binding.breakingNewsRv.adapter = adapter
    }

    private fun setUpObserver() {
        //Observer and get the Articles

        viewModel.getBreakingNews().observe(viewLifecycleOwner, Observer {

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
    }

    private fun renderList(breaking: List<Breaking>) {
        adapter.addData(breaking)
        adapter.notifyDataSetChanged()
    }

    private fun pullToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            pullSoundEffect()
            setUpObserver()
        }
    }

    private fun pullSoundEffect() {
        MediaPlayer.create(context, R.raw.pull_load).start()
    }

    private fun handleRecyclerViewItemClick() {
        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(Constants.ARGS_BREAKING, it)
            }
            findNavController().navigate(
                R.id.action_breakingFragment_to_breakingNewsViewerFragment,
                bundle
            )
        }
    }

}