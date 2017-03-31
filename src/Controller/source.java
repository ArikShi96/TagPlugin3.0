package Controller;

    import java.applet.*;
import java.awt.*;
import java.nio.channels.ScatteringByteChannel;

public class source {
	private int age;
	private String  name;
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}

class car{
	private String color;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
}

interface doRun {
	void start();
	
	void stop();
}

interface hello {
	void start();
	
	void stop();
}