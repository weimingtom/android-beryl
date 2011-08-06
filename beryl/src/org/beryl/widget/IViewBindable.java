package org.beryl.widget;

import org.beryl.diagnostics.ILogWriter;

interface IViewBindable {
	void setLogger(ILogWriter logWriter);
	void bindViews();
}
