package com.lambdatest.jenkins.freestyle.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "appAutomationCapabilityRequest"
})
public class AppCapabilityRequest {

    @JsonProperty("appAutomationCapabilityRequest")
    private List<AppAutomationCapabilityRequest> appAutomationCapabilityRequest = null;

    @JsonProperty("appAutomationCapabilityRequest")
    public List<AppAutomationCapabilityRequest> getAppAutomationCapabilityRequest() {
        return appAutomationCapabilityRequest;
    }

    @JsonProperty("appAutomationCapabilityRequest")
    public void setAppAutomationCapabilityRequest(List<AppAutomationCapabilityRequest> appAutomationCapabilityRequest) {
        this.appAutomationCapabilityRequest = appAutomationCapabilityRequest;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{appAutomationCapabilityRequest=");
		builder.append(appAutomationCapabilityRequest);
		builder.append("}");
		return builder.toString();
	}

}