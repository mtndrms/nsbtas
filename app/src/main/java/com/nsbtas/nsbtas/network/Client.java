package com.nsbtas.nsbtas.network;

import com.contentful.java.cda.CDAClient;

public class Client {
    public static CDAClient client = null;

    public static CDAClient getClient() {
        if (client == null) {
            client = CDAClient.builder()
                    .setSpace("2u27d4lk17xx")
                    .setToken("keo8d4fz1uumyI_d7q7143xIqGXl4xpQmJQMgoKK0e4")
                    .build();
        }
        return client;
    }
}
