package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CyberTab
import com.example.ui.components.CyberTopBar
import com.example.ui.screens.AiCopilotScreen
import com.example.ui.screens.AssessmentScreen
import com.example.ui.screens.ExecutiveReportScreen
import com.example.ui.screens.OverviewScreen
import com.example.ui.screens.WarRoomScreen
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.CyberDarkVoid
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextSecondary
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.CyberDefenseViewModel

class MainActivity : ComponentActivity() {

  private val viewModel: CyberDefenseViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        CyberDefenseApp(viewModel = viewModel)
      }
    }
  }
}

@Composable
fun CyberDefenseApp(viewModel: CyberDefenseViewModel) {
  val currentTab by viewModel.activeTab.collectAsState()
  val answers by viewModel.answers.collectAsState()
  val pillarFilter by viewModel.pillarFilter.collectAsState()
  val chatMessages by viewModel.chatMessages.collectAsState()
  val isAiTyping by viewModel.isAiTyping.collectAsState()
  val selectedScenario by viewModel.selectedScenario.collectAsState()
  val scenarioResult by viewModel.scenarioResult.collectAsState()
  val isSimulating by viewModel.isSimulatingScenario.collectAsState()

  val summary = viewModel.calculateSummary()
  val tracker = viewModel.calculateProgressTracker()

  // Handle back button: return to Overview if in sub-screen
  BackHandler(enabled = currentTab != CyberTab.OVERVIEW) {
    viewModel.selectTab(CyberTab.OVERVIEW)
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = CyberDarkVoid,
    topBar = {
      CyberTopBar(summary = summary)
    },
    bottomBar = {
      CyberBottomNavBar(
        activeTab = currentTab,
        onTabSelected = { viewModel.selectTab(it) }
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentTab) {
        CyberTab.OVERVIEW -> {
          OverviewScreen(
            summary = summary,
            tracker = tracker,
            onNavigateToTab = { viewModel.selectTab(it) },
            onFilterPillar = { viewModel.setPillarFilter(it) }
          )
        }
        CyberTab.ASSESSMENT -> {
          AssessmentScreen(
            answers = answers,
            tracker = tracker,
            selectedFilter = pillarFilter,
            onFilterChange = { viewModel.setPillarFilter(it) },
            onAnswerSelected = { qId, lvl -> viewModel.setAnswer(qId, lvl) },
            onReset = { viewModel.resetToDefaults() }
          )
        }
        CyberTab.WAR_ROOM -> {
          WarRoomScreen(
            selectedScenario = selectedScenario,
            scenarioResult = scenarioResult,
            isSimulating = isSimulating,
            onSelectScenario = { viewModel.selectScenario(it) },
            onRunSimulation = { viewModel.runWarRoomSimulation(it) }
          )
        }
        CyberTab.AI_COPILOT -> {
          AiCopilotScreen(
            chatMessages = chatMessages,
            isTyping = isAiTyping,
            onSendMessage = { viewModel.sendChatMessage(it) }
          )
        }
        CyberTab.REPORT -> {
          ExecutiveReportScreen(
            summary = summary
          )
        }
      }
    }
  }
}

@Composable
fun CyberBottomNavBar(
  activeTab: CyberTab,
  onTabSelected: (CyberTab) -> Unit,
  modifier: Modifier = Modifier
) {
  NavigationBar(
    modifier = modifier
      .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
      .border(1.dp, CyberBorder, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
    containerColor = CyberDarkBg,
    tonalElevation = 8.dp
  ) {
    val items = listOf(
      Triple(CyberTab.OVERVIEW, Icons.Default.Shield, "ภาพรวม"),
      Triple(CyberTab.ASSESSMENT, Icons.Default.Assignment, "ประเมิน 4 ด้าน"),
      Triple(CyberTab.WAR_ROOM, Icons.Default.Security, "War Room"),
      Triple(CyberTab.AI_COPILOT, Icons.Default.Psychology, "CISO AI"),
      Triple(CyberTab.REPORT, Icons.Default.Description, "รายงาน")
    )

    items.forEach { (tab, icon, label) ->
      val isSelected = activeTab == tab
      NavigationBarItem(
        selected = isSelected,
        onClick = { onTabSelected(tab) },
        icon = {
          Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(20.dp)
          )
        },
        label = {
          Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontFamily = FontFamily.Monospace
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = CyberCyan,
          selectedTextColor = CyberCyan,
          unselectedIconColor = CyberTextMuted,
          unselectedTextColor = CyberTextMuted,
          indicatorColor = CyberCardBg
        ),
        modifier = Modifier.testTag("tab_${tab.name.lowercase()}")
      )
    }
  }
}
