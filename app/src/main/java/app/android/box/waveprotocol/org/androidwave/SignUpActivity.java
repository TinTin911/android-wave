///**
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// * <p/>
// * http://www.apache.org/licenses/LICENSE-2.0
// * <p/>
// * Unless required by applicable law or agreed to in writing,
// * software distributed under the License is distributed on an
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied.  See the License for the
// * specific language governing permissions and limitations
// * under the License.
// */
//
//package app.android.box.waveprotocol.org.androidwave;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLConnection;
//import java.net.URLEncoder;
//
//import app.android.box.waveprotocol.org.androidwave.app.android.box.waveprotocol.org.androidwave.util.Util;
//
///**
// * Apache Wave Sign Up Activity
// * Created by roshan on 6/24/15.
// */
//public class SignUpActivity extends Activity {
//
//    private static final String CHARSET = "utf-8";
//    EditText username;
//    EditText password;
//    EditText reEnterPassword;
//    Button signUp;
//    TextView signIn;
//
//    AsyncTask<String, Void, Boolean> waveSignUpTask;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        username = (EditText) findViewById(R.id.input_username);
//        password = (EditText) findViewById(R.id.input_password);
//        reEnterPassword = (EditText) findViewById(R.id.input_reEnterPassword);
//        signUp = (Button) findViewById(R.id.btn_signIn);
//        signIn = (TextView) findViewById(R.id.link_signup);
//
//        waveSignUpTask = new AsyncTask<String, Void, Boolean>() {
//
//            @Override
//            protected Boolean doInBackground(String... params) {
//                return waveSignUp(params[0], params[1], params[2]);
//            }
//
//            @Override
//            protected void onPostExecute(Boolean signUpResult) {
//                if (signUpResult)
//                    Toast.makeText(SignUpActivity.this, "User sign up successfully", Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(SignUpActivity.this, "User sign up fail", Toast.LENGTH_LONG).show();
//            }
//        };
//
//        signUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                waveSignUpTask.execute(Util.getHostAndUserNames(username.getText().toString())[0], Util.getHostAndUserNames(username.getText().toString())[0], password.getText().toString());
//            }
//        });
//
//        signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openLoginActivity = new Intent("app.android.box.waveprotocol.org.androidwave.LOGINACTIVITY");
//                startActivity(openLoginActivity);
//            }
//        });
//    }
//
//
//    /**
//     * This method get Wave server name, Wave user's username and Wave user's password as input parameters
//     * and it will invoke UserRegistrationServlet in the Wave server. If sign up get success the method
//     * will return true if not it return false
//     *
//     * @param host     Apache Wave hostname
//     * @param username Apache Wave user's username
//     * @param password Apache Wave user's password
//     * @return True or false
//     */
//    private boolean waveSignUp(String host, String username, String password) {
//
//        String servlet = "auth/register";
//        String hostURL = Util.hostCreator(host, servlet);
//        String httpQuery = "";
//        HttpURLConnection connection = null;
//
//        try {
//            httpQuery = "address=" + URLEncoder.encode(username, CHARSET) + "&password="
//                    + URLEncoder.encode(password, CHARSET);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            URL url = new URL(hostURL);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Accept-Charset", CHARSET);
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset="
//                    + CHARSET);
//
//            OutputStream out = connection.getOutputStream();
//            out.write(httpQuery.getBytes(CHARSET));
//
//            if (connection.getResponseCode() == 200) {
//                Log.i(SignUpActivity.class.getSimpleName(), "User sign up successfully");
//                return true;
//
//            } else {
//                Log.e(SignUpActivity.class.getSimpleName(), "User sign up fail");
//                return false;
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            connection.disconnect();
//        }
//
//        return false;
//    }
//
//}
