package com.example.lesson06recuclerviewdiffutils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson06recuclerviewdiffutils.databinding.ActivityMainBinding
import com.example.lesson06recuclerviewdiffutils.databinding.ItemBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

//        val items = mutableListOf<String>()
        val items = List(7) {
            "Item $it"
        }

        val adapter = ItemAdapter { item ->
            Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
        }

        with(binding) {

            button.setOnClickListener {
//                items.add("Item ${items.size}")
                adapter.submitList(items.shuffled())
//                adapter.notifyDataSetChanged()
            }
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = adapter
        }
    }
}

//class ItemDiffCallback(
//    private val oldItems: List<String>,
//    private val newItems: List<String>
//) : DiffUtil.Callback() {
//    override fun getOldListSize(): Int {
//        return oldItems.size
//    }
//
//    override fun getNewListSize(): Int {
//        return newItems.size
//    }
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldItems[oldItemPosition] == newItems[newItemPosition]
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldItems[oldItemPosition] == newItems[newItemPosition]
//    }
//}

class ItemViewHolder(
    private val binding: ItemBinding,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String) {
        binding.textView.text = item
        binding.root.setOnClickListener {
            onItemClicked(item)
        }
    }
}

class ItemAdapter(
    private val onItemClicked: (String) -> Unit
) : ListAdapter<String, ItemViewHolder>(DIFF_CALLBACK) {

//    private val list = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

//    override fun getItemCount(): Int = list.size

//    fun setNewList(newList: List<String>) {
//
//        val callback = ItemDiffCallback(list.toList(), newList)
//        list.clear()
//        list.addAll(newList)
//        DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
//    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}