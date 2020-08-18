package Ex1Testing;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.Iterator;
import org.junit.Test;
import Ex1.ComplexFunction;
import Ex1.Functions_GUI;
import Ex1.Monom;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.Range;
import Ex1.function;
import Ex1.functions;
public class Functions_GUITest
{
	@Test
	
	public void testFunctions_GUI() 
	{
		Functions_GUI my_gui = new Functions_GUI();
		function func = new Polynom("2.0x^3");
		my_gui.add(func);
		assertTrue(my_gui.contains(func));
	}
	@Test
	public void testInitFromFile() 
	{
		Functions_GUI my_gui = new Functions_GUI();
		String line_one = "f(x)= plus(2.0x^2,3.0x^3)";
		String line_two = "f(x)= mul(2.0x^2,3.0x^3)";
		ComplexFunction cf = new ComplexFunction();
		function one = cf.initFromString(line_one);
		function two = cf.initFromString(line_two);
		String file_to_init_from = "my_file.txt";
		try 
		{
			my_gui.initFromFile(file_to_init_from);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		Iterator<function> my_itr = my_gui.iterator();
		assertTrue(my_gui.isEmpty()==false);
		function first_in_list = my_itr.next();
		assertEquals(one.toString(), first_in_list.toString());
		function second_in_list = my_itr.next();
		assertEquals(two.toString(), second_in_list.toString());
	}
	@Test
	public void testSaveToFile() 
	{
		Functions_GUI my_gui = new Functions_GUI();
		String file_to_init_from = "my_file.txt";
		String write_to_file_test = "write_test.txt";
		try 
		{
			my_gui.initFromFile(file_to_init_from);
			my_gui.saveToFile(write_to_file_test);
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	@Test
	public void testDrawFunctionsIntIntRangeRangeInt() 
	{
		functions data = FunctionsFactory();
		int w=1000, h=600, res=200;
		Range rx = new Range(-10,10);
		Range ry = new Range(-5,15);
		data.drawFunctions(w,h,rx,ry,res);
	}
	@Test
	public void testDrawFunctionsString() 
	{
		functions data = FunctionsFactory();
		String jason = "GUI_params.js";
		data.drawFunctions(jason);
	}
	public static functions FunctionsFactory()
	{
		functions ans = new Functions_GUI();
		String s1 = "3.1 +2.4x^2 -x^4";
		String s2 = "5 +2x -3.3x +0.1x^5";
		String[] s3 = {"x +3","x -2", "x -4"};
		Polynom p1 = new Polynom(s1);
		Polynom p2 = new Polynom(s2);
		Polynom p3 = new Polynom(s3[0]);
		ComplexFunction cf3 = new ComplexFunction(p3);
		for(int i=1;i<s3.length;i++) {
			cf3.mul(new Polynom(s3[i]));
		}
		
		ComplexFunction cf = new ComplexFunction(Operation.Plus, p1,p2);
		ComplexFunction cf4 = new ComplexFunction("div", new Polynom("x +1"),cf3);
		cf4.plus(new Monom("2"));
		ans.add(cf.copy());
		ans.add(cf4.copy());
		cf.div(p1);
		ans.add(cf.copy());
		function cf5 = cf4.initFromString(s1);
		function cf6 = cf4.initFromString(s2);
		ans.add(cf5.copy());
		ans.add(cf6.copy());
		Iterator<function> iter = ans.iterator();
		function f = iter.next();
		ComplexFunction max = new ComplexFunction(f);
		ComplexFunction min = new ComplexFunction(f);
		while(iter.hasNext()) {
			f = iter.next();
			max.max(f);
			min.min(f);
		}
		ans.add(max);
		ans.add(min);		
		return ans;
	}
}