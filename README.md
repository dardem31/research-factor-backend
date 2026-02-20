# ResearchFactor — Backend

A backend service for a crowdfunded, publicly transparent platform for conducting
independent Randomized Controlled Trials (RCTs) on pharmaceuticals and supplements.

The platform creates an immutable, step-by-step record of the research process —
enabling donors to fund and monitor studies in real time while PhD reviewers ensure
methodological integrity.

---

## Stack

- **Kotlin** + **Spring Boot 3.x**
- **Spring WebFlux** + **Kotlin Coroutines** — fully non-blocking end-to-end
- **Hibernate Reactive** — coroutine-based database access
- **PostgreSQL 16+**
- **JWT** + **Spring Security** — authentication and RBAC
- **Liquibase** — database migrations
- **S3-compatible storage** — artifact files

---

## Roles

`RESEARCHER` `LEAD_PHD` `SHADOW_PHD` `DONOR` `SUBJECT`

---

## Domain Entities

### Research
Top-level container for a scientific study.
- `id`, `title`, `hypothesis`, `description`
- `status`: `DRAFT | PENDING_REVIEW | PUBLISHED | ACTIVE | COMPLETED`
- `blindingType`: `OPEN | SINGLE_BLIND | DOUBLE_BLIND | BLINDED_ANALYSIS`
- `ethicsApprovalDocument`: Artifact
- `protocol`: StudyProtocol
- `primaryOutcomes`: PrimaryOutcome[]
- `subjectGroups`: SubjectGroup[]
- `trackedParameters`: TrackedParameter[]
- `lines`: ResearchLine[]
- `report`: ResearchReport

### StudyProtocol
Methodological specification. Primary object of PhD review.
- `primaryOutcome`, `sampleSizeJustification`, `statisticalMethod`
- `randomizationMethod`, `blindingDetails`, `interventionDescription`
- `inclusionCriteria`, `exclusionCriteria`, `earlyStoppingCriteria`

### PrimaryOutcome
Pre-defined research-level question. Immutable once approved.
- `text`
- `status`: `DRAFT | APPROVED`

### SubjectGroup
A named group that subjects are randomized into.
- `label`, `description`
- `subjects`: Subject[]

### Subject
A registered participant.
- `code` — platform-assigned anonymous code (e.g. `SUB-0042`)
- `status`: `ACTIVE | WITHDRAWN | EXCLUDED`
- `withdrawalReason`, `withdrawalDate`
- `group`: SubjectGroup — assigned by platform randomization, seed recorded in audit trail
- `parameterFields`: ParameterField[]

### TrackedParameter
A measurable metric monitored across all subjects throughout the research.
- `name`, `unit`
- `referenceMin`, `referenceMax` — optional reference range

### ParameterField
Current value of a single TrackedParameter for a specific Subject.
- `parameter`: TrackedParameter
- `currentValue`: Decimal
- `updatedAt`: Timestamp — backend-generated

### ResearchLine
A major phase of the study. Executed sequentially.
- `sequenceOrder`, `title`, `duration`
- `status`: `LOCKED | ACTIVE | COMPLETED`
- `plannedStartDate`, `plannedEndDate`, `actualStartDate`, `actualEndDate`
- `stageQuestions`: StageQuestion[]
- `tasks`: ResearchTask[]
- `objective`: Objective

### StageQuestion
A pre-defined question this phase must answer. Immutable once approved.
- `text`
- `status`: `DRAFT | APPROVED`

### ResearchTask
A granular unit of lab work within a phase.
- `title`, `description`
- `status`: `OPEN | SUBMITTED`
- `targetGroups`: SubjectGroup[] — optional
- `logEntries`: LogEntry[]
- `artifacts`: Artifact[]

### LogEntry
An immutable timestamped note within a task. Supports @mention of subjects.
- `text` — supports @mention of subject codes
- `subjectUpdates`: SubjectUpdate[]
- `artifacts`: Artifact[]
- `createdAt`: Timestamp — backend-generated, immutable

### SubjectUpdate
A snapshot of parameter changes for a subject captured at the moment of @mention.
- `subject`: Subject
- `parameterChanges`: ParameterChange[]

### ParameterChange
A single parameter value recorded as part of a SubjectUpdate.
- `parameter`: TrackedParameter
- `previousValue`, `newValue`: Decimal

### AdverseEvent
An unwanted medical occurrence reported during the study.
- `subject`: Subject — optional
- `description`, `actionTaken`
- `severity`: `MILD | MODERATE | SEVERE | LIFE_THREATENING`
- `occurredAt`: LocalDate
- `relatedToIntervention`: Boolean
- `reportedAt`: Timestamp — backend-generated

### Objective
Submitted by the researcher to close a ResearchLine. Triggers PhD review.
- `summary`, `narrative` (Markdown)
- `stageQuestionAnswers`: StageQuestionAnswer[]
- `protocolDeviations`, `adverseEvents` — required, explicit "None" if not applicable
- `nextPhaseRecommendation` — optional
- `status`: `PENDING | FULFILLED | FAILED`
- `submittedAt`: Timestamp — backend-generated
- `review`: ObjectiveReview

### StageQuestionAnswer
Researcher's answer to a StageQuestion within an Objective.
- `stageQuestion`: StageQuestion
- `answer`: Markdown

### ObjectiveReview
PhD assessment of a submitted Objective.
- `reviewer`: User
- `verdict`: `APPROVED | REJECTED | REVISION_REQUESTED`
- `comment`: Markdown
- `createdAt`: Timestamp — backend-generated

### ResearchReport
Submitted after all ResearchLines are completed. Triggers final PhD review.
- `summary`, `narrative` (Markdown)
- `primaryOutcomeAnswers`: PrimaryOutcomeAnswer[]
- `protocolDeviations`, `adverseEvents` — cumulative
- `status`: `PENDING | FULFILLED | FAILED`
- `submittedAt`: Timestamp — backend-generated
- `review`: ResearchReportReview

### PrimaryOutcomeAnswer
Researcher's answer to a PrimaryOutcome within the ResearchReport.
- `primaryOutcome`: PrimaryOutcome
- `answer`: Markdown

### ResearchReportReview
PhD final assessment of the completed research.
- `reviewer`: User
- `verdict`: `APPROVED | REJECTED | REVISION_REQUESTED`
- `comment`: Markdown
- `createdAt`: Timestamp — backend-generated

### Artifact
An evidence file attached to a task or log entry.
- `type`: `RAW_DATA | PHOTO | CODE | CONFIG | ETHICS_APPROVAL | LAB_RESULT`
- `storageUrl`, `sha256`
- `metadata`: Map<String, String> — device IDs, environment specs, etc.

### AuditEntry
Immutable record of every state-changing operation.
- `researchId`, `actorId`, `actorRole`
- `action` — e.g. `OBJECTIVE_APPROVED`, `RANDOMIZATION_SEED_RECORDED`, `BLINDING_LIFTED`
- `payload` — JSON snapshot of relevant state at the time of action
- `createdAt`: Timestamp — backend-generated