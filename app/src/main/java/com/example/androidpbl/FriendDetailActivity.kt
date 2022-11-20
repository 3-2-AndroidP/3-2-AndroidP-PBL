package com.example.androidpbl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class FriendDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend_detail)
        setSupportActionBar(findViewById(R.id.toolbar))

        val postAddButton = findViewById<ImageButton>(R.id.postAddButton)
        postAddButton.setOnClickListener {
            val intent = Intent(this, PostAddActivity::class.java)
            startActivity(intent)
        }

        // 클릭한 친구의 이메일
        val intent = intent //전달할 데이터를 받을 Intent
        val friendInfo = intent.getSerializableExtra("friendInfo")as SearchFriend
        println("@@@@@@@@@@@@@@@@@@@@@@@@@${friendInfo.name} ${friendInfo.email}") // 이름과 이메일 전달

    }
}