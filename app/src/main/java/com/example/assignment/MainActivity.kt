package com.example.assignment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.assignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var bi: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bi = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bi.root)

        setupView()
        observeResponse()
    }

    private fun setupView() {
        bi.btnFetchText.setOnClickListener {
            bi.txtExtracted.text = "Loading\n"

            lifecycleScope.launch {
                delay(2000)
                viewModel.callAllThreeRequests()
            }
        }
    }

    private fun observeResponse() {

        viewModel.contentResponse.observe(this) { state ->
            when (state) {

                is DataState.Error -> {
                    (bi.txtExtracted.text.toString() + state.message).also {
                        bi.txtExtracted.text = it
                    }
                }

                is DataState.Success -> {
                    val sb = StringBuilder()
                    sb.append("*****Content Fetch from blog*****\n\n")
                        .append(state.data.wholeText).append("\n\n")
                    sb.append("*****Content Fetch from blog*****\n\n")
                        .append(state.data.request1Content)
                        .append(state.data.request2Content)
                        .append(state.data.request3Content)

                    bi.txtExtracted.text = sb.toString()
                }
            }
        }
    }

}