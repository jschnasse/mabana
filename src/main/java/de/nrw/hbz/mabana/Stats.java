package de.nrw.hbz.mabana;

import java.util.HashMap;
import java.util.Map;

import org.culturegraph.mf.framework.DefaultStreamReceiver;

/**
 * Find the original code at the tests folder under lodmill-rd
 * http://github.com/lobid/lodmill
 * 
 * @author Fabian Steeg
 * 
 */
class Stats extends DefaultStreamReceiver {

    final Map<String, Integer> map = new HashMap<String, Integer>();

    @Override
    public void literal(final String name, final String value) {
	map.put(name, (map.containsKey(name) ? map.get(name) : 0) + 1);
    }
}
