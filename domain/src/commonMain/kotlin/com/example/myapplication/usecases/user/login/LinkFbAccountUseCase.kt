package com.example.myapplication.usecases.user.login

import com.example.myapplication.repositories.ArenaTournamentRepository
import com.example.myapplication.usecases.UseCaseWithParamsSuspending

class LinkFbAccountUseCase(
    private val repository: ArenaTournamentRepository
) : UseCaseWithParamsSuspending<SignInWithFacebookUseCase.Params, Boolean> {

    override suspend fun buildAction(params: SignInWithFacebookUseCase.Params) =
        repository.linkFacebookAuthProvider(params.fbToken)

}