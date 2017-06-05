package kelvey;
import bluej.extensions.BlueJ;
import bluej.extensions.Extension;

public class HelloWorld extends Extension {

	@Override
	public String getName() {
		return "Kevin's HelloWorld";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	@Override
	public boolean isCompatible() {
		return true;
	}

	@Override
	public void startup(BlueJ blueJ) {
		blueJ.setMenuGenerator(new MenuBuilder());
	}
	
	public static void main(String[] args) {
		System.exit(1);
	}

}
