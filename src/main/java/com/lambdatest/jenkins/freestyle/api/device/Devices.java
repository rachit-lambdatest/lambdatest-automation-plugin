package com.lambdatest.jenkins.freestyle.api.device;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ 
    "brand",
    "devices"
})
public class Devices {

    @JsonProperty("brand")
	private String brand;
	@JsonProperty("devices")
	private List<Device> devices = null;

    @JsonProperty("brand")
	public String getBrandName() {
		return brand;
	}

    @JsonProperty("brand")
	public void setBrandName(String brand) {
		this.brand = brand;
	}

	@JsonProperty("devices")
	public List<Device> getDevices() {
		return devices;
	}

	@JsonProperty("devices")
	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
}