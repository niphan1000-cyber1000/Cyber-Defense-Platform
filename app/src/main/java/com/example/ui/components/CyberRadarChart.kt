package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PillarScore
import com.example.model.PillarType
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTextMuted
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CyberRadarChart(
  pillarScores: Map<PillarType, PillarScore>,
  modifier: Modifier = Modifier
) {
  // 4 axes: Visibility (top: -90°), Detection (right: 0°), Automation (bottom: 90°), AI Agents (left: 180°)
  val visPercent = (pillarScores[PillarType.VISIBILITY]?.percentage ?: 50) / 100f
  val detPercent = (pillarScores[PillarType.DETECTION_TO_DECISION]?.percentage ?: 50) / 100f
  val autPercent = (pillarScores[PillarType.CONTROLLED_AUTOMATION]?.percentage ?: 50) / 100f
  val agtPercent = (pillarScores[PillarType.SECURE_AI_AGENTS]?.percentage ?: 50) / 100f

  val animVis by animateFloatAsState(targetValue = visPercent, animationSpec = tween(700, easing = FastOutSlowInEasing), label = "vis")
  val animDet by animateFloatAsState(targetValue = detPercent, animationSpec = tween(700, easing = FastOutSlowInEasing), label = "det")
  val animAut by animateFloatAsState(targetValue = autPercent, animationSpec = tween(700, easing = FastOutSlowInEasing), label = "aut")
  val animAgt by animateFloatAsState(targetValue = agtPercent, animationSpec = tween(700, easing = FastOutSlowInEasing), label = "agt")

  Box(
    modifier = modifier.size(240.dp),
    contentAlignment = Alignment.Center
  ) {
    Canvas(modifier = Modifier.fillMaxSize().padding(28.dp)) {
      val center = Offset(size.width / 2f, size.height / 2f)
      val radius = (size.width.coerceAtMost(size.height) / 2f)

      // Draw concentric rings (20%, 40%, 60%, 80%, 100%)
      val rings = listOf(0.25f, 0.50f, 0.75f, 1.0f)
      rings.forEach { ratio ->
        drawRadarRing(center, radius * ratio, CyberBorder)
      }

      // Draw cross axis lines
      drawLine(
        color = CyberBorder,
        start = Offset(center.x, center.y - radius),
        end = Offset(center.x, center.y + radius),
        strokeWidth = 1.5f
      )
      drawLine(
        color = CyberBorder,
        start = Offset(center.x - radius, center.y),
        end = Offset(center.x + radius, center.y),
        strokeWidth = 1.5f
      )

      // Build data polygon path
      // Top: (0, -1) -> Visibility
      // Right: (1, 0) -> Detection
      // Bottom: (0, 1) -> Automation
      // Left: (-1, 0) -> AI Agents
      val pTop = Offset(center.x, center.y - radius * animVis)
      val pRight = Offset(center.x + radius * animDet, center.y)
      val pBottom = Offset(center.x, center.y + radius * animAut)
      val pLeft = Offset(center.x - radius * animAgt, center.y)

      val polygonPath = Path().apply {
        moveTo(pTop.x, pTop.y)
        lineTo(pRight.x, pRight.y)
        lineTo(pBottom.x, pBottom.y)
        lineTo(pLeft.x, pLeft.y)
        close()
      }

      // Draw glowing gradient fill
      val fillBrush = Brush.radialGradient(
        colors = listOf(CyberEmerald.copy(alpha = 0.45f), CyberCyan.copy(alpha = 0.20f), Color.Transparent),
        center = center,
        radius = radius
      )
      drawPath(polygonPath, brush = fillBrush)

      // Draw polygon stroke
      drawPath(
        polygonPath,
        color = CyberCyan,
        style = Stroke(width = 3.5f, cap = StrokeCap.Round)
      )

      // Draw axis vertices
      listOf(pTop to CyberCyan, pRight to CyberEmerald, pBottom to CyberCyan, pLeft to CyberEmerald).forEach { (pt, color) ->
        drawCircle(color = color, radius = 5.5f, center = pt)
        drawCircle(color = Color.White, radius = 2.5f, center = pt)
      }
    }

    // Outer pillar labels
    Box(modifier = Modifier.fillMaxSize()) {
      Text(
        text = "VISIBILITY",
        color = CyberCyan,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier.align(Alignment.TopCenter)
      )
      Text(
        text = "DECISION",
        color = CyberEmerald,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier.align(Alignment.CenterEnd)
      )
      Text(
        text = "AUTOMATION",
        color = CyberCyan,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier.align(Alignment.BottomCenter)
      )
      Text(
        text = "AI AGENTS",
        color = CyberEmerald,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        modifier = Modifier.align(Alignment.CenterStart)
      )
    }
  }
}

private fun DrawScope.drawRadarRing(center: Offset, r: Float, color: Color) {
  val path = Path().apply {
    moveTo(center.x, center.y - r)
    lineTo(center.x + r, center.y)
    lineTo(center.x, center.y + r)
    lineTo(center.x - r, center.y)
    close()
  }
  drawPath(path, color = color, style = Stroke(width = 1f))
}
