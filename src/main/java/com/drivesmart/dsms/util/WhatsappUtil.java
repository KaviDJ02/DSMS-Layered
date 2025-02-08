package com.drivesmart.dsms.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WhatsappUtil {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String API_URL = "https://waapi.app/api/v1/instances/28895/client/action/send-message";
    private static final String AUTH_TOKEN = "Bearer "+dotenv.get("WHATSAPP_API_TOKEN");

    public static boolean sendMessage(String chatId, String message) throws Exception {
        chatId = formatPhoneNumber(chatId);
        String fullChatId = chatId + "@c.us";
        String requestBody = String.format("{\"chatId\":\"%s\",\"message\":\"%s\"}", fullChatId, message);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .header("authorization", AUTH_TOKEN)
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() == 200;
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber.startsWith("0")) {
            return "94" + phoneNumber.substring(1);
        }
        return phoneNumber;
    }
}