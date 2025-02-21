package com.example.elreal

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PruebaReal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prueba_real)



        // Referencia al botón
        val buttonMonedas = findViewById<Button>(R.id.button_ac2)

        // Agregar la acción para abrir la nueva Activity
        buttonMonedas.setOnClickListener {
            val intent = Intent(this, ConversorMonedas::class.java)
            startActivity(intent)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los elementos de la UI
        val inputTemperatura = findViewById<EditText>(R.id.inputTemperatura)
        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo)
        val buttonConvertir = findViewById<Button>(R.id.buttonConvertir)
        val textViewResultado = findViewById<TextView>(R.id.textViewResultado)

        // Opciones para los Spinners
        val unidades = arrayOf("Celsius", "Fahrenheit", "Kelvin")

        // Adaptadores para los Spinners
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, unidades)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Acción del botón de conversión
        buttonConvertir.setOnClickListener {
            val tempStr = inputTemperatura.text.toString()

            if (tempStr.isNotEmpty()) {
                val temp = tempStr.toDouble()
                val unidadOrigen = spinnerFrom.selectedItem.toString()
                val unidadDestino = spinnerTo.selectedItem.toString()

                val resultado = convertirTemperatura(temp, unidadOrigen, unidadDestino)
                textViewResultado.text = "Resultado: $resultado $unidadDestino"
            } else {
                Toast.makeText(this, "Ingrese una temperatura válida", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para convertir temperaturas
    private fun convertirTemperatura(valor: Double, from: String, to: String): Double {
        return when {
            from == "Celsius" && to == "Fahrenheit" -> (valor * 9/5) + 32
            from == "Celsius" && to == "Kelvin" -> valor + 273.15
            from == "Fahrenheit" && to == "Celsius" -> (valor - 32) * 5/9
            from == "Fahrenheit" && to == "Kelvin" -> (valor - 32) * 5/9 + 273.15
            from == "Kelvin" && to == "Celsius" -> valor - 273.15
            from == "Kelvin" && to == "Fahrenheit" -> (valor - 273.15) * 9/5 + 32
            else -> valor  // Si las unidades son iguales, devuelve el mismo valor
        }
    }
}
