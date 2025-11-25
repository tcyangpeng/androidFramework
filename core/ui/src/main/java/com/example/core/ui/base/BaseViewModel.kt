package com.example.core.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel with MVI pattern support.
 * Provides unidirectional data flow architecture.
 * 
 * @param S The UI State type
 * @param E The Event type (user actions)
 * @param F The Effect type (one-time events like navigation, showing snackbar)
 */
abstract class BaseViewModel<S : UiState, E : UiEvent, F : UiEffect> : ViewModel() {

    /**
     * Initial UI state.
     */
    abstract fun createInitialState(): S

    /**
     * Handle incoming events from the UI.
     */
    abstract fun handleEvent(event: E)

    /**
     * Current UI state.
     */
    private val _uiState: MutableStateFlow<S> by lazy { MutableStateFlow(createInitialState()) }
    val uiState: StateFlow<S> = _uiState.asStateFlow()

    /**
     * Current state value.
     */
    protected val currentState: S
        get() = _uiState.value

    /**
     * One-time effects (navigation, snackbar, etc).
     */
    private val _effect: Channel<F> = Channel()
    val effect = _effect.receiveAsFlow()

    /**
     * Events from UI.
     */
    private val _event: MutableSharedFlow<E> = MutableSharedFlow()
    val event: SharedFlow<E> = _event.asSharedFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            event.collect { handleEvent(it) }
        }
    }

    /**
     * Set a new event from the UI.
     */
    fun setEvent(event: E) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    /**
     * Update the current state.
     */
    protected fun setState(reduce: S.() -> S) {
        _uiState.value = currentState.reduce()
    }

    /**
     * Send a one-time effect to the UI.
     */
    protected fun setEffect(effect: F) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}

/**
 * Marker interface for UI State.
 */
interface UiState

/**
 * Marker interface for UI Events.
 */
interface UiEvent

/**
 * Marker interface for UI Effects.
 */
interface UiEffect
