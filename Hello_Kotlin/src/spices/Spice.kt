package spices

abstract class Spice(val name : String, spiciness : String = "mild", color : SpiceColor) : SpiceColor by color {

    private val _spiciness = spiciness
    init {
        println(spiciness)
    }

    val heat : Int
        get() =  when (_spiciness) {
            "mild" -> 1
            "medium" -> 3
            "spicy" -> 5
            "very spicy" -> 8
            "hot" -> 10
            else -> 0
        }

    abstract fun prepareSpice()
}

interface SpiceColor {
    val color : String
}

object YellowSpiceColor : SpiceColor {
    override val color = "Yellow"
}