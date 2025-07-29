package com.impendingmoon.starrylytra;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StarrylytraClient implements ClientModInitializer {
	public static final String MOD_ID = "starrylytra";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Starrylytra loaded!");	}
}