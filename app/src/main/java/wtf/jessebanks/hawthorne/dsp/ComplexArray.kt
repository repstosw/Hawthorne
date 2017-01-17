package wtf.jessebanks.hawthorne.dsp

/**
 * ComplexArray class.
 * Encapsulates dual DoubleArray objects for efficient representation of Complex types.
 * Created by jessebanks on 1/16/17.
 */
class ComplexArray(size: Int) {

    private val realArray = DoubleArray(size)
    private val imagArray = DoubleArray(size)

    operator fun get(index: Int) : Complex {
        return Complex(realArray[index], imagArray[index])
    }

    operator fun set(index: Int, value: Complex) {
        realArray[index] = value.real
        imagArray[index] = value.imaginary
    }
}