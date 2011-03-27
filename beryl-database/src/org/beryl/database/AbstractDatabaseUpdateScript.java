package org.beryl.database;

public abstract class AbstractDatabaseUpdateScript implements IDatabaseUpdateScript {

	protected final DatabaseUpdateParameters updateParameters;
	
	public AbstractDatabaseUpdateScript(DatabaseUpdateParameters params) {
		this.updateParameters = params;
	}
}
