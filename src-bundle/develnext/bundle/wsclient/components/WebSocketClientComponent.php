<?php


namespace develnext\bundle\wsclient\components;


use bundle\wsclient\WebSocketClientScript;
use ide\scripts\AbstractScriptComponent;

class WebSocketClientComponent extends AbstractScriptComponent
{

    public function isOrigin($any){
        return $any instanceof WebSocketClientComponent;
    }

    /**
     * @return string
     */
    public function getName(){
        return "WebSocket Client";
    }

    public function getIcon()
    {
        return 'develnext/bundle/wsclient/icon16.png';
    }
    /**
     * @return string
     */
    public function getType(){
        return WebSocketClientScript::class;
    }

    public function getDescription(){
        return null;
    }

    public function getGroup()
    {
        return 'Интернет и сеть';
    }

    public function getIdPattern()
    {
        return "webSocket%s";
    }
}