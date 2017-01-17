package wtf.jessebanks.hawthorne

import org.junit.Assert.assertEquals
import org.junit.Test
import wtf.jessebanks.hawthorne.dsp.Complex
import wtf.jessebanks.hawthorne.dsp.ComplexArray

/**
 * Tests for Complex/ComplexArray objects
 * Created by jessebanks on 1/16/17.
 */
class ComplexTests {

    val DELTA = 0.00001

    @Test
    fun testComplex() {
        var complex = Complex()

        assertEquals(complex.real, 0.0, DELTA)
        assertEquals(complex.imaginary, 0.0, DELTA)
        assertEquals(complex.magnitude, 0.0, DELTA)

        complex = Complex(1.0, 1.0)
        assertEquals(complex.magnitude, Math.sqrt(2.0), DELTA)

        complex = Complex(1.0, 2.0)
        assertEquals(complex.magnitude, Math.sqrt(5.0), DELTA)
    }

    @Test
    fun testOperators() {
        var a = Complex(1.0, 1.0)
        var b = Complex(2.0, 2.0)

        var c = a + b
        assertEquals(c.real, 3.0, DELTA)
        assertEquals(c.imaginary, 3.0, DELTA)

        c = a - b
        assertEquals(c.real, -1.0, DELTA)
        assertEquals(c.imaginary, -1.0, DELTA)

        c = a * b
        assertEquals(c.real, 0.0, DELTA)
        assertEquals(c.imaginary, 4.0, DELTA)
    }

    @Test(expected = ArrayIndexOutOfBoundsException::class)
    fun testComplexArray() {

        var complexArray = ComplexArray(2)
        complexArray[0] = Complex()
        complexArray[1] = Complex(1.0, 2.0)

        assertEquals(complexArray[1].magnitude, Math.sqrt(5.0), DELTA)
        assertEquals(complexArray[0].real, 0.0, DELTA)

        var counter = 0
        complexArray.forEach {
            counter++
        }
        assertEquals(counter, 2)

        complexArray[2] = Complex()
    }

}