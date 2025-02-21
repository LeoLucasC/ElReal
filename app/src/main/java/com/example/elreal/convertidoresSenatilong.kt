package com.example.elreal

import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class convertidoresSenatilong : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_convertidores_senatilong)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los elementos de la UI
        val inputLongitud = findViewById<EditText>(R.id.inputLongitud)
        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo)
        val buttonConvertir = findViewById<Button>(R.id.buttonConvertir)
        val textViewResultado = findViewById<TextView>(R.id.textViewResultado)
        val buttonRegresar = findViewById<Button>(R.id.buttonRegresar)

        // Lista de unidades de longitud
        val unidades = arrayOf("Metros", "Kilómetros", "Centímetros", "Milímetros", "Pulgadas", "Pies", "Yardas", "Millas")

        // Configurar los Spinners
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unidades)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Botón de conversión
        buttonConvertir.setOnClickListener {
            val cantidadStr = inputLongitud.text.toString()
            if (cantidadStr.isEmpty()) {
                Toast.makeText(this, "Ingrese un valor válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cantidad = cantidadStr.toDouble()
            val unidadOrigen = spinnerFrom.selectedItem.toString()
            val unidadDestino = spinnerTo.selectedItem.toString()

            val resultado = convertirLongitud(cantidad, unidadOrigen, unidadDestino)
            textViewResultado.text = "Resultado: $resultado $unidadDestino"
        }

        // Botón para regresar
        buttonRegresar.setOnClickListener {
            finish() // Cierra esta actividad y regresa a la anterior
        }
    }

    private fun convertirLongitud(valor: Double, desde: String, hacia: String): Double {
        val factores = mapOf(
            "Metros" to 1.0,
            "Kilómetros" to 0.001,
            "Centímetros" to 100.0,
            "Milímetros" to 1000.0,
            "Pulgadas" to 39.3701,
            "Pies" to 3.28084,
            "Yardas" to 1.09361,
            "Millas" to 0.000621371
        )

        val valorEnMetros = valor / (factores[desde] ?: 1.0)
        return valorEnMetros * (factores[hacia] ?: 1.0)
    }
}
