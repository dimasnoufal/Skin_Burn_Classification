package com.dimasnoufal.skinburnclassification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dimasnoufal.skinburnclassification.databinding.ActivityNoSunburnBinding
//import com.dimasnoufal.skinburnclassification.db.HistoryHelper
//import com.dimasnoufal.skinburnclassification.entity.History

class NoSunburnActivity : AppCompatActivity() {

    lateinit var binding: ActivityNoSunburnBinding

//    private var history: History? = null
//    private lateinit var historyHelper: HistoryHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoSunburnBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}