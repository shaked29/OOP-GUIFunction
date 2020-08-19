package Ex1;
import java.util.Iterator;
import java.util.TreeMap;
import Ex1.Monom;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Shaked Aviad
 *
 */
public class Polynom implements Polynom_able{

	private TreeMap<Integer, Monom> map=new TreeMap<Integer,Monom>();
	/* constructors */
	/**
	 * Zero (empty Polynom)
	 */
	public Polynom() {}
	/**
	 * init a Polynom from a String such as: {"x", "3+1.4X^3-34x",
	 * "25", "-3X^78 + 14.3X" etc};
	 * 
	 * @param s: is a string represents a Polynom.
	 * 			except: well defined Monoms (double, -, + or empty coefficient. 
	 * 			low or capital x. natural number as power(and '^' before power))
	 * 			and + / - between the Monoms. (without (,),*)
	 */

	public Polynom(String s) {
		s=s.replaceAll(" ","");
		String[] monoms=s.split("(?=[-,+])");
		for (int i = 0; i < monoms.length; i++) {
			add(new Monom(monoms[i]));
		}
	}

	/**
	 * Compute Riemann's Integral over this Polynom starting from x0, till x1 using
	 * eps size steps. The function received x0 and x1 such as x0 need to be smaller
	 * then x1 else the function returns area = 0.
	 * 
	 * @return the approximated area above the x-axis below this Polynom and between
	 *         the [x0,x1] range.
	 */
	@Override
	public double area(double x0, double x1, double eps) {
			if(x1<x0)
				throw new RuntimeException("Invalid input: x1"+x1+"can't be largger than x0"+x0);
			
			double area=0,
					      avg=0;
			while(x0+eps<x1) {
				avg=(f(x0)+f(x0+eps))*0.5;
				if(avg>0)
					area+=avg*eps;
				x0+=eps;
			}
			if(avg>0&&x0<x1)
				area+=avg*(x1-x0);
			return area;
	}
	/**
	 * The method root finds a solution to the function f(x)=0 between 2 points.
	 * The function received x0 and x1 such as f(x0) and f(x1) must be from the
	 * opposite side of x-line else the function will throw RuntimeException. The
	 * function received x0 and x1 such as x0 need to be smaller then x1 else the
	 * function will throw RuntimeException.
	 * @param x0  starting point.
	 * @param x1  end point.
	 * @param eps the epsilon defines the accuracy amount of the function
	 * @return the x solution to f(x)=0.
	 */
	@Override
	public double root(double x0, double x1, double eps) {
		if(f(x0)*f(x1) > 0)
			throw new RuntimeException("Invalid input: x1 and x0 can't be in the same side of the x line");

		if(f(x0) > 0)
			return root(x1,x0,eps);

		double x = (x0+x1)*0.5;
		double y = f(x);
		if(-eps < y && y < eps)
			return x;

		if(y > 0)
			return root(x0, x, eps);
		else
			return root(x, x1, eps);
	}
	/** This method returns the values of y for a certain x. */
	@Override
	public double f(double x) {
		double ans=0;
		for(Iterator<Monom> iter=iteretor();iter.hasNext();) {
			Monom m=iter.next();
			ans+=m.f(x);
		}
		return ans+0;
	}
	/** This method create a new polynom from String and return him as function */

	@Override
	public function initFromString(String s) {
		return new Polynom(s);
	}
	
	

	@Override
	public void add(Polynom_able p1) {
		for (Iterator<Monom> iterator = p1.iteretor(); iterator.hasNext();) {
			Monom monom = iterator.next();
			if(!monom.isZero())
				add(monom);
		}

	}
	/**
	 * This method adds a monom to the polynom. If the monom equals to zero- and
	 * the polynom is empty the function will add the zero monom else it does
	 * nothing. . If the power of the monom equals to an existing monom , the
	 * function will add them together. else the function adds the monom to the
	 * polynom.
	 * 
	 * @param m1 the monom that we add.
	 */

	@Override
	public void add(Monom m1) {
		int index=m1.get_power();
		if(map.get(index)!=null)
			map.get(index).add(m1);
		else map.put(index, new Monom(m1));

		if(map.get(index).isZero())
			map.remove(index);
	}
	/**
	 * This method subtract between two polynoms.
	 * 
	 * @param p1 the polynom that we want to subtract with.
	 */

	@Override
	public void substract(Polynom_able p1) {
		Polynom_able p=(Polynom_able)p1.copy();
		p.multiply(Monom.MINUS1);
		add(p);
	}

	@Override
	public void multiply(Monom m1) {
		if(m1.isZero())
			map.clear();
		else {
			Iterator<Monom> iter=this.copy().iteretor();
			map.clear();
			while(iter.hasNext()) {
				Monom m=iter.next();
				m.multiply(m1);
				map.put(m.get_power(), m);
			}
		}
	}
	/**
	 * This method receives a polynomial p1 and multiplies it with (this). If one
	 * of the polynomial is empty the function will add the second polynomial to the
	 * empty one. If one of the polynomial equals to zero, the function will return
	 * zero. Else the function will multiply the monomial of each polynomial in
	 * mathematical order , put the answer in a new polynomial and deep copy it into
	 * (this).
	 */

	@Override
	public void multiply(Polynom_able p1) {
		if(p1.isZero())
			map.clear();
		else {
			 Iterator<Monom>iter=((Polynom_able)p1.copy()).iteretor();
			 Polynom_able p2=this.copy();
			 map.clear();
			 while(iter.hasNext()) {
				 Polynom_able p3=(Polynom_able)p2.copy();
				 p3.multiply(iter.next());
				 add(p3);
			 }
		}

	}
	/** This method returns true if this polynom contains only the zero monom. */

	@Override
	public boolean isZero() {
		for (Iterator<Monom> iterator = this.iteretor(); iterator.hasNext();) {
			Monom monom = iterator.next();
			if (!monom.isZero())
				return false;
		}
		return true;
	}
	
	/** This method creates a deep copy of the object. */

	@Override
	public Polynom_able copy() {
		Polynom p = new Polynom();
		p.add(this);
		return p;
	}
	/**
	 * This method test if this Polynom is logically equals to o.
	 * 
	 * @param o the polynom that we compere with.
	 * @return true if this polynom represents the same function ans p1.
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof ComplexFunction)
			return o.equals(this);
		
		if(o instanceof Polynom)
			return equals((Polynom)o);
		
		if(o instanceof Monom)
			return equals(new Polynom(o.toString()));
		
		return false;
	}
	/**
	 * This method returns the derivative polynom.
	 * 
	 * @return A new polynom that derivative to this polynom.
	 */
	
	
	@Override
	public Polynom_able derivative() {
		Polynom p=new Polynom();
		for (Iterator<Monom>iter=iteretor();iter.hasNext();) {
			Monom m=iter.next();
			p.add(m.derivative());
		}
		return p;
	}
	/** Prints the polynom as: a0+a1x+a2x^2+...... +anx^n . */

	@Override
	public Iterator<Monom> iteretor() {
		// TODO Auto-generated method stub
		return map.values().iterator();
	}
	@Override
	public String toString() {
		if(map.size()==0)
			return "0";
		Iterator<Monom>iter=this.iteretor();
		String ans="";
		if(iter.hasNext())
			ans+=iter.next();
		while(iter.hasNext()) {
			Monom m=iter.next();
			if(m.get_coefficient()>0)
				ans+="+";
			ans+=m;
		}
		return ans;
	}
	private boolean equals(Polynom p1) {
		Polynom_able p = copy();
		p.substract(p1);
		return p.isZero();
	}
}