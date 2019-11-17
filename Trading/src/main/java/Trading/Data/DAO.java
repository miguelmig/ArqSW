package Trading.Data;

import java.util.List;

public interface DAO<K, T> {

	/**
	 * 
	 * @param k
	 * @param o
	 */
	void put(K k, T o);

	/**
	 * 
	 * @param k
	 */
	T get(K k);

	List<T> list();

	/**
	 * 
	 * @param k
	 */
	void remove(K k);


	int size();

}