package com.nordsecurity.test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nordsecurity.test.databinding.ServerListItemBinding


class ServerListAdapter : RecyclerView.Adapter<ServerListAdapter.ListViewHolder>() {

    var list = ArrayList<ServersModelItem>()

    fun setContentList(list: ArrayList<ServersModelItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ListViewHolder(val viewHolder: ServerListItemBinding) :
        RecyclerView.ViewHolder(viewHolder.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ServerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.viewHolder.serverList = this.list[position]
    }

    override fun getItemCount(): Int {
        return this.list.size
    }
}