package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PillarScore
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberDarkVoid
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary

@Composable
fun PillarMetricCard(
  pillarScore: PillarScore,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val pillar = pillarScore.pillar
  val progress by animateFloatAsState(
    targetValue = pillarScore.percentage / 100f,
    animationSpec = tween(600),
    label = "progress"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(CyberCardBg)
      .border(1.dp, CyberBorder, RoundedCornerShape(12.dp))
      .clickable(onClick = onClick)
      .padding(14.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(pillar.accentColor.copy(alpha = 0.2f))
              .border(1.dp, pillar.accentColor, RoundedCornerShape(4.dp))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = pillar.codePrefix,
              color = pillar.accentColor,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = pillar.displayNameTh,
            color = CyberTextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
          )
        }

        Text(
          text = "${pillarScore.percentage}%",
          color = pillar.accentColor,
          fontSize = 18.sp,
          fontWeight = FontWeight.Black,
          fontFamily = FontFamily.Monospace
        )
      }

      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = pillar.displayNameEn,
        color = CyberTextMuted,
        fontSize = 11.sp,
        fontFamily = FontFamily.Monospace
      )

      Spacer(modifier = Modifier.height(8.dp))
      LinearProgressIndicator(
        progress = { progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = pillar.accentColor,
        trackColor = CyberDarkVoid,
        strokeCap = StrokeCap.Round
      )

      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "สถานะ: ${pillarScore.levelDescription} (${pillarScore.earnedPoints}/${pillarScore.maxPoints} pts)",
          color = CyberTextSecondary,
          fontSize = 11.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "ประเมิน",
            color = pillar.accentColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
          Spacer(modifier = Modifier.width(4.dp))
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Go to questions",
            tint = pillar.accentColor,
            modifier = Modifier.height(14.dp)
          )
        }
      }
    }
  }
}
