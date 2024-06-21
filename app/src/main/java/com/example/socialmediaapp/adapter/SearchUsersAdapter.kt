package com.example.socialmediaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.socialmediaapp.databinding.SearchUserItemLayoutBinding
import com.example.socialmediaapp.model.User
import com.example.socialmediaapp.utility.FOLLOWEDUSER
import com.example.socialmediaapp.utility.Utils.Companion.showToast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class SearchUsersAdapter(val context:Context,val userList: ArrayList<User>):RecyclerView.Adapter<SearchUsersAdapter.SearchUserViewHolder>(){

    inner class SearchUserViewHolder(val binding: SearchUserItemLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserViewHolder {
        val binding = SearchUserItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return SearchUserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: SearchUserViewHolder, position: Int) {
        var isFollow = false

        val user = userList[position]

        Glide.with(context).load(user.image).into(holder.binding.userImg)
        holder.binding.userName.text = user.name

        Firebase.firestore.collection(FOLLOWEDUSER+Firebase.auth.currentUser!!.uid).whereEqualTo("email",userList.get(position).email).get()
            .addOnSuccessListener {
                if(it.isEmpty){
                    isFollow = false
                }else{
                    holder.binding.followUnfollowBtn.text = "Unfollow"
                    isFollow = true
                }
            }

        holder.binding.followUnfollowBtn.setOnClickListener {
          try {
              if (isFollow) {
                  showToast(context, "user unfollowed")
                  Firebase.firestore.collection(FOLLOWEDUSER + Firebase.auth.currentUser!!.uid)
                      .whereEqualTo("email", userList.get(position).email).get()
                      .addOnSuccessListener {
                          Firebase.firestore.collection(FOLLOWEDUSER + Firebase.auth.currentUser!!.uid)
                              .document(it.documents.get(0).id).delete()
                          holder.binding.followUnfollowBtn.text = "Follow"
                          isFollow = false
                      }

              } else {
                  showToast(context, "you follow" + user.name)
                  Firebase.firestore.collection(FOLLOWEDUSER + Firebase.auth.currentUser!!.uid)
                      .document().set(userList.get(position))
                      .addOnSuccessListener {

                          holder.binding.followUnfollowBtn.text = "Unfollow"
                          isFollow = true
                      }
              }
          }catch (e:Exception){}



        }


    }
}