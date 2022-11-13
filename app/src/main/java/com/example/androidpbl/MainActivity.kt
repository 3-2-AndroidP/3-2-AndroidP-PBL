package com.example.androidpbl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpbl.databinding.ArticlesItemMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.main_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = MyAdapter()

        val postAddButton = findViewById<ImageButton>(R.id.mainPostAddButton)
        postAddButton.setOnClickListener {
            val intent = Intent(this, PostAddActivity::class.java)
            startActivity(intent)
        }

        // 화면좀 보려고 추가했습니다
        val showFriendButton = findViewById<ImageView>(R.id.showFriendButton)
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

class MyViewHolder(val binding : ArticlesItemMainBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter() : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ArticlesItemMainBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      holder.binding.myArticleTitle.text ="post1"
        holder.binding.myArticleContent.text= "post Content"
    }

    override fun getItemCount(): Int {
        return 3
    }
}