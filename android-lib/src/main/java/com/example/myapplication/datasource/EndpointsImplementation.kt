package com.example.myapplication.datasource

import android.annotation.SuppressLint
import io.ktor.http.Parameters
import io.ktor.http.URLProtocol
import io.ktor.http.Url
import java.text.SimpleDateFormat
import java.time.LocalDateTime

data class EndpointsImplementation(
    override val protocol: String,
    override val host: String,
    override val port: Int
) : ArenaTournamentPublicDatasource.Endpoints {

    private fun parametersOf(vararg headers: Pair<String, Any>) =
        io.ktor.http.parametersOf(*headers.map { it.first to listOf(it.second.toString()) }.toTypedArray())

    private fun buildUrl(path: String, parameters: Parameters = parametersOf()) =
        Url(URLProtocol(protocol, port), host, port, path, parameters, "", null, null, false)

    @SuppressLint("SimpleDateFormat")
    private fun dateTimeToString(dateTime: LocalDateTime, pattern: String = "yyyy-MM-dd'T'HH:mm:ss" ): String {
        val parser = SimpleDateFormat(pattern)
        val formatter = SimpleDateFormat(pattern)
        return formatter.format(parser.parse(dateTime.toString())!!)

    }

    override fun allGamesUrl(page: Int) =
        buildUrl("/game", parametersOf("page" to page))

    override fun gameByNameUrl(name: String) =
        buildUrl("/game")

    override fun searchGamesByNameUrl(query: String, page: Int) =
        buildUrl("/game/search/byGameName", parametersOf("gameName" to query, "page" to page))


    override fun allTournamentsUrl(page: Int) =
        buildUrl("/tournament")

    override fun tournamentByIdUrl(id: Long) =
        buildUrl("/tournament/$id")

    override fun tournamentsByGameLinkUrl(gameLink: String, page: Int) =
        buildUrl("/tournament/search/byGame", parametersOf("game" to gameLink, "page" to page))

    override fun tournamentsByModeUrl(mode: String, page: Int) =
        buildUrl("/tournament/search/byMode", parametersOf("tournamentMode" to mode, "page" to page))

    override fun searchTournamentsByNameUrl(query: String, page: Int)=
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    override fun matchByIdUrl(id: Long) =
        buildUrl("/match/$id")

    override fun matchesByTournamentLinkUrl(tournamentLink: String, page: Int) =
        buildUrl("/match/search/byTournament", parametersOf("tournament" to tournamentLink, "page" to page))

    override fun matchesByTournamentIdUrl(tournamentId: Long, page: Int) =
        buildUrl("/match/search/byTournamentId", parametersOf("tournamentId" to tournamentId))

    override fun matchesByGameLinkUrl(gameLink: String, page: Int) =
        buildUrl("/match/search/byGame", parametersOf("game" to gameLink, "page" to page))

    override fun matchesByGameIdUrl(gameId: Long, page: Int) =
        buildUrl("/match/search/byGameId", parametersOf("gameId" to gameId))

    override fun allMatchesUrl(page: Int) =
        buildUrl("/match")

    override fun matchesAfterDateUrl(dateTime: LocalDateTime, page: Int) =
        buildUrl("/match/search/byMatchDateTimeIsAfter", parametersOf("matchDateTime" to dateTimeToString(dateTime), "page" to page))

    //TODO: current time server side
    override fun matchesAvailableUrl(page: Int) =
        buildUrl("/match/search/availableMatches", parametersOf("page" to page))


    override fun allRegistrationsUrl(page: Int) =
        buildUrl("/registration")

    override fun registrationByIdUrl(id: Long) =
        buildUrl("/registration/$id")

    override fun matchesNotFullUrl(page: Int) =
        buildUrl("/match/search/notFull")

    override fun registrationsByUserUrl(userId: String, page: Int) =
        buildUrl("/registration/search/byUserId", parametersOf("userId" to userId))


    override fun registrationsByMatchLinkUrl(matchLink: String, page: Int) =
        buildUrl("/registration/search/byMatch/$matchLink")

    override fun registrationsByMatchIdUrl(matchId: Long, page: Int) =
        buildUrl("/registration/search/byMatchId", parametersOf("matchId" to matchId))

    override fun currentUserUrl() =
        buildUrl("/currentUser")

    override fun userByIdUrl(userId: String) =
        buildUrl("/user/$userId")

    override fun usersByMatchLinkUrl(matchLink: String, page: Int) =
        buildUrl("/user/search/byMatch", parametersOf("match" to matchLink))

    override fun usersByMatchIdUrl(matchId: Long, page: Int) =
        buildUrl("/user/search/byMatchId", parametersOf("matchId" to matchId))

    override fun isAccountVerifiedUrl() =
        buildUrl("isAccountVerified")
}