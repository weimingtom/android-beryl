package org.beryl.database;

import org.beryl.diagnostics.Log;

class DatabaseUpdateScriptRunner implements Runnable {

	private final IDatabaseUpdateScript script;
	private final DatabaseUpdateParameters params;
	
	public DatabaseUpdateScriptRunner(DatabaseUpdateParameters params, IDatabaseUpdateScript script) {
		this.params = params;
		this.script = script;
	}
	
	public void run() {
		final Log logger = params.log;
		logger.d("onBeforeSchemaUpdate");
		script.onBeforeSchemaUpdate(params);
		logger.d("updateSchema");
		script.updateSchema(params);
		logger.d("onAfterSchemaUpdate");
		script.onAfterSchemaUpdate(params);
	}

}
