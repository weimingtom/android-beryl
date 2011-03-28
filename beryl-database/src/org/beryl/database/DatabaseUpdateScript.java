package org.beryl.database;

import org.beryl.diagnostics.Log;

public abstract class DatabaseUpdateScript implements IDatabaseUpdateScript {

	public void updateSchema(DatabaseUpdateParameters params) {
		String sqlScript = getSchemaSqlScript();
		boolean scriptSuccess = true;
		if(sqlScript != null) {
			scriptSuccess = executeScript(params, sqlScript);
		}
		if(! scriptSuccess) {
			params.log.e(sqlScript + " did not run successfully.");
		}
		
		onUpdateSchema(params);
	}
	
	protected boolean executeScript(DatabaseUpdateParameters params, String sqlScriptAssetFilePath) {
		boolean success = false;
		final Log log = params.log;
		log.d(String.format("Running sql script: %s", sqlScriptAssetFilePath));
		success = DatabaseUtils.runSqlScriptFromAsset(params.context, params.db, sqlScriptAssetFilePath);
		return success;
	}
	
	public abstract void onUpdateSchema(DatabaseUpdateParameters params);
	protected abstract String getSchemaSqlScript();
}
