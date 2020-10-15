package app.le.chatsapp.ui.main

data class Message(
    val text: String,
    val textTo: String,
    val timeStamp: Long,
    val status: Status
)