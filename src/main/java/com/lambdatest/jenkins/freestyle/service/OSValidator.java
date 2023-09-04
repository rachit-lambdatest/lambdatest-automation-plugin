package com.lambdatest.jenkins.freestyle.service;
import java.util.logging.Logger;
public class OSValidator {

	private static String OS = System.getProperty("os.name").toLowerCase();
	private static final Logger logger = Logger.getLogger(OSValidator.class.getName());

	public static void main(String[] args) {
		logger.info(OS);
		if (isWindows()) {
			logger.info("This is Windows");
		} else if (isMac()) {
			logger.info("This is Mac");
		} else if (isUnix()) {
			logger.info("This is Unix or Linux");
		} else if (isSolaris()) {
			logger.info("This is Solaris");
		} else if (isAndroid()) {
			logger.info("This is Android");
		} else if (isIOS()) {
			logger.info("This is iOS");
		} 
		else {
			logger.info("Your OS is not support!!");
		}
	}

	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0);
	}

	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
	}

	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}
	public static boolean isAndroid() {
		return (OS.indexOf("android") >= 0);
	}

	public static boolean isIOS() {
		return (OS.indexOf("ios") >= 0);
	}


}
