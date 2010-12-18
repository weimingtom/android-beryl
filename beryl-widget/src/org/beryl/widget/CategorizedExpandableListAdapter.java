package org.beryl.widget;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListAdapter;

public class CategorizedExpandableListAdapter extends BaseExpandableListAdapter
{
	// TODO This implementation is not complete and needs to be optimized.
	
	private final ArrayList<String> _headers = new ArrayList<String>();
	private final ArrayList<ListAdapter> _adapters = new ArrayList<ListAdapter>();
	private boolean _hasStableIds = true;
	public void clear()
	{
		_headers.clear();
		_adapters.clear();
		_hasStableIds = true;
	}
	
	public void addGroup(String groupName, ListAdapter groupAdapter)
	{
		_headers.add(groupName);
		_adapters.add(groupAdapter);
		_hasStableIds &= groupAdapter.hasStableIds();
	}
	
	public Object getChild(int groupPosition, int childPosition)
	{
		return getChildObject(groupPosition, childPosition);
	}

	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		ListAdapter adapter = getAdapter(groupPosition);
		
		if(adapter != null)
		{
			return adapter.getView(childPosition, convertView, parent);
		}
		
		return null;
	}

	public int getChildrenCount(int groupPosition)
	{
		if(_adapters.size() > groupPosition)
		{
			return _adapters.get(groupPosition).getCount();
		}
		
		return 0;
	}

	// Not an overriden method.
	public Object getChildObject(int groupPosition, int childPosition)
	{
		ListAdapter adapter = getAdapter(groupPosition);
		
		if(adapter != null && adapter.getCount() > childPosition)
		{
			return adapter.getItem(childPosition);
		}
		
		return null;
	}
	
	// Not an overriden method.
	public ListAdapter getAdapter(int groupPosition)
	{
		if(_adapters.size() > groupPosition)
		{
			return _adapters.get(groupPosition);
		}
		
		return null;
	}
	
	public Object getGroup(int groupPosition)
	{
		return getAdapter(groupPosition);
	}

	public int getGroupCount()
	{
		return _headers.size();
	}

	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasStableIds()
	{
		return _hasStableIds;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		boolean result = false;
	
		ListAdapter adapter = this.getAdapter(groupPosition);
		if(adapter != null)
		{
			if(adapter.getCount() > childPosition)
			{
				result = adapter.isEnabled(childPosition);
			}
		}
		
		return result;
	}
}
