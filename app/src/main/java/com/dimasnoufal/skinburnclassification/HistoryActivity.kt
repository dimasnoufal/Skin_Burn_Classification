//package com.dimasnoufal.skinburnclassification
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import androidx.activity.result.ActivityResultLauncher
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.dimasnoufal.skinburnclassification.adapter.HistoryAdapter
//import com.dimasnoufal.skinburnclassification.databinding.ActivityHistoryBinding
//
//class HistoryActivity : AppCompatActivity() {
//
//    lateinit var binding: ActivityHistoryBinding
//    private lateinit var adapter: HistoryAdapter
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityHistoryBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.rvHistory.layoutManager = LinearLayoutManager(this)
//        binding.rvHistory.setHasFixedSize(true)
//
//    }
//}