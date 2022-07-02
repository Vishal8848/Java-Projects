package Parser;

import java.util.ArrayList;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPClient;

public class DirectoryTree {

	FTPClient ftpClient = null;
	
	public DirectoryTree(FTPClient ftpClient)	{
		this.ftpClient = ftpClient;
	}
	
	public String newFileEntity(String entity)	{
		return "<i class=\"fas fa-file text-primary me-2\"></i>" + entity;
	}
	
	public String newFolderEntity(String entity)	{
		return "<div class=\"folder-open\" data-bs-toggle=\"collapse\" data-bs-target=\"#" + entity + "\">\r\n"
				+ "    <i class=\"fas fa-folder me-2 text-warning\"></i>" + entity + "\r\n"
				+ "</div>";
	}
	
	public String newListEntity(String entity)	{
		return "<li>" + entity + "</li>";
	}
	
	public String appendFileEntities(String foldername, ArrayList<String> entities)	{
		String out = "<ul class=\"collapse\" id=\"" + foldername + "\">";
		for(String entity: entities)
			out += entity;
		out += "</ul>";
		return out;
	}
	
	public String printDirectoryTree(FTPFile folder) throws Exception {
	    if (!folder.isDirectory()) {
	        throw new IllegalArgumentException("Not a Directory");
	    }
	    int indent = 0;
	    StringBuilder sb = new StringBuilder();
	    printDirectoryTree(folder, indent, sb);
	    return sb.toString();
	}
	
	private void printDirectoryTree(FTPFile folder, int indent, StringBuilder sb) throws Exception {
		
	    if (!folder.isDirectory())
	        throw new IllegalArgumentException("Not a Directory");
	    
	    sb.append(newFolderEntity(folder.getName()));
	    
	    this.ftpClient.changeWorkingDirectory(folder.getName());
	    FTPFile[] files = ftpClient.listFiles();
	    
	    ArrayList<String> entities = new ArrayList<String>();
	    
	    for (FTPFile file : files)
	    	if (file.isDirectory()) 	{
	    		entities.add(newListEntity(newFolderEntity(file.getName())));
	    		printDirectoryTree(file, indent + 1, sb);
	    	}	else	entities.add(newListEntity(newFileEntity(file.getName())));
	    
	    sb.append(appendFileEntities(folder.getName(), entities));
	    
	    this.ftpClient.changeToParentDirectory();
	}
	
//	private void printFile(FTPFile file, int indent, StringBuilder sb) {
//	    sb.append(getIndentString(indent));
//	    sb.append("+--");
//	    sb.append(file.getName());
//	    sb.append("\n");
//	}
//	
//	private String getIndentString(int indent) {
//	    StringBuilder sb = new StringBuilder();
//	    for (int i = 0; i < indent; i++)
//	        sb.append("|  ");
//	    return sb.toString();
//	}
	
}
