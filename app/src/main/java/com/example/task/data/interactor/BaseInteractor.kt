package com.example.task.data.interactor

import com.example.task.data.LoginFactory
import com.example.task.data.ServiceFactory


abstract class BaseInteractor {
    protected val service by lazy { ServiceFactory.create() }
    protected val loginService by lazy { LoginFactory.create() }
}