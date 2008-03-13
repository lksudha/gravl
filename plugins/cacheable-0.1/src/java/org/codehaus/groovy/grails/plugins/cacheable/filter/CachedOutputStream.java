/**
 * (c) 2007 Syrics Software, Dipl.-Inf. (FH) Sven Schomaker, Softwareentwicklung
 *
 */
package org.codehaus.groovy.grails.plugins.cacheable.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;

public class CachedOutputStream extends ServletOutputStream {
	ByteArrayOutputStream cached;
	ServletOutputStream output;
	
	public CachedOutputStream(ServletOutputStream output, ByteArrayOutputStream cached) {
		this.output = output;
		this.cached = cached;
	}
	
	@Override
	public void write(int b) throws IOException {
		output.write(b);
		cached.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		output.write(b, off, len);
		cached.write(b, off, len);
	}

	@Override
	public void write(byte[] b) throws IOException {
		output.write(b);
		cached.write(b);
	}

	@Override
	public void close() throws IOException {
		output.close();
		cached.close();
		
	}

	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub
		output.flush();
		cached.flush();
	}
}
