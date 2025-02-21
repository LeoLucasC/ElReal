package com.example.elreal

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ConversorMonedas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_conversor_monedas)


        // Referencia al botón
        val buttonMonedas = findViewById<Button>(R.id.button_potencia2)

        // Agregar la acción para abrir la nueva Activity
        buttonMonedas.setOnClickListener {
            val intent = Intent(this, convertidoresSenatilong::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los elementos del layout
        val inputMonto = findViewById<EditText>(R.id.inputTemperatura)
        val spinnerFrom = findViewById<Spinner>(R.id.spinnerFrom)
        val spinnerTo = findViewById<Spinner>(R.id.spinnerTo)
        val buttonConvertir = findViewById<Button>(R.id.buttonConvertir)
        val textResultado = findViewById<TextView>(R.id.textViewResultado)
        val buttonRegresar = findViewById<Button>(R.id.buttonRegresar) // Botón de regreso

        // Lista de monedas disponibles
        val monedas = arrayOf("USD", "EUR", "PEN", "MXN", "GBP")

        // Adaptador para los `Spinner`
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, monedas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Mapa con tasas de conversión
        val tasasConversion = mapOf(
            "USD_PEN" to 3.8,
            "PEN_USD" to 0.26,
            "USD_EUR" to 0.91,
            "EUR_USD" to 1.1,
            "PEN_EUR" to 0.24,
            "EUR_PEN" to 4.2,
            "USD_GBP" to 0.78,
            "GBP_USD" to 1.28,
            "MXN_USD" to 0.059,
            "USD_MXN" to 17.0
        )

        // Evento del botón de conversión
        buttonConvertir.setOnClickListener {
            val montoTexto = inputMonto.text.toString()
            if (montoTexto.isEmpty()) {
                textResultado.text = "Ingrese un monto"
                return@setOnClickListener
            }

            val monto = montoTexto.toDouble()
            val monedaOrigen = spinnerFrom.selectedItem.toString()
            val monedaDestino = spinnerTo.selectedItem.toString()

            // Evitar conversiones entre la misma moneda
            if (monedaOrigen == monedaDestino) {
                textResultado.text = "Seleccione monedas distintas"
                return@setOnClickListener
            }

            // Obtener la tasa de conversión
            val claveConversion = "${monedaOrigen}_${monedaDestino}"
            val tasa = tasasConversion[claveConversion]

            if (tasa != null) {
                val resultado = monto * tasa
                textResultado.text = "Resultado: %.2f $monedaDestino".format(resultado)
            } else {
                textResultado.text = "Conversión no disponible"
            }
        }

        // Evento del botón "Regresar"
        buttonRegresar.setOnClickListener {
            finish() // Cierra la actividad y regresa a la anterior
        }
    }
}
