package com.lambdatest.jenkins.freestyle.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "platformName",
    "brandName",
    "device"
})
public class AppAutomationCapabilityRequest {

    @JsonProperty("platformName")
    private String platformName;
    @JsonProperty("brandName")
    private String brandName;
    @JsonProperty("device")
    private String device;

    @JsonProperty("platformName")
    public String getPlatformName() {
        return platformName;
    }

    @JsonProperty("platformName")
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    @JsonProperty("brandName")
    public String getBrandName() {
        return brandName;
    }

    @JsonProperty("brandName")
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }    

    @JsonProperty("device")
    public String getDevice() {
        return device;
    }

    @JsonProperty("device")
    public void setDevice(String device) {
        this.device = device;
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{platformName=");
		builder.append(platformName);
		builder.append(", device=");
		builder.append(device);
		builder.append("}");
		return builder.toString();
	}

}