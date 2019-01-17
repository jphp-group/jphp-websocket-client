<?php


namespace php\net\websocket;


class WebSocketFrame{
    /**
     * @var int
     * @readonly
     */
    public $closeCode;
    /**
     * @var string
     * @readonly
     */
    public $closeReason;
    /**
     * @var int
     * @readonly
     */
    public $payloadLength;
    /**
     * @var string
     * @readonly
     */
    public $payloadText;
    /**
     * @var bool
     */
    public $fin;
    /**
     * @var int
     */
    public $opcode;
    /**
     * @var bool
     */
    public $rsv1;
    /**
     * @var bool
     */
    public $rsv2;
    /**
     * @var bool
     */
    public $rsv3;


    /**
     * @return bool
     */
    public function hasPayload(){}
    /**
     * @return bool
     */
    public function isBinaryFrame(){}
    /**
     * @return bool
     */
    public function isCloseFrame(){}
    /**
     * @return bool
     */
    public function isContinuationFrame(){}
    /**
     * @return bool
     */
    public function isControlFrame(){}
    /**
     * @return bool
     */
    public function isDataFrame(){}
    /**
     * @return bool
     */
    public function isPingFrame(){}
    /**
     * @return bool
     */
    public function isPongFrame(){}
    /**
     * @return bool
     */
    public function isTextFrame(){}
    /**
     * @param int $closeCode
     * @param string $reason
     */
    public function setCloseFramePayload($closeCode, $reason){}
}