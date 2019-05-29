package spices

class SimpleSpice {
    val name : String = "curry"
    val spiciness : String = "mild"
    val heat : Int
        get() = when (spiciness) {
                "mild" -> 5
                else -> 0
            }

}