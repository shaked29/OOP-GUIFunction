package Ex1;
import java.util.Comparator;
/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b)
	{
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************
	public Monom(String s) 
	{
		double doubleCoeff = 0; //the variable we will convert our string into
		String coeff = "";
		int pow = 0;
		if(s.contains("x") == true) //'x' is in the string.
		{
			coeff = s.substring(0, s.indexOf("x")); //take the chars before the "x".
			if(coeff.equals("") || coeff.equals("+")) doubleCoeff = 1.0; //there is no real number attached to "x".
			else if(coeff.charAt(0) == '-' && coeff.length() == 1) doubleCoeff = -1.0; //only '-' appears
			else doubleCoeff = Double.parseDouble(coeff); //converting the chars into doubles.
			if(s.contains("^") == true) //'^' is in the string.
			{
				pow = Integer.parseInt(s.substring(s.indexOf("^")+1)); //take the chars after the "^" and  
				//converting them into integers.
			}
			else //'^' is not in the string.
			{
				pow = 1;
			}
		}
		else //only a real number appears
		{
			doubleCoeff = Double.parseDouble(s); //converting the chars into doubles.
			pow = 0;
		}
		this.set_coefficient(doubleCoeff);
		this.set_power(pow);
	}
	public void add(Monom m) //adding between two monoms.
	{
		if(m.get_power() == this.get_power()) //checking if the powers are equals
		{
			this.set_coefficient(m.get_coefficient() + this.get_coefficient());
		}
		else throw new RuntimeException("Error while trying to add this Monom, the powers are not equals.");
	}
	public void multiply(Monom d) //multiplying between two monoms.
	{
		this.set_coefficient(d.get_coefficient() * this.get_coefficient()); //multiplying between the two monoms coefficients.
		if(this.get_power() != 0 || d.get_power() != 0) this.set_power(this.get_power() + d.get_power()); //in case either of the monoms power
		//is not equal to 0.
	}
	public String toString() {
		String ans = "";
		if(this.get_power() == 0) ans = this.get_coefficient()+""; //in case power is 0
		else ans = this.get_coefficient()+"x^"+this.get_power(); 
		return ans;
	}
	public boolean equals(Object m) //checking if two monoms are equal.
	{
		if(m instanceof Monom)
		{
			if(((Monom) m).get_power() == this.get_power())
			{
				if(((Monom) m).get_coefficient() == this.get_coefficient()) return true;
				else if(((Monom) m).get_coefficient() + EPSILON > this.get_coefficient() && ((Monom) m).get_coefficient() < this.get_coefficient()) return true;
				else if(((Monom) m).get_coefficient() - EPSILON < this.get_coefficient() && ((Monom) m).get_coefficient() > this.get_coefficient()) return true;
			}
		}
		return false; //return false we the coefficient and power are not equals.
	}

	/** This method creates a deep copy of a monom. */
	public Monom my_copy() 
	{
		Monom p = new Monom(this._coefficient, this._power);
		return p;
	}
	/** This method creates a deep copy of monom and return him as function */ 
	public function copy()
	{
		return this.my_copy();
	}
	/** This method creates a monom from string and return him as function */
	@Override
	public function initFromString(String s) 
	{
		return new Monom(s);
	}
	// you may (always) add other methods.

	//****************** Private Methods and Data *****************
	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	/** Default constructor sets the monom to zero monom. */
	public Monom() {
		this.set_coefficient(0);
		this.set_power(0);
	}
}