package com.lambdastack.go.executors;

import com.lambdastack.go.core.DependencyResolver;
import com.thoughtworks.go.plugin.api.response.execution.ExecutionResult;
import com.thoughtworks.go.plugin.api.task.TaskConfig;
import com.thoughtworks.go.plugin.api.task.TaskExecutionContext;
import com.thoughtworks.go.plugin.api.task.TaskExecutor;


public class ArtifactsDownloaderExecutor implements TaskExecutor {
    @Override
    public ExecutionResult execute(TaskConfig taskConfig, TaskExecutionContext taskExecutionContext) {
        try {
            this.getDependencyResolver(taskExecutionContext).resolveDependencies().fetchArtifacts();
            return ExecutionResult.success("Artifacts downloaded successfully");
        }
        catch (Exception e) {
            if(taskExecutionContext!=null) {
                taskExecutionContext.console().printLine(stackTraceToString(e));
            }
            return ExecutionResult.failure(e.getMessage(), e);
        }
    }

    protected DependencyResolver getDependencyResolver(TaskExecutionContext taskExecutionContext) {
        return new DependencyResolver(taskExecutionContext);
    }

    public String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}

