import java.lang.IllegalArgumentException

interface Expr

class Num(val value: Double) : Expr
class Sum(val left : Expr, val right : Expr) : Expr
class Men(val left : Expr, val right : Expr) : Expr
class Per(val left : Expr, val right : Expr) : Expr
class Div(val left : Expr, val right : Expr) : Expr

fun eval(e: Expr) : Double =
    when (e) {
        is Num -> e.value
        is Sum -> eval(e.right) + eval(e.left)
        is Men -> eval(e.right) - eval(e.left)
        is Per -> eval(e.right) * eval(e.left)
        is Div -> eval(e.right) / eval(e.left)
        else -> throw IllegalArgumentException("Unknown expression!")
    }