package org.beryl.content;

public class SchemaField {
	public final String Name;
	public final String Type;
	public final boolean AllowNulls;
	public final String DefaultValue;
	
	public SchemaField(String name, String type) {
		Name = name;
		Type = type;
		AllowNulls = true;
		DefaultValue = null;
	}
	
	public SchemaField(String name, String type, boolean allowNulls, String defaultValue) {
		Name = name;
		Type = type;
		AllowNulls = allowNulls;
		DefaultValue = defaultValue;
	}
	
	public String toFieldSQL()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(Name);
		sb.append(" ");
		sb.append(Type);
		if(! AllowNulls)
		{
			sb.append(" NOT NULL");
		}
		
		if(DefaultValue != null)
		{
			sb.append(" DEFAULT \"");
			sb.append(DefaultValue);
			sb.append("\"");
		}
		
		return sb.toString();
	}
}
