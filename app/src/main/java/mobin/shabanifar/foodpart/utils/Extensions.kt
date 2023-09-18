package mobin.shabanifar.foodpart.utils

fun String.isValidPassword(): Boolean {
    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z]).{8,}$")
    return passwordRegex.matches(this)
}

fun String.isValidUser(): Boolean {
    val userNameRegex = Regex("^[a-zA-Z0-9\\p{IsArabic}]{4,}$")
    return userNameRegex.matches(this)
}