package app.le.chatsapp

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import app.le.chatsapp.ui.main.ChatFragment
import app.le.chatsapp.ui.main.Message
import app.le.chatsapp.ui.main.MessageReceiver
import app.le.chatsapp.ui.main.SectionsPagerAdapter

const val FRAGMENT_ME = ":0"
const val FRAGMENT_YOU = ":1"

class MainActivity : AppCompatActivity(), MessageReceiver {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, this)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    override fun onMessageReceived(message: Message) {
        receiverFragment(message.textTo).pushMessage(message)
    }

    private fun receiverFragment(textTo: String): ChatFragment {
        return supportFragmentManager.fragments[if(textTo.endsWith(FRAGMENT_ME)) 0 else 1] as ChatFragment
    }
}