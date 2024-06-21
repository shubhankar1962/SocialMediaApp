package com.example.socialmediaapp.auth

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import com.bumptech.glide.Glide
import com.example.socialmediaapp.MainActivity

import com.example.socialmediaapp.databinding.ActivitySignUpBinding
import com.example.socialmediaapp.model.User
import com.example.socialmediaapp.utility.USER_NODE
import com.example.socialmediaapp.utility.USER_PROFILE_FOLDER
import com.example.socialmediaapp.utility.Utils.Companion.showToast

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

import java.util.UUID


class SignUpActivity : AppCompatActivity() {
    lateinit var binding:ActivitySignUpBinding
    lateinit var user:User
    private var profileImg: Uri? = null
    private var imageUrl:String?= null

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {uri->
       if(uri.resultCode == Activity.RESULT_OK){
           profileImg = uri.data!!.data
           binding.signUpImgeView.setImageURI(profileImg)
           binding.signUpImgeView.visibility = View.VISIBLE

           profileImg?.let{
               uploadImage()
           }
       }



    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = User()

        if(intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE", -1) == 1) {
                binding.registerBtn.text = "Update Profile"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!
                        if(!user.image.isNullOrEmpty()) {
                            Glide.with(this).load(user.image).into(binding.signUpImgeView)
                        }
                        binding.name.editText?.setText(user.name)
                        binding.mail.editText?.setText(user.email)
                        binding.password.editText?.setText(user.password)
                    }
            }
        }

        binding.registerBtn.setOnClickListener {

            if(intent.hasExtra("MODE")){
                if(intent.getIntExtra("MODE",-1)==1){
                    Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user)
                        .addOnSuccessListener {
                            Log.e("TAG",user.image.toString())
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }

                }
            }else {
                if (binding.registerMail.text.toString()
                        .equals("") or binding.registerPassword.text.toString().equals("") or
                    binding.registerUserName.text.toString().equals("")
                ) {
                    Toast.makeText(
                        this,
                        "Please filled all the required fields",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.registerMail.text.toString(),
                        binding.registerPassword.text.toString()
                    ).addOnCompleteListener { result ->
                        if (result.isSuccessful) {
                            createUserInDB()
                            showToast(this, "User Register Successfully")
                        } else {
                            showToast(this, "Something went Wrong")
                        }

                    }
                }
            }
        }

        binding.addImageIcon.setOnClickListener {
            val intent = Intent("android.intent.action.GET_CONTENT")
            intent.type  = "image/*"
            launcher.launch(intent)


        }

        binding.signUptoLogin.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
        }
    }

    private fun uploadImage(){

        val fileName = UUID.randomUUID().toString()+".jpg"
        FirebaseStorage.getInstance().getReference(USER_PROFILE_FOLDER).child(fileName)
            .putFile(profileImg!!)
            .addOnSuccessListener {res->
                res.storage.downloadUrl.addOnSuccessListener { image ->
                    imageUrl = image.toString()
                    user.image = imageUrl.toString()
                }
            }

    }

    private fun createUserInDB() {
        user.name = binding.registerUserName.text.toString()
        user.image = binding.signUpImgeView.toString()
        user.email = binding.registerMail.text.toString()
        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user)
            .addOnSuccessListener {
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }

        }
}
