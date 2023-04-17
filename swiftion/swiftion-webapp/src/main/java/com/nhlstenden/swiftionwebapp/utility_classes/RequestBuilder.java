package com.nhlstenden.swiftionwebapp.utility_classes;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.reactive.function.client.WebClient;

/*****************************************************************
 * This class is the request builder class. It builds a Post request
 * to the API
 ****************************************************************/
public class RequestBuilder {


    public static String buildPostRequestDbAPI(String url, String mode) {
        Dotenv env = Dotenv.load();

        WebClient client = WebClient.create();
        return client.post()
                .uri(String.format(env.get("API_PATH") + ":" + env.get("API_PORT") + url + "&%s=true", mode.toLowerCase()))
                .header("Authorization", env.get("API_TOKEN"))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
                .block();

    }


}