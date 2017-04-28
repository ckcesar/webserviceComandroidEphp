package com.example.n1e.webservice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.n1e.webservice.conexao.ConexaoHttpClient;
import com.example.n1e.webservice.dao.User;
import com.example.n1e.webservice.modelo.UserPass;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Index extends Activity {

    EditText etUsu, etSe;
    Button Acessar;
    private String json;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

                Acessar = (Button) findViewById(R.id.btnentrar);
                etUsu = (EditText) findViewById(R.id.etUsuario);
                etSe = (EditText) findViewById(R.id.atSenha);

                Acessar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Logar();
                    }
                });
    }

    public void Logar(){
        new Thread() {
            @Override
            public void run() {
                super.run();
                Log.i("Entrou => ", "Executando o Thread");
                String urlPost="http://192.168.237.9/logar.php";
                ArrayList<NameValuePair> ParametroPost = new ArrayList<NameValuePair>();
                ParametroPost.add(new BasicNameValuePair("usuario",etUsu.getText().toString()));
                ParametroPost.add(new BasicNameValuePair("senha",etSe.getText().toString()));
                String respostaRetornada = null;
                try {
                    Log.i("Entrou no => ","Try");
                    respostaRetornada = ConexaoHttpClient.executaHttpPost(urlPost, ParametroPost);
                    String resposta = respostaRetornada.toString();
                    Log.i("#","Resposta => "+resposta);
                    resposta = resposta.replaceAll("\\s+", "");
                    if(resposta.equals("0")){
                        Log.i("#","Nem vai rolar Mano => "+resposta);
                    }else{
                        Log.i("#","Mano vc logou ");
                        GerarJson(resposta);
                        //Aqui dar continuidade e navegar nas paginas

                    }
                }
                catch(Exception erro){
                    Context logar;
                    Log.i("Conexao","erro => "+erro);
                    Log.i("#","Sem Net Men");
                    Log.i("#","==============//-========");
                    LogarOff(etUsu.getText().toString(),etSe.getText().toString());
                }
            }
        }.start();
    }

    public void GerarJson(final String resposta){
        Log.i("#","Methodo novo");
        Log.i("#","==============//-========");
        try {
            JSONArray pessoasJson = new JSONArray(resposta);
            JSONObject pessoa;

            JSONObject jsonObject = new JSONObject();
            FileWriter writeFile = null;

            for (int i = 0; i < pessoasJson.length(); i++) {
                pessoa = new JSONObject(pessoasJson.getString(i));
                String id     = pessoa.getString("id");
                String email  = pessoa.getString("email");
                String password_hash = pessoa.getString("password_hash");
                String customer_id   = pessoa.getString("customer_id");

                //Uso para gerar valores JSON
              //  jsonObject.put("id", id);
               // jsonObject.put("email", email);
               // jsonObject.put("password_hash", password_hash);
               // jsonObject.put("customer_id", customer_id);

                    //Aqui conecto no banco do celular e faz um registro
                    try{
                        User aDao = new User(this);
                        UserPass a = new UserPass();

                        a.setId(id);
                        a.setEmail(email);
                        a.setPassword_hash(password_hash);
                        a.setCustomer_id(customer_id);

                        if(aDao.getString(id) == "1"){
                            aDao.update(a);
                            Log.i("#","Usuário Alterado");
                        }else{
                            aDao.add(a);
                            Log.i("#","Usuário Salvo");
                        }

                    }catch (Exception e) {
                        Log.i("#","Não Gravou");
                    }
            }
            //Aqui eu trato para registrar dentro de um JSON e salvar na parte interna do Android
          //  try{
              //  writeFile = new FileWriter("/sdcard/user.json");
              //  //Escreve no arquivo conteudo do Objeto JSON
            //    writeFile.write(jsonObject.toString());
             //   writeFile.close();
          //      Log.i("#","Usuário => "+jsonObject.toString());
          //  }catch (IOException e){
               // Log.d("Error ", e.getMessage());
         //   }


        } catch (JSONException e) {
            Log.e("Erro", "Erro no parsing do JSON", e);
        }

    }

    public void LogarOff(final String email, String senha){

        Log.i("#","Usuario=>"+email);
        Log.i("#","Senha=>"+senha);
        //Aqui logar no sistema offline, pegando dados do banco sqlite
        try{
            User aDao = new User(this);
            UserPass a = new UserPass();

            a.setEmail(email);
            a.setPassword_hash(senha);

            if(aDao.Logar(email,senha) == "1"){
                Log.i("#","Usuário Logado");
            }else{
                Log.i("#","Usuário Não Loghado");
            }

        }catch (Exception e) {
            Log.i("#","Não Conectou corretamente");
        }


    }


}
