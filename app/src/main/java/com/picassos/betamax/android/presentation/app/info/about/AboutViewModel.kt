package com.picassos.betamax.android.presentation.app.info.about

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor(app: Application): AndroidViewModel(app) {

}
