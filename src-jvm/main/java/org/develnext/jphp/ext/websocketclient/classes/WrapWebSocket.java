package org.develnext.jphp.ext.websocketclient.classes;


import com.neovisionaries.ws.client.*;
import org.develnext.jphp.ext.websocketclient.WebSocketClientExtension;
import php.runtime.Memory;
import php.runtime.annotation.Reflection;
import php.runtime.common.collections.map.HashedMap;
import php.runtime.env.Environment;
import php.runtime.exceptions.CriticalException;
import php.runtime.ext.core.classes.stream.MemoryStream;
import php.runtime.ext.core.classes.stream.MiscStream;
import php.runtime.invoke.Invoker;
import php.runtime.lang.BaseObject;
import php.runtime.memory.ArrayMemory;
import php.runtime.memory.ObjectMemory;
import php.runtime.memory.support.MemoryOperation;
import php.runtime.reflection.ClassEntity;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.List;

@Reflection.Namespace(WebSocketClientExtension.NS)
@Reflection.Name("WebSocket")
public class WrapWebSocket extends BaseObject{
    public WrapWebSocket(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    private String serverName;
    private String url;
    private int connectionTimeout;
    private boolean verifyHostName;
    private String userInfo;

    private String proxyHost;
    private int proxyPort;
    private String proxyPassword;
    private boolean proxySecure;
    private Map<String, List<String>> headers = new HashMap<>();


    private WebSocket webSocket;
    private Map<String,Map<String,Invoker>> handlers = new HashMap<>();


    @Reflection.Signature
    public void __construct(){

    }

    @Reflection.Getter
    public String getServerName(){
        return serverName;
    }
    @Reflection.Setter
    public void setServerName(String serverName){
        this.serverName = serverName;
    }

    @Reflection.Getter
    public String getUrl(){
        return url;
    }
    @Reflection.Setter
    public void setUrl(String url){
        this.url = url;
    }

    @Reflection.Getter
    public String getUserInfo(){
        return userInfo;
    }
    @Reflection.Setter
    public void setUserInfo(String userInfo){
        this.userInfo = userInfo;
    }

    @Reflection.Getter
    public int getConnectionTimeout(){
        return connectionTimeout;
    }
    @Reflection.Setter
    public void setConnectionTimeout(int connectionTimeout){
        this.connectionTimeout = connectionTimeout;
    }

    @Reflection.Getter
    public String getProxyHost(){
        return proxyHost;
    }
    @Reflection.Setter
    public void setProxyHost(String proxyHost){
        this.proxyHost = proxyHost;
    }

    @Reflection.Getter
    public int getProxyPort(){
        return proxyPort;
    }
    @Reflection.Setter
    public void setProxyPort(int proxyPort){
        this.proxyPort = proxyPort;
    }

    @Reflection.Getter
    public String getProxyPassword(){
        return proxyPassword;
    }
    @Reflection.Setter
    public void setProxyPassword(String proxyPassword){
        this.proxyPassword = proxyPassword;
    }

    @Reflection.Getter("proxySecure")
    public boolean isProxySecure(){
        return proxySecure;
    }
    @Reflection.Setter
    public void setProxySecure(boolean proxySecure){
        this.proxySecure = proxySecure;
    }

    @Reflection.Getter("verifyHostName")
    public boolean isVerifyHostName(){
        return verifyHostName;
    }
    @Reflection.Setter
    public void setVerifyHostName(boolean verifyHostName){
        this.verifyHostName = verifyHostName;
    }

    @Reflection.Signature
    public void addHeader(String name, String value){
        if(!headers.containsKey(name)){
            headers.put(name, new ArrayList<>());
        }
        headers.get(name).add(value);
    }
    @Reflection.Signature
    public void removeHeader(String name){
        headers.remove(name);
    }
    @Reflection.Signature
    public void clearHeaders(){
        headers.clear();
    }
    @Reflection.Signature
    public Map<String,List<String>> getHeaders(){
        return headers;
    }

    @Reflection.Signature
    public void connect() throws IOException,WebSocketException {
        connect(url);
    }
    @Reflection.Signature
    public void connect(String url) throws IOException,WebSocketException {
        connect(url, connectionTimeout);
    }
    @Reflection.Signature
    public void connect(String url, int timeout) throws IOException,WebSocketException {
        connect(url, timeout, false);
    }

    @Reflection.Signature
    public void connectAsynchronously() throws IOException,WebSocketException {
        connectAsynchronously(url);
    }
    @Reflection.Signature
    public void connectAsynchronously(String url) throws IOException,WebSocketException {
        connectAsynchronously(url, connectionTimeout);
    }
    @Reflection.Signature
    public void connectAsynchronously(String url, int timeout) throws IOException,WebSocketException {
        connect(url, timeout, true);
    }

    private void connect(String url, int timeout, boolean async) throws IOException,WebSocketException{
        if(webSocket != null){
            throw new IllegalStateException("Already opened");
        }
        // TODO check state
        url = url == null ? this.url : url;
        WebSocketFactory factory = new WebSocketFactory();
        factory.setServerName(serverName);
        factory.setConnectionTimeout(timeout);
        factory.setVerifyHostname(verifyHostName);

        ProxySettings proxy = factory.getProxySettings();
        proxy.setHost(proxyHost);
        proxy.setPort(proxyPort);
        proxy.setPassword(proxyPassword);
        proxy.setSecure(proxySecure);

        webSocket = factory.createSocket(url);
        webSocket.addListener(new CustomWebSocketListener());
        for(Map.Entry<String, List<String>> headerEntry : headers.entrySet()){
            String headerName = headerEntry.getKey();
            for(String headerValue : headerEntry.getValue()) {
                webSocket.addHeader(headerName, headerValue);
            }
        }
        webSocket.setUserInfo(userInfo);
        if(async){
            webSocket.connectAsynchronously();
        }
        else{
            webSocket.connect();
        }
    }

    @Reflection.Signature
    public void disconnect() throws IOException, WebSocketException{
        disconnect(WebSocketCloseCode.NORMAL, null);
    }
    @Reflection.Signature
    public void disconnect(int statusCode) throws IOException, WebSocketException{
        disconnect(statusCode, null);
    }
    @Reflection.Signature
    public void disconnect(int statusCode, String reason) throws IOException, WebSocketException{
        if(webSocket == null){
            throw new IllegalStateException("Not connected");
        }
        webSocket.disconnect(statusCode, reason);
    }

    @Reflection.Signature
    public void sendText(String text){
        webSocket.sendText(text);
    }
    @Reflection.Signature
    public void sendFrame(WebSocketFrame frame){
        webSocket.sendFrame(frame);
    }

    @Reflection.Signature
    public void on(String event, Invoker handler, String group){
        if(!handlers.containsKey(event)){
            handlers.put(event, new HashMap<>());
        }
        handlers.get(event).put(group, handler);
    }
    @Reflection.Signature
    public void on(String event, Invoker handler){
        on(event, handler, "general");
    }
    @Reflection.Signature
    public void off(String event, String group){
        if(!handlers.containsKey(event)){
            return;
        }
        if(group == null){
            handlers.get(event).clear();
        }
        else{
            handlers.get(event).remove(group);
        }
    }
    @Reflection.Signature
    public void off(String event){
        off(event, null);
    }
    private void trigger(String event, Object ...objects){
        if(!handlers.containsKey(event)){
            return;
        }
        Memory[] args = new Memory[objects.length];
        for(int i = 0; i < objects.length; i++){
            Object object = objects[i];
            //System.out.println(object);
            if(object == null){
                args[i] = Memory.NULL;
            }
            else if(object instanceof Memory){
                //System.out.println(object);
                args[i] = (Memory)object;
            }
            else{
                Memory memory;
                try{
                    memory = Memory.wrap(getEnvironment(), object);
                }
                catch(Throwable e){
                    //e.printStackTrace();
                    continue;
                }
                if(memory == null){
                    throw new CriticalException(String.format("Unsupported bind type %s", object.getClass().getName()));
                }
                args[i] = memory;
            }
        }

        for(Invoker invoker : handlers.get(event).values()){
            try{
                //System.out.println(event);
                invoker.call(args);
            }
            catch(Throwable e){
                // e.printStackTrace();
            }
        }
    }

    private class CustomWebSocketListener implements WebSocketListener{
        @Override
        public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {
            trigger("stateChanged", newState.toString());
        }

        @Override
        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
            trigger("connected",  new LinkedHashMap<>(headers));
        }

        @Override
        public void onConnectError(WebSocket websocket, WebSocketException cause) throws Exception {
            trigger("connectError", cause);
        }

        @Override
        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
            WrapWebSocket.this.webSocket = null;
            trigger("disconnected", serverCloseFrame, clientCloseFrame, closedByServer);
        }

        @Override
        public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
            trigger("frame", frame);
        }

        @Override
        public void onContinuationFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
            trigger("continuationFrame", frame);
        }

        @Override
        public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
            trigger("textFrame", frame);
        }

        @Override
        public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
            trigger("binaryFrame", frame);
        }

        @Override
        public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
            trigger("closeFrame", frame);
        }

        @Override
        public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
            trigger("pingFrame", frame);
        }

        @Override
        public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
            trigger("pongFrame", frame);
        }

        @Override
        public void onTextMessage(WebSocket websocket, String text) throws Exception {
            trigger("textMessage", text);
        }

        @Override
        public void onTextMessage(WebSocket websocket, byte[] data) throws Exception {
            //trigger("rawTextMessage", new ObjectMemory(new MiscStream()));
        }

        @Override
        public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
            trigger("binaryMessage", new ByteArrayInputStream(binary));
        }

        @Override
        public void onSendingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
            trigger("sendingFrame", frame);
        }

        @Override
        public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception {
            trigger("frameSent", frame);
        }

        @Override
        public void onFrameUnsent(WebSocket websocket, WebSocketFrame frame) throws Exception {
            trigger("frameUnsent", frame);
        }

        @Override
        public void onThreadCreated(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
            trigger("threadCreated", threadType.toString(), thread);
        }

        @Override
        public void onThreadStarted(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
            trigger("threadStarted", threadType.toString(), thread);
        }

        @Override
        public void onThreadStopping(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
            trigger("threadStopping", threadType.toString(), thread);
        }

        @Override
        public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
            trigger("error", cause);
        }

        @Override
        public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
            trigger("frameError", cause, frame);
        }

        @Override
        public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception {
            trigger("messageError", cause, frames);
        }

        @Override
        public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception {

        }

        @Override
        public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {

        }

        @Override
        public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
            trigger("sendError", cause, frame);
        }

        @Override
        public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {
            trigger("unexpectedError", cause);
        }

        @Override
        public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {
            // trigger("callbackError", cause);
        }

        @Override
        public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> headers) throws Exception {
            Map<String, List<String>> headersMap = new LinkedHashMap<>();
            for(String[] strings : headers){
                List<String> parent;
                if(headersMap.containsKey(strings[0])){
                    parent = headersMap.get(strings[0]);
                }
                else{
                    parent = new ArrayList<>();
                    headersMap.put(strings[0], parent);
                }
                parent.add(strings[1]);
            }
            trigger("sendingHandshake", requestLine, headersMap);
        }
    }
}