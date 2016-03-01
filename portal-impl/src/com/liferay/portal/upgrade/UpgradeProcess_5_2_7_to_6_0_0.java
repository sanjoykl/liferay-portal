/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.upgrade;

import com.liferay.counter.service.persistence.impl.CounterClassNameThreadLocal;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.upgrade.v5_2_7_to_6_0_0.UpgradeSchema;
import com.liferay.portal.upgrade.v6_0_0.UpgradeAsset;
import com.liferay.portal.upgrade.v6_0_0.UpgradeAssetPublisher;
import com.liferay.portal.upgrade.v6_0_0.UpgradeBlogs;
import com.liferay.portal.upgrade.v6_0_0.UpgradeDocumentLibrary;
import com.liferay.portal.upgrade.v6_0_0.UpgradeExpando;
import com.liferay.portal.upgrade.v6_0_0.UpgradeJournal;
import com.liferay.portal.upgrade.v6_0_0.UpgradePolls;
import com.liferay.portal.upgrade.v6_0_0.UpgradePortletId;
import com.liferay.portal.upgrade.v6_0_0.UpgradeResourceAction;
import com.liferay.portal.upgrade.v6_0_0.UpgradeShopping;

/**
 * @author Douglas Wong
 */
public class UpgradeProcess_5_2_7_to_6_0_0 extends UpgradeProcess {

	@Override
	public int getThreshold() {
		return ReleaseInfo.RELEASE_6_0_0_BUILD_NUMBER;
	}

	@Override
	protected void doUpgrade() throws Exception {
		CounterClassNameThreadLocal.setCounterClassName(
			"com.liferay.counter.model.Counter");

		upgrade(UpgradeSchema.class);

		upgrade(UpgradeAsset.class);
		upgrade(UpgradeAssetPublisher.class);
		upgrade(UpgradeBlogs.class);
		upgrade(UpgradeDocumentLibrary.class);
		upgrade(UpgradeExpando.class);
		upgrade(UpgradeJournal.class);
		upgrade(UpgradePolls.class);
		upgrade(UpgradePortletId.class);
		upgrade(UpgradeResourceAction.class);
		upgrade(UpgradeShopping.class);

		clearIndexesCache();

		CounterClassNameThreadLocal.setCounterClassName(
			"com.liferay.counter.kernel.model.Counter");
	}

}