package com.pixelart.githubuserscoroutine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pixelart.githubuserscoroutine.R
import com.pixelart.githubuserscoroutine.model.User

class UsersAdapter(private val listener: OnItemClickedListener):ListAdapter<User, UsersAdapter.ViewHolder>(diffCallback) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.apply {
            setContent(user)
            itemView.setOnClickListener { listener.onItemClicked(position) }
        }
    }
    
    class ViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        private val login: TextView = view.findViewById(R.id.tvLogin)
        private val avatar: ImageView = view.findViewById(R.id.ivAvatar)
        
        fun setContent(user: User){
            login.text = user.login
            
            Glide.with(view.context)
                .load(user.avatarUrl)
                .into(avatar)
        }
    }
    
    companion object{
        val diffCallback: DiffUtil.ItemCallback<User> = object : DiffUtil.ItemCallback<User>(){
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }
    
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
    
    interface OnItemClickedListener{
        fun onItemClicked(position: Int)
    }
}