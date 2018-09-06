package ar.edu.utn.frsf.isi.dam.clase04_ejemplos;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import android.os.Handler;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    public MyIntentService () {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String name = "";
        try {
            name =Thread.currentThread().getName();
            Thread.sleep(12000);
            Message m =myHandler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("nameHilo",name);
            m.setData(b);
            myHandler.sendMessage(m);
            Log.d("CLASE04","TERMINA HILO "+name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(MyIntentService.this,
            "Termino "+msg.getData().get("nameHilo"), Toast.LENGTH_SHORT).show();}
    };

}
