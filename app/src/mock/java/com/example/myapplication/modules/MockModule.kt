package com.example.myapplication.modules

import android.content.res.Resources
import android.util.Log
import androidx.annotation.RawRes
import com.example.myapplication.R
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.fullPath
import io.ktor.http.headersOf
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

object MockModule : KodeinModuleProvider {

    override fun provideModule(): Kodein.Builder.() -> Unit = {
        bind<HttpClient>(overrides = true) with singleton {
            HttpClient(MockEngine) {
                install(JsonFeature) {
                    serializer = KotlinxSerializer()
                }
                engine {
                    addHandler {
                        Log.d("MockEngine", "$it")
                        handleMockEngineRequest(it, instance())
                    }
                }
            }
        }
    }

    private fun respondJsonFromRawResources(@RawRes resId: Int, resources: Resources) = respond(
        resources.openRawResource(resId).bufferedReader().use { it.readText() },
        headers = headersOf(HttpHeaders.ContentType to listOf(ContentType.Application.Json.toString()))
    )

    private fun buildMockEngineError(request: HttpRequestData): Nothing =
        error("$request cannot be handled by the mock engine")

    private fun handleMockEngineRequest(request: HttpRequestData, res: Resources) =
        when (request.url.fullPath) {
            "/game" -> respondJsonFromRawResources(R.raw.multiple_games_response, res)
            "/game/search/byGameName" -> respondJsonFromRawResources(R.raw.game_response, res)
            "/tournament/1/game" -> respondJsonFromRawResources(R.raw.game_response, res)


            "/tournament" -> respondJsonFromRawResources(R.raw.multiple_tournaments_response, res)
            "/tournament/1" -> respondJsonFromRawResources(R.raw.tournament_response, res)
            "/tournament/2" -> respondJsonFromRawResources(R.raw.tournament_response, res)
            "/tournament/search/byMode" -> respondJsonFromRawResources(
                R.raw.multiple_tournaments_response,
                res
            )
            "/tournament/search/byGame" -> respondJsonFromRawResources(
                R.raw.multiple_tournaments_response,
                res
            )
            "/tournament/search/byName" -> respondJsonFromRawResources(
                R.raw.multiple_tournaments_response,
                res
            )
            "/match/1/tournament" -> respondJsonFromRawResources(
                R.raw.tournament_response,
                res
            )
            "/match/2/tournament"-> respondJsonFromRawResources(
                R.raw.tournament_response,
                res
            )

            "/match" -> respondJsonFromRawResources(R.raw.multiple_matches_response, res)
            "/match/1" -> respondJsonFromRawResources(R.raw.match_response, res)
            "/match/2" -> respondJsonFromRawResources(R.raw.match_response, res)
            "/match/search/byTournament" -> respondJsonFromRawResources(
                R.raw.multiple_matches_response,
                res
            )
            "/match/search/byTournamentId" -> respondJsonFromRawResources(
                R.raw.multiple_matches_response,
                res
            )
            "/match/search/byGame" -> respondJsonFromRawResources(
                R.raw.multiple_matches_response,
                res
            )
            "/match/search/byGameId" -> respondJsonFromRawResources(
                R.raw.multiple_matches_response,
                res
            )
            "/match/search/byMatchDateTimeIsAfter" -> respondJsonFromRawResources(
                R.raw.multiple_matches_response,
                res
            )
            "/match/search/availableMatches?page=0" -> respondJsonFromRawResources(
                R.raw.multiple_matches_response,
                res
            )
            "/match/search/notFull" -> respondJsonFromRawResources(
                R.raw.multiple_matches_response,
                res
            )
            "/registration/1/match" -> respondJsonFromRawResources(R.raw.match_response, res)
            "/registration/2/match" -> respondJsonFromRawResources(R.raw.match_response, res)
            "/registration/3/match" -> respondJsonFromRawResources(R.raw.match_response, res)

            "/registration" -> respondJsonFromRawResources(
                R.raw.multiple_registrations_response,
                res
            )
            "/registration/1" -> respondJsonFromRawResources(R.raw.registration_response, res)
            "/registration/2" -> respondJsonFromRawResources(R.raw.registration_response, res)
            "/registration/3" -> respondJsonFromRawResources(R.raw.registration_response, res)
            "/registration/search/byUserId" -> respondJsonFromRawResources(
                R.raw.multiple_registrations_response,
                res
            )
            "/registration/search/byMatchId" -> respondJsonFromRawResources(
                R.raw.multiple_registrations_response,
                res
            )
            "/registration/search/byMatch/http://localhost:8080/match/1?page=0" -> respondJsonFromRawResources(
                R.raw.multiple_registrations_response,
                res
            )
            "/registration/search/byMatch/http://localhost:8080/match/1?page=1" -> respondJsonFromRawResources(
                R.raw.multiple_registrations_response,
                res
            )

            "/currentUser" -> respondJsonFromRawResources(R.raw.user_response, res)
            "/user/Cesare_1" -> respondJsonFromRawResources(R.raw.user_response, res)
            "/user/Lamba_2" -> respondJsonFromRawResources(R.raw.user_response, res)
            "/user/Beppe_3" -> respondJsonFromRawResources(R.raw.user_response, res)
            "/user/search/byMatch" -> respondJsonFromRawResources(
                R.raw.multiple_users_response,
                res
            )
            "/user/search/byMatchId" -> respondJsonFromRawResources(
                R.raw.multiple_users_response,
                res
            )
            "/registration/1/user" -> respondJsonFromRawResources(R.raw.user_response, res)
            "/registration/2/user" -> respondJsonFromRawResources(R.raw.user_response, res)
            "/registration/3/user" -> respondJsonFromRawResources(R.raw.user_response, res)
            "/tournament/1/admin" -> respondJsonFromRawResources(R.raw.user_response, res)
            // "/isAccountVerified" -> respondJsonFromRawResources(R.raw.multiple_users_response, res)

            else -> buildMockEngineError(request)
        }
}