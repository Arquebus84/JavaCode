package HonorsUITests;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SortingVisualized extends Application{

	/**
	 	Enter number of digits: 					List Size: (n + 1) (for visual size)
	 	[a0], [a1], [a2], ... [an]
	 											[Sort] if the size is > 1
	 											
	 											[Reset] if the size is > 1
	 	
	 	[1]				[2]			[3]			[a] //The number to add	
	 	[4]				[5]			[6]
	 	[7]				[8]			[9]	 	
	 	[Add Number]	[0]			[Remove]
	 */
	
	private BorderPane b_Pane;
	private ArrayList<Integer> arrayList;
	
	private static int WIDTH = 500;
	private static int HEIGHT = 300;
	
	private GridPane g_Pane;
	private Button[] buttons;
	
	private TextField listText;
	private TextField numberText;
	
	private boolean wasSorted;
	
	//Sort an ArrayList of size n
	@SuppressWarnings("exports")
	@Override
	public void start(Stage stage) {
		//Integer array list that organizes the integers input
		arrayList = new ArrayList<>();
		
		g_Pane = new GridPane();
		b_Pane = new BorderPane();
						
		//listText is the numbers that you added
		listText = new TextField();
		listText.setStyle("-fx-font: 25px \"Times New Roman\";");
		listText.setEditable(false);
		//listText.setMaxWidth(10);	//Set the Maximum Width
		listText.setPrefWidth(250);
		
		//numberText is where you'll see the numbers you wrote
		numberText = new TextField();
		numberText.setStyle("-fx-font: 25px \"Times New Roman\";");
		numberText.setEditable(false);
		numberText.setPrefWidth(50); //Set the Maximum Width
		
		
		FlowPane numbersPane = new FlowPane();
		numbersPane.getChildren().addAll(listText);
		numbersPane.setAlignment(Pos.CENTER);
		buttons = new Button[14]; //14 Buttons total (the last 2 are for sort and reset)
		for(int i = 0; i < 9; i++) {
			buttons[i] = new Button((i + 1) + "");
		}
		buttons[9] = new Button("Add"); buttons[10] = new Button("0"); buttons[11] = new Button("Remove");
		
		//gridPane.add(Node, column, row)**
		int k = 0;
		for(int row = 0; row < 4; row++) {
			for(int col = 0; col < 3; col++) {
				buttons[k].setStyle("-fx-font: 12px \"Times New Roman\";");
				buttons[k].setPrefSize(60, 60);
				g_Pane.add(buttons[k], col, row);
				k++;
			}
		}
		g_Pane.setAlignment(Pos.CENTER);
		
		/**Instantiate the Sort and Reset Buttons*/
		//Keep them both invisible until the array length is greater than 2
		buttons[12] = new Button("Sort");
		buttons[12].setStyle("-fx-font: 25px \"Times New Roman\";");
		buttons[13] = new Button("Reset");
		buttons[13].setStyle("-fx-font: 25px \"Times New Roman\";");

		VBox sortResetNumberPane = new VBox(buttons[12], buttons[13], numberText);
		sortResetNumberPane.setSpacing(5);
		
		b_Pane.setCenter(g_Pane);
		b_Pane.setTop(numbersPane);
		b_Pane.setRight(sortResetNumberPane);
		
		Scene scene = new Scene(b_Pane, WIDTH, HEIGHT);
		stage.setTitle("Bubble Sort");
		stage.setScene(scene);
		stage.show();
		
		//Add functionality to buttons in Keypad
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setOnAction(e ->{
				//System.out.println("Clicked: " + ((Button)e.getSource()).getText());
				
				ArrayList<Integer> sortArrayList = new ArrayList<>();
				//TextField currentNumber = new TextField();
				
				if(((Button)e.getSource()).getText() == "Remove") {
					System.out.println("Remove current number");
					
					if(arrayList.size() > 0) {
						arrayList.remove(arrayList.size() - 1);
					}else {
						System.out.println("length is zero");
					}					
					
				}else if(((Button)e.getSource()).getText() == "Add"){
					arrayList.add(Integer.parseInt(numberText.getText()));
					numberText.clear();
					
					System.out.println("Add current number to list: \n" + arrayList.toString());					
				}else if(((Button)e.getSource()).getText() == "Sort"){
					if(arrayList.size() > 1) {
						wasSorted = true;
						System.out.println("Sorting list");
						//arrayList.add(Integer.parseInt(((Button)e.getSource()).getText()));
						//arrayList.add(currentNumber.getText());
						int[] sortArray = bubbleSort(arrayList);
						for(int r = 0; r < sortArray.length; r++) {
							sortArrayList.add(sortArray[r]);
						}
						//Change the arrayList to the Sorted Array in order to append more numbers
						arrayList = sortArrayList;
					}else {
						System.out.println("List is too small");
					}					
				
				}else if(((Button)e.getSource()).getText() == "Reset"){
					System.out.println("Reset list to 0");
					
					arrayList.clear();
					//listText.setText(arrayList.toString());
					
				}else{
					//Create a number by adding it to the existing listText
					listText.setText(arrayList.toString());
					numberText.setText(numberText.getText() + ((Button)e.getSource()).getText());
				}
				
				/**Append the elements into the arrayList*/
				listText.setText(arrayList.toString());
				System.out.println(wasSorted);
				
			});
		}
		
	}
	
	public int[] bubbleSort(ArrayList<Integer> arrayList) {
		//Convert ArrayList into an Array of type int
		int[] array = new int[arrayList.size()];
		for(int i = 0; i < array.length; i++) {
			array[i] = arrayList.get(i);	//initialize each element in the array of type int
		}
		
		int[] bubbleArray = new int[array.length];
		
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array.length; j++) {
				if(array[i] < array[j]) {
					int temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
		
		for(int i = 0; i < array.length; i++) {
			bubbleArray[i] = array[i];
		}
		
		return bubbleArray;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
