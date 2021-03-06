package com.example.myapplication.mock

object GameResponse : JsonMockProvider {
    override val json = """
        {
          "gameName": "Counter Strike: GO",
          "availableModes": [
            "OPEN_WORLD"
          ],
          "image": "https://logodix.com/logo/2115839.png",
          "icon": "https://logodix.com/logo/2115839.png",
          "_links": {
            "self": {
              "href": "http://localhost:8080/game/go"
            },
            "gameEntity": {
              "href": "http://localhost:8080/game/go"
            }
          }
        }
    """.trimIndent()
}