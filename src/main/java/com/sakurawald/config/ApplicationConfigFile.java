package com.sakurawald.config;

import com.sakurawald.ui.App;

public class ApplicationConfigFile extends ConfigFile<ApplicationConfigData> {

	public ApplicationConfigFile() {
		super(App.getApplicationConfigsPath(),
				"ApplicationConfig.json", ApplicationConfigData.class);
	}
}

