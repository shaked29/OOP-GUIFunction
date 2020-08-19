package Ex1;
/**
 * This class represent a Complex function form:
 * Operation(function, function) when function is a Monom, Polynom or ComplexFunction.
 * with add, multiply, divide and more functionality.
 * @author Shaked Aviad
 *
 */
public class ComplexFunction implements complex_function {
	private function left;
	private function right;
	private Operation op;
	
	public ComplexFunction() {}
	/**
	 * Constructor that gets one function (the left function) and initialize None Operation.
	 * this ComplexFunction will be equals to f
	 * @param f - the left function.
	 */
	public ComplexFunction(function f) {
		if (f == null)
			throw new RuntimeException("Function argument can't be null");
		this.left = f.copy();
		this.op = Operation.None;
	}
	/**
	 * Constructor that gets operation and two functions.
	 * @param op - the Operation.
	 * @param f1 - the left function.
	 * @param f2 - the right function.
	 */
	public ComplexFunction(Operation op, function f1, function f2) {
		if (f1 == null)
			throw new RuntimeException("Left function argument can't be null");
		this.left = f1.copy();
		this.right = f2 == null ? null : f2.copy();
		this.op = op;

		if (f2 == null && op == null)
			this.op = Operation.None;
	}
	/**
	 * Constructor that gets operation represents by string and two functions.
	 * @param s - the Operation String.
	 * @param f1 - left function.
	 * @param f2 - right function.
	 */
	public ComplexFunction(String s, function f1, function f2) {
		this(stringtoOperation(s), f1, f2);
	}

	@Override
	public double f(double x) {

		switch (op) {

		case Plus:
			return left.f(x) + right.f(x);
		case Times:
			return left.f(x) * right.f(x);
		case Divid:
			double rightF = right.f(x);
			if(rightF == 0)
				throw new ArithmeticException("/ by zero");
			return left.f(x) / rightF;
		case Max:
			return Math.max(left.f(x), right.f(x));
		case Min:
			return Math.min(left.f(x), right.f(x));
		case Comp:
			return (left.f(right.f(x)));
		case None:
			return left.f(x);

		case Error:

		default:
			throw new RuntimeException("Operation unknown");
		}
	}

	@Override
	public function initFromString(String s) {
		s=s.replaceAll(" ", "");
		if(s.charAt(s.length()-1) == ')') {
			Operation op;
			try {
				op = stringtoOperation(s.substring(0, s.indexOf('(')));				
			} catch (IndexOutOfBoundsException e) {
				throw new RuntimeException("Invalid string - not contain '('");
			}

			int count = 0;
			int index = -1; 
			for (int i = s.indexOf('(')+1; i < s.length()-1 && index == -1; i++) {
				if(s.charAt(i) == '(')
					count++;
				else if(s.charAt(i) == ')')
					count--;
				else if(s.charAt(i) == ',' && count == 0)
					index = i;
			}

			if(index == -1)
				throw new RuntimeException("Invalid string - can't find ',' in the correct place");

			function left = initFromString(s.substring(s.indexOf('(')+1, index));
			function right = initFromString(s.substring(index+1, s.length()-1));

			return new ComplexFunction(op, left, right);
		} else {
			return new Polynom(s);
		}
	}

	@Override
	public function copy() {
		function left = this.left.copy();
		function right = this.right == null ? null : this.right.copy();
		return new ComplexFunction(this.op, left, right);
	}

	@Override
	public void plus(function f1) {
		makeCF(f1, Operation.Plus);
	}

	@Override
	public void mul(function f1) {
		makeCF(f1, Operation.Times);
	}

	@Override
	public void div(function f1) {
		makeCF(f1, Operation.Divid);
	}

	@Override
	public void max(function f1) {
		makeCF(f1, Operation.Max);
	}

	@Override
	public void min(function f1) {
		makeCF(f1, Operation.Min);
	}

	@Override
	public void comp(function f1) {
		makeCF(f1, Operation.Comp);
	}

	@Override
	public function left() {
		return left;
	}

	@Override
	public function right() {
		return right;
	}

	@Override
	public Operation getOp() {
		return op;
	}
	
	private void makeCF(function f, Operation op) {
		if (f == null)
			throw new RuntimeException("Function argument can't be null");
		function tmp = f.copy();
		if (this.op != Operation.None)
			this.left = copy();
		this.right = tmp;
		this.op = op;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof function))
			return false;

		function fun = (function)obj;

		for (int i = -20; i < 20; i++) {
			if(i==0)
				continue; 
			if(!compareDouble(f(i), fun.f(i))) 
				return false;
		}
		return true;
	}

	private boolean compareDouble(double d1, double d2) {
		return Math.abs(d2-d1) < Monom.EPSILON;
	}

	@Override
	public String toString() {
		if(op == Operation.None)
			return left.toString();
		return operationToString(op)+"("+left+","+right+")";
	}
	/**
	 * Convert String to {@link Operation} by the format in {@link Operation}
	 * @param s the string we want to convert to operation
	 * @return the converted {@link Operation} 
	 */
	static private Operation stringtoOperation(String s) {
		s = s.toLowerCase();
		switch (s) {

		case "plus":
			return Operation.Plus;

		case "mul":
			return Operation.Times;

		case "div":
			return Operation.Divid;

		case "max":
			return Operation.Max;

		case "min":
			return Operation.Min;

		case "comp":
			return Operation.Comp;

		default:
			throw new RuntimeException("Operation '"+s+"' unknown");

		}
	}
	/**
	 * Convert {@link Operation} to String by the format in {@link Operation}
	 * @param op the operation we want to convert to string 
	 * @return the converted string 
	 */
	static private String operationToString(Operation op) {
		switch (op) {

		case Plus:
			return "plus";

		case Times:
			return "mul";

		case Divid:
			return "div";

		case Max:
			return "max";

		case Min:
			return "min";

		case Comp:
			return "comp";

		default:
			throw new RuntimeException("Operation not soppost to br used");

		}
	}
}