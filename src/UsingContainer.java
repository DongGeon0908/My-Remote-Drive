
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

/**
 * Servlet implementation class UsingContainer
 */
@ServerEndpoint("/websocket")
public class UsingContainer extends HttpServlet {
	// WebSocket으로 브라우저가 접속하면 요청되는 함수

	// WebSocket에서 전송되는 대화
	String command;

	@OnOpen
	public void handleOpen() {
		// 콘솔에 접속 로그를 출력한다.
		System.out.println("client is now connected...");
	}

	// 해당 메서드가 외부로 출력되는 메서드이다.
	// WebSocket으로 메시지가 오면 요청되는 함수
	@OnMessage
	public String handleMessage(String message) throws Exception {

		String result = show(message);

		return result;
	}

	public static String show(String console) {
		System.out.println("Client to Server" + console);
		String result = "";
		try {
			result = shellCmd(console);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "Error...";
		}
		return result;
	}

	// WebSocket과 브라우저가 접속이 끊기면 요청되는 함수
	@OnClose
	public void handleClose() {
		// 콘솔에 접속 끊김 로그를 출력한다.
		System.out.println("client is now disconnected...");
	}

	// WebSocket과 브라우저 간에 통신 에러가 발생하면 요청되는 함수.
	@OnError
	public void handleError(Throwable t) {
		// 콘솔에 에러를 표시한다.
		t.printStackTrace();
	}

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UsingContainer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String shellCmd(String command) throws Exception {
		Runtime runTime = Runtime.getRuntime();
		Process process = runTime.exec(command);
		InputStream inputStream = process.getInputStream();

		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader bufferReader = new BufferedReader(inputStreamReader);

		String line;
		String result = "";
		if (bufferReader.readLine() != null) {
			while ((line = bufferReader.readLine()) != null) {
				result = result + "\n" + line;
			}
		} else
			result = "Order Error...";

		return result;
	}

	public static String Reader(String fileName) throws IOException {
		BufferedReader bufferReader = new BufferedReader(new FileReader(fileName));
		String result = "";
		while (true) {
			String line = bufferReader.readLine();
			if (line == null)
				break;
			result = result + "<br>" + line;
		}
		bufferReader.close();
		return result;
	}
}
