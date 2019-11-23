package Trading.Business;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Scanner;

public class AlphaVantageAPI implements LiveAPI {


    @Override
    public float[] getPrecosAtivo(String id) {

        return parseData(makeRequest(id));
    }


    private JSONObject makeRequest(String id){
        try {
            URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + id + "&interval=1min&apikey=NUY2ZKFZVGL817XJ");
            String inline = "";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //System.out.println("GET - " + id);
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
               // System.out.println("TO PARSE - " + id);
                return (JSONObject) parse.parse(inline);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }


	/**
	 * 
	 * @param jobj
	 */
	private float[] parseData(JSONObject jobj) {
        jobj = (JSONObject)jobj.get("Time Series (1min)");

        Collection c = jobj.values();

        jobj = (JSONObject) (c.toArray())[0];

        //System.out.println(jobj);

        float r[] = new float[2];
        r[0] = Float.parseFloat(jobj.get("3. low").toString());
        r[1] = Float.parseFloat(jobj.get("2. high").toString());

        return r;
	}


}