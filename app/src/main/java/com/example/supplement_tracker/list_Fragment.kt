package com.example.supplement_tracker

import android.content.Intent
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

    val helper by lazy { SQLiteHelper(requireContext(), "Item_Database", 1) }
    var checked_item = mutableSetOf<Long?>()
    var itemRecord = mutableMapOf<Long, ITEM_RECORD>()
    var allItem_checked_cancle = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var binding = FragmentListBinding.inflate(inflater, container, false)
        val adapter = RecyclerViewAdapter()
        helper.let { adapter.itemList.addAll(it.select_AllItem_List()) }

        with(binding) {
            RecyclerView.adapter = adapter
            RecyclerView.layoutManager = LinearLayoutManager(activity)

            /* ============= 체크버튼 누르면 아이템레코드 저장 ============== */
            checkBtn.setOnClickListener {
                for ((i,j) in itemRecord) {
                    helper.insert_Item_Record(helper.writableDatabase ,j)
                }
                allItem_checked_cancle = true
                adapter.notifyDataSetChanged()

            }

            /* =============== list_add 액티비티 띄우기 ================ */
            addBtn.setOnClickListener {
                val intent = Intent(context, list_add::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }

    /* ================ 카드뷰 xml을 리사이클러 뷰 아이템으로 할당 ===================== */

    inner class RecyclerViewAdapter() : RecyclerView.Adapter<Holder>() {
        val itemList = mutableListOf<ITEM_LIST>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            Log.d(logmsg,"onCreateViewHolder")
            return Holder(RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            Log.d(logmsg,"onBindViewHolder")
            val item_List = itemList.get(position)
            holder.set_Item_List(item_List)
            val binding = holder.binding

            with(binding) {
                /* ============ 아이템 알람 켜고 끄기 ================== */
                ItemAlarm.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) item_List.Alarm_Enable = 1
                    else item_List.Alarm_Enable = 0
                    helper.update_Item_list(item_List)
                }

                /* ============= 아이템 체크박스 버튼으로 체크된 아이템 checked_item에 넘기기 ============ */
                ItemSelectedChkbox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        var record = ITEM_RECORD(
                            null,
                            item_List.ID_Num,
                            item_List.Name,
                            null)
                        itemRecord[item_List.ID_Num] = record
                    }
                    else itemRecord.remove(item_List.ID_Num)
                }
            }
        }

        override fun getItemCount(): Int {
            return itemList.size
        }
    }

    inner class Holder(val binding: RecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun set_Item_List(item_List: ITEM_LIST) {
            Log.d(logmsg,"set_Item_List")
            binding.ItemName.text = item_List.Name
            binding.ItemDecsription.text = item_List.Item_desc
            binding.ItemAlarm.isChecked = item_List.Alarm_Enable == 1
            if(allItem_checked_cancle){
                binding.ItemSelectedChkbox.isChecked = false
            }
        }
    }
}