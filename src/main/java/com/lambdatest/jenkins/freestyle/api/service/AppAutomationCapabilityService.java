package com.lambdatest.jenkins.freestyle.api.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.collections.MapUtils;
import org.json.JSONObject;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdatest.jenkins.freestyle.api.Constant;
import com.lambdatest.jenkins.freestyle.api.device.Devices;
import com.lambdatest.jenkins.freestyle.api.device.Device;
import com.lambdatest.jenkins.freestyle.api.device.DeviceVersion;
import com.lambdatest.jenkins.freestyle.api.operatingsystem.OsList;

public class AppAutomationCapabilityService {

    private final static Logger logger = Logger.getLogger(AppAutomationCapabilityService.class.getName());

	public static Map<String, String> supportedPlatforms = new LinkedHashMap<>();
	public static Set<String> supportedBrands;
	public static Set<String> supportedDevices;
	public static Map<String, Set<String>> allBrandNames = new LinkedHashMap<>();
	public static Map<AppAutomationDeviceKey, List<Device>> allDeviceNames = new LinkedHashMap<>();
	public static Map<AppAutomationVersionKey, List<DeviceVersion>> allDeviceVersions = new LinkedHashMap<>();
	public static Set<String> supportedDeviceVersions;

    public static Map<String, String> getPlatformNames() {
		try {
			logger.info("getOS Triggered");
			if (MapUtils.isEmpty(supportedPlatforms)) {
				supportedPlatforms = new LinkedHashMap<>();
				String jsonResponse = CapabilityService.sendGetRequest(Constant.APP_AUTOMATION_OS_API_URL);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				OsList osList = objectMapper.readValue(jsonResponse, OsList.class);
				parseAppAutomationSupportedOs(osList);
			}
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}
		return supportedPlatforms;
	}

    private static void parseAppAutomationSupportedOs(OsList osList) {
		if (osList != null && osList.getOs() != null) {
			osList.getOs().forEach(os -> {
				AppAutomationCapabilityService.supportedPlatforms.put(os.getId(), os.getName());
			});
		}
	}

	public static Set<String> getBrandNames(String platformName) {
		try {
			// logger.info("getBrandNames Triggered");
			if (allBrandNames.containsKey(platformName)) {
				logger.info("Supported Brand List Exists for " + platformName);
				return allBrandNames.get(platformName);
			}
			supportedBrands = new LinkedHashSet<String>();
			String deviceApiURL = Constant.DEVICE_API_URL;
			String jsonResponse = CapabilityService.sendGetRequest(deviceApiURL);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            JSONObject jsonObj = new JSONObject(jsonResponse);
            String jsonResponseOs = jsonObj.getJSONArray(platformName).toString();
			List<Devices> devices = objectMapper.readValue(jsonResponseOs, new TypeReference<List<Devices>>() {});
			parseSupportedBrandsAndDevices(devices, platformName);
		} catch (Exception e) {
			logger.warning(e.getMessage());
		}
		return supportedBrands;
	}

    private static Set<String> parseSupportedBrandsAndDevices(List<Devices> devices, String platformName) {
		if (!CollectionUtils.isEmpty(devices)) {
			devices.forEach(devs -> {
				List<Device> realDevices = new ArrayList<Device>();
				Set<String> supportedDevice = new LinkedHashSet<String>();
				devs.getDevices().forEach(dev ->  {
					if (dev.getDeviceType().equals("real")) {
						supportedDevice.add(dev.getDeviceName());
						realDevices.add(dev);
						AppAutomationVersionKey avk = new AppAutomationVersionKey(platformName, dev.getDeviceName());
						if (!CollectionUtils.isEmpty(dev.getVersions())) {
							allDeviceVersions.put(avk, dev.getVersions());
						}
					}
				});
				if (!realDevices.isEmpty()) {
					supportedBrands.add(devs.getBrandName());
					AppAutomationDeviceKey adk = new AppAutomationDeviceKey(platformName, devs.getBrandName());
					if (!CollectionUtils.isEmpty(realDevices)) {
						allDeviceNames.put(adk, realDevices);
					}
				}
			});
			allBrandNames.put(platformName, supportedBrands);
		}
		return supportedBrands;
	}

	public static Set<String> getDeviceNames(String platformName, String brandName) {
		supportedDevices = new LinkedHashSet<String>();
		AppAutomationDeviceKey adk = new AppAutomationDeviceKey(platformName, brandName);
		if (allDeviceNames.containsKey(adk)) {
			logger.info("Supported Device List Exists for " + brandName);
			allDeviceNames.get(adk).forEach(dn -> {
				supportedDevices.add(dn.getDeviceName());
			});
		} else {
			logger.info(adk + " not found");
		}
		return supportedDevices;
	}

    public static Set<String> getDeviceVersions(String platformName, String deviceName) {
		supportedDeviceVersions = new LinkedHashSet<String>();
		AppAutomationVersionKey avk = new AppAutomationVersionKey(platformName, deviceName);
		if (allDeviceVersions.containsKey(avk)) {
			logger.info("Supported Device Versions List Exists for " + platformName + ":" + deviceName);
			allDeviceVersions.get(avk).forEach(dv -> {
				supportedDeviceVersions.add(dv.getVersion());
			});
		} else {
			logger.info(avk + " not found");
		}
		return supportedDeviceVersions;
	}

	public static String appAutomationBuildHubURL(String username, String accessToken, String type) {
		try {
			StringBuilder sb = new StringBuilder("https://");
			sb.append(username).append(":").append(accessToken);
			if (Constant.STAGE.equals(type)) {
				sb.append(Constant.Stage.APP_AUTOMATION_HUB_URL);
			} else {
				sb.append(Constant.APP_AUTOMATION_HUB_URL);
			}
			return sb.toString();
		} catch (Exception e) {
			return Constant.NOT_AVAILABLE;
		}
	}

    public static void main(String[] args) throws Exception {
		System.out.println(getPlatformNames());
		System.out.println(getBrandNames("android"));
		System.out.println(getDeviceNames("android", "Asus"));
		System.out.println(getDeviceVersions("android", "Zenfone 6"));
		System.out.println(allDeviceVersions);
	}

}