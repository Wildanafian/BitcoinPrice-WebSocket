package com.practice.websocket.constant

object AllYouNeed {
    const val TAG = "@@"
    const val CoinbasePoint = "wss://ws-feed.pro.coinbase.com"
    const val IndodaxPoint = "wss://ws3.indodax.com/ws/"

    const val SHARED_PREF = "Uwaw"
    const val CoinbasePref = "CoinbasePrice"
    const val IndodaxPref = "IndodaxPrice"

    const val SubsCoinbase = "{\"type\":\"subscribe\",\"channels\":[{\"name\":\"ticker\",\"product_ids\":[\"BTC-USD\"]}]}"
    const val UnSubsCoinbase = "{\"type\":\"unsubscribe\",\"channels\":[\"ticker\"]}"

    const val SubsIndodax = "{\"method\":1,\"params\":{\"channel\":\"chart:tick-btcidr\",\"recover\":true,\"offset\":820574},\"id\":2}"
    const val SubsAuthIndodax = "{\"params\":{\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE5NDY2MTg0MTV9.UR1lBM6Eqh0yWz-PVirw1uPCxe60FdchR8eNVdsskeo\"},\"id\":1}"
    const val UnSubsIndodax = "{\"method\":2,\"params\":{\"channel\":\"chart:tick-btcidr\"},\"id\":3}"
}