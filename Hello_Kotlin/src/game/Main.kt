package game

fun main()
{
    val g = Game()

    while (true) {
        println("Enter a direction: n/s/e/w:")
        g.makeMove(readLine())
    }

    println(g.path)
}