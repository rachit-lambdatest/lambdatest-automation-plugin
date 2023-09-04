package com.lambdatest.jenkins.freestyle.api.service;

public class AppAutomationVersionKey {

    private String platformName;
	private String deviceName;

	public AppAutomationVersionKey(String platformName, String deviceName) {
		super();
		this.platformName = platformName;
		this.deviceName = deviceName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((platformName == null) ? 0 : platformName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppAutomationVersionKey other = (AppAutomationVersionKey) obj;
		if (deviceName == null) {
			if (other.deviceName != null)
				return false;
		} else if (!deviceName.equals(other.deviceName))
			return false;
		if (platformName == null) {
			if (other.platformName != null)
				return false;
		} else if (!platformName.equals(other.platformName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\nplatformName=");
		builder.append(platformName);
		builder.append(",\ndeviceName=");
		builder.append(deviceName);
		builder.append("\n}");
		return builder.toString();
	}

}