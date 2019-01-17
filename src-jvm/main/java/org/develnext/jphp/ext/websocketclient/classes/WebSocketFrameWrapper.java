package org.develnext.jphp.ext.websocketclient.classes;

import com.neovisionaries.ws.client.WebSocketFrame;
import org.develnext.jphp.ext.websocketclient.WebSocketClientExtension;
import php.runtime.Memory;
import php.runtime.annotation.Reflection;
import php.runtime.env.Environment;
import php.runtime.ext.core.classes.stream.MiscStream;
import php.runtime.lang.BaseWrapper;
import php.runtime.memory.ObjectMemory;
import php.runtime.reflection.ClassEntity;

import java.io.ByteArrayInputStream;


@Reflection.Namespace(WebSocketClientExtension.NS)
@Reflection.Name("WebSocketFrame")
public class WebSocketFrameWrapper extends BaseWrapper<WebSocketFrame>{
    public WebSocketFrameWrapper(Environment env, WebSocketFrame wrappedObject) {
        super(env, wrappedObject);
    }
    public WebSocketFrameWrapper(Environment env, ClassEntity clazz) {
        super(env, clazz);
    }

    interface WrappedInterface{
        @Reflection.Getter int getCloseCode();
        @Reflection.Getter String getCloseReason();
        @Reflection.Getter int getPayloadLength();
        @Reflection.Getter String getPayloadText();

        boolean hasPayload();
        boolean isBinaryFrame();
        boolean isCloseFrame();
        boolean isContinuationFrame();
        boolean isControlFrame();
        boolean isDataFrame();
        boolean isPingFrame();
        boolean isPongFrame();
        boolean isTextFrame();
        void setCloseFramePayload(int closeCode, String reason);

        @Reflection.Property boolean fin();
        @Reflection.Property int opcode();
        @Reflection.Property boolean rsv1();
        @Reflection.Property boolean rsv2();
        @Reflection.Property boolean rsv3();
    }


    @Reflection.Signature
    public void __construct(){
        __wrappedObject = new WebSocketFrame();
    }
    @Reflection.Signature
    public Memory getPayloadStream(Environment env){
        return new ObjectMemory(new MiscStream(env, new ByteArrayInputStream(getWrappedObject().getPayload())));
    }
    @Reflection.Signature
    public void setPayload(String payload){
        getWrappedObject().setPayload(payload);
    }
}