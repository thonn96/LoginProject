package nexlsoft.loginsample.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import nexlsoft.loginsample.R
import nexlsoft.loginsample.data.repository.model.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var listCategory: MutableList<Category> = mutableListOf()

    fun updateCategory(categories: MutableList<Category>) {
        categories.let {
            this.listCategory.clear()
            this.listCategory.addAll(categories)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.onBind(listCategory[position])
    }

    override fun getItemCount() = listCategory.size

    inner class CategoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val textName = itemView.findViewById<TextView>(R.id.text_name)
        private val cardView = itemView.findViewById<CardView>(R.id.card_view)
        private var isClick = false
        fun onBind(category: Category) {

            itemView.run {
                textName.text = category.name
                cardView.setBackgroundResource(R.drawable.layout_black)
                isClick = true
            }

            itemView.setOnClickListener {
                isClick = if (isClick) {
                    cardView.setBackgroundResource(R.drawable.layout_purple)
                    false
                } else {
                    cardView.setBackgroundResource(R.drawable.layout_black)
                    true
                }
            }
        }
    }
}
