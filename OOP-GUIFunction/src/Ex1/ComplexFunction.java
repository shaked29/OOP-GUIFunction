package Ex1;
public class ComplexFunction implements complex_function
{
	private static final long serialVersionUID = 1L;
	private function _left;
	private function _right;
	private Operation _op;
	//A constructor that gets a function as an argument.
	public ComplexFunction(function f1) 
	{
		this._left = f1;
		this._right = null;
		this._op = Operation.None;
	}
	
	//A constructor that gets an String operation & two functions as an argument.
	public ComplexFunction(String operation, function f1, function f2)
	{
		this._left = f1;
		this._right = f2;
		switch(operation.toLowerCase())
		{
		case "plus": this._op = Operation.Plus; break;
		case "mul": this._op = Operation.Times; break;
		case "times": this._op = Operation.Times; break;
		case "min": this._op = Operation.Min; break;
		case "max": this._op = Operation.Max; break;
		case "div": this._op = Operation.Divid; break;
		case "divid": this._op = Operation.Divid; break;
		case "comp": this._op = Operation.Comp; break;
		case "none": 
		{
			if(f1 !=null && f2 != null)
			{
				this._op = Operation.None;
				throw new RuntimeException("No operation between the two functions, illegal complex function behavior behaviour."); 
			}
		}
		//In case of error.
		default:
		{
			this._op = Operation.Error;
			throw new RuntimeException("Unknown or unsupported operation");
		}
		}
	}
	public ComplexFunction() {
	}
	public ComplexFunction(Operation op, function f1, function f2) 
	{
		this(op.toString(), f1, f2);
	}

	@Override
	public double f(double x)
	{
		double answer = 0;
		switch(this._op)
		{
		case Plus: answer = this._left.f(x) + this.right().f(x); break;
		case Times: answer = this._left.f(x) * this._right.f(x); break;
		case Min: answer = Math.min(this._left.f(x), this._right.f(x)); break;
		case Max: answer = Math.max(this._left.f(x), this._right.f(x)); break;
		case Divid: 
		{
			if(this._right.f(x)==0) throw new RuntimeException("Cannot divide by zero"); 
			else answer = this._left.f(x) / this._right.f(x); break;
		}
		case Comp:
		{
			if(this._right == null) answer = this._left.f(x); 
			else answer = this._left.f(this._right.f(x)); break;
		}
		case None: if(this._right == null) answer = this._left.f(x);
		else throw new RuntimeException("There is no operation between the functions");
		default: break;
		}
		return answer;
	}
	@Override
	public function initFromString(String text) 
	{
		text = text.toLowerCase();
		text = remove_spaces(text);
		//We cut off the prefix "f(x)= ".
		if(text.length() > 6 && text.substring(0, 6).equals("f(x)= ")) text = text.substring(6);
		else if(text.length() > 5 && text.substring(0, 5).equals("f(x)=")) text = text.substring(5);
		
		//In case there is no operation and text represent only a polynom.
		if (text.indexOf('(') == -1 && text.indexOf(')') == -1) 
		{ 
			function func = new Polynom (text);
			return func;
		}
		//This algorithm will go over the text in a while loop and will count the open brackets and the close brackets. if we encounter a comma and the
		//difference between them is equal to 1 then we know we are exactly at the middle of the given text which represent a function.
		int middle = 0;
		int index = 0;
		int open_brackets = 0;
		int close_brackets = 0;
		while(text.length() > index)
		{
			if(text.charAt(index) == '(') open_brackets++;
			else if(text.charAt(index) == ')') close_brackets++;
			int diff = open_brackets - close_brackets;
			if(diff == 1 && text.charAt(index) == ',')
			{
				middle = index;
				break;
			}
			index++;
		}
		String operation = text.substring(0, text.indexOf("("));
		function left_side = initFromString(text.substring(text.indexOf("(")+1, middle));
		function right_side = initFromString(text.substring(middle+1, text.length()-1));
		return new ComplexFunction(operation, left_side, right_side);
	}
	@Override
	public function copy()
	{
		String to_send = toString();
		function new_func = initFromString(to_send);
		return new_func;
	}
	//This method will return true if the object inside the argument is one of three possible scenarios:
	//1.If the object is an instance of ComplexFunction and both functions and the operation is equal to the ComplexFunction that
	//call for the method.
	//2.If the object is an instance of Monom and in cases of "plus(x,x) == 2x" or "multiply(x,x) == x^2"
	//3.Same as 2, but applying on Polynom this time.
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof ComplexFunction)
		{
			if(this._left == ((ComplexFunction) obj).left() && this._right == ((ComplexFunction) obj).right() && this._op == ((ComplexFunction) obj).getOp()) return true;
			else if(this._left == ((ComplexFunction) obj).right() && this._right == ((ComplexFunction) obj).left() && this._op == ((ComplexFunction) obj).getOp()) return true;
		}
		else if(obj instanceof Monom)
		{
			if(this._op == Operation.Plus)
			{
				Monom m1 = new Monom((Monom) this._left);
				m1.add((Monom) this._right);
				if(m1.equals(obj)) return true;
			}
			else if(this._op == Operation.Times)
			{
				Monom m1 = new Monom((Monom) this._left);
				m1.multiply((Monom) this._right);
				if(m1.equals(obj)) return true;
			}
		}
		else if(obj instanceof Polynom)
		{
			if(this._op == Operation.Plus)
			{
				Polynom p = new Polynom();
				p.add((Polynom_able) this._left);
				if(p.equals(obj)) return true;
			}
			else if(this._op == Operation.Times)
			{
				Polynom p = new Polynom();
				p.add((Polynom_able) this._left);
				if(p.equals(obj)) return true;
			}
		}
		return false;
	}
	@Override
	public void plus(function f1)
	{
		add_new_function(f1);
		this._op = Operation.Plus;
	}	
	@Override
	public void mul(function f1) 
	{
		add_new_function(f1);
		this._op = Operation.Times;
	}
	@Override
	public void div(function f1)
	{
		add_new_function(f1);
		this._op = Operation.Divid;
	}
	@Override
	public void max(function f1) 
	{
		add_new_function(f1);
		this._op = Operation.Max;
	}
	@Override
	public void min(function f1) 
	{
		add_new_function(f1);
		this._op = Operation.Min;
	}
	@Override
	public void comp(function f1) 
	{
		add_new_function(f1);
		this._op = Operation.Comp;
	}
	@Override
	public String toString()
	{
		String ans =this.getOp().toString();
		if(this.getOp() == Operation.Times) ans = "mul";
		else if(this.getOp() == Operation.Divid) ans = "div";
		return ans.toLowerCase()+"("+this._left+","+this._right+")";
	}
	@Override
	public function left() 
	{
		return this._left;
	}
	@Override
	public function right() 
	{
		return this._right;
	}
	@Override
	public Operation getOp() 
	{
		return this._op;
	}
	//Private methods
	private void add_new_function(function f1)
	{
		if(this._right != null)
		{
			function new_left = new ComplexFunction(this.getOp().toString(), this._left, this._right);
			this._left = new_left;
		}
		this._right = f1;
	}
	//A private function that removes spaces from a given String.
	private String remove_spaces(String text)
	{
		String text_with_no_spaces = "";
		for (int i=0; i<text.length(); i++) 
		{
			if (text.charAt(i)==' ') continue;
			text_with_no_spaces=""+text_with_no_spaces+text.charAt(i);
		}
		return text_with_no_spaces;
	}
}
