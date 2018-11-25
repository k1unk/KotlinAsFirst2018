@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.lang.Math.pow

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                }
                else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> = TODO()


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val wrong1 = listOf('ж', 'ч', 'ш', 'щ')
    val wrong2small = listOf('ы', 'я', 'ю')
    val wrong2big = listOf('Ы','Я','Ю')
    val right2small = listOf('и', 'а', 'у')
    val right2big = listOf('И','А','У')
    var countWrong = 0                    //чтобы узнать была ли предыдущая буква ЖЧШЩ и стоит ли делать проверку на ЫЯЮ
    var countWrote = 0                    //чтобы узнать была ли буква уже написана
    var index: Int
    for (line in File(inputName).readText()) {
        if (line.toLowerCase() !in wrong2small) countWrong = 0
        if (line.toLowerCase() in wrong1) countWrong = 1
        if (line in wrong2small && countWrong == 1) {
            index = wrong2small.indexOf(line)
            outputStream.write(right2small[index].toString())
            countWrote = 1
            countWrong = 0
        }
        if (line in wrong2big && countWrong == 1) {
            index = wrong2big.indexOf(line)
            outputStream.write(right2big[index].toString())
            countWrote = 1
            countWrong = 0
        }
        if (countWrote == 0) outputStream.write(line.toString())
        else countWrote = 0
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var maxLength = 0

    for (line in File(inputName).readLines()) {
        if (line.trim().length > maxLength) maxLength = line.trim().length
    }

    for (line in File(inputName).readLines()) {
        var count = if (line.trim().length % 2 == 0) (maxLength - line.trim().length) / 2
                    else (maxLength - line.trim().length) / 2
        while (count > 0) {
            count--
            outputStream.write(" ")
        }
        outputStream.write(line.trim())
        outputStream.newLine()
    }
    outputStream.close()
}


/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var maxLength = 0                           // максимальная длина строки

    for (line in File(inputName).readLines()) {
        if (line.trim().length > maxLength) maxLength = line.trim().length
    }

    for (line in File(inputName).readLines()) {
        var countWords = 0                     // сколько слов в строке
        var countLetters = 0                   // количество букв в строке
        var countSpaces: Int                   // количество пробельных отрезков в строке
        var stabilSpaces = 0                   // стабильное количество пробелов после слова
        var spacesPlus1: Int                   // в скольки пробельных отрезках надо добавить пробел

        for (part in line.trim().split(" ")) countWords++
        for (part in line) if (part != ' ') countLetters++
        countSpaces = countWords - 1
        if (countSpaces != 0) stabilSpaces = (maxLength - countLetters) / countSpaces
        spacesPlus1 = maxLength - countLetters - stabilSpaces * countSpaces

        for (part in line.trim().split(" ")) {
            if (countWords > 1) {
                var newStabilProbels = stabilSpaces
                outputStream.write(part)
                while (newStabilProbels > 0) {
                    outputStream.write(" ")
                    newStabilProbels--
                }
                if (spacesPlus1 > 0) {
                    outputStream.write(" ")
                    spacesPlus1--
                }
                countWords--
            }
            else outputStream.write(part)
        }
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    val resTop20 = mutableMapOf<String, Int>()
    var top = 20
    var reg2: String
    for (line in File(inputName).readLines()) {
        val reg1 = Regex("""[^ a-zA-Zа-яёА-ЯЁ]*""").replace(line, " ")
        for (a in reg1.split("  ")) {
            reg2 = Regex("""[^a-zA-Zа-яёА-ЯЁ]+""").replace(a, "").toLowerCase()
            if (reg2.isNotEmpty()) res[reg2] = res.getOrDefault(reg2, 0) + 1
        }
    }
    for ((a, b) in res.toList().sortedByDescending { it.second }.toMap().toMutableMap()) {
        if (top > 0) resTop20[a] = b
        top--
    }
    return resTop20
}


/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()

    for (line in File(inputName).readLines()) {
        for (letter in 0 until line.length) {
            var c = 0
            for ((a, b) in dictionary) {
                if (a.toString().toLowerCase() == line[letter].toString().toLowerCase()) {
                    if (line[letter].isUpperCase()) {
                        var bToMut = ""
                        for (i in 0..b.length - b.length) bToMut += b[i].toString().toUpperCase()
                        for (i in 1 until b.length) bToMut += b[i].toString().toLowerCase()
                        outputStream.write(bToMut)
                    }
                    else outputStream.write(b.toLowerCase())
                    c++
                }
            }
            if (c == 0) outputStream.write(line[letter].toString())
        }
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val resAll = mutableListOf<String>()
    val resLongest = mutableListOf<String>()
    var wrongNum = 0
    var max = 0
    for (line in File(inputName).readLines()) {
        for (words in line.toLowerCase().split(" ")) {
            for (first in 0 until words.length) {
                for (others in first + 1 until words.length) {
                    if (words[first] == words[others]) {
                        wrongNum = 1
                    }
                }
            }
            if (wrongNum == 0) resAll.add(line)
        }
    }
    if (resAll.isEmpty()) outputStream.close()
    for (i in 0 until resAll.size) {
        var l = 0
        for (j in resAll[i]) l++
        if (l > max) max = l
    }
    for (i in 0 until resAll.size) {
        if (resAll[i].length == max) resLongest.add(resAll[i])
    }
    if (resLongest.size > 1) {
        for (i in 0..resLongest.size - 2) {
            outputStream.write(resLongest[i])
            outputStream.write(", ")
        }
    }
    for (i in resLongest.size - 1..resLongest.size - 1) outputStream.write(resLongest[i])
    outputStream.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
    <body>
        <p>
            Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
            Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
        </p>
        <p>
            Suspendisse <s>et elit in enim tempus iaculis</s>.
        </p>
    </body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var star1 = 0
    var star2 = 0
    var star12 = 0
    var wave = 0

    outputStream.write("<html>")
    outputStream.newLine()
    outputStream.write("<body>")
    outputStream.newLine()
    outputStream.write("<p>")
    outputStream.newLine()
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) outputStream.write("</p><p>")

        for (letter in 0 until line.length) {
            when {
                (star1 == 0 && line[letter] == '*' && line[letter - 1] != '*' && line[letter + 1] != '*' && star12 == 0) -> {
                    star1 = 1
                    star12 = 1
                    outputStream.write("<i>")
                }
                (star1 == 1 && line[letter] == '*' && line[letter - 1] != '*' && line[letter + 1] != '*' && star12 == 1) -> {
                    star1 = 0
                    star12 = 0
                    outputStream.write(("</i>"))
                }


                (star2 == 0 && line[letter] == '*' && line[letter + 1] == '*' && line[letter - 1] != '*') -> {
                    star2 = 1
                    star12 = 1
                    outputStream.write(("<b>"))
                }
                (star2 == 0 && line[letter] == '*' && line[letter - 1] == '*' && line[letter - 2] != '*') -> { }
                (star2 == 0 && line[letter] == '*' && line[letter - 1] == '*' && line[letter - 2] == '*' && star12 == 0) -> {
                    star12 = 1
                    outputStream.write("</i>")
                }
                (star2 == 1 && line[letter] == '*' && line[letter + 1] == '*' && line[letter - 1] != '*') -> {
                    star2 = 0
                    star12 = 0
                    outputStream.write(("</b>"))
                }
                (star2 == 1 && line[letter] == '*' && line[letter - 1] == '*' && line[letter - 2] != '*') -> { }
                (star2 == 1 && line[letter] == '*' && line[letter - 1] == '*' && line[letter - 2] == '*' && star12 == 1) -> {
                    star12 = 0
                    outputStream.write(("<i>"))
                }


                (wave == 0 && line[letter] == '~' && line[letter + 1] == '~' && line[letter - 1] != '~') -> {
                    wave = 1
                    outputStream.write(("<s>"))
                }
                (wave == 0 && line[letter] == '~' && line[letter - 1] == '~') -> { }
                (wave == 1 && line[letter] == '~' && line[letter + 1] == '~' && line[letter - 1] != '~') -> {
                    wave = 0
                    outputStream.write(("</s>"))
                }
                (wave == 1 && line[letter] == '~' && line[letter - 1] == '~') -> { }
                else -> outputStream.write((line[letter].toString()))
            }
        }
        for (letter in line.length - 1 until line.length - 1) outputStream.write((line[letter].toString()))
        for (letter in line.length until line.length) outputStream.write(line[letter].toString())
    }
    outputStream.write("</p>")
    outputStream.newLine()
    outputStream.write("</body>")
    outputStream.newLine()
    outputStream.write("</html>")
    outputStream.newLine()
    outputStream.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
* Утка по-пекински
    * Утка
    * Соус
* Салат Оливье
    1. Мясо
        * Или колбаса
    2. Майонез
    3. Картофель
    4. Что-то там ещё
* Помидоры
* Фрукты
    1. Бананы
    23. Яблоки
        1. Красные
        2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
  <body>
    <ul>
      <li>
        Утка по-пекински
        <ul>
          <li>Утка</li>
          <li>Соус</li>
        </ul>
      </li>
      <li>
        Салат Оливье
        <ol>
          <li>Мясо
            <ul>
              <li>
                  Или колбаса
              </li>
            </ul>
          </li>
          <li>Майонез</li>
          <li>Картофель</li>
          <li>Что-то там ещё</li>
        </ol>
      </li>
      <li>Помидоры</li>
      <li>
        Яблоки
        <ol>
          <li>Красные</li>
          <li>Зелёные</li>
        </ol>
      </li>
    </ul>
  </body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
   19935
*    111
--------
   19935
+ 19935
+19935
--------
 2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
  235
*  10
-----
    0
+235
-----
 2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val maxLength = (lhv * rhv).toString().length + 1
    val lines = "".padStart(maxLength, '-')

    outputStream.write(lhv.toString().padStart(maxLength, ' '))
    outputStream.newLine()
    outputStream.write("*")
    outputStream.write(rhv.toString().padStart(maxLength - 1, ' '))
    outputStream.newLine()
    outputStream.write(lines)
    outputStream.newLine()

    for (i in 0..rhv.toString().length - rhv.toString().length) {
        val numOfRhvLast = rhv / pow(10.0, i.toDouble()).toInt() % 10
        outputStream.write((lhv * numOfRhvLast).toString().padStart(maxLength, ' '))
        outputStream.newLine()
    }
    for (i in 1 until rhv.toString().length) {
        val numOfRhv = rhv / pow(10.0, i.toDouble()).toInt() % 10
        outputStream.write("+")
        outputStream.write((lhv * numOfRhv).toString().padStart(maxLength - i - 1, ' '))
        outputStream.newLine()
    }
    outputStream.write(lines)
    outputStream.newLine()
    outputStream.write((lhv * rhv).toString().padStart(maxLength, ' '))

    outputStream.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
  19935 | 22
 -198     906
 ----
    13
    -0
    --
    135
   -132
   ----
      3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val result = lhv / rhv
    var umenshaemoe = 0
    var umenshaemoeString: String
    var vichitaemoe: Int
    var ostatok: Int
    var ostalosTcifr: Int
    var newTcifra: Int

    if (lhv / rhv > 9) {
        // 1 строка
        outputStream.write(" $lhv | $rhv")
        outputStream.newLine()

        // 2 строка
        var stop = 0
        for (i in 0 until lhv.toString().length) {
            if (lhv / pow(10.0, (lhv.toString().length - i).toDouble()) / rhv > 1 && stop == 0) {
                umenshaemoe = (lhv / pow(10.0, (lhv.toString().length - i).toDouble())).toInt()
                stop = 1
            }
        }
        vichitaemoe = umenshaemoe / rhv * rhv
        outputStream.write("-$vichitaemoe")
        for (i in 0..lhv.toString().length - vichitaemoe.toString().length + 2) outputStream.write(" ")
        outputStream.write(result.toString())
        outputStream.newLine()

        // 3 строка
        outputStream.write("".padStart(vichitaemoe.toString().length + 1, '-'))
        outputStream.newLine()

        //
        ostalosTcifr = lhv.toString().length - umenshaemoe.toString().length

        // остальные строки
        if (lhv / rhv > 0) {
            umenshaemoeString = umenshaemoe.toString()
            while (ostalosTcifr > 0) {
                ostatok = umenshaemoeString.toInt() - vichitaemoe
                newTcifra = lhv / (pow(10.0, ostalosTcifr - 1.0)).toInt() % 10
                umenshaemoeString = ostatok.toString().plus(newTcifra.toString())
                vichitaemoe = umenshaemoeString.toInt() / rhv * rhv
                ostalosTcifr--

                outputStream.write("".padStart(lhv.toString().length + 1 - ostalosTcifr - umenshaemoeString.length, ' '))
                outputStream.write(umenshaemoeString)
                outputStream.newLine()

                outputStream.write("".padStart(lhv.toString().length + 1 - ostalosTcifr - vichitaemoe.toString().length - 1, ' '))
                outputStream.write("-$vichitaemoe")
                outputStream.newLine()

                outputStream.write("".padStart(minOf(lhv.toString().length + 1 - ostalosTcifr - vichitaemoe.toString().length - 1,
                        lhv.toString().length+1 - ostalosTcifr - umenshaemoeString.length ), ' '))
                for (i in 0 until maxOf(vichitaemoe.toString().length + 1, umenshaemoeString.length)) outputStream.write("-")
                outputStream.newLine()
            }

            ostatok = umenshaemoeString.toInt() - vichitaemoe
            outputStream.write(ostatok.toString().padStart(lhv.toString().length + 1, ' '))
        }
        else outputStream.write(lhv.toString().padStart(lhv.toString().length + 1, ' '))
    }

    else {
        if (lhv / rhv == 0 && lhv.toString().length > (lhv / rhv * rhv).toString().length ) {
            // 1 строка
            outputStream.write("$lhv | $rhv")
            outputStream.newLine()
            // 2 строка
            outputStream.write("-0".padStart(lhv.toString().length, ' '))
            outputStream.write("   0")
            outputStream.newLine()
            //3 строка
            for (i in 0 until lhv.toString().length) {
                outputStream.write("-")
            }
            outputStream.newLine()
            // 4 строка
            outputStream.write(lhv.toString())
        }
        else {
            vichitaemoe = lhv / rhv * rhv
            if (lhv.toString().length > vichitaemoe.toString().length) {
                // 1 строка
                outputStream.write("$lhv | $rhv")
                outputStream.newLine()
                // 2 строка
                outputStream.write("-$vichitaemoe   $result")
                outputStream.newLine()
                //3 строка
                outputStream.write("".padStart(lhv.toString().length, '-'))
                outputStream.newLine()
                // 4 строка
                outputStream.write((lhv - vichitaemoe).toString().padStart(lhv.toString().length, ' '))
                outputStream.newLine()
            }
            else {
                // 1 строка
                outputStream.write(" $lhv | $rhv")
                outputStream.newLine()
                // 2 строка
                outputStream.write("-$vichitaemoe   $result")
                outputStream.newLine()
                // 3 строка
                outputStream.write("".padStart(lhv.toString().length + 1, '-'))
                outputStream.newLine()
                // 4 строка
                outputStream.write((lhv - vichitaemoe).toString().padStart(lhv.toString().length + 1, ' '))
                outputStream.newLine()
            }
        }
    }
    outputStream.close()
}

