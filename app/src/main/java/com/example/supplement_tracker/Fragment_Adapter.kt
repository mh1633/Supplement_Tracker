package com.example.supplement_tracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class Fragment_Adapter(fragmentActivity: FragmentActivity) :FragmentStateAdapter(fragmentActivity) {

    var fragment_List = listOf<Fragment>()

// 프래그먼트 갯수를 가져오기 위한 필수 오버라이드 함수
    override fun getItemCount(): Int {
        return fragment_List.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragment_List.get(position)
    }

}