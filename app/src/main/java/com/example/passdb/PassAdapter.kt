package com.example.passdb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.passdb.DB.PassItem
import com.example.passdb.databinding.ActivityPassItemBinding

class PassAdapter(
    val onClickItem: (PassItem) -> Unit,
    val onDeleteClickItem: (PassItem) -> Unit,
    val onChangeItem: (PassItem) -> Unit
): RecyclerView.Adapter<PassAdapter.TodoViewHolder>()  {

    var items = emptyList<PassItem>()

    class TodoViewHolder(val binding: ActivityPassItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ActivityPassItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        var item = items[position]
        holder.binding.tvSite.text = item.name
        //holder.binding.tvLogin.text = item.login
        //holder.binding.tvPassword.text = item.password

        holder.binding.root.setOnClickListener {onClickItem(item)}
        holder.binding.ivDelete.setOnClickListener {onDeleteClickItem(item)}
        holder.binding.ivChange.setOnClickListener {onChangeItem(item)}
    }

    fun update(items: List<PassItem>){
        this.items = items
        notifyDataSetChanged()
    }
}