package me.sulatskovalex.twallet.screens.start.create

data class State(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val words: List<String> = emptyList(),
) {
    fun loading(): State =
        copy(
            isLoading = true,
            isError = false,
        )

    fun words(words: List<String>): State =
        copy(
            isLoading = false,
            isError = false,
            words = words,
        )

    fun error(): State =
        copy(
            isLoading = false,
            isError = true,
        )
}