package il.co.topq.uia.client.infra.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class enables executing commands on the Windows command prompt (cmd.exe)
 * @author Rony Byalsky
 */
public class WindowsCommandPrompt {

	private String command;
	private String outputStream;
	private String errorStream;
	private int exitValue;
	
	public WindowsCommandPrompt(String command) {
		this.command = command;
	}
	
	private class StreamReader extends Thread {
		InputStream is;
		String type;

		StreamReader(InputStream is, String type) {
			this.is = is;
			this.type = type;
		}

		public void run() {
			try {
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line=null;
				StringBuilder sb = new StringBuilder();
				
				while ( (line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				
				if (this.type.equals("OUTPUT")) {
					outputStream = sb.toString();  
				}
				else if (this.type.equals("ERROR")) {
					errorStream = sb.toString();  
				}
			}
			catch (IOException ioe) {
				ioe.printStackTrace();  
			}
		}
	}
	
	public String execute() throws Exception {
		
	     Runtime runtime = Runtime.getRuntime();
	     Process process = runtime.exec("cmd /C " + command);

	     StreamReader outputStreamReader = new StreamReader(process.getInputStream(), "OUTPUT");
	     StreamReader errorStreamReader = new StreamReader(process.getErrorStream(), "ERROR");            
         
         // read output and error streams on separate threads 
         outputStreamReader.start();
         errorStreamReader.start();
                                 
         // wait for the process to end and get the exit value
         exitValue = process.waitFor();
         
         process.destroy();
         
         return "ExitVal: " + exitValue + "\nStdOut: " + outputStream + "\nStdErr: " + errorStream;
	}
	
	
	/* GETTERS & SETTERS */
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(String outputStream) {
		this.outputStream = outputStream;
	}

	public String getErrorStream() {
		return errorStream;
	}

	public void setErrorStream(String errorStream) {
		this.errorStream = errorStream;
	}

	public int getExitValue() {
		return exitValue;
	}

	public void setExitValue(int exitValue) {
		this.exitValue = exitValue;
	}
}
