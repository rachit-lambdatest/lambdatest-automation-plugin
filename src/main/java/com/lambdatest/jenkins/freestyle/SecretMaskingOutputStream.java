package com.lambdatest.jenkins.freestyle;

import hudson.console.LineTransformationOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Wraps a build's console OutputStream so that any occurrence of the
 * provided secret is replaced with {@code ****} before reaching the log.
 */
class SecretMaskingOutputStream extends LineTransformationOutputStream {

	private static final String MASK = "****";

	private final OutputStream out;
	private final String secret;

	SecretMaskingOutputStream(OutputStream out, String secret) {
		this.out = out;
		this.secret = secret;
	}

	@Override
	protected void eol(byte[] b, int len) throws IOException {
		if (secret == null || secret.isEmpty()) {
			out.write(b, 0, len);
			return;
		}
		String line = new String(b, 0, len, StandardCharsets.UTF_8);
		String redacted = line.replace(secret, MASK);
		out.write(redacted.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public void flush() throws IOException {
		super.flush();
		out.flush();
	}

	@Override
	public void close() throws IOException {
		super.close();
		out.close();
	}
}
