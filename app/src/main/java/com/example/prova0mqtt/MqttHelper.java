package com.example.prova0mqtt;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttHelper {
    public MqttAndroidClient mqttAndroidClient;//classe importata da Paho
    final String serverURI="tcp://gdandria.ovh:1883";//tcp://indirizzo del server: porta
    final String clientId="EsempioAndroidClient";
    final String topic="temperatura";
    final String username="mqttAll";
    final String password="mqttAll";
    public MqttHelper(Context context)
    {
     mqttAndroidClient=new MqttAndroidClient(context, serverURI,clientId);//Context=Interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system. It allows access to application-specific resources and classes, as well as up-calls for application-level operations such as launching activities, broadcasting and receiving intents, etc

        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
         @Override
         public void connectComplete(boolean reconnect, String serverURI) {
             Log.w("mqtt",serverURI);
         }

         @Override
         public void connectionLost(Throwable cause) {

         }

         @Override
         public void messageArrived(String topic, MqttMessage message) throws Exception {
             Log.w("Mqtt",message.toString());
         }

         @Override
         public void deliveryComplete(IMqttDeliveryToken token) {

         }
     });
     connect();
    }
    public void setCallback (MqttCallbackExtended callback)//passo la connessione (dato arriva
    {
        mqttAndroidClient.setCallback(callback);
    }

    private void connect(){


        MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    DisconnectedBufferOptions disconnectedBufferOptions=new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopics();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverURI + exception.toString());

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
private void subscribeToTopics()
{
    try {
        mqttAndroidClient.subscribe(topic, 0, null, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                Log.w("Mqtt","Subscribed!");
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                Log.w("Mqtt", "Subscribed fail!");

            }
        });
    } catch (MqttException e) {
        e.printStackTrace();
    }
}

}
