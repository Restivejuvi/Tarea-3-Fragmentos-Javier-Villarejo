package com.example.tarea3fragmentosporjaviervillarejo.activities

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tarea3fragmentosporjaviervillarejo.R
import com.example.tarea3fragmentosporjaviervillarejo.fragments.FragmentDatosAlumno

class AlumnoActivity : AppCompatActivity() {

    var frameLayoutData: FrameLayout? = null
    var dataFragmentDatosAlumno: FragmentDatosAlumno? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_alumno)

        val messeage = intent.getStringExtra("idAlumno")
        Toast.makeText(this,messeage, Toast.LENGTH_SHORT).show()

        val idAlumno = Bundle()
        idAlumno.putString("idAlumno", messeage)

        frameLayoutData = findViewById(R.id.frameLayoutFragment)
        dataFragmentDatosAlumno = FragmentDatosAlumno()
        dataFragmentDatosAlumno!!.setArguments(idAlumno)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frameLayoutFragment, dataFragmentDatosAlumno!!)
        fragmentTransaction.commit()
    }
}