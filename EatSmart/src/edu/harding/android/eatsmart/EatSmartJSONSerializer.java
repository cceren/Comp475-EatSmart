package edu.harding.android.eatsmart;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class EatSmartJSONSerializer {
    private Context mContext;
    private String mFilename;
    private String mPendingFilename;
    private String mProfileFilename;

    public EatSmartJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
        mPendingFilename = "pending" + mFilename;
        mProfileFilename = "userName" + mFilename;
    }

    public ArrayList<Food> loadFoods(String action) throws IOException, JSONException {
        ArrayList<Food> foods = new ArrayList<Food>();
        BufferedReader reader = null;
        try {
            // open and read the file into a StringBuilder
            InputStream in;
            if(action.equals("pending"))
            	in = mContext.openFileInput(mPendingFilename);
            else
            	in = mContext.openFileInput(mFilename);
            
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for (int i = 0; i < array.length(); i++) {
                foods.add(new Food(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // we will ignore this one, since it happens when we start fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return foods;
    }
    
    public Profile loadProfile()throws IOException, JSONException{
    	Profile profile = new Profile();
    	BufferedReader reader = null;
        try {
            InputStream in = mContext.openFileInput(mProfileFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONObject json = (JSONObject) new JSONTokener(jsonString.toString()).nextValue();
            profile =  new Profile(json.getJSONObject(""));
            
        } catch (FileNotFoundException e) {
            // we will ignore this one, since it happens when we start fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return profile;
    	
    }
    

    public void saveFoods(ArrayList<Food> foods, String action) throws JSONException, IOException {
        // build an array in JSON
        JSONArray array = new JSONArray();
        for (Food c : foods)
            array.put(c.toJSON());
        
        // write the file to disk
        Log.d("SavedState", array.toString());
        Writer writer = null;
        try {
        	OutputStream out;
        	if(action.equals("pending"))
        		out = mContext.openFileOutput(mPendingFilename, Context.MODE_PRIVATE);
        	else
        		out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
        	
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
    
    public void saveProfile(Profile profile)throws JSONException, IOException{
    	JSONObject jsonProfile = new JSONObject();
    	jsonProfile = profile.toJSON();
    	Writer writer = null;
    	try{
    		OutputStream out = mContext
    				.openFileOutput(mProfileFilename, Context.MODE_PRIVATE);
    		writer = new OutputStreamWriter(out);
    		writer.write(jsonProfile.toString());
    	}finally{
    		if(writer != null)
    			writer.close();
    	}
    }
}
