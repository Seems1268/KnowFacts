package com.example.knowfacts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.knowfacts.databinding.FactListItemBinding
import com.example.knowfacts.model.Info

class FactsListAdapter(private val facts: List<Info>, private val context: Context) :
    RecyclerView.Adapter<FactsListAdapter.FactsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return FactsViewHolder(FactListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {
        facts.getOrNull(position)?.let { fact ->
            holder.bind(fact)
        }
    }

    override fun getItemCount(): Int = facts.size

    inner class FactsViewHolder(val binding: FactListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fact: Info) {

            with(binding) {
                factTitle.text =
                    if (fact.title.isNullOrEmpty()) context.getString(R.string.no_title) else fact.title
                factDescription.text =
                    if (fact.description.isNullOrEmpty()) context.getString(R.string.no_description) else fact.description

                if (fact.imageHref.isNullOrEmpty()) {
                    factImage.setImageResource(R.drawable.ic_image_24px)
                } else {
                    Glide.with(context)
                        .load(fact.imageHref)
                        .placeholder(R.drawable.loading)
                        .into(factImage)
                }
            }
        }

    }
}