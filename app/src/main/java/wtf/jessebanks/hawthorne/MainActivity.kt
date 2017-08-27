package wtf.jessebanks.hawthorne

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import wtf.jessebanks.hawthorne.dsp.BufferQueue
import wtf.jessebanks.hawthorne.dsp.ProcessingThread
import wtf.jessebanks.hawthorne.dsp.RecordThread
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class MainActivity : AppCompatActivity() {

    private val audioQueue = BufferQueue(512)

    private val fftQueue: BlockingQueue<DoubleArray> = LinkedBlockingQueue(1)
    private var recordThread = RecordThread(audioQueue)
    private var processingThread = ProcessingThread(audioQueue, fftQueue)
    private val handler = Handler()

    private var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plotView.scaley = 100

        activity_main.startButton.setOnClickListener { startProcessing()}
    }

    val plotUpdate: Runnable = object : Runnable {
        override fun run() {
            if (!fftQueue.isEmpty()) {
                var array = fftQueue.take()
                array = array.copyOf(array.size / 2) // Nyquist
                plotView.data = array
            }
            handler.postDelayed(this, 50)
        }
    }


    fun startProcessing() {

        if (isRunning) return
        recordThread = RecordThread(audioQueue)
        processingThread = ProcessingThread(audioQueue, fftQueue)

        recordThread.start()
        processingThread.start()

        isRunning = true
        handler.post(plotUpdate)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        recordThread.exit = true
        processingThread.exit = true

        handler.removeCallbacks(null)
        isRunning = false
        super.onPause()
    }
}
