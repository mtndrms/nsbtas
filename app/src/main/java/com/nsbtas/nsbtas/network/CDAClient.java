package com.nsbtas.nsbtas.network;

import com.nsbtas.nsbtas.utils.Constants;

public class CDAClient {
    private static com.contentful.java.cda.CDAClient client = null;

    public static com.contentful.java.cda.CDAClient getClient() {
        if (client == null) {
            client = com.contentful.java.cda.CDAClient.builder()
                    .setSpace(Constants.spaceID)
                    .setToken(Constants.apiToken)
                    .setEnvironment("master")
                    .build();
        }
        return client;
    }
}
