package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AssessmentProgressTracker
import com.example.model.CyberTab
import com.example.model.PillarType
import com.example.model.ReadinessSummary
import com.example.ui.components.CisoPillarProgressTracker
import com.example.ui.components.CyberRadarChart
import com.example.ui.components.PillarMetricCard
import com.example.ui.theme.CyberAmber
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
fun OverviewScreen(
  summary: ReadinessSummary,
  tracker: AssessmentProgressTracker,
  onNavigateToTab: (CyberTab) -> Unit,
  onFilterPillar: (PillarType) -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()
  val overallProgress by animateFloatAsState(
    targetValue = summary.overallScore / 100f,
    animationSpec = tween(800),
    label = "overall"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CyberDarkVoid)
      .verticalScroll(scrollState)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // -----------------------------------------------------------------
    // Executive Dominance Scorecard
    // -----------------------------------------------------------------
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(CyberCardBg)
        .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
        .padding(18.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "OVERALL DOMINANCE INDEX",
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = summary.tier.tierNameTh,
            color = CyberTextPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = summary.tier.summaryTh,
            color = CyberTextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
          )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Big Circular Gauge
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(86.dp)) {
          CircularProgressIndicator(
            progress = { 1f },
            modifier = Modifier.size(86.dp),
            color = CyberDarkVoid,
            strokeWidth = 8.dp,
            strokeCap = StrokeCap.Round
          )
          CircularProgressIndicator(
            progress = { overallProgress },
            modifier = Modifier.size(86.dp),
            color = summary.tier.color,
            strokeWidth = 8.dp,
            strokeCap = StrokeCap.Round
          )
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "${summary.overallScore}%",
              color = summary.tier.color,
              fontSize = 20.sp,
              fontWeight = FontWeight.Black,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "SCORE",
              color = CyberTextMuted,
              fontSize = 8.sp,
              fontFamily = FontFamily.Monospace
            )
          }
        }
      }
    }

    // -----------------------------------------------------------------
    // 4-Pillar Visual Progress Indicator Cockpit
    // -----------------------------------------------------------------
    CisoPillarProgressTracker(
      tracker = tracker,
      onPillarSelected = { pillar ->
        if (pillar != null) {
          onFilterPillar(pillar)
          onNavigateToTab(CyberTab.ASSESSMENT)
        }
      }
    )

    // -----------------------------------------------------------------
    // Radar Spider Chart Section
    // -----------------------------------------------------------------
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(CyberDarkBg)
        .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
        .padding(16.dp)
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "4-PILLAR RADAR MATRIX",
            color = CyberCyan,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 1.sp
          )
          Text(
            text = "CISO MODEL",
            color = CyberEmerald,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
        CyberRadarChart(pillarScores = summary.pillarScores)
      }
    }

    // -----------------------------------------------------------------
    // 4 Pillar Breakdown Cards
    // -----------------------------------------------------------------
    Text(
      text = "ยุทธศาสตร์ 4 เสาหลัก (CISO CYBER DEFENSE)",
      color = CyberTextPrimary,
      fontSize = 14.sp,
      fontWeight = FontWeight.Bold
    )

    summary.pillarScores.values.forEach { pillarScore ->
      PillarMetricCard(
        pillarScore = pillarScore,
        onClick = {
          onFilterPillar(pillarScore.pillar)
          onNavigateToTab(CyberTab.ASSESSMENT)
        }
      )
    }

    // -----------------------------------------------------------------
    // Vulnerabilities & Strengths Alert Box
    // -----------------------------------------------------------------
    if (summary.criticalVulnerabilities.isNotEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(CyberCrimson.copy(alpha = 0.08f))
          .border(1.dp, CyberCrimson.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
          .padding(14.dp)
      ) {
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Warning,
              contentDescription = "Alert",
              tint = CyberCrimson,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "จุดเสี่ยงที่ต้องระวัง (CRITICAL GAPS)",
              color = CyberCrimson,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold
            )
          }
          Spacer(modifier = Modifier.height(8.dp))
          summary.criticalVulnerabilities.forEach { vuln ->
            Text(
              text = "• $vuln",
              color = CyberTextPrimary,
              fontSize = 12.sp,
              modifier = Modifier.padding(vertical = 2.dp)
            )
          }
        }
      }
    }

    // -----------------------------------------------------------------
    // Quick Action Bar
    // -----------------------------------------------------------------
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Button(
        onClick = { onNavigateToTab(CyberTab.ASSESSMENT) },
        colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = CyberDarkVoid),
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("start_assessment_button"),
        shape = RoundedCornerShape(10.dp)
      ) {
        Icon(Icons.Default.Assessment, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text("ทำแบบประเมิน", fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }

      Button(
        onClick = { onNavigateToTab(CyberTab.WAR_ROOM) },
        colors = ButtonDefaults.buttonColors(containerColor = CyberEmerald, contentColor = CyberDarkVoid),
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("open_war_room_button"),
        shape = RoundedCornerShape(10.dp)
      ) {
        Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text("War Room", fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }
    }

    OutlinedButton(
      onClick = { onNavigateToTab(CyberTab.AI_COPILOT) },
      modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .testTag("ciso_ai_advisor_button"),
      colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberCyan),
      border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(CyberCyan)),
      shape = RoundedCornerShape(10.dp)
    ) {
      Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(18.dp), tint = CyberCyan)
      Spacer(modifier = Modifier.width(8.dp))
      Text("ปรึกษา CISO AI Strategic Advisor (Real-time)", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
    }

    Spacer(modifier = Modifier.height(16.dp))
  }
}
