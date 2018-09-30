import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HTTPresponse {
	
	public void handleResponse(OutputStream out, String desiredFile) throws Exception {
		StringBuilder response = new StringBuilder();
		
		File file = new File(desiredFile);
		String canonicalPath = file.getCanonicalPath();
		System.out.println(canonicalPath);
		if (!canonicalPath.contains("/Users/knownastron/Documents/knownastron/cs6011/assignments/assignment02-RefactoringServer/resources/")) {
			throw new Exception("Access denied");
		}

		if (file.exists()) {
			response.append("HTTP/1.1 200 OK" + "\r\n");
			response.append("Content-Length: " + file.length() + "\r\n");
			response.append("\n");

			InputStream fileInput = new FileInputStream(desiredFile);
			out.write(response.toString().getBytes());
			out.write(fileInput.readAllBytes());
			
			fileInput.close();
		} else {
			response.append("HTTP/1.1 404 Not Found" + "\r\n");
			out.write(response.toString().getBytes());
		}
	}
}
