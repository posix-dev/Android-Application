package com.example.myapplication.mappers.entitieslinkmapper

import com.example.myapplication.mappers.SingleToRemoteMapper
import io.ktor.http.URLProtocol
import io.ktor.http.Url

class UserLinkMapper(
    private val protocol: String,
    private val host: String,
    private val port: Int
) : SingleToRemoteMapper<Url, String> {

    override fun toRemoteSingle(entity: String) =
        Url(URLProtocol(protocol, port), host, port, "/user/$entity",
            io.ktor.http.parametersOf(), "", null, null, false)

}

