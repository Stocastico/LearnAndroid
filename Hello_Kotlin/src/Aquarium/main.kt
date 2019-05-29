package Aquarium

fun main (args : Array<String>) {
    buildAquarium()
    makeFish()
}

private fun buildAquarium() {

    val myAquarium = Aquarium()

    println("Length: ${myAquarium.length} " +
            "Width: ${myAquarium.width} " +
            "Height: ${myAquarium.height}")

    myAquarium.height = 80

    println("Height: ${myAquarium.height} cm")

    println("Volume: ${myAquarium.volume} liters")

    val smallAquarium = Aquarium(length = 20, width = 15, height = 30)

    println("Volume: ${smallAquarium.volume} liters")

    val myAquarium2 = Aquarium(numberOfFish = 9)

    println("Length: ${myAquarium2.length} " +
            "Width: ${myAquarium2.width} " +
            "Height: ${myAquarium2.height}")
}

fun feedFish(fish: FishAction) {
    fish.eat()
}

fun makeFish() {
    val s = Shark()
    val p = Plecostomus()
    println("Shark: ${s.color}, Plecostomus: ${p.color} ")

    s.eat()
    p.eat()

}