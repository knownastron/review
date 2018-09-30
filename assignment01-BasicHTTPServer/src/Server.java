import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Server {

	private Socket socket = null; // socket for a new connection
	private ServerSocket server = null;
	private Scanner input = null;
	private OutputStream output = null;
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
			
			
			// create input and output streams
			input = new Scanner(socket.getInputStream());
			output = socket.getOutputStream();
			
			parseInput(output, input.nextLine());
			
			
			// flush and close everything
			
			output.flush();

			input.close();
			output.close();
			socket.close();
		}
	}
	
	private void parseInput(OutputStream output, String line) throws IOException {
		StringBuilder response = new StringBuilder();
		String[] splitLine = line.split("\\s");
		String method = splitLine[0];
		String uri = splitLine[1];
		System.out.println(uri);
		String protocol = splitLine[2];
		if (uri.equals("/")) {
			uri = "./resources/index.html";
		} else {
			uri = "./resources/" + splitLine[1].substring(1);
		}
		
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

	
	
	public static void main(String[] args) throws IOException {
		Server muhServer = new Server(8080);
		muhServer.run();
	}
}

