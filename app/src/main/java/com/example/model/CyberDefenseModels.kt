package com.example.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberPurple
import com.example.ui.theme.CyberCrimson

enum class PillarType(
  val displayNameEn: String,
  val displayNameTh: String,
  val codePrefix: String,
  val accentColor: Color,
  val descriptionTh: String
) {
  VISIBILITY(
    displayNameEn = "Continuous Visibility",
    displayNameTh = "การมองเห็นแบบต่อเนื่องรอบทิศทาง",
    codePrefix = "VIS",
    accentColor = CyberCyan,
    descriptionTh = "ครอบคลุม Attack Surface, ขอบเขต Identity, คลาวด์ และ Shadow IT ตรวจจับสินทรัพย์แบบ Real-time"
  ),
  DETECTION_TO_DECISION(
    displayNameEn = "Detection to Decision",
    displayNameTh = "การตรวจจับสู่การตัดสินใจอย่างแม่นยำ",
    codePrefix = "DET",
    accentColor = CyberEmerald,
    descriptionTh = "ลดเวลา MTTD รวบรวมและวิเคราะห์ Telemetry แบบหลายมิติ แปลงสัญญาณภัยคุกคามสู่ Action ทันที"
  ),
  CONTROLLED_AUTOMATION(
    displayNameEn = "Controlled Automation",
    displayNameTh = "ระบบอัตโนมัติภายใต้การควบคุม",
    codePrefix = "AUT",
    accentColor = CyberAmber,
    descriptionTh = "SOAR Playbooks, การกักกันภัยอัตโนมัติที่มี Human-in-the-Loop และ Kill-Switch เพื่อความปลอดภัย"
  ),
  SECURE_AI_AGENTS(
    displayNameEn = "Secure AI Agents",
    displayNameTh = "ความมั่นคงปลอดภัย AI & Agents",
    codePrefix = "AGT",
    accentColor = CyberPurple,
    descriptionTh = "กำกับดูแลโมเดล AI, ป้องกัน Prompt Injection, Sandbox สิทธิ์ Agent และติดตามพฤติกรรมอัตโนมัติ"
  )
}

enum class DominanceTier(
  val tierNameTh: String,
  val tierNameEn: String,
  val minScore: Int,
  val color: Color,
  val badgeText: String,
  val summaryTh: String
) {
  SUPREMACY(
    tierNameTh = "Autonomous Cyber Supremacy",
    tierNameEn = "Cyber Supremacy",
    minScore = 85,
    color = CyberEmerald,
    badgeText = "TIER 5 // SUPREMACY",
    summaryTh = "องค์กรมีความพร้อมระดับสูงสุด สามารถตอบโต้ภัยคุกคามด้วย AI และระบบอัตโนมัติที่มีเสถียรภาพและมองเห็นภัยรอบด้าน"
  ),
  DOMINANT(
    tierNameTh = "Strategic Dominance",
    tierNameEn = "Strategic Dominant",
    minScore = 70,
    color = CyberCyan,
    badgeText = "TIER 4 // DOMINANT",
    summaryTh = "มีขีดความสามารถการป้องกันเชิงรุกที่แข็งแกร่ง มีระบบอัตโนมัติรองรับ และเริ่มมีมาตรการความปลอดภัย AI ชัดเจน"
  ),
  TACTICAL(
    tierNameTh = "Tactical Defender",
    tierNameEn = "Tactical Defender",
    minScore = 55,
    color = CyberAmber,
    badgeText = "TIER 3 // TACTICAL",
    summaryTh = "มีเครื่องมือตรวจจับและตอบสนองตามมาตรฐาน แต่ยังพึ่งพาการทำงานแบบ Manual และขาดการบูรณาการระบบ AI"
  ),
  EMERGING(
    tierNameTh = "Emerging Guard",
    tierNameEn = "Emerging Guard",
    minScore = 35,
    color = CyberAmber,
    badgeText = "TIER 2 // EMERGING",
    summaryTh = "เริ่มมีการจัดวางกรอบการทำงานเบื้องต้น แต่ยังมีจุดบอดขนาดใหญ่ด้าน Visibility และไม่มีระบบกักกันอัตโนมัติ"
  ),
  FRAGILE(
    tierNameTh = "Cyber Fragile",
    tierNameEn = "Cyber Fragile",
    minScore = 0,
    color = CyberCrimson,
    badgeText = "TIER 1 // CRITICAL",
    summaryTh = "มีความเสี่ยงสูงมากต่อการถูกเจาะระบบ ขาดการมองเห็นและต้องใช้เวลาตรวจจับนาน เสี่ยงต่อความเสียหายระดับองค์กร"
  )
}

data class MaturityOption(
  val level: Int,
  val titleTh: String,
  val detailTh: String
)

data class AssessmentQuestion(
  val id: String,
  val pillar: PillarType,
  val code: String,
  val titleTh: String,
  val subtitleTh: String,
  val businessImpactTh: String,
  val options: List<MaturityOption>
)

data class PillarScore(
  val pillar: PillarType,
  val percentage: Int,
  val earnedPoints: Int,
  val maxPoints: Int,
  val levelDescription: String
)

data class ReadinessSummary(
  val overallScore: Int,
  val tier: DominanceTier,
  val defconLevel: Int,
  val pillarScores: Map<PillarType, PillarScore>,
  val topStrengths: List<String>,
  val criticalVulnerabilities: List<String>
)

data class ThreatScenario(
  val id: String,
  val title: String,
  val code: String,
  val threatActor: String,
  val killChainOverview: String,
  val primaryPillars: List<PillarType>,
  val impactLevel: String
)

data class ScenarioDefenseResult(
  val scenario: ThreatScenario,
  val survivalRate: Int,
  val blastRadiusSeverity: String,
  val estimatedMTTRHours: Double,
  val executionLogs: List<String>,
  val strategicFixes: List<String>
)

data class ChatMessage(
  val id: String,
  val isAi: Boolean,
  val text: String,
  val timestamp: String,
  val isStreaming: Boolean = false
)

enum class CyberTab(val labelTh: String, val labelEn: String) {
  OVERVIEW("ภาพรวม", "Overview"),
  ASSESSMENT("ประเมิน 4 ด้าน", "Assessment"),
  WAR_ROOM("จำลองภัยคุกคาม", "War Room"),
  AI_COPILOT("CISO AI", "AI Copilot"),
  REPORT("รายงานเชิงกลยุทธ์", "Executive Report")
}

enum class PillarMaturityGrade(
  val label: String,
  val gradeBadge: String,
  val color: Color
) {
  CRITICAL("Critical / เปราะบาง", "L1-L2", CyberCrimson),
  MODERATE("Emerging / เริ่มต้น", "L2-L3", CyberAmber),
  STRONG("Advanced / มั่นคง", "L3-L4", CyberCyan),
  DOMINANT("Supremacy / อัตโนมัติ", "L4-L5", CyberEmerald)
}

data class PillarProgressTrack(
  val pillar: PillarType,
  val answeredQuestionsCount: Int,
  val totalQuestionsCount: Int,
  val isCompleted: Boolean,
  val completionPercentage: Int,
  val maturityScorePercentage: Int,
  val averageLevel: Double,
  val currentGrade: PillarMaturityGrade,
  val milestoneDescriptionTh: String
)

data class AssessmentProgressTracker(
  val totalAnsweredCount: Int,
  val totalQuestionsCount: Int,
  val overallCompletionPercentage: Int,
  val isFullyCompleted: Boolean,
  val pillarTracks: List<PillarProgressTrack>,
  val leadPillar: PillarType?,
  val laggingPillar: PillarType?
)
