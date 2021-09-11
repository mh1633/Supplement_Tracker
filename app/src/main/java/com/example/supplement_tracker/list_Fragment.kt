package com.example.supplement_tracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.supplement_tracker.databinding.FragmentListBinding
import com.example.supplement_tracker.databinding.RecyclerviewItemBinding


class list_Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var binding = FragmentListBinding.inflate(inflater, container, false)
        Log.d(logmsg,"onCreateView 호출")
        binding.RecyclerView.adapter = RecyclerViewAdapter()
        binding.RecyclerView.layoutManager = LinearLayoutManager(activity)
        return binding.root
    }
}

/* 카드뷰 xml을 리사이클러 뷰 아이템으로 할당 */
class RecyclerViewAdapter: RecyclerView.Adapter<Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        Log.d(logmsg,"onCreateViewHolder 호출")
        return Holder(RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        Log.d(logmsg,"onBindViewHolder 호출")
        val binding = (holder as Holder).binding
        with(binding){
            //
            //
            //
        }
    }

    override fun getItemCount(): Int {
        Log.d(logmsg,"getItemCount 호출")
        return 6
    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val binding = (holder as ViewHolder).binding
//
//    }

}

class Holder(val binding: RecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root){
}
