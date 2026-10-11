package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AssessmentProgressTracker
import com.example.model.PillarProgressTrack
import com.example.model.PillarType
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCrimson
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.CyberDarkVoid
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary

@Composable
fun CisoPillarProgressTracker(
  tracker: AssessmentProgressTracker,
  selectedPillar: PillarType? = null,
  onPillarSelected: ((PillarType?) -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  val overallProgress by animateFloatAsState(
    targetValue = tracker.overallCompletionPercentage / 100f,
    animationSpec = tween(700, easing = FastOutSlowInEasing),
    label = "overall_progress"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(CyberDarkBg)
      .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
      .padding(14.dp)
      .testTag("ciso_pillar_progress_tracker")
  ) {
    Column {
      // -------------------------------------------------------------
      // Header: Overall Progress Telemetry
      // -------------------------------------------------------------
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(26.dp)
              .clip(CircleShape)
              .background(CyberCyan.copy(alpha = 0.2f))
              .border(1.dp, CyberCyan, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Speed,
              contentDescription = null,
              tint = CyberCyan,
              modifier = Modifier.size(15.dp)
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "CISO 4-PILLAR PROGRESS TRACKER",
              color = CyberCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              letterSpacing = 0.8.sp
            )
            Text(
              text = "ความคืบหน้าการประเมิน 4 เสาหลัก",
              color = CyberTextSecondary,
              fontSize = 11.sp
            )
          }
        }

        // Completion Badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (tracker.isFullyCompleted) CyberEmerald.copy(alpha = 0.2f) else CyberCardBg)
            .border(
              1.dp,
              if (tracker.isFullyCompleted) CyberEmerald else CyberBorder,
              RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
          Text(
            text = "${tracker.totalAnsweredCount}/${tracker.totalQuestionsCount} COMPLETED (${tracker.overallCompletionPercentage}%)",
            color = if (tracker.isFullyCompleted) CyberEmerald else CyberTextSecondary,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Overall Progress Bar
      LinearProgressIndicator(
        progress = { overallProgress },
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp)),
        color = CyberCyan,
        trackColor = CyberDarkVoid,
        strokeCap = StrokeCap.Round
      )

      // Lead and Lag telemetry pill row
      if (tracker.leadPillar != null || tracker.laggingPillar != null) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          tracker.leadPillar?.let { lead ->
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "LEADING: ",
                color = CyberTextMuted,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
              )
              Text(
                text = lead.displayNameEn,
                color = lead.accentColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
              )
            }
          }
          tracker.laggingPillar?.let { lag ->
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "LAGGING: ",
                color = CyberTextMuted,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace
              )
              Text(
                text = lag.displayNameEn,
                color = CyberCrimson,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // -------------------------------------------------------------
      // 4 Pillar Progress Cards
      // -------------------------------------------------------------
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        tracker.pillarTracks.forEach { track ->
          PillarProgressItemRow(
            track = track,
            isSelected = selectedPillar == track.pillar,
            onClick = { onPillarSelected?.invoke(track.pillar) }
          )
        }
      }
    }
  }
}

@Composable
private fun PillarProgressItemRow(
  track: PillarProgressTrack,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val pillar = track.pillar
  val animatedMaturity by animateFloatAsState(
    targetValue = track.maturityScorePercentage / 100f,
    animationSpec = tween(600),
    label = "pillar_maturity"
  )

  val borderAnimColor by animateColorAsState(
    targetValue = if (isSelected) pillar.accentColor else CyberBorder,
    animationSpec = tween(300),
    label = "border_color"
  )

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .background(if (isSelected) pillar.accentColor.copy(alpha = 0.08f) else CyberCardBg)
      .border(1.dp, borderAnimColor, RoundedCornerShape(10.dp))
      .clickable(onClick = onClick)
      .padding(10.dp)
      .testTag("pillar_track_${pillar.codePrefix.lowercase()}")
  ) {
    Column {
      // Top line: Code badge + Name + Score Percentage
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          // Pill prefix
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(pillar.accentColor.copy(alpha = 0.2f))
              .border(1.dp, pillar.accentColor.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = pillar.codePrefix,
              color = pillar.accentColor,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Column {
            Text(
              text = pillar.displayNameEn,
              color = CyberTextPrimary,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = pillar.displayNameTh,
              color = CyberTextMuted,
              fontSize = 9.sp
            )
          }
        }

        // Score % and Status Badge
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(track.currentGrade.color.copy(alpha = 0.15f))
              .border(1.dp, track.currentGrade.color.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
              .padding(horizontal = 5.dp, vertical = 2.dp)
          ) {
            Text(
              text = track.currentGrade.gradeBadge,
              color = track.currentGrade.color,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Text(
            text = "${track.maturityScorePercentage}%",
            color = pillar.accentColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // 5-Segment Level Progress Gauge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        for (step in 1..5) {
          val isStepAchieved = track.averageLevel >= step
          val isStepCurrent = track.averageLevel >= (step - 0.5) && !isStepAchieved

          val segmentColor = when {
            isStepAchieved -> pillar.accentColor
            isStepCurrent -> pillar.accentColor.copy(alpha = 0.45f)
            else -> CyberDarkVoid
          }

          Box(
            modifier = Modifier
              .weight(1f)
              .height(6.dp)
              .clip(RoundedCornerShape(3.dp))
              .background(segmentColor)
              .border(0.5.dp, if (isStepAchieved) pillar.accentColor else CyberBorder, RoundedCornerShape(3.dp))
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Bottom Row: Milestone description + evaluation status
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = track.milestoneDescriptionTh,
          color = CyberTextSecondary,
          fontSize = 10.sp,
          fontFamily = FontFamily.Monospace
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
          if (track.isCompleted) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = "Completed",
              tint = CyberEmerald,
              modifier = Modifier.size(11.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
          }
          Text(
            text = "${track.answeredQuestionsCount}/${track.totalQuestionsCount} มิติ",
            color = if (track.isCompleted) CyberEmerald else CyberTextMuted,
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Monospace
          )
        }
      }
    }
  }
}
