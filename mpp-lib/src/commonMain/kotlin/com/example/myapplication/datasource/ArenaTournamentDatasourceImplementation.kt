package com.example.myapplication.datasource

import com.example.myapplication.entities.GameEntity
import com.example.myapplication.entities.MatchEntity
import com.example.myapplication.entities.TournamentEntity
import com.example.myapplication.entities.UserEntity
import com.example.myapplication.rawresponses.*
import com.soywiz.klock.DateTimeTz
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.util.InternalAPI
import io.ktor.util.encodeBase64

class ArenaTournamentDatasourceImplementation(
    private val httpClient: HttpClient,
    private val endpoints: ArenaTournamentDatasource.Endpoints,
    override val tokenFactory: TokenFactory
) : ArenaTournamentDatasource {

    override suspend fun createGame(name: String, availableModes: List<String>, image: String, icon: String): GameJSON =
        httpClient.authenticatedPost(endpoints.createGameUrl(name, availableModes, image, icon))

    override suspend fun createGame(gameJson: CreateGameJson) =
        httpClient.authenticatedPost(endpoints.createGameUrl(???), gameJson)

    override suspend fun createMatch(matchDateTime: DateTimeTz, playersCount: Int, isRegistrationPossible: Boolean, tournament: TournamentEntity): MatchJSON =
        httpClient.authenticatedPost(endpoints.createMatchUrl(matchDateTime, playersCount, isRegistrationPossible, tournament))

    override suspend fun createGameMode(modeName: String): ModeJSON =
        httpClient.authenticatedPost(endpoints.createGameModeUrl(modeName))

    override suspend fun createRegistration(user: UserEntity, match: MatchEntity, outcome: String?): RegistrationJSON =
        httpClient.authenticatedPost(endpoints.createRegistrationUrl(user, match, outcome))

    override suspend fun createTournament(playersNumber: Int, title: String, tournamentDescription: String, tournamentMode: String, admin: UserEntity, game: GameEntity): TournamentJSON =
        httpClient.authenticatedPost(endpoints.createTournamentUrl(playersNumber, title, tournamentDescription, tournamentMode, admin, game))

    override suspend fun createUser(email: String, password: String, nickname: String, image: String): UserJSON =
        httpClient.authenticatedPost(endpoints.createUserUrl(email, password, nickname, image))


    override suspend fun getAllGames(page: Int): MultipleGamesJSON =
        httpClient.get(endpoints.allGamesUrl(page))

    override suspend fun getGameByName(gameName: String): GameJSON =
        httpClient.get(endpoints.gameByNameUrl(gameName))

    override suspend fun getGameByLink(link: String): GameJSON =
        httpClient.get(link)

    override suspend fun searchGamesByName(query: String, page: Int): MultipleGamesJSON =
        httpClient.get(endpoints.searchGamesByNameUrl(query, page))

    override suspend fun getGamesContainingName(gameName: String, page: Int): MultipleGamesJSON =
        httpClient.get(endpoints.gamesContainingNameUrl(gameName, page))

    override suspend fun getGamesByMode(mode: String, page: Int): MultipleGamesJSON =
        httpClient.get(endpoints.gamesByModeUrl(mode, page))


    override suspend fun getTournamentById(id: Long): TournamentJSON =
        httpClient.get(endpoints.tournamentByIdUrl(id))

    override suspend fun getTournamentByLink(link: String): TournamentJSON =
        httpClient.get(link)

    override suspend fun getTournamentsByMode(mode: String, page: Int): MultipleTournamentsJSON =
        httpClient.get(endpoints.tournamentsByModeUrl(mode, page))

    override suspend fun getAllTournaments(page: Int): MultipleTournamentsJSON =
        httpClient.get(endpoints.allTournamentsUrl(page))

    override suspend fun getTournamentsByGameName(
        gameName: String,
        page: Int
    ): MultipleTournamentsJSON =
        httpClient.get(endpoints.tournamentsByGameName(gameName, page))

    /**
     * Query sbagliata (????)
     */
    override suspend fun getTournamentsByUser(userId: String, page: Int): MultipleTournamentsJSON =
        httpClient.get(endpoints.tournamentsByAdmin(userId, page))

    override suspend fun searchTournamentsByName(name: String, page: Int): MultipleTournamentsJSON =
        httpClient.get(endpoints.searchTournamentsByNameUrl(name, page))

    override suspend fun getShowCaseTournaments(page: Int): MultipleTournamentsJSON =
        httpClient.get(endpoints.getShowCaseTournaments(page))

    override suspend fun getTournamentsContainingTitle(
        title: String,
        page: Int
    ): MultipleTournamentsJSON =
        httpClient.get(endpoints.getTournamentsContainingTitle(title, page))

    override suspend fun getMatchById(id: Long): MatchJSON =
        httpClient.get(endpoints.matchByIdUrl(id))

    override suspend fun getMatchByLink(link: String): MatchJSON =
        httpClient.get(link)

    override suspend fun getMatchesByTournamentId(
        tournamentId: Long,
        page: Int
    ): MultipleMatchJSON =
        httpClient.get(endpoints.matchesByTournamentIdUrl(tournamentId, page))

    override suspend fun getMatchesByGameName(gameName: String, page: Int): MultipleMatchJSON =
        httpClient.get(endpoints.matchesByGameNameUrl(gameName, page))

    override suspend fun getMatchesAfterDate(
        dateTime: DateTimeTz,
        page: Int
    ): MultipleMatchJSON =
        httpClient.get(endpoints.matchesAfterDateUrl(dateTime, page))

    override suspend fun getMatchesAvailable(page: Int): MultipleMatchJSON =
        httpClient.get(endpoints.matchesAvailableUrl(page))

    override suspend fun getMatchesByUser(userId: String, page: Int): MultipleMatchJSON =
        httpClient.get(endpoints.matchesByUserIdUrl(userId, page))

    override suspend fun getRegistrationById(id: Long): RegistrationJSON =
        httpClient.get(endpoints.registrationByIdUrl(id))

    override suspend fun getRegistrationByLink(link: String): RegistrationJSON =
        httpClient.get(link)


    override suspend fun getMatchesNotFull(page: Int): MultipleMatchJSON =
        httpClient.get(endpoints.matchesNotFullUrl(page))

    override suspend fun getRegistrationsByUser(
        userId: String,
        page: Int
    ): MultipleRegistrationsJSON =
        httpClient.get(endpoints.registrationsByUserUrl(userId, page))

    override suspend fun getRegistrationsByMatchId(
        matchId: Long,
        page: Int
    ): MultipleRegistrationsJSON =
        httpClient.get(endpoints.registrationsByMatchIdUrl(matchId, page))

    override suspend fun getUserById(id: String): UserJSON =
        httpClient.get(endpoints.userByIdUrl(id))

    override suspend fun getUserByLink(link: String): UserJSON =
        httpClient.get(link)

    override suspend fun getUsersByMatchId(matchId: Long, page: Int): MultipleUsersJSON =
        httpClient.get(endpoints.usersByMatchIdUrl(matchId, page))

    override suspend fun getCurrentUser(): UserJSON =
        httpClient.authenticatedGet(endpoints.currentUserUrl())

    override suspend fun getAccountVerificationStatus(): AccountStatusJSON =
        httpClient.authenticatedGet(endpoints.isAccountVerifiedUrl())

    override suspend fun getAccountSubscription(): SubscriptionStatusJSON =
        httpClient.authenticatedGet(endpoints.isAccountSubscribedUrl())

    @UseExperimental(InternalAPI::class)
    private suspend inline fun <reified T> HttpClient.authenticatedGet(url: Url) =
        get<T>(url) {
            tokenFactory.factory()?.let {
                header(HttpHeaders.Authorization, "Bearer: ${"$it:".encodeBase64()}")
            }
        }

    @UseExperimental(InternalAPI::class)
    private suspend inline fun <reified T> HttpClient.authenticatedPost(url: Url, content: Any?) =
        post<T>(url) {
            tokenFactory.factory()?.let {
                header(HttpHeaders.Authorization, "Bearer: ${"$it:".encodeBase64()}")
            }
            content?.let { body = it }
        }

}