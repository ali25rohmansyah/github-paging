package com.raproject.alirohmansyah.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.raproject.alirohmansyah.ui.adapter.UsersAdapter
import com.raproject.alirohmansyah.databinding.ActivityMainBinding
import com.raproject.alirohmansyah.ui.adapter.UsersLoadingAdapter
import com.raproject.alirohmansyah.utils.Constants.SEARCH_TIME_DELAY
import com.raproject.alirohmansyah.ui.viewmodels.MainViewModel
import com.raproject.alirohmansyah.utils.RvItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val adapter = UsersAdapter()
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpAdapter()

        binding.edtQuery.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchUsers(editable.toString())
                            .observe(this@MainActivity, Observer {
                                adapter.submitData(this@MainActivity.lifecycle, it)
                            })
                    }
                }
            }
        }
    }

    private fun setUpAdapter() {

        binding.rvListUsers.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            addItemDecoration(RvItemDecoration())
        }
        binding.rvListUsers.adapter = adapter.withLoadStateFooter(
            footer = UsersLoadingAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->

            if (loadState.refresh is LoadState.Loading) {

                if (adapter.snapshot().isEmpty()) {
                    binding.progressBar.visibility = View.VISIBLE
                }
                binding.txtError.visibility = View.INVISIBLE

            } else {
                binding.progressBar.visibility = View.INVISIBLE


                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error

                    else -> null
                }
                error?.let {
                    if (adapter.snapshot().isEmpty()) {
                        binding.txtError.isVisible = true
                        binding.txtError.text = it.error.localizedMessage
                    }

                }

            }
        }

    }
}