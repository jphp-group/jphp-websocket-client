<?php
namespace php\net\websocket;


use php\io\IOException;


class WebSocket{
    /**
     * @var string
    */
    public $serverName;
    /**
     * @var string
     */
    public $url;
    /**
     * @var string
     */
    public $userInfo;
    /**
     * @var int
     */
    public $connectionTimeout;

    /**
     * @var string
     */
    public $proxyHost;
    /**
     * @var int
     */
    public $proxyPort;
    /**
     * @var string
     */
    public $proxyPassword;
    /**
     * @var bool
     */
    public $proxySecure;
    /**
     * @var bool
     */
    public $verifyHostName;

    
    public function __construct(){
        
    }

    /**
     * @param string|null $url
     * @param int|null $timeout
     * @throws IOException
     */
    public function connect($url = null, $timeout = null){
        
    }
    /**
     * @param string|null $url
     * @param int|null $timeout
     * @throws IOException
     */
    public function connectAsynchronously($url = null, $timeout = null){

    }

    /**
     * @param int|null $code
     * @param string|null $status
     * @throws IOException
     */
    public function disconnect($code = null, $status = null){
        
    }

    /**
     * @param string $event
     * @param string|null $group
     */
    public function off($event, $group = null){
        
    }
    /**
     * @param string $event
     * @param callable $callback
     * @param string|null $group
     */
    public function on($event, $callback, $group = 'general'){
        
    }

    /**
     * @param string $text
     * @throws IOException
     */
    public function sendText($text){
        
    }
    /**
     * @param WebSocketFrame $frame
     * @throws IOException
     */
    public function sendFrame(WebSocketFrame $frame){
        
    }
    /**
     * @param string $key
     * @param string $value
     */
    public function addHeader($key, $value){
        
    }
    /**
     * @param string $key
     */
    public function removeHeader(string $key){
        
    }
    public function clearHeaders(){
        
    }
}