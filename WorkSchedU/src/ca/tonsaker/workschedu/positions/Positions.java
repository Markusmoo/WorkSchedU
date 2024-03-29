package ca.tonsaker.workschedu.positions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Positions{ //TODO
	
	@Expose public String[] POSITIONS;
	
	public static boolean savePositions(String[] positions) throws IOException{
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Settings\\positions.json";
		new File(System.getenv("APPDATA")+"\\WorkSchedU\\Settings").mkdirs();
		Writer writer = new OutputStreamWriter(new FileOutputStream(path));
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		
		Positions p = new Positions();
		p.POSITIONS = positions;
		
		writer.write(gson.toJson(p));
		writer.flush();
		writer.close();
		
		System.out.println("Positions JSON file successfully saved to: "+path); 
		return true;
	}
	
	public static String[] loadPositions() throws FileNotFoundException{
		String path = System.getenv("APPDATA")+"\\WorkSchedU\\Settings\\positions.json";
		FileInputStream file = new FileInputStream(path);
		
		Reader reader = new InputStreamReader(file);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

		Positions p = new Positions();
		p = gson.fromJson(reader, Positions.class);
		if(p == null || p.POSITIONS == null){
			System.out.println("WARNING: No positions found.");
			return null;
		}else{
			return p.POSITIONS;
		}
	}
}
