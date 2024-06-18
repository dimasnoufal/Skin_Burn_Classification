//package com.dimasnoufal.skinburnclassification.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.dimasnoufal.skinburnclassification.R
//import com.dimasnoufal.skinburnclassification.databinding.ItemHistoryBinding
//import com.dimasnoufal.skinburnclassification.entity.History
//
//class HistoryAdapter(private val onItemClickCallback: OnItemClickCallback) :
//    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
//    var listHistory = ArrayList<History>()
//        set(listNotes) {
//            if (listNotes.size > 0) {
//                this.listHistory.clear()
//            }
//            this.listHistory.addAll(listNotes)
//        }
//
//    fun addItem(history: History) {
//        this.listHistory.add(history)
//        notifyItemInserted(this.listHistory.size - 1)
//    }
//
//    fun updateItem(position: Int, history: History) {
//        this.listHistory[position] = history
//        notifyItemChanged(position, history)
//    }
//
//    fun removeItem(position: Int) {
//        this.listHistory.removeAt(position)
//        notifyItemRemoved(position)
//        notifyItemRangeChanged(position, this.listHistory.size)
//    }
//
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): HistoryAdapter.HistoryViewHolder {
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
//        return HistoryViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
//        holder.bind(listHistory[position])
//    }
//
//    override fun getItemCount(): Int {
//        return this.listHistory.size
//    }
//
//    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val binding = ItemHistoryBinding.bind(itemView)
//        fun bind(history: History) {
//            binding.tvItemTitle.text = history.title
//            binding.tvItemDate.text = history.date
//            binding.tvItemDescription.text = history.description
//            binding.cvItemNote.setOnClickListener {
//                onItemClickCallback.onItemClicked(history, adapterPosition)
//            }
//        }
//    }
//
//    interface OnItemClickCallback {
//        fun onItemClicked(selectedNote: History?, position: Int?)
//    }
//}
