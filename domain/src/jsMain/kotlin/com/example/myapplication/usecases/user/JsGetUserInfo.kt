package com.example.myapplication.usecases.user

import com.example.myapplication.entities.UserEntity
import com.example.myapplication.usecases.JsUseCasePromise
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

class JsGetUserInfo(
    override val usecase: GetUserInfoUseCase
): JsUseCasePromise<GetUserInfoUseCase, UserEntity> {

    override fun buildAction(): Promise<UserEntity> = GlobalScope.promise {
        usecase.buildAction()
    }
}