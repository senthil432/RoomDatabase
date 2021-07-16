package com.example.app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_item_view.view.*

class UserAdapter(val context: Context,
                  private val userList: List<User>?,
                  private val click: ItemClicked
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.user_item_view, parent, false)
        return ViewHolder(v)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemCount(): Int = userList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindUsers(userList!![position])

        holder.itemView.imageDelete.setOnClickListener {
            MainActivity.userData = userList[position]
            click.itemClick(true, "Delete")
        }

        holder.itemView.imageUpdate.setOnClickListener {
            MainActivity.userData = userList[position]
            click.itemClick(true, "Update")
        }

        holder.itemView.imageView.setOnClickListener {
            MainActivity.userData = userList[position]
            click.itemClick(true, "View")
        }
    }

    interface ItemClicked {
        fun itemClick(flag: Boolean?, value: String)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBindUsers(user: User) {
            itemView.apply {
                userName.text = user.userName
                userNumber.text = user.userNumber
                userEmail.text = user.userEmail
            }
        }
    }
}