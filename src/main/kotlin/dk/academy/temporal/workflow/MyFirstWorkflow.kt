package dk.academy.temporal.workflow

import dk.academy.config.TemporalConfig
import io.temporal.spring.boot.WorkflowImpl
import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

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

