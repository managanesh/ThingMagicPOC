package com.veda.cache;

public class CrunchifyInMemoryCacheTest {

	private void crunchifyTestAddRemoveObjects() {

		// Test with timeToLiveInSeconds = 200 seconds
		// timerIntervalInSeconds = 500 seconds
		// maxItems = 6
		CrunchifyInMemoryCache<String, String> cache = new CrunchifyInMemoryCache<String, String>(200, 500, 10);

		cache.put("eBay", "eBay");
		cache.put("Paypal", "Paypal");
		cache.put("Google", "Google");
		cache.put("Microsoft", "Microsoft");
		cache.put("IBM", "IBM");
		cache.put("Facebook", "Facebook");

		System.out.println("6 Cache Object Added.. cache.size(): " + cache.size());
		cache.remove("IBM");
		System.out.println("One object removed.. cache.size(): " + cache.size());

		cache.put("Twitter", "Twitter");
		cache.put("SAP", "SAP");
		cache.put("SAPp", "SAPp");
		cache.put("SAPpp", "SAPpp");
		
		System.out.println("Two objects Added but reached maxItems.. cache.size(): " + cache.size());

	}

	public static void main(String[] args) {
		CrunchifyInMemoryCacheTest crunchifyCache = new CrunchifyInMemoryCacheTest();
		System.out.println("\n\n==========Test1: crunchifyTestAddRemoveObjects ==========");
		crunchifyCache.crunchifyTestAddRemoveObjects();


	}

}
