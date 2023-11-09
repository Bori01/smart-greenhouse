package bme.hit.greenhouse.feature.charts

import androidx.compose.runtime.Composable


@Composable
fun GaugeChart(

    percentValue: Int,
    modifier: Modifier = Modifier,
    gaugeChartConfig: GaugeChartConfig = GaugeChartDefaults.gaugeConfigDefaults(),
    needleConfig: NeedleConfig = GaugeChartDefaults.needleConfigDefaults(),
    animated: Boolean = true,
    animationSpec: AnimationSpec<Float> = tween(),
) {
    // Implementation details...
}