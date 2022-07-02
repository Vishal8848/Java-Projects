import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTPClient;

import Parser.JsonParser;
import Parser.Transaction;

/**
 * Servlet implementation class JsonServlet
 */
@WebServlet("/JsonServlet")
public class JsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		
		out.println("Only POST Requests are Handled");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		
		// FTP Server Credentials
		String Host = request.getParameter("host");
		String User = request.getParameter("user");
		String Pass = request.getParameter("pass");
		String Port = request.getParameter("port");
		
		// Metadata & Preferences
		String URL = request.getParameter("url");
		String Folder = request.getParameter("fname");
		String Type = request.getParameter("ftype");
		String folderStructure = "";
		
		JsonParser data = new JsonParser();
		
		try {
			
			Transaction result[] = data.parseJsonFromURL(URL);
			
			String ftpcred[] = { Host, User, Pass, Port, Folder, Type };
			
			FTPClient ftpClient = data.remoteCsvWriter(result, ftpcred);
			
			folderStructure = data.buildDirectoryTree(ftpClient);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.println("<!DOCTYPE html>\r\n"
				+ "\r\n"
				+ "<html lang=\"en\">\r\n"
				+ "\r\n"
				+ "    <head>\r\n"
				+ "        \r\n"
				+ "        <meta charset=\"UTF-8\">\r\n"
				+ "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
				+ "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "        \r\n"
				+ "        <title>Transaction Parser Client</title>\r\n"
				+ "\r\n"
				+ "        <!-- Bootstrap v5.0.2 CSS -->\r\n"
				+ "        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC\" crossorigin=\"anonymous\">\r\n"
				+ "\r\n"
				+ "        <!-- Google Fonts - Quicksand -->\r\n"
				+ "        <link href=\"https://fonts.googleapis.com/css2?family=Quicksand:wght@700&display=swap\" rel=\"stylesheet\">\r\n"
				+ "\r\n"
				+ "        <!-- Font Awesome v5.15.0 -->\r\n"
				+ "        <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.0/css/all.min.css\" integrity=\"sha512-BnbUDfEUfV0Slx6TunuB042k9tuKe3xrD6q4mg5Ed72LTgzDIcLPxg6yI2gcMFRyomt+yJJxE+zJwNmxki6/RA==\" crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\" />\r\n"
				+ "        \r\n"
				+ "        <style>\r\n"
				+ "            *   {\r\n"
				+ "                font-family: 'Quicksand';\r\n"
				+ "            }\r\n"
				+ "            body {\r\n"
				+ "            	background-color: #F6FBF4;\r\n"
				+ "            }\r\n"
				+ "            .folder-block {\r\n"
				+ "            	background: #FFFFFF;\r\n"
				+ "                width: 500px;\r\n"
				+ "                margin: 0 auto;\r\n"
				+ "                margin-top: 150px;\r\n"
				+ "            }\r\n"
				+ "            .folder-open {\r\n"
				+ "                cursor: pointer;\r\n"
				+ "            }\r\n"
				+ "            ul {\r\n"
				+ "                margin-left: 20px;\r\n"
				+ "                list-style-type: none;\r\n"
				+ "            }\r\n"
				+ "        </style>\r\n"
				+ "\r\n"
				+ "    </head>\r\n"
				+ "\r\n"
				+ "    <body>\r\n"
				+ "\r\n"
				+ "        <div class=\"folder-block p-3 rounded border shadow\">\r\n"
				+ "\r\n"
				+ "            <legend class=\"text-center fs-4\">Remote System Folder Structure</legend><hr/>\r\n"
				+ "\r\n"
				+ folderStructure
				+ "            \r\n"
				+ "        </div>\r\n"
				+ "\r\n"
				+ "        <!-- Bootstrap v5.0.2 JS -->\r\n"
				+ "        <script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM\" crossorigin=\"anonymous\"></script>\r\n"
				+ "\r\n"
				+ "    </body>\r\n"
				+ "\r\n"
				+ "</html>");
	}

}
