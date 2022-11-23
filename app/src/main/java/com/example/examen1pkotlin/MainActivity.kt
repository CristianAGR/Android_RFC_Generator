package com.example.examen1pkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logicaSpinner()
        Fecha.setOnClickListener { showDatePicketDialog() }
        btnRegistrar.setOnClickListener { generarCurp() }
        btnLimpiar.setOnClickListener{limpiarCampos()}
    }

    // Crear spinner
    private fun logicaSpinner() {
        var estado = ""
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,lstEstados)
        spEstados.adapter = arrayAdapter
        spEstados.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // another interface callback
            }
        }
    }

    private fun showDatePicketDialog() {
        val vDatePicker = DatePicketFragment { day, month, year ->
            onDateSelected(day, month, year)
        }
        vDatePicker.show(supportFragmentManager, "Selector de fecha")
    }

    fun onDateSelected(vDay: Int, vMonth: Int, vYear: Int) {
        var vResultado =
            String.format("%02d", vDay) + "/" + String.format("%02d", vMonth + 1) + "/" + vYear
        Fecha.setText(vResultado)
    }

    private fun limpiarCampos() {
        Nombre.setText("")
        Ap.setText("")
        Am.setText("")
        Fecha.setText("")
    }
    // funcion para generar la curp
    private fun generarCurp() {
        if (validador() == false) {
            var vPrimeraLetraNombre = Nombre.text?.subSequence(0, 1)
            var vPrimeraLetraAP = Ap.text?.subSequence(0, 1)
            var vPrimeraLetraAM = Am.text?.subSequence(0, 1)
            var vPrimeraVocalAP = getVocal(Ap.text.toString())
            var vAño = Fecha.text?.subSequence(8, 10)
            var vMes = Fecha.text?.subSequence(3, 5)
            var vDia = Fecha.text?.subSequence(0, 2)
            var vSexo = getSexo()
            var vEstado = determinarClaveEstado(spEstados.selectedItem.toString())
            var vConsonanteAP = getConsonante(Ap.text.toString())
            var vConsonanteAM = getConsonante(Am.text.toString())
            var vConsonanteNombre = getConsonante(Nombre.text.toString())
            var vCaracterDif = getCaracterDiferenciador()
            txtRFC.text =
                "Tu RFC es: $vPrimeraLetraAP$vPrimeraVocalAP$vPrimeraLetraAM$vPrimeraLetraNombre$vAño$vMes$vDia$vSexo$vEstado$vConsonanteAP$vConsonanteAM$vConsonanteNombre$vCaracterDif".uppercase()
        }

    }

    // valida que no esten vacios los campos
    fun validador(): Boolean {
        //var TextInputLayoutName = findViewById<TextInputLayout>(R.id.Nombre)
        //var TextInputLayoutSecondName = findViewById<TextInputLayout>(R.id.Apellido)
        var validadorEntro = false
        if (TextUtils.isEmpty(Nombre.text.toString())) {
            lytNombre.isErrorEnabled = true
            lytNombre.error = "Capture un nombre"
            validadorEntro = true
        } else
            lytNombre.isErrorEnabled = false
        if (TextUtils.isEmpty(Ap.text.toString())) {
            lytAp.isErrorEnabled = true
            lytAp.error = "Capture un apellido Paterno"
            validadorEntro = true
        } else
            lytAp.isErrorEnabled = false
        if (TextUtils.isEmpty(Am.text.toString())) {
            lytAm.isErrorEnabled = true
            lytAm.error = "Capture un apellido Materno"
            validadorEntro = true
        } else
            lytAm.isErrorEnabled = false
        if (TextUtils.isEmpty(Fecha.text.toString())) {
            lytFecha.isErrorEnabled = true
            lytFecha.error = "Capture una fecha de nacimiento"
            validadorEntro = true
        } else
            lytFecha.isErrorEnabled = false
        return  validadorEntro
    }


    fun getCaracterDiferenciador(): Int {
        val Año = Fecha.text?.subSequence(6, 10)
        val Mes = Fecha.text?.subSequence(3, 5)
        val Dia = Fecha.text?.subSequence(0, 2)
        var caracter = 0;
        if (Dia.toString().toInt() <= 31 && Mes.toString().toInt() <= 12 && Año.toString().toInt() <= 1999) {
            caracter = 0
        } else {
            caracter = 1
        }

        return caracter
    }

    // metodo para encontrar la primera vocal de una palabra
    fun getVocal(texto: String): String {
        var vocal = ""
        for (char in texto) {
            if (char.toString() == "a" || char.toString() == "e"
                || char.toString() == "i"
                || char.toString() == "o" || char.toString() == "u"
            ) {
                if (vocal == "") {
                    vocal = char.toString()
                }
            }
        }
        return vocal
    }

    // funcion para obtener la consonante de cualquier string
    fun getConsonante(texto: String): String {
        var consonante = ""
        for (char in texto) {
            if (lstconsonantes.contains(char.toString())
            ){
                if (consonante == "") {
                    consonante = char.toString()
                }
            }
        }
        return consonante
    }

    // funcion para obtener sexo de la persona
    fun getSexo(): String {
        var sx = ""
        if (rdb_H.isChecked) {
            sx = rdb_H.text.toString()
        } else if (rdb_M.isChecked) {
            sx = rdb_M.text.toString()
        }
        return sx
    }

    // arreglo de consonantes
    val lstconsonantes = arrayOf(
        "b",
        "c",
        "d",
        "f",
        "g",
        "h",
        "j",
        "k",
        "l",
        "m",
        "n",
        "p",
        "q",
        "r",
        "s",
        "t",
        "v",
        "x",
        "y",
        "z"
    )

    // arreglo de estados
    val lstEstados = arrayOf(
        "Aguascalientes",
        "Baja California",
        "Baja California Sur",
        "Campeche",
        "Coahuila de Zaragoza",
        "Colima",
        "Chiapas",
        "Chihuahua",
        "Distrito Federal",
        "Durango",
        "Guanajuato",
        "Guerrero",
        "Hidalgo",
        "Jalisco",
        "México",
        "Michoacán de Ocampo",
        "Morelos",
        "Nayarit",
        "Nuevo León",
        "Oaxaca",
        "Puebla",
        "Querétaro",
        "Quintana Roo",
        "San Luis Potosí",
        "Sinaloa",
        "Sonora",
        "Tabasco",
        "Tamaulipas",
        "Tlaxcala",
        "Veracruz de Ignacio de la Llave",
        "Yucatán",
        "Zacatecas",
        "Nacido en el Extranjero"
    )

    private fun determinarClaveEstado(pNbEstadoSeleccionado: String): String {
        val hmEstados: HashMap<String, String> = HashMap<String, String>()
        hmEstados . put ("Aguascalientes", "AS")
        hmEstados.put("Baja California", "BC")
        hmEstados.put("Baja California Sur", "BS")
        hmEstados.put("Campeche", "CC")
        hmEstados.put("Coahuila de Zaragoza", "CL")
        hmEstados.put("Colima", "CM")
        hmEstados.put("Chiapas", "CS")
        hmEstados.put("Chihuahua", "CH")
        hmEstados.put("Distrito Federal", "DF")
        hmEstados.put("Durango", "DG")
        hmEstados.put("Guanajuato", "GT")
        hmEstados.put("Guerrero", "GR")
        hmEstados.put("Hidalgo", "HG")
        hmEstados.put("Jalisco", "JC")
        hmEstados.put("México", "MC")
        hmEstados.put("Michoacán de Ocampo", "MN")
        hmEstados.put("Morelos", "MS")
        hmEstados.put("Nayarit", "NT")
        hmEstados.put("Nuevo León", "NL")
        hmEstados.put("Oaxaca", "OC")
        hmEstados.put("Puebla", "PL")
        hmEstados.put("Querétaro", "QT")
        hmEstados.put("Quintana Roo", "QR")
        hmEstados.put("San Luis Potosí", "SP")
        hmEstados.put("Sinaloa", "SL")
        hmEstados.put("Sonora", "SR")
        hmEstados.put("Tabasco", "TC")
        hmEstados.put("Tamaulipas", "TS")
        hmEstados.put("Tlaxcala", "TL")
        hmEstados.put("Veracruz de Ignacio de la Llave", "VZ")
        hmEstados.put("Yucatán", "YN")
        hmEstados.put("Zacatecas", "ZS")
        hmEstados.put("Nacido en el Extranjero", "NE")

        return hmEstados[pNbEstadoSeleccionado].toString()
    }
}
