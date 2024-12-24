package com.themoviedbdemo.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.themoviedbdemo.R
import com.themoviedbdemo.adapter.PersonAdapter
import com.themoviedbdemo.databinding.ActivityMainBinding
import com.themoviedbdemo.network.RetrofitInstance
import com.themoviedbdemo.repository.PersonViewModelFactory
import com.themoviedbdemo.ui.dialog.showErrorDialog
import com.themoviedbdemo.viewmodel.PersonViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  adapter : PersonAdapter

    private val viewModel: PersonViewModel by viewModels {
        PersonViewModelFactory(RetrofitInstance.getInstance(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeData()

        binding.retryButton.setOnClickListener {
            adapter.retry()
        }
    }

    private fun setupRecyclerView() {
        adapter  = PersonAdapter{ person ->
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

            binding.retryButton.visibility =
                if (refreshState is LoadState.Error) View.VISIBLE else View.GONE

            binding.loadingIndicator.visibility =
                if (refreshState is LoadState.Loading) View.VISIBLE else View.GONE

            if (refreshState is LoadState.Error) {
                showErrorDialog(this,"Error", refreshState.error.message ?: "An error occurred") {
                    adapter.retry()
                }
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.getPopularPersons().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
}