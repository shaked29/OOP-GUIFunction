#Assignment 1
Class: Monom.
Constructors:
Monom(double a, double b):
This constructor will initial a monom with the values given inside the arguments while a is our coefficient and b is our power.

Monom(Monom ot):
This constructor will initial a monom from a given monom.

Monom(String s): 
first we check if the char ‘x’ is in the string. If so, we take the chars before the ‘x’ and ask if there are real numbers attached to it. If not, we set our coefficient variable ‘doubleCoeff’ to 1. If there are real numbers attached to ‘x’ we take and convert them into doubles.  In case we have the char ‘-‘ only without any real numbers attached to it, we set our ‘doubleCoeff’ to -1. After we check if the char ‘^’ is in the string. If so, we take the chars after ‘^’ and convert them into integers. If he doesn’t appear, we set our power variable ‘pow’ to 1. In case both chars ‘x’ & ‘-‘ doesn’t appear in the string, we simple convert the string into doubles.

Methods:
Public void add(Monom m):
We check if the powers are equals, if so, we add between the two coefficients and set the that value as our new coefficient.
If not, we throw a new exception, that says that we had an error trying to add between those two monoms due to a different power values.

Public void multiply(Monom d):
First, we multiply between the two monoms coefficients.
Then we check if either of the monoms power is equal to 0, if so, we don’t change the power. If its not equal to 0, then we add between the monoms power.

Public String toString():
First, we check if our coefficient is equal to 1. If so, we set our string variable ‘ans’ to ‘x^’+power. the same thing we do with 
-1, except now we will output ‘-x^’+power. In case the power is 0, we will only output our coefficient. In case the power is 1, we will output our coefficient+’x’. in any other case we will simply output the string: coefficient +’x^’ + power.

Public boolean equals(Object m):
First we check if 'm' is an instance of Monom. 
If so, we check if Monom 'm' coefficient and power is equal to our Monom coefficient and power.
If so, we return true. else we return false.

Public Monom my_copy():
This this method creates a deep copy of a monom.

Public function copy():
This method calls to my_copy() and creates a deep copy of the monom and return him as function.

Public function initFromString(String s):
This method creates a new monom and return him as function.

Class: Polynom.
Constructors:
Polynom():
This constructor will initial a new Array List  called “poly” which represent our polynom.

Public Polynom(String str):
This constructor will initial a polynom from a given String. It will create a new Array List and a private method called “init_from_string” which will create a polynom from a given string. At the end we will add this polynom with the method “add”.

Methods:
Public double f(double x):
This method returns the values of y for a certain x.
We used an Iteretor of Monoms in order to go over our Array List(Polynom).

Public void add(Polynom_able p1):
This method gets an Polynom_able as an argument and we used an iteretor in order to go over the Array List (Polynom) and in each iteration we add the current monom.
In the end we activate a private function called removeZero which removes from the Polynom all the monoms that are the zero monom.
Public void add(Monom m1):
This method adds a monom to the polynom. If the monom equals to zero- and
the polynom is empty the function will add the zero monom else it does
nothing. . If the power of the monom equals to an existing monom , the
function will add them together. else the function adds the monom to the
polynom array. In the end the function sort the polynom by powers.

Public void substract(Polynom_able p1):
This method will subtract a given argument polynom from (this) polynom.
It will create a temp Polynom and add (this) monoms to it, after that it will go over p1(given argument) and add his monoms to the temp polynom multiplied by minus 1 which is basically what subtract means. 
At the end we clear our current list and build it again from the temp polynom which is the answer. Then we sort our list(our polynom) and again run our private method removeZero.

Public void multiply(Polynom_able p1):
This method receives a polynomial p1 and multiplies it with (this). If one of the polynomial is empty the function will add the second polynomial to the empty one. If one of the polynomial equals to zero, the function will return zero. Else the function will multiply the monomial of each polynomial in mathematical order , put the answer in a new polynomial and deep copy it into (this).

Public void multiply(Monom m1):
This method will multiply a polynom by a given monom.
It uses the method multiply from the Monom class for each monom in our polynom.

Public boolean equals(Object p1):
This method first checks if p1 is an instance of Polynom.
If so, test if (this) polynom Is logically equal to p1.
Return false in case they are different.

Public boolean isZero():
This method return true if this polynom contains only the zero monom.
Public double root(double x0, double x1, double eps):
The method root finds a solution to the function f(x)=0 between 2 points.
This method receive x0 and x1 such as f(x0) and f(x1) must be from the opposite side of x-line else the method will throw Run time Exception. This method receive   x0 and x1 such as x0 need to be smaller then x1 else the method will throw Run time Exception.

Public Polynom_able copy():
This method creates a deep copy of the object.

Public Polynom_able derivative():
This method returns the derivative polynom.

Public double area(double x0, double x1, double eps):
Compute Riemann's Integral over this Polynom starting from x0, till x1 using
eps size steps. The function received x0 and x1 such as x0 need to be smaller
then x1 else the function returns area = 0. return the approximated area above the x-axis below this Polynom and between the [x0,x1] range.

Public String toString():
This method prints the polynom as: a0+a1x+a2x^2+…+anx^n.

Public function initFromString(String s):
This method calls a private method called my_init_from_string which creates a new polynom from the given String and return him as polynom, then we return the polynom as a function.

Private Methods:
Private void removeZero():
This method moves over the polynom and checks two things:
1.If this is the zero polynom do nothing.
2.If the polynom contains more then one monom and one of them is the zero monom, remove him.

Private void my_init_from_string(String s):
This private method will initial a polynom from a given String. allowed chars are: 0,1,2,3,4,5,6,7,8,9,+,-,.,x,^ No spaces allowed. When a is a real number and b is a positive integer (natural) number. a can be positive or negative , b must be
positive. Constructing a new Polynom with string: The string must be written as such:"ax^b+a1x^b1..." No spaces are allowed. The program will detect invalid input and throw a runtime exception.

Private int size(Polynom_able p1):
This function gets polynom_able and return the amount of monoms in the polynom.

Class: ComplexFunction.
Constructors:
ComplexFunction(function f1):
This constructor gets a function and initiate a new complex function object while the left side is the f1 given and right side set to null and the operation set to none.

ComplexFunction(String operation, function f1, function f2):
This constructor gets two functions and an operation, f1 is set to the left side of the complex function object and f2 is set to the right side. the operation given is set to the one suited to him from the Enum class Operation.

Methods:
public double f(double x):
This method gets a double x as an argument and then send this x to the correct Complex Function operation, lets say the operation set for this complex function instance is plus, then we return the left_side.f(x) + right_side.f(x) and the same applies to the rest of the operations.

public function initFromString(String text):
This method initiate an function from a given string, first we change the entire text to lower case then we remove spaces because in some of the given tests the text examples have spaces in them. then we remove the prefix of "f(x)= " which also was presented in the example tests. after we done with this changes we go over the algorithm, first we want to know where is the exact comma that separate the two functions, once we have that info we can send the left & right side in a recursive way to initFromString again, each with his correct string. when the recursive algorithm is done we get the result we wanted which is a function that have an operation and a left & right side which each of them represent a function.

public boolean equals(Object obj):
This equals method gets an object as an argument. if the given object is an instance of complex function we check both sides. if they are equal we return true, else false. if the given object is an instance of monom or polynom we check the possibility of "plus(x,x) == 2x" or "mul(x,x) == x^2" in those cases the function will return true. but in cases of a big complex function there is no real way to check every single value of x, so we will return false.

Methods plus,mul,div,max,min,comp:
we call on our private method add_new_function(function f1) which will only add new function, if the right side is null she will add the given function to it. if its not null she will make the current complex function as the new left side, and the given function as the right side.

public String toString():
This method will return a string that represent this complex function we will take this.operation and this.left and this.right, 
in case the operation is Times we will return "mul", in case the operation is Divide we will return "div". and we return every thing in lower cases.

Methods right,left,getOp: 
This methods are a simple get methods.

Class: Functions_GUI:
Constructor: public Functions_GUI():
This constructor initiate a new ArrayList of type functions.

Methods: size, isEmpty, contains, iterator, Object[] toArray(), <T> T[] toArray, add, remove, containsAll, addAll, removeAll, retainAll & clear implementations are from java.util.ArrayList, java.util.Collection & java.util.Iterator.

public void initFromFile(String file):
This method will add to our ArrayList the functions from a given file. we use FileReader & BufferReader objects to read the file lines and insert them into our array. 

public void saveToFile(String file):
This method will save our list into a file. we use FileWriter & PrintWriter objects in order to do so.

public void drawFunctions(int width, int height, Range rx, Range ry, int resolution):
This method will draw the functions inside our list using the object StdDraw and it uses the parameters given inside the argument in order to do so.

public void drawFunctions(String json_file):
This method will call the drawFunctions(with the parameters that StdDraw needs) those parameters are inside the jason file given in the argument. I am using json.simple 1.1 JAR.

Author: Sagi Cohen.
