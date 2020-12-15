package com.example.tarea3fragmentosporjaviervillarejo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.tarea3fragmentosporjaviervillarejo.activities.AlumnoActivity
import com.example.tarea3fragmentosporjaviervillarejo.databases.*
import com.example.tarea3fragmentosporjaviervillarejo.fragments.FragmentDatosAlumno
import com.example.tarea3fragmentosporjaviervillarejo.fragments.FragmentListaAlumno
import com.example.tarea3fragmentosporjaviervillarejo.fragments.FragmentListaProfesor
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    var frameLayoutFragmentProfesor: FrameLayout? = null
    var frameLayoutFragmentAlumnos: FrameLayout? = null
    var frameLayoutFragmentData: FrameLayout? = null

    var listaFragmentProfesorListaProfesor: FragmentListaProfesor? = null
    var listaFragmentAlumnoListaAlumno: FragmentListaAlumno? = null
    var dataFragmentDatosAlumno: FragmentDatosAlumno? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        completarLista()
        spinnerActivity()
    }

    private fun completarLista() {

        var dataRepository = DataRepository(this)
        var bufferedReaderRecurso = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.datos)))
        var textoLeido = bufferedReaderRecurso.readLine()
        val todo = StringBuilder()

        while (textoLeido != null) {
            todo.append(textoLeido + "\n")
            textoLeido = bufferedReaderRecurso.readLine()
        }
        textoLeido = todo.toString()
        bufferedReaderRecurso.close()

        val jsonObject = JSONObject(textoLeido)
        val jsonArray = jsonObject.optJSONArray("asignaturas")
        var listaAlumnos = ArrayList<Alumnos>()
        var listaProfesores = ArrayList<Profesores>()

        for (i in 0 until jsonArray.length()) {
            listaAlumnos.clear()
            listaProfesores.clear()

            var asignatura = Asignaturas(i + 1, jsonArray.get(i).toString())
            val asignaturaTex = jsonArray.get(i).toString()
            val jsonArrayProf = jsonObject.optJSONArray("profesores")

            for (i in 0 until jsonArrayProf.length()) {
                val objeto = jsonArrayProf.getJSONObject(i)

                if (objeto.optString("asignatura").equals(asignaturaTex)) {
                    var codigoProfesor = objeto.optString("codigo")
                    var nombreProfesor = objeto.optString("nombre")
                    var apellidoProfesor = objeto.optString("apellido")
                    var profesor = Profesores(codigoProfesor.toString().toInt(), nombreProfesor.toString(), apellidoProfesor.toString())
                    listaProfesores.add(profesor)
                }
            }
            val jsonArrayAlumno = jsonObject.optJSONArray("alumnos")

            for (i in 0 until jsonArrayAlumno.length()) {
                val objeto = jsonArrayAlumno.getJSONObject(i)
                val jsonArrayAsignaturas = objeto.optJSONArray("Asignaturas")

                for (i in 0 until jsonArrayAsignaturas.length()) {
                    val objetoAsignatura = jsonArrayAsignaturas[i].toString()

                    if (objetoAsignatura.equals(asignaturaTex)) {
                        var codigoAlumno = objeto.optString("codigo")
                        var nombreAlumno = objeto.optString("nombre")
                        var apellidoAlumno = objeto.optString("apellido")
                        var alumno = Alumnos(codigoAlumno.toString().toInt(), nombreAlumno.toString(), apellidoAlumno.toString())
                        listaAlumnos.add(alumno)
                    }
                }
            }

            var asignaturasAlumnos = AsignaturasAlumnos(asignatura, listaAlumnos)
            var asignaturasProfesores = AsignaturasProfesores(asignatura, listaProfesores)
            dataRepository.insertAsignaturasAlumnos(asignaturasAlumnos)
            dataRepository.insertAsignaturasProfesores(asignaturasProfesores)
        }
    }

    private fun verProfesores(asignatura: String){
        if (!asignatura.equals("Seleccione uno:")) {
            val asignaturaNombre = Bundle()
            asignaturaNombre.putString("asignatura", asignatura)

            frameLayoutFragmentProfesor = findViewById(R.id.frameLayoutProfesor)
            frameLayoutFragmentAlumnos = findViewById(R.id.frameLayoutAlumno)
            frameLayoutFragmentData = findViewById(R.id.frameLayoutData)

            frameLayoutFragmentProfesor?.removeAllViewsInLayout()
            frameLayoutFragmentAlumnos?.removeAllViewsInLayout()
            frameLayoutFragmentData?.removeAllViewsInLayout()

            listaFragmentProfesorListaProfesor = FragmentListaProfesor.newInstance()
            listaFragmentAlumnoListaAlumno = FragmentListaAlumno.newInstance()
            listaFragmentAlumnoListaAlumno!!.activityListener = activityListener

            listaFragmentProfesorListaProfesor!!.setArguments(asignaturaNombre)
            listaFragmentAlumnoListaAlumno!!.setArguments(asignaturaNombre)

            dataFragmentDatosAlumno = FragmentDatosAlumno()

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            if (frameLayoutFragmentData !=null){
                fragmentTransaction.add(R.id.frameLayoutProfesor, listaFragmentProfesorListaProfesor!!)
                fragmentTransaction.add(R.id.frameLayoutAlumno, listaFragmentAlumnoListaAlumno!!)
                fragmentTransaction.add(R.id.frameLayoutData, dataFragmentDatosAlumno!!)
            }
            else {
                fragmentTransaction.add(R.id.frameLayoutProfesor, listaFragmentProfesorListaProfesor!!)
                fragmentTransaction.add(R.id.frameLayoutAlumno, listaFragmentAlumnoListaAlumno!!)
            }
            fragmentTransaction.commit()
        }
    }

    var activityListener = View.OnClickListener {
        if (frameLayoutFragmentData!=null) {
            dataFragmentDatosAlumno!!.updateData(listaFragmentAlumnoListaAlumno!!.itemSeleccionado)
        }else{
            val intent = Intent(this, AlumnoActivity::class.java).apply {
                putExtra("idAlumno", listaFragmentAlumnoListaAlumno!!.itemSeleccionado?.id.toString())
            }
            startActivity(intent)
        }
    }

    private fun spinnerActivity(){
        var dataRepository = DataRepository(this)
        val spinner = findViewById<Spinner>(R.id.spinner)
        var pedidosGuardados = dataRepository.getAsignaturas()
        var ArraySpinner = ArrayList<String>()
        ArraySpinner.add("Seleccione uno:")

        for (items in pedidosGuardados) {
            ArraySpinner.add(items.nombre.toString())
        }
        spinner.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArraySpinner)
        if (spinner != null) {
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(this@MainActivity, spinner.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                    verProfesores(spinner.selectedItem.toString())
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }
}