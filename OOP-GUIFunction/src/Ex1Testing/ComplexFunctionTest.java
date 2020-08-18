package Ex1Testing;
import static org.junit.Assert.*;
import org.junit.Test;
import Ex1.ComplexFunction;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.function;
public class ComplexFunctionTest 
{
	@Test
	public void testComplexFunctionFunction() 
	{
		String poly_str = "3.0x^2";
		Polynom p = new Polynom(poly_str);
		ComplexFunction cf = new ComplexFunction(p);
		assertTrue(cf.left() == p);
		assertTrue(cf.right() == null);
		assertTrue(cf.getOp() == Operation.None);
	}
	@Test
	public void testComplexFunctionStringFunctionFunction()
	{
		String poly_str = "2.0x^2+2.0x";
		String poly_str2 = "3.0x^2+3.0x";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf = new ComplexFunction("plus",p,p2);
		ComplexFunction cf2 = new ComplexFunction("plus",p,cf);
		String check = "plus(2.0x^2+2.0x^1,plus(2.0x^2+2.0x^1,3.0x^2+3.0x^1))";
		assertTrue(cf.getOp().equals(Operation.Plus));
		assertTrue(cf.left() == p);
		assertTrue(cf.right() == p2);
		assertEquals(check, cf2.toString());
	}
	@Test
	public void testF() 
	{
		String poly_str = "2.0x^2+2.0x";
		String poly_str2 = "3.0x^2+3.0x";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf_plus = new ComplexFunction("plus",p,p2);
		ComplexFunction cf_mul = new ComplexFunction("mul",p,p2);
		ComplexFunction cf_plus_and_mul = new ComplexFunction(p);
		cf_plus_and_mul.plus(cf_plus);
		assertTrue(cf_plus.f(2)==30); //5*2^2 + 5*2
		assertTrue(cf_mul.f(2)==216); //6*2^4 +12*2^3 +6*2^2
	}
	@Test
	public void testInitFromString() 
	{
		String text = "plus(plus(2.0x^1,3.0x^2),mul(2.0x^2,3.0x^2))";
		ComplexFunction cf = new ComplexFunction(null);
		function func = cf.initFromString(text);
		assertEquals(text, func.toString());
	}
	@Test
	public void testCopy() 
	{
		String poly_str = "2.0x^2+2.0x";
		String poly_str2 = "3.0x^2+3.0x";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		function func = new ComplexFunction("plus",p,p2);
		function func_copy = func.copy();
		System.out.println(func);
		System.out.println(func_copy);
		assertEquals(func.toString(), func_copy.toString());
	}
	@Test
	public void testEqualsObject()
	{
		String poly_str = "2.0x";
		String poly_str2 = "3.0x^2";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf = new ComplexFunction("plus", p, p2);
		ComplexFunction cf2 = new ComplexFunction("plus", p, p2);
		ComplexFunction cf3 = new ComplexFunction("plus", p2, p);
		assertTrue(cf.equals(cf2)); //Testing cf & cf2, they both have the same left and the same right functions.
		assertTrue(cf.equals(cf3)); //Testing cf & cf3, they have the opposite functions. meaning : cf.left == cf3.right & cf.right == cf3.left.
	}
	@Test
	public void testPlus() 
	{
		String poly_str = "2.0x";
		String poly_str2 = "3.0x";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf_plus = new ComplexFunction("plus",p,p2); //cf_plus : plus(2.0x,3.0x)
		cf_plus.plus(p); //cf_plus : plus(plus(2.0x,3.0x),2.0x)
		cf_plus.plus(p); //cf_plus : plus(plus(plus(2.0x,3.0x),2.0x)),2.0x)
		assertTrue(cf_plus.f(2)==18); //9*2
	}
	@Test
	public void testMul()
	{
		String poly_str = "2.0x";
		String poly_str2 = "3.0x";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf_mul = new ComplexFunction("mul",p,p2); //cf_mul : mul(2.0x,3.0x) = 6.0x^2
		cf_mul.mul(p); //cf_mul : mul(mul(2.0x,3.0x),2.0x) = 12.0x^3
		cf_mul.mul(p); //cf_mul : mul(mul(mul(2.0x,3.0x),2.0x)),2.0x) = 24.0x^4
		assertTrue(cf_mul.f(2)==384); // 24.0 * 2^4 
	}
	@Test
	public void testDiv() 
	{
		String poly_str = "3.0x^5";
		String poly_str2 = "x";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf_div = new ComplexFunction("div",p,p2); //cf_div : div(3.0x^5,x) = 3.0x^4
		cf_div.div(p2); //cf_div : div(div(3.0x^5,x),x) = 3.0x^3
		cf_div.div(p2); //cf_div : div(div(div(3.0x^5,x),x),x) = 3.0x^2
		assertTrue(cf_div.f(2)==12); // 3.0 * 2^2
	}
	@Test
	public void testMax() 
	{
		String poly_str = "3.0x^5";
		String poly_str2 = "2.0x^3";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf_max = new ComplexFunction("max",p,p2);
		cf_max.max(p2); 
		Polynom p3 = new Polynom("2.0x^6");
		cf_max.max(p3); //cf_max : max(max(max(3.0x^5,2.0x^3),2.0x^3),2.0x^6)
		assertTrue(cf_max.f(2)==128); //2.0 * 2^6
	}
	@Test
	public void testMin() 
	{
		String poly_str = "3.0x^5";
		String poly_str2 = "2.0x^3";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf_min = new ComplexFunction("min",p,p2);
		cf_min.min(p2); 
		Polynom p3 = new Polynom("2.0x^6");
		cf_min.min(p3); //cf_min : min(min(min(3.0x^5,2.0x^3),2.0x^3),2.0x^6)
		assertTrue(cf_min.f(2)==16); //2.0 * 2^3
	}
	@Test
	public void testComp() 
	{
		String poly_str = "3.0x^2";
		String poly_str2 = "2.0x";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf_comp = new ComplexFunction("comp",p,p2); //comp(3.0(2.0x)^2)
		cf_comp.comp(p2); //comp(comp(3.0(2.0(2.0x))^2)
		assertTrue(cf_comp.f(2)==192); //3.0(2.0(2.0 * 2))^2
		 
	}
	@Test
	public void testToString() 
	{
		String poly_str = "3.0x^5";
		String poly_str2 = "2.0x^2";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf_div = new ComplexFunction("div",p,p2); //cf_div : div(3.0x^5,2.0x^2)
		cf_div.div(p2); //cf_div : div(div(3.0x^5,2.0x^2),2.0x^2)
		cf_div.div(p2); //cf_div : div(div(div(3.0x^5,2.0x^2),2.0x^2),2.0x^2)
		String cf_div_str = "div(div(div(3.0x^5,2.0x^2),2.0x^2),2.0x^2)";
		assertEquals(cf_div_str, cf_div.toString());
	}
	@Test
	public void testLeft() 
	{
		String poly_str = "3.0x^5";
		String poly_str2 = "2.0x^3";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf = new ComplexFunction("min",p,p2);
		function left = cf.left();
		assertEquals(cf.left(), left);
	}
	@Test
	public void testRight() 
	{
		String poly_str = "3.0x^5";
		String poly_str2 = "2.0x^3";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf = new ComplexFunction("min",p,p2);
		function right = cf.right();
		assertEquals(cf.right(), right);
	}
	@Test
	public void testGetOp()
	{
		String poly_str = "3.0x^5";
		String poly_str2 = "2.0x^3";
		Polynom p = new Polynom(poly_str);
		Polynom p2 = new Polynom(poly_str2);
		ComplexFunction cf = new ComplexFunction("min",p,p2);
		assertEquals(Operation.Min, cf.getOp());
	}
}
