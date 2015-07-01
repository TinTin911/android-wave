package app.android.box.waveprotocol.org.androidwave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by roshan on 6/24/15.
 */
public class LoginActivity extends Activity {

    Button login;
    TextView singup;
    EditText email;
    EditText password;


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
}
