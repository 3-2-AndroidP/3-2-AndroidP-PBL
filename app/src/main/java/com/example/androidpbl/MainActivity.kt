package com.example.androidpbl

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val postAddButton = findViewById<ImageButton>(R.id.postAddButton)
        postAddButton.setOnClickListener {
            val intent = Intent(this, PostAddActivity::class.java)
            startActivity(intent)
        }

        // 화면좀 보려고 추가했습니다
        val showFriendButton = findViewById<ImageButton>(R.id.showFriendButton)
        showFriendButton.setOnClickListener {
            val intent = Intent(this, FriendListActivity::class.java);
            startActivity(intent)
        }

        val searchFriendButton = findViewById<ImageButton>(R.id.imageButton)
        searchFriendButton.setOnClickListener {
            val intent = Intent(this, FriendSearchActivity::class.java)
            startActivity(intent);
        }
    }
}