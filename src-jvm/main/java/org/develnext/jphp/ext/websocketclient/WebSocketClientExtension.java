package org.develnext.jphp.ext.websocketclient;

import org.develnext.jphp.ext.websocketclient.classes.WrapWebSocket;
import php.runtime.env.CompileScope;
import php.runtime.ext.support.Extension;

public class WebSocketClientExtension extends Extension {
    public static final String NS = "php\\net\\websocket";
    
    @Override
    public Status getStatus() {
        return Status.EXPERIMENTAL;
    }
    
    @Override
    public void onRegister(CompileScope scope) {
        registerClass(scope, WrapWebSocket.class);
    }
}