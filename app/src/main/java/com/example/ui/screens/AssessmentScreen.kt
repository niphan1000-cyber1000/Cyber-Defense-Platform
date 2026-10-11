package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CyberAssessmentRepository
import com.example.model.AssessmentProgressTracker
import com.example.model.AssessmentQuestion
import com.example.model.MaturityOption
import com.example.model.PillarType
import com.example.ui.components.CisoPillarProgressTracker
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
fun AssessmentScreen(
  answers: Map<String, Int>,
  tracker: AssessmentProgressTracker,
  selectedFilter: PillarType?,
  onFilterChange: (PillarType?) -> Unit,
  onAnswerSelected: (String, Int) -> Unit,
  onReset: () -> Unit,
  modifier: Modifier = Modifier
) {
  val questions = CyberAssessmentRepository.questions
  val filteredQuestions = if (selectedFilter != null) {
    questions.filter { it.pillar == selectedFilter }
  } else {
    questions
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CyberDarkVoid)
  ) {
    // -------------------------------------------------------------
    // Top Filter Bar
    // -------------------------------------------------------------
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(CyberDarkBg)
        .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "ประเมินความพร้อม 12 มิติกลยุทธ์",
            color = CyberCyan,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = "เลือกความพร้อมจริงเพื่อคำนวณ Dominance Index",
            color = CyberTextMuted,
            fontSize = 11.sp
          )
        }
        IconButton(
          onClick = onReset,
          modifier = Modifier.testTag("reset_assessment_button")
        ) {
          Icon(
            imageVector = Icons.Default.RestartAlt,
            contentDescription = "Reset",
            tint = CyberTextSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Horizontal Filter Chips
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        FilterChip(
          selected = selectedFilter == null,
          onClick = { onFilterChange(null) },
          label = { Text("ทั้งหมด (12)", fontSize = 11.sp) },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = CyberCyan.copy(alpha = 0.2f),
            selectedLabelColor = CyberCyan,
            containerColor = CyberCardBg,
            labelColor = CyberTextSecondary
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selectedFilter == null,
            borderColor = CyberBorder,
            selectedBorderColor = CyberCyan
          )
        )

        PillarType.entries.forEach { pillar ->
          FilterChip(
            selected = selectedFilter == pillar,
            onClick = { onFilterChange(pillar) },
            label = { Text(pillar.displayNameEn, fontSize = 11.sp) },
            colors = FilterChipDefaults.filterChipColors(
              selectedContainerColor = pillar.accentColor.copy(alpha = 0.2f),
              selectedLabelColor = pillar.accentColor,
              containerColor = CyberCardBg,
              labelColor = CyberTextSecondary
            ),
            border = FilterChipDefaults.filterChipBorder(
              enabled = true,
              selected = selectedFilter == pillar,
              borderColor = CyberBorder,
              selectedBorderColor = pillar.accentColor
            )
          )
        }
      }
    }

    // -------------------------------------------------------------
    // Questions List
    // -------------------------------------------------------------
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      item {
        CisoPillarProgressTracker(
          tracker = tracker,
          selectedPillar = selectedFilter,
          onPillarSelected = onFilterChange
        )
      }

      items(filteredQuestions, key = { it.id }) { question ->
        val currentLevel = answers[question.id] ?: 1

        QuestionItemCard(
          question = question,
          currentLevel = currentLevel,
          onLevelSelected = { level -> onAnswerSelected(question.id, level) }
        )
      }

      item {
        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}

@Composable
private fun QuestionItemCard(
  question: AssessmentQuestion,
  currentLevel: Int,
  onLevelSelected: (Int) -> Unit
) {
  val pillar = question.pillar

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(CyberCardBg)
      .border(1.dp, CyberBorder, RoundedCornerShape(14.dp))
      .padding(14.dp)
  ) {
    Column {
      // Header: Code and Pillar
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
              text = question.code,
              color = pillar.accentColor,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = pillar.displayNameEn,
            color = CyberTextMuted,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace
          )
        }

        // Current level badge
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(CyberDarkVoid)
            .border(1.dp, pillar.accentColor.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
          Text(
            text = "LEVEL $currentLevel / 5",
            color = pillar.accentColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = question.titleTh,
        color = CyberTextPrimary,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = question.subtitleTh,
        color = CyberTextSecondary,
        fontSize = 11.sp
      )

      // Business impact note
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(6.dp))
          .background(CyberDarkVoid.copy(alpha = 0.6f))
          .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Info,
          contentDescription = null,
          tint = CyberCyan,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = question.businessImpactTh,
          color = CyberCyan.copy(alpha = 0.9f),
          fontSize = 10.sp,
          lineHeight = 14.sp
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // 5 Maturity Options
      question.options.forEach { opt ->
        val isSelected = opt.level == currentLevel
        MaturityOptionRow(
          option = opt,
          isSelected = isSelected,
          pillarColor = pillar.accentColor,
          onClick = { onLevelSelected(opt.level) }
        )
        Spacer(modifier = Modifier.height(6.dp))
      }
    }
  }
}

@Composable
private fun MaturityOptionRow(
  option: MaturityOption,
  isSelected: Boolean,
  pillarColor: androidx.compose.ui.graphics.Color,
  onClick: () -> Unit
) {
  val borderColor = if (isSelected) pillarColor else CyberBorder.copy(alpha = 0.6f)
  val bgColor = if (isSelected) pillarColor.copy(alpha = 0.12f) else CyberDarkVoid.copy(alpha = 0.4f)

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .background(bgColor)
      .border(1.dp, borderColor, RoundedCornerShape(8.dp))
      .clickable(onClick = onClick)
      .padding(horizontal = 10.dp, vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    RadioButton(
      selected = isSelected,
      onClick = onClick,
      colors = RadioButtonDefaults.colors(
        selectedColor = pillarColor,
        unselectedColor = CyberTextMuted
      ),
      modifier = Modifier.size(20.dp)
    )

    Spacer(modifier = Modifier.width(10.dp))

    Column(modifier = Modifier.weight(1f)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = "L${option.level}: ${option.titleTh}",
          color = if (isSelected) CyberTextPrimary else CyberTextSecondary,
          fontSize = 12.sp,
          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
      }
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = option.detailTh,
        color = if (isSelected) CyberTextSecondary else CyberTextMuted,
        fontSize = 10.sp,
        lineHeight = 14.sp
      )
    }
  }
}
