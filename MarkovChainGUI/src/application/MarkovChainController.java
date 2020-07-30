package application;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.sun.prism.es2.ES2Graphics;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class MarkovChainController {
	
	@FXML 
	GridPane gridPane;
	@FXML
	TextArea inputTextArea;
	@FXML
	TextArea outputTextArea;
	@FXML
	Button generateButton;
//working on this
	@FXML
	TextArea ngramsTextArea;
	@FXML
	Label typeHereLabel;
	@FXML
	Label generatedLabel;
	@FXML
	Label ngramsLabel;
	
	 String text;
	 String subText;
	 int order = 6;
	 HashMap<Object, ArrayList<Character>> ngrams = new HashMap<Object, ArrayList<Character>>();
	 ArrayList<Character> aftergrams = new ArrayList<Character>();
	 Random rand = new Random();
	
	@FXML
	public void init() {
	}
	
	
	public void generateButtonPressed() {
		try {
			text = inputTextArea.getText().trim();    
			generateAfterGrams();
			
		}catch (Exception e) {
			outputTextArea.setText("YOU ENTERED NOTHING!");
		}	
	}
	
	public void generateAfterGrams() {
		ngrams.clear();
		for(int i = 0; i< text.length()-order;i++){
			subText = text.substring(i, i+order);
			
			if(!ngrams.containsKey(subText)) {
				aftergrams = new ArrayList<Character>();
				aftergrams.add(text.charAt(i+order));
			}
			else {
				aftergrams = ngrams.get(subText);
				aftergrams.add(text.charAt(i+order));
			}
			ngrams.put(subText, aftergrams);	
		}
		
			markov();
		
		ngramsTextArea.setText(ngrams.toString());
	//	System.out.println(ngrams);
	}
	
	
	public void markov() { 
		String currSubText;
		int randIndex;
		do{
			randIndex = rand.nextInt(text.length()-order);
			currSubText = text.substring(randIndex, randIndex+order);
		}while (currSubText.charAt(0) != ' ');
		
		String result = currSubText.replaceFirst("^\\s+", "");

		
		for(int i=0; i<text.length(); i++) {
		
			if(ngrams.get(currSubText)==null) {
				break;
			}
			ArrayList<Character> possibilities = new ArrayList<Character>();
			possibilities= ngrams.get(currSubText);
			int nextIndex = rand.nextInt(possibilities.size());	
			char nextPossibility = possibilities.get(nextIndex);
			result+= nextPossibility;
			currSubText = result.substring(result.length()-order, result.length());
		} 
		result =result.substring(0,1).toUpperCase() + result.substring(1);
		outputTextArea.setText(result);
		
	}
}
