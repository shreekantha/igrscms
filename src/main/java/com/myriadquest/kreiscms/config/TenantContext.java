package com.myriadquest.kreiscms.config;
/**
 * @author Shreekantha
 * @since 1.0
 */
public final class TenantContext {

	private static volatile ThreadLocal<String> currentTenant = new ThreadLocal<>();

	private TenantContext() {

	}

	public static void setCurrentTenant(String tenant) {
		currentTenant.set(tenant);
	}

	public static String getCurrentTenant() {
		return currentTenant.get();
	}

}
