package com.lyf.databinding.entity;

import java.util.Map;

/**
 * @author lyf
 * @version 1.0
 * @Date 2019年10月24日 下午3:29:51
 * @copyright (c) 长沙亿朋信息科技有限公司
 */
public class Binding {
	private String profile;
	private Map<String, String> properties;

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}


}
