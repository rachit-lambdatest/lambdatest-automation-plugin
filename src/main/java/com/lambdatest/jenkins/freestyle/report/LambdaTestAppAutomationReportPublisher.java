package com.lambdatest.jenkins.freestyle.report;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import jenkins.tasks.SimpleBuildStep;
import hudson.tasks.Recorder;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.logging.Logger;

import com.lambdatest.jenkins.freestyle.api.Constant;

public class LambdaTestAppAutomationReportPublisher extends Recorder implements SimpleBuildStep {

    private final static Logger logger = Logger.getLogger(LambdaTestAppAutomationReportPublisher.class.getName());

    @DataBoundConstructor
    public LambdaTestAppAutomationReportPublisher() {
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    @Override
    public void perform(@Nonnull Run<?, ?> build, @Nonnull FilePath workspace, @Nonnull Launcher launcher, @Nonnull TaskListener listener) throws InterruptedException, IOException {
        final EnvVars parentEnvs = build.getEnvironment(listener);
        final String username = parentEnvs.get(Constant.LT_USERNAME);
        final String accessKey = parentEnvs.get(Constant.LT_ACCESS_KEY);
        final String buildName = parentEnvs.get(Constant.LT_BUILD_NAME);

        logger.info("Generating LambdaTest App Automation Report" +  "\n" + "buildName : " + buildName);
        AppAutomationReportBuildAction ltReportAction = new AppAutomationReportBuildAction(build, username, accessKey, buildName);
        ltReportAction.generateLambdaTestAppAutomationReport();
        build.addAction(ltReportAction);
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher> {

        @Override
        @SuppressWarnings("rawtypes")
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            // indicates that this builder can be used with all kinds of project types
            return true;
        }

        /**
         * This human readable name is used in the configuration screen.
         */
        @Override
        public String getDisplayName() {
            return Constant.LT_APP_AUTOMATION_REPORT_DISPLAY_NAME;
        }
    }

}