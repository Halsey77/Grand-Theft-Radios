package com.halsey.grandtheftradios.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.halsey.grandtheftradios.R


class CarouselAdapter(var context: Context, var arrayList: ArrayList<Int>) :
    RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.carousel_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(arrayList[position]).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView

        init {
            imageView = itemView.findViewById<ImageView>(R.id.carousel_item_image)
        }
    }
}