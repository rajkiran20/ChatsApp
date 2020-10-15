package app.le.chatsapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import app.le.chatsapp.FRAGMENT_ME
import app.le.chatsapp.R

private const val TYPE_SENDER = 1
private const val TYPE_RECEIVER = 2

class ChatAdapter(
    private val messages: List<Message>,
    private val index: Int
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        @LayoutRes var layoutResId = getLayoutResId(viewType)

        val contactView: View = inflater.inflate(
            layoutResId,
            parent,
            false
        )

        return ChatViewHolder(contactView)
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.tvMessage.text = messages[position].text
        holder.tvTimestamp.text = messages[position].timeStamp.toString()
        holder.tvStatus.text = messages[position].status.name
    }

    override fun getItemViewType(position: Int): Int =
        if (messages[position].textTo == FRAGMENT_ME) TYPE_SENDER else TYPE_RECEIVER

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvMessage: TextView = itemView.findViewById(R.id.tv_msg)
        var tvTimestamp: TextView = itemView.findViewById(R.id.tv_timestamp)
        var tvStatus: TextView = itemView.findViewById(R.id.tv_status)
    }

    private fun getLayoutResId(viewType: Int): Int {
        return if (viewType == TYPE_RECEIVER) {
            if (index == 0) {
                R.layout.chat_row_right
            } else {
                R.layout.chat_row_left
            }
        } else {
            if (index == 0) {
                R.layout.chat_row_left
            } else {
                R.layout.chat_row_right
            }
        }
    }
}