package Ex1Testing;

import static org.junit.Assert.*;

import org.junit.Test;

import Ex1.Monom;
import Ex1.function;

public class MonomTest 
{
	@Test
	public void testMonomDoubleInt() 
	{
		Monom m1 = new Monom(5.0,2);
		assertTrue(m1.get_coefficient()==5.0);
		assertTrue(m1.get_power()==2);
	}

	@Test
	public void testMonomMonom() 
	{
		Monom m1 = new Monom(1,1);
		Monom m2 = new Monom(m1);
		assertEquals(m1, m2);
	}

	@Test
	public void testDerivative() 
	{
		Monom m1 = new Monom(2,1);
		String derivative_of_m1 = "2.0";
		m1 = m1.derivative();
		assertTrue(m1.toString().equals(derivative_of_m1));
	}

	@Test
	public void testF() 
	{
		Monom m1 = new Monom(2,1);
		double y = m1.f(3);
		assertTrue(y==6);
	}

	@Test
	public void testIsZero() 
	{
		Monom m1 = new Monom(0,1);
		assertTrue(m1.isZero()==true);
	}

	@Test
	public void testMonomString() 
	{
		String monom = "2.0x^1";
		String monom2 = "-5.0x^1";
		Monom m1 = new Monom(monom);
		Monom m2 = new Monom(monom2);
		assertEquals(monom, m1.toString());
		assertEquals(monom2, m2.toString());
	}

	@Test
	public void testAdd() 
	{
		Monom m1 = new Monom(5,3);
		Monom m2 = new Monom(2,3);
		m1.add(m2);
		assertTrue(m1.get_coefficient()==7.0);
		assertTrue(m1.get_power()==3);
	}

	@Test
	public void testMultiply() 
	{
		Monom m1 = new Monom(3,2);
		Monom m2 = new Monom(5,4);
		m1.multiply(m2);
		assertTrue(m1.get_coefficient()==15.0);
		assertTrue(m1.get_power()==6);
	}

	@Test
	public void testToString() 
	{
		String monom_str = "5.0x^3";
		String monom_str2 = "-8.0x^1";
		Monom m1 = new Monom(monom_str);
		Monom m2 = new Monom(monom_str2);
		assertEquals(monom_str, m1.toString());
		assertEquals(monom_str2, m2.toString());
	}

	@Test
	public void testEqualsObject() 
	{
		Monom m1 = new Monom(1,1);
		Monom m2 = new Monom(1,1);
		assertEquals(m2, m1);
	}

	@Test
	public void testCopy() 
	{
		Monom m1 = new Monom(1,1);
		function func = m1.copy();
		assertTrue(func.toString().equals(m1.toString()));
	}

	@Test
	public void testInitFromString() 
	{
		String func_str = "2.0x^2";
		function func = new Monom();
		func = func.initFromString(func_str);
		assertEquals(func_str, func.toString());
	}
}
