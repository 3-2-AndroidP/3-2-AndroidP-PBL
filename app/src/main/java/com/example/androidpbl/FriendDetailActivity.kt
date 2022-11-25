package com.example.androidpbl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpbl.databinding.ActivityFriendDetailBinding
import com.example.androidpbl.databinding.ArticlesItemMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

//data class FriendPosts( val title: String, val content : String) {
//    constructor(doc: QueryDocumentSnapshot) :
//            this(doc.id, doc["content"].toString())
//    constructor(key: String, map: Map<*, *>) :
//            this(key, map["content"].toString())
//}
data class FriendPost( val title: String, val content : String) {
    constructor(doc: QueryDocumentSnapshot) :
            this(doc.id, doc["content"].toString())
    constructor(key: String, map: Map<*, *>) :
            this(key, map["content"].toString())
}

class FriendDetailActivity : AppCompatActivity() {
    private var loginUserEmail: String? = null
    val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFriendDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        loginUserEmail= intent?.getStringExtra("loginUserEmail") ?: ""

        binding.friendDetailRecyclerView.adapter = RecyclerViewAdapter()
        binding.friendDetailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.friendDetailRecyclerView.setHasFixedSize(true)

        // 클릭한 친구의 이메일
        val intent = intent //전달할 데이터를 받을 Intent
        val friendInfo = intent.getSerializableExtra("friendInfo") as SearchFriend
        println("@@@@@@@@@@@@@@@@@@@@@@@@@${friendInfo.name} ${friendInfo.email}") // 이름과 이메일 전달

        binding.friendNameText.text = friendInfo.name
        binding.showEmailText.text = friendInfo.email

        binding.postAddButton.setOnClickListener {
            val intent = Intent(this, PostAddActivity::class.java)
            intent.putExtra("loginUserEmail",  loginUserEmail)
            startActivity(intent)
        }
        binding.showFriendButton.setOnClickListener {
            val intent = Intent(this, FriendListActivity::class.java)
            intent.putExtra("loginUserEmail",  loginUserEmail)
            startActivity(intent) // 검색창의 이름을 전달한다
        }

        val searchFriendButton = findViewById<ImageButton>(R.id.imageButton)
        searchFriendButton.setOnClickListener {
            val searchName = binding.editTextTextPersonName.text.toString()
            if(searchName == ""){
                Snackbar.make(it, "검색할 이름을 입력해주세요.", Snackbar.LENGTH_LONG). show();
            }
            else {
                val intent = Intent(this, FriendSearchActivity::class.java)
                intent.putExtra("searchName", searchName)
                intent.putExtra("loginUserEmail", loginUserEmail)
                startActivity(intent);
            }
        }

        binding.logout4.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var PostsArray = mutableListOf<FriendPost>()
        val listUserEmail = intent.getStringExtra("loginUserEmail").toString()
        val friendInfo = intent.getSerializableExtra("friendInfo") as SearchFriend
        init {  // users의 문서를 불러온 뒤 SearchFriend으로 변환해 ArrayList에 담음
            firestore.collection("users").document(friendInfo.email!!).collection("posts")
                ?.get()?.addOnCompleteListener { data ->
                    for (doc in data.result) {
                        PostsArray.add(FriendPost(doc))
                    }
                    println(PostsArray)
                    notifyDataSetChanged()
                }
        }
        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.articles_item_main, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val postTitle = view.findViewById<TextView>(R.id.myArticleTitle)
            val postContent = view.findViewById<TextView>(R.id.myArticleContent)
            fun bind(item: FriendPost) {
                postTitle.text = item.title
                postContent.text = item.content
            }
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var smallContent = PostsArray[position].content
            println(smallContent)
            if(PostsArray[position].content!!.length > 20)
                smallContent = PostsArray[position].content!!.substring(0,20) + "..."
            var viewHolder = (holder as ViewHolder).itemView
            viewHolder.findViewById<TextView>(R.id.myArticleTitle).text = PostsArray[position].title
            viewHolder.findViewById<TextView>(R.id.myArticleContent).text = smallContent
            holder.bind(PostsArray[position])
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return PostsArray.size
        }
    }
}



