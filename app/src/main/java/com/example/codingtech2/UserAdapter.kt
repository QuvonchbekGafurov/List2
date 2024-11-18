package com.example.codingtech2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserAdapter(
    private val users: MutableList<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        private val tvUserTime: TextView = itemView.findViewById(R.id.tvUserTime)

        fun bind(user: User) {
            tvUserName.text = user.name
            tvUserTime.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(user.time * 1000))
            itemView.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun removeUserById(id: Int) {
        val index = users.indexOfFirst { it.id == id }
        if (index != -1) {
            users.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun updateUserById(id: Int, newName: String) {
        val index = users.indexOfFirst { it.id == id }
        if (index != -1) {
            users[index] = users[index].copy(name = newName)
            notifyItemChanged(index)
        }
    }
}
