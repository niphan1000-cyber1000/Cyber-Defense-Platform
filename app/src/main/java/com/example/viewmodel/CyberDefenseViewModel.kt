package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CyberAssessmentRepository
import com.example.model.AssessmentQuestion
import com.example.model.ChatMessage
import com.example.model.CyberTab
import com.example.model.DominanceTier
import com.example.model.PillarScore
import com.example.model.PillarType
import com.example.model.ReadinessSummary
import com.example.model.AssessmentProgressTracker
import com.example.model.PillarProgressTrack
import com.example.model.PillarMaturityGrade
import com.example.model.ScenarioDefenseResult
import com.example.model.ThreatScenario
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class CyberDefenseViewModel : ViewModel() {

  // Default baseline maturity levels (1 to 5)
  private val _answers = MutableStateFlow<Map<String, Int>>(
    mapOf(
      "vis_01" to 3,
      "vis_02" to 3,
      "vis_03" to 2,
      "det_01" to 3,
      "det_02" to 2,
      "det_03" to 3,
      "aut_01" to 2,
      "aut_02" to 3,
      "aut_03" to 3,
      "agt_01" to 2,
      "agt_02" to 2,
      "agt_03" to 1
    )
  )
  val answers: StateFlow<Map<String, Int>> = _answers.asStateFlow()

  private val _activeTab = MutableStateFlow(CyberTab.OVERVIEW)
  val activeTab: StateFlow<CyberTab> = _activeTab.asStateFlow()

  private val _pillarFilter = MutableStateFlow<PillarType?>(null)
  val pillarFilter: StateFlow<PillarType?> = _pillarFilter.asStateFlow()

  private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
    listOf(
      ChatMessage(
        id = "msg_init",
        isAi = true,
        text = "สวัสดีครับ ผมคือ CISO Strategic Cyber Defense Advisor ยินดีต้อนรับสู่แพลตฟอร์มประเมินความพร้อม Cyber Defense & Dominance ระบบนี้วิเคราะห์ตาม 4 เสาหลัก: Continuous Visibility, Detection to Decision, Controlled Automation และ Secure AI Agents คุณสามารถเริ่มทำแบบประเมิน หรือสอบถามแนวทางกลยุทธ์ได้แบบ Real-time ครับ",
        timestamp = getCurrentTimeString()
      )
    )
  )
  val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

  private val _isAiTyping = MutableStateFlow(false)
  val isAiTyping: StateFlow<Boolean> = _isAiTyping.asStateFlow()

  private val _selectedScenario = MutableStateFlow<ThreatScenario?>(CyberAssessmentRepository.threatScenarios.first())
  val selectedScenario: StateFlow<ThreatScenario?> = _selectedScenario.asStateFlow()

  private val _scenarioResult = MutableStateFlow<ScenarioDefenseResult?>(null)
  val scenarioResult: StateFlow<ScenarioDefenseResult?> = _scenarioResult.asStateFlow()

  private val _isSimulatingScenario = MutableStateFlow(false)
  val isSimulatingScenario: StateFlow<Boolean> = _isSimulatingScenario.asStateFlow()

  fun selectTab(tab: CyberTab) {
    _activeTab.value = tab
  }

  fun setPillarFilter(pillar: PillarType?) {
    _pillarFilter.value = pillar
  }

  fun setAnswer(questionId: String, level: Int) {
    val updated = _answers.value.toMutableMap()
    updated[questionId] = level
    _answers.value = updated
  }

  fun resetToDefaults() {
    _answers.value = mapOf(
      "vis_01" to 3, "vis_02" to 3, "vis_03" to 2,
      "det_01" to 3, "det_02" to 2, "det_03" to 3,
      "aut_01" to 2, "aut_02" to 3, "aut_03" to 3,
      "agt_01" to 2, "agt_02" to 2, "agt_03" to 1
    )
    _scenarioResult.value = null
  }

  fun selectScenario(scenario: ThreatScenario) {
    _selectedScenario.value = scenario
    _scenarioResult.value = null
  }

  fun calculateSummary(): ReadinessSummary {
    val currentAnswers = _answers.value
    val questions = CyberAssessmentRepository.questions

    val pillarScores = mutableMapOf<PillarType, PillarScore>()

    for (pillar in PillarType.entries) {
      val pillarQuestions = questions.filter { it.pillar == pillar }
      val maxPossible = pillarQuestions.size * 5
      val earned = pillarQuestions.sumOf { currentAnswers[it.id] ?: 1 }
      val percentage = if (maxPossible > 0) ((earned.toFloat() / maxPossible) * 100).toInt() else 0

      val levelDesc = when {
        percentage >= 85 -> "Autonomous Dominance"
        percentage >= 70 -> "Resilient Defense"
        percentage >= 50 -> "Standardized"
        percentage >= 35 -> "Emerging"
        else -> "Vulnerable"
      }

      pillarScores[pillar] = PillarScore(
        pillar = pillar,
        percentage = percentage,
        earnedPoints = earned,
        maxPoints = maxPossible,
        levelDescription = levelDesc
      )
    }

    val totalEarned = pillarScores.values.sumOf { it.earnedPoints }
    val totalMax = pillarScores.values.sumOf { it.maxPoints }
    val overallPercentage = if (totalMax > 0) ((totalEarned.toFloat() / totalMax) * 100).toInt() else 0

    val tier = when {
      overallPercentage >= DominanceTier.SUPREMACY.minScore -> DominanceTier.SUPREMACY
      overallPercentage >= DominanceTier.DOMINANT.minScore -> DominanceTier.DOMINANT
      overallPercentage >= DominanceTier.TACTICAL.minScore -> DominanceTier.TACTICAL
      overallPercentage >= DominanceTier.EMERGING.minScore -> DominanceTier.EMERGING
      else -> DominanceTier.FRAGILE
    }

    val defcon = when (tier) {
      DominanceTier.SUPREMACY -> 5 // Peace / Dominant
      DominanceTier.DOMINANT -> 4
      DominanceTier.TACTICAL -> 3
      DominanceTier.EMERGING -> 2
      DominanceTier.FRAGILE -> 1 // Critical alert
    }

    val strengths = mutableListOf<String>()
    val vulnerabilities = mutableListOf<String>()

    pillarScores.values.sortedByDescending { it.percentage }.firstOrNull()?.let { top ->
      strengths.add("จุดแข็งสูงสุด: ${top.pillar.displayNameTh} (${top.percentage}%)")
    }
    pillarScores.values.sortedBy { it.percentage }.firstOrNull()?.let { weak ->
      vulnerabilities.add("จุดเสี่ยงวิกฤต: ${weak.pillar.displayNameTh} (${weak.percentage}%) ควรเร่งยกระดับ")
    }

    if (currentAnswers["agt_01"] ?: 1 <= 2) {
      vulnerabilities.add("เสี่ยงสูงต่อ AI Prompt Injection & ข้อมูลรั่วไหลผ่าน Agent")
    }
    if (currentAnswers["aut_01"] ?: 1 <= 2) {
      vulnerabilities.add("การกักกันภัยยังทำแบบ Manual อาจสกัด Lateral Movement ไม่ทันการณ์")
    }
    if (currentAnswers["vis_01"] ?: 1 >= 4) {
      strengths.add("มีระบบมองเห็น Attack Surface แบบเกือบ Real-time ช่วยลด Shadow Assets")
    }

    return ReadinessSummary(
      overallScore = overallPercentage,
      tier = tier,
      defconLevel = defcon,
      pillarScores = pillarScores,
      topStrengths = strengths,
      criticalVulnerabilities = vulnerabilities
    )
  }

  fun calculateProgressTracker(): AssessmentProgressTracker {
    val currentAnswers = _answers.value
    val allQuestions = CyberAssessmentRepository.questions
    val totalQuestions = allQuestions.size
    val totalAnswered = allQuestions.count { currentAnswers.containsKey(it.id) }

    val pillarTracks = PillarType.entries.map { pillar ->
      val pQuestions = allQuestions.filter { it.pillar == pillar }
      val pTotal = pQuestions.size
      val answeredForPillar = pQuestions.filter { currentAnswers.containsKey(it.id) }
      val pAnsweredCount = answeredForPillar.size
      val isComplete = pAnsweredCount == pTotal && pTotal > 0
      val completionPct = if (pTotal > 0) ((pAnsweredCount.toFloat() / pTotal) * 100).toInt() else 0

      val maxPts = pTotal * 5
      val earnedPts = pQuestions.sumOf { currentAnswers[it.id] ?: 1 }
      val maturityPct = if (maxPts > 0) ((earnedPts.toFloat() / maxPts) * 100).toInt() else 0
      val avgLvl = if (pTotal > 0) earnedPts.toDouble() / pTotal else 1.0

      val grade = when {
        maturityPct >= 80 -> PillarMaturityGrade.DOMINANT
        maturityPct >= 65 -> PillarMaturityGrade.STRONG
        maturityPct >= 45 -> PillarMaturityGrade.MODERATE
        else -> PillarMaturityGrade.CRITICAL
      }

      val milestone = when {
        maturityPct >= 85 -> "L5: Autonomous Supremacy"
        maturityPct >= 70 -> "L4: Strategic Resilient"
        maturityPct >= 50 -> "L3: Standardized Process"
        maturityPct >= 35 -> "L2: Emerging Controls"
        else -> "L1: Fragile / Ad-hoc"
      }

      PillarProgressTrack(
        pillar = pillar,
        answeredQuestionsCount = pAnsweredCount,
        totalQuestionsCount = pTotal,
        isCompleted = isComplete,
        completionPercentage = completionPct,
        maturityScorePercentage = maturityPct,
        averageLevel = avgLvl,
        currentGrade = grade,
        milestoneDescriptionTh = milestone
      )
    }

    val overallCompletion = if (totalQuestions > 0) ((totalAnswered.toFloat() / totalQuestions) * 100).toInt() else 0
    val lead = pillarTracks.maxByOrNull { it.maturityScorePercentage }?.pillar
    val lag = pillarTracks.minByOrNull { it.maturityScorePercentage }?.pillar

    return AssessmentProgressTracker(
      totalAnsweredCount = totalAnswered,
      totalQuestionsCount = totalQuestions,
      overallCompletionPercentage = overallCompletion,
      isFullyCompleted = totalAnswered == totalQuestions,
      pillarTracks = pillarTracks,
      leadPillar = lead,
      laggingPillar = lag
    )
  }

  fun sendChatMessage(prompt: String) {
    if (prompt.isBlank() || _isAiTyping.value) return

    val userMessage = ChatMessage(
      id = UUID.randomUUID().toString(),
      isAi = false,
      text = prompt,
      timestamp = getCurrentTimeString()
    )

    _chatMessages.value = _chatMessages.value + userMessage
    _isAiTyping.value = true

    viewModelScope.launch {
      val summary = calculateSummary()
      val weakestPillar = summary.pillarScores.values.minByOrNull { it.percentage }?.pillar ?: PillarType.SECURE_AI_AGENTS
      val fullAnswer = CyberAssessmentRepository.generateStrategicAdvice(prompt, summary.overallScore, weakestPillar)

      val aiMessageId = UUID.randomUUID().toString()
      // Initial empty AI message
      val initialAiMsg = ChatMessage(
        id = aiMessageId,
        isAi = true,
        text = "",
        timestamp = getCurrentTimeString(),
        isStreaming = true
      )
      _chatMessages.value = _chatMessages.value + initialAiMsg

      // Real-time typewriter effect simulation
      val chunkSize = 8
      var currentIndex = 0
      while (currentIndex < fullAnswer.length) {
        val nextIndex = (currentIndex + chunkSize).coerceAtMost(fullAnswer.length)
        val currentChunk = fullAnswer.substring(0, nextIndex)

        _chatMessages.value = _chatMessages.value.map { msg ->
          if (msg.id == aiMessageId) msg.copy(text = currentChunk) else msg
        }
        currentIndex = nextIndex
        delay(25)
      }

      _chatMessages.value = _chatMessages.value.map { msg ->
        if (msg.id == aiMessageId) msg.copy(isStreaming = false) else msg
      }
      _isAiTyping.value = false
    }
  }

  fun runWarRoomSimulation(scenario: ThreatScenario) {
    if (_isSimulatingScenario.value) return
    _isSimulatingScenario.value = true
    _scenarioResult.value = null

    viewModelScope.launch {
      delay(600) // Initial triage
      val summary = calculateSummary()
      val relevantScores = scenario.primaryPillars.map { summary.pillarScores[it]?.percentage ?: 30 }
      val avgRelevant = relevantScores.average().toInt()

      // Calculate survival and blast radius based on real score
      val survivalRate = (avgRelevant * 0.95 + 5).toInt().coerceIn(15, 98)
      val blastRadius = when {
        survivalRate >= 80 -> "CONTAINED: Minimal Blast Radius (1-2 isolated hosts)"
        survivalRate >= 50 -> "PARTIAL: Moderate Segment Impact (Departmental quarantine)"
        else -> "UNCONTROLLED: Enterprise-wide lateral escalation"
      }
      val mttrHours = when {
        survivalRate >= 80 -> 0.4
        survivalRate >= 50 -> 4.5
        else -> 36.0
      }

      val logs = mutableListOf<String>()
      logs.add("[T+00:01] Infiltration vector detected: ${scenario.threatActor}")
      delay(300)
      logs.add("[T+00:05] Telemetry correlated across ${scenario.primaryPillars.joinToString { it.codePrefix }}")
      delay(300)
      if (survivalRate >= 65) {
        logs.add("[T+00:12] Automated Containment Playbook triggered. Host isolation in progress.")
        logs.add("[T+00:18] Lateral movement severed by Zero-Trust micro-segmentation.")
        logs.add("[T+00:25] AI Agent Sandboxing held attacker payload inside boundary.")
      } else {
        logs.add("[T+00:15] WARNING: Automated isolation bypassed due to lack of SOAR coverage.")
        logs.add("[T+00:45] Attacker executed privilege escalation on unmonitored credentials.")
        logs.add("[T+01:20] Incident escalated to Emergency Response Team.")
      }

      val fixes = mutableListOf<String>()
      if (summary.pillarScores[PillarType.CONTROLLED_AUTOMATION]?.percentage ?: 0 < 60) {
        fixes.add("เปิดใช้งาน Automated Host Isolation & Session Revocation ใน SOAR")
      }
      if (summary.pillarScores[PillarType.SECURE_AI_AGENTS]?.percentage ?: 0 < 60) {
        fixes.add("ติดตั้ง LLM Guardrails และบังคับใช้ Least-Agency สำหรับ AI Tools")
      }
      if (summary.pillarScores[PillarType.VISIBILITY]?.percentage ?: 0 < 60) {
        fixes.add("ปรับปรุง Continuous ASM เพื่อปิด Shadow Assets ภายนอก")
      }

      _scenarioResult.value = ScenarioDefenseResult(
        scenario = scenario,
        survivalRate = survivalRate,
        blastRadiusSeverity = blastRadius,
        estimatedMTTRHours = mttrHours,
        executionLogs = logs,
        strategicFixes = fixes
      )
      _isSimulatingScenario.value = false
    }
  }

  private fun getCurrentTimeString(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date())
  }
}
