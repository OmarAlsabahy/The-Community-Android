package com.tawajood.the_community_user.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.the_community_user.app.language.AppLanguage
import com.tawajood.the_community_user.domain.usecase.language.GetLanguageFlowUseCase
import com.tawajood.the_community_user.domain.usecase.language.SetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    getLanguageFlow: GetLanguageFlowUseCase,
    private val setLanguage: SetLanguageUseCase,
    // private val isNewVersionUseCase: IsNewVersionUseCase
) : ViewModel() {

    val currentLang = getLanguageFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    var isNewVersion = MutableStateFlow(false)
        private set

    init {
        isNewVersion()
    }

    fun switchTo(new: AppLanguage) = viewModelScope.launch { setLanguage(new) }

    private fun isNewVersion() {
        viewModelScope.launch {
            /*
            isNewVersionUseCase(Unit).collect { result ->
                when (result) {
                    RequestState.Loading -> {}
                    is RequestState.Error -> {}
                    is RequestState.Success -> {
                        isNewVersion.value = result.data
                    }
                }
            }
            */
        }
    }
}
