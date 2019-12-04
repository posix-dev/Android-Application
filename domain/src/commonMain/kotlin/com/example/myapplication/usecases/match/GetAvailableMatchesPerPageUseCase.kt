package com.example.myapplication.usecases.match

import com.example.myapplication.entities.MatchEntity
import com.example.myapplication.repositories.ArenaTournamentRepository
import com.example.myapplication.usecases.UseCaseWithParamSuspending

class GetAvailableMatchesPerPageUseCase(
    private val repository: ArenaTournamentRepository
) : UseCaseWithParamSuspending<GetAvailableMatchesPerPageUseCase.Params, List<MatchEntity>> {

    override suspend fun buildAction(params: Params) =
        repository.getMatchesAvailable(params.page)

    suspend fun buildAction(page: Int) =
        buildAction(Params(page))

    data class Params(val page: Int)

}