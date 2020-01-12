package com.example.calculadorapract.ui.StandartCalculator;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.calculadorapract.R;

public class StandartCalculatorFragment extends Fragment {

    private StandartCalculatorViewModel standartCalculatorViewModel;
    private String operacion = "";
    private Double resultado = 0.0;
    private String numerosIntroducidos = "";
    private String operador = "";
    private int numerosMax = 22;
    private int contadorNumeros = 0;


    private double primerNumero;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        standartCalculatorViewModel =
                ViewModelProviders.of(this).get(StandartCalculatorViewModel.class);


        View root = inflater.inflate(R.layout.fragment_standart_calculator, container, false);

        final TextView numeroPulsado = (TextView) root.findViewById(R.id.tv_result);
        final TextView operaciones = (TextView) root.findViewById(R.id.tv_process);



        Button button = (Button) root.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(numerosMax > contadorNumeros ){
                    if(numeroPulsado.getText().charAt(0) == '0' && numeroPulsado.getText().length() == 1){
                        numeroPulsado.setText("1");
                    } else {
                        numeroPulsado.setText(numeroPulsado.getText() + "1");
                    }
                    contadorNumeros++;
                }
            }
        });

        Button button2 = (Button) root.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numerosMax > contadorNumeros ) {
                    if (numeroPulsado.getText().charAt(0) == '0' && numeroPulsado.getText().length() == 1) {
                        numeroPulsado.setText("2");
                    } else {
                        numeroPulsado.setText(numeroPulsado.getText() + "2");
                    }
                    contadorNumeros++;
                }
            }
        });


        Button button3 = (Button) root.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                if(numerosMax > contadorNumeros ) {
                    if (numeroPulsado.getText().charAt(0) == '0' && numeroPulsado.getText().length() == 1) {
                        numeroPulsado.setText("3");
                    } else {
                        numeroPulsado.setText(numeroPulsado.getText() + "3");
                    }
                    contadorNumeros++;
                }
            }
        });

        Button button4 = (Button) root.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(numerosMax > contadorNumeros ) {
                    if (numeroPulsado.getText().charAt(0) == '0' && numeroPulsado.getText().length() == 1) {
                        numeroPulsado.setText("4");
                    } else {
                        numeroPulsado.setText(numeroPulsado.getText() + "4");
                    }
                    contadorNumeros++;
                }
            }
        });


        Button button5 = (Button) root.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numerosMax > contadorNumeros ) {
                    if (numeroPulsado.getText().charAt(0) == '0' && numeroPulsado.getText().length() == 1) {
                        numeroPulsado.setText("5");
                    } else {
                        numeroPulsado.setText(numeroPulsado.getText() + "5");
                    }
                    contadorNumeros++;
                }
            }
        });


        Button button6 = (Button) root.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(numerosMax > contadorNumeros ) {
                    if (numeroPulsado.getText().charAt(0) == '0' && numeroPulsado.getText().length() == 1) {
                        numeroPulsado.setText("6");
                    } else {
                        numeroPulsado.setText(numeroPulsado.getText() + "6");
                    }
                    contadorNumeros++;
                }
            }
        });

        Button button7 = (Button) root.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(numerosMax > contadorNumeros ) {
                    if (numeroPulsado.getText().charAt(0) == '0' && numeroPulsado.getText().length() == 1) {
                        numeroPulsado.setText("7");
                    } else {
                        numeroPulsado.setText(numeroPulsado.getText() + "7");
                    }
                    contadorNumeros++;
                }
            }
        });

        Button button8 = (Button) root.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(numerosMax > contadorNumeros ){
                    if(numeroPulsado.getText().charAt(0) == '0' && numeroPulsado.getText().length() == 1){
                        numeroPulsado.setText("8");
                    } else {
                        numeroPulsado.setText(numeroPulsado.getText() + "8");
                    }
                    contadorNumeros++;
                }
            }
        });

        Button button9 = (Button) root.findViewById(R.id.button9);
        button9.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(numerosMax > contadorNumeros ) {
                    if (numeroPulsado.getText().charAt(0) == '0' && numeroPulsado.getText().length() == 1) {
                        numeroPulsado.setText("9");
                    } else {
                        numeroPulsado.setText(numeroPulsado.getText() + "9");
                    }
                    contadorNumeros++;
                }
            }
        });

        Button button0 = (Button) root.findViewById(R.id.button0);
        button0.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(numerosMax > contadorNumeros ) {
                    if (numeroPulsado.getText().charAt(0) == '0' && numeroPulsado.getText().length() == 1) {

                    } else if (numeroPulsado.getText().charAt(0) != '0' && numeroPulsado.getText().length() > 2) {
                        if (numeroPulsado.getText().charAt(1) == '.') {
                            numeroPulsado.setText(numeroPulsado.getText() + "0");
                        } else {
                            numeroPulsado.setText(numeroPulsado.getText() + "0");
                        }

                    } else {
                        numeroPulsado.setText(numeroPulsado.getText() + "0");
                    }
                    contadorNumeros++;
                }
            }
        });

        Button btnSuma = (Button) root.findViewById(R.id.buttonSuma);
        Button btnResta = (Button) root.findViewById(R.id.buttonResta);
        Button btnMultiplica = (Button) root.findViewById(R.id.buttonMultiplicacion);
        Button btnDivide = (Button) root.findViewById(R.id.buttonDivision);
        Button btnPorcentaje = (Button) root.findViewById(R.id.porcentaje);

        btnPorcentaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (numeroPulsado.getText().charAt(0) != '0' && numeroPulsado.getText().length() > 0 ) {

                    primerNumero = (Double.valueOf(numeroPulsado.getText().toString()) / 100);
                    String numeroSinDividir = numeroPulsado.getText().toString();

                    if (operacion.length() == 0) {

                        operacion = numeroPulsado.getText().toString();
                    } else {

                        operacion = String.valueOf((Double.valueOf(operacion)/100) * primerNumero);


                    }


                    String comprobarNumero = String.valueOf(numeroSinDividir);

                    if (comprobarNumero.indexOf('.') == comprobarNumero.length() - 2 && comprobarNumero.endsWith("0")) {
                        numerosIntroducidos += String.valueOf(comprobarNumero.substring(0, comprobarNumero.length() - 2)) + "%";


                    } else {
                        numerosIntroducidos += numeroSinDividir + "%";

                    }

                    operaciones.setText(numerosIntroducidos);


                    numeroPulsado.setText("0");
                    operador = "%";
                    primerNumero = 0;

                }
            }
        });


        btnSuma.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                if (numeroPulsado.getText().toString().length() != 0) {
                    primerNumero = Double.valueOf(numeroPulsado.getText().toString());


                    if (operacion.length() == 0) {

                        operacion = numeroPulsado.getText().toString();

                    } else if(operador == "%"){

                        operacion = String.valueOf((Double.valueOf(operacion)/100) * primerNumero);

                    } else {

                        Log.i("A ver que sale aquine ",  operacion);
                        operacion = String.valueOf(Double.valueOf(operacion) + primerNumero);




                    }


                    String comprobarNumero = String.valueOf(primerNumero);

                    if (comprobarNumero.indexOf('.') == comprobarNumero.length() - 2 && comprobarNumero.endsWith("0")) {
                        numerosIntroducidos += String.valueOf(comprobarNumero.substring(0, comprobarNumero.length() - 2)) + "+";

                    } else {
                        numerosIntroducidos += primerNumero + "+";

                    }

                    operaciones.setText(numerosIntroducidos);
                    contadorNumeros = 0;
                    numeroPulsado.setText("0");
                    operador = "+";
                    primerNumero = 0;

                }
            }
        });


        btnResta.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                if (numeroPulsado.getText().toString().length() != 0) {
                    primerNumero = Double.valueOf(numeroPulsado.getText().toString());


                    if (operacion.length() == 0) {

                        operacion = numeroPulsado.getText().toString();

                    }  else if(operador == "%"){

                        operacion = String.valueOf((Double.valueOf(operacion)/100) * primerNumero);

                    } else {

                        operacion = String.valueOf(Double.valueOf(operacion) - primerNumero);

                    }


                    String comprobarNumero = String.valueOf(primerNumero);

                    if (comprobarNumero.indexOf('.') == comprobarNumero.length() - 2 && comprobarNumero.endsWith("0")) {
                        numerosIntroducidos += String.valueOf(comprobarNumero.substring(0, comprobarNumero.length() - 2)) + "-";

                    } else {
                        numerosIntroducidos += primerNumero + "-";

                    }

                operaciones.setText(numerosIntroducidos);
                contadorNumeros = 0;
                numeroPulsado.setText("0");
                operador = "-";
                primerNumero = 0;
            }



            }




        });

        btnMultiplica.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                if (numeroPulsado.getText().toString().length() != 0) {
                    primerNumero = Double.valueOf(numeroPulsado.getText().toString());

                    if (operacion.length() == 0) {
                        operacion = numeroPulsado.getText().toString();
                    }  else if(operador == "%"){

                        operacion = String.valueOf((Double.valueOf(operacion)/100) * primerNumero);

                    } else {

                        operacion = String.valueOf(Double.valueOf(operacion) * primerNumero);
                    }

                    String comprobarNumero = String.valueOf(primerNumero);

                    if (comprobarNumero.indexOf('.') == comprobarNumero.length() - 2 && comprobarNumero.endsWith("0")) {
                        numerosIntroducidos += String.valueOf(comprobarNumero.substring(0, comprobarNumero.length() - 2)) + "x";

                    } else {
                        numerosIntroducidos += primerNumero + "x";

                    }

                    operaciones.setText(numerosIntroducidos);
                    contadorNumeros = 0;
                    numeroPulsado.setText("0");
                    operador = "x";
                    primerNumero = 0;
                }
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if (numeroPulsado.getText().toString().length() != 0) {
                    primerNumero = Double.valueOf(numeroPulsado.getText().toString());

                    if (operacion.length() == 0) {
                        operacion = numeroPulsado.getText().toString();
                    }  else if(operador == "%"){

                        operacion = String.valueOf((Double.valueOf(operacion)/100) * primerNumero);

                    } else {

                        operacion = String.valueOf(Double.valueOf(operacion) / primerNumero);
                    }

                    String comprobarNumero = String.valueOf(primerNumero);

                    if (comprobarNumero.indexOf('.') == comprobarNumero.length() - 2 && comprobarNumero.endsWith("0")) {
                        numerosIntroducidos += String.valueOf(comprobarNumero.substring(0, comprobarNumero.length() - 2)) + "/";

                    } else {
                        numerosIntroducidos += primerNumero + "/";

                    }
                    operaciones.setText(numerosIntroducidos);
                    contadorNumeros = 0;
                    numeroPulsado.setText("0");
                    operador = "/";
                    primerNumero = 0;
                }
            }
        });

        Button btnBorrar = (Button) root.findViewById(R.id.buttonBorrar);
        btnBorrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (numeroPulsado.getText().toString().length() != 1) {
                    String mostrar = numeroPulsado.getText().toString();
                    mostrar = mostrar.substring(0, mostrar.length() - 1);
                    numeroPulsado.setText(mostrar);
                } else if(numeroPulsado.getText().toString().length() == 1){
                    numeroPulsado.setText("0");
                }
                contadorNumeros--;
            }
        });

        Button btnAC = (Button) root.findViewById(R.id.buttonAC);
        btnAC.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                numeroPulsado.setText("0");
                operaciones.setText("");
                operacion = "";
                numerosIntroducidos = "";
                operador = "";
                contadorNumeros = 0;
            }
        });

        Button btnPunto = (Button) root.findViewById(R.id.punto);
        btnPunto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(numerosMax > contadorNumeros ) {

                    boolean puntoEncontrado = false;
                    CharSequence numeros = numeroPulsado.getText();
                    for (int i = 0; i < numeroPulsado.getText().toString().length(); i++) {
                        if (numeros.charAt(i) == '.') {
                            puntoEncontrado = true;
                        }
                    }

                    if (puntoEncontrado == false && (numeroPulsado.getText().toString().length() != 0)) {
                        numeroPulsado.setText(numeroPulsado.getText() + ".");
                    }
                    contadorNumeros++;
                }



            }
        });

        Button btnIgual = (Button) root.findViewById(R.id.buttonIgual);
        btnIgual.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(operaciones.getText().length() != 0) {
                    primerNumero = Double.valueOf(numeroPulsado.getText().toString());

                    if (operador.equals("+")) {
                        operacion = String.valueOf(Double.valueOf(operacion) + primerNumero);
                    } else if (operador.equals("-")) {
                        operacion = String.valueOf(Double.valueOf(operacion) - primerNumero);
                    } else if (operador.equals("x")) {
                        operacion = String.valueOf(Double.valueOf(operacion) * primerNumero);
                    } else if (operador.equals("/")) {
                        operacion = String.valueOf(Double.valueOf(operacion) / primerNumero);
                    } else if (operador.equals("%")){
                        operacion = String.valueOf((Double.valueOf(operacion)/100) * primerNumero);
                    }
                    resultado = Double.valueOf(operacion);


                    if (operacion.indexOf('.') == operacion.length() - 2 && operacion.endsWith("0")) {
                        numeroPulsado.setText(String.valueOf(operacion.substring(0, operacion.length() - 2)));

                    } else {
                        numeroPulsado.setText(String.valueOf(resultado));

                    }


                    operacion = "";
                    contadorNumeros = 0;

                    operaciones.setText("");
                    numerosIntroducidos = "";

                    primerNumero = 0;
                }
            }
        });
        
        return root;
    }
}