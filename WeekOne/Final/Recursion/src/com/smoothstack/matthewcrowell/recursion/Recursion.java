package com.smoothstack.matthewcrowell.recursion;

import java.util.List;

/**
 * Class to recursively determine if a subgroup of an
 * array can be assembled with a sum equal to a
 * specified target number.
 */
public class Recursion {

	/**
	 * Function to recurse through a list of integers
	 * to determine if the sum can be reached with the
	 * caveat that and adjacent identical numbers must
	 * either be used collectively or not at all.
	 *
	 * @param begin Integer, index of list to start at
	 * @param list  List<Integer>, list of integers
	 * @param sum   Integer, target value
	 * @return Boolean, whether or not sum is possible
	 */
	public Boolean groupSumClump(Integer begin, List<Integer> list, Integer sum) {
		if (sum == 0) {
			return true;
		}

		if (begin >= list.size() - 1) {
			return sum - list.get(begin) == 0;
		}

		Cluster cluster = new Cluster();
		cluster.setValue(list.get(begin));
		int i = begin;
		while (list.get(i).equals(cluster.getValue())) {
			if (i >= list.size()) {
				break;
			}

			cluster.setCount(cluster.getCount() + 1);
			i++;
		}

		return groupSumClump(begin + cluster.getCount(), list,
				sum - cluster.getTotal()) || groupSumClump(begin + cluster.getCount(), list, sum);
	}
}


/**
 * Class to track a cluster of Integers.
 */
class Cluster {
	private Integer count;
	private Integer value;

	/**
	 * Default constructor, sets count to 0.
	 */
	public Cluster() {
		this.count = 0;
	}

	/**
	 * Getter for count.
	 *
	 * @return Integer, size of cluster
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * Setter for count.
	 *
	 * @param count, Integer size of cluster
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * Getter for value.
	 *
	 * @return Integer, value of cluster
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Setter for value.
	 *
	 * @param value Integer, value of cluster
	 */
	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * Getter for total of count times value
	 *
	 * @return Integer, count * value
	 */
	public Integer getTotal() {
		return count * value;
	}
}