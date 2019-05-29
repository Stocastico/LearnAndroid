package spices

class Curry(name : String = "curry", spiciness: String, color: SpiceColor = YellowSpiceColor) :
    Spice(name, spiciness, color), Grinder {

    override fun prepareSpice() {
        grind()
    }

    override fun grind() {
        println("grinding")
    }

}