package za.co.bb.core.util

fun <T> Result<T>.ifSuccessful(block: () -> Unit) {
    if (isSuccess) block()
}

fun <T> Result<T>.ifFailure(block: () -> Unit) {
    if (isFailure) block()
}