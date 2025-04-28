package HonorsUITests;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.*;
import javafx.scene.shape.*;

import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LensFlareTest extends Application {
	
	private File imgBackGroundFile;
	private ArrayList<File> imgFlaresFile;	//Organize the flare image files into array list
	
	private Image img_Background;
	private ArrayList<Image> img_Flare; //Organize the flare images into array list
	
	private ImageView imgView;
	private ArrayList<ImageView> imgViewFlare; //Organize the flare image views into array list
	
	private Circle dragPoint;
	private Circle circle;
	
	Line l1;
	Slider hSlide;
	Slider vSlide;
	
	@SuppressWarnings("exports")
	@Override
	public void start(Stage stage) {
		
		/**Background*/
		imgBackGroundFile = new File("C:\\Users\\matte\\OneDrive\\Pictures\\BlackForest_Winter.jpg");
		/**Lens Flare Effects*/
		imgFlaresFile = new ArrayList<>();	//File
		img_Flare = new ArrayList<>();		//Image
		imgViewFlare = new ArrayList<>();	//ImageView
		imgFlaresFile.add(new File("C:\\Users\\matte\\OneDrive\\Pictures\\LensFlare Images\\Hexagon_Flare_Filled.png"));
		imgFlaresFile.add(new File("C:\\Users\\matte\\OneDrive\\Pictures\\LensFlare Images\\Circle_Flare_Ring.png"));
		imgFlaresFile.add(new File("C:\\Users\\matte\\OneDrive\\Pictures\\LensFlare Images\\SunGlowFlare.png"));
		
		try {
			img_Background = new Image(imgBackGroundFile.toURI().toURL().toString());
			img_Flare.add(new Image(imgFlaresFile.get(0).toURI().toURL().toString()));
			img_Flare.add(new Image(imgFlaresFile.get(1).toURI().toURL().toString()));
			img_Flare.add(new Image(imgFlaresFile.get(2).toURI().toURL().toString()));
			
			imgView = new ImageView(img_Background);
			imgViewFlare.add(new ImageView(img_Flare.get(0)));		//Hexagon
			imgViewFlare.add(new ImageView(img_Flare.get(1)));	//Ring
			imgViewFlare.add(new ImageView(img_Flare.get(2)));	//Sun Flare
		}catch(Exception ex) {
			System.out.println("Image Not Found!");
		}
		
		/**Set circle parameters (circle is at the sun)*/
		circle = new Circle();
		circle.setFill(Color.TRANSPARENT);
		circle.setStroke(Color.WHITE);
		circle.setOpacity(0);	//Set the opacity to 0 from start
		
		dragPoint = new Circle(10);
		dragPoint.setFill(Color.GREEN);
		
		l1 = new Line();
		l1.setFill(Color.BLACK);
		
		/**Set the ImageView size*/
		imgView.setFitHeight(700);
		imgView.setFitWidth(2000);
		
		/**Sliders*/
		hSlide = new Slider();
		hSlide.setMin(0);
		hSlide.setMax(1000);
		hSlide.setValue(0);
		
		vSlide = new Slider(); vSlide.setOrientation(Orientation.VERTICAL);
		vSlide.setPrefHeight(100);
		vSlide.setMin(-10);
		vSlide.setMax(10);
		vSlide.setValue(0);
		
		/**Set the imgView X and Y Values*/
		imgView.setX(0);
		imgView.setY(0);
		
		/**Set imgViewFlare values (also get the center for the image since the coord. are set to upper-left**)*/
		//By default, the flares will be invisible
		imgViewFlare.get(0).setOpacity(0);
		imgViewFlare.get(1).setOpacity(0);
		imgViewFlare.get(1).setFitWidth(150); imgViewFlare.get(1).setFitHeight(150); //Scale by width and height not X and Y
		imgViewFlare.get(2).setOpacity(0);
		imgViewFlare.get(2).setFitWidth(900); imgViewFlare.get(2).setFitHeight(800);
				
		imgViewFlare.get(0).setFitHeight(100); imgViewFlare.get(0).setFitWidth(100);
		double hexagonFilledCenterX = imgViewFlare.get(0).getFitWidth() * 0.5;	//Hexagon Flare
		double hexagonFilledCenterY = imgViewFlare.get(0).getFitHeight() * 0.5;
		double ringCenterX = imgViewFlare.get(1).getFitWidth() * 0.5;			//Ring Flare
		double ringCenterY = imgViewFlare.get(1).getFitHeight() * 0.5;
		double sunCenterX = imgViewFlare.get(2).getFitWidth() * 0.5;			//Sun Flare
		double sunCenterY = imgViewFlare.get(2).getFitHeight() * 0.5;
		
		Pane pane = new Pane();
		pane.getChildren().add(imgView);
		pane.getChildren().add(imgViewFlare.get(0));
		pane.getChildren().add(imgViewFlare.get(1));
		pane.getChildren().add(imgViewFlare.get(2));
		pane.getChildren().add(circle);
		/**Debug the blueprints behind the scenes*/
		//pane.getChildren().add(dragPoint);
		//pane.getChildren().add(l1);
		
		HBox hBox = new HBox();
		hBox.getChildren().addAll(vSlide, hSlide);
		hBox.setAlignment(Pos.CENTER);
		
		BorderPane bPane = new BorderPane();
		bPane.setCenter(pane);
		bPane.setBottom(hBox);
		
		Scene scene = new Scene(bPane, 1000, 700);
		stage.setTitle("Lens Flare");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
		
		/**Set the circle centered at the sun*/
		circle.setCenterX((imgView.getFitWidth() * 0.64) - hSlide.getValue());
		circle.setCenterY((imgView.getFitHeight() * 0.1));
		
		dragPoint.setCenterX(hSlide.getValue());
		dragPoint.setCenterY(vSlide.getValue() + (imgView.getFitHeight() * 0.5));
		
		//Set the circle's radius to the distance from the circle's center to the dragPoint (1/4)
		circle.setRadius(getDistanceForRadius() * 0.25);
						
		//Set the line distance from the circle to dragPoint
		l1.setStartX(circle.getCenterX()); l1.setEndX(dragPoint.getCenterX());
		l1.setStartY(circle.getCenterY()); l1.setEndY(dragPoint.getCenterY());
		
		/**Flare Settings*/
		/*Flare Positions*/
		imgViewFlare.get(0).setX(changeUpperX(2) - hexagonFilledCenterX);	//First flare image (Hexagon) (center is using FilledCenter variables)
		imgViewFlare.get(0).setY(changeUpperY(2) - hexagonFilledCenterY);
		imgViewFlare.get(1).setX(changeLowerX(3) - ringCenterX);		//Flare 2 (Ring Flare)
		imgViewFlare.get(1).setY(changeLowerY(3) - ringCenterY);
		imgViewFlare.get(2).setX(circle.getCenterX() - sunCenterX);	//Flare 3 (Sun Flare)
		imgViewFlare.get(2).setY(circle.getCenterY() - sunCenterY);
		
		/*Flare Animation (Fading)*/
		FadeTransition fade0 = new FadeTransition();
		fade0.setDuration(Duration.millis(1));		//No need to set Nodes, only set the duration
		
		//Set the flare images X (or Y) coordinates using this combination:
		
		hSlide.valueProperty().addListener(e-> {
			imgView.setX(-hSlide.getValue());	//Invert it for user input
			
			dragPoint.setCenterX(hSlide.getValue());
			
			//Adjust the circle's X position as the slider shifts the background
			circle.setCenterX((imgView.getFitWidth() * 0.64) - hSlide.getValue());
			circle.setRadius(getDistanceForRadius() * 0.25);
						
			l1.setStartX(circle.getCenterX());
			l1.setEndX(dragPoint.getCenterX());
			
			//Update the flares' X-coord
			imgViewFlare.get(0).setX(changeUpperX(2) - hexagonFilledCenterX);	//Flare 1 Hexagon
			imgViewFlare.get(0).setY(changeUpperY(2) - hexagonFilledCenterY);
			imgViewFlare.get(1).setX(changeLowerX(3) - ringCenterX);		//Flare 2 (Ring Flare)
			imgViewFlare.get(1).setY(changeLowerY(3) - ringCenterY);
			imgViewFlare.get(2).setX(circle.getCenterX() - sunCenterX);	//Flare 3 (Sun Flare)
			imgViewFlare.get(2).setY(circle.getCenterY() - sunCenterY);
			
			/**For each flare in imgViewFlare*/
			//If hSlide.getValue() < 210, then the flare will be invisible: Use Fade Transition to make it more natural
			if(hSlide.getValue() < 210) {
				//System.out.println("< 210");
				fade0.setFromValue(fade0.getFromValue());
				fade0.setToValue(0);
				circle.setOpacity(fade0.getToValue());
				imgViewFlare.get(0).setOpacity(fade0.getToValue());
				imgViewFlare.get(1).setOpacity(fade0.getToValue());
				imgViewFlare.get(2).setOpacity(fade0.getToValue());
			}else if(hSlide.getValue() >= 210 && hSlide.getValue() < 500){
				//System.out.println(">= 210 && < 500");
				fade0.setFromValue(fade0.getFromValue());
				fade0.setToValue(0.5);
				circle.setOpacity(fade0.getToValue());
				imgViewFlare.get(0).setOpacity(fade0.getToValue());
				imgViewFlare.get(1).setOpacity(fade0.getToValue());
				imgViewFlare.get(2).setOpacity(fade0.getToValue() * 1.25);
			}else if(hSlide.getValue() >= 500 && hSlide.getValue() < 750){
				//System.out.println(">= 500 && < 750");
				fade0.setFromValue(fade0.getFromValue());
				fade0.setToValue(1);
				circle.setOpacity(fade0.getToValue());
				imgViewFlare.get(0).setOpacity(fade0.getToValue());
				imgViewFlare.get(1).setOpacity(fade0.getToValue());
				imgViewFlare.get(2).setOpacity(fade0.getToValue());
			}else if(hSlide.getValue() >= 750){
				//System.out.println(">= 750");
				fade0.setFromValue(fade0.getFromValue());
				fade0.setToValue(0.5);
				circle.setOpacity(fade0.getToValue());
				imgViewFlare.get(0).setOpacity(fade0.getToValue());
				imgViewFlare.get(1).setOpacity(fade0.getToValue());
				imgViewFlare.get(2).setOpacity(fade0.getToValue() * 1.5);
			}			
			fade0.play();
			
		});
		vSlide.valueProperty().addListener(e-> {
			imgView.setY(vSlide.getValue());
			
			//dragPoint.setCenterY((imgView.getFitHeight() * 0.5) - vSlide.getValue());
			
			//Adjust the circle's Y position as the slider shifts the background
			circle.setCenterY((imgView.getFitHeight() * 0.1) + vSlide.getValue());
			circle.setRadius(getDistanceForRadius() * 0.25);
			
			l1.setStartY(circle.getCenterY());
			l1.setEndY(dragPoint.getCenterY());
			
			//Update The lensFlares' Y-coord
			imgViewFlare.get(0).setX(changeUpperX(2) - hexagonFilledCenterX);	//Flare 1 Hexagon
			imgViewFlare.get(0).setY(changeUpperY(2) - hexagonFilledCenterY);
			imgViewFlare.get(1).setX(changeLowerX(3) - ringCenterX);		//Flare 2 (Ring Flare)
			imgViewFlare.get(1).setY(changeLowerY(3) - ringCenterY);
			imgViewFlare.get(2).setX(circle.getCenterX() - sunCenterX);	//Flare 3 (Sun Flare)
			imgViewFlare.get(2).setY(circle.getCenterY() - sunCenterY);
		});
		
	}
	
	/**Method is used to calculate the distance between the dragPoint and the circle object's centerX and centerY*/
	public double getDistanceForRadius() {
		double radius = Math.sqrt((dragPoint.getCenterY() - circle.getCenterY())*(dragPoint.getCenterY() - circle.getCenterY())+
				((dragPoint.getCenterX() - circle.getCenterX())*(dragPoint.getCenterX() - circle.getCenterX())));
		
		return radius;
	}
	
	/**Adjust the images in the upper or lower bounds of the line*/
			//Change upper is above the midPoint
	public double changeUpperX(int value) {
		int divisor = (int) Math.pow(2, value);
		double result = 0;

		for (int i = 1; i < divisor; i++) {
			result = (l1.getEndX() + (l1.getStartX() * (i - 1))) / i;
		}
		return result;
	}
	public double changeUpperY(int value) {
		int divisor = (int) Math.pow(2, value);
		double result = 0;

		for (int i = 1; i < divisor; i++) {
			result = (l1.getEndY() + (l1.getStartY() * (i - 1))) / i;
		}
		return result;
	}
		//Change lower is below the midpoint (closer to dragPoint)
	public double changeLowerX(int value) {
		int divisor = (int) Math.pow(2, value);
		double result = 0;

		for (int i = 1; i < divisor; i++) {
			result = (l1.getStartX() + (l1.getEndX() * (i - 1))) / i;
		}
		return result;
	}
	public double changeLowerY(int value) {
		int divisor = (int) Math.pow(2, value);
		double result = 0;

		for (int i = 1; i < divisor; i++) {
			result = (l1.getStartY() + (l1.getEndY() * (i - 1))) / i;
		}
		return result;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
