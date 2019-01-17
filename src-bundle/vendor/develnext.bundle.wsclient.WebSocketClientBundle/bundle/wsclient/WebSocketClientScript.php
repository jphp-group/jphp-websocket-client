<?php


namespace bundle\wsclient;


use php\gui\framework\AbstractScript;
use php\lib\str;
use php\net\URL;
use php\net\websocket\WebSocket;
use php\net\websocket\WebSocketException;
use php\net\websocket\WebSocketFrame;

class WebSocketClientScript extends AbstractScript
{
    /**
     * @var string
     */
    public $url;
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
     * @var array
     */
    public $headers = [];


    /**
     * @var WebSocket
     */
    private $client;


    function __construct(){
        $this->client = new WebSocket();
        $this->client->on('connected', function($headers){
            $this->trigger('connected', ['headers' => $headers]);
        });
        $this->client->on('connectError', function(WebSocketException $error){
            $this->trigger('connectError', ['error' => $error]);
        });
        $this->client->on('frame', function(WebSocketFrame $frame){
            $this->trigger('frame', ['frame' => $frame]);
        });
        $this->client->on('textMessage', function(string $textMessage){
            $this->trigger('textMessage', ['textMessage' => $textMessage]);
        });
        $this->client->on('stateChanged', function($state){
            $this->trigger('stateChanged', ['state' => $state]);
        });
        $this->client->on('disconnected', function(WebSocketFrame $serverCloseFrame, WebSocketFrame $clientCloseFrame, bool $closedByServer){
            $this->trigger('disconnected', ['serverCloseFrame' => $serverCloseFrame, 'clientCloseFrame' => $clientCloseFrame, 'closedByServer' => $closedByServer]);
        });
    }

    /**
     * @throws \php\io\IOException
     */
    function connect(){
        $this->client->clearHeaders();
        if(is_array($this->headers)){
            foreach($this->headers as $name => $header){
                if(is_iterable($header)){
                    foreach($header as $item){
                        $this->client->addHeader($name, $item);
                    }
                }
                else{
                    $this->client->addHeader($name, $header);
                }
            }
        }
        else if(is_string($this->headers)){
            foreach(str::split($this->headers, "\n") as $headerPart){
                [$key, $value] = str::split($headerPart, ':');
                $this->client->addHeader(str::trim($key), str::trim($value));
            }
        }
        $this->client->connect($this->url);
    }
    /**
     * @param int|null $code
     * @param string|null $message
     * @throws \php\io\IOException
     */
    function disconnect(int $code = null, string $message = null){
        if(func_num_args() == 0){
            $this->client->disconnect();
        }
        elseif(func_num_args() == 1){
            $this->client->disconnect($code);
        }
        else{
            $this->client->disconnect($code, $message);
        }
    }
    /**
     * @param string $text
     * @throws \php\io\IOException
     */
    function sendText(string $text){
        $this->client->sendText($text);
    }
    /**
     * @param WebSocketFrame $frame
     * @throws \php\io\IOException
     */
    function sendFrame(WebSocketFrame $frame){
        $this->client->sendFrame($frame);
    }

    /**
     * @param $target
     * @return mixed
     */
    protected function applyImpl($target){

    }
}