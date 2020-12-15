package com.example.tarea3fragmentosporjaviervillarejo.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tarea3fragmentosporjaviervillarejo.R
import com.example.tarea3fragmentosporjaviervillarejo.adapters.ItemAdapterAlumno
import com.example.tarea3fragmentosporjaviervillarejo.databases.DataRepository
import com.example.tarea3fragmentosporjaviervillarejo.model.alumno

class FragmentListaAlumno : Fragment() {

    var activityListener: View.OnClickListener? = null
    var itemSeleccionado: alumno? = null
    var thiscontext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_list_alumno, container, false)
        val asignatura = arguments!!.getString("asignatura")
        val recyclerViewLista: RecyclerView =
            v.findViewById<View>(R.id.recyclerviewlista) as RecyclerView
        thiscontext = container?.getContext();

        var dataRepository = DataRepository(thiscontext!!)
        var numeroAsignatura: Int
        if (asignatura.equals("BBDD")) {
            numeroAsignatura = 2
        } else {
            numeroAsignatura = 1
        }

        var alumnosGuardados = dataRepository.getAlumnos(numeroAsignatura)
        var alumno = alumnosGuardados.component1().alumnos
        var items = ArrayList<alumno>()
        for (i in 0..alumno.size-1) {
            items.add(alumno(alumno.get(i).alumnosId.toInt(),alumno.get(i).nombre.toString(), alumno.get(i).apellido.toString()))
        }

        val adapter = ItemAdapterAlumno(items) { item ->
            itemSeleccionado = item
            if (activityListener != null) {
                activityListener!!.onClick(view)
            }
        }
        recyclerViewLista.setAdapter(adapter)
        recyclerViewLista.setLayoutManager(
            LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL,false)
        )
        return v
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentListaAlumno().apply {}
    }
}