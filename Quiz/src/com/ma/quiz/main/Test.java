package com.ma.quiz.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class Test.
 */
public class Test{
	
	/** The mod object. */
	public static JSONObject modObject = null;
	
	/**
	 * Generate text file.
	 *
	 * @param object the object
	 * @param level the level
	 */
	public static void generateTextFile(JSONObject object, String level,int num) {
		String dir = "ExportTextFiles";
		File d = new File(dir);
		if(!d.exists()) {
			d.mkdir();
		}
		 BufferedWriter writer = null;
	        try {
	            //create a temporary file
	            String timeLog = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
	           // checkExportDirectory();
	            File logFile = new File("Quiz-"+System.currentTimeMillis()+".txt");

	            // This will output the full path where the file will be written to...
	           // System.out.println(logFile.getCanonicalPath());

	            writer = new BufferedWriter(new FileWriter(logFile));
	            int count = object.keySet().size();
	            int i=1;
	            while(i <= count) {
	            	writer.write(i+"."+object.getJSONObject("q"+Integer.toString(i)).getString("qn")+System.lineSeparator());
	            	writer.write("option a:"+object.getJSONObject("q"+Integer.toString(i)).getString("a")+"  ");
	            	writer.write("option b:"+object.getJSONObject("q"+Integer.toString(i)).getString("b")+System.lineSeparator());      	
	            	writer.write("option c:"+object.getJSONObject("q"+Integer.toString(i)).getString("c")+"  ");
	            	writer.write("option d:"+object.getJSONObject("q"+Integer.toString(i)).getString("d")+System.lineSeparator());
	            	if(num==1) {
	            		writer.write("Correct Answer:"+object.getJSONObject("q"+Integer.toString(i)).getString("answer"));
	            	}
	            	writer.write(System.lineSeparator());
	            	writer.write(System.lineSeparator());
	            	i++;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                // Close the writer regardless of what happens...
	                writer.close();
	            } catch (Exception e) {
	            }
	        }
	}
	
	/**
	 * Generate PDF file.
	 *
	 * @param object the object
	 * @param level the level
	 */
	public static void generatePDFFile(JSONObject object,String level) {
		String dir = "ExportTextFiles";
		File d = new File(dir);
		if(!d.exists()) {
			d.mkdir();
		}
		 BufferedWriter writer = null;
	        try {
	            //create a temporary file
	            String timeLog = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
	           // checkExportDirectory();
	            File logFile = new File("Quiz-"+System.currentTimeMillis()+".pdf");

	            // This will output the full path where the file will be written to...
	           // System.out.println(logFile.getCanonicalPath());

	            writer = new BufferedWriter(new FileWriter(logFile));
	            int count = object.keySet().size();
	            int i=1;
	            while(i <= count) {
	            	writer.write(i+"."+object.getJSONObject("q"+Integer.toString(i)).getString("qn")+System.lineSeparator());
	            	writer.write("option a:"+object.getJSONObject("q"+Integer.toString(i)).getString("a")+"  ");
	            	writer.write("option b:"+object.getJSONObject("q"+Integer.toString(i)).getString("b")+System.lineSeparator());      	
	            	writer.write("option c:"+object.getJSONObject("q"+Integer.toString(i)).getString("c")+"  ");
	            	writer.write("option d:"+object.getJSONObject("q"+Integer.toString(i)).getString("d")+System.lineSeparator());
	            	
	            	writer.write(System.lineSeparator());
	            	writer.write(System.lineSeparator());
	            	count--;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                // Close the writer regardless of what happens...
	                writer.close();
	            } catch (Exception e) {
	            }
	        }
	}
	
	/**
	 * Generate file.
	 *
	 * @param option the option
	 * @param object the object
	 * @param level the level
	 */
	public static void generateFile(String option, JSONObject object, String level,int num) {
		if(option.equals("1")) {
			generateTextFile(object,level,num);
		}
		else if(option.equals("2")) {
			generatePDFFile(object,level);
		}
		else if(option.equals("3")) {
			generateTextFile(object,level,num);
			generatePDFFile(object,level);
		}
	}
	
	/**
	 * Sets the mob object.
	 *
	 * @param obj the new mob object
	 */
	public static void setMobObject(JSONObject obj) {
		modObject = obj ;
	}
	
	/**
	 * Read file.
	 *
	 * @param fileName the file name
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String readFile(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	       while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	/**
	 * Gets the admin password.
	 *
	 * @return the admin password
	 */
	public static String getAdminPassword() {
		String admin = "";
		try {
			 admin=readFile("admin.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return admin;
	}
	
	/**
	 * Creates the xml.
	 */
	public static void createXml() {
		try {
			File xmlFile = new File("result.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("History");
			doc.appendChild(rootElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("result.xml"));

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);
		}
		catch(Exception e) {
			
		}
	}
	
	/**
	 * Read console.
	 *
	 * @return the string
	 */
	public static String readConsole() {
		String input = "";
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		try {
			input = r.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return input;
	}
	
	/**
	 * Gets the integer.
	 *
	 * @return the integer
	 */
	public static int getInteger() {
		Scanner reader = new Scanner(System.in);
		int number = 1;
		number = reader.nextInt();
		//reader.close();
		return number;
	}
	
	/**
	 * Gets the answer.
	 *
	 * @return the answer
	 */
	public static String getAnswer() {
		Scanner reader = new Scanner(System.in);
		String answer = "";
		System.out.print("Enter your answer:");
		answer = reader.nextLine();   
		//reader.close();// reads each answer from the student
		return answer;
	}
	
	/**
	 * Gets the option.
	 *
	 * @return the option
	 */
	public static String getOption() {
		Scanner reader = new Scanner(System.in);
		String option = "";
		option = reader.nextLine();
		//reader.close();
		return option;
	}
	
	/**
	 * Adds the mark.
	 *
	 * @param name the name
	 * @param mark the mark
	 * @param difficultyLevel the difficulty level
	 * @param chosenList the chosen list
	 */
	public static void addMark(String name, String mark, String difficultyLevel, List chosenList) {
		String resourceName = "/StudentMarksnew.json";
		String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        InputStream is = Test.class.getResourceAsStream(resourceName);
    	//is = Student.class.getResourceAsStream(resourceName);
    	JSONTokener tokener = new JSONTokener(is);
        JSONObject nobject = new JSONObject(tokener);
        int qnCount = nobject.keySet().size();
        System.out.println(qnCount);
        JSONObject jo = new JSONObject();
	    jo.put("name", name);
	    jo.put("topics", chosenList);
	    jo.put("level", difficultyLevel);
        jo.put("mark", mark);
        System.out.println(nobject.toString());
        System.out.println(timeLog);
        nobject.put(timeLog,jo);
        System.out.println(nobject.toString());
        try{
	         FileWriter fw = new FileWriter("src/StudentMarksnew.json");
	         fw.write(nobject.toString());
	         fw.close();
        }
       catch (Exception e){
	         System.err.println("Error: " + e);
	    	}
	}
	
}