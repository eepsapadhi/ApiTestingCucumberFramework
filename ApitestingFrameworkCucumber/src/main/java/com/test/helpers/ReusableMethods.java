package com.test.helpers;

import java.util.HashMap;

public class ReusableMethods {

	public static HashMap<String, String> getHeaderForAuth(String token) {
		HashMap<String, String> hm = new HashMap();
		hm.put("token", token);
		return hm;
	}

}
