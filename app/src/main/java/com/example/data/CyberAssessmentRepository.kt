package com.example.data

import com.example.model.AssessmentQuestion
import com.example.model.MaturityOption
import com.example.model.PillarType
import com.example.model.ThreatScenario

object CyberAssessmentRepository {

  val questions: List<AssessmentQuestion> = listOf(
    // -------------------------------------------------------------
    // PILLAR 1: CONTINUOUS VISIBILITY (การมองเห็นแบบต่อเนื่อง)
    // -------------------------------------------------------------
    AssessmentQuestion(
      id = "vis_01",
      pillar = PillarType.VISIBILITY,
      code = "VIS-01",
      titleTh = "การบริหารจัดการพื้นที่การโจมตี (Attack Surface Management - ASM)",
      subtitleTh = "การค้นหา ตรวจสอบสินทรัพย์และจุดรั่วไหลภายนอก/ภายในองค์กร",
      businessImpactTh = "ลดช่องโหว่ที่ไม่ถูกบันทึก (Shadow Assets) ที่แฮกเกอร์มักใช้เป็นจุดเจาะเข้าด่านแรก",
      options = listOf(
        MaturityOption(1, "Manual Inventory", "นับสินทรัพย์ด้วย Excel หรือทำ Audit ปีละ 1-2 ครั้ง ไม่มีระบบค้นหาอัตโนมัติ"),
        MaturityOption(2, "Periodic Vulnerability Scan", "สแกนช่องโหว่ตามรอบ (เช่น รายเดือน) แต่ตรวจจับเฉพาะ IP ที่ลงทะเบียนไว้ล่วงหน้า"),
        MaturityOption(3, "Automated Discovery", "มีเครื่องมือสแกน External ASM ค้นหาโดเมนและ Subdomain ใหม่ได้ภายใน 24-48 ชั่วโมง"),
        MaturityOption(4, "Continuous Real-time ASM", "ตรวจจับพื้นที่เสี่ยงแบบเกือบ Real-time ครอบคลุมทั้ง API, Cloud Storage และ Certificate"),
        MaturityOption(5, "Autonomous Attack Graph", "เชื่อมโยงสินทรัพย์ทั้งหมดเข้ากับ Threat Graph และจัดลำดับความเสี่ยงตาม Business Context แบบอัตโนมัติ")
      )
    ),
    AssessmentQuestion(
      id = "vis_02",
      pillar = PillarType.VISIBILITY,
      code = "VIS-02",
      titleTh = "การมองเห็นด้านสิทธิ์และอัตลักษณ์ (Identity & CIEM Fabric)",
      subtitleTh = "การตรวจสอบสิทธิ์ของผู้ใช้และบัญชีเครื่องจักร (Non-Human Identities / Service Accounts)",
      businessImpactTh = "ป้องกันการขโมย Token, สิทธิ์คงค้าง (Over-privileged), และการขยายสิทธิ์ในระบบคลาวด์",
      options = listOf(
        MaturityOption(1, "Static Password & Siloed IAM", "ไม่มีระบบ MFA แบบครอบคลุม บัญชี Service Accounts ไม่มีบันทึกผู้รับผิดชอบ"),
        MaturityOption(2, "Basic MFA & Periodic Review", "บังคับใช้ MFA กับพนักงาน แต่ Service Accounts และ API Keys ยังไม่มีการตรวจจับการรั่วไหล"),
        MaturityOption(3, "Centralized Identity Management", "เชื่อมโยง SSO/IdP มีกระบวนการตัดสิทธิ์เมื่อพนักงานลาออก (JML Process)"),
        MaturityOption(4, "Continuous CIEM & Identity Risk", "ตรวจจับ Over-permissioned Accounts และพฤติกรรมผิดปกติของบัญชีอัตโนมัติ"),
        MaturityOption(5, "Zero-Trust Identity Mesh", "ตรวจสอบสิทธิ์แบบ Contextual Just-In-Time (JIT) พร้อมจัดการ Non-Human Identity แบบอัตโนมัติ 100%")
      )
    ),
    AssessmentQuestion(
      id = "vis_03",
      pillar = PillarType.VISIBILITY,
      code = "VIS-03",
      titleTh = "การตรวจจับ Shadow IT & เครื่องมือ AI ที่ไม่ได้รับอนุญาต (Shadow AI Discovery)",
      subtitleTh = "ความสามารถในการตรวจสอบการใช้ SaaS และบริการ Generative AI ของพนักงาน",
      businessImpactTh = "สกัดกั้นการนำข้อมูลความลับ ข้อมูลลูกค้า หรือโค้ดซอร์สไปป้อนให้ AI สาธารณะ",
      options = listOf(
        MaturityOption(1, "Blind to Shadow SaaS & AI", "ไม่มีนโยบายและไม่มีเทคโนโลยีคอยมอนิเตอร์การรับส่งข้อมูลไปยังภายนอก"),
        MaturityOption(2, "Policy-Only Restriction", "มีเพียงเอกสารข้อบังคับห้ามใช้ แต่ไม่มีระบบทางเทคนิคป้องกันการเข้าถึง"),
        MaturityOption(3, "Web Proxy / CASB Filtering", "บล็อกเว็บไซต์หรือเครื่องมือ AI ที่มีความเสี่ยงสูงผ่าน Proxy หรือ DNS Gateway"),
        MaturityOption(4, "Deep DLP & Contextual AI Auditing", "ตรวจจับเนื้อหา (Sensitive Data/PII) ก่อนส่งออกไปยังโมเดล AI ภายนอกได้แบบเรียลไทม์"),
        MaturityOption(5, "Unified AI Governance Gateway", "มี Enterprise AI Hub พร้อมระบบสกัดข้อมูลลับและจัดทำบันทึกความเสี่ยงอัตโนมัติ")
      )
    ),

    // -------------------------------------------------------------
    // PILLAR 2: DETECTION TO DECISION (การตรวจจับสู่การตัดสินใจ)
    // -------------------------------------------------------------
    AssessmentQuestion(
      id = "det_01",
      pillar = PillarType.DETECTION_TO_DECISION,
      code = "DET-01",
      titleTh = "การรวมศูนย์และวิเคราะห์สัญญาณ Telemetry (XDR & Data Lake)",
      subtitleTh = "การเชื่อมต่อ Log จาก Endpoint, Network, Cloud, และ Identity เข้าด้วยกัน",
      businessImpactTh = "ลด Alert Fatigue และช่วยให้นักวิเคราะห์เห็นภาพรวมการโจมตีแบบ End-to-End",
      options = listOf(
        MaturityOption(1, "Fragmented Siloed Logs", "แต่ละระบบเก็บ Log แยกกัน ตรวจดูเฉพาะเมื่อเกิดเหตุการณ์ร้ายแรง"),
        MaturityOption(2, "Basic Log Aggregation", "ส่ง Syslog มารวมที่ส่วนกลาง แต่ไม่มีการเขียนกฎ Correlation ข้ามระบบ"),
        MaturityOption(3, "Correlated SIEM/EDR", "มี SIEM และ EDR แจ้งเตือนเมื่อพบ Indicator of Compromise (IoC) พื้นฐาน"),
        MaturityOption(4, "Unified XDR Telemetry", "รวบรวมข้อมูลหลายชั้น (Host, Cloud, Network, Identity) และใช้ Machine Learning ลด Noise"),
        MaturityOption(5, "Autonomous Cyber Data Lake", "วิเคราะห์เหตุการณ์ระดับ Petabyte ด้วย AI Graph Correlation พร้อมระบุ Kill-chain ทันที")
      )
    ),
    AssessmentQuestion(
      id = "det_02",
      pillar = PillarType.DETECTION_TO_DECISION,
      code = "DET-02",
      titleTh = "ความเร็วในการตรวจจับและการสืบสวน (Mean Time to Detect & Triage)",
      subtitleTh = "เวลาเฉลี่ยที่ใช้ตั้งแต่ผู้บุกรุกเริ่มเข้ามา จนถึงการระบุระดับภัยคุกคาม",
      businessImpactTh = "ตัดวงจรการบุกรุกก่อนที่แฮกเกอร์จะขโมยข้อมูลหรือเข้ารหัสไฟล์ระบบ",
      options = listOf(
        MaturityOption(1, "Months to Weeks", "มักทราบเรื่องเมื่อมีบุคคลภายนอกหรือผู้ใช้ร้องเรียนเข้ามา (เฉลี่ยเกิน 30 วัน)"),
        MaturityOption(2, "Days to Triaged", "ทีม SOC ใช้เวลาหลายวันในการค้นหาและยืนยันว่าเป็นภัยจริงหรือ False Positive"),
        MaturityOption(3, "Hours (Under 24h)", "สามารถยืนยันและวิเคราะห์ความเสี่ยงได้ภายใน 4-24 ชั่วโมง"),
        MaturityOption(4, "Under 1 Hour (Sub-60m)", "มีระบบอัตโนมัติช่วยรวบรวมหลักฐาน (Artifact Enrichment) ทำให้ Triage เสร็จภายใน 1 ชั่วโมง"),
        MaturityOption(5, "Real-time Under 5 Minutes", "ระบบตรวจจับและสรุป Incident Timeline พร้อมประเมินผลกระทบเชิงธุรกิจในระดับนาที")
      )
    ),
    AssessmentQuestion(
      id = "det_03",
      pillar = PillarType.DETECTION_TO_DECISION,
      code = "DET-03",
      titleTh = "การใช้ Threat Intelligence & MITRE ATT&CK Mapping",
      subtitleTh = "การนำข้อมูลแนวโน้มการโจมตีจริงมาแมปกับพฤติกรรมในระบบ",
      businessImpactTh = "ป้องกันการโจมตีด้วยพฤติกรรม (TTPs) ไม่ใช่แค่การตรวจจับแฮชไฟล์แบบเดิม",
      options = listOf(
        MaturityOption(1, "No Threat Intel", "ไม่อัปเดตข้อมูลข่าวกรองภัยคุกคาม"),
        MaturityOption(2, "Open Source Feeds Only", "รับ Feed IoC ฟรีมาบล็อกที่ Firewall แบบไม่มีการวิเคราะห์บริบท"),
        MaturityOption(3, "Commercial Threat Intel", "มีฟีดระดับองค์กรและแมปบาง Incident กับ MITRE ATT&CK Framework"),
        MaturityOption(4, "Dynamic Adversary Profiling", "ปรับแต่งการเฝ้าระวังตามกลุ่มผู้โจมตีที่มุ่งเป้าอุตสาหกรรมตนเองโดยเฉพาะ"),
        MaturityOption(5, "Predictive Threat Anticipation", "ประเมินเส้นทางการโจมตีล่วงหน้าและวางกับดัก (Deception/Honey-tokens) ดักทางผู้บุกรุก")
      )
    ),

    // -------------------------------------------------------------
    // PILLAR 3: CONTROLLED AUTOMATION (ระบบอัตโนมัติภายใต้การควบคุม)
    // -------------------------------------------------------------
    AssessmentQuestion(
      id = "aut_01",
      pillar = PillarType.CONTROLLED_AUTOMATION,
      code = "AUT-01",
      titleTh = "ระบบกักกันภัยอัตโนมัติ (Automated Containment Playbooks)",
      subtitleTh = "การสั่งตัดการเชื่อมต่อเครื่องที่ติดมัลแวร์หรือรีโวคสิทธิ์ผู้ใช้ทันที",
      businessImpactTh = "สกัดการแพร่กระจาย Lateral Movement ภายในองค์กรในระดับวินาที",
      options = listOf(
        MaturityOption(1, "100% Manual Execution", "เจ้าหน้าที่ต้องรีโมตไปถอดสายแลนหรือเปลี่ยนรหัสผ่านด้วยตนเอง"),
        MaturityOption(2, "Script-Assisted Reaction", "มีสคริปต์เตรียมไว้ แต่ต้องรอคำสั่งและการอนุมัติหลายขั้นตอน"),
        MaturityOption(3, "SOAR for Low-Risk Events", "ระบบ SOAR สั่งกักกันอัตโนมัติเฉพาะกรณีความเสี่ยงต่ำ เช่น บล็อก IP ชัดเจน"),
        MaturityOption(4, "Automated Host/Account Isolation", "กักกันอุปกรณ์และเพิกถอน Active Session อัตโนมัติเมื่อตรวจพบภัยร้ายแรง"),
        MaturityOption(5, "Full Micro-Segmentation & Healing", "ระบบตัดแยก Blast Radius อัตโนมัติและสร้าง Clean Instance ใหม่โดยไม่กระทบ Business")
      )
    ),
    AssessmentQuestion(
      id = "aut_02",
      pillar = PillarType.CONTROLLED_AUTOMATION,
      code = "AUT-02",
      titleTh = "กลไกควบคุมและสวิตช์ความปลอดภัย (Human-in-the-Loop & Kill-Switch)",
      subtitleTh = "การรักษาการควบคุมของมนุษย์เหนือระบบอัตโนมัติ เพื่อป้องกันระบบล่มหรือความผิดพลาด",
      businessImpactTh = "ป้องกันกรณีระบบป้องกันเข้าใจผิดแล้วสั่งปิดระบบ Core Banking หรือ ERP ขององค์กร",
      options = listOf(
        MaturityOption(1, "No Safety Guards", "ไม่มีระบบอัตโนมัติ หรือมีแต่ไม่มีกลไกหยุดฉุกเฉิน"),
        MaturityOption(2, "Hardcoded Scripts", "สคริปต์ทำงานอัตโนมัติโดยไม่มีปุ่ม Overrule และยากต่อการตรวจสอบย้อนหลัง"),
        MaturityOption(3, "Dual-Approval for Critical Action", "การกระทำที่มีผลกระทบต่อ Core Service ต้องได้รับการยืนยันจาก CISO/Lead SOC"),
        MaturityOption(4, "Granular Kill-Switch & Rollback", "มี Kill-Switch สำหรับ Playbook ทุกตัว และมีฟังก์ชัน Rollback การตั้งค่าในคลิกเดียว"),
        MaturityOption(5, "Dynamic Confidence Governance", "ระบบอัตโนมัติตัดสินใจตามระดับ Confidence Score หากความมั่นใจต่ำจะเรียกผู้เชี่ยวชาญทันที")
      )
    ),
    AssessmentQuestion(
      id = "aut_03",
      pillar = PillarType.CONTROLLED_AUTOMATION,
      code = "AUT-03",
      titleTh = "การจำกัดขอบเขตความเสียหายและการกู้คืนข้อมูล (Blast Radius & Resilience)",
      subtitleTh = "การสำรองข้อมูลแบบ Immutable และการจำลอง Disaster Recovery เสมือนจริง",
      businessImpactTh = "ต่อต้าน Ransomware แบบ Double-Extortion และทำให้องค์กรดำเนินธุรกิจต่อได้",
      options = listOf(
        MaturityOption(1, "Unprotected Backups", "สำรองข้อมูลบนเครือข่ายเดียวกัน หากโดน Ransomware ก็ติดไปด้วย"),
        MaturityOption(2, "Offline Backup Without Testing", "มี Backup แยกต่างหาก แต่แทบไม่เคยทดสอบขั้นตอนการกู้คืนจริง"),
        MaturityOption(3, "Immutable Storage & Annual Drill", "ใช้ Immutable Storage และมีการซ้อมแผน Incident Response ปีละ 1 ครั้ง"),
        MaturityOption(4, "Automated Snapshot & Fast Restore", "ทริกเกอร์ Snapshot ทันทีที่มีการตรวจพบการเคลื่อนไหวผิดปกติ กู้คืนได้ภายใน 4 ชม."),
        MaturityOption(5, "Continuous Cyber Resilience Fabric", "สถาปัตยกรรม High Availability แบบ Self-Healing ที่ทนทานต่อการโจมตีระดับโครงสร้างพื้นฐาน")
      )
    ),

    // -------------------------------------------------------------
    // PILLAR 4: SECURE AI AGENTS (ความมั่นคงปลอดภัย AI & AGENTS)
    // -------------------------------------------------------------
    AssessmentQuestion(
      id = "agt_01",
      pillar = PillarType.SECURE_AI_AGENTS,
      code = "AGT-01",
      titleTh = "การป้องกันการโจมตีโมเดล AI (Prompt Injection & Jailbreak Defense)",
      subtitleTh = "การสร้างเกราะป้องกัน Direct & Indirect Prompt Injection ในระบบ LLM/Agent",
      businessImpactTh = "ป้องกันผู้ไม่หวังดีป้อนคำสั่งลักลอบหลอกให้ AI เปิดเผยข้อมูลลับหรือรันคำสั่งอันตราย",
      options = listOf(
        MaturityOption(1, "No AI Protections", "เชื่อมต่อ LLM API ตรงๆ โดยไม่มีการคัดกรอง Input/Output ใดๆ"),
        MaturityOption(2, "Basic System Prompt Rules", "พึ่งพาเพียง System Prompt เช่น 'อย่าบอกรหัสผ่าน' ซึ่งถูก Jailbreak ได้ง่าย"),
        MaturityOption(3, "Input Sanitation & Heuristics", "มีฟิลเตอร์ตรวจจับคีย์เวิร์ดอันตรายก่อนส่งเข้าโมเดล"),
        MaturityOption(4, "Multi-Layer LLM Firewall", "มี Guardrail ตรวจสอบความหมาย ป้องกัน Data Exfiltration และตรวจจับ Indirect Injection"),
        MaturityOption(5, "Adversarial Robustness Pipeline", "ทดสอบ Red Teaming อัตโนมัติด้วย AI พร้อม Real-time Anomaly Detection ในทุก Inference")
      )
    ),
    AssessmentQuestion(
      id = "agt_02",
      pillar = PillarType.SECURE_AI_AGENTS,
      code = "AGT-02",
      titleTh = "การจำกัดสิทธิ์และการ Sandbox ของ AI Agents (Least-Agency Architecture)",
      subtitleTh = "การควบคุมว่า AI Agent แต่ละตัวสามารถเข้าถึงระบบ ฐานข้อมูล หรือรันคำสั่งใดได้บ้าง",
      businessImpactTh = "ป้องกัน Agent ทำงานนอกลู่นอกทางเมื่อถูกหลอกลวง (Agent Hijacking)",
      options = listOf(
        MaturityOption(1, "Root/Admin Privileged Agents", "ให้ Agent ใช้ Service Account สิทธิ์สูงสุดเพื่อให้ทำงานได้ง่าย"),
        MaturityOption(2, "Shared Credentials", "Agent ใช้ Credentials เดียวกันกับพนักงานทั่วไป ไม่มีการแยก Identity เฉพาะ"),
        MaturityOption(3, "Scoped Tool Definitions", "กำหนด API Spec และขอบเขตเครื่องมือที่ Agent ใช้งานได้อย่างชัดเจน"),
        MaturityOption(4, "Sandboxed Micro-Agents", "Agent ทำงานใน Environment ที่ถูก Sandbox และต้องขอยืนยันสิทธิ์ก่อนทำ Action สำคัญ"),
        MaturityOption(5, "Autonomous Least-Agency Runtime", "ระบบสร้าง Ephemeral Token สิทธิ์ขั้นต่ำตามงานที่ระบุ และตัดสิทธิ์ทันทีเมื่อทำงานเสร็จ")
      )
    ),
    AssessmentQuestion(
      id = "agt_03",
      pillar = PillarType.SECURE_AI_AGENTS,
      code = "AGT-03",
      titleTh = "ความปลอดภัยในห่วงโซ่อุปทานโมเดลและการบันทึกพฤติกรรม (AI Supply Chain & Auditing)",
      subtitleTh = "การตรวจสอบความน่าเชื่อถือของ Weights, Plugins, Vector DB และการบันทึก Audit Logs",
      businessImpactTh = "สกัดโมเดลที่ถูกฝัง Backdoor และมีหลักฐานสืบสวนคดีทางไซเบอร์ครบถ้วน",
      options = listOf(
        MaturityOption(1, "Untracked AI Usage", "ดาวน์โหลดโมเดลจากเน็ตมารันโดยไม่มีการตรวจเช็กความปลอดภัย"),
        MaturityOption(2, "Approved Model List Only", "มีรายชื่อโมเดลที่อนุญาตให้ใช้ แต่ไม่มีการตรวจสอบ Package Dependencies"),
        MaturityOption(3, "Model Signing & Basic Audit", "ดาวน์โหลดจากที่เก็บที่เชื่อถือได้และบันทึกประวัติการเรียกใช้งานเบื้องต้น"),
        MaturityOption(4, "AI BOM & Continuous Telemetry", "มี AI Bill of Materials (AIBOM) และบันทึก Token Telemetry ทุกรอบเพื่อตรวจสอบย้อนหลัง"),
        MaturityOption(5, "Zero-Trust Autonomous Governance", "ตรวจสอบความผิดปกติของคำตอบ (Hallucination/Drift) และมีระบบตัดการเชื่อมต่อโมเดลทันทีเมื่อพบความเสี่ยง")
      )
    )
  )

  val threatScenarios: List<ThreatScenario> = listOf(
    ThreatScenario(
      id = "scen_01",
      title = "Autonomous AI Agent Hijacking & Lateral Pivot",
      code = "SIM-CYBER-01",
      threatActor = "Advanced Persistent Threat (APT-AgentStrike)",
      killChainOverview = "ส่งอีเมลที่มี Indirect Prompt Injection ไปยัง Customer Service AI Agent -> บังคับให้ Agent สั่งรันคำสั่ง SQL ดึงข้อมูล PII -> พยายามขยายสิทธิ์ข้ามไปยัง Internal Kubernetes Cluster",
      primaryPillars = listOf(PillarType.SECURE_AI_AGENTS, PillarType.CONTROLLED_AUTOMATION),
      impactLevel = "CRITICAL (Data Breach & Infrastructure Takeover)"
    ),
    ThreatScenario(
      id = "scen_02",
      title = "Multi-Stage Ransomware via Shadow Cloud Storage",
      code = "SIM-CYBER-02",
      threatActor = "Ransomware Cartel (BlackVault Nexus)",
      killChainOverview = "ตรวจพบบัคเก็ตคลาวด์เปิดสาธารณะที่ไม่ได้ลงทะเบียน (Shadow IT) -> ขโมย IAM Credential -> โจมตีข้ามระบบและเริ่มเข้ารหัสฐานข้อมูลการผลิต",
      primaryPillars = listOf(PillarType.VISIBILITY, PillarType.DETECTION_TO_DECISION),
      impactLevel = "HIGH (Operational Disruption & Extortion)"
    ),
    ThreatScenario(
      id = "scen_03",
      title = "CI/CD Software Supply Chain Poisoning",
      code = "SIM-CYBER-03",
      threatActor = "State-Sponsored Cyber Espionage",
      killChainOverview = "แทรกโค้ดอันตรายใน Open-Source Library ยอดนิยม -> ฝัง Backdoor ใน Artifact ที่คอมไพล์ -> รอคำสั่ง C2 เพื่อดักจับข้อมูลการเงิน",
      primaryPillars = listOf(PillarType.VISIBILITY, PillarType.DETECTION_TO_DECISION, PillarType.CONTROLLED_AUTOMATION),
      impactLevel = "CATASTROPHIC (Trust & Regulatory Impact)"
    )
  )

  val sampleCisoPrompts = listOf(
    "วิเคราะห์จุดอ่อนสำคัญที่สุดตามผลประเมินปัจจุบัน",
    "เราควรจัดลำดับการลงทุน (Investment Prioritization) อย่างไร?",
    "สถาปัตยกรรม Secure AI Agent ที่ดีควรมีอะไรบ้าง?",
    "วิธีลด MTTD จากระดับชั่วโมงสู่ระดับนาทีตามหลัก CISO"
  )

  fun generateStrategicAdvice(prompt: String, overallScore: Int, weakestPillar: PillarType): String {
    return when {
      prompt.contains("จุดอ่อน") || prompt.contains("วิเคราะห์") -> {
        """
        [CISO STRATEGIC ADVISORY: VULNERABILITY ANALYSIS]
        
        จากการวิเคราะห์ผลคะแนนรวมขององค์กรที่ ${overallScore}%:
        - ด้านที่มีความเสี่ยงสูงสุดคือ: ${weakestPillar.displayNameTh} (${weakestPillar.displayNameEn})
        
        ข้อสังเกตเชิงยุทธศาสตร์:
        1. ความเปราะบางหลัก: องค์กรยังขาดความต่อเนื่องในการบูรณาการข้อมูลข้ามระบบ ทำให้เกิด 'Blind Spots' ที่แฮกเกอร์สามารถใช้ซ่อนตัวได้
        2. Threat Exposure: ผู้โจมตียุคปัจจุบันใช้เวลาเฉลี่ยเพียง 8-15 นาที ในการเจาะและเริ่ม Lateral Movement หากกระบวนการยังพึ่งพา Manual Approval จะไม่สามารถรับมือได้ทัน
        3. คำแนะนำเร่งด่วน: ปรับปรุงการใช้งาน Guardrails และตั้งค่าการตัดการเชื่อมต่ออัตโนมัติ (Automated Containment) โดยมี Human-in-the-Loop สำหรับภารกิจวิกฤต
        """.trimIndent()
      }
      prompt.contains("ลงทุน") || prompt.contains("Prioritization") -> {
        """
        [CISO BOARDROOM ROADMAP: INVESTMENT ALLOCATION]
        
        แผนงบประมาณเพื่อมุ่งสู่ Strategic Dominance:
        
        1. ระยะสั้น (0 - 90 วัน): Quick Wins
           - บังคับใช้ Phishing-Resistant MFA และจัดการ Over-privileged Accounts ทันที
           - ติดตั้ง AI Input/Output Guardrails บนโมเดลและแอปพลิเคชันที่มีการต่อเชื่อมกับข้อมูลลูกค้า
        
        2. ระยะกลาง (3 - 6 เดือน): Resilience Scaling
           - อัปเกรดระบบตรวจจับเป็น Unified XDR เชื่อมต่อกับ Data Lake ลด Alert Fatigue
           - พัฒนา SOAR Playbooks 3 ชุดหลัก: Host Isolation, Token Revocation, และ Network Segmentation
        
        3. ระยะยาว (6 - 12 เดือน): Autonomous Dominance
           - สร้าง Zero-Trust Architecture และโครงสร้างกำกับดูแล Autonomous AI Agents (AIBOM & Least-Agency)
        """.trimIndent()
      }
      prompt.contains("Secure AI") || prompt.contains("AI Agent") -> {
        """
        [DEFENSE ARCHITECTURE: SECURE AI AGENTS]
        
        กรอบการสร้างความมั่นคงปลอดภัยสำหรับ AI ยุคใหม่ (OWASP Top 10 for LLM & Agents):
        
        1. Input/Output Gateway Firewall:
           - กรอง Direct/Indirect Prompt Injection ด้วยโมเดลตรวจสอบคู่ขนาน (Dual LLM Inspection)
        
        2. Least-Agency & Ephemeral Credentials:
           - กำหนดให้ AI Agent ได้รับ Token ชั่วคราวที่มีอายุไม่เกิน 15 นาที และมีสิทธิ์เฉพาะฟังก์ชันที่จำเป็น
        
        3. Hardcoded Human Authorization Gate:
           - การกระทำที่มีผลลบข้อมูล โอนเงิน หรือเปลี่ยนคอนฟิกระบบเครือข่าย ต้องรอการอนุมัติ 2FA จากเจ้าหน้าที่ที่เป็นมนุษย์เสมอ
        """.trimIndent()
      }
      else -> {
        """
        [EXECUTIVE TELEMETRY SYNTHESIS]
        
        หลักการ Cyber Dominance สมัยใหม่ไม่ได้ขึ้นอยู่กับการมีเครื่องมือมากที่สุด แต่ขึ้นอยู่กับความเร็วในการประสานพลัง 4 ด้าน:
        1. Continuous Visibility -> มองเห็นทุกสิ่งที่มี IP และสิทธิ์
        2. Detection to Decision -> เปลี่ยนสัญญาณรบกวนให้เป็นการตัดสินใจในระดับนาที
        3. Controlled Automation -> ตอบโต้ภัยแบบอัตโนมัติด้วยความมั่นใจและปลอดภัย
        4. Secure AI Agents -> คุ้มครองและนำ AI มาเป็นแนวหน้าในการปกป้ององค์กร
        
        พร้อมตอบคำถามเชิงลึกเพิ่มเติมสำหรับ CISO และทีมวิศวกรรมความมั่นคงปลอดภัยครับ
        """.trimIndent()
      }
    }
  }
}
