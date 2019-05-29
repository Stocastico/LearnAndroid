package game

class Game {
    var path : MutableList<Directions> = mutableListOf(Directions.START)

    val north = {path.add(Directions.NORTH)}
    val east = {path.add(Directions.EAST)}
    val west = {path.add(Directions.WEST)}
    val south = {path.add(Directions.SOUTH)}

    val end = {
        path.add(Directions.END)
        println("Game Over! ${path}")
        path.clear()
        false
    }

    fun move(where: () -> Boolean) {
        where.invoke()
    }

    fun makeMove(dir : String?) {
        when (dir) {
            "n" -> move(north)
            "s" -> move(south)
            "e" -> move(east)
            "w" -> move(west)
            else -> move(end)
        }
    }
}