package wtf.jessebanks.hawthorne.dsp

import android.util.Log
import java.util.concurrent.BlockingQueue

/**
 * DSP processing thread
 * Created by jessebanks on 1/16/17.
 */
class ProcessingThread(val consumeQueue: BlockingQueue<ShortArray>,
                       val resultQueue: BlockingQueue<DoubleArray>) : Thread("ProcessingThread") {

    private val TAG = "ProcessingThread"

    var exit: Boolean = false
        get() = synchronized(this, { field })
        set(value) = synchronized(this, {
            Log.i(TAG, "Setting quit value: $value")
            field = value })

    override fun run() {
        Log.i(TAG, "Starting processing thread")

        while(!exit) {
            val samples = consumeQueue.take()

            // Apply Hamming window
            val hammingSamples = hamming(samples)

            // Apply FFT
            val fftSamples = fft(hammingSamples)

            // Get magnitudes
            val mags = mag2db(fftSamples)

            // For now only add to queue if there's space, since UI will be updating much more slowly
            if (resultQueue.isEmpty()) {
                resultQueue.add(mags)
            }
        }

        Log.i(TAG, "Exiting processing thread")

    }
}