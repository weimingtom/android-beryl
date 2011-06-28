package org.beryl.util;

public class Memory {

	public static final long KB_IN_BYTES = 1024;
	public static final long MB_IN_BYTES = 1024 * KB_IN_BYTES;
	public static final long GB_IN_BYTES = 1024 * MB_IN_BYTES;
	public static final long TB_IN_BYTES = 1024 * GB_IN_BYTES;
	
	public static final long FOUR_MB_IN_BYTES = 4 * MB_IN_BYTES;
	
	public static long getReasonableMemoryCushion() {
		return FOUR_MB_IN_BYTES;
	}
	
	public static long bytesToKilobytes(long bytes) {
		return bytes / KB_IN_BYTES;
	}
	
	public static long bytesToMegabytes(long bytes) {
		return bytes / MB_IN_BYTES;
	}
	
	public static long bytesToGigabytes(long bytes) {
		return bytes / GB_IN_BYTES;
	}
	
	public static long bytesToTerabytes(long bytes) {
		return bytes / TB_IN_BYTES;
	}
	
	public static long freeMemory() {
		final Runtime rt = Runtime.getRuntime();
		return rt.freeMemory();
	}
	
	public static long memoryLimit() {
		final Runtime rt = Runtime.getRuntime();
		return rt.maxMemory();
	}
	
	public static double consumptionRatio() {
		return freeMemory() / (double)memoryLimit();
	}
	
}
