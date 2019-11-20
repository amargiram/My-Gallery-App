package com.example.mytask.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mytask.activity.FullScreenActivity
import com.example.mytask.R
import com.example.mytask.model.Images
import com.squareup.picasso.Picasso

import kotlin.collections.ArrayList

    class ImageAdapter(var context: Context,var imageList: ArrayList<Images>) : androidx.recyclerview.widget.RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)

            return ImageAdapter.ViewHolder(view)
        }


        override fun onBindViewHolder(Viewholder: ImageAdapter.ViewHolder, position: Int) {

            val dataAdapterOBJ = imageList.get(position)


            // setting image title
            Viewholder.tvImageTitle.setText(dataAdapterOBJ.title)
            // loading image
            Picasso.get().load(dataAdapterOBJ.url).into(Viewholder.imageView)

            Viewholder.root.setOnClickListener {


         // sending data through intent

                val i = Intent(context, FullScreenActivity::class.java)
                i.putExtra("IMAGE_URL",imageList[position].url)
                i.putExtra("TITLE",imageList[position].title)

                this.context.startActivity(i)


            }

        }

        override fun getItemCount(): Int {
            return imageList.size
        }


        class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
        {
            var imageView: ImageView
            var tvImageTitle:TextView
            var root: RelativeLayout

            init {
                imageView = itemView.findViewById(R.id.image)
                tvImageTitle = itemView.findViewById(R.id.tvImageTitle)
                root=itemView.findViewById(R.id.root)

            }



        }


    }
