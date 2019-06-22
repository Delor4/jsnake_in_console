package delor.jsnake.core;

//helper class (counting created objects)
public class Counted {
	private static int counter = 0;
	private int counted;
	Counted(){
		counted = counter;
		counter++;
	}
	int getCounter() {
		return counted;
	}
}
