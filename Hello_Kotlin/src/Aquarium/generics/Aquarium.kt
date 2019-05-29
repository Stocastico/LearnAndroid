package Aquarium.generics

fun main()
{
    genericExample()
}

open class WaterSupply(var needsProcessed: Boolean)

class TapWater : WaterSupply(true) {

    fun addChemicalCleaners() {
        needsProcessed = false
    }
}

class FishStoreWater : WaterSupply(false)

class LakeWater : WaterSupply(true)
{

    fun filter() {
        needsProcessed = false
    }
}

class Aquarium<out T: WaterSupply>(val waterSupply: T) {

    fun addWater(cleaner: Cleaner<T>) {
        if (waterSupply.needsProcessed) {
            cleaner.clean(waterSupply)
        }

        println("adding water from $waterSupply")
    }
}

inline fun <reified R: WaterSupply> Aquarium<*>.hasWaterSupplyOfType() = waterSupply is R

interface Cleaner<in T: WaterSupply> {
    fun clean(waterSupply: T)
}

class TapWaterCleaner: Cleaner<TapWater> {
    override fun clean(waterSupply: TapWater) {
        waterSupply.addChemicalCleaners()
    }
}

fun <T : WaterSupply> isWaterClean(aquarium : Aquarium<T>) {
    println("Aquarium water is clean: ${aquarium.waterSupply.needsProcessed}")
}

fun addItemTo(aquarium: Aquarium<WaterSupply>) = println("Item added")

inline fun <reified T: WaterSupply> WaterSupply.isOfType() = this is T


fun genericExample() {

    val cleaner = TapWaterCleaner()

    val aquarium = Aquarium<TapWater>(TapWater())
    isWaterClean(aquarium)
    aquarium.addWater(cleaner)
    aquarium.hasWaterSupplyOfType<TapWater>()
    aquarium.waterSupply.isOfType<LakeWater>()

    addItemTo(aquarium)

    aquarium.waterSupply.addChemicalCleaners()


    val aquarium2 = Aquarium(LakeWater())
    aquarium2.waterSupply.filter()
}