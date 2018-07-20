# WebSocket

- **class** `WebSocket` (`php\net\websocket\WebSocket`)
- **source** `php/net/websocket/WebSocket.php`

---

#### Properties

- `->`[`url`](#prop-url) : `string`

---

#### Methods

- `->`[`__construct()`](#method-__construct)
- `->`[`reconnect()`](#method-reconnect)
- `->`[`reconnectAsync()`](#method-reconnectasync)
- `->`[`connect()`](#method-connect)
- `->`[`connectAsync()`](#method-connectasync)
- `->`[`close()`](#method-close)
- `->`[`off()`](#method-off)
- `->`[`on()`](#method-on)
- `->`[`send()`](#method-send)
- `->`[`sendTextFrame()`](#method-sendtextframe)
- `->`[`addHeader()`](#method-addheader)
- `->`[`removeHeader()`](#method-removeheader)
- `->`[`clearHeaders()`](#method-clearheaders)
- `->`[`addHeaders()`](#method-addheaders)

---
# Methods

<a name="method-__construct"></a>

### __construct()
```php
__construct(): void
```

---

<a name="method-reconnect"></a>

### reconnect()
```php
reconnect(): void
```

---

<a name="method-reconnectasync"></a>

### reconnectAsync()
```php
reconnectAsync(): void
```

---

<a name="method-connect"></a>

### connect()
```php
connect(): void
```

---

<a name="method-connectasync"></a>

### connectAsync()
```php
connectAsync(): void
```

---

<a name="method-close"></a>

### close()
```php
close(int $code, string $status): void
```

---

<a name="method-off"></a>

### off()
```php
off(string $event, [ string $group): void
```

---

<a name="method-on"></a>

### on()
```php
on(string $event, callable $arg1, [ string $group): void
```

---

<a name="method-send"></a>

### send()
```php
send(mixed $text): void
```

---

<a name="method-sendtextframe"></a>

### sendTextFrame()
```php
sendTextFrame(string $text, boolean $isFinal): void
```

---

<a name="method-addheader"></a>

### addHeader()
```php
addHeader(string $key, string $value): void
```

---

<a name="method-removeheader"></a>

### removeHeader()
```php
removeHeader(string $key): void
```

---

<a name="method-clearheaders"></a>

### clearHeaders()
```php
clearHeaders(): void
```

---

<a name="method-addheaders"></a>

### addHeaders()
```php
addHeaders(array $args): void
```