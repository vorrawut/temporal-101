# Loan Processing Use Case

## Overview

This training uses a **Loan Processing System** as the primary use case to demonstrate Temporal workflow patterns. The system is split into two main parts that naturally showcase different Temporal capabilities:

1. **Onboarding & Disbursement** - Sequential workflows demonstrating workflow orchestration
2. **Repayments** - Parallel workflows demonstrating concurrent processing patterns

## Part 1: Onboarding & Disbursement (Sequential)

### Business Flow
A complete loan lifecycle from application to funds disbursement, involving multiple sequential stages with human approvals and external service integrations.

### Workflow Stages

#### 1. Loan Application
- Customer data collection and validation
- Document upload (ID, income proof, bank statements)
- Initial eligibility checks
- Application scoring and categorization

#### 2. Loan Underwriting  
- Credit bureau checks and credit score analysis
- Income verification with employer/bank APIs
- Risk assessment and affordability calculations
- Fraud detection and compliance checks

#### 3. Loan Approval & Contract Generation
- Final approval decision (automated or manual)
- Loan terms calculation (interest rate, tenure, EMI)
- Legal contract generation and digital signing
- Internal approvals for high-value loans

#### 4. Loan Disbursement
- Bank account verification
- Fund transfer initiation 
- Payment confirmation and reconciliation
- Disbursement notification to customer

#### 5. Post-Disbursement Setup
- Repayment schedule creation
- Auto-debit mandate setup
- Account activation and customer onboarding
- Welcome communications and next steps

### Temporal Patterns Demonstrated
- **Hand-off Workflows**: Each stage hands off to the next
- **Long-running Processes**: Days/weeks between stages
- **Human Approvals**: Manual intervention points with timeouts
- **External Service Calls**: Credit bureaus, banks, document services
- **Error Handling**: Stage-specific retry and compensation logic
- **Durable Timers**: SLA monitoring and reminder workflows

## Part 2: Repayments (Parallel)

### Business Flow
Processing different types of repayment scenarios simultaneously, each with its own business logic and processing requirements.

### Repayment Types

#### 1. Normal Repayment
- Scheduled monthly EMI processing
- Auto-debit from customer accounts
- Payment confirmation and allocation
- Account balance updates

#### 2. Early Termination
- Full loan payoff calculations
- Interest rebate computations
- Closure documentation generation
- Account closure and final settlement

#### 3. Partial Repayment
- Extra principal payment processing
- EMI schedule recalculation
- Updated payment schedule generation
- Customer notification of changes

#### 4. Late Payment Processing
- Overdue payment identification
- Penalty and interest calculations
- Reminder notifications (SMS, email, calls)
- Collection workflow initiation

#### 5. Refinancing/Restructuring
- Loan modification requests
- New terms negotiation and approval
- Documentation updates
- Schedule restructuring

### Temporal Patterns Demonstrated
- **Fan-out/Fan-in**: Processing multiple payment types concurrently
- **Child Workflows**: Independent lifecycle for each repayment type
- **Task Queues**: Routing different complexities to appropriate workers
- **Parallel Activities**: Simultaneous processing within each type
- **Saga Patterns**: Compensation for failed payment scenarios
- **Worker Scaling**: Different workers for different repayment complexities

## Training Lab Mapping

### Day One Labs (Local Temporal)
**Lab 1: Hello Workflow**
- Simple loan application submission with basic validation

### Day Two Labs (Centralized Temporal)
**Lab 1: Child Workflows**
- Loan disbursement orchestrating underwriting, approval, and disbursement workflows

**Lab 2: Worker Versioning** 
- Updating loan processing logic without breaking existing applications

**Lab 3: Fan-Out/Fan-In**
- Processing multiple repayment types simultaneously for different customers

**Lab 4: Saga Patterns**
- Handling failed disbursements with compensation workflows

**Lab 5: Observability**
- Monitoring loan processing metrics and performance

## Why This Use Case Works

### Realistic & Relatable
- Most engineer understands loan processing
- Real-world complexity without domain expertise requirements
- Clear business logic that maps to technical patterns

### Sequential + Parallel Nature
- Disbursement naturally demonstrates workflow orchestration
- Repayments naturally demonstrate parallel processing
- Both scenarios are common in financial services

### Rich Error Scenarios
- External service failures (credit bureaus, banks)
- Human approval timeouts and escalations
- Payment failures and retry mechanisms
- Regulatory compliance requirements

### Scalability Demonstration
- 70 engineers can process different loan applications
- Centralized server handles concurrent processing
- Task queues demonstrate routing and scaling patterns

## Data Strategy

### Lab Configuration
- Each engineer gets unique customer IDs (ENG001-ENG070)
- Loan amounts vary to trigger different approval workflows
- Different risk profiles to demonstrate various processing paths
- Shared centralized Temporal server for realistic multi-tenant experience

### Simulation Elements
- Mock external services (credit bureau, bank APIs)
- Configurable delays and failure rates
- Human approval simulation with timeouts
- Real-time metrics and monitoring dashboards 