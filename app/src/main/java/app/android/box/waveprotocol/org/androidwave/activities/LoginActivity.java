/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package app.android.box.waveprotocol.org.androidwave.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import app.android.box.waveprotocol.org.androidwave.R;
import app.android.box.waveprotocol.org.androidwave.util.Util;

/**
 * Apache Wave Sign In Activity
 * Created by roshan on 6/24/15.
 */
public class LoginActivity extends Activity {

    private static final String CHARSET = "utf-8";
    private static String WAVE_SESSION_COOKIE = "WSESSIONID";

    Button login;
    TextView singup;
    EditText email;
    EditText password;

    AsyncTask<String, Void, String> waveSignInTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.btn_login);
        singup = (TextView) findViewById(R.id.link_signup);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openInboxActivity = new Intent("app.android.box.waveprotocol.org.androidwave.INBOXACTIVITY");
                startActivity(openInboxActivity);

            }
        });

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openSingupActivity = new Intent("app.android.box.waveprotocol.org.androidwave.SINGUPACTIVITY");
                startActivity(openSingupActivity);
            }
        });

    }

    /**
     * This method get Wave server name, Wave user's username and Wave user's password as input parameters
     * and it will invoke AuthenticationServlet in the Wave server. If sign in get success the method
     * will return session id
     *
     * @param host     Apache Wave hostname
     * @param username Apache Wave user's username
     * @param password Apache Wave user's password
     * @return Apache Wave user's sessionId
     */
    private String waveSignIn(String host, String username, String password) {

        String sessionId = null;

        String servlet = "auth/signin?r=none";
        String hostURL = Util.hostCreator(host, servlet);
        String httpQuery = "";
        HttpURLConnection connection = null;

        try {
            httpQuery = "address=" + URLEncoder.encode(username, "UTF-8") + "&password="
                    + URLEncoder.encode(password, CHARSET) + "&signIn="
                    + URLEncoder.encode("Sign+in", CHARSET);

        } catch (UnsupportedEncodingException e) {
            Log.e(LoginActivity.class.getSimpleName(), "Http Query encoding error");
            e.printStackTrace();
        }

        try {

            URL url = new URL(hostURL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestProperty("Accept-Charset", CHARSET);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset="
                    + CHARSET);

            OutputStream out = connection.getOutputStream();
            out.write(httpQuery.getBytes(CHARSET));


            if (connection.getResponseCode() == 200) {

                List<String> cookies = connection.getHeaderFields().get("Set-Cookie");

                for (String c : cookies) {
                    if (c.startsWith(WAVE_SESSION_COOKIE)) {

                        String cookie = c;

                        if (cookie.contains(";"))
                            cookie = cookie.split(";")[0];

                        sessionId = cookie.split("=")[1];
                        break;
                    }
                }

                if (sessionId == null) {
                    Log.e(LoginActivity.class.getSimpleName(), "Cookie session not found");
                }


            } else {
                Log.e(LoginActivity.class.getSimpleName(), "Http error");
            }

        } catch (Exception e) {
            Log.e(LoginActivity.class.getSimpleName(), "Http login error");
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return sessionId;
    }
}
