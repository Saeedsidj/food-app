package mobin.shabanifar.foodpart.data

data class LevelState(
    val HARD: String,
    val MEDIUM: String,
    var EASY: String,
    var NO_MATTER: String
) {
    companion object {
        val HARD = "سخت"
        val MEDIUM = "متوسط"
        val EASY = "آسان"
        val NO_MATTER = "مهم نیست"
    }
}


