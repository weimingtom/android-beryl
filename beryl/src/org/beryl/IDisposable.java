package org.beryl;

/** Contract for objects that hold unmanaged resources that must be deallocated manually.
 * Normally these classes have a finalizer method that is called as a back up.
 * But it is best practice to call the dispose() method when the resources are no longer needed.
 */
public interface IDisposable {
	/** Free unmanaged resources. */
	void dispose();
}
