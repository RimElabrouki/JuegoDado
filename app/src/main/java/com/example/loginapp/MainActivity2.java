package com.example.loginapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import android.content.Context;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
//import android.os.Handler;

//import android.provider.MediaStore;
import android.util.Log;

//import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginapp.ui.login.LoginActivity;
import com.example.loginapp.ui.login.MyAdapter;
import com.google.common.base.Joiner;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * Esta clase contiene atributos y metodos del MainActivity2
 * @author Rim
 * @version 12
 * @since 02/04/2022
 *
 */
public class MainActivity2 extends AppCompatActivity {
    private String file = "Save.xml";

    MediaPlayer micancion;
    int LENGHT = 10;
    int POSITION_WINNER = 99;
    Button botonReiniciar;
    Button botonSalir;
    TextView textoPuntuacion;
     TableLayout table;
    int position;
    int rightOption, leftOption, topOption, downOption;
    int tiradas;
    int recordarTiradas;
    PriorityQueue<Integer> recordList = new PriorityQueue<>(3);
    SortedMap<Integer, String> recordAllUsers = new TreeMap<Integer, String>();

    String email;

    /**
     * Metodo oneCreate
     * @param savedInstanceState de tipo Bundle
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();
        Button rollButton = findViewById(R.id.rollbutton);
        final ImageView rightDice = findViewById(R.id.image_rightDice);

        /**Guardad dados en Un array (diceArray)
         *
         */

        final int[] diceArray = {
                R.drawable.dice1,
                R.drawable.dice2,
                R.drawable.dice3,
                R.drawable.dice4,
                R.drawable.dice5,
                R.drawable.dice6
        };

        // Tell the button to listen for clicks
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random randomNumberGenerator = new Random();
                int number = randomNumberGenerator.nextInt(6);
                Log.d("Dicee", "The random number is " + number + 1);
                rightDice.setImageResource(diceArray[number]);
                textoPuntuacion.setText("Nº Tiradas: " + ++tiradas);
                recordarTiradas++;
                enabledPosition(number);
                rollButton.setEnabled(false);
            }
        });


    }

    /**
     * metodo que inicia la musica del juego
     * @param v de tipo View
     */
    public void juegalo(View v) {
        if (micancion == null) {
            micancion = MediaPlayer.create(MainActivity2.this, R.raw.play);
            micancion.start();

        }else{
            Toast.makeText(getApplicationContext(), "Intenta de nuevo ", Toast.LENGTH_LONG).show();
        }if(micancion != null){
            Toast.makeText(getApplicationContext(), "la canción comenzó ", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Intenta de nuevo ", Toast.LENGTH_LONG).show();
        }       botonSalir = findViewById(R.id.botonJuegoSalir);

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stop(null);
                startActivity(new Intent(MainActivity2.this, LoginActivity.class));
            }
        });
    }

    /**
     * Metodo que se cierra la musica del juego
     * @param v de tipo View
     */
    public void Stop(View v) {
        if (micancion != null) {
            micancion.release();
            micancion = null;
        }else{
            try {
                Toast.makeText(MainActivity2.this, "la canción se detiene", Toast.LENGTH_LONG).show();
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }
    /**
     * Metodo que indica las posiciones disponibles en el tablero
     * @param number de tipo int
     */
    private void enabledPosition(int number) {
        for (int i = 0; i < LENGHT * LENGHT; i++) {
            ImageButton routeMoreInfo = (ImageButton) table.findViewById(i);
            routeMoreInfo.setEnabled(false);
        }
        number++;
        rightOption = position + number;
        if (rightOption < position - (position % LENGHT) + LENGHT) {
            ImageButton routeMoreInfo = (ImageButton) table.findViewById(rightOption);
            routeMoreInfo.setImageResource(R.drawable.options);
            routeMoreInfo.setEnabled(true);
        } else {
            rightOption = 0;
        }
        leftOption = position - number;
        if (leftOption >= (position - (position % LENGHT))) {
            ImageButton routeMoreInfo = (ImageButton) table.findViewById(leftOption);
            routeMoreInfo.setImageResource(R.drawable.options);
            routeMoreInfo.setEnabled(true);
        } else {
            leftOption = 0;
        }
        topOption = position + (number * LENGHT);
        if (topOption < 100) {
            ImageButton routeMoreInfo = (ImageButton) table.findViewById(topOption);
            routeMoreInfo.setImageResource(R.drawable.options);
            routeMoreInfo.setEnabled(true);
        } else {
            topOption = 0;
        }
        downOption = position - (number * LENGHT);
        if (downOption > 0) {
            ImageButton routeMoreInfo = (ImageButton) table.findViewById(downOption);
            routeMoreInfo.setImageResource(R.drawable.options);
            routeMoreInfo.setEnabled(true);
        } else {
            downOption = 0;
        }
    }

    /**
     * Metodo que carga el tablero de 10 * 10
     */
    private void cargarTablero() {
        int i;
        int j;
        position = 0;
        tiradas = 0;
        recordarTiradas = 0;
        table = (TableLayout) findViewById(R.id.tablelayout);
        for (i = 0; i < LENGHT; i++) {
            TableRow tableRow = new TableRow(this);
            for (j = 0; j < LENGHT; j++) {
                ImageButton button = new ImageButton(this);
                if (i == 0 && j == 0) {
                    button.setImageResource(R.drawable.la0);
                } else if (i == 9 && j == 9) {
                    button.setImageResource(R.drawable.ben);
                } else {
                    button.setImageResource(R.drawable.box);
                }

                button.setEnabled(false);
                button.setId(Integer.valueOf(String.valueOf(i) + String.valueOf(j)));
                button.setPadding(0, 0, 0, 0);
                tableRow.addView(button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view.getId() == POSITION_WINNER) {
                            for (int i = 0; i < LENGHT * LENGHT; i++) {
                                ImageButton routeMoreInfo = (ImageButton) table.findViewById(i);
                                routeMoreInfo.setImageResource(R.drawable.la0);
                                routeMoreInfo.setEnabled(false);
                            }
                            recordList.add(recordarTiradas);
                            RecordarTiradas();
                            SaveRecordarTiradas(Joiner.on(",").join(recordList));

                            Toast.makeText(getApplicationContext(), "Has Ganado !!! El numero de tiradas son " + tiradas, Toast.LENGTH_LONG).show();


                        } else {


                            movePosition(view);
                            ImageButton routeMoreInfo = (ImageButton) table.findViewById(POSITION_WINNER);
                            routeMoreInfo.setImageResource(R.drawable.ben);
                            Button rollButton = findViewById(R.id.rollbutton);
                            rollButton.setEnabled(true);
                        }
                    }
                });
            }
            table.addView(tableRow);
        }
    }

    /**
     * Metodo que localiza el posicio del conejo
     * @param view de tipo View
     */
    private void movePosition(View view) {
        //Toast.makeText(getApplicationContext(), "has pulsado en el button:"+ view.getId(), Toast.LENGTH_SHORT).show();
        ImageButton routeMoreInfo = (ImageButton) view;
        routeMoreInfo.setImageResource(R.drawable.la0);
        routeMoreInfo.setEnabled(false);
        if (view.getId() == rightOption) {
            if (topOption > 0) {
                routeMoreInfo = (ImageButton) table.findViewById(topOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }
            if (leftOption > 0 && leftOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(leftOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }
            if (downOption > 0 && downOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(downOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }
        } else if (view.getId() == leftOption) {
            if (topOption > 0 && topOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(topOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }
            if (rightOption > 0 && rightOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(rightOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }
            if (downOption > 0 && downOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(downOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }

        } else if (view.getId() == topOption) {
            if (rightOption > 0 && rightOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(rightOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }
            if (leftOption > 0 && leftOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(leftOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }
            if (downOption > 0 && downOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(downOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }

        } else {
            if (rightOption > 0 && rightOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(rightOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }
            if (leftOption > 0 && leftOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(leftOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }
            if (topOption > 0 && topOption != POSITION_WINNER) {
                routeMoreInfo = (ImageButton) table.findViewById(topOption);
                routeMoreInfo.setImageResource(R.drawable.box);
                routeMoreInfo.setEnabled(false);
            }
        }
        ImageButton imagebuttonPosition = (ImageButton) table.findViewById(position);
        imagebuttonPosition.setImageResource(R.drawable.box);
        imagebuttonPosition.setEnabled(false);
        routeMoreInfo.setEnabled(true);
        position = view.getId();
    }

    /**
     *metodo cargarBotones que carga los botones y son dos
     * Boton Reiniciar : para empezar el juego de nuevo
     * Boton salir: para salir del juego y ir a loginActivity
     *
     *
     */
    private void cargarBotones() {
        botonReiniciar = findViewById(R.id.botonJuegoReiniciar);
        botonSalir = findViewById(R.id.botonJuegoSalir);
        // metodo onClick cuando pulsas el boton Reinciar empieza el juego de nuevo

        botonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table.removeAllViews();
                init();
            }
        });

 /**
  * metodo onClick cuando pulsa el boton salir te vas a LoginActivity
  * */

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity2.this, LoginActivity.class));
            }
        });
    }

    /**
     * metodo cragarTexto que le asigna el numero de tiradas
     */
    private void cargarTexto() {
        textoPuntuacion = findViewById(R.id.texto_puntuacion);
        textoPuntuacion.setText("Nº Tiradas: " + tiradas);
    }
    private void RecordarTiradas() {
        TextView recordar = findViewById(R.id.textView3);
        if(recordList.size()>0){
            recordar.setText("Record de Tiradas: \t" +recordList.peek());
        }else{
            recordar.setText("Record de Tiradas: \t" + recordarTiradas);
        }
    }

    /** metodo que carga Botenes y texto y tablero  */
    private void init() {
        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        Button rollButton = findViewById(R.id.rollbutton);
        rollButton.setEnabled(true);
        tiradas = 0;
        recordarTiradas = 0;
        ReadRecordarTiradas();
        cargarBotones();
        cargarTexto();
        cargarTablero();
        RecordarTiradas();
    }

    /**
     * Metodo que lee los record de tiradas
     */
    private void ReadRecordarTiradas(){
        recordList.clear();
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader inputReader = new BufferedReader(
                    new InputStreamReader(openFileInput(file)));
            String inputString;
            while ((inputString = inputReader.readLine()) != null) {
                stringBuffer.append(inputString + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int num = 0;
        String[] lineas = stringBuffer.toString().split("\n");
        for (String str: lineas) {
            String[] emailValues =  str.split(":");
            if(emailValues[0].equals(email)){
                for (String s : emailValues[1].split(","))
                    recordList.add(Integer.parseInt(s));
            }
        }
    }

    /**
     * Metodo que guarda Records de tiradas hechas para cada usuario
     * @param data de tipo String
     */
    private void SaveRecordarTiradas(String data)
    {

        FileOutputStream fos;
        try {
            fos = openFileOutput(file, Context.MODE_PRIVATE);
            fos.write((email + ":" + data + "\n").getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();}
        catch (IOException e) {
            e.printStackTrace();}
    }
}





