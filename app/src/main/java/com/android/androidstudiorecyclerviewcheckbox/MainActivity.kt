package com.android.androidstudiorecyclerviewcheckbox

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.androidstudiorecyclerviewcheckbox.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerViewAdapter:RecyclerViewAdapter
    var list= arrayListOf<CheckboxData>()
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        for (i in 0..100){
            list.add(CheckboxData(i,false))
        }
         recyclerViewAdapter=RecyclerViewAdapter(list)
        binding.recyclerView.adapter=recyclerViewAdapter



        val swipeHelperCallback = SwipeHelperCallback(recyclerViewAdapter).apply {
            setClamp(resources.displayMetrics.widthPixels.toFloat() / 4)    // 1080 / 4 = 270
        }
        ItemTouchHelper(swipeHelperCallback).attachToRecyclerView(binding.recyclerView)


        binding.recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL))

        binding.recyclerView.setOnTouchListener { _, _ ->
            swipeHelperCallback.removePreviousClamp(binding.recyclerView)
            false
        }
    }
}