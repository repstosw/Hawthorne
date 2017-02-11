package wtf.jessebanks.hawthorne

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*
import wtf.jessebanks.hawthorne.dsp.ProcessingThread
import wtf.jessebanks.hawthorne.dsp.RecordThread
import wtf.jessebanks.hawthorne.dsp.fft
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity() {

    private val audioQueue: BlockingQueue<ShortArray> = LinkedBlockingQueue()

    private val fftQueue: BlockingQueue<DoubleArray> = LinkedBlockingQueue(1)
    private val recordThread = RecordThread(audioQueue)
    private val processingThread = ProcessingThread(audioQueue, fftQueue)
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recordThread.start()
        processingThread.start()

        handler.post(plotUpdate)
    }

    val plotUpdate: Runnable = object : Runnable {
        override fun run() {
            if (!fftQueue.isEmpty()) {
                plotView.data = fftQueue.take()
            }
            handler.postDelayed(this, 250)
        }
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        recordThread.exit = true
        processingThread.exit = true

        handler.removeCallbacks(null)
        super.onPause()
    }
}
