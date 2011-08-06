package org.beryl.widget;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CategorizedListAdapter extends BaseAdapter implements OnItemClickListener
{
	private final static int ITEM_ID_HEADER = 0;
	private final ArrayList<String> _headers = new ArrayList<String>();
	private final ArrayList<ListAdapter> _adapters = new ArrayList<ListAdapter>();
	private final Context _context;
	private final CategorizedListAdapterDataSetObserver _observer = new CategorizedListAdapterDataSetObserver();
	private int _viewTypeIdCounter = 1;
	private final HashMap<String, AdapterDescriptor> _adapterDescriptors = new HashMap<String, AdapterDescriptor>(10);
	
	public CategorizedListAdapter(Context context)
	{
		_context = context;
	}

	public void addSection(String header, ListAdapter adapter)
	{
		_headers.add(header);
		_adapters.add(adapter);
		adapter.registerDataSetObserver(_observer);
		addAdapterToDescriptorList(adapter);
	}

	public void clear()
	{
		int i;
		int sect_size = _adapters.size();
		for(i = 0; i < sect_size; i++)
		{
			_adapters.get(i).unregisterDataSetObserver(_observer);
		}
		
		_adapters.clear();
		_headers.clear();
		_viewTypeIdCounter = 1;
		_adapterDescriptors.clear();
	}
	
	public ListAdapter getAdapter(String header)
	{
		header = header.toLowerCase();
		int sect_len = _headers.size();
		int i;
		for(i = 0; i < sect_len; i++)
		{
			if(_headers.get(i).toLowerCase().compareTo(header) == 0)
			{
				return _adapters.get(i);
			}
		}
		return null;
	}
	
	public ListAdapter removeAdapter(String header)
	{
		header = header.toLowerCase();
		int sect_len = _headers.size();
		int i;
		for(i = 0; i < sect_len; i++)
		{
			if(_headers.get(i).toLowerCase().compareTo(header) == 0)
			{
				ListAdapter retval = _adapters.get(i);
				_adapters.remove(i);
				_headers.remove(i);
				return retval;
			}
		}
		return null;
	}
	@Override
	public boolean isEmpty()
	{
		int sect_size = _adapters.size();
		for(int i = 0; i < sect_size; i++)
		{
			if(_adapters.get(i).getCount() > 0)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public int getCount()
	{
		int result = 0;
		int sect_len;
		int sect_size = _adapters.size();
		for(int i = 0; i < sect_size; i++)
		{
			sect_len = _adapters.get(i).getCount();
			if(sect_len != 0)
			{
				result += sect_len + 1;
			}
		}
		
		return result;
	}

	public ListAdapter getAdapterFromPosition(int position)
	{
		PositionSpec ps = getInternalPosition(position);
		return ps.adapter;
	}
	
	public Object getItem(int position)
	{
		PositionSpec ps = getInternalPosition(position);
		if(ps.adapter == null)
		{
			return _headers.get(ps.position);
		}
		else
		{
			return ps.adapter.getItem(ps.position);
		}
	}

	public long getItemId(int position)
	{
		return position;
	}
	
	@Override
	public int getItemViewType(int position)
	{
		PositionSpec ps = getInternalPosition(position);
		if(ps.adapter == null)
		{
			return ITEM_ID_HEADER;
		}
		else
		{
			return getInternalViewTypeId(ps.adapter, ps.position);
		}
	}
	
	private int getInternalViewTypeId(ListAdapter adapter, int position)
	{
		int result;
		int adapter_viewtypeid = adapter.getItemViewType(position);
		result = _adapterDescriptors.get(adapter.getClass().getName()).viewTypeIdBase + adapter_viewtypeid;
		
		return result;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		PositionSpec ps = getInternalPosition(position);
		
		// If this is for a position that actually categorized then...
		if(ps.adapter != null)
		{
			convertView = ps.adapter.getView(ps.position, convertView, parent);
		}
		else
		{
			// This view is a category header.
			 
			Context context = this._context;
			ViewHolder holder = null;
			if(convertView == null)
			{
				convertView = new TextView(context, null, android.R.attr.listSeparatorTextViewStyle);
				holder = new ViewHolder();
				holder.caption = (TextView)convertView;
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
				
			}
			
			holder.caption.setText(_headers.get(ps.position));
		}
		return convertView;
	}
	
	private static class ViewHolder
	{
		TextView caption;
	}
	
	private class CategorizedListAdapterDataSetObserver extends DataSetObserver
	{
		@Override
		public void onChanged()
		{
			CategorizedListAdapter.this.notifyDataSetChanged();
		}
		
		@Override
		public void onInvalidated()
		{
			CategorizedListAdapter.this.notifyDataSetInvalidated();
		}
	}
	
	
	private static class AdapterDescriptor
	{
		int numViewTypes;
		ArrayList<ListAdapter> adapters = new ArrayList<ListAdapter>();
		int viewTypeIdBase;
	}
	private void addAdapterToDescriptorList(ListAdapter adapter)
	{
		String classname;
		AdapterDescriptor descriptor;
		
		classname = adapter.getClass().getName();
		descriptor = _adapterDescriptors.get(classname);
		if(descriptor != null)
		{
			descriptor.adapters.add(adapter);
		}
		else
		{
			descriptor = new AdapterDescriptor();
			descriptor.numViewTypes = adapter.getViewTypeCount();
			descriptor.adapters.add(adapter);
			descriptor.viewTypeIdBase = this._viewTypeIdCounter;
			
			// Increment the internal view type counter.
			this._viewTypeIdCounter += descriptor.numViewTypes;
			
			// Register the class type.
			_adapterDescriptors.put(classname, descriptor);
		}
	}
	
	@Override
	public int getViewTypeCount()
	{
		int result = 1;
		for(Object key : _adapterDescriptors.keySet())
		{
			result += _adapterDescriptors.get(key).numViewTypes;
		}
		
		return result;
	}
	
	@Override
	public boolean areAllItemsEnabled()
	{
		return false;
	}
	
	@Override
	public boolean isEnabled(int position)
	{
		PositionSpec ps = getInternalPosition(position);
		if(ps == null)
		{
			return false;
		}
		else if(ps.adapter == null)
		{
			return false;
		}
		else
		{
			return ps.adapter.isEnabled(ps.position);
		}
	}
	
	protected PositionSpec getInternalPosition(final int position)
	{
		int cpos = position;
		int sect_index = 0;
		PositionSpec result = null;
		ListAdapter c_adapter;
		int sect_len;
		int sect_size = _adapters.size();
		
		while(sect_index < sect_size)
		{
			c_adapter = _adapters.get(sect_index);
			sect_len = c_adapter.getCount();
			
			// Sections with size of 0 are ignored.
			if(sect_len > 0)
			{
				// If this is a header.
				if(cpos == 0)
				{
					result = new PositionSpec();
					result.adapter = null;
					result.position = sect_index;
					return result;
				}
				else
				{
					cpos--;
					if(cpos < sect_len)
					{
						result = new PositionSpec();
						result.adapter = c_adapter;
						result.position = cpos;
						return result;
					}
					cpos -= sect_len;
				}
			}
			sect_index++;
		}

		return result;
	}

	private static class PositionSpec
	{
		ListAdapter adapter;
		int position;
	}
	
	public void onItemClick(AdapterView<?> parent, View v, int position, long id)
	{
		PositionSpec pos_spec = getInternalPosition(position);
		if(pos_spec != null)
		{
			if(pos_spec.adapter != null)
			{
				if(pos_spec.adapter instanceof OnClickListener)
				{
					((OnClickListener)pos_spec.adapter).onClick(v);
				}
			}
		}
	}
}
