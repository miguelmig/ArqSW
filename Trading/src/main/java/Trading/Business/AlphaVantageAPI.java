package Trading.Business;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Long;

public class AlphaVantageAPI implements LiveAPI {


    @Override
    public float[] getPrecosAtivo(String id) {

        return parseData(makeRequest(id));
    }


    private JSONObject makeRequest(String id){
        try {
            URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + id + "&interval=1min&apikey=NUY2ZKFZVGL817XJ");
            String inline = "";

            URLConnection conn = url.openConnection();

            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                inline += sc.nextLine();
            }
            sc.close();

            JSONParser parse = new JSONParser();
            JSONObject json = (JSONObject) parse.parse(inline);

            if(json.get("Error Message") != null) {
                url = new URL("https://www.alphavantage.co/query?function=DIGITAL_CURRENCY_DAILY&symbol=" + id + "&market=EUR&apikey=NUY2ZKFZVGL817XJ");

                conn = url.openConnection();
                inline = "";
                sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inline += sc.nextLine();
                }
                sc.close();

                parse = new JSONParser();
                return (JSONObject) parse.parse(inline);
            }
            return json;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return new JSONObject();
    }


	private float[] parseData(JSONObject jobj) {
        float r[] = new float[2];
        JSONObject json;
        if((json = (JSONObject)jobj.get("Time Series (1min)")) != null){
            Collection c = json.values();

            json = (JSONObject) (c.toArray())[0];

            r[0] = Float.parseFloat(json.get("1. open").toString());
            r[1] = r[0]*1.01f;
        }
        else if ((json = (JSONObject)jobj.get("Time Series (Digital Currency Daily)")) != null){
            Set<String> keys = json.keySet();

            Date now = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String closest_date = Collections.min(keys, new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    try {
                        long diff1 = Math.abs(df.parse(o1).getTime() - now.getTime());
                        long diff2 = Math.abs(df.parse(o2).getTime() - now.getTime());
                        return Long.compare(diff1, diff2);
                    }
                    catch(java.text.ParseException e)
                    {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });

            json = (JSONObject)json.get(closest_date);

            r[0] = Float.parseFloat(json.get("1a. open (EUR)").toString());
            r[1] = r[0]*1.01f;
        }

        return r;
	}


}