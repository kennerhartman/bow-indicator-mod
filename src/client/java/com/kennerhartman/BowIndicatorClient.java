//
// Copyright (c) 2024 by Kenner Hartman. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for details.
//

package com.kennerhartman;

import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BowIndicatorClient implements ClientModInitializer {
	public static final String MOD_ID = "bow-indicator";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeClient() {
		LOGGER.info("Successfully loaded " + MOD_ID + " mod!");
	}
}