package Ex1;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.awt.Font;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Functions_GUI extends ArrayList<function> implements functions {
	private Color [] colors = {Color.BLUE, Color.DARK_GRAY, Color.MAGENTA, Color.GREEN, Color.ORANGE, Color.CYAN, Color.RED, Color.PINK };
	/**
	 * class thats can hold a collection of functions,
	 * write and read the functions to file and show them on GUI.
	 * 
	 * @author Shaked Aviad
	 */
	@Override
	public void initFromFile(String file) throws IOException 
	{
		java.util.List<String> s=null;
		try {
			s = Files.readAllLines(Paths.get(file));
		}catch (IOException  e) {
			throw new IOException("file '"+file+"' not exist");
		}
		clear();
		ComplexFunction cf=new ComplexFunction(new Monom(0,0));
		for (int i = 0; i < s.size(); i++) {
			try {
				add(cf.initFromString(s.get(i)));
			} catch (Exception e) {
				throw new IOException("file is not in correct format");
			}
		}
	}
	@Override
	public void saveToFile(String file) throws IOException 
	{
		ArrayList<String> s=new ArrayList<String>();
		for (int i = 0; i < size(); i++) {
			s.add(get(i).toString());
		}
		Files.write(Paths.get(file), s);
	}
	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) 
	{
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());
		int x1 = (int)rx.get_min();
		int y1 = (int)ry.get_min();
		Font font = new Font(StdDraw.getFont().toString(), Font.BOLD, 14 );
		StdDraw.setFont(font);
		for(int p = x1; p<rx.get_max();p++){
			StdDraw.setPenColor(Color.gray);
			StdDraw.line(p,ry.get_min(),p,ry.get_max());
			StdDraw.setPenColor(Color.black);
			if(p==0){continue;}
			StdDraw.text(p, 0.3, ""+p);
		}
		for(int p = y1; p<ry.get_max();p++){
			StdDraw.setPenColor(Color.gray);
			StdDraw.line(rx.get_min(), p,rx.get_max() ,p );
			StdDraw.setPenColor(Color.black);
			StdDraw.text(0.3, p+0.1, ""+p);
		}
		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(0.004);
		StdDraw.line(0, ry.get_min(), 0,ry.get_max());
		StdDraw.line(rx.get_min(), 0, ry.get_max(), 0);
		StdDraw.setPenRadius();
		int size = size();
		double[] x_parameters = new double[resolution+1];
		double[][] y_parameters = new double[size][resolution+1];
		double x_step = (rx.get_max()-rx.get_min())/resolution;
		double x0 = rx.get_min();
		for (int i=0; i<=resolution; i++) 
		{
			x_parameters[i] = x0;
			for(int a=0;a<size;a++)	y_parameters[a][i] = get(a).f(x_parameters[i]);
			x0+=x_step;
		}
		for(int a=0;a<size;a++) 
		{
			int c = a%colors.length;
			StdDraw.setPenColor(colors[c]);
			System.out.println(a+") "+colors[a]+"  f(x)= "+get(a));
			for (int i = 0; i < resolution; i++) StdDraw.line(x_parameters[i], y_parameters[a][i], x_parameters[i+1], y_parameters[a][i+1]);
		}	
	}
	@Override
	public void drawFunctions(String json_file) 
	{
		Object obj = null;
		try 
		{
			JSONParser jp = new JSONParser();
			FileReader fr = new FileReader(json_file);
			obj = jp.parse(fr);
		} catch (IOException | ParseException e) 
		{
			e.printStackTrace();
		} 
		JSONObject jo = (JSONObject) obj; 
		int width = Integer.parseInt(jo.get("Width").toString());
		int height = Integer.parseInt(jo.get("Height").toString());
		int resolution = Integer.parseInt(jo.get("Resolution").toString());
		JSONArray ja = (JSONArray) jo.get("Range_X"); 
		Iterator itr = ja.iterator(); 
		double rx_min = 0, rx_max = 0, ry_min = 0, ry_max = 0;
		rx_min = Double.parseDouble(itr.next().toString()); 
		rx_max = Double.parseDouble(itr.next().toString());  
		Range rx = new Range(rx_min, rx_max);
		JSONArray ja2 = (JSONArray) jo.get("Range_Y"); 
		Iterator itr2 = ja2.iterator(); 
		ry_min = Double.parseDouble(itr2.next().toString()); 
		ry_max = Double.parseDouble(itr2.next().toString());  
		Range ry = new Range(ry_min, ry_max);
		drawFunctions(width,height,rx,ry,resolution);
	} 

}
