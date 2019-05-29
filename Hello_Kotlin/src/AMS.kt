import java.util.Random

fun main(args: Array<String>) {
    println(eval(Sum(Per(Num(args[0].toDouble()), Num(2.0)), Men(Div(Num(4.0), Num(0.5)), Num(1.1)))))
    feedTheFish()
}

val waterFilter = {dirty : Int -> dirty / 2}

fun getDirtySensorReading() = 20

fun updateDirty(dirty: Int, operation: (Int) -> Int) : Int {
    return operation(dirty)
}

//fun dirtyProcessor(dirty: Int) {
//    val dirty2 = updateDirty(dirty, waterFilter)
//    val dirty3 = updateDirty(dirty2, ::feedFish)
//}

fun shouldChangeWater( day : String, temperature: Int = 22, dirty: Int = 20) : Boolean{

    return when {
        isTooHot(temperature) -> true
        isDirty(dirty) -> true
        isSunday(day) -> true
        else -> false
    }
}

fun isTooHot(temperature : Int) = temperature > 30

fun isDirty(dirty : Int) = dirty > 30

fun isSunday(day : String) = day == "Sunday"

fun feedTheFish() {
    val day = randomDay()
    val food = fishFood(day)
    println("Today is $day and the fish eat $food")

    if (shouldChangeWater(day)) {
        println("You should change water")
    }
}

fun swim(speed : String = "fast") {
    println("swimming $speed")
}

fun randomDay() : String {
    val week = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    return week[Random().nextInt(7)]
}

fun fishFood(day : String) : String =
    when (day) {
        "Monday" -> "flakes"
        "Wednesday" -> "redworms"
        "Thursday" -> "granules"
        "Friday" -> "plankton"
        else -> "nothing"
    }
