package ar.edu.utn.frsf.isi.dam.clase04_ejemplos;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class BloqueoMainThreadActivity extends AppCompatActivity {

    private Button btnNoHilo;
    private Button btnActuaUIFuera;
    private Button btnHandler;
    private Button btnRunUIThread;
    private Button btnAsynTask;
    private Button btnIntentService;

    private TextView estado;

    private Handler miHandler;

    private static final int CODIGO_OPERACION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueo_main_thread);

        estado = (TextView) findViewById(R.id.estado);

        btnNoHilo = findViewById(R.id.btnNoHilo);
        btnActuaUIFuera = findViewById(R.id.btnActuaUIFuera);
        btnHandler = findViewById(R.id.btnHandler);
        btnRunUIThread = findViewById(R.id.btnRunUIThread);
        btnAsynTask = findViewById(R.id.btnAsynTask);
        btnIntentService = findViewById(R.id.btnIntentService);

        btnNoHilo.setOnClickListener(this.listenerNoHilo);
        btnActuaUIFuera.setOnClickListener(this.listenerActuUiFuera);
        btnHandler.setOnClickListener(this.listenerMensajeHandler);

        miHandler =  new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message inputMessage) {
                if(inputMessage.what==CODIGO_OPERACION){
                    String texto = "FINALIZA proceso largo. La duracion es "+inputMessage.arg1+" millis";
                    estado.setTextColor((Integer)inputMessage.obj);
                    estado.setText(texto);
                }
            }
        };

        btnRunUIThread.setOnClickListener(this.listenerActuUIOk);
        btnAsynTask.setOnClickListener(this.listenerActuAsyn);
        btnIntentService.setOnClickListener(this.listenerIntentService);

    }



    View.OnClickListener listenerNoHilo = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            estado.setText("Comienza proceso largo");
            long inicio = System.currentTimeMillis();
            Log.d("CLASE04","Inicia HILO "+inicio);
                btnNoHilo.setEnabled(false);
                long x = 0;
                while((System.currentTimeMillis()-inicio)<10500){
                    x++;
                }
                long duracion = (System.currentTimeMillis()-inicio);
                btnNoHilo.setEnabled(true);
                estado.setTextColor(Color.GREEN);
                estado.setText( "FINALIZA proceso largo. La duracion es "+duracion+" millis");
                Log.d("CLASE04","Finaliza HILO: duracion<"+duracion+"> x<"+x+">");
        }
    };


    View.OnClickListener listenerActuUiFuera= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Log.d("CLASE04","Inicia HILO ");
                    try {
                        estado.setText("Comienza proceso largo");
                        Thread.sleep(9500);
                        estado.setText("FINALIZA proceso largo");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread t1 = new Thread(r);
            t1.start();
            Log.d("CLASE04","Finaliza HILO");
        }
    };

    View.OnClickListener listenerMensajeHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final Message mensaje = new Message();
            mensaje.what=CODIGO_OPERACION;
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Log.d("CLASE04","Inicia HILO ");
                    try {

                        long inicio = System.currentTimeMillis();
                        Thread.sleep(3500);
                        int duracion = (int) (System.currentTimeMillis()-inicio);
                        mensaje.arg1=duracion;
                        if(duracion%2==0) mensaje.obj = Color.BLUE;
                        else mensaje.obj = Color.BLUE;
                        miHandler.sendMessage(mensaje);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            estado.setText("Comienza proceso largo");
            Thread t1 = new Thread(r);
            t1.start();
            Log.d("CLASE04","Finaliza HILO");
        }
    };

    View.OnClickListener listenerActuUIOk= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Log.d("CLASE04","Inicia HILO ");
                    try {
                        long inicio = System.currentTimeMillis();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                    estado.setText("Comienza proceso largo");
                            }
                        });
                        Thread.sleep(3544);
                        final long duracion = (System.currentTimeMillis()-inicio);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                estado.setText( "FINALIZA proceso largo. La duracion es "+duracion+" millis");

                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            Thread t1 = new Thread(r);
            t1.start();
            Log.d("CLASE04","Finaliza HILO");
        }
    };

    View.OnClickListener listenerActuAsyn= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("CLASE04","Crear tarea asincronica");
        }
    };

    View.OnClickListener listenerIntentService= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("CLASE04","Crear intent service");
        }
    };

}

