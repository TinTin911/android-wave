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
public class SignUpActivity extends Activity{

    EditText username;
    EditText password;
    EditText reEnterPassword;
    Button signUp;
    TextView signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        reEnterPassword = (EditText) findViewById(R.id.input_reEnterPassword);
        signUp = (Button) findViewById(R.id.btn_signIn);
        signIn = (TextView) findViewById(R.id.link_signup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
