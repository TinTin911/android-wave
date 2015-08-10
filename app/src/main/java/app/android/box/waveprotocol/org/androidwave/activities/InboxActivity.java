/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package app.android.box.waveprotocol.org.androidwave.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import app.android.box.waveprotocol.org.androidwave.R;

/**
 * Created by roshan on 6/25/15.
 */
public class InboxActivity extends AppCompatActivity{
    private Toolbar toolbar;
    ImageButton FAB;
    Button singOut;
    ImageButton search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);


        FAB = (ImageButton) findViewById(R.id.imageButton);
        search = (ImageButton) findViewById(R.id.ic_search);
        //singOut = (Button) findViewById(R.id.singOut);

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openNewWavectivity = new Intent("app.android.box.waveprotocol.org.androidwave.NEWWAVEACTIVITY");
                startActivity(openNewWavectivity);
                //test
                //setContentView(R.layout.activity_new_wave);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openNewWavectivity = new Intent("app.android.box.waveprotocol.org.androidwave.SEARCHACTIVITY");
                startActivity(openNewWavectivity);
                //test
                //setContentView(R.layout.activity_new_wave);
            }
        });

    }

//    @Override
////    public boolean onCreateOptionsMenu(Menu menu) {
////        // Inflate the menu; this adds items to the action bar if it is present.
////        getMenuInflater().inflate(R.menu.menu_main, menu);
////        return true;
////    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
////        if (id == R.id.action_search) {
////            Intent openSearchActivity = new Intent("app.android.box.waveprotocol.org.androidwave.SEARCHACTIVITY");
////            startActivity(openSearchActivity);
////        }
//
//        return super.onOptionsItemSelected(item);
    }

