package com.example.socialmediaapp.post

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.socialmediaapp.MainActivity
import com.example.socialmediaapp.R
import com.example.socialmediaapp.databinding.ActivityPostBinding
import com.example.socialmediaapp.fragments.Home
import com.example.socialmediaapp.model.Post
import com.example.socialmediaapp.model.User
import com.example.socialmediaapp.utility.POSTS
import com.example.socialmediaapp.utility.POST_FOLDER
import com.example.socialmediaapp.utility.USER_NODE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.MainScope
import java.util.UUID

class PostActivity : AppCompatActivity() {

    lateinit var binding:ActivityPostBinding
    private var postImg: Uri? = null
    private var imageUrl:String? = null
  //  private var user:User? = null

     val gallaryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { uri ->
        if (uri.resultCode == Activity.RESULT_OK) {
            postImg = uri.data!!.data
            binding.circleImageView2.setImageURI(postImg)
            binding.circleImageView2.visibility = View.VISIBLE

            postImg?.let {
                uploadImage()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

//        user=User()
//        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
//            user = it.toObject<User>()!!
//    }


        binding.circleImageView2.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "image/*"
            gallaryLauncher.launch(intent)
        }

        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        binding.postBtn.setOnClickListener {

           // Log.e("Tag", user!!.image.toString())

            val post: Post = Post(
                postImgUrl = imageUrl!!,
                caption = binding.captions.editText?.text.toString(),
                uid = Firebase.auth.currentUser!!.uid,
                time = System.currentTimeMillis().toString()
                // userName = user!!.name.toString(),
               // userProfileImg = user!!.image.toString()

            )




            Firebase.firestore.collection(POSTS).document()
                .set(post)
                .addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                        .set(post)
                        .addOnSuccessListener {
                            Toast.makeText(this, "post added successfully", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                }
        }

    }

    private fun uploadImage() {
        val filename = UUID.randomUUID().toString()+".jpg"

        FirebaseStorage.getInstance().getReference(POST_FOLDER).child(filename)
            .putFile(postImg!!)
            .addOnSuccessListener {img->
                img.storage.downloadUrl.addOnSuccessListener {url->
                    imageUrl = url.toString()
                    Log.e("imageUrl",imageUrl.toString())
                }

            }
    }
}



