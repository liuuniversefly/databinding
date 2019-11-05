
<!-- 将插件中的配置参数与application.properties,application-${profiles.active}.properties中的配置进行绑定，两者不一致时打包失败 -->
<plugin>
	<groupId>com.lyf</groupId>
	<artifactId>databinding-maven-plugin</artifactId>
	<version>1.0</version>
	<configuration>
		<!-- 包含的配置文件，默认为 application.properties,application-${profiles.active}.properties-->
		<!--
		<includes>
			<include>application.properties</include>
			<include>application-${profiles.active}.properties</include>
		</includes>
		-->
		<bindings>
			<binding>
				<profile>dev</profile>
				<properties>
					<!-- 绑定参数 -->
					<spring.datasource.url>jdbc:mysql://localhost/database_1?useUnicode=true<![CDATA[&]]>characterEncoding=utf8<![CDATA[&]]>serverTimezone=GMT%2B8<![CDATA[&]]>useSSL=false</spring.datasource.url>
					<spring.datasource.username>root</spring.datasource.username>
					<spring.datasource.password>root</spring.datasource.password>
				</properties>
			</binding>
			<binding>
				<profile>pro</profile>
				<properties>
					<spring.datasource.url>jdbc:mysql://localhost/database_2?useUnicode=true<![CDATA[&]]>characterEncoding=utf8<![CDATA[&]]>serverTimezone=GMT%2B8<![CDATA[&]]>useSSL=false</spring.datasource.url>
					<spring.datasource.username>root</spring.datasource.username>
					<spring.datasource.password>root</spring.datasource.password>
				</properties>
			</binding>
			<!-- ... -->
		</bindings>
	</configuration>
	<executions>
		<execution>
			<phase>package</phase>
			<goals>
				<goal>databinding</goal>
			</goals>
		</execution>
	</executions>
</plugin>