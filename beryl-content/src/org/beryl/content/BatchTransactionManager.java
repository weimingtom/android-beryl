package org.beryl.content;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.os.RemoteException;

public class BatchTransactionManager
{
	private final ContentResolver _resolver;
	private final ArrayList<ContentProviderOperation> _pendingOperations = new ArrayList<ContentProviderOperation>();
	private int _batchSize = 0;
	private final String _authority;
	
	public BatchTransactionManager(ContentResolver resolver, String authority)
	{
		_resolver = resolver;
		_authority = authority;
	}
	
	public int size()
	{
		return _pendingOperations.size();
	}
	
	public void setBatchSize(int batch_size)
	{
		_batchSize = batch_size;
	}
	
	public void add(ContentProviderOperation operation)
	{
		_pendingOperations.add(operation);
		if(_batchSize > 0 && _pendingOperations.size() > _batchSize)
		{
			commitTransaction();
		}
	}
	
	public void commitTransaction()
	{
		if(_pendingOperations.size() > 0)
		{
			try
			{
				_resolver.applyBatch(_authority, _pendingOperations);
			}
			catch(final OperationApplicationException oae)
			{
				
			}
			catch(final RemoteException re)
			{
				
			}
			
			_pendingOperations.clear();
		}
	}
}
