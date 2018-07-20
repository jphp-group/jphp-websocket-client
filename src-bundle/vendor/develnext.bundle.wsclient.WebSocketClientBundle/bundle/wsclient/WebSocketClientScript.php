<?php


namespace bundle\wsclient;


use php\gui\framework\AbstractScript;
use php\net\websocket\WebSocket;

class WebSocketClientScript extends AbstractScript
{
    public $url;
    /**
     * @var array
     */
    public $headers = [];
    /**
     * @var WebSocket
     */
    private $client;

    function __construct(){
        $this->client = new WebSocket();
        $this->client->on('open', function($statusCode, $statusMessage, $headers){
            $this->trigger('open', ['statusCode' => $statusCode, 'message' => $statusMessage, 'headers' => $headers]);
        });
        $this->client->on('close', function($statusCode, $statusMessage, $remote){
            $this->trigger('close', ['statusCode' => $statusCode, 'message' => $statusMessage, 'remote' => $remote]);
        });
        $this->client->on('message', function($message){
            $this->trigger('message', ['message' => $message]);
        });
        $this->client->on('error', function($error){
            $this->trigger('error', ['error' => $error]);
        });
    }
    function connect(){
        $this->_connect(false);
    }
    function connectAsync(){
        $this->_connect(true);
    }
    private function _connect(bool $async){
        $this->client->url = $this->url;
        $this->client->clearHeaders();
        if(is_array($this->headers)){
            $this->client->addHeaders($this->headers);
        }
        if($async){
            $this->client->connectAsync();
        }
        else{
            $this->client->connect();
        }
    }
    function close(int $code = null, string $message = null){
        if(func_num_args() == 0){
            $this->client->close();
        }
        elseif(func_num_args() == 1){
            $this->client->close($code);
        }
        else{
            $this->client->close($code, $message);
        }
    }
    function send(string $text){
        $this->client->send($text);
    }
    function sendTextFrame(string $text, bool $isFinal){
        $this->client->sendTextFrame($text, $isFinal);
    }

    /**
     * @param $target
     * @return mixed
     */
    protected function applyImpl($target){

    }
}