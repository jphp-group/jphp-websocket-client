package org.develnext.jphp.ext.websocketclient.classes;


import org.develnext.jphp.ext.websocketclient.WebSocketClientExtension;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.TextFrame;
import org.java_websocket.handshake.ServerHandshake;
import php.runtime.annotation.Reflection;
import php.runtime.common.collections.map.HashedMap;
import php.runtime.env.Environment;
import php.runtime.ext.core.classes.stream.MemoryStream;
import php.runtime.invoke.Invoker;
import php.runtime.lang.BaseObject;
import php.runtime.memory.ArrayMemory;
import php.runtime.reflection.ClassEntity;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Reflection.Namespace(WebSocketClientExtension.NS)
@Reflection.Name("WebSocket")
public class WrapWebSocket extends BaseObject {
    public WrapWebSocket(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    private SimpleWebSocketClient client;
    private Map<String,String> headers = new HashedMap<>();
    private URI uri;

    private Map<String,Map<String,Invoker>> handlers = new HashMap<>();

    @Reflection.Signature
    public void __construct(){

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
    private void trigger(String event, Object ...args){
        if(!handlers.containsKey(event)){
            return;
        }
        for(Invoker invoker : handlers.get(event).values()){
            try{
                invoker.callAny(args);
            }
            catch(Throwable e){
                // nop
            }
        }
    }
    @Reflection.Signature
    public void send(String text){
        client.send(text);
    }
    @Reflection.Signature
    public void connectAsync() throws InterruptedException {
        connect(true);
    }
    @Reflection.Signature
    public void connect() throws InterruptedException {
        connect(false);
    }
//    @Reflection.Signature
//    public void reconnect() throws InterruptedException {
//        reconnect(false);
//    }
//    @Reflection.Signature
//    public void reconnectAsync() throws InterruptedException {
//        reconnect(true);
//    }
    @Reflection.Signature
    public void close(){
        client.close();
    }
    @Reflection.Signature
    public void close(int code){
        client.close(code);
    }
    @Reflection.Signature
    public void close(int code, String message){
        client.close(code, message);
    }
    @Reflection.Signature
    public void sendTextFrame(String text, boolean fin){
        TextFrame frame = new TextFrame();
        frame.setFin(fin);
        frame.setPayload(ByteBuffer.wrap(text.getBytes()));
        client.sendFrame(frame);
    }

    private void connect(boolean async) throws InterruptedException{
        if(client != null && !client.isClosed()){
            throw new IllegalStateException("Already connect. Use close() before");
        }
        client = new SimpleWebSocketClient(uri, headers);
        if(async){
            client.connect();
        }
        else{
            client.connectBlocking();
        }
    }
    private void reconnect(boolean async) throws InterruptedException{
        if(client == null){
            throw new IllegalStateException("Client not initialized. Use connect() before");
        }
        if(async){
            client.reconnect();
        }
        else{
            client.reconnectBlocking();
        }
    }
    @Reflection.Setter
    public void setUrl(String url) throws URISyntaxException {
        uri = new URI(url);
    }
    @Reflection.Getter
    public String getUrl(){
        return uri == null ? null : uri.toString();
    }
    @Reflection.Signature
    public void addHeader(String name, String value){
        headers.put(name, value);
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
    public void addHeaders(Map<String,String> values){
        headers.putAll(values);
    }

    class SimpleWebSocketClient extends WebSocketClient{
        SimpleWebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
            super(serverUri, httpHeaders);
        }
        @Override
        public void onOpen(ServerHandshake data){
            ArrayMemory headers = new ArrayMemory();
            Iterator<String> iterator = data.iterateHttpFields();
            String key;
            while(iterator.hasNext()){
                key = iterator.next();
                headers.refOfIndex(key).assign(data.getFieldValue(key));
            }

            trigger("open", (int)data.getHttpStatus(), data.getHttpStatusMessage(), headers);
        }
        @Override
        public void onMessage(String message){
            trigger("message", message);
        }
        @Override
        public void onClose(int code, String reason, boolean remote){
            client = null;
            trigger("close", code, reason, remote);
        }
        @Override
        public void onError(Exception ex){
            trigger("error", ex);
        }
        @Override
        public void onMessage(ByteBuffer buffer){
            trigger("binaryMessage", new MemoryStream(buffer.array()));
        }
    }
}
