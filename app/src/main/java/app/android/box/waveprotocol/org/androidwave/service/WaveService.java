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

import org.waveprotocol.wave.model.id.IdGenerator;
import org.waveprotocol.wave.model.id.IdGeneratorImpl;
import org.waveprotocol.wave.model.wave.ParticipantId;

import app.android.box.waveprotocol.org.androidwave.models.TypeIdGenerator;

public class WaveService {

    private String waveHost;
    private String waveSessionId;
    private String waveUsername;

    private ParticipantId participantId;
    private IdGenerator idGenerator;
    private TypeIdGenerator typeIdGenerator;


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

    public String getWaveHost() {
        return waveHost;
    }

    public void setWaveHost(String waveHost) {
        this.waveHost = waveHost;
    }

    public String getWaveUsername() {
        return waveUsername;
    }

    public void setWaveUsername(String waveUsername) {
        this.waveUsername = waveUsername;
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
                openWebSocketConnection(waveHost, waveSessionId);
            }
        }
    }

    public boolean isWaveSessionStarted() {
        return waveSessionId != null;
    }

    private void openWebSocketConnection(String hostName, String SessionId){

        String webSocketUrl = "http://"+ hostName +"/atmosphere";

        idGenerator = new IdGeneratorImpl(webSocketUrl, new IdGeneratorImpl.Seed() {
            @Override
            public String get() {
                return waveSessionId.substring(0, 5);
            }
        });

        typeIdGenerator = TypeIdGenerator.get(idGenerator);



    }

}
