package com.picassos.betamax.android.presentation.app.tvchannel

import com.picassos.betamax.android.domain.model.TvChannels

data class TvState(
    val isLoading: Boolean = false,
    val response: TvChannels? = null,
    val error: String? = null)