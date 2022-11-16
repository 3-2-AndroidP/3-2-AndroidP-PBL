package com.example.androidpbl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpbl.databinding.ActivityFriendSearchBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FriendSearchActivity : AppCompatActivity() {

    val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFriendSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.friendSearch.adapter = RecyclerViewAdapter2()
        binding.friendSearch.layoutManager = LinearLayoutManager(this)
        binding.friendSearch.setHasFixedSize(true)

        val intent = intent //전달할 데이터를 받을 Intent
        val searchName = intent.getStringExtra("searchName")

        binding.textView3.setText("${searchName}검색 결과")

    }
        inner class RecyclerViewAdapter2 : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var UserArray: ArrayList<SearchFriend> = arrayListOf()
        val searchName = intent.getStringExtra("searchName")
        init {
            firestore?.collection("users")
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    // ArrayList 비워줌
                    UserArray.clear()
                    for (snapshot in querySnapshot!!.documents) {
                        var item = snapshot.toObject(SearchFriend::class.java)
                        if (item?.name!!.contains(searchName.toString())) {// 문서의 name 속성이 검색한 이름과 같으면 넣는다
                            UserArray.add(item!!)
                        }
                    }
                    notifyDataSetChanged()
                }
        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            viewHolder.findViewById<TextView>(R.id.textView2).text = UserArray[position].name
            viewHolder.findViewById<TextView>(R.id.textView7).text = UserArray[position].email
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return UserArray.size
        }
    }
}