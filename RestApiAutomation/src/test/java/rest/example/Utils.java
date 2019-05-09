package rest.example;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;



public class Utils {
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";


    public Properties fetchFromPropertiesFile(){
        Properties properties = new Properties();
        try {
            File file = new File("src/test/resources/config.properties");
            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);
            fileInput.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        return properties;
    }

    public String getValueFromDB(Connection con,String sql) throws Exception{
            System.out.println("Connection warehouse:"+con);
            String value ="";
            Statement stmt;
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                value = rs.getString(1);
                break;
//                logger.info("ResultSet:" + value);
            }
            return value;
    }



    public ArrayList<Integer> getMultipleValues(Connection con,String sql) throws Exception{

        ArrayList<Integer> resultList = new ArrayList<Integer>();
        Statement stmt;
        stmt = con.createStatement();
        System.out.println("Query is:>>>"+sql);
        ResultSet rs = stmt.executeQuery(sql);
        String value ="";

        while (rs.next()) {
            value = rs.getString(1);
            System.out.println("Value is ::::::"+value);
            resultList.add(Integer.parseInt(value));
        }
        return resultList;

    }



    public String readJsonDataFromFile(String arg1) throws Throwable {
        String json;
        FileInputStream fin = new FileInputStream(new File(System.getProperty("user.dir")+"//src//test//resources//testData//"+arg1));
        InputStreamReader in = new InputStreamReader(fin);
        BufferedReader bufferedReader = new BufferedReader(in);
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }
        json = sb.toString();
        System.out.println(json);
        return json;
    }

    public JSONObject update(JSONObject obj, String keyMain, Object newValue) throws Exception {
        // We need to know keys of Jsonobject
        JSONObject json = new JSONObject();
        Iterator iterator = obj.keys();
        String key = null;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            if ((obj.optJSONArray(key)==null) && (obj.optJSONObject(key)==null)) {
                if ((key.equals(keyMain))) {
                    obj.put(key, newValue);
                }
            }
            if (obj.optJSONObject(key) != null) {
                update(obj.getJSONObject(key), keyMain, newValue);
            }
            if (obj.optJSONArray(key) != null) {
              if(key.contains("Ids") || key.contains("IdList") || key.contains("placeholders")){
                  if ((key.equals(keyMain))) {
                      JSONArray jArray = new JSONArray();
                      if(newValue.toString().contains(",")){
                          String[] valueArray =newValue.toString().split(",");
                          for(int i=0;i<valueArray.length;i++){
                              jArray.put(Integer.parseInt(valueArray[i]));
                          }
                      }else {
                          if(!(newValue.toString().isEmpty())) {
                              jArray.put(Integer.parseInt(newValue.toString()));
                          }
                      }
                      obj.put(key, jArray);
                  }
              }else {
                  JSONArray jArray = obj.getJSONArray(key);
                  for (int i = 0; i < jArray.length(); i++) {
                      update(jArray.getJSONObject(i), keyMain, newValue);
                  }
              }
            }else if(keyMain.contains("placeholders")){
                if ((key.equals(keyMain))) {
                    JSONArray jArray = new JSONArray();
                    if(newValue.toString().contains(",")){
                        String[] valueArray =newValue.toString().split(",");
                        for(int i=0;i<valueArray.length;i++){
                            jArray.put(Integer.parseInt(valueArray[i]));
                        }
                    }else {
                        if(!(newValue.toString().isEmpty())) {
                            jArray.put(Integer.parseInt(newValue.toString()));
                        }
                    }
                    obj.put(key, jArray);
                }
            }
        }
        return obj;
    }


    public String getValueFromRequest(JSONObject obj, String keyMain) throws Exception {
        // We need to know keys of Jsonobject
        System.out.println("Inside get value from request:");
        String objValue=new String();
        JSONObject json = new JSONObject();
        Iterator iterator = obj.keys();
        String key = null;
        while (iterator.hasNext()) {
          key = (String) iterator.next();
            // if object is just string we change value in key

            if ((obj.optJSONArray(key) == null) && (obj.optJSONObject(key) == null)) {
              // get key value
                if ((key.equals(keyMain))) {
                    objValue = obj.get(key).toString();
                    break;
                }

            }else if (obj.optJSONObject(key) != null) {
              objValue = obj.getJSONObject(key).toString();
              break;
            }else if (obj.optJSONArray(key) != null) {
              JSONArray jArray = obj.getJSONArray(key);
                if(jArray!=null && jArray.length()>0)
                {
                    objValue = jArray.getJSONObject(0).toString();
                    break;
                }

          }
        }
        return objValue;
    }

    public String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public int randomInt(int max){
        int randomNum =0;
        randomNum= ThreadLocalRandom.current().nextInt(4000, max + 1);
        return randomNum;
    }

    public Date storeTriggerTime(Integer triggerHr, Integer triggerMin, Date baseDt) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(baseDt);
        cal.set(Calendar.HOUR, triggerHr);
        cal.set(Calendar.MINUTE, triggerMin);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.AM_PM, 0);
        return cal.getTime();
    }

}

