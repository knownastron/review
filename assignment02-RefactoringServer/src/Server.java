import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Server {

	private Socket socket = null; // socket for a new connection
	private ServerSocket server = null;
	private int port;
	
	
	public Server(int port) throws IOException {
		this.port = port;
	}
	
	public void run() throws IOException {
		this.server = new ServerSocket(port);
		
		while (true) {
			System.out.println("Server listening on port " + port);
			this.socket = server.accept(); // waiting on client
			System.out.println("Client connected!");
			try {
				// create input and output streams
				Scanner in = new Scanner(socket.getInputStream());
				OutputStream out = socket.getOutputStream();
				
				// handle request and response
				HTTPrequest request = new HTTPrequest();
				String desiredFile = request.handleRequest(in);
				HTTPresponse response = new HTTPresponse();
				response.handleResponse(out, desiredFile);

				// flush and close everything
				out.flush();
				in.close();
				out.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Server muhServer = new Server(8888);
		muhServer.run();
	}
}

/* deprecated: moved to HTTPrequest and HTTPresponse
private String getFilePath(String status) {

	String uri = status.split("\\s")[1];
	System.out.println(uri);
	if (uri.equals("/")) {
		uri = "./resources/index.html";
	} else {
		uri = "./resources" + uri;
	}
	
	return uri;
}

private void handleResponse(OutputStream output, String line) throws IOException {
	StringBuilder response = new StringBuilder();
	String uri = getFilePath(line);
	
	File file = new File(uri);
	if (file.exists()) {
		response.append("HTTP/1.1 200 OK" + "\r\n");
		response.append("Content-Length: " + file.length() + "\r\n");
		response.append("\n");

		InputStream fileInput = new FileInputStream(uri);
		output.write(response.toString().getBytes());
		output.write(fileInput.readAllBytes());
		
		fileInput.close();
	} else {
		response.append("HTTP/1.1 404 Not Found");
		output.write(response.toString().getBytes());
	}
}


*/

