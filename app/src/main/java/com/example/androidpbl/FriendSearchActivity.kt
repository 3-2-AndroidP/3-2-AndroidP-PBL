package com.example.androidpbl

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidpbl.databinding.ActivityFriendListBinding
import com.example.androidpbl.databinding.ActivityFriendSearchBinding

class FriendSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_friend_list)
//        setSupportActionBar(findViewById(R.id.toolbar2))
        val binding = ActivityFriendSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: ListViewModel by viewModels()

        binding.friendSearch.adapter = FriendSearchAdapter(viewModel)
        binding.friendSearch.layoutManager = LinearLayoutManager(this)
        binding.friendSearch.setHasFixedSize(true)

        val friendName = "친구"
        binding.textView3.setText("${friendName}검색 결과")

    }
}