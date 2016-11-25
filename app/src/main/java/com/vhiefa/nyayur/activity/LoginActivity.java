package com.vhiefa.nyayur.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vhiefa.nyayur.helper.JSONParser;
import com.vhiefa.nyayur.R;
import com.vhiefa.nyayur.app.AppConfig;
import com.vhiefa.nyayur.helper.SessionManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends Activity {
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputNoHp;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputNoHp = (EditText) findViewById(R.id.nohp);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, TambahTukang.class);
            startActivity(intent);
            finish();
        }

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String nohp = inputNoHp.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!nohp.isEmpty() && !password.isEmpty()) {
                    // login user
                 //   checkLogin(nohp, password);
                    checkLogin m= (checkLogin) new checkLogin().execute(nohp, password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    /**
     * function to verify login details in mysql db
     * */



    class checkLogin extends AsyncTask<String, Void, String>
    {
        ProgressDialog pDialog;
        JSONParser jParser = new JSONParser();
        String errorMsg;

        String uid, name, nohp,tukangsayur, alamat ,created_at ;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... sText) {
            String nohp_ = sText[0];
            String password_ = sText[1];
            String returnResult = getUserAccount(nohp_, password_);
            return returnResult;

        }

        public String getUserAccount(String nohp_, String password_)
        {

            List<NameValuePair> parameter = new ArrayList<NameValuePair>();
            parameter.add(new BasicNameValuePair("no_hp", nohp_));
            parameter.add(new BasicNameValuePair("password", password_));

            try {
                String url_ = AppConfig.URL_LOGIN;

                JSONObject jObj = jParser.makeHttpRequest(url_, "POST", parameter);
                boolean error = jObj.getBoolean("error");

                if (!error)  {
                  //  session.setLogin(true);

                    // Now store the user in SQLite
                    uid = jObj.getString("uid");

                    JSONObject user = jObj.getJSONObject("user");
                    name = user.getString("name");
                    nohp = user.getString("no_hp");
                    tukangsayur = user.getString("tukangsayur");
                    alamat = user.getString("alamat");
                    created_at = user.getString("created_at");

                    // Inserting row in users table
                   // db.addUser(name, nohp, alamat, tukangsayur, uid, created_at);

                    return "OK";

                }
                else {
                    //Tidak Ada Record Data (SUCCESS = 0)
                    //EMAIL ATAU PASSWORD SALAH
                    errorMsg = jObj.getString("error_msg");
                    return "error";
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

                // Creating user login session and putting user data in preference
                session.createLoginSession(name, nohp, alamat, tukangsayur, uid, created_at);

                if (tukangsayur == "null") {
                    Intent intent = new Intent(LoginActivity.this,
                            TambahTukang.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(LoginActivity.this,
                            MainActivity.class);
                    intent.putExtra("tukangsayur", tukangsayur);
                    startActivity(intent);
                    finish();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),
                        errorMsg, Toast.LENGTH_LONG).show();
            }
        }

    }


   /* private void checkLogin(final String nohp, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        //pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();
                pDialog.setMessage(response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String nohp = user.getString("no_hp");
                        String tukangsayur = user.getString("tukangsayur");
                        String alamat = user.getString("alamat");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, nohp, alamat, tukangsayur, uid, created_at);

                        if (tukangsayur == "null")
                        {
                            Intent intent = new Intent(LoginActivity.this,
                                    TambahTukang.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Intent intent = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            intent.putExtra("tukangsayur", tukangsayur);
                            startActivity(intent);
                            finish();
                        }


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("no_hp", nohp);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    } */
}