package com.picassos.betamax.android.presentation.app.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.picassos.betamax.android.core.resource.Resource
import com.picassos.betamax.android.domain.usecase.main.MainUseCases
import com.picassos.betamax.android.presentation.app.subscription.check_subscription.CheckSubscriptionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(app: Application, private val mainUseCases: MainUseCases): AndroidViewModel(app) {
    private val _checkSubscription = MutableStateFlow(CheckSubscriptionState())
    val checkSubscription = _checkSubscription.asStateFlow()

    fun requestCheckSubscription() {
        viewModelScope.launch {
            mainUseCases.getLocalAccountUseCase.invoke().collect { account ->
                mainUseCases.checkSubscriptionUseCase(account.token).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _checkSubscription.emit(CheckSubscriptionState(
                                isLoading = result.isLoading))
                        }
                        is Resource.Success -> {
                            _checkSubscription.emit(CheckSubscriptionState(
                                response = result.data))
                        }
                        is Resource.Error -> {
                            _checkSubscription.emit(CheckSubscriptionState(
                                error = result.message))
                        }
                    }
                }
            }
        }
    }
}