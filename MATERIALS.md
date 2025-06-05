* Create our first Temporal Workflow
```kotlin
@WorkflowInterface
interface MyFirstWorkflow {
    
    @WorkflowMethod
    fun start(name: String): String
}

@WorkflowImpl(taskQueues = [TemporalConfig.DEFAULT_TASK_QUEUE])
class MyFirstWorkflowImpl : MyFirstWorkflow {

    override fun start(name: String): String {
        return "Hello, $name!"
    }
}
```

* Temporal Workflow lifecycle
   * Starting our Workflow
```kotlin
fun startGreeting(name: String) {
    val workflowId = "MyGreeting-$name"
    try {
        val workflow = workflowClient
            .newWorkflowStub<MyFirstWorkflow> {
                setWorkflowId(workflowId)
                setTaskQueue(TemporalConfig.DEFAULT_TASK_QUEUE)
            }

        WorkflowClient.start { workflow.start(name) }
    } catch (e: Exception) {
        logger.error(e) { "Failed to start MyFirstWorkflow for Request(name=$name)" }
        throw e
    }
}
```

   * Terminating our Workflow
```kotlin
fun terminate(workflowId: String) {
    try {
        workflowClient
            .newUntypedWorkflowStub(workflowId)
            .terminate("User requested termination at ${Instant.now()}")
    } catch (e: Exception) {
        logger.error(e) { "Failed to terminate Workflow(id=$workflowId)" }
    }
}
```

   * Describing our Workflow
```kotlin
fun describe(workflowId: String): String {
    return try {
        val exec = WorkflowExecution.newBuilder()
            .setWorkflowId(workflowId)
            .build()

        val request = DescribeWorkflowExecutionRequest.newBuilder()
            .setNamespace(workflowClient.options.namespace)
            .setExecution(exec)
            .build()

        val description = workflowClient.workflowServiceStubs.blockingStub()
            .describeWorkflowExecution(request)
            .workflowExecutionInfo.toString()
            .replace("\\n", "\n")
            .replace("\n", "\n  ")

        return "{\n  ${description}\n}"
    } catch (e: Exception) {
        logger.error(e) { "Failed to describe Workflow(id=$workflowId)" }
        "Workflow(id=$workflowId) failed to be described"
    }
}
```

   * Testing our Temporal Workflow
```kotlin
class MyFirstWorkflowTest {
    
    @JvmField
    @RegisterExtension
    val testWorkflowExtension: TestWorkflowExtension = TestWorkflowExtension
      .newBuilder()
      .registerWorkflowImplementationTypes(/*...*/)
      .setActivityImplementations(/*...*/)
      .setWorkflowClientOptions(
          WorkflowClientOptions {
              setDataConverters(TemporalConfig.dataConverter(objectMapper))
          }
      )
      .build()
  
    @Test
    fun `Given something - When something - Then something`(
        environment: TestWorkflowEnvironment, // <- already injected
        worker: Worker,                       // <- already injected
        workflow: MyFirstWorkflow,            // <- already injected
    ) {
        // Given
        val name = "Jonathan"
        
        // When
        val actual = workflow.start(name)
        
        // Then
        assertEquals("Hello, $name!", actual)
    }
}
```

* Create our first Temporal Activity
* Using Temporal Activity from our Temporal Workflow
* Testing our Temporal Activity
