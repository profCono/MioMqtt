package com.example.prova0mqtt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    MqttHelper mqttHelper;
    TextView dataReceived;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //seconda versione https://wildanmsyah.wordpress.com/2017/05/11/mqtt-android-client-tutorial/
       dataReceived=(TextView) findViewById(R.id.dataRecived);
       startMqtt();
    }
    private void startMqtt()
    {
        mqttHelper=new MqttHelper(getApplicationContext());//chiama il costruttore e passa  l'intera applicazione come parametro (tutti i processi di tutte le activity)
        mqttHelper.setCallback(new MqttCallbackExtended() {//richiamo il metodo setCallback della classe mqttHelper a cui passo l'oggetto dell'interfaccia MQtt....
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
            Log.w("debug",message.toString());
            dataReceived.setText(message.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

    }
}