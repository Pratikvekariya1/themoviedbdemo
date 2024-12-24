package com.themoviedbdemo.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.themoviedbdemo.adapter.PersonAdapter
import com.themoviedbdemo.databinding.ActivityMainBinding
import com.themoviedbdemo.network.RetrofitInstance
import com.themoviedbdemo.network.repository.PersonViewModelFactory
import com.themoviedbdemo.ui.dialog.showErrorDialog
import com.themoviedbdemo.utills.CustomException
import com.themoviedbdemo.utills.DelayedTextWatcher
import com.themoviedbdemo.utills.ExceptionType
import com.themoviedbdemo.viewmodel.PersonViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PersonAdapter
    private var searchJob: Job? = null
    private val viewModel: PersonViewModel by viewModels {
        PersonViewModelFactory(RetrofitInstance.getInstance(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        search("")

        binding.retryButton.setOnClickListener {
            adapter.retry()
        }

        binding.customToolbar.clearIcon.isVisible =
            !binding.customToolbar.searchEditText.text.isNullOrEmpty()


        binding.customToolbar.clearIcon.setOnClickListener {
            hideKeyboard()
            binding.customToolbar.searchEditText.setText("")
        }

        binding.customToolbar.searchEditText.addTextChangedListener(DelayedTextWatcher {
            val text = binding.customToolbar.searchEditText.text
            binding.customToolbar.clearIcon.isVisible = !text.isNullOrEmpty()
            search(text.toString())
        })

    }

    private fun setupRecyclerView() {
        adapter = PersonAdapter { person ->
            val intent = Intent(this, PersonDetailsActivity::class.java)
            intent.putExtra("person", person)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Add divider
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        adapter.addLoadStateListener { loadState ->
            val refreshState = loadState.source.refresh
            // gone error text field
            binding.txtError.visibility = View.GONE

            binding.retryButton.visibility =
                if (refreshState is LoadState.Error) View.VISIBLE else View.GONE

            binding.loadingIndicator.visibility =
                if (refreshState is LoadState.Loading) View.VISIBLE else View.GONE

            binding.recyclerView.isVisible = refreshState is LoadState.NotLoading

            if (refreshState is LoadState.Error) {
                (refreshState.error as? CustomException)?.let {
                    binding.recyclerView.visibility = View.GONE
                    binding.txtError.visibility = View.GONE
                    binding.txtError.text = refreshState.error.message
                    binding.loadingIndicator.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE

                    when (it.type) {
                        ExceptionType.UNKNOWN -> {
                            showErrorDialog(
                                this,
                                "Error",
                                refreshState.error.message ?: "An error occurred"
                            ) {
                                adapter.retry()
                            }
                        }

                        ExceptionType.API_ERROR -> {
                            showErrorDialog(
                                this,
                                "Error",
                                refreshState.error.message ?: "An error occurred"
                            ) {
                                adapter.retry()
                            }
                        }

                        ExceptionType.NO_DATA_FOUND -> {
                            binding.txtError.visibility = View.VISIBLE
                            binding.retryButton.visibility = View.VISIBLE
                        }
                    }
                }
            }

            /*} else {
                    binding.txtError.visibility = View.VISIBLE
                    binding.loadingIndicator.visibility = View.GONE
                    binding.retryButton.visibility =View.VISIBLE
                    binding.txtError.text =

                // visible
// settext = ""
            }*/
        }
    }

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getPopularPersons(query).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}