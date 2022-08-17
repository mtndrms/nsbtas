package com.nsbtas.nsbtas.network;

import com.nsbtas.nsbtas.utils.Constants;

public class CMAClient {
    private static com.contentful.java.cma.CMAClient client = null;

    public static com.contentful.java.cma.CMAClient getClient() {
        if (client == null) {
            client = new com.contentful.java.cma.CMAClient.Builder()
                    .setAccessToken(Constants.apiKey)
                    .setSpaceId(Constants.spaceID)
                    .setEnvironmentId("master")
                    .build();
        }
        return client;
    }
}
