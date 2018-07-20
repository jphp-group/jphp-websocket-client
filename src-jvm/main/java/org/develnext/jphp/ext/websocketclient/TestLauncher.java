package org.develnext.jphp.ext.websocketclient;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TestLauncher {
    public static void main(String[] args) throws Exception {
        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization", "Bot NDA3NDMzMDIzNjk3NTg0MTQ4.Di9yFg.DobrE8uXRMBKKjPy79F6CGV8C60");
        SimpleWebSocketClient client = new SimpleWebSocketClient(new URI("wss://gateway.discord.gg/?v=6&encoding=json"));
        client.connectBlocking();

        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        client.close();
    }
    static class SimpleWebSocketClient extends WebSocketClient {
        public SimpleWebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
            super(serverUri, httpHeaders);
        }
        public SimpleWebSocketClient(URI serverUri) {
            super(serverUri);
        }
        @Override
        public void onOpen(ServerHandshake handshakedata) {
            System.out.println("Status: "+handshakedata.getHttpStatusMessage());
        }
        @Override
        public void onMessage(String message){
            System.out.println("Message: "+message);
            if(message.contains("op\":10")){
                send("{ \"op\": 11 }");
            }
        }
        @Override
        public void onClose(int code, String reason, boolean remote){
            System.out.printf("Closed: code - %s;message - %s", code, reason);
        }
        @Override
        public void onError(Exception ex){
            ex.printStackTrace();
        }
    }
}
