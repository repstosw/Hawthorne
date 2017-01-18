package wtf.jessebanks.hawthorne

import org.junit.Assert.assertEquals
import org.junit.Test
import wtf.jessebanks.hawthorne.dsp.Complex
import wtf.jessebanks.hawthorne.dsp.ComplexArray
import wtf.jessebanks.hawthorne.dsp.fft

/**
 * DSP tests
 * Created by jessebanks on 1/17/17.
 */
class DspTests {

    val DELTA = 0.0001
    @Test
    fun testFFTImpulse() {
        val array = ComplexArray(4)
        array[0] = Complex(1.0, 0.0)

        fft(array).forEach {
            assertEquals(it.real, 1.0, DELTA)
            assertEquals(it.imaginary, 0.0, DELTA)
        }
    }

    @Test
    fun testFFTShifted() {
        val array = ComplexArray(4)
        array[1] = Complex(1.0, 0.0)

        fft(array).forEach {
            assertEquals(it.magnitude, 1.0, DELTA)
        }
    }
}