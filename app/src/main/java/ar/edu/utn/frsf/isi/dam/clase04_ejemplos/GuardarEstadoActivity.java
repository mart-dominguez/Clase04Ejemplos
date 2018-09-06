package ar.edu.utn.frsf.isi.dam.clase04_ejemplos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class GuardarEstadoActivity extends AppCompatActivity {
    private Button btnRestar;
    private Button btnSumar;
    private Button btnReiniciar;
    private TextView tv;
    private Integer contador;
    private ToggleButton guardarEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_estado);
        contador=0;
        btnSumar= (Button) findViewById(R.id.btnSumar);
        btnSumar.setOnClickListener(this.listenerBtn);
        btnRestar=(Button) findViewById(R.id.btnRestar);
        btnRestar.setOnClickListener(this.listenerBtn);
        btnReiniciar=(Button) findViewById(R.id.btnReiniciar);
        btnReiniciar.setOnClickListener(this.listenerBtn);
        tv=(TextView) findViewById(R.id.textoResultado);
        tv.setText("Contador : "+contador);
        guardarEstado = (ToggleButton) findViewById(R.id.habilitarGuardarDatos);
    }

    View.OnClickListener listenerBtn = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnSumar:
                    contador++;
                    if(contador==10){
                        btnSumar.setEnabled(false);
                    }
                    break;
                case R.id.btnRestar:
                    contador--;
                    if(contador==-10){
                        btnRestar.setEnabled(false);
                    }
                    break;
                case R.id.btnReiniciar:
                    contador =0;
                    btnSumar.setEnabled(true);
                    btnRestar.setEnabled(true);
            }
            tv.setText("Contador : "+contador);
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(guardarEstado.isChecked()) {
            outState.putInt("contador", contador);
            outState.putBoolean("btnSumar", btnSumar.isEnabled());
            outState.putBoolean("btnRestar", btnRestar.isEnabled());
            Toast.makeText(GuardarEstadoActivity.this,"SI GUARDARA el estado",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(GuardarEstadoActivity.this,"NO GUARDARA el estado",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(guardarEstado.isChecked()) {
            contador = savedInstanceState.getInt("contador");
            tv.setText("Contador : " + contador);
            btnSumar.setEnabled(savedInstanceState.getBoolean("btnSumar"));
            btnRestar.setEnabled(savedInstanceState.getBoolean("btnRestar"));
            Toast.makeText(GuardarEstadoActivity.this,"SI guardo estado",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(GuardarEstadoActivity.this,"NO guardo estado",Toast.LENGTH_LONG).show();
        }
    }
}
