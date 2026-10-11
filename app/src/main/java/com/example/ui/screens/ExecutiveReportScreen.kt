package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PillarType
import com.example.model.ReadinessSummary
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.CyberDarkVoid
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary

@Composable
fun ExecutiveReportScreen(
  summary: ReadinessSummary,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()

  val reportMarkdown = buildString {
    appendLine("# CYBER DEFENSE & DOMINANCE READINESS REPORT")
    appendLine("Overall Score: ${summary.overallScore}% - ${summary.tier.tierNameEn}")
    appendLine("Tier Status: ${summary.tier.tierNameTh}")
    appendLine()
    appendLine("## 4-PILLAR SCORES:")
    summary.pillarScores.values.forEach { p ->
      appendLine("- ${p.pillar.displayNameEn}: ${p.percentage}% (${p.earnedPoints}/${p.maxPoints} pts) [${p.levelDescription}]")
    }
    appendLine()
    appendLine("## EXECUTIVE SUMMARY:")
    appendLine(summary.tier.summaryTh)
    appendLine()
    appendLine("## KEY VULNERABILITIES:")
    summary.criticalVulnerabilities.forEach { v ->
      appendLine("- $v")
    }
    appendLine()
    appendLine("## STRATEGIC ACTION ROADMAP:")
    appendLine("- Q1: Identity & Attack Surface Hygiene (ASM & CIEM Zero-Trust)")
    appendLine("- Q2: Unified XDR Telemetry & Mean Time to Detect (MTTD) Reduction")
    appendLine("- Q3: SOAR Controlled Playbooks & Human-in-the-Loop Killswitches")
    appendLine("- Q4: Autonomous AI Agent Security Governance (Least-Agency & Guardrails)")
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CyberDarkVoid)
      .verticalScroll(scrollState)
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // -------------------------------------------------------------
    // Header
    // -------------------------------------------------------------
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(CyberCardBg)
        .border(1.dp, CyberBorder, RoundedCornerShape(14.dp))
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Description,
              contentDescription = null,
              tint = CyberCyan,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "EXECUTIVE CISO DOSSIER",
              color = CyberCyan,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              letterSpacing = 1.sp
            )
          }
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "รายงานวิเคราะห์ความพร้อมระดับบริหาร (Boardroom Ready)",
            color = CyberTextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(summary.tier.color.copy(alpha = 0.2f))
            .border(1.dp, summary.tier.color, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "${summary.overallScore}%",
            color = summary.tier.color,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            fontFamily = FontFamily.Monospace
          )
        }
      }
    }

    // -------------------------------------------------------------
    // Pillar Score Table
    // -------------------------------------------------------------
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(CyberDarkBg)
        .border(1.dp, CyberBorder, RoundedCornerShape(14.dp))
        .padding(14.dp)
    ) {
      Column {
        Text(
          text = "สรุปผลการประเมิน 4 เสาหลัก (PILLAR BENCHMARK)",
          color = CyberCyan,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.height(10.dp))

        summary.pillarScores.values.forEach { p ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = p.pillar.displayNameTh,
                color = CyberTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
              )
              Text(
                text = "${p.pillar.codePrefix} • ${p.levelDescription}",
                color = CyberTextMuted,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace
              )
            }
            Text(
              text = "${p.percentage}%",
              color = p.pillar.accentColor,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }
        }
      }
    }

    // -------------------------------------------------------------
    // Strategic Roadmap (Q1 - Q4)
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
          text = "แผนปฏิบัติการยกระดับความพร้อม (ROADMAP TO DOMINANCE)",
          color = CyberEmerald,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.Monospace
        )
        Spacer(modifier = Modifier.height(10.dp))

        val roadmapItems = listOf(
          "Q1: Zero-Trust Identity & Exposure Control" to "ติดตั้ง ASM ค้นหา Shadow IT และบังคับใช้ Phishing-Resistant MFA สำหรับทุกบัญชี",
          "Q2: Telemetry Correlation & MTTD Acceleration" to "เชื่อมโยง SIEM/EDR สู่ Unified Data Lake และลดเวลาตรวจจับ Triage ให้ต่ำกว่า 60 นาที",
          "Q3: Automated Containment with Safe Kill-Switches" to "สร้าง Playbooks กักกัน Host อัตโนมัติ พร้อมสวิตช์ความปลอดภัย Human-in-the-Loop",
          "Q4: Autonomous Secure AI Governance" to "สร้าง LLM Guardrails, Sandbox สิทธิ์ AI Agents และจัดทำ AI Bill of Materials (AIBOM)"
        )

        roadmapItems.forEach { (quarter, desc) ->
          Column(modifier = Modifier.padding(vertical = 6.dp)) {
            Text(
              text = quarter,
              color = CyberCyan,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = desc,
              color = CyberTextSecondary,
              fontSize = 11.sp,
              lineHeight = 15.sp
            )
          }
        }
      }
    }

    // -------------------------------------------------------------
    // Export & Share Actions
    // -------------------------------------------------------------
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Button(
        onClick = {
          val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
          val clip = ClipData.newPlainText("Cyber Readiness Report", reportMarkdown)
          clipboard.setPrimaryClip(clip)
          Toast.makeText(context, "คัดลอกรายงานเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show()
        },
        colors = ButtonDefaults.buttonColors(containerColor = CyberCyan, contentColor = CyberDarkVoid),
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("copy_report_button"),
        shape = RoundedCornerShape(10.dp)
      ) {
        Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text("คัดลอกรายงาน", fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }

      OutlinedButton(
        onClick = {
          val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "CISO Cyber Defense & Dominance Report")
            putExtra(Intent.EXTRA_TEXT, reportMarkdown)
          }
          context.startActivity(Intent.createChooser(shareIntent, "Share Cyber Report"))
        },
        colors = ButtonDefaults.outlinedButtonColors(contentColor = CyberEmerald),
        border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(CyberEmerald)),
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("share_report_button"),
        shape = RoundedCornerShape(10.dp)
      ) {
        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text("แชร์รายงาน", fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
  }
}
