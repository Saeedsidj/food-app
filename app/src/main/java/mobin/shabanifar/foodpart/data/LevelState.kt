package mobin.shabanifar.foodpart.data

sealed class LevelState(
    val name: String, val id: String
) {
    object Hard : LevelState("سخت", "3")
    object Medium : LevelState("متوسط", "2")
    object Easy : LevelState("آسان", "1")
    object Any : LevelState("مهم نیست", "")
}