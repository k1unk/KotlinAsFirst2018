@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.digitNumber
import java.lang.Math.*
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var n = 0.0
    for (element in v) {
        n += element * element
    }
    return if (v.isEmpty()) 0.0 else sqrt(n)
}


/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    return if (list.isNotEmpty()) {
        list.sum() / list.size
    } else 0.0
}


/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val b = list.sum() / list.size
    for (i in 0 until list.size) {
        list[i] -= b
    }
    return list
}


/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var c = 0.0
    for (i in 0 until a.size) {
        val element1 = a[i]
        val element2 = b[i]
        c += element1 * element2
    }
    return c
}


/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var c = 0.0
    for (i in 0 until p.size) {
        val element = p[i]
        c += element * pow(x, i.toDouble())
    }
    return c
}


/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    var c = 0.0
    for (i in 0 until list.size) {
        c += list[i]
        list[i] = c
    }
    return list
}


/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var k = 2
    var t = n
    val result = mutableListOf<Int>()
    while (t > 1) {
        if (t % k == 0) {
            result.add(k)
            t /= k
        } else k++
    }
    return result
}


/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")


/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var num = n
    val res = mutableListOf<Int>()
    while (num != 0) {
        res.add(num % base)
        num /= base
    }
    if (n == 0) res.add(0)
    return res.reversed()
}


/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    var k: Int
    var num = n
    var res = mutableListOf<String>()
    val x2: List<String> = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c",
            "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z")
    if (num == 0) res.add("0")
    else {
        while (num > 0) {
            k = num % base
            res.add(x2[k])
            num /= base
        }
    }
    res = res.reversed().toMutableList()
    return res.joinToString(separator = "")
}


/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var c = 0
    val res = digits.reversed()
    for (i in 0 until res.size) {
        c = (c + res[i] * pow(base.toDouble(), i.toDouble())).toInt()
    }
    return c
}


/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    val res = mutableListOf<Int>()
    var c = 0
    for (i in str) {
        when (i) {
            '0' -> res.add(0)
            '1' -> res.add(1)
            '2' -> res.add(2)
            '3' -> res.add(3)
            '4' -> res.add(4)
            '5' -> res.add(5)
            '6' -> res.add(6)
            '7' -> res.add(7)
            '8' -> res.add(8)
            '9' -> res.add(9)
            'a' -> res.add(10)
            'b' -> res.add(11)
            'c' -> res.add(12)
            'd' -> res.add(13)
            'e' -> res.add(14)
            'f' -> res.add(15)
            'g' -> res.add(16)
            'h' -> res.add(17)
            'i' -> res.add(18)
            'j' -> res.add(19)
            'k' -> res.add(20)
            'l' -> res.add(21)
            'm' -> res.add(22)
            'n' -> res.add(23)
            'o' -> res.add(24)
            'p' -> res.add(25)
            'q' -> res.add(26)
            'r' -> res.add(27)
            's' -> res.add(28)
            't' -> res.add(29)
            'u' -> res.add(30)
            'v' -> res.add(31)
            'w' -> res.add(32)
            'x' -> res.add(33)
            'y' -> res.add(34)
            'z' -> res.add(35)
        }
    }
    for (i in 0 until res.size) {
        c = (c + res.reversed()[i] * pow(base.toDouble(), i.toDouble())).toInt()
    }
    return c
}


/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var res = mutableListOf<String>()
    var num = n
    val x: List<String> = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    val y: List<Int> = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    for (i in 0 until y.size) {
        if (num - y[i] >= 0) {
            while (num - y[i] >= 0) {
                res.add(x[i])
                num -= y[i]
            }
        }
    }
    res = res.toMutableList()
    return res.joinToString(separator = "")
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String {
    val num = n
    var res = mutableListOf<String>()
    val x1: List<String> = listOf("один", "два", "три", "четыре", "пять", "шесть",
            "семь", "восемь", "девять")
    val x1_0: List<String> = listOf("одна", "две", "три", "четыре", "пять", "шесть",
            "семь", "восемь", "девять")
    val x2: List<String> = listOf("десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
            "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать")
    val x3: List<String> = listOf("двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят",
            "семьдесят", "восемьдесят", "девяносто")
    val x4: List<String> = listOf("", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот",
            "семьсот", "восемьсот", "девятьсот")
    if (num / 100000 % 10 in 1..9) {
        res.add(x4[num / 100000 % 10])
    }
    if (num / 10000 % 10 in 2..9) {
        res.add(x3[num / 10000 % 10 - 2])
    }
    if (num / 10000 % 10 == 1) {
        res.add(x2[num / 1000 % 10])
    }
    if (num / 1000 % 10 in 1..9 && num / 10000 % 10 != 1) {
        res.add(x1_0[num / 1000 % 10 - 1])
    }
    if (num / 1000 % 10 == 1 && num / 10000 % 10 != 1) {
        res.add("тысяча")
    }
    if (num / 1000 % 10 in 2..4 && num / 10000 % 10 != 1) {
        res.add("тысячи")
    }
    if ((num / 1000 > 0) && (num / 1000 % 10 != 1) && (num / 1000 % 10 !in 2..4) || num / 10000 % 10 == 1) {
        res.add("тысяч")
    }
    if (num / 100 % 10 in 1..9) {
        res.add(x4[num / 100 % 10])
    }
    if (num / 10 % 10 in 2..9) {
        res.add(x3[num / 10 % 10 - 2])
    }
    if (num / 10 % 10 == 1) {
        res.add(x2[num % 100 - 10])
    }
    if (num / 1 % 10 in 1..9 && num / 10 % 10 != 1) {
        res.add(x1[num / 1 % 10 - 1])
    }
    res = res.toMutableList()
    return res.joinToString(separator = " ")
}