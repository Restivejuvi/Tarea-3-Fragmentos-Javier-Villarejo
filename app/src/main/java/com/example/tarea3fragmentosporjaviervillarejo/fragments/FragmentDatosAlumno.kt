package com.example.tarea3fragmentosporjaviervillarejo.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tarea3fragmentosporjaviervillarejo.R
import com.example.tarea3fragmentosporjaviervillarejo.databases.DataRepository
import com.example.tarea3fragmentosporjaviervillarejo.model.alumno

class FragmentDatosAlumno : Fragment(){

    var textViewNombre: TextView? = null
    var textViewApellido: TextView? = null
    var thiscontext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_datos_alumno, container, false)
        val idAlumno = arguments?.getString("idAlumno")?.toInt()
        thiscontext = container?.getContext();

        var dataRepository = thiscontext?.let { DataRepository(it) }
        var alumnosGuardados = idAlumno?.let { dataRepository?.GetAlumnoOne(it.toInt()) }
        textViewNombre = v.findViewById<View>(R.id.textViewDatoNombre) as TextView
        textViewApellido = v.findViewById<View>(R.id.textViewDatoApellido) as TextView

        if (alumnosGuardados != null) {
            textViewNombre!!.text = alumnosGuardados.get(0).nombre
            textViewApellido!!.text = alumnosGuardados.get(0).apellido
        }
        return v
    }

    fun updateData(item: alumno?) {
        if (item!=null) {
            textViewNombre!!.text = item.nombre
            textViewApellido!!.text = item.apellido
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentDatosAlumno().apply {
            }
    }
}