package com.nsbtas.nsbtas.network;

import com.contentful.java.cda.CDAClient;
import com.nsbtas.nsbtas.utils.Constants;

public class Client {
    private static CDAClient client = null;

    public static CDAClient getClient() {
        if (client == null) {
            client = CDAClient.builder()
                    .setSpace(Constants.spaceID)
                    .setToken(Constants.apiToken)
                    .setEnvironment("master")
                    .build();
        }
        return client;
    }
}
