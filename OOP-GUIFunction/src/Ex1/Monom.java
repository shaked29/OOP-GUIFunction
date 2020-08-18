package Ex1;
import java.util.Comparator;
/**
 * This class represents a simple "Monom" of shape ax^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Shaked Aviad
 * 
 */
public class Monom implements function
{
	
	private static final long serialVersionUID = 1L;
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	/* constructors */
	public Monom() {
		this.set_coefficient(0);
		this.set_power(0);
	}	
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
		if(this.get_power()==0) 
			return getNewZeroMonom();

		return
				new Monom(
						this.get_coefficient()*this.get_power(), this.get_power()-1
						);
	}

	/**
	 * @param x a number to calculate the mathematics value for 
	 * 
	 * @return the Monom value in the specific x
	 */
	public double f(double x) {
		return this.get_coefficient()*Math.pow(x, this.get_power());
	} 
	/**
	 * @return true if the coefficient is less then {@value #EPSILON}, false otherwise
	 */
	public boolean isZero() {return this.get_coefficient() < this.EPSILON;}

	/**
	 * Constructs Monom from a String in form ax^b:
	 * 			a: double, -, + or empty coefficient. 
	 * 			x: low or capital x. 
	 * 			b: natural number as power (and '^' before power if needed). 
	 * @param s is a proper String representation of Monom
	 */
	public Monom(String s) 
	{
		s=s.toLowerCase();
		double cof=this.findCofficient(s);
		int pow=this.findPower(s);

		this.set_coefficient(cof);
		this.set_power(pow);

	}

	/**
	 * add this Monom with the given Monom form the same power
	 * [(ax^b)+(mx^b) = (a+m)x^(b)] 
	 * @param d is other Monom to multiply with.
	 * @throws RuntimeException when the powers not equals.
	 */
	public void add(Monom m) 
	{
		if(m.get_power() == this.get_power()) 
		{
			this.set_coefficient(m.get_coefficient() + this.get_coefficient());
		}
		else
			throw new RuntimeException("adding differente powers isn't allowd "
					+ "(" + get_power() +", "+ m.get_power() + ")");
	}
	/**
	 * multiply this Monom with the given Monom 
	 * [(ax^b)*(mx^n) = (a*m)x^(b+n)] 
	 * @param d is other Monom to multiply with
	 */
	public void multiply(Monom d) 
	{
		this.set_coefficient(d.get_coefficient() * this.get_coefficient()); 
		this.set_power(this.get_power() + d.get_power());
	}
	public String toString() {
		String ans="";
		if(this.get_coefficient()==-1) {
			ans+='-';
			if(this.get_power()==0)
				ans+="1";			
		}else if(this.get_coefficient()!=1||this.get_power()==0)
			ans+=this.get_coefficient();
		if(this.get_power()>0)ans+="x";
		if(this.get_power()>1)ans+="^"+this.get_power();
		return ans;
	}
	
	/**
	 * @param o - a Monom to compare
	 * @return true if the powers are equals 
	 * and the coefficient difference is less then {@value #EPSILON}, false otherwise
	 */
	public boolean equals(Object m) 
	{
		if(m instanceof Polynom ||m instanceof ComplexFunction )
			return m.equals(this);
		if (m instanceof Monom) {
			Monom m1=(Monom)m;
			if(this.isZero()&&m1.isZero())
				return true;
			return this.get_power()==m1.get_power()&&
					Math.abs(this.get_coefficient()-m1.get_coefficient())<this.EPSILON;
		}
			return false;
		
	}

	
	/** This method creates a deep copy of monom 
	 *  @return  new Monom 
	 */ 
	public Monom Copy() {
		return new Monom(this._coefficient, this._power);
	}
	public function copy()
	{
		return Copy();
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
	private int findPower(String s) {
		int p=0;
		if(!s.contains("x"))
			p=0;
		else if(!s.contains("^")) {
			if(s.indexOf('x')!=s.length()-1)
				throw new RuntimeException(s+"isn't a polynom ans is an invalid coefficient");
			p=1;
		}
		else p=this.parseInt(s.substring(s.indexOf('^')+1));
		return p;
	}
	private double findCofficient(String s) {
		if(s.indexOf('x')!=s.lastIndexOf('x'))
			throw new RuntimeException(s+"isn't a polynom ans is an invails cofficient");
		//find coefficient
		double cof=0;
		if(!s.contains("x"))
			cof=parseDouble(s);
		else if(s.indexOf('x')==1) {
			char op=s.charAt(0);
			if(op=='-')cof=-1;
			else if(op=='+')cof=1;
			else cof=this.parseDouble(s.substring(0, s.indexOf('x')));	
		} 
		else cof =this.parseDouble(s.substring(0, s.indexOf('x')));	

		return cof;
	} 
	private int parseInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			throw new RuntimeException("'" +s + "' isn't a integer number and is"
					+ " an invalid power");		
		}
	}
	private double parseDouble(String s) {
		try {
			return Double.parseDouble(s);
		}catch (Exception e) {
			throw new RuntimeException(s+"isn't a flaot number and is an invaild coefficient");
		}
	}
}