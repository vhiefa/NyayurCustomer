package com.vhiefa.nyayur.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vhiefa.nyayur.R;
import com.vhiefa.nyayur.app.AppConfig;
import com.vhiefa.nyayur.helper.JSONParser;
import com.vhiefa.nyayur.helper.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnRegister;
    private Button btnLinkToLogin;
    private EditText inputFullName;
    private EditText inputnohp;
    private EditText inputPassword;
    private EditText inputAlamat;
    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputFullName = (EditText) findViewById(R.id.name);
        inputnohp = (EditText) findViewById(R.id.nohp);
        inputAlamat = (EditText) findViewById(R.id.alamat);
        inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());


        // Check if user is already logged in or not
    /*    if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    BelanjaActivity.class);
            startActivity(intent);
            finish();
        } */

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputFullName.getText().toString().trim();
                String nohp = inputnohp.getText().toString().trim();
                String alamat = inputAlamat.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (!name.isEmpty() && !nohp.isEmpty() && !alamat.isEmpty() && !password.isEmpty()) {
                    //registerUser(name, nohp, alamat, password);
                    registerUser m= (registerUser) new registerUser().execute(name, nohp, alamat, password);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */


    class registerUser extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;
        JSONParser jParser = new JSONParser();
        JSONArray posts = null;
        String errorMsg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Loading..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String name  = sText[0];
            String nohp = sText[1];
            String alamat = sText[2];
            String password = sText[3];
            String returnResult = createAccount(name, nohp, alamat, password);
            return returnResult;

        }

        public String createAccount(String name,String nohp,String alamat,String password)
        {

            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("name", name));
            parameter.add(new BasicNameValuePair("no_hp", nohp));
            parameter.add(new BasicNameValuePair("alamat", alamat));
            parameter.add(new BasicNameValuePair("password", password));

            try {
                String url_reg = AppConfig.URL_REGISTER;

                JSONObject jObj = jParser.makeHttpRequest(url_reg,"POST", parameter);
                boolean error = jObj.getBoolean("error");

                if (!error) {
                  /*  String uid = jObj.getString("uid");

                    JSONObject user = jObj.getJSONObject("user");
                    String name = user.getString("name");
                    String nohp = user.getString("no_hp");
                    String alamat = user.getString("alamat");
                    String tukangsayur = user.getString("tukangsayur");
                    String created_at = user.getString("created_at");

                    // Inserting row in users table
                    db.addUser(name, nohp, alamat, tukangsayur, uid, created_at); */
                    return "OK";
                }
                else {
                    errorMsg = jObj.getString("error_msg");
                    return "fail";
                }

            } catch (Exception e) {
                errorMsg = e.getMessage();
                e.printStackTrace();
                return "Exception Caught";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();

            if (result.equalsIgnoreCase("OK")) {
                Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                // Launch login activity
                Intent intent = new Intent(
                        RegisterActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_LONG).show();
            }

    }

    }


}
