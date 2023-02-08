package com.picassos.betamax.android.presentation.television.tvchannel.tvchannels

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.picassos.betamax.android.R
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.picassos.betamax.android.domain.model.TvChannels
import com.picassos.betamax.android.domain.listener.OnTvChannelClickListener

class TelevisionTvChannelsAdapter(private val listener: OnTvChannelClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
     internal class TvChannelsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container: LinearLayout = itemView.findViewById(R.id.tvchannel_container)
        val thumbnail: SimpleDraweeView = itemView.findViewById(R.id.tvchannel_thumbnail)
        private val order: TextView = itemView.findViewById(R.id.tvchannel_order)
        val title: TextView = itemView.findViewById(R.id.tvchannel_title)

        @SuppressLint("SetTextI18n")
        fun setData(data: TvChannels.TvChannel) {
            thumbnail.controller = Fresco.newDraweeControllerBuilder()
                .setTapToRetryEnabled(true)
                .setUri(data.banner)
                .build()
            order.text = (absoluteAdapterPosition + 1).toString()
            title.text = data.title
        }

        fun bind(item: TvChannels.TvChannel, listener: OnTvChannelClickListener) {
            itemView.setOnClickListener {  listener.onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_television_tvchannel, parent, false)
        return TvChannelsHolder(view)
    }

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<TvChannels.TvChannel>() {
        override fun areItemsTheSame(oldItem: TvChannels.TvChannel, newItem: TvChannels.TvChannel): Boolean {
           return oldItem.id == newItem.id
               && oldItem.title == newItem.title
               && oldItem.banner == newItem.banner
        }

        override fun areContentsTheSame(oldItem: TvChannels.TvChannel, newItem: TvChannels.TvChannel): Boolean {
            return oldItem.id == newItem.id
                && oldItem.title == newItem.title
                && oldItem.banner == newItem.banner
        }
    })

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvChannels = differ.currentList[position]
        val tvChannelsHolder = holder as TvChannelsHolder
        tvChannelsHolder.setData(tvChannels)
        tvChannelsHolder.bind(tvChannels, listener)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}