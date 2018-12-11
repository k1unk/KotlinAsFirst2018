@file:Suppress("UNUSED_PARAMETER")
package lesson8.task2

import kotlin.math.abs

/**
 * Клетка шахматной доски. Шахматная доска квадратная и имеет 8 х 8 клеток.
 * Поэтому, обе координаты клетки (горизонталь row, вертикаль column) могут находиться в пределах от 1 до 8.
 * Горизонтали нумеруются снизу вверх, вертикали слева направо.
 */
data class Square(val column: Int, val row: Int) {
    /**
     * Пример
     *
     * Возвращает true, если клетка находится в пределах доски
     */
    fun inside(): Boolean = column in 1..8 && row in 1..8

    /**
     * Простая
     *
     * Возвращает строковую нотацию для клетки.
     * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
     * Для клетки не в пределах доски вернуть пустую строку
     */
    fun notation(): String {
        val list = listOf("a", "b", "c", "d", "e", "f", "g", "h")
        return if (!inside()) ""
        else "${list[column - 1]}$row"
    }
}

/**
 * Простая
 *
 * Создаёт клетку по строковой нотации.
 * В нотации, колонки обозначаются латинскими буквами от a до h, а ряды -- цифрами от 1 до 8.
 * Если нотация некорректна, бросить IllegalArgumentException
 */
fun square(notation: String): Square {
    val list = listOf("a", "b", "c", "d", "e", "f", "g", "h")
    if (!Regex("""[a-h][1-8]""").matches(notation)) throw IllegalArgumentException()
    val x1 = list.indexOf(notation.toList().first().toString()) + 1
    val y1 = notation.toList().last().toString().toInt()
    return Square(x1, y1)
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматная ладья пройдёт из клетки start в клетку end.
 * Шахматная ладья может за один ход переместиться на любую другую клетку
 * по вертикали или горизонтали.
 * Ниже точками выделены возможные ходы ладьи, а крестиками -- невозможные:
 *
 * xx.xxххх
 * xх.хxххх
 * ..Л.....
 * xх.хxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 * xx.xxххх
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: rookMoveNumber(Square(3, 1), Square(6, 3)) = 2
 * Ладья может пройти через клетку (3, 3) или через клетку (6, 1) к клетке (6, 3).
 */
fun rookMoveNumber(start: Square, end: Square): Int {
    val x1 = start.column
    val y1 = start.row
    val x2 = end.column
    val y2 = end.row
    if (!(start.inside() && end.inside())) throw IllegalArgumentException()
    return when {
        (x1 == x2 && y1 == y2) -> 0
        (x1 != x2 && y1 != y2) -> 2
        else -> 1
    }
}

/**
 * Средняя
 *
 * Вернуть список из клеток, по которым шахматная ладья может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов ладьи см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: rookTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможен ещё один вариант)
 *          rookTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(3, 3), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          rookTrajectory(Square(3, 5), Square(8, 5)) = listOf(Square(3, 5), Square(8, 5))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun rookTrajectory(start: Square, end: Square): List<Square> {
    val x1 = start.column
    val y1 = start.row
    val x2 = end.column
    val y2 = end.row
    val list = mutableListOf(Square(x1, y1))
    if (x1 != x2) list += Square(x2, y1)
    if (y1 != y2) list += Square(x2, y2)
    return list
}

/**
 * Простая
 *
 * Определить число ходов, за которое шахматный слон пройдёт из клетки start в клетку end.
 * Шахматный слон может за один ход переместиться на любую другую клетку по диагонали.
 * Ниже точками выделены возможные ходы слона, а крестиками -- невозможные:
 *
 * .xxx.ххх
 * x.x.xххх
 * xxСxxxxx
 * x.x.xххх
 * .xxx.ххх
 * xxxxx.хх
 * xxxxxх.х
 * xxxxxхх.
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если клетка end недостижима для слона, вернуть -1.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Примеры: bishopMoveNumber(Square(3, 1), Square(6, 3)) = -1; bishopMoveNumber(Square(3, 1), Square(3, 7)) = 2.
 * Слон может пройти через клетку (6, 4) к клетке (3, 7).
 */
fun bishopMoveNumber(start: Square, end: Square): Int {
    val x1 = start.column
    val y1 = start.row
    val x2 = end.column
    val y2 = end.row
    if (!(start.inside() && end.inside())) throw IllegalArgumentException()
    return when {
        (x1 == x2 && y1 == y2) -> 0
        (x1 + y1 == x2 + y2 || x1 - y1 == x2 - y2) -> 1
        ((x1 + x2) % 2 == (y1 + y2) % 2) -> 2
        else -> -1
    }
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный слон может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов слона см. предыдущую задачу.
 *
 * Если клетка end недостижима для слона, вернуть пустой список.
 *
 * Если клетка достижима:
 * - список всегда включает в себя клетку start
 * - клетка end включается, если она не совпадает со start.
 * - между ними должны находиться промежуточные клетки, по порядку от start до end.
 *
 * Примеры: bishopTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          bishopTrajectory(Square(3, 1), Square(3, 7)) = listOf(Square(3, 1), Square(6, 4), Square(3, 7))
 *          bishopTrajectory(Square(1, 3), Square(6, 8)) = listOf(Square(1, 3), Square(6, 8))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun bishopTrajectory(start: Square, end: Square): List<Square> {
    val x1 = start.column
    val y1 = start.row
    val x2 = end.column
    val y2 = end.row
    val list = mutableListOf<Square>()
    var x: Int                                                 // в какой мы точке на оси Х
    var y: Int                                                 // в какой мы точке на оси Y

    if ((x1 + y1) % 2 != (x2 + y2) % 2) return list            // если добраться невозможно

    list += Square(x1, y1)                                     // пишем координаты точки 1

    if (x1 == x2 && y1 == y2) return list                      // если ходов 0

    if (x1 + y1 == x2 + y2 || x1 - y1 == x2 - y2) {            // если
        list += Square(x2, y2)                                 // ходов
        return list                                            // 1
    }

    if (x1 + y1 > x2 + y2) {                                   // если ходов 2
        x = x1
        y = y1
    }
    else {
        x = x2
        y = y2
    }
    while (minOf(x1 + y1, x2 + y2) != x + y) {
        x--
        y--
    }
    if (x !in 1..8 || y !in 1..8) {
        x = x2 + x1 - x
        y = y2 + y1 - y
        while (maxOf(x1 + y1, x2 + y2) != x + y) {
            x++
            y++
        }
    }

    list += Square(x, y)                                       // пишем координаты точки 2

    list += Square(x2, y2)                                     // пишем координаты точки 3

    return list
}

/**
 * Средняя
 *
 * Определить число ходов, за которое шахматный король пройдёт из клетки start в клетку end.
 * Шахматный король одним ходом может переместиться из клетки, в которой стоит,
 * на любую соседнюю по вертикали, горизонтали или диагонали.
 * Ниже точками выделены возможные ходы короля, а крестиками -- невозможные:
 *
 * xxxxx
 * x...x
 * x.K.x
 * x...x
 * xxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: kingMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Король может последовательно пройти через клетки (4, 2) и (5, 2) к клетке (6, 3).
 */
fun kingMoveNumber(start: Square, end: Square): Int {
    val x1 = start.column
    val y1 = start.row
    val x2 = end.column
    val y2 = end.row
    if (!(start.inside() && end.inside())) throw IllegalArgumentException()
    return maxOf(abs(x1 - x2), abs(y1 - y2))
}

/**
 * Сложная
 *
 * Вернуть список из клеток, по которым шахматный король может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов короля см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры: kingTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 *          (здесь возможны другие варианты)
 *          kingTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(4, 2), Square(5, 2), Square(6, 3))
 *          (здесь возможен единственный вариант)
 *          kingTrajectory(Square(3, 5), Square(6, 2)) = listOf(Square(3, 5), Square(4, 4), Square(5, 3), Square(6, 2))
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun kingTrajectory(start: Square, end: Square): List<Square> {
    var x1 = start.column
    var y1 = start.row
    val x2 = end.column
    val y2 = end.row
    val list = mutableListOf(Square(x1, y1))
    while (x1 != x2 || y1 != y2) {
        when {
            (x1 > x2 && y1 > y2) -> {
                list += Square(x1 - 1, y1 - 1)
                x1--
                y1--
            }
            (x1 > x2 && y1 == y2) -> {
                list += Square(x1 - 1, y1)
                x1--
            }
            (x1 > x2 && y1 < y2) -> {
                list += Square(x1 - 1, y1 + 1)
                x1--
                y1++
            }
            (x1 == x2 && y1 > y2) -> {
                list += Square(x1, y1 - 1)
                y1--
            }
            (x1 == x2 && y1 < y2) -> {
                list += Square(x1, y1 + 1)
                y1++
            }
            (x1 < x2 && y1 > y2) -> {
                list += Square(x1 + 1, y1 - 1)
                x1++
                y1--
            }
            (x1 < x2 && y1 == y2) -> {
                list += Square(x1 + 1, y1)
                x1++
            }
            (x1 < x2 && y1 < y2) -> {
                list += Square(x1 + 1, y1 + 1)
                x1++
                y1++
            }
        }
    }
    return list
}

/**
 * Сложная
 *
 * Определить число ходов, за которое шахматный конь пройдёт из клетки start в клетку end.
 * Шахматный конь одним ходом вначале передвигается ровно на 2 клетки по горизонтали или вертикали,
 * а затем ещё на 1 клетку под прямым углом, образуя букву "Г".
 * Ниже точками выделены возможные ходы коня, а крестиками -- невозможные:
 *
 * .xxx.xxx
 * xxKxxxxx
 * .xxx.xxx
 * x.x.xxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 * xxxxxxxx
 *
 * Если клетки start и end совпадают, вернуть 0.
 * Если любая из клеток некорректна, бросить IllegalArgumentException().
 *
 * Пример: knightMoveNumber(Square(3, 1), Square(6, 3)) = 3.
 * Конь может последовательно пройти через клетки (5, 2) и (4, 4) к клетке (6, 3).
 */
fun knightMoveNumber(start: Square, end: Square): Int = knightTrajectory(start,end).size - 1

/**
 * Очень сложная
 *
 * Вернуть список из клеток, по которым шахматный конь может быстрее всего попасть из клетки start в клетку end.
 * Описание ходов коня см. предыдущую задачу.
 * Список всегда включает в себя клетку start. Клетка end включается, если она не совпадает со start.
 * Между ними должны находиться промежуточные клетки, по порядку от start до end.
 * Примеры:
 *
 * knightTrajectory(Square(3, 3), Square(3, 3)) = listOf(Square(3, 3))
 * здесь возможны другие варианты)
 * knightTrajectory(Square(3, 1), Square(6, 3)) = listOf(Square(3, 1), Square(5, 2), Square(4, 4), Square(6, 3))
 * (здесь возможен единственный вариант)
 * knightTrajectory(Square(3, 5), Square(5, 6)) = listOf(Square(3, 5), Square(5, 6))
 * (здесь опять возможны другие варианты)
 * knightTrajectory(Square(7, 7), Square(8, 8)) =
 *     listOf(Square(7, 7), Square(5, 8), Square(4, 6), Square(6, 7), Square(8, 8))
 *
 * Если возможно несколько вариантов самой быстрой траектории, вернуть любой из них.
 */
fun knightTrajectory(start: Square, end: Square): List<Square> {
    val x = start.column
    val y = start.row
    val finish = Square(end.column, end.row)
    val res = mutableListOf<Square>()
    val resToDeleteLast = mutableListOf<Square>()
    var thisSq: Square
    var th2: Square
    var th3: Square
    var th4: Square
    var th5: Square
    var th6: Square
    val list = mutableListOf(
            Square(x, y), Square(x, y),
            Square(x, y), Square(x, y),
            Square(x, y), Square(x, y),
            Square(x, y), Square(x, y))
    val xx = listOf(1, 1, -1, -1, 2, 2, -2, -2)
    val yy = listOf(2, -2, 2, -2, 1, -1, 1, -1)
    val resShortest = mutableListOf(finish, finish, finish, finish, finish, finish, finish)     //важен только размер
                                                                                 //списка (больше 6), а не содержимое

    if (Square(x,y) == finish) return listOf(finish)

    for (i1 in 0 until 8) {
        if (Square(list[i1].column + xx[i1], list[i1].row + yy[i1]).inside()) {
            thisSq = Square(list[i1].column + xx[i1], list[i1].row + yy[i1])
            res.add(thisSq)
            if (thisSq == finish) {
                if (res.size < resShortest.size) {
                    resShortest.clear()
                    res.forEach { resShortest.add(it) }
                }
            }
            else {
                th2 = thisSq
                for (i2 in 0 until 8) {
                    if (Square(th2.column + xx[i2], th2.row + yy[i2]).inside()) {
                        thisSq = Square(th2.column + xx[i2], th2.row + yy[i2])
                        res.add(thisSq)
                        if (thisSq == finish) {
                            if (res.size < resShortest.size) {
                                resShortest.clear()
                                res.forEach { resShortest.add(it) }
                            }
                        }
                        else {
                            th3 = thisSq
                            for (i3 in 0 until 8) {
                                if (Square(th3.column + xx[i3], th3.row + yy[i3]).inside()) {
                                    thisSq = Square(th3.column + xx[i3], th3.row + yy[i3])
                                    res.add(thisSq)
                                    if (thisSq == finish) {
                                        if (res.size < resShortest.size) {
                                            resShortest.clear()
                                            res.forEach { resShortest.add(it) }
                                        }
                                    }
                                    else {
                                        th4 = thisSq
                                        for (i4 in 0 until 8) {
                                            if (Square(th4.column + xx[i4], th4.row + yy[i4]).inside()) {
                                                thisSq = Square(th4.column + xx[i4], th4.row + yy[i4])
                                                res.add(thisSq)
                                                if (thisSq == finish) {
                                                    if (res.size < resShortest.size) {
                                                        resShortest.clear()
                                                        res.forEach { resShortest.add(it) }
                                                    }
                                                }
                                                else {
                                                    th5 = thisSq
                                                    for (i5 in 0 until 8) {
                                                        if (Square(th5.column + xx[i5], th5.row + yy[i5]).inside()) {
                                                            thisSq = Square(th5.column + xx[i5], th5.row + yy[i5])
                                                            res.add(thisSq)
                                                            if (thisSq == finish) {
                                                                if (res.size < resShortest.size) {
                                                                    resShortest.clear()
                                                                    res.forEach { resShortest.add(it) }
                                                                }
                                                            }
                                                            else {
                                                                th6 = thisSq
                                                                for (i6 in 0 until 8) {
                                                                    if (Square(th6.column + xx[i6], th6.row + yy[i6]).inside()) {
                                                                        thisSq = Square(th6.column + xx[i6], th6.row + yy[i6])
                                                                        res.add(thisSq)
                                                                        if (thisSq == finish) {
                                                                            if (res.size < resShortest.size) {
                                                                                resShortest.clear()
                                                                                res.forEach { resShortest.add(it) }
                                                                            }
                                                                        }


                                                                        resToDeleteLast.clear()
                                                                        for (j in 0..res.size - res.size + 4) {
                                                                            resToDeleteLast.add(res[j])
                                                                        }
                                                                        res.clear()
                                                                        resToDeleteLast.forEach { res.add(it) }
                                                                    }
                                                                }
                                                            }
                                                            resToDeleteLast.clear()
                                                            for (j in 0..res.size - res.size + 3) {
                                                                resToDeleteLast.add(res[j])
                                                            }
                                                            res.clear()
                                                            resToDeleteLast.forEach { res.add(it) }
                                                        }
                                                    }
                                                }
                                                resToDeleteLast.clear()
                                                for (j in 0..res.size - res.size + 2) {
                                                    resToDeleteLast.add(res[j])
                                                }
                                                res.clear()
                                                resToDeleteLast.forEach { res.add(it) }
                                            }
                                        }
                                    }
                                    resToDeleteLast.clear()
                                    for (j in 0..res.size - res.size+1) {
                                        resToDeleteLast.add(res[j])
                                    }
                                    res.clear()
                                    resToDeleteLast.forEach { res.add(it) }
                                }
                            }
                        }
                        resToDeleteLast.clear()
                        for (j in 0..res.size - res.size) {
                            resToDeleteLast.add(res[j])
                        }
                        res.clear()
                        resToDeleteLast.forEach { res.add(it) }
                    }
                }
            }
            res.clear()
        }
    }

    val finalResult = mutableListOf<Square>()
    finalResult.add(Square(x, y))
    resShortest.forEach { finalResult.add(it) }
    return finalResult
}
