package com.lyf.databinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.lyf.databinding.entity.Binding;

/**
 * 数据绑定插件 goal:databinding
 * 
 * @author lyf
 * @version 1.0
 * @Date 2019年10月24日 上午10:55:54
 * @copyright (c) 长沙亿朋信息科技有限公司
 */
@Mojo(name = "databinding", defaultPhase = LifecyclePhase.PACKAGE)
public class DataBindingMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project.build.outputDirectory}")
	private String outputDirectory;
	@Parameter(defaultValue = "${profiles.active}")
	private String profile;
	@Parameter
	private List<String> includes;
	@Parameter
	private List<Binding> bindings;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		if (profile == null) {
			getLog().info("未激活profile环境");
			return;
		}
		if (bindings == null) {
			getLog().info("未进行数据绑定");
			return;
		}
		Properties properties = loadProperties();
		for (Binding binding : bindings) {
			if (profile.equals(binding.getProfile())) {
				getLog().info("匹配profile环境：" + profile);
				Map<String, String> properties2 = binding.getProperties();
				for (Map.Entry<String, String> entry : properties2.entrySet()) {
					if (!Objects.equals(properties.get(entry.getKey()), entry.getValue())) {
						getLog().error("数据绑定失败：" + properties.get(entry.getKey()) + " - " + entry.getValue());
						throw new MojoFailureException("数据绑定失败");
					}
				}
				getLog().info("数据绑定成功");
				return;
			}
		}
		getLog().error("数据绑定失败，未匹配到profile环境：" + profile);
		throw new MojoFailureException("数据绑定失败");
	}

	/**
	 * 获取包含的配置文件列表，默认为application.properties,application-${profiles.active}.properties
	 * 
	 * @return
	 */
	private List<String> getLoadFiles() {
		List<String> loadFiles = new ArrayList<>();
		if (includes == null || includes.size() == 0) {
			loadFiles.add("application.properties");
			loadFiles.add("application-" + profile + ".properties");
		} else {
			loadFiles.addAll(includes);
		}
		return loadFiles;
	}
	
	/**
	 * 加载配置文件
	 * @return
	 */
	private Properties loadProperties() {
		Properties properties = new Properties();
		for (String name : getLoadFiles()) {
			File file = new File(outputDirectory, name);
			getLog().info("加载配置文件：" + file.getAbsolutePath());
			if (file.exists()) {
				try {
					properties.load(new FileInputStream(file));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

}
