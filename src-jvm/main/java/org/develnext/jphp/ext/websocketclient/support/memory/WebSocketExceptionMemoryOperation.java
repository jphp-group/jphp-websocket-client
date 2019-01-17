package org.develnext.jphp.ext.websocketclient.support.memory;

import com.neovisionaries.ws.client.WebSocketException;
import org.develnext.jphp.ext.websocketclient.classes.WrapWebSocketException;
import php.runtime.Memory;
import php.runtime.env.Environment;
import php.runtime.env.TraceInfo;
import php.runtime.memory.ObjectMemory;
import php.runtime.memory.support.MemoryOperation;

public class WebSocketExceptionMemoryOperation extends MemoryOperation<WebSocketException>{
    @Override
    public Class<?>[] getOperationClasses(){
        return new Class[]{WebSocketException.class};
    }

    @Override
    public WebSocketException convert(Environment environment, TraceInfo traceInfo, Memory memory) throws Throwable {
        if(memory.instanceOf(WrapWebSocketException.class)){
            return memory.toObject(WrapWebSocketException.class).getWebSocketException();
        }
        return null;
    }

    @Override
    public Memory unconvert(Environment environment, TraceInfo traceInfo, WebSocketException o) throws Throwable {
        return new ObjectMemory(new WrapWebSocketException(environment, o));
    }
}
