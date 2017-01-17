package wtf.jessebanks.hawthorne.dsp

/**
 * ComplexArray class.
 * Encapsulates dual DoubleArray objects for efficient representation of Complex types.
 * Created by jessebanks on 1/16/17.
 */
class ComplexArray(size: Int) : Iterable<Complex> {

    private val realArray = DoubleArray(size)
    private val imagArray = DoubleArray(size)

    val size = realArray.size

    operator fun get(index: Int) : Complex {
        return Complex(realArray[index], imagArray[index])
    }

    operator fun set(index: Int, value: Complex) {
        realArray[index] = value.real
        imagArray[index] = value.imaginary
    }

    override fun iterator() = object : Iterator<Complex> {
        private var counter = 0
        override fun hasNext() = counter < realArray.size
        override fun next() = this@ComplexArray[counter++]
    }

}