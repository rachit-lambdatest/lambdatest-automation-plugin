package com.lambdatest.jenkins.pipeline;

import com.lambdatest.jenkins.pipeline.Constant;
import com.lambdatest.jenkins.pipeline.enums.ProjectType;
import com.google.common.collect.ImmutableSet;
import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContext;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import java.util.Set;

public class LambdaTestReportStep extends Step {
    public ProjectType project;
    public String product;

    @DataBoundConstructor
    public LambdaTestReportStep(String product) {
        if (Constant.APP_AUTOMATION.equalsIgnoreCase(product)) {
            this.project = ProjectType.APP_AUTOMATION;
            this.product = Constant.APP_AUTOMATION;
        } else {
            this.project = ProjectType.AUTOMATION;
            this.product = Constant.AUTOMATION;
        }
    }

    

    @Override
    public StepExecution start(StepContext stepContext) throws Exception {
        return new LambdaTestReportStepExecution(stepContext, project);
    }

    @Extension
    public static class DescriptorImpl extends StepDescriptor {

        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return ImmutableSet.of(Run.class, TaskListener.class);
        }

        @Override
        public String getFunctionName() {
            return Constant.LT_REPORT_PIPELINE_FUNCTION;
        }

        @Override
        public String getDisplayName() {
            return Constant.LT_REPORT_DISPLAY_NAME;
        }

        public ListBoxModel doFillProductItems() {
            ListBoxModel items = new ListBoxModel();
            items.add("Automation", Constant.AUTOMATION);
            items.add("App Automation", Constant.APP_AUTOMATION);
            return items;
        }

        public FormValidation doCheckProduct(@QueryParameter String product) {
            return FormValidation.ok();
        }
    }
}
