package com.lambdatest.jenkins.freestyle.report;

import hudson.model.Action;
import hudson.model.Run;

import com.lambdatest.jenkins.freestyle.api.Constant;

public class AbstractAppAutomationReportBuildAction implements Action{
    private Run<?, ?> build;

    @Override
    public String getIconFileName() {
        return Constant.LT_ICON_FILE_NAME;
    }

    @Override
    public String getDisplayName() {
        return Constant.LT_APP_AUTOMATION_REPORT_DISPLAY_NAME;
    }

    @Override
    public String getUrlName() {
        return Constant.LT_REPORT_URL;
    }

    public Run<?, ?> getBuild() {
        return build;
    }

    public void setBuild(Run<?, ?> build) {
        this.build = build;
    }
}