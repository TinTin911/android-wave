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

package app.android.box.waveprotocol.org.androidwave.service;

import android.os.AsyncTask;

public class WaveService {

    private String waveSessionId;

    public boolean waveSignUpTask(String host, String username, String password){
        WaveSignUp waveSignUpService = new WaveSignUp();
        return waveSignUpService.waveSignUp(host,username,password);
    }

    public void waveLoginTask(String host, String username, String password){
        new WaveSession().execute(host,username,password);
    }

    public String getWaveSessionId() {
        return waveSessionId;
    }

    public void setWaveSessionId(String waveSessionId) {
        this.waveSessionId = waveSessionId;
    }

    public class WaveSession extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            WaveSignIn waveSignIn = new WaveSignIn();
            waveSessionId =waveSignIn.waveSignIn(params[0], params[1], params[2]);
            return waveSessionId;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null){

            }
        }
    }

}
