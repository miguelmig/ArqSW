package Trading.Data;

import java.util.List;

public interface DAO<K, T> {

	void put(K k, T o);

	T get(K k);

	List<T> list();

	void remove(K k);

	int size();

}