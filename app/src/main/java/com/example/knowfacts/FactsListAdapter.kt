package com.example.knowfacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knowfacts.databinding.FactListItemBinding
import com.example.knowfacts.model.Info

class FactsListAdapter(private val facts: List<Info>) :
    RecyclerView.Adapter<FactsListAdapter.FactsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return FactsViewHolder(FactListItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {
        facts.getOrNull(position)?.let { fact ->
            holder.bind(fact, position)
        }
    }

    override fun getItemCount(): Int = facts.size

    class FactsViewHolder(val binding: FactListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fact: Info, position: Int) {

            with(binding) {
                binding.factTitle.text = fact.title
            }
        }

    }
}