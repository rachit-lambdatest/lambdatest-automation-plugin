package com.lambdatest.jenkins.pipeline.report;

import hudson.model.Action;
import hudson.model.Run;

import com.lambdatest.jenkins.pipeline.Constant;

public class AbstractReportBuildAction implements Action{
    private Run<?, ?> build;

    @Override
    public String getIconFileName() {
        return Constant.LT_ICON_FILE_NAME;
    }

    @Override
    public String getDisplayName() {
        return Constant.LT_REPORT_DISPLAY_NAME;
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
