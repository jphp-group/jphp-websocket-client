package org.develnext.jphp.ext.websocketclient.classes;

import com.neovisionaries.ws.client.WebSocketException;
import org.develnext.jphp.ext.websocketclient.WebSocketClientExtension;
import php.runtime.annotation.Reflection;
import php.runtime.env.Environment;
import php.runtime.ext.java.JavaException;
import php.runtime.reflection.ClassEntity;

@Reflection.Namespace(WebSocketClientExtension.NS)
@Reflection.Name("WebSocketException")
public class WrapWebSocketException extends JavaException{
    public WrapWebSocketException(Environment env, Throwable throwable) {
        super(env, throwable);
    }
    public WrapWebSocketException(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }


    public WebSocketException getWebSocketException(){
        return (WebSocketException)getThrowable();
    }
}