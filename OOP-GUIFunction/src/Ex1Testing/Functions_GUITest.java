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

	public static String[] cfs =  {
			"comp(comp(-4.0x^2+5.0x^3-2.0x^4,-2.0+x^2),0)",
			"max(plus(x,-3),6.0x^3)",
			"div(div(3.0x^8,2.0+x^3-12.0x^7+x^2),-2.0x^3)",
			"max(comp(4.0x^8,-x-2.0x^2+4.0x^6),7.0x^5-x^5)",
			"max(max(3.0x^6-4.0x^4,-4.0-3.0x^9),1.5)",
			"max(-2.0x^3,-x)",
			"mul(2.0x^4,-2.0x^2-x^5)",
			"min(mul(x-2.0x^3-5.0x^6,5.0x^3-5.0x^4-3.0x^5),x-x^4)"		
	};
	
	@Test
public	void testSaveAndInitFileAndDrow() {
		Functions_GUI fgui1 = new Functions_GUI();
		ComplexFunction cf = new ComplexFunction(new Monom(0,0));
		for (int i = 0; i < cfs.length; i++) {
			fgui1.add(cf.initFromString(cfs[i]));
		}
		try {
			fgui1.saveToFile("my_file.txt");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		Functions_GUI fgui2 = new Functions_GUI();
		try {
			fgui2.initFromFile("write_test.txt");
		} catch (IOException e) {
			fail(e.getMessage());
		}
		for (int i = 0; i < fgui1.size(); i++) {
			assertEquals(fgui1.get(i), fgui2.get(i));	
		}
		
		// draw GUI
		fgui1.drawFunctions("GUI_params.txt");
	}
}