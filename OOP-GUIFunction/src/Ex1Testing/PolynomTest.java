package Ex1Testing;

import static org.junit.Assert.*;

import org.junit.Test;

import Ex1.Monom;
import Ex1.Polynom;
import Ex1.Polynom_able;
import Ex1.function;

public class PolynomTest 
{
	@Test
	public void testPolynomString() 
	{
		String poly_str = "2.0x^6+4.0x^5";
		Polynom_able p1 = new Polynom(poly_str);
		assertEquals(poly_str, p1.toString());
	}

	@Test
	public void testF()
	{
		String poly_str = "3.0x^2+2.0x";
		Polynom_able p1 = new Polynom(poly_str);
		double y = p1.f(2.0);
		assertTrue(y==16);
	}

	@Test
	public void testAddPolynom_able() 
	{
		String poly_str = "5.0x^3+3.0x^2";
		String poly_str2 = "3.0x^3+2.0x^2";
		String str_plus_str2 = "8.0x^3+5.0x^2";
		Polynom_able p1 = new Polynom(poly_str);
		Polynom_able p2 = new Polynom(poly_str2);
		p1.add(p2); //adding p2 to p1.
		assertEquals(str_plus_str2, p1.toString());
	}

	@Test
	public void testAddMonom() 
	{
		String poly_str = "5.0x^3+3.0x^2";
		String monom_str = "3.0x^3";
		String poly_plus_monom = "8.0x^3+3.0x^2";
		Polynom_able p1 = new Polynom(poly_str);
		Monom m1 = new Monom(monom_str);
		p1.add(m1); //adding p2 to p1.
		assertEquals(poly_plus_monom, p1.toString());
	}

	@Test
	public void testSubstract() 
	{
		String poly_str = "5.0x^3+3.0x^2";
		String poly_str2 = "3.0x^3";
		String str_minus_str2 = "2.0x^3+3.0x^2";
		Polynom_able p1 = new Polynom(poly_str);
		Polynom_able p2 = new Polynom(poly_str2);
		p1.substract(p2); //subtracting p2 from p1.
		assertEquals(str_minus_str2, p1.toString());
	}

	@Test
	public void testMultiplyPolynom_able()
	{
		String poly_str = "5.0x^3+3.0x^2";
		String poly_str2 = "3.0x^3+2.0x^2";
		String str_multiply_str2 = "15.0x^6+19.0x^5+6.0x^4";
		Polynom_able p1 = new Polynom(poly_str);
		Polynom_able p2 = new Polynom(poly_str2);
		p1.multiply(p2); //multiplying p1 with p1.
		assertEquals(str_multiply_str2, p1.toString());
	}

	@Test
	public void testMultiplyMonom()
	{
		String poly_str = "5.0x^3+3.0x^2";
		String monom_str = "3.0x^3";
		String poly_multiply_monom = "15.0x^6+9.0x^5";
		Polynom_able p1 = new Polynom(poly_str);
		Monom m1 = new Monom(monom_str);
		p1.multiply(m1); //multiplying p1 with m1.
		assertEquals(poly_multiply_monom, p1.toString());
	}

	@Test
	public void testEqualsObject() 
	{
		String poly_str = "5.0x^3+3.0x^2";
		String poly_str2 = "5.0x^3+3.0x^2";
		Polynom_able p1 = new Polynom(poly_str);
		Polynom_able p2 = new Polynom(poly_str2);
		assertEquals(p1, p2);
	}

	@Test
	public void testIsZero()
	{
		String poly_str = "0x^2";
		Polynom_able p1 = new Polynom(poly_str);
		assertTrue(p1.isZero()==true);
	}

	@Test
	public void testRoot() 
	{
		String[] polynoms = {"3x^2","-6x^3","9x","-2"};
		Polynom p1 = new Polynom();
		for (int i = 0; i < polynoms.length; i++) 
		{
			Monom temp = new Monom(polynoms[i]);
			p1.add(temp);
		}
		double poly_result = p1.root(0, 1, 0.0001);
		double result = 0.2135; 
		double diff = poly_result - result;
		assertTrue(result==poly_result || (diff < 0.01 && diff > -0.01));
	}

	@Test
	public void testCopy()
	{
		String poly_str = "3.0x^2+5.0x^1";
		Polynom_able p1 = new Polynom(poly_str);
		Polynom_able p2 = p1.copy();
		assertEquals(p1, p2);
	}

	@Test
	public void testDerivative() 
	{
		String poly_str = "3.0x^3+5.0x^2";
		String poly_derivative = "9.0x^2+10.0x^1";
		Polynom_able p1 = new Polynom(poly_str);
		p1 = p1.derivative();
		assertEquals(poly_derivative, p1.toString());
	}

	@Test
	public void testArea() 
	{
		String[] polynoms = {"4x^6", "-5x^5", "1"};
		Polynom p1 = new Polynom();
		for (int i = 0; i < polynoms.length; i++) 
		{
			Monom temp = new Monom(polynoms[i]);
			p1.add(temp);
		}
		double poly_result = p1.area(-1, 0, 0.0001);
		double result = 2.404; 
		double diff = poly_result - result;
		assertTrue(result==poly_result || (diff < 0.01 && diff > -0.01));
	}

	@Test
	public void testToString() 
	{
		String poly_str = "5.0x^3+3.0x^2";
		Polynom_able p1 = new Polynom(poly_str);
		assertEquals(poly_str, p1.toString());
	}

	@Test
	public void testInitFromString() 
	{
		String poly_str = "5.0x^3+3.0x^2";
		function func = new Polynom();
		func = func.initFromString(poly_str);
		assertEquals(poly_str, func.toString());
	}
}
