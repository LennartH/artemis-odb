package com.artemis.compile;

import com.artemis.Component;
import com.artemis.io.SaveFileFormat;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;


/**
 * Resolver for {@link com.artemis.EntityTransmuter transmuters} as defined
 * in the json representing {@link SaveFileFormat#archetypes}.
 */
public class TransmuterStore {
	public final IntMap<List<Class<? extends Component>>> transmuters = new IntMap<>();

	public TransmuterStore(GlobalComponentContext components, JsonValue jsonValue) {
		jsonValue = jsonValue.get("archetypes");
		System.out.println(jsonValue);
		JsonValue entry = jsonValue.child;
		do {
			int archetypeId = Integer.parseInt(entry.name());
			JsonValue componentIt = entry.child;

			transmuters.put(archetypeId, resolveTypes(components, componentIt));
		} while ((entry = entry.next) != null);
	}

	private static List<Class<? extends Component>> resolveTypes(GlobalComponentContext components,
	                                                             JsonValue component) {

		List<Class<? extends Component>> types = new ArrayList<>();
		do {
			types.add(components.get(component.asString()));
		} while ((component = component.next) != null);

		return types;
	}


}
