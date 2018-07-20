# WebSocket Client for JPHP
## Установка
```
jppm add jphp-websocket-client
```
## Примеры
```php
use php\net\websocket\WebSocket;

// Инициализация
$client = new WebSocket();
$client->url = 'wss://example.org/';
$client->addHeader('Authorization', 'Bearer abcdefgh0123456789');

// События
$client->on('open', function(int $code, string $message, array $headers){
  echo "Соединение установлено!\n";
});
$client->on('message', function(string $message){
  echo "Получено сообщение: {$message}\n";
});
$client->on('close', function(int $code, string $message, bool $remote){
  echo "Соединение закрыто({$code}-{$message})\n";
});
$client->on('error', function(\Exception $e){
  echo "Ошибка - {$e->getMessage()}\n";
});
$client->connect();
// $client->connectAsync();
```

## Дополнительно

[Расширение для DevelNext](https://github.com/jphp-group/jphp-websocket-client/releases/latest)

[Venity](https://vk.com/venity)

[DevelNext - extensions & manuals.](https://vk.com/dn_extension)