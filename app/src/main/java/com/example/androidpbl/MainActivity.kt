package com.example.androidpbl

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidpbl.databinding.ArticlesItemMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
data class Posts( val title: String, val content : String) {
    constructor(doc: QueryDocumentSnapshot) :
            this(doc.id, doc["내용"].toString())
    constructor(key: String, map: Map<*, *>) :
            this(key, map["내용"].toString())
}
class MainActivity : AppCompatActivity() {
    private var adapter: MyAdapter? = null
    private var loginUserEmail: String? = null
    val firestore = Firebase.firestore
    private var itemsCollectionRef : CollectionReference? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginUserEmail= intent?.getStringExtra("loginUserEmail") ?: ""

        itemsCollectionRef = firestore.collection("users").document(loginUserEmail!!).collection("posts")

        val recyclerView = findViewById<RecyclerView>(R.id.main_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapter(this, emptyList())
        recyclerView.adapter = adapter
        updateList()

        val logOutButton = findViewById<TextView>(R.id.logutTextView)
        logOutButton.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        val postAddButton = findViewById<Button>(R.id.mainPostAddButton)
        postAddButton.setOnClickListener {
            val intent = Intent(this, PostAddActivity::class.java)
            startActivity(intent)
        }

        // 화면좀 보려고 추가했습니다
        val showFriendButton = findViewById<Button>(R.id.mainShowFriendButton)
        val searchFriendText = findViewById<EditText>(R.id.editTextTextPersonName3)

        showFriendButton.setOnClickListener {
//            val input = searchFriendText.getText()
            val intent = Intent(this, FriendListActivity::class.java);
//            intent.putExtra("searchName", input)
            startActivity(intent) // 검색창의 이름을 전달한다
        }

        val searchFriendButton = findViewById<ImageButton>(R.id.imageButton)
        searchFriendButton.setOnClickListener {
            val input = searchFriendText.text.toString()
            val intent = Intent(this, FriendSearchActivity::class.java)
            intent.putExtra("searchName", input)
            startActivity(intent);
        }
    }

    private fun updateList() {
        itemsCollectionRef?.get()?.addOnSuccessListener {
            val posts = mutableListOf<Posts>()
            for (doc in it) {
                posts.add(Posts(doc))
            }
            println(posts)
            adapter?.updateAdapterList(posts)
        }
    }
    inner class MyViewHolder(val binding : ArticlesItemMainBinding) : RecyclerView.ViewHolder(binding.root)

    inner class MyAdapter(private val context: Context, private var items: List<Posts>) : RecyclerView.Adapter<MyViewHolder>() {

        fun updateAdapterList(newList: MutableList<Posts>) {
            items = newList
            println("posts $items")
            notifyDataSetChanged()
        }

        init {
            firestore.collection("users").document(loginUserEmail!!).collection("posts")
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    updateList()
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ArticlesItemMainBinding.inflate(inflater, parent, false)
            return MyViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            var smallContent =items[position].content
            if(items[position].content.length > 20)
                smallContent = items[position].content.substring(0,20) + "..."
            holder.binding.myArticleTitle.text = items[position].title
            holder.binding.myArticleContent.text= smallContent
        }

        override fun getItemCount(): Int {
            return items.size
        }
    }

}



