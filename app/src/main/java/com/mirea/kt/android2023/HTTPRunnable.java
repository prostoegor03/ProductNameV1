package com.mirea.kt.android2023;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;




public class HTTPRunnable extends MainActivity implements Runnable{
    private String address;
    private HashMap<String,String> requestBody;
    private String responseBody;

    public HTTPRunnable(String address, HashMap<String, String> requestBody) {
        this.address = address;
        this.requestBody = requestBody;
    }

    public String getResponseBody() {
        return responseBody;
    }

    private String generateStringBody(){
        StringBuilder sbParams = new StringBuilder();

        if(this.requestBody != null && !requestBody.isEmpty()){
            int i =0;
            for(String key : this.requestBody.keySet()){
                try{
                    if (i != 0){
                        sbParams.append("&");
                    }
                    sbParams.append(key).append("=").append(URLEncoder.encode(this.requestBody.get(key), "UTF-8"));
                }
                catch(UnsupportedEncodingException e){
                    e.printStackTrace();
                    Log.d("Errors HTTP","Проблемы с форматом",e);
                }
                i++;
            }

        }
        return sbParams.toString();
    }
//Собирает пакет, который будет отправлен на сервер(идет по ключам HashMap и зачовывает значения чере амперсант)
//метод encode перегоняет все в заданную кодировку и пакуют в массив симаолов
    @Override
    public void run() {
        if(this.address != null && ! this.address.isEmpty()){
            try{
                URL url = new URL(this.address);
                URLConnection connection = url.openConnection();//Возвращае экземпляр класса URLConnection
                HttpURLConnection httpConnection = (HttpURLConnection)connection;//Установили соединение
                httpConnection.setRequestMethod("POST");//Установили тип запроса
                httpConnection.setDoOutput(true);//Флаг показывающий,что мы собираемся вытаскивать с сервера данные


                OutputStreamWriter osw = new OutputStreamWriter(httpConnection.getOutputStream());//Открываем поток для того чтобы сформировать запрос с кодировкой
                osw.write(generateStringBody());//Записываем в тело запроса данные из метода generateToStringBode
                osw.flush();//сбрасываем поток


                int responseCode = httpConnection.getResponseCode();//Вытаскиваем код состояния из ответа

                if(responseCode == 200){
                    BufferedReader br = null;//хранит считанные значения(Специальный класс наследуется от Reader)

                    InputStreamReader isr = new InputStreamReader(httpConnection.getInputStream());//считывает поток данных из открытого соединения
                    br = new BufferedReader(isr);


                    String currentLine;

                    StringBuilder sbResponce = new StringBuilder();//Класс для работы со строками
                    while((currentLine = br.readLine())!= null){
                        sbResponce.append(currentLine);//Считываем по строка  и записываем все что пришло
                    }
                    responseBody =  sbResponce.toString();
                    isr.close();
                }else{
                    Toast.makeText(getApplicationContext(),"Неудалось установить соединение с сервером",Toast.LENGTH_LONG).show();
                }

            }
            catch(IOException iex){
                Log.d("HTTP","Отвалился run",iex);
            }
        }


    }
}