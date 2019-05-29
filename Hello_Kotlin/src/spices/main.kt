package spices

fun main(args : Array<String>) {
    makeSpice()
}


fun makeSpice() {
    val mySpice = SimpleSpice()

    println("Type = ${mySpice.name} " +
            "Spiciness = ${mySpice.heat}")
}


val spices = listOf(
    Curry(spiciness = "mild")
)

val spicelist = spices.filter {it.heat < 5}

