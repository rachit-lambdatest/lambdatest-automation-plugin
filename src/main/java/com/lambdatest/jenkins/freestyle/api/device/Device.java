package com.lambdatest.jenkins.freestyle.api.device;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ 
    "deviceName", 
	"deviceType",
    "osVersion" 
})
public class Device {

	@JsonProperty("deviceName")
	private String deviceName;
	@JsonProperty("deviceType")
	private String deviceType;
	@JsonProperty("osVersion")
	private List<DeviceVersion> deviceVersions = null;

    @JsonProperty("deviceName")
	public String getDeviceName() {
		return deviceName;
	}

    @JsonProperty("deviceName")
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@JsonProperty("deviceType")
	public String getDeviceType() {
		return deviceType;
	}

    @JsonProperty("deviceType")
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	@JsonProperty("osVersion")
	public List<DeviceVersion> getVersions() {
		return deviceVersions;
	}

	@JsonProperty("osVersion")
	public void setVersions(List<DeviceVersion> deviceVersions) {
		this.deviceVersions = deviceVersions;
	}
}