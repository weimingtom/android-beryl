package org.beryl.database;

public interface IDatabaseUpdateScript {
	void onBeforeSchemaUpdate(DatabaseUpdateParameters params);
	void updateSchema(DatabaseUpdateParameters params);
	void onAfterSchemaUpdate(DatabaseUpdateParameters params);
}
