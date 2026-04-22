package com.lambdatest.jenkins.freestyle.service;

/** Holds the tunnel Process handle and the info API port it was launched with. */
public class TunnelStartResult {
	private final Process process;
	private final int infoAPIPort;

	public TunnelStartResult(Process process, int infoAPIPort) {
		this.process = process;
		this.infoAPIPort = infoAPIPort;
	}

	public Process getProcess() {
		return process;
	}

	public int getInfoAPIPort() {
		return infoAPIPort;
	}
}
