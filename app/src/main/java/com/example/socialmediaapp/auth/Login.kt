package com.example.socialmediaapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialmediaapp.MainActivity
import com.example.socialmediaapp.R
import com.example.socialmediaapp.databinding.ActivityLoginBinding
import com.example.socialmediaapp.model.User
import com.example.socialmediaapp.utility.Utils.Companion.showToast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.LoginBtn.setOnClickListener {
            if(binding.loginmail.text.toString().equals("") or binding.loginPassword.text.toString().equals("")){
                showToast(this,"Please fill all the required Fields")
            }else{
                FirebaseAuth.getInstance().signInWithEmailAndPassword(binding.loginmail.text.toString(),binding.loginPassword.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        showToast(this,"Login Successful")
                        startActivity(Intent(this,MainActivity::class.java))
                    }else{
                        showToast(this,"User does not exist ")
                    }
                }

            }
        }



    }
}