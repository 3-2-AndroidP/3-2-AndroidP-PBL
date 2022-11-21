package com.example.androidpbl

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpbl.databinding.ActivityFriendListBinding
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FriendListActivity : AppCompatActivity() {
    var listUserEmail: String? = null
    val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFriendListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.friendList.adapter = RecyclerViewAdapter()
        binding.friendList.layoutManager = LinearLayoutManager(this)
        binding.friendList.setHasFixedSize(true)
        binding.friendList.addItemDecoration(VerticalItemDecorator(10))
        binding.friendList.addItemDecoration(HorizontalItemDecorator(30))

        binding.postAddButton2.setOnClickListener {
            val intent = Intent(this, PostAddActivity::class.java)
            startActivity(intent)
        }

        binding.showFriendButton2.setOnClickListener {
            val intent = Intent(this, FriendListActivity::class.java);
            startActivity(intent) // 검색창의 이름을 전달한다
        }

        val intent = intent
        listUserEmail = intent.getStringExtra("loginUserEmail").toString()
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var UserArray: ArrayList<SearchFriend> = arrayListOf()
//        val listUserEmail = "aaa@naver.com"
        init {  // users의 문서를 불러온 뒤 SearchFriend으로 변환해 ArrayList에 담음
            firestore?.collection("users")?.document(listUserEmail.toString())?.collection("friends")?.orderBy("name", Query.Direction.ASCENDING)
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                UserArray.clear()
                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(SearchFriend::class.java)
                    UserArray.add(item!!)
                }
                notifyDataSetChanged()
            }
        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout2, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val clickName = view.findViewById<TextView>(R.id.nameText)
            val clickEmail = view.findViewById<TextView>(R.id.textView6)
            fun bind(item: SearchFriend) {
                clickName.text = item.name
                clickEmail.text = item.email
                itemView.setOnClickListener {
                    val intent = Intent(this@FriendListActivity, FriendDetailActivity::class.java)
                    intent.putExtra("friendInfo", item)
                    startActivity(intent);
                }

            }

        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView
            viewHolder.findViewById<TextView>(R.id.nameText).text = UserArray[position].name
            viewHolder.findViewById<TextView>(R.id.textView6).text = UserArray[position].email
            holder.bind(UserArray[position])
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return UserArray.size
        }
    }


}