package com.example.tarea3fragmentosporjaviervillarejo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea3fragmentosporjaviervillarejo.R
import com.example.tarea3fragmentosporjaviervillarejo.model.profesor

class ItemAdapterProfesor(var items: ArrayList<profesor>, private val listener: (profesor) -> Unit) : RecyclerView.Adapter<ItemAdapterProfesor.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterProfesor.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val viewHolder = ViewHolder(v)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemAdapterProfesor.ViewHolder, position: Int) {
        holder.bindItems(items[position])
        holder.itemView.setOnClickListener { listener(items[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(cliente: profesor) {
            val textViewNombre = itemView.findViewById<TextView>(R.id.textViewNombre)
            textViewNombre.text = cliente.nombre + " " + cliente.apellido
        }
    }
}