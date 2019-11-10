package Trading.Business;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.*;

public class LiveStock implements Subject {

	Map<String, Ativo> ativos;

	public List<Ativo> getMercado() {
		// TODO - implement LiveStock.getMercado
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Observer> getObservers() {
		return null;
	}

	@Override
	public void addObserver(Observer observer) {

	}

	@Override
	public void removeObserver(Observer observer) {

	}

	@Override
	public void notifyObservers() {

	}

	private JSONObject makeRequest(String id_ativo){
		try {
			URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + id_ativo + "&interval=1min&apikey=NUY2ZKFZVGL817XJ");
			String inline = "";

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			conn.connect();
			int responsecode = conn.getResponseCode();

			if (responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			else {
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				sc.close();

				JSONParser parse = new JSONParser();

				return (JSONObject) parse.parse(inline);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return new JSONObject();
	}


	private void parseData(Ativo a, JSONObject jobj){
		jobj = (JSONObject)jobj.get("Time Series (1min)");

		Collection c = jobj.values();
		jobj = (JSONObject) (c.toArray())[0];

		a.setPrecoVenda(Float.parseFloat(jobj.get("3. low").toString()));
		a.setPrecoCompra(Float.parseFloat(jobj.get("2. high").toString()));

		this.ativos.put(a.getID(), a);
	}

	public void refreshMarket(){
		for(Ativo a : this.ativos.values()){
			parseData(a, makeRequest(a.getID()));
			System.out.println(a.toString());
		}
	}


	public LiveStock(){
		/*
		DAO ativoDAO = new AtivoDAO();
		this.ativos = new HashMap<>();

		List<Ativo> ativos_list = ativoDAO.list();
		for(Ativo a : ativos_list) this.ativos.put(a.getID(), a); */

		this.ativos = new HashMap<>();

		Ativo a = new Ativo("FB", 0, 0);
		Ativo b = new Ativo("TSLA", 0, 0);

		this.ativos.put(a.getID(), a);
		this.ativos.put(b.getID(), b);
	}
}