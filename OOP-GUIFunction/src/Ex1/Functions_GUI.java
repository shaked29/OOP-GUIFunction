package Ex1;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Functions_GUI implements functions
{
	private ArrayList<function> my_list;
	private Color [] colors = {Color.BLUE, Color.DARK_GRAY, Color.MAGENTA, Color.GREEN, Color.ORANGE, Color.CYAN, Color.RED, Color.PINK };
	public Functions_GUI()
	{
		this.my_list = new ArrayList<function>();
	}
	@Override
	public int size() 
	{
		return my_list.size();
	}
	@Override
	public boolean isEmpty()
	{
		return my_list.isEmpty();
	}
	@Override
	public boolean contains(Object o) 
	{
		return my_list.contains(o);
	}
	@Override
	public Iterator<function> iterator() 
	{
		return my_list.iterator();
	}
	@Override
	public Object[] toArray() 
	{
		return my_list.toArray();
	}
	@Override
	public <T> T[] toArray(T[] a) 
	{
		return my_list.toArray(a);
	}
	@Override
	public boolean add(function e) 
	{
		return my_list.add(e);
	}
	@Override
	public boolean remove(Object o) 
	{
		return my_list.remove(o);
	}
	@Override
	public boolean containsAll(Collection<?> c) 
	{
		return my_list.containsAll(c);
	}
	@Override
	public boolean addAll(Collection<? extends function> c)
	{
		return my_list.addAll(c);
	}
	@Override
	public boolean removeAll(Collection<?> c) 
	{
		return my_list.removeAll(c);
	}
	@Override
	public boolean retainAll(Collection<?> c)
	{
		return my_list.retainAll(c);
	}
	@Override
	public void clear() 
	{
		my_list.clear();
	}
	@Override
	public void initFromFile(String file) throws IOException 
	{
		try 
		{ 
			FileReader file_reader = new FileReader(file); 
			BufferedReader buffer_reader = new BufferedReader(file_reader);
			String line = buffer_reader.readLine();
			function new_function = new ComplexFunction();
			my_list.add(new_function.initFromString(line));
			for(int i=1; line!=null; i=i+1) 
			{
				line = buffer_reader.readLine();
				if (line != null) my_list.add(new_function.initFromString(line));
			}
			buffer_reader.close();     
			file_reader.close();     
		}
		catch(IOException ex) 
		{  
			System.out.print("Error reading file\n" + ex);
			System.exit(2);
		}
	}
	@Override
	public void saveToFile(String file) throws IOException 
	{
		try 
		{ 
			FileWriter fw = new FileWriter(file);  
			PrintWriter outs = new PrintWriter(fw);
			for(int i = 0; i < my_list.size(); i++)
			{
				outs.println("f(x)= "+my_list.get(i).toString());
			}
			outs.close(); 
			fw.close();
		}
		catch(IOException ex) 
		{  
			System.out.print("Error writing file\n" + ex);
		}		
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
		int size = my_list.size();
		double[] x_parameters = new double[resolution+1];
		double[][] y_parameters = new double[size][resolution+1];
		double x_step = (rx.get_max()-rx.get_min())/resolution;
		double x0 = rx.get_min();
		for (int i=0; i<=resolution; i++) 
		{
			x_parameters[i] = x0;
			for(int a=0;a<size;a++)	y_parameters[a][i] = my_list.get(a).f(x_parameters[i]);
			x0+=x_step;
		}
		for(int a=0;a<size;a++) 
		{
			int c = a%colors.length;
			StdDraw.setPenColor(colors[c]);
			System.out.println(a+") "+colors[a]+"  f(x)= "+my_list.get(a));
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
