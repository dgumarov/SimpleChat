package com.example.simplechat.ui.main

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simplechat.R
import com.example.simplechat.entity.Message
import com.example.simplechat.utils.Preferences
import com.example.simplechat.utils.convertLongToTime
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.java.KoinJavaComponent.inject

class MessagesAdapter: RecyclerView.Adapter<MessageVH>(), KoinComponent {

    private val messageList: MutableList<Message> = mutableListOf()
    private val preferences: Preferences by inject()

    fun updateList(newList: List<Message>)
    {
        messageList.clear()
        messageList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageVH {

        val view = if (viewType == 1)
            LayoutInflater.from(parent.context).inflate(R.layout.li_message, parent, false)
        else
            LayoutInflater.from(parent.context).inflate(R.layout.li_my_message, parent, false)
        return MessageVH(view)
    }

    override fun onBindViewHolder(holder: MessageVH, position: Int) {
        holder.bind(messageList[position])
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (messageList[position].userUid == preferences.getUid().toString())
            return 0

        return 1
    }
}

class MessageVH(itemView: View): RecyclerView.ViewHolder(itemView){

    private val userNameTextView = itemView.findViewById<TextView>(R.id.userName)
    private val messageTextView = itemView.findViewById<TextView>(R.id.message)

    fun bind(message: Message)
    {
        val userAndTime = "${message.userName}  [${convertLongToTime(message.time)}]"
        userNameTextView.text = userAndTime
        messageTextView.text = message.messageText
    }
}