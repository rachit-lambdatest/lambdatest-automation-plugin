package com.lambdatest.jenkins.pipeline;

import com.lambdatest.jenkins.pipeline.report.ReportBuildAction;


import com.lambdatest.jenkins.pipeline.Constant;
import com.lambdatest.jenkins.pipeline.enums.ProjectType;


import hudson.EnvVars;
import hudson.model.Run;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.SynchronousNonBlockingStepExecution;

import java.io.PrintStream;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.Map;
import hudson.model.AbstractBuild;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.tasks.BuildWrapper;
import java.io.IOException;
import hudson.FilePath;
import hudson.Launcher;
import hudson.FilePath;



import hudson.model.ItemGroup;

import hudson.util.Secret;
import net.sf.json.JSONObject;


public class LambdaTestReportStepExecution extends SynchronousNonBlockingStepExecution {
    private final static Logger logger = Logger.getLogger(LambdaTestReportStepExecution.class.getName());

    private final ProjectType product;


    public LambdaTestReportStepExecution(StepContext context, final ProjectType product) {
        super(context);
        this.product = product;
    }
    

    @Override
    protected Void run() throws Exception {
        Run<?, ?> run = getContext().get(Run.class);
        TaskListener taskListener = getContext().get(TaskListener.class);
        final EnvVars parentContextEnvVars = getContext().get(EnvVars.class);
        
        final EnvVars parentEnvs = run.getEnvironment(taskListener);
        
        String buildName = parentContextEnvVars.get(Constant.LT_BUILD_NAME);
        
        final String username = parentContextEnvVars.get(Constant.LT_USERNAME);
        final String accessKey = parentContextEnvVars.get(Constant.LT_ACCESS_KEY);

        logger.info("buildName : " + buildName);
        
        final ReportBuildAction ltReportAction =
                new ReportBuildAction(run, username ,accessKey, buildName, product);
        
        ltReportAction.generateLambdaTestReport();
        
        run.addAction(ltReportAction);
        return null;

    }
}
