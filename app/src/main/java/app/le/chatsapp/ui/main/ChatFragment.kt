package app.le.chatsapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.le.chatsapp.FRAGMENT_ME
import app.le.chatsapp.FRAGMENT_YOU
import app.le.chatsapp.R
import java.lang.System.currentTimeMillis

class ChatFragment : Fragment() {
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<Message>()
    lateinit var layout: View
    lateinit var messageReceiver: MessageReceiver
    var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatAdapter = ChatAdapter(messages, index)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layout = inflater.inflate(R.layout.fragment_main, container, false)
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        layout.findViewById<ImageButton>(R.id.btn_send).setOnClickListener { sendMessage() }
        val recyclerView = layout.findViewById<RecyclerView>(R.id.rv_messages)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = chatAdapter
        recyclerView.setItemViewCacheSize(20)
    }

    private fun sendMessage() {
        val editText = layout.findViewById<EditText>(R.id.et_msg)
        val text = editText.text.toString().trim()
        editText.setText("")
        if (text.isNullOrBlank().not()) {
            val message = composeMessage(text)
            messages.add(message)
            messageReceiver.onMessageReceived(message)
            chatAdapter.notifyItemInserted(messages.size - 1)
        }
    }

    private fun composeMessage(message: String): Message =
        Message(message, getReceiverId(), currentTimeMillis(), Status.SENT)

    private fun getReceiverId(): String = if (index == 0) FRAGMENT_YOU else FRAGMENT_ME

    fun pushMessage(message: Message) {
        messages.add(message)
        chatAdapter.notifyItemInserted(messages.size - 1)
    }

    companion object {
        private const val RECEIVER = "receiver"

        @JvmStatic
        fun newInstance(receiver: MessageReceiver, position: Int): ChatFragment {
            val chatFragment = ChatFragment()
            chatFragment.messageReceiver = receiver
            chatFragment.index = position
            return chatFragment
        }
    }
}