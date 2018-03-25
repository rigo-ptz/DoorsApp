package com.jollypanda.petrsudoors.ui.common

/**
 * @author Yamushev Igor
 * @since  24.03.18
 */

open class State
class StubState: State()
class ProgressState: State()
class SuccessState<T>(result: T? = null): State()
class ErrorState<T>(error: T? = null): State()