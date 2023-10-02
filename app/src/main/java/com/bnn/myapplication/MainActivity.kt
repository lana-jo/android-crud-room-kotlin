package com.bnn.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bnn.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private lateinit var contentViewModel: ContentViewModel
	private lateinit var listAdapter: ContentAdapter
//	var listAdapter = ContentAdapter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		contentViewModel= ViewModelProvider(this)[ContentViewModel::class.java]
		setContentView(binding.root)

		listAdapter = ContentAdapter()
		binding.recyclerview.adapter = listAdapter
		binding.recyclerview.layoutManager = LinearLayoutManager(this)
		binding.recyclerview.setHasFixedSize(true)


		contentViewModel.dataList.observe(this, Observer { developers ->
				developers?.let { listAdapter.setItems(it) }
			})
	}
}