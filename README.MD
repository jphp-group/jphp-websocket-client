# WebSocket Client for JPHP
## Установка
```
jppm add jphp-websocket-client
```
## Пример
```php
use php\net\websocket\WebSocket;
use php\net\websocket\WebSocketFrame;
use php\net\websocket\WebSocketException;

$ws = new WebSocket; // Создаём WebSocket
$ws->url = 'ws://127.0.0.1:8888/simple_server/'; // Задаём url
$ws->connectionTimeout = 5000; // Задаём максимальное время подключения
// Настраиваем прокси
$ws->proxyHost = '159.183.31.220';
$ws->proxyPort = 3128;
$ws->proxyPassword = 'sqrt322';
// Навешиваем обработчики для событий
$ws->on('stateChanged', function(string $state){
    echo "stateChanged: {$state}\n";
});
$ws->on('connected', function(array $headers)use($ws){
    //var_dump($headers);
    echo "Successful connection\n";
    $ws->sendText('hey');
});
$ws->on('textMessage', function(string $message)use($ws){
    echo "Input message: {$message}\n";
    if($message == 'bye'){
        $ws->sendText('see you later :)');
    }
});
$ws->on('connectError', function(WebSocketException $e){
    echo "Connection error: {$e->getMessage()}\n";
});
$ws->on('disconnected', function(WebSocketFrame $serverCloseFrame){
    echo "WebSocket closed by reason: {$serverCloseFrame->closeReason}\n";
});
$ws->on('sendingHandshake', function($request, $headers){
    echo "sendingHandshake({$request}):\n";
    //var_dump($headers);
});
$ws->connectAsynchronously(); // Подключаемся
```
## События
### connected
```php
$ws->on("connected", function(array $headers){
    // Вызывается после успешного подключения.
});
```
### connectError
```php
$ws->on("connectError", function(WebSocketException $cause){
    // Вызывается после неудачного вызова connectAsynchronously.
});
```
### stateChanged
```php
$ws->on("stateChanged", function(string $state){
    // Вызывается при смене состояния WebSocket`а. Возможные значения:
    // CREATED, CONNECTING, OPEN, CLOSING, CLOSED
});
```
### disconnected
```php
$ws->on("disconnected", function(WebSocketFrame $serverCloseFrame, WebSocketFrame $clientCloseFrame, bool $closedByServer){
    // Вызывается после завершения подключения WebSocket`а.
});
```
### frame
```php
$ws->on("frame", function(WebSocketFrame $frame){
    // Вызывается при получении фрейма.
});
```
### continuationFrame
```php
$ws->on("continuationFrame", function(WebSocketFrame $frame){
    // Вызывается при получении "продолжительного" фрейма(fin=0).
});
```
### textFrame
```php
$ws->on("textFrame", function(WebSocketFrame $frame){
    // Вызывается при получении текстового фрейма.
});
```
### textMessage
```php
$ws->on("textMessage", function(string $message){
    // Вызывается при получении текстового сообщения.
});
```
### binaryFrame
```php
$ws->on("binaryFrame", function(WebSocketFrame $frame){
    // Вызывается при получении бинарного фрейма.
});
```
### pingFrame
```php
$ws->on("pingFrame", function(WebSocketFrame $frame){
    // Вызывается при получении "пинг" фрейма(для проверки подключения).
});
```
### pongFrame
```php
$ws->on("pongFrame", function(string $message){
    // Вызывается при получении текстового сообщения.
});
```
### sendingFrame
```php
$ws->on("sendingFrame", function(WebSocketFrame $frame){
    // Вызывается при отправлении фрейма.
});
```
### frameSent
```php
$ws->on("frameSent", function(WebSocketFrame $frame){
    // Вызывается после успешного отправления фрейма.
});
```
### frameUnsent
```php
$ws->on("frameUnsent", function(WebSocketFrame $frame){
    // Вызывается после ошибки при отправлении фрейма.
});
```
### threadCreated
```php
$ws->on("threadCreated", function(string $threadType, php\lang\Thread $thread){
    // Вызывается при создании нового потока. Возможные значения для threadType:
    // READING_THREAD, WRITING_THREAD, CONNECT_THREAD, FINISH_THREAD
});
```
### threadStarted
```php
$ws->on("threadStarted", function(string $threadType, php\lang\Thread $thread){
    // Вызывается при запуске созданного потока.
});
```
### threadStopping
```php
$ws->on("threadStopping", function(string $threadType, php\lang\Thread $thread){
    // Вызывается при остановке созданного потока.
});
```
### error
```php
$ws->on("error", function(WebSocketException $error){
    // Вызывается при ошибке.
});
```
### frameError
```php
$ws->on("frameError", function(WebSocketException $error, WebSocketFrame $frame){
    // Вызывается при ошибке чтения фрейма.
});
```
### messageError
```php
$ws->on("messageError", function(WebSocketException $error, array $frames){
    // Вызывается при ошибке построения сообщения.
});
```
### sendError
```php
$ws->on("sendError", function(WebSocketException $error, WebSocketFrame $frame){
    // Вызывается при ошибке отправки фрейма.
});
```
### unexpectedError
```php
$ws->on("unexpectedError", function(WebSocketException $error){
    // Вызывается при неожиданной ошибке.
});
```
### sendingHandshake
```php
$ws->on("sendingHandshake", function(string $requestLine, array $headers){
    // Вызывается перед установкой соединения.
});
```

## Дополнительно

[Расширение для DevelNext](https://github.com/jphp-group/jphp-websocket-client/releases/latest)

[Оригинальная библиотека](https://github.com/TakahikoKawasaki/nv-websocket-client)

[Venity](https://vk.com/venity)

[DevelNext - extensions & manuals.](https://vk.com/dn_extension)