package com.example.dm_esg_05;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;

    private String numeroActual = "0";
    private String numeroAnterior = "";
    private String operador = null;
    private boolean reiniciarPantalla = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvDisplay = findViewById(R.id.tvDisplay);

        // Botones de números
        findViewById(R.id.btnCero).setOnClickListener(v -> agregarNumero("0"));
        findViewById(R.id.btnUno).setOnClickListener(v -> agregarNumero("1"));
        findViewById(R.id.btnDos).setOnClickListener(v -> agregarNumero("2"));
        findViewById(R.id.btnTres).setOnClickListener(v -> agregarNumero("3"));
        findViewById(R.id.btnCuatro).setOnClickListener(v -> agregarNumero("4"));
        findViewById(R.id.btnCinco).setOnClickListener(v -> agregarNumero("5"));
        findViewById(R.id.btnSeis).setOnClickListener(v -> agregarNumero("6"));
        findViewById(R.id.btnSiete).setOnClickListener(v -> agregarNumero("7"));
        findViewById(R.id.btnOcho).setOnClickListener(v -> agregarNumero("8"));
        findViewById(R.id.btnNueve).setOnClickListener(v -> agregarNumero("9"));

        findViewById(R.id.btnPunto).setOnClickListener(v -> agregarPunto());

        findViewById(R.id.btnMas).setOnClickListener(v -> setOperador("+"));
        findViewById(R.id.btnMenos).setOnClickListener(v -> setOperador("-"));
        findViewById(R.id.btnMulti).setOnClickListener(v -> setOperador("×"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> setOperador("÷"));

        findViewById(R.id.btnIgual).setOnClickListener(v -> calcular());

        actualizarPantalla();
    }

    private void agregarNumero(String numero) {
        if (reiniciarPantalla) {
            numeroActual = "0";
            reiniciarPantalla = false;
        }
        numeroActual = numeroActual.equals("0") ? numero : numeroActual + numero;
        actualizarPantalla();
    }

    private void agregarPunto() {
        if (reiniciarPantalla) {
            numeroActual = "0";
            reiniciarPantalla = false;
        }
        if (!numeroActual.contains(".")) {
            numeroActual += ".";
            actualizarPantalla();
        }
    }

    private void setOperador(String nuevoOperador) {
        if (operador != null && !reiniciarPantalla) {
            calcular();
        }
        numeroAnterior = numeroActual;
        operador = nuevoOperador;
        reiniciarPantalla = true;
    }

    private void calcular() {
        if (operador == null) return;

        double n1, n2;
        try {
            n1 = Double.parseDouble(numeroAnterior);
            n2 = Double.parseDouble(numeroActual);
        } catch (NumberFormatException e) {
            return;
        }

        double resultado;
        switch (operador) {
            case "+":
                resultado = n1 + n2;
                break;
            case "-":
                resultado = n1 - n2;
                break;
            case "×":
                resultado = n1 * n2;
                break;
            case "÷":
                resultado = (n2 != 0.0) ? n1 / n2 : Double.NaN;
                break;
            default:
                resultado = n2;
        }

        if (Double.isNaN(resultado)) {
            numeroActual = "Error";
        } else if (resultado == Math.floor(resultado) && !Double.isInfinite(resultado)) {
            numeroActual = String.valueOf((long) resultado);
        } else {
            numeroActual = new DecimalFormat("#.##########").format(resultado);
        }

        operador = null;
        numeroAnterior = "";
        reiniciarPantalla = true;
        actualizarPantalla();
    }

    private void actualizarPantalla() {
        tvDisplay.setText(numeroActual);
    }
}