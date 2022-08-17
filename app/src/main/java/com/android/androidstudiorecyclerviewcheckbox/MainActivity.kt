package com.android.androidstudiorecyclerviewcheckbox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.androidstudiorecyclerviewcheckbox.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var list= arrayListOf<CheckboxData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        for (i in 0..100){
            list.add(CheckboxData(i,false))
        }
        binding.recyclerView.adapter=RecyclerViewAdapter(list)






    }
}