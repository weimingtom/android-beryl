package org.beryl.database;

import org.beryl.diagnostics.Log;

class DatabaseUpdateLoader {

	private final DatabaseUpdateRunner runner;
	private final DatabaseUpdateParameters updateParams;
	
	public DatabaseUpdateLoader(DatabaseUpdateRunner runner, DatabaseUpdateParameters params) {
		this.runner = runner;
		this.updateParams = params;
	}

	public void queueScriptFromClassName(String className) {
		try {
			ClassLoader loader = this.getClass().getClassLoader();
			Class<?> scriptClass = loader.loadClass(className);
			// TODO: Do some runtime validation on the Class<? extends IDatabaseUpdateScript>
			queueScriptFromClass((Class<? extends IDatabaseUpdateScript>)scriptClass);
		} catch (Exception e) {
		}
	}
	
	public void queueScriptFromClass(Class<? extends IDatabaseUpdateScript> createScriptClass) {
		IDatabaseUpdateScript script;
		final Log log = updateParams.log;
		final String scriptName = createScriptClass.getName();
		try {
			log.i("UpdateLoader: Loading " + scriptName);
			script = createScriptClass.newInstance();
			DatabaseUpdateScriptRunner scriptRunner = new DatabaseUpdateScriptRunner(updateParams, script);
			runner.add(scriptRunner);
		} catch (Exception e) {
			log.e("UpdateLoader: Loading Update Script: " + scriptName + " failed.");
			log.e(e);
		}
	}

	public void queueScriptFromClassNameTemplate(String updateScriptClassNameTemplate, int oldVersion, int newVersion) {
		for(int i = oldVersion + 1; i <= newVersion; i++) {
			String className = String.format(updateScriptClassNameTemplate, i);
			queueScriptFromClassName(className);
		}
	}
}
