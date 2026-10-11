package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CyberAssessmentRepository
import com.example.model.ScenarioDefenseResult
import com.example.model.ThreatScenario
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCrimson
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.CyberDarkVoid
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTerminalGreen
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary

@Composable
fun WarRoomScreen(
  selectedScenario: ThreatScenario?,
  scenarioResult: ScenarioDefenseResult?,
  isSimulating: Boolean,
  onSelectScenario: (ThreatScenario) -> Unit,
  onRunSimulation: (ThreatScenario) -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()
  val scenarios = CyberAssessmentRepository.threatScenarios
  val activeScenario = selectedScenario ?: scenarios.first()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CyberDarkVoid)
      .verticalScroll(scrollState)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // -------------------------------------------------------------
    // War Room Header
    // -------------------------------------------------------------
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(CyberCardBg)
        .border(1.dp, CyberBorder, RoundedCornerShape(14.dp))
        .padding(16.dp)
    ) {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = null,
              tint = CyberCrimson,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "CYBER WAR ROOM SIMULATOR",
              color = CyberCyan,
              fontSize = 13.sp,
              fontWeight = FontWeight.Black,
              fontFamily = FontFamily.Monospace,
              letterSpacing = 1.sp
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(4.dp))
              .background(CyberCrimson.copy(alpha = 0.2f))
              .border(1.dp, CyberCrimson, RoundedCornerShape(4.dp))
              .padding(horizontal = 6.dp, vertical = 2.dp)
          ) {
            Text(
              text = "LIVE BATTLE LAB",
              color = CyberCrimson,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "จำลองการบุกรุกจริงเพื่อทดสอบว่าความพร้อมปัจจุบันของคุณสามารถสกัดกั้นและเอาชีวิตรอดได้หรือไม่",
          color = CyberTextSecondary,
          fontSize = 11.sp,
          lineHeight = 15.sp
        )
      }
    }

    // -------------------------------------------------------------
    // Scenario Selectors
    // -------------------------------------------------------------
    Text(
      text = "เลือกรูปแบบภัยคุกคาม (THREAT SCENARIO):",
      color = CyberTextPrimary,
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
      scenarios.forEach { sc ->
        val isSelected = sc.id == activeScenario.id
        val borderColor = if (isSelected) CyberCyan else CyberBorder
        val bgColor = if (isSelected) CyberCyan.copy(alpha = 0.1f) else CyberDarkBg

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable { onSelectScenario(sc) }
            .padding(12.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "${sc.code}: ${sc.title}",
                color = if (isSelected) CyberTextPrimary else CyberTextSecondary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = "Actor: ${sc.threatActor}",
                color = CyberTextMuted,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
              )
            }
            if (isSelected) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(4.dp))
                  .background(CyberCyan)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "ACTIVE",
                  color = CyberDarkVoid,
                  fontSize = 9.sp,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace
                )
              }
            }
          }
        }
      }
    }

    // -------------------------------------------------------------
    // Active Scenario Dossier
    // -------------------------------------------------------------
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(CyberCardBg)
        .border(1.dp, CyberBorder, RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Column {
        Text(
          text = "THREAT DOSSIER & KILL-CHAIN",
          color = CyberAmber,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = activeScenario.killChainOverview,
          color = CyberTextPrimary,
          fontSize = 12.sp,
          lineHeight = 17.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "ทดสอบเสาหลัก: ",
            color = CyberTextMuted,
            fontSize = 11.sp
          )
          activeScenario.primaryPillars.forEach { p ->
            Box(
              modifier = Modifier
                .padding(horizontal = 3.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(p.accentColor.copy(alpha = 0.2f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text(
                text = p.displayNameEn,
                color = p.accentColor,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Button(
          onClick = { onRunSimulation(activeScenario) },
          enabled = !isSimulating,
          colors = ButtonDefaults.buttonColors(
            containerColor = CyberCrimson,
            contentColor = CyberDarkVoid,
            disabledContainerColor = CyberBorder
          ),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("run_war_room_simulation_button"),
          shape = RoundedCornerShape(10.dp)
        ) {
          if (isSimulating) {
            CircularProgressIndicator(color = CyberDarkVoid, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            Spacer(modifier = Modifier.width(8.dp))
            Text("จำลองการโจมตี (EXECUTE BATTLE)...", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          } else {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("สั่งจำลองสถานการณ์โจมตี (RUN WAR SIMULATION)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // -------------------------------------------------------------
    // Live Telemetry Stream & Results
    // -------------------------------------------------------------
    AnimatedVisibility(
      visible = scenarioResult != null,
      enter = fadeIn() + slideInVertically()
    ) {
      scenarioResult?.let { res ->
        val resultColor = if (res.survivalRate >= 70) CyberEmerald else if (res.survivalRate >= 45) CyberAmber else CyberCrimson

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
          // Scorecard & Survivability
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(14.dp))
              .background(CyberDarkBg)
              .border(1.dp, resultColor, RoundedCornerShape(14.dp))
              .padding(16.dp)
          ) {
            Column {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = "SURVIVABILITY & CONTAINMENT",
                    color = resultColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                  )
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(
                    text = "อัตราการรอดพ้นความเสียหาย",
                    color = CyberTextSecondary,
                    fontSize = 12.sp
                  )
                }

                Text(
                  text = "${res.survivalRate}%",
                  color = resultColor,
                  fontSize = 28.sp,
                  fontWeight = FontWeight.Black,
                  fontFamily = FontFamily.Monospace
                )
              }

              Spacer(modifier = Modifier.height(10.dp))
              LinearProgressIndicator(
                progress = { res.survivalRate / 100f },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(8.dp)
                  .clip(RoundedCornerShape(4.dp)),
                color = resultColor,
                trackColor = CyberDarkVoid,
                strokeCap = StrokeCap.Round
              )

              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = "Blast Radius: ${res.blastRadiusSeverity}",
                color = CyberTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = "Estimated MTTR: ~${res.estimatedMTTRHours} Hours",
                color = CyberTextMuted,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace
              )
            }
          }

          // Terminal Execution Logs
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(CyberDarkVoid)
              .border(1.dp, CyberBorder, RoundedCornerShape(12.dp))
              .padding(12.dp)
          ) {
            Column {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Terminal,
                  contentDescription = null,
                  tint = CyberTerminalGreen,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "DEFENSE TELEMETRY LOGS (REAL-TIME)",
                  color = CyberTerminalGreen,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.Monospace
                )
              }
              Spacer(modifier = Modifier.height(8.dp))
              res.executionLogs.forEach { logLine ->
                Text(
                  text = logLine,
                  color = if (logLine.contains("WARNING")) CyberCrimson else CyberTerminalGreen,
                  fontSize = 11.sp,
                  fontFamily = FontFamily.Monospace,
                  lineHeight = 16.sp
                )
              }
            }
          }

          // Strategic Fixes
          if (res.strategicFixes.isNotEmpty()) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(CyberCardBg)
                .border(1.dp, CyberBorder, RoundedCornerShape(12.dp))
                .padding(14.dp)
            ) {
              Column {
                Text(
                  text = "มาตรการแก้ไขเร่งด่วนเพื่อเพิ่มอัตราการรอดพ้น:",
                  color = CyberCyan,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                res.strategicFixes.forEach { fix ->
                  Text(
                    text = "• $fix",
                    color = CyberTextPrimary,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                  )
                }
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}
