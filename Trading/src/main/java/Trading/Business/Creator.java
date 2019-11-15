package Trading.Business;

public class Creator {

	/**
	 * 
	 * @param type
	 */
	public CFD factoryMethod(String type, Trader t) {

		return new Long(0 ,0 , 0, 0, 100, null, t);
	}

}