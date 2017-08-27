package wtf.jessebanks.hawthorne.dsp

/**
 * DSP functions
 * Created by jessebanks on 1/16/17.
 */
val MAX_N = 1024 // Maximum samples processed by FFT at once

/**
 * Vanilla Cooley-Tukey FFT algorithm, without scaling.
 * Modified from pseudocode at https://en.wikipedia.org/wiki/Cooley-Tukey_FFT_algorithm
 */
fun fft(x: ComplexArray): ComplexArray {

    val n = x.size

    // Base case
    if (n == 1) {
        return x
    }

    // check radix
    if (n % 2 != 0) {
        throw IllegalArgumentException("Input data length must have radix 2, length is $n")
    }

    val even = ComplexArray(n / 2)
    for (i in 0 until (n / 2)) {
        even[i] = x[2 * i]
    }
    val evenResult = fft(even)

    val odd = ComplexArray(n / 2)
    for (i in 0 until (n / 2)) {
        odd[i] = x[(2 * i) + 1]
    }
    val oddResult = fft(odd)

    val final = ComplexArray(n)
    for (k in 0 until (n / 2)) {
        val twiddle = Twiddles[k * (MAX_N / n)]
        final[k] = evenResult[k] + twiddle * oddResult[k]
        final[k + (n / 2)] = evenResult[k] - twiddle * oddResult[k]
    }

    return final
}


object Twiddles {

    private val twiddles = ComplexArray(MAX_N / 2)

    init {
        for (i in 0 until (MAX_N / 2)) {
            val thetak = (-2.0 * Math.PI * i) / MAX_N
            twiddles[i] = Complex(Math.cos(thetak), Math.sin(thetak))
        }
    }

    operator fun get(i: Int) : Complex {
        return twiddles[i]
    }
}

/**
 * Apply a Hamming window to an input of integers.
 */
fun hamming(input: ShortArray): ComplexArray {
    val N = input.size

    val array = ComplexArray(N)
    input.forEachIndexed {
        i, sh ->
            val scalar = 0.54 - (0.46 * Math.cos((2 * Math.PI * i) / N))
            array[i] = Complex(sh * scalar, 0.0)
    }

    return array
}

/**
 * Convert a array of complex numbers to an array of dB magnitudes
 */
fun mag2db(input: ComplexArray): DoubleArray {
    val output = DoubleArray(input.size)
    input.forEachIndexed { i, complex ->  output[i] = 20 * Math.log10(complex.magnitude) }
    return output
}
