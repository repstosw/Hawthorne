package wtf.jessebanks.hawthorne

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Custom line plot view
 * Created by jessebanks on 1/19/17.
 */
class PlotView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    constructor(context: Context?) : this(context, null)

    private var linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    // X and Y scales, i.e. what the full View represents
    var scalex: Int = 0
        set(value) {
            field = value
            xScalar = width.toDouble() / value
        }

    var scaley: Int = 0
        set(value) {
            field = value
            yScalar = height.toDouble() / value
        }

    var data: DoubleArray = DoubleArray(0)
        set(data) {
            scalex = data.size
            invalidate()
        }

    private var xScalar: Double = 1.0
    private var yScalar: Double = 1.0

    init {
        linePaint.color = Color.BLUE
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeWidth = 1.0f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        xScalar = width.toDouble() / scalex
        yScalar = height.toDouble() / scaley
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        path.reset()
        if (isInEditMode) {
            scalex = 100
            scaley = 100

            path.moveTo(0.0f, 0.0f)

            for (i in 0..100) {
                path.lineTo((i * xScalar).toFloat(), height - (Math.sin(i / 4.0) * 25 * yScalar).toFloat() - (height / 2))
            }

            canvas.drawPath(path, linePaint)
            return
        }

        path.moveTo(0.0f, data[0].toFloat())
        data.forEachIndexed {
            i, d -> path.lineTo((i * xScalar).toFloat(), height - (d * yScalar).toFloat())
        }

        canvas.drawPath(path, linePaint)
    }



}