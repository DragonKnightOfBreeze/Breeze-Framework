// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain;

public class SystemProperties {
	private SystemProperties() {}

	public static final String workDirectory = System.getProperty("user.dir");
	public static final String workPlatform = System.getProperty("os.name");
	public static final String userName = System.getProperty("user.name");
	public static final String userCountry = System.getProperty("user.country");
	public static final String userLanguage = System.getProperty("user.language");
	public static final String fileSeparator = System.getProperty("file.separator");
	public static final String fileEncoding = System.getProperty("file.encoding");
	public static final String lineSeparator = System.getProperty("line.separator");
}
