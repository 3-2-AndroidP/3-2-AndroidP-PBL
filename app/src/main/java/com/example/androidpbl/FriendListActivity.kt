package com.example.androidpbl

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidpbl.databinding.ActivityFriendListBinding

class FriendListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_friend_list)
//        setSupportActionBar(findViewById(R.id.toolbar2))
        val binding = ActivityFriendListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: ListViewModel by viewModels()

        binding.friendList.adapter = FriendListAdapter(viewModel)
        binding.friendList.layoutManager = LinearLayoutManager(this)
        binding.friendList.setHasFixedSize(true)


    }
}