package com.lambdatest.jenkins.freestyle.api.service;

public class AppAutomationDeviceKey {

    private String platformName;
	private String brandName;

	public AppAutomationDeviceKey(String platformName, String brandName) {
		super();
		this.platformName = platformName;
		this.brandName= brandName;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brandName == null) ? 0 : brandName.hashCode());
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
		AppAutomationDeviceKey other = (AppAutomationDeviceKey) obj;
		if (brandName == null) {
			if (other.brandName != null)
				return false;
		} else if (!brandName.equals(other.brandName))
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
		builder.append(",\nbrandName=");
		builder.append(brandName);
		builder.append("\n}");
		return builder.toString();
	}

}