package com.hits.itindr

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.math.max

class TagFlowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    var tags: List<TagItem> = emptyList()
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var cornerRadius = 0f
    private var horizontalPadding = 0
    private var verticalPadding = 0
    private var tagSpacing = 0
    private var lineSpacing = 0
    private var defaultBgColor = Color.GRAY
    private var selectedBgColor = Color.BLACK
    private var defaultTextColor = Color.WHITE
    private var selectedTextColor = Color.BLACK

    private val tagRects = mutableListOf<TagRect>()

    private val selectionManager = TagSelectionManager()

    var onTagClick: ((String, Boolean) -> Unit)? = null
    var onSelectionChange: ((List<String>) -> Unit)? = null
    var onSelectionLimitReached: ((Int) -> Unit)? = null

    init {
        context.withStyledAttributes(attrs, R.styleable.TagFlowView) {
            defaultBgColor = getColor(R.styleable.TagFlowView_tagBackgroundColor, Color.GRAY)
            selectedBgColor = getColor(R.styleable.TagFlowView_tagSelectedBackgroundColor, Color.BLACK)
            defaultTextColor = getColor(R.styleable.TagFlowView_tagTextColor, Color.WHITE)
            selectedTextColor = getColor(R.styleable.TagFlowView_tagSelectedTextColor, Color.BLACK)

            textPaint.textSize = getDimension(R.styleable.TagFlowView_tagTextSize, 40f)

            val typeface = Typeface.DEFAULT
            val textStyle = getInt(R.styleable.TagFlowView_tagTextStyle, Typeface.NORMAL)
            textPaint.typeface = Typeface.create(typeface, textStyle)

            horizontalPadding = getDimensionPixelSize(R.styleable.TagFlowView_tagHorizontalPadding, 16.dp)
            verticalPadding = getDimensionPixelSize(R.styleable.TagFlowView_tagVerticalPadding, 8.dp)
            tagSpacing = getDimensionPixelSize(R.styleable.TagFlowView_tagSpacing, 8.dp)
            lineSpacing = getDimensionPixelSize(R.styleable.TagFlowView_tagLineSpacing, 8.dp)
            cornerRadius = getDimension(R.styleable.TagFlowView_tagCornerRadius, 16.dp.toFloat())
        }

        selectionManager.onSelectionChange = { selectedIds ->
            onSelectionChange?.invoke(selectedIds)
            invalidate()
        }
        selectionManager.onSelectionLimitReached = { max ->
            onSelectionLimitReached?.invoke(max)
        }
    }

    var multiSelect: Boolean
        get() = selectionManager.multiSelect
        set(value) { selectionManager.multiSelect = value }

    var maxSelected: Int?
        get() = selectionManager.maxSelected
        set(value) { selectionManager.maxSelected = value }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        calculateTagPositions(width)
        val totalHeight = tagRects.lastOrNull()?.rect?.bottom?.toInt() ?: paddingTop
        setMeasuredDimension(width, resolveSize(totalHeight + paddingBottom, heightMeasureSpec))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) = Unit

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (rect in tagRects) {
            updatePaint(rect.item)
            canvas.drawRoundRect(rect.rect, cornerRadius, cornerRadius, bgPaint)
            val textX = rect.rect.left + horizontalPadding
            val textY = rect.rect.top + verticalPadding - textPaint.ascent()
            canvas.drawText(rect.item.text, textX, textY, textPaint)
        }
    }

    private fun updatePaint(tag: TagItem) {
        val selected = selectionManager.isSelected(tag.id)
        bgPaint.color = if (selected) selectedBgColor else defaultBgColor
        textPaint.color = if (selected) selectedTextColor else defaultTextColor
    }

    private fun calculateTagPositions(width: Int) {
        var x = paddingLeft.toFloat()
        var y = paddingTop.toFloat()
        var lineHeight = 0f
        tagRects.clear()

        for (tag in tags) {
            val textWidth = textPaint.measureText(tag.text)
            val textHeight = textPaint.descent() - textPaint.ascent()
            val tagWidth = textWidth + horizontalPadding * 2
            val tagHeight = textHeight + verticalPadding * 2

            if (x + tagWidth + paddingRight > width) {
                x = paddingLeft.toFloat()
                y += lineHeight + lineSpacing
                lineHeight = 0f
            }

            tagRects.add(TagRect(tag, RectF(x, y, x + tagWidth, y + tagHeight)))
            x += tagWidth + tagSpacing
            lineHeight = max(lineHeight, tagHeight)
        }
    }

    override fun performClick(): Boolean = super.performClick()

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        if (event.action == MotionEvent.ACTION_UP) {
            tagRects.find { it.rect.contains(x, y) }?.let { clicked ->
                selectionManager.handleClick(clicked.item)
                onTagClick?.invoke(clicked.item.id, selectionManager.isSelected(clicked.item.id))
            }
            performClick()
            return true
        }
        return true
    }

    private data class TagRect(val item: TagItem, val rect: RectF)

    private class TagSelectionManager {
        var multiSelect: Boolean = true
        var maxSelected: Int? = null
        private val selectedIds = mutableSetOf<String>()
        var onSelectionChange: ((List<String>) -> Unit)? = null
        var onSelectionLimitReached: ((Int) -> Unit)? = null

        fun handleClick(tag: TagItem) {
            val currentlySelected = selectedIds.contains(tag.id)
            if (multiSelect) {
                if (!currentlySelected) {
                    if (maxSelected != null && selectedIds.size >= maxSelected!!) {
                        onSelectionLimitReached?.invoke(maxSelected!!)
                        return
                    }
                    selectedIds.add(tag.id)
                } else {
                    selectedIds.remove(tag.id)
                }
            } else {
                selectedIds.clear()
                selectedIds.add(tag.id)
            }
            onSelectionChange?.invoke(selectedIds.toList())
        }

        fun isSelected(id: String): Boolean = selectedIds.contains(id)
    }

    private val Int.dp: Int get() = (this * resources.displayMetrics.density).toInt()
}
