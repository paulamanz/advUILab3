import javax.swing.UIManager;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
		    
		    UIManager.setLookAndFeel(
		        UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) { }
		Phototheque phototheque = new Phototheque();

	}

}
