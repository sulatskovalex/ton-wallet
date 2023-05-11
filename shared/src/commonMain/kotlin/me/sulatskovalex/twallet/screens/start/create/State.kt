package me.sulatskovalex.twallet.screens.start.create

data class State(
    val isLoadingWords: Boolean = false,
    val isLoadingWallet: Boolean = false,
    val isError: Boolean = false,
    val randomWords: List<String> = emptyList(),
    val words: List<String> = emptyList(),
) {
    fun loadingWords(): State =
        copy(
            isLoadingWords = true,
            isLoadingWallet = false,
            isError = false,
        )

    fun loadingWallet(): State =
        copy(
            isLoadingWords = false,
            isLoadingWallet = true,
            isError = false,
        )

    fun words(words: List<String>): State =
        copy(
            isLoadingWords = false,
            isLoadingWallet = false,
            isError = false,
            words = words,
        )

    fun randomWords(words: List<String>): State =
        copy(
            randomWords = words,
        )

    fun error(): State =
        copy(
            isLoadingWords = false,
            isLoadingWallet = false,
            isError = true,
        )
}