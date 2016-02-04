package com.hmc.onegoodday;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class OgdRandom extends Random {

	private static final long serialVersionUID = -6493392162048018779L;

	private OgdRandom() {
	}

	private static OgdRandom instance;

	public static OgdRandom instance() {
		if (null == instance) {
			instance = new OgdRandom();
		}

		return instance;
	}

	public boolean toss(int chance) {
		return nextInt(100) < chance;
	}

	public <T> List<T> getSubset(List<T> list, int count) {
		if (list.size() < count) {
			throw new IllegalArgumentException("Count must be greater than or equal to list size");
		}

		if (list.size() == count) {
			return new ArrayList<T>(list);
		}

		Set<Integer> indexes = new HashSet<Integer>();
		while (indexes.size() != count) {
			indexes.add(nextInt(list.size()));
		}

		List<T> results = new ArrayList<T>(count);
		for (Integer index : indexes) {
			results.add(list.get(index));
		}

		return results;
	}

}
