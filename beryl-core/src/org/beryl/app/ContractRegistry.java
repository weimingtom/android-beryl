package org.beryl.app;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Registry of objects categorized by their interfaces that extend {@link org.beryl.app.RegisterableContract}.
 * Objects stored in the registry can be retrieved by users to be called against.
 * 
 * 
 * This class is mainly used for decoupling {@link android.app.Fragment}s from their parent activities as well as enabling communication between Fragments.
 * 
 * 
 * The example below shows how a fragment can talk to another fragment without any communication to the Activity.
 * 
<pre class="code"><code class="java">

public class MainActivity extends FragmentActivity implements IContractMediator {
	final private ContractRegistry contracts = new ContractRegistry(this);
	public ContractRegistry getContractRegistry() {
		return contracts;
	}
}

public interface IDoWorkCommand extends RegisterableContract {
	void doWork();
}

public class WorkerFragment extends Fragment {

	ContractRegistry contractSource = null;
	
	// Standard boilerplate code for attaching/detaching the Contract members of code.
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		contractSource = ContractRegistry.getContractRegistry(activity);
		contractSource.attachMembers(this);
	}

	public void onDetach() {
		super.onDetach();
		contractSource.detachMembers(this);
		contractSource = null;
	}
	
	final IDoWorkCommand fragmentISavePosterCommand = new IDoWorkCommand() {
		public void doWork() {
			// Do something.
		}
	};
}

public class RequesterFragment extends Fragment {

	// Standard boilerplate code for attaching/detaching the Contract members of code.
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		contractSource = ContractRegistry.getContractRegistry(activity);
		contractSource.attachMembers(this);
	}

	public void onDetach() {
		super.onDetach();
		contractSource.detachMembers(this);
		contractSource = null;
	}

	public void requestWork() {
		final ArrayList<IDoWorkCommand> listeners = contractSource.getAll(IDoWorkCommand);
		for(IDoWorkCommand cmd : cmds) {
			cmd.doWork();
		}
	}
}
</code></pre>*/
public class ContractRegistry {

	private final HashMap<Class<?>, ArrayList<?>> contractsContainer = new HashMap<Class<?>, ArrayList<?>>();
	
	public ContractRegistry() {
	}
	
	/** Constructs a new contract registry and attaches all ContractRegisterable fields from the class passed in. */
	public ContractRegistry(Object parent) {
		attachMembers(parent);
	}
	
	@SuppressWarnings("unchecked")
	public void attach(Object object) {
		Class<?> clazz = object.getClass();
		Class<?> [] ifaces = clazz.getInterfaces();
		for(Class<?> iface : ifaces) {
			if(isRegisterableContractInterface(iface)) {
				ArrayList/* <LOL> */ contacts = getAll(iface);
				contacts.add(object);
			}
		}
	}
	
	private boolean isRegisterableContractInterface(Class<?> clazz) {
		return hasInterface(clazz, RegisterableContract.class);
	}
	
	private boolean hasInterface(Class<?> target, Class<?> iface) {
		return iface.isAssignableFrom(target);
	}
	
	/** Unregisters an object from the manager. */
	public void detach(Object object) {
		for(Class<?> key : contractsContainer.keySet()) {
			final ArrayList<?> contractList = contractsContainer.get(key);
			contractList.remove(object);
		}
	}
	
	public <T> T get(Class<T> clazz) {
		final ArrayList<T> elements = getAll(clazz);
		
		if(! elements.isEmpty()) {
			return elements.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> getAll(Class<T> clazz) {
		ArrayList<T> results = (ArrayList<T>)contractsContainer.get(clazz);
		
		if(results == null) {
			results = createContractList(clazz);
		}
		
		return results;
	}
	
	private <T> ArrayList<T> createContractList(Class<T> clazz) {
		final ArrayList<T> results = new ArrayList<T>();
		contractsContainer.put(clazz, results);
		return results;
	}

	public void attachMembers(Object obj) {
		final ArrayList<Field> fields = getContractFields(obj);
		for(Field field : fields) {
			try {
				Object contractMember = field.get(obj);
				if(contractMember != null) {
					attach(contractMember);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void detachMembers(Object obj) {
		final ArrayList<Field> fields = getContractFields(obj);
		for(Field field : fields) {
			try {
				detach(field.get(obj));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private ArrayList<Field> getContractFields(Object obj) {
		ArrayList<Field> result = new ArrayList<Field>();
		Class<?> clazz = obj.getClass();
		Field [] fields = clazz.getDeclaredFields();
		
		for(Field field : fields) {
			field.setAccessible(true);
			Class<?> fieldType = field.getType();

			if(isRegisterableContractInterface(fieldType)) {
				result.add(field);
			}
		}
		
		return result;
	}
	
	public static ContractRegistry getContractRegistry(Object maybeContractMediator) {
		ContractRegistry registry = null;
		try {
			registry = ((IContractMediator)maybeContractMediator).getContractRegistry();
		} catch(ClassCastException e) {
			throw new ClassCastException("maybeContractMediator must implement interface, " + IContractMediator.class.getName());
		}
		
		return registry;
	}
}
