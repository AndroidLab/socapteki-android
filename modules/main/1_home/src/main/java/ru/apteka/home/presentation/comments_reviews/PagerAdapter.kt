package ru.apteka.home.presentation.comments_reviews

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Представляет страницы комментариев и отзывов.
 */
class PagerAdapter(
    activity: FragmentActivity,
    private val fragments: ArrayList<Fragment>
) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}