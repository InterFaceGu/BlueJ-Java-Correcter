package kelvey;
import bluej.extensions.BlueJ;
import bluej.extensions.Extension;

public class HelloWorld extends Extension {
	public static HelloWorld instance;
	
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
		instance = this;
		blueJ.setMenuGenerator(new MenuBuilder());
	}
	
	public static void main(String[] args) {
		System.exit(1);
	}

}
