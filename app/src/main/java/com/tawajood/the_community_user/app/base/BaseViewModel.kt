package com.tawajood.the_community_user.app.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tawajood.the_community_user.utils.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface UiEffect
interface UiIntent
interface UiState

abstract class BaseViewModel<STATE : UiState, INTENT : UiIntent, EFFECT : UiEffect>(
    initialState: STATE
) : ViewModel() {

    private val _intentChannel = Channel<INTENT>(Channel.BUFFERED)

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EFFECT>()
    val effect = _effect.asSharedFlow()

    init {
        processIntents()
    }

    private fun processIntents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { intent ->
                try {
                    handleIntent(intent)
                } catch (e: Exception) {
                    onError(e)
                }
            }
        }
    }

    protected open fun onError(e: Exception) {
        e.printStackTrace()
        e.logE()
    }

    abstract suspend fun handleIntent(intent: INTENT)

    fun sendIntent(intent: INTENT) {
        viewModelScope.launch {
            _intentChannel.send(intent)
        }
    }

    protected fun setState(reducer: STATE.() -> STATE) {
        _state.update { currentState -> currentState.reducer() }
    }

    protected fun emitEffect(builder: () -> EFFECT) {
        viewModelScope.launch {
            _effect.emit(builder())
        }
    }

    protected fun launchScope(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            block()
        }
    }

    suspend fun launchMain(block: () -> Unit) {
        withContext(Dispatchers.Main) {
            block()
        }
    }
    protected fun getCurrentState():STATE = state.value
}
