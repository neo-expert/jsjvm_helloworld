import js.*;
import js.event.*;
import static js.dom.DOM.*;
import js.dom.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import com.neoexpert.jsjvm.*;;

public class DOMMain{
	public static void main(String[] args)throws Throwable{
		//MetafactoryTest.main(args);
			/*
		MethodType mt= MethodType.methodType(String.class,int.class);
		try{
			MethodHandle mh = MethodHandles.lookup().findVirtual(String.class, "substring", mt);
			System.out.println("before concat");
			//System.out.println(mh);
			String result=(String)
				mh.invokeExact("STRING", 1);
			System.out.println(result);
		}
		catch(Throwable e){
			throw new RuntimeException(e);
		}
		*/
	System.out.println("hello world");
          HashMap<String, String> hm=new HashMap<>();
          hm.put("hello","world");

          alert("hello "+hm.get("hello"));
          final DOMElement body=getElementById("body");

          final DOMElement b=createElement("button");
          b.setContent("hello world");
					js.Console.log("hello world");
          b.css("background","gray");
          b.css("width","100%");
          b.css("height","100px");
          EventListener evl=
          new EventListener(){
              public void handle(JSElement e, JSEvent event){
                    body.appendChild(b.cloneNode(true));
               }
                                            
          };
          b.addEventListener("click",evl);
          body.appendChild(b);
          
	  /*
		  try{
          	readFile();
		  }
		  catch(IOException e){
		  	e.printStackTrace();
		  }*/
		  Console.debugger();
          MainGL.main(args);
            
	}
        private static void readFile()throws IOException{
          File f=new File("huhufile.txt");
          
          FileInputStream fis=new FileInputStream(f);
          byte[] buf=new byte[(int)f.length()];
          fis.read(buf);
          
          Console.log(new String(buf));
					fis.close();
        }
        
}
