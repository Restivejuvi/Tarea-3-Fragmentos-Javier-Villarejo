package com.example.tarea3fragmentosporjaviervillarejo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea3fragmentosporjaviervillarejo.R
import com.example.tarea3fragmentosporjaviervillarejo.model.alumno

class ItemAdapterAlumno(var items: ArrayList<alumno>, private val listener: (alumno) -> Unit) : RecyclerView.Adapter<ItemAdapterAlumno.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapterAlumno.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        val viewHolder = ViewHolder(v)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ItemAdapterAlumno.ViewHolder, position: Int) {
        holder.bindItems(items[position])
        holder.itemView.setOnClickListener { listener(items[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(cliente: alumno) {

            val textViewNombre = itemView.findViewById<TextView>(R.id.textViewNombre)
            textViewNombre.text = cliente.nombre + " " + cliente.apellido
        }
    }
}