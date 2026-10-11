package com.example.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CyberAssessmentRepository
import com.example.model.ChatMessage
import com.example.ui.theme.CyberBorder
import com.example.ui.theme.CyberCardBg
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.CyberDarkVoid
import com.example.ui.theme.CyberEmerald
import com.example.ui.theme.CyberTerminalGreen
import com.example.ui.theme.CyberTextMuted
import com.example.ui.theme.CyberTextPrimary
import com.example.ui.theme.CyberTextSecondary

@Composable
fun AiCopilotScreen(
  chatMessages: List<ChatMessage>,
  isTyping: Boolean,
  onSendMessage: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var inputText by remember { mutableStateOf("") }
  val listState = rememberLazyListState()

  // Auto scroll to bottom when messages update
  LaunchedEffect(chatMessages.size, chatMessages.lastOrNull()?.text?.length) {
    if (chatMessages.isNotEmpty()) {
      listState.animateScrollToItem(chatMessages.size - 1)
    }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CyberDarkVoid)
      .imePadding()
  ) {
    // -------------------------------------------------------------
    // Header
    // -------------------------------------------------------------
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(CyberDarkBg)
        .border(1.dp, CyberBorder, RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
        .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(CyberCyan.copy(alpha = 0.2f))
              .border(1.dp, CyberCyan, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Psychology,
              contentDescription = null,
              tint = CyberCyan,
              modifier = Modifier.size(18.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "CISO STRATEGIC AI ADVISOR",
              color = CyberCyan,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace
            )
            Text(
              text = "Real-time Telemetry & Strategic Consulting",
              color = CyberTextMuted,
              fontSize = 10.sp,
              fontFamily = FontFamily.Monospace
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(CyberEmerald.copy(alpha = 0.15f))
            .border(1.dp, CyberEmerald, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = if (isTyping) "STREAMING..." else "ONLINE",
            color = CyberEmerald,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
        }
      }
    }

    // -------------------------------------------------------------
    // Quick Suggestion Chips
    // -------------------------------------------------------------
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 16.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      CyberAssessmentRepository.sampleCisoPrompts.forEach { prompt ->
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(CyberCardBg)
            .border(1.dp, CyberBorder, RoundedCornerShape(16.dp))
            .clickable {
              if (!isTyping) {
                onSendMessage(prompt)
              }
            }
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(
            text = prompt,
            color = CyberCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }

    // -------------------------------------------------------------
    // Chat Message List
    // -------------------------------------------------------------
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .padding(horizontal = 16.dp, vertical = 6.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      items(chatMessages, key = { it.id }) { msg ->
        ChatMessageBubble(message = msg)
      }
    }

    // -------------------------------------------------------------
    // Input Box
    // -------------------------------------------------------------
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(CyberDarkBg)
        .border(1.dp, CyberBorder)
        .padding(12.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        OutlinedTextField(
          value = inputText,
          onValueChange = { inputText = it },
          placeholder = {
            Text("พิมพ์คำถามเชิงกลยุทธ์ CISO เช่น สอบถามจุดอ่อน, แผนงบประมาณ...", fontSize = 12.sp, color = CyberTextMuted)
          },
          modifier = Modifier
            .weight(1f)
            .testTag("ai_chat_input_field"),
          colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = CyberDarkVoid,
            unfocusedContainerColor = CyberDarkVoid,
            focusedBorderColor = CyberCyan,
            unfocusedBorderColor = CyberBorder,
            focusedTextColor = CyberTextPrimary,
            unfocusedTextColor = CyberTextPrimary,
            cursorColor = CyberCyan
          ),
          shape = RoundedCornerShape(10.dp),
          maxLines = 3,
          enabled = !isTyping
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
          onClick = {
            val textToSend = inputText.trim()
            if (textToSend.isNotEmpty()) {
              inputText = ""
              onSendMessage(textToSend)
            }
          },
          enabled = inputText.isNotBlank() && !isTyping,
          modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (inputText.isNotBlank() && !isTyping) CyberCyan else CyberBorder)
            .testTag("send_ai_chat_button")
        ) {
          if (isTyping) {
            CircularProgressIndicator(
              modifier = Modifier.size(20.dp),
              color = CyberDarkVoid,
              strokeWidth = 2.dp
            )
          } else {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.Send,
              contentDescription = "Send",
              tint = CyberDarkVoid,
              modifier = Modifier.size(20.dp)
            )
          }
        }
      }
    }
  }
}

@Composable
private fun ChatMessageBubble(message: ChatMessage) {
  val isAi = message.isAi

  val infiniteTransition = rememberInfiniteTransition(label = "cursor")
  val cursorAlpha by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(400),
      repeatMode = RepeatMode.Reverse
    ),
    label = "cursor"
  )

  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = if (isAi) Alignment.Start else Alignment.End
  ) {
    // Sender label
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        text = if (isAi) "⚡ CISO ADVISOR // AI" else "OPERATOR // YOU",
        color = if (isAi) CyberCyan else CyberEmerald,
        fontSize = 10.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = message.timestamp,
        color = CyberTextMuted,
        fontSize = 9.sp,
        fontFamily = FontFamily.Monospace
      )
    }

    Spacer(modifier = Modifier.height(4.dp))

    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(12.dp))
        .background(if (isAi) CyberCardBg else CyberDarkBg)
        .border(
          1.dp,
          if (isAi) CyberBorder else CyberEmerald.copy(alpha = 0.5f),
          RoundedCornerShape(12.dp)
        )
        .padding(14.dp)
        .fillMaxWidth(if (isAi) 0.95f else 0.85f)
    ) {
      Column {
        Text(
          text = message.text + if (message.isStreaming && cursorAlpha > 0.5f) " ▋" else "",
          color = if (isAi) CyberTextPrimary else CyberEmerald,
          fontSize = 12.sp,
          fontFamily = if (isAi) FontFamily.Default else FontFamily.Monospace,
          lineHeight = 18.sp
        )
      }
    }
  }
}
