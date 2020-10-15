package app.le.chatsapp.ui.main

import java.io.Serializable

interface MessageReceiver {
    fun onMessageReceived(message: Message)
}
