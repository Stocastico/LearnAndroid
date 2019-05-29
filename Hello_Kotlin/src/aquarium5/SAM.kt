package aquarium5

interface Runnable {
    fun run()
}

interface Callable<T> {
    fun call(): T
}


