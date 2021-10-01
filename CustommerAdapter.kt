package com.example.roomdemo2
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import com.example.roomdemo2.R
import kotlinx.android.synthetic.main.row_custommer.view.*


class CustommerAdapter : RecyclerView.Adapter<CustommerAdapter.ItemHolder> {

    companion object {
        var clickListener: ClickListener? = null
    }

    var arrayList: ArrayList<Custommer>

    constructor(list: ArrayList<Custommer>) : super() {
        this.arrayList = list
    }

    class ItemHolder : RecyclerView.ViewHolder, View.OnClickListener{

        var textView_name: TextView
        var textView_age: TextView

        constructor(itemView: View) : super(itemView) {
            textView_name = itemView.textView_name
            textView_age = itemView.textView_age

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            clickListener!!.onItemClick(adapterPosition, v)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        var v: View = LayoutInflater.from(parent.context).inflate(R.layout.row_custommer, parent, false)
        var itemHolder = ItemHolder(v)

        return itemHolder
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var item = arrayList.get(holder.adapterPosition)
        holder.textView_name.text = item.cusName
        holder.textView_age.text = item.cusAge.toString()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    interface ClickListener {
        fun onItemClick(position: Int, v: View)
    }

    fun setOnItemClickListener(clickListener: ClickListener?) {
        CustommerAdapter.clickListener = clickListener!!
    }

}
