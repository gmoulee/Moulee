package com.ma.quiz.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// TODO: Auto-generated Javadoc
/**
 * The Class Admin.
 */
public class Admin{
	
	/** The option. */
	public static String option;
	
	/** The difficulty level. */
	public static String difficultyLevel;
	
	/** The topic. */
	public static String topic;
	
	/**
	 * Gets the difficulty level.
	 *
	 */
	//public static Scanner areader = new Scanner(System.in);
	public static void getDifficultyLevel() {
		System.out.println("Enter the difficulty level");
		difficultyLevel = Test.readConsole();
	}
	
	/**
	 * Gets the topic.
	 *
	 */
	public static void getTopic() {
		Student.getTopics();
		System.out.println("Enter the topic number:");
		topic = Test.readConsole();
		getDifficultyLevel();
	}
	
	/**
	 * View questions.
	 */
	public static void viewQuestions() {
		Student.chooseTopics();
		getDifficultyLevel();
		JSONObject object = Student.readJSONFile();
		//System.out.println(admObj.toString());
		//getTopic();
		JSONObject studentObject = new JSONObject();
		int qnCount = object.keySet().size(); //finding number of questions.
        int stuCount = 1;
        while(qnCount > 0) {
        	String no = "q"+qnCount;
        	JSONObject tempObj = object.getJSONObject(no);
        	String tempTopic = tempObj.getString("topic");
        	String tempDiff = tempObj.getString("difficulty");
        	
        	if(Student.chosenList.contains(tempTopic) && difficultyLevel.equals(tempDiff)) {
        		String stNo = "q"+stuCount;
        		stuCount++;
        		JSONObject tso = object.getJSONObject(no);
        		tso.put("id", no.substring(1));
        		studentObject.put(stNo,tso);
        	}
        	qnCount--;
        	
        }
        int cnCount = studentObject.keySet().size();
        if(studentObject.keySet().size()==0) {
        	System.out.println("No questions in given selection");
        }
        else {
        	System.out.println("Topics:"+Student.chosenList+"DifficultyLevel:"+difficultyLevel);
        	while(cnCount > 0) {
        		JSONObject tempJ = studentObject.getJSONObject("q"+Integer.toString(cnCount));
        		System.out.println("Id:"+tempJ.getString("id"));
        		System.out.println("Question:"+tempJ.getString("qn"));
        		System.out.println("Option a) "+tempJ.getString("a"));
        		System.out.println("Option b) "+tempJ.getString("b"));
        		System.out.println("Option c) "+tempJ.getString("c"));
        		System.out.println("Option d) "+tempJ.getString("d"));
        		System.out.println("Correct Answer:"+tempJ.getString("answer")+System.lineSeparator());
        		cnCount--;
        	}
        }
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
        	Test.generateFile(st,studentObject,difficultyLevel,0);
        }
        displayAdmin();
	}
	 
	
	/**
	 * Display admin console.
	 */
	public static void displayAdmin(){
		System.out.println("1. CRUD 2.View Result 3.MainMenu");
		//Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a number: ");
		option = Test.readConsole(); // Scans the next token of the input as an int.
		//reader.close();
		while(!option.equals("1") && !option.equals("2") && !option.equals("3")) {
			System.out.println("Enter correct input"); //Checks and asks option to enter 1 or 2.
			option = Test.readConsole(); 
		}
		if(option.equals("1")) {
			System.out.println("1.Add question 2.View 3.Edit 4.Delete");
			System.out.println("Enter the option:");
			while(!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4")) {
				System.out.println("Enter correct input"); //Checks and asks option to enter 1 or 2.
				option = Test.readConsole(); 
			}
			//BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
			String answer=Test.readConsole();
			
			if(answer.equals("1")) {
				addQuestion();
			}
			else if(answer.equals("2")) {
				viewQuestions();
			}
			else if(answer.equals("3")) {
				editQuestion();
			}
			else if(answer.equals("4")) {
				System.out.println("Enter the question number:");
				String qn = "q"+Test.readConsole();
				deleteQuestion(qn);
				displayAdmin();
			}
		}
		else if(option.equals("2")) {
		      viewResults();
			displayAdmin();
		}
		else if(option.equals("3")) {
			Launch.main(null);
		}
	}
	
	/**
	 * Edits the question.
	 */
	public static void editQuestion() {
		System.out.println("Enter the qn id :");
		String id = "q"+Test.readConsole();
		System.out.println("Enter the option you want to edit :");
		System.out.println("1.Question 2.Option A 3.Option B 4.Option C 5.Option D 6.Answer 7.Topic 8.DifficultyLevel");
		String option = Test.readConsole();
		while(!option.equals("1") && !option.equals("2") && !option.equals("3") && !option.equals("4") && !option.equals("5") && !option.equals("6") && !option.equals("7") && !option.equals("8")) {
			System.out.println("Enter the correct Option:");
			option = Test.readConsole();
		}
		System.out.println("Enter the new value:");
		String newvalue = Test.readConsole();
		JSONObject tempJson = Student.readJSONFile();
		int count = tempJson.keySet().size();
		JSONObject tj = tempJson.getJSONObject(id);
		if(option.equals("1")) {
			tj.put("qn", newvalue);
		}
		else if(option.equals("2")) {
			tj.put("a", newvalue);
		}
		else if(option.equals("3")) {
			tj.put("b", newvalue);
		}
		else if(option.equals("4")) {
			tj.put("c", newvalue);
		}
		else if(option.equals("5")) {
			tj.put("d", newvalue);
		}
		else if(option.equals("6")) {
			tj.put("answer", newvalue);
		}
		else if(option.equals("7")) {
			tj.put("topic", newvalue);
		}
		else if(option.equals("8")) {
			tj.put("difficulty", newvalue);
		}
		tempJson.remove(id);
		tempJson.put(id, tj);
		try {
			FileWriter fw = new FileWriter("src/questions2.json");
			fw.write(tempJson.toString());
			Test.setMobObject(tempJson);
			fw.close();
		}
		catch(Exception e) {
			
		}
		displayAdmin();
	}
	
	/**
	 * Adds the question.
	 */
	public static void addQuestion() {
		System.out.println("Enter the topic :");
		topic = Test.readConsole();
		getDifficultyLevel();
		System.out.println("Enter the Question:");
		String qn = Test.readConsole();
		System.out.println("Enter the Option a) ");
		String a = Test.readConsole();
		System.out.println("Enter the Option b) ");
		String b = Test.readConsole();
		System.out.println("Enter the Option c) ");
		String c = Test.readConsole();
		System.out.println("Enter the Option d) ");
		String d = Test.readConsole();
		System.out.println("Enter the Answer Option:");
		String ans = Test.readConsole();
		JSONObject jso = Student.readJSONFile();
		int count = jso.keySet().size();
		String id = "q"+Integer.toString(count+1);
		JSONObject to = new JSONObject();
		to.put("qn", qn);
		to.put("a", a);
		to.put("b", b);
		to.put("c", c);
		to.put("d", d);
		to.put("answer", ans);
		to.put("topic", topic);
		to.put("difficulty", difficultyLevel);
		jso.put(id, to);
		try {
			FileWriter fw = new FileWriter("src/questions2.json");
			fw.write(jso.toString());
			Test.setMobObject(jso);
			fw.close();
		}
		catch(Exception e) {
			
		}
		displayAdmin();
	}
	
	/**
	 * Delete question.
	 *
	 * @param qn the qn
	 */
	public static void deleteQuestion(String qn) {
		JSONObject nobject = Student.readJSONFile();
		int count = nobject.keySet().size();
		//System.out.println(nobject.keySet().size());
		nobject.remove(qn);
		//System.out.println(nobject.keySet());
		int i = Integer.parseInt(qn.substring(1));
		while(i < count) {
			String ts = "q"+Integer.toString(i+1);
			JSONObject tempJ = nobject.getJSONObject(ts);
			nobject.remove(ts);
			nobject.put(qn, tempJ);
			System.out.println(nobject.keySet());
			qn=ts;
			i++;
		}
		 try{
		        FileWriter fw = new FileWriter("src/questions2.json",false);
		        fw.write(nobject.toString());
		        Test.setMobObject(nobject);
		       //.out.println("delete"+nobject);
		        fw.close();
		      }catch (Exception e){
		      System.err.println("Error: " + e);
		      }
	}
	
	
	/**
	 * View results.
	 */
	public static void viewResults() {
		try {
			File mXml = new File("result.xml");
			if(!mXml.exists()) {
				Test.createXml();
			}
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(mXml);
					
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					
			NodeList nList = doc.getElementsByTagName("m");
			if(nList.getLength()==0) {
				System.out.println("No Student Record found:");
			}
			else {
				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);
							
					//System.out.println("\nCurrent Element :" + nNode.getNodeName());
							
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;

						//System.out.println("Time : " + eElement.getAttribute("time"));
						System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
						System.out.println("Time : " + eElement.getElementsByTagName("time").item(0).getTextContent());
						System.out.println("Marks : " + eElement.getElementsByTagName("mark").item(0).getTextContent());
						System.out.println("Topics : " + eElement.getElementsByTagName("topic").item(0).getTextContent());
						System.out.println("Difficulty Level : " + eElement.getElementsByTagName("difficultyLevel").item(0).getTextContent());
						System.out.println("");

					}
				}
			}
			System.out.println("----------------------------");
	
		}
		catch(Exception e) {
			System.out.println("Exception:"+e);
		}
	}
	
	/**
	 * Sets the difficulty level.
	 */
	public static void setDifficultyLevel() {     // Get inputs from Admin to set the difficulty level
		System.out.println("Choose the level you want to view/edit");
		System.out.println("1.Easy  2.Medium  3.Difficult");
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("Enter a number: ");
		difficultyLevel = Test.getOption(); // Scans the next token of the input as an int.
		//reader.close();
		while(!difficultyLevel.equals("1") && !difficultyLevel.equals("2") && !difficultyLevel.equals("3")) {
			System.out.println("Enter correct level:"); //Checks and asks option to enter 1 or 2 or 3.
			difficultyLevel = Test.getOption();
		}
	}
}