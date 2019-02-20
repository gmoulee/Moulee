package com.ma.quiz.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
	

// TODO: Auto-generated Javadoc
/**
 * The Class Student.
 */
public class Student{
	
	/** The option. */
	public static int option;
	
	/** The difficulty level. */
	public static String difficultyLevel;
	
	/** The name. */
	public static String name="";
	
	/** The id. */
	public static int id;
	
	/** The questions. */
	public static JSONObject questions;
	
	/** The mark. */
	public static String mark ;
	
	/** The topic list. */
	public static List topicList ;
	
	/** The chosen list. */
	public static List chosenList ;
	
	/** The sreader. */
	public static Scanner sreader = new Scanner(System.in);
	
	/**
	 * Display student.
	 */
	//public static 
	public static void displayStudent(){
		  // Reading from System.i
		if(name.equals("")) {
	    name = getName(); 
		}//Gets name from the student
	    chooseTopics(); //Gets the list of topics from User
	    setDifficultyLevel(); //Gets the difficulty level of the quiz
		getQuestions();   //calls method to display questions
	}
	
	
	
	/**
	 * Choose topics.
	 */
	public static void chooseTopics() {
		getTopics();
		System.out.println("Choose your topics. Multiple topics can be chosen seperated by commas eg: 1,2");
		
		//populateTopics();
		//System.out.println("reading");
		String userTopics = Test.getOption();
		System.out.println("chosen topics:"+userTopics);
		String[] userList = userTopics.split(",");
		int tempi=0;
		chosenList = new ArrayList();
		while(tempi<userList.length){
			try {
			int index = Integer.parseInt(userList[tempi]);
			chosenList.add(topicList.get(index-1)); 
			}
			catch(NumberFormatException ex) {
				
			}
			                 //Adds list of chosen topics by the user
			tempi++;
		}
		//System.out.println(chosenList);
	}
	
	/**
	 * Gets the topics.
	 */
	public static void getTopics() {
		JSONObject object = readJSONFile();
		JSONObject topic = new JSONObject();
		int totalCount = object.keySet().size(); //finding number of questions.
		String tempTopic = object.getJSONObject("q1").getString("topic");
		topicList = new ArrayList();
		topicList.add(tempTopic);
	    while(totalCount > 0) {
	    	String no = "q"+totalCount;
	        tempTopic = object.getJSONObject(no).getString("topic");
	    	if(!topicList.contains(tempTopic)) {
	    		topicList.add(tempTopic);
	    	}
	    	totalCount--;
	    }
	    int i=0;
		int count = topicList.size();
		while(i<count) {
			System.out.println((i+1)+"."+topicList.get(i));
			i++;
		}
	}
	
	/**
	 * Sets the difficulty level.
	 */
	public static void setDifficultyLevel() {     // Get inputs from student to set the difficulty level
		System.out.println("Choose the defficulty level ");
		System.out.println("1.Easy  2.Medium  3.Difficult");
		  // Reading from System.in
		System.out.print("Enter a number: ");
		difficultyLevel = Test.getOption(); // Scans the next token of the input as an int.
		while(!difficultyLevel.equals("1") && !difficultyLevel.equals("2") && !difficultyLevel.equals("3")) {
			System.out.println("Enter correct level"); //Checks and asks option to enter 1 or 2 or 3.
			difficultyLevel = Test.getOption();
		}
	}
	
	/**
	 * Read JSON file.
	 *
	 * @return the JSON object
	 */
	public static JSONObject readJSONFile() {
		if(Test.modObject==null) {
		String resourceName = "/questions2.json";
		String answer = "";
        InputStream is = Student.class.getResourceAsStream(resourceName);
        if (is == null) {
            throw new NullPointerException("Cannot find resource file " + resourceName);
        }
        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        
       // int qnCount = object.keySet().size();
        return object;
		}
		else {
			return Test.modObject;
		}
	}
	
	
	/**
	 * Gets the questions.
	 *
	 */
	public static void getQuestions() {
		///*String resourceName = "/questions"+difficultyLevel+".json";
		String answer = "";
		JSONObject studentObject = new JSONObject();
		JSONObject object = readJSONFile();
        int qnCount = object.keySet().size(); //finding number of questions.
        int stuCount = 1;
        while(qnCount > 0) {
        	String no = "q"+qnCount;
        	JSONObject tempObj = object.getJSONObject(no);
        	String tempTopic = tempObj.getString("topic");
        	String tempDiff = tempObj.getString("difficulty");
        	if(chosenList.contains(tempTopic) && difficultyLevel.equals(tempDiff)) {
        		String stNo = "q"+stuCount;
        		stuCount++;
        		studentObject.put(stNo,object.getJSONObject(no));
        	}
        	qnCount--;
        	
        }
        int cnCount = studentObject.keySet().size();
        mark = "/"+cnCount;
        int imark = 0;
        while(cnCount > 0) {
        	String no = "q"+cnCount;
        	JSONObject tempObj = studentObject.getJSONObject(no);
        	String tempQn = tempObj.getString("qn");
        	System.out.println("Question:"+tempQn);
        	System.out.println("Option a) "+tempObj.getString("a"));
        	System.out.println("Option b) "+tempObj.getString("b"));
        	System.out.println("Option c) "+tempObj.getString("c"));
        	System.out.println("Option d) "+tempObj.getString("d"));
        	System.out.println("Enter option (a or b or c or d)");
        	System.out.println("Answer:");
        	answer = Test.getAnswer();
        	while(!answer.equals("a") && !answer.equals("b") && !answer.equals("c") && !answer.equals("d")) {
        		System.out.println("Enter correct option (small letters a or b or c or d)");
        		answer=Test.readConsole();
        	}
        	String correctAnswer = tempObj.getString("answer");
        	if(answer.equals(correctAnswer)) { //checks if the answer is correct
        		imark ++;   //increments the mark by 1 if answer is correct
        	}
        	cnCount--;
        }
        mark = Integer.toString(imark)+mark;
        System.out.println("Name:"+name+"   Mark:"+mark+"  Topics:"+chosenList.toString()+"   Difficulty:"+difficultyLevel);
        storeMark();
        System.out.println("Do you want to export the quiz generated ?");
        System.out.println("1.Yes 2.No");
        String str = Test.readConsole();
        while(!str.equals("1") && !str.equals("2")) {
        	System.out.println("enter correct option:");
        	str = Test.readConsole();
        }
        if(str.equals("1")) {
        	System.out.println("1.Text file 2.PDF file 3.BOTH format");
        	String st = Test.readConsole();
        	while(!st.equals("1") && !st.equals("2") && !st.equals("3")) {
            	System.out.println("enter correct option:");
            	st = Test.readConsole();
            }
        	Test.generateFile(st,studentObject,difficultyLevel,1);
        }
        /*System.out.println("Export the quiz as Text file ?");
        System.out.println("1. Yes 2.No");
        int cOption = sreader.nextInt();
        while(cOption!=1 && cOption!=2) {
        	System.out.println("Enter the correct option 1 or 2:");
        }
        if(cOption==1) {
        	checkExportDirectory();
        }*/
        //code to store student marks.
        //storeMark();
        Launch.main(null);
	}
	
	/**
	 * Store mark.
	 */
	public static void storeMark() {
		try {
			File xmlFile = new File("result.xml");
			if(!xmlFile.exists()) {
				Test.createXml();
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("m");
			String id = Integer.toString(nList.getLength()+1);
			Element root = doc.getDocumentElement();
			Element cd = doc.createElement("m");
			root.appendChild(cd);
			
			// shorten way
			 cd.setAttribute("id", id);

			// name 
			Element sname = doc.createElement("name");
			sname.appendChild(doc.createTextNode(name));
			cd.appendChild(sname);

			// Mark 
			Element smark = doc.createElement("mark");
			smark.appendChild(doc.createTextNode(mark));
			cd.appendChild(smark);

			// Topics 
			Element topics = doc.createElement("topic");
			topics.appendChild(doc.createTextNode(chosenList.toString()));
			cd.appendChild(topics);

			// Difficulty
			Element diff = doc.createElement("difficultyLevel");
			diff.appendChild(doc.createTextNode(difficultyLevel));
			cd.appendChild(diff);
			
			//Time
			Element time = doc.createElement("time");
            String timeLog = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss").format(Calendar.getInstance().getTime());
			time.appendChild(doc.createTextNode(timeLog));
			cd.appendChild(time);
			
			//write into file
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
	
	/*public static void storeMark() {
		File d = new File("src/StudentMarksnew.json");
		int newfile = 0;
		System.out.println(d.exists());
		if(!d.exists()) {
			try {
				d.createNewFile(); //Creats file if dowsn't exist
				newfile = 1;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        
        
        if (newfile==1) {
        	JSONObject object=new JSONObject();
        	JSONObject tempObj = new JSONObject();
        	tempObj.put("name", name);
        	tempObj.put("topics", chosenList);
        	tempObj.put("level", difficultyLevel);
        	tempObj.put("mark", mark);
            //JSONObject tempObj2 = new JSONObject();
            object.put(timeLog,tempObj);
            try{
   	         FileWriter fw = new FileWriter("src/StudentMarksnew.json");
   	         fw.write(object.toString());
   	         fw.close();
   	    }
           catch (Exception e){
   	         System.err.println("Error: " + e);
   	    }
        }
        
        else {
        	Test.addMark(name, String.valueOf(mark), difficultyLevel, chosenList);
        	/*String resourceName = "/StudentMarksnew.json";
            InputStream is = Student.class.getResourceAsStream(resourceName);
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
   	    	//}/*
	   }
	}*/
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public static String getName() {
		String name="";
		System.out.println("Enter your name:");
		name = sreader.nextLine();
		return name;
	}
	
	/**
	 * Check export directory.
	 */
	public static void checkExportDirectory() {
		String dir = "ExportTextFiles";
		File d = new File(dir);
		if(!d.exists()) {
			d.mkdir();
		}
		 BufferedWriter writer = null;
	        try {
	            //create a temporary file
	            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	           // checkExportDirectory();
	            File logFile = new File(chosenList.toString()+"  Difficulty-"+difficultyLevel+timeLog+".txt");

	            // This will output the full path where the file will be written to...
	           // System.out.println(logFile.getCanonicalPath());

	            writer = new BufferedWriter(new FileWriter(logFile));
	            writer.write("Hello world!"+"\n");
	            writer.write("hello Moulee");
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
	 * Gets the answer.
	 *
	 * @return the answer
	 */
	public static String getAnswer() {
		String answer = "";
		System.out.println("Enter your Answer:");
		answer = sreader.nextLine();
		return answer;
	}
}