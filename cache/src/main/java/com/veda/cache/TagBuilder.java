package com.veda.cache;

public class TagBuilder {
	CrunchifyInMemoryCache<String, String> cache = new CrunchifyInMemoryCache<String, String>(200, 500, 10);
	private void tagReader(int i){
		Integer.toString(i);
		cache.put(Integer.toString(i), Integer.toString(i));
	}

	public static void main(String[] args) {
		for(int i=0;i<1000;i++){
			TagBuilder tbldr = new TagBuilder();
			tbldr.tagReader(i);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
