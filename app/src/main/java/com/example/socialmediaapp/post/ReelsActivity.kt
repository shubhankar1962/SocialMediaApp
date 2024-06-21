package com.example.socialmediaapp.post

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.socialmediaapp.MainActivity
import com.example.socialmediaapp.R
import com.example.socialmediaapp.databinding.ActivityReelsBinding
import com.example.socialmediaapp.model.Reel
import com.example.socialmediaapp.model.User
import com.example.socialmediaapp.utility.REEL
import com.example.socialmediaapp.utility.REEL_FOLDER
import com.example.socialmediaapp.utility.USER_NODE
import com.example.socialmediaapp.utility.Utils.Companion.showToast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class ReelsActivity : AppCompatActivity() {

    lateinit var binding:ActivityReelsBinding
    private var postVid:Uri?= null
    private var vidUrl:String? = null

    val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){uri->
        if(uri.resultCode == Activity.RESULT_OK){
            postVid = uri.data!!.data

            postVid?.let {
                uploadReel()
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReelsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reelImgView.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type = "video/*"
            galleryLauncher.launch(intent)
        }

        binding.cancelBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        binding.postBtn.setOnClickListener {

            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    var user:User = it.toObject<User>()!!
                    val reel:Reel = Reel(vidUrl!!,binding.captions.editText?.text.toString(),user.image!!)

                    Firebase.firestore.collection(REEL).document().set(reel)
                        .addOnSuccessListener {
                            Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+REEL).document()
                                .set(reel)
                                .addOnSuccessListener {
                                    startActivity(Intent(this,MainActivity::class.java))
                                    finish()
                                }

                }


                }
        }

    }

    private fun uploadReel() {
        val dialog = ProgressDialog(this)
        dialog.setTitle("Loading...")
        dialog.show()
        val fileName = UUID.randomUUID().toString()+".mp4"
        FirebaseStorage.getInstance().getReference(REEL_FOLDER).child(fileName)
            .putFile(postVid!!).addOnSuccessListener {vid->
                vid.storage.downloadUrl.addOnSuccessListener {url->
                    vidUrl = url.toString()
                    dialog.dismiss()
                }

            }
            .addOnProgressListener {
                val uploadedVal:Long = (it.bytesTransferred/it.totalByteCount)*100
                dialog.setMessage("uploaded $uploadedVal% data successfully")
            }
    }
}