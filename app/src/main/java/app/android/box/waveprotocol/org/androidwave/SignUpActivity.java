package app.android.box.waveprotocol.org.androidwave;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by roshan on 6/24/15.
 */
public class SignUpActivity extends Activity {

    private static final String CHARSET = "utf-8";
    EditText username;
    EditText password;
    EditText reEnterPassword;
    Button signUp;
    TextView signIn;

    AsyncTask<String, Void, Boolean> waveSignUpTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        reEnterPassword = (EditText) findViewById(R.id.input_reEnterPassword);
        signUp = (Button) findViewById(R.id.btn_signIn);
        signIn = (TextView) findViewById(R.id.link_signup);

        waveSignUpTask = new AsyncTask<String, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                return waveSignUp(params[0], params[1], params[2]);
            }

            @Override
            protected void onPostExecute(Boolean signUpResult) {
                if (signUpResult)
                    Toast.makeText(SignUpActivity.this, "User sign up successfully", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(SignUpActivity.this, "User sign up fail", Toast.LENGTH_LONG).show();
            }
        };

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveSignUpTask.execute(getHostName(username.getText().toString()), username.getText().toString(), password.getText().toString());
                Intent openInboxActivity = new Intent("app.android.box.waveprotocol.org.androidwave.INBOXACTIVITY");
                startActivity(openInboxActivity);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLoginActivity = new Intent("app.android.box.waveprotocol.org.androidwave.LOGINACTIVITY");
                startActivity(openLoginActivity);
            }
        });
    }

    private boolean waveSignUp(String host, String username, String password) {

        String hostURL = hostCreator(host);
        String httpQuery = "";
        HttpURLConnection connection = null;

        try {
            httpQuery = "address=" + URLEncoder.encode(username, CHARSET) + "&password="
                    + URLEncoder.encode(password, CHARSET);
        } catch (UnsupportedEncodingException e) {
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
                Log.i(SignUpActivity.class.getSimpleName(), "User sign up successfully");
                return true;

            } else {
                Log.e(SignUpActivity.class.getSimpleName(), "User sign up fail");
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }

        return true;
    }

    private String getHostName(String username) {

        String hostName = "";

        String[] usernameAndHost = username.split("@");

        if (usernameAndHost.length > 1) {
            return usernameAndHost[1];
        }

        return hostName;
    }

    private String hostCreator(String hostname) {

        StringBuilder hostUrl = new StringBuilder();

        if (hostname.equalsIgnoreCase("local.net")) {

            hostname = "localhost:9898";

        }

        return hostUrl.append("http://").append(hostname).toString();
    }

//    private String httpQueryCreator(String host, String userName, String password){
//
//        StringBuilder httpQuery = new StringBuilder();
//
//
//
//    }

}
