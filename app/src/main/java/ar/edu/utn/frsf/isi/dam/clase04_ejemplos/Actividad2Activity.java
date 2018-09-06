package ar.edu.utn.frsf.isi.dam.clase04_ejemplos;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Actividad2Activity extends AppCompatActivity {

    private String[] productos = {"Aaa_6","Abc_44","AbK_44","AKc_44","AYc_87","Abc_44","Aay99","Axy99",
            "Aop_469","Aw1_1a_469","Aa9_oyx_223","AH_oyx_223","A_oyx_223","B10_f","B92_a","B14_C","B94_AK",
            "B74_O","B45_P","B11_H","Bn9_a1","B09_aa","BNN_OK","Bac_34","Bop_10","Bzxa_a",
    };


    private ArrayAdapter<String> adapter;
    private ListView lista;
    private EditText edtCantidad;
    private Button aceptar;
    private String eleccion ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acividad2);
        lista = (ListView) findViewById(R.id.listaProductos);
        lista.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        edtCantidad = (EditText) findViewById(R.id.edtCantidad);
        aceptar = (Button) findViewById(R.id.btnAceptar);
        String[] datos = filtrarDatos(getIntent().getStringExtra("primerLetra"));
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_single_choice,datos);
        this.lista.setAdapter(adapter);
        this.lista.setOnItemClickListener(this.listenerClickLista);
        this.aceptar.setOnClickListener(listenerBtnAceptar);
    }

    private View.OnClickListener listenerBtnAceptar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            lista.getSelectedItem();
            Intent intentResultado = new Intent();
            intentResultado.putExtra("cantidad",Integer.valueOf(edtCantidad.getText().toString()));
            Log.d("CLASE04","Eleccion en set result: "+eleccion);
            intentResultado.putExtra("producto",eleccion);
            setResult(Activity.RESULT_OK,intentResultado);
            finish();
        }
    };

    private String[] filtrarDatos(String x){
        List<String> lista = new ArrayList<>();
        for(String elemento : productos){
            Log.d("CLASE04","COMPARA: "+elemento.toUpperCase().charAt(0) +"=="+ x.toUpperCase().charAt(0));
            if(elemento.toUpperCase().charAt(0) == x.toUpperCase().charAt(0) )lista.add(elemento);
        }
        return lista.toArray(new String[0]);
    }

    private  AdapterView.OnItemClickListener listenerClickLista = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("CLASE04","Eleccion: "+ adapterView.getItemAtPosition(i));
            eleccion = (String) adapterView.getItemAtPosition(i);
        }
    };
}