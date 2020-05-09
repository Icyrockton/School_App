package com.icyrockton.school_app.fragment.score

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.icyrockton.school_app.fragment.score.cet.CetScoreFragment
import com.icyrockton.school_app.fragment.score.daily.DailyScoreFragment
import com.icyrockton.school_app.fragment.score.overview.ScoreOverViewFragment
import kotlin.math.log

class ScoreFragmentAdapter(scoreFragment: ScoreFragment) : FragmentStateAdapter(scoreFragment) {
    companion object {
        private const val TAG = "ScoreFragmentAdapter"
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment: 创建${position}")
        return when (position) {
            0 -> ScoreOverViewFragment()
            1 -> DailyScoreFragment()
            2-> CetScoreFragment()
            else -> ScoreOverViewFragment()
        }
    }

}