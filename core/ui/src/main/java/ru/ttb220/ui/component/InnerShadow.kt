package ru.ttb220.ui.component

import android.graphics.BlurMaskFilter
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


fun Modifier.innerShadow(
    shape: Shape,
    color: Color = Color.Black,
    blur: Dp = 3.dp,
    offsetX: Dp = 3.dp,
    offsetY: Dp = 3.dp,
    spread: Dp = 0.dp
) = this.drawWithContent {
    drawContent()
    drawIntoCanvas { canvas ->
        val shadowSize = Size(size.width + spread.toPx(), size.height + spread.toPx())
        val shadowOutline = shape.createOutline(shadowSize, layoutDirection, this)
        val paint = Paint()
        paint.color = color

        canvas.saveLayer(size.toRect(), paint)
        canvas.drawOutline(shadowOutline, paint)

        paint.asFrameworkPaint().apply {
            this.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
            if (blur.toPx() > 0) {
                maskFilter = BlurMaskFilter(blur.toPx(), BlurMaskFilter.Blur.NORMAL)
            }
        }

        paint.color = Color.Black

        canvas.translate(offsetX.toPx(), offsetY.toPx())
        canvas.drawOutline(shadowOutline, paint)
        canvas.restore()
    }
}

@Preview
@Composable
private fun InnerShadowPreview() {
    Box(
        Modifier
            .size(160.dp)
            .background(Color(0xFFD7DEE8), CircleShape)
    ) {
        Box(
            Modifier
                .offset(20.dp)
                .size(120.dp)
                .background(Color(0xFFEFC529), CircleShape)
                .innerShadow(CircleShape)
        )
    }
}