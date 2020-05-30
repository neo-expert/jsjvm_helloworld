import js.dom.*;
import com.neoexpert.jsjvm.*;

public class Main{
	public static void main(String[] args)throws Throwable{
		if(!DOM.isInitialized()){
			//DOM is not initialized
			JVMServer.startThisJar(8080, false);
			return;
		}
		else DOMMain.main(args);
	}
        
}
