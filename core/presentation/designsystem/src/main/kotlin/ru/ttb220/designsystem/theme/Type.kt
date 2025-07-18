package ru.ttb220.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import ru.ttb220.presentation.model.R


val Roboto = FontFamily(
    // ExtraLight
    Font(
        resId = R.font.roboto_extra_light,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.roboto_extra_light_italic,
        weight = FontWeight.ExtraLight,
        style = FontStyle.Italic
    ),

    // Light
    Font(
        resId = R.font.roboto_light,
        weight = FontWeight.Light,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.roboto_light_italic,
        weight = FontWeight.Light,
        style = FontStyle.Italic
    ),

    // Normal
    Font(
        resId = R.font.roboto_regular,
        weight = FontWeight.Normal,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.roboto_italic,
        weight = FontWeight.Normal,
        style = FontStyle.Italic
    ),

    // Medium
    Font(
        resId = R.font.roboto_medium,
        weight = FontWeight.Medium,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.roboto_medium_italic,
        weight = FontWeight.Medium,
        style = FontStyle.Italic
    ),

    // SemiBold
    Font(
        resId = R.font.roboto_semi_bold,
        weight = FontWeight.SemiBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.roboto_semi_bold_italic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),

    // Bold
    Font(
        resId = R.font.roboto_bold,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.roboto_bold_italic,
        weight = FontWeight.Bold,
        style = FontStyle.Normal
    ),

    // ExtraBold
    Font(
        resId = R.font.roboto_extra_bold,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Normal
    ),
    Font(
        resId = R.font.roboto_extra_bold_italic,
        weight = FontWeight.ExtraBold,
        style = FontStyle.Normal
    ),

    // Black
    Font(
        resId = R.font.roboto_black,
        weight = FontWeight.Black,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.roboto_black_italic,
        weight = FontWeight.Black,
        style = FontStyle.Italic
    ),

    // Thin
    Font(
        resId = R.font.roboto_thin,
        weight = FontWeight.Thin,
        style = FontStyle.Normal,
    ),
    Font(
        resId = R.font.roboto_thin_italic,
        weight = FontWeight.Thin,
        style = FontStyle.Italic
    ),
)

fun robotoRegularTextStyle(
    fontSize: TextUnit,
    lineHeight: TextUnit,
    letterSpacing: TextUnit = 0.sp,
): TextStyle = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Normal,
    fontStyle = FontStyle.Normal,
    fontSize = fontSize,
    lineHeight = lineHeight,
    letterSpacing = letterSpacing,
)

val DisplayLarge = robotoRegularTextStyle(
    fontSize = 57.sp,
    lineHeight = 64.sp,
    letterSpacing = (-0.25).sp,
)

val DisplayMedium = robotoRegularTextStyle(
    fontSize = 45.sp,
    lineHeight = 52.sp,
    letterSpacing = 0.sp,
)

val DisplaySmall = robotoRegularTextStyle(
    fontSize = 36.sp,
    lineHeight = 44.sp,
    letterSpacing = 0.sp,
)

val HeadlineLarge = robotoRegularTextStyle(
    fontSize = 32.sp,
    lineHeight = 40.sp,
)

val HeadlineMedium = robotoRegularTextStyle(
    fontSize = 28.sp,
    lineHeight = 36.sp
)

val HeadlineSmall = robotoRegularTextStyle(
    fontSize = 24.sp,
    lineHeight = 32.sp
)

val BodyLarge = robotoRegularTextStyle(
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.5.sp
)

val BodyMedium = robotoRegularTextStyle(
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.25.sp
)

val BodySmall = robotoRegularTextStyle(
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.4.sp
)

val LabelLarge = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontStyle = FontStyle.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.1.sp,
)

val LabelMedium = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontStyle = FontStyle.Normal,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
)

val LabelSmall = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontStyle = FontStyle.Normal,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
)

val TitleLarge = robotoRegularTextStyle(
    fontSize = 22.sp,
    lineHeight = 28.sp,
)

val TitleMedium = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontStyle = FontStyle.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp,
)

val TitleSmall = TextStyle(
    fontFamily = Roboto,
    fontWeight = FontWeight.Medium,
    fontStyle = FontStyle.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.1.sp,
)

val Typography = Typography(
    displayLarge = DisplayLarge,
    displayMedium = DisplayMedium,
    displaySmall = DisplaySmall,
    headlineLarge = HeadlineLarge,
    headlineMedium = HeadlineMedium,
    headlineSmall = HeadlineSmall,
    titleLarge = TitleLarge,
    titleMedium = TitleMedium,
    titleSmall = TitleSmall,
    bodyLarge = BodyLarge,
    bodyMedium = BodyMedium,
    bodySmall = BodySmall,
    labelLarge = LabelLarge,
    labelMedium = LabelMedium,
    labelSmall = LabelSmall
)