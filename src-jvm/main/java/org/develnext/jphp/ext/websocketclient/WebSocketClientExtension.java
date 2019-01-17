package org.develnext.jphp.ext.websocketclient;

import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFrame;
import org.develnext.jphp.ext.websocketclient.classes.*;
import org.develnext.jphp.ext.websocketclient.support.memory.WebSocketExceptionMemoryOperation;
import php.runtime.env.CompileScope;
import php.runtime.ext.support.Extension;

public class WebSocketClientExtension extends Extension {
    public static final String NS = "php\\net\\websocket";
    
    @Override
    public Status getStatus() {
        return Status.EXPERIMENTAL;
    }
    
    @Override
    public void onRegister(CompileScope scope){
        registerClass(scope, WrapWebSocket.class);
        registerWrapperClass(scope, WebSocketFrame.class, WebSocketFrameWrapper.class);
        scope.registerJavaException(WrapWebSocketException.class, WebSocketException.class);
        registerClass(scope, WrapWebSocketException.class);
        registerMemoryOperation(WebSocketExceptionMemoryOperation.class);
    }
}