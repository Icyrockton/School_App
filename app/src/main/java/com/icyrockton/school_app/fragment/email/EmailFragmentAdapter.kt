package com.icyrockton.school_app.fragment.email

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.icyrockton.school_app.fragment.email.inbox.EmailInboxFragment
import com.icyrockton.school_app.fragment.email.send.EmailSendFragment

class EmailFragmentAdapter(emailFragment: EmailFragment) : FragmentStateAdapter(emailFragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return  when (position) {
            0 -> EmailInboxFragment()
            1 -> EmailSendFragment()
            else -> EmailInboxFragment()
        }
    }
}