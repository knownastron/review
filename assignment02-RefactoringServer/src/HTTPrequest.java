import java.util.Scanner;

public class HTTPrequest {
	public String handleRequest(Scanner in){
		String line = in.nextLine();
		String[] status = line.split("\\s");
//		String[] status = in.nextLine().split("\\s");
		String method = status[0];
		String uri = status[1]; 
//		String resource = status[2];
		System.out.println(uri);
		
		String path = "";
		if (method.equals("GET")) {
			path = getFilePath(uri);
		}
		
		return path;
	}
	
	private String getFilePath(String uri) {
		if (uri.equals("/")) {
			uri = "./resources/index.html";
		} else {
			uri = "./resources" + uri;
		}
		
		return uri;
	}
}

