package ar.edu.utn.frsf.isi.dam.clase04_ejemplos;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class BloqueoMainThreadActivity extends AppCompatActivity {

    private Button btnNoHilo;
    private Button btnActuaUIFuera;
    private Button btnHandler;
    private Button btnRunUIThread;
    private Button btnAsynTask;
    private Button btnIntentService;

    private TextView estado;
    private ProgressBar progreso;

    private Handler miHandler;

    private static final int CODIGO_OPERACION = 99;

    private List<Pedido> generarDatos(){
        List<Pedido> lista = new ArrayList<>();
        for(int i=0;i<200;i++){
            lista.add(new Pedido(i,"PEDIDO "+i));
        }
        return lista;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloqueo_main_thread);

        estado = (TextView) findViewById(R.id.estado);
        progreso= (ProgressBar) findViewById(R.id.progressBar2);
        progreso.setProgress(0);
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

    View.OnClickListener listenerRunnableHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                Log.d("CLASE04","Inicia HILO ");
                try {
                    final long inicio = System.currentTimeMillis();
                    Thread.sleep(3500);
                    Runnable r2 = new Runnable() {
                        @Override
                        public void run() {
                            int duracion = (int) (System.currentTimeMillis()-inicio);
                            if(duracion%2==0) estado.setTextColor( Color.BLUE);
                            else estado.setTextColor(Color.RED);
                            estado.setText( "FINALIZA proceso largo. La duracion es "+duracion+" millis");
                        }
                    };
                    estado.post(r2);
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
            MyAsyncTask miTarea = new MyAsyncTask();
            miTarea.execute(generarDatos().toArray(new Pedido[0]));
        }
    };

    View.OnClickListener listenerIntentService= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d("CLASE04","Crear intent service");
            Intent nuevoServicio = new Intent(BloqueoMainThreadActivity.this,MyIntentService.class);
            startService(nuevoServicio);
            finish();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"SALIENDO",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"DESTRUYENDO",Toast.LENGTH_LONG).show();
    }

    class MyAsyncTask extends AsyncTask<Pedido,Double,Integer> {

        @Override
        protected Integer doInBackground(Pedido... pedidos) {
            int cantidad =0;
            int total = pedidos.length;
            int procesados = 0;
            for(Pedido p : pedidos){
                cantidad += p.getCantidad();
                try {
                    Thread.sleep(100+p.getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                procesados++;
                publishProgress(((procesados*1.0)/total)*100.0);
                if(isCancelled()) break;
            }
            return cantidad;
        }


        @Override
        protected void onPreExecute() {
            estado.setText("Se comienza el procesamiento");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            estado.setTextColor(Color.BLUE);
            estado.setText("Se procesaron todos los pedidos. Total de items"+integer );
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            progreso.setProgress(values[0].intValue());
            estado.setTextColor(Color.MAGENTA);
            estado.setText("se lleva procesado "+values[0].intValue()+"%");
        }
    }

}

