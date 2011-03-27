package org.beryl.database;

import java.util.LinkedList;

class DatabaseUpdateRunner implements Runnable {

	private final LinkedList<Runnable> updateScripts = new LinkedList<Runnable>();
	
	public DatabaseUpdateRunner() {
	}
	
	void add(Runnable script) {
		updateScripts.add(script);
	}
	
	public void run() {
		while(! updateScripts.isEmpty()) {
			Runnable script = updateScripts.remove();
			script.run();
		}
	}

}
