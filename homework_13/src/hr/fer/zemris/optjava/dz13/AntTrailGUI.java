package hr.fer.zemris.optjava.dz13;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

import hr.fer.zemris.optjava.dz13.Simulation.BooleanW;
import hr.fer.zemris.optjava.dz13.util.AlgConst;

public class AntTrailGUI extends Application {
	public static final CountDownLatch latch = new CountDownLatch(1);
	public static AntTrailGUI antTrailGUI = null;
	private static BooleanW monitor;
	private Canvas canvas = new Canvas(AlgConst.CANVAS_WIDTH, AlgConst.CANVAS_HEIGHT);

	public static AntTrailGUI waitForAntTrailGUI() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return antTrailGUI;
	}

	public static void setAntTrailGUI(AntTrailGUI antTrailGUI0) {
		antTrailGUI = antTrailGUI0;
		latch.countDown();
	}

	public AntTrailGUI() {
		setAntTrailGUI(this);
	}

	public void displayIteration(String data) {
		// display data
		GraphicsContext gc=canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, AlgConst.CANVAS_WIDTH, AlgConst.CANVAS_HEIGHT);
		int matrixDim=data.indexOf('\n');
		int charAt=0;
		int elementQuant=(int)(AlgConst.CANVAS_HEIGHT/matrixDim);
		
		for(int i=0;i<matrixDim;i++){
			for(int j=0;j<matrixDim;j++){
				if(data.charAt(charAt)=='1'){
					//mark food
					gc.setFill(Paint.valueOf("CADETBLUE"));
					gc.fillRect(j*elementQuant,i*elementQuant, elementQuant, elementQuant);
				}else if(data.charAt(charAt)=='N' || data.charAt(charAt)=='E' || data.charAt(charAt)=='S' || data.charAt(charAt)=='W'){
					//mark agent
					//ant
					gc.setFill(Paint.valueOf("CHARTREUSE"));
					gc.fillRect(j*elementQuant,i*elementQuant, elementQuant, elementQuant);
					//view
					gc.setFill(Paint.valueOf("BLUE"));
					
					if(data.charAt(charAt)=='N' ){
						gc.fillRect(j*elementQuant+0,i*elementQuant+0, elementQuant, (int)(elementQuant/4));
						
					}else if(data.charAt(charAt)=='E'){
						gc.fillRect(j*elementQuant+(int)(3*elementQuant/4),i*elementQuant, (int)(elementQuant/4), elementQuant);
						
					}else if(data.charAt(charAt)=='S'){
						gc.fillRect(j*elementQuant,i*elementQuant+(int)(3*elementQuant/4), elementQuant, (int)(elementQuant/4));
						
					}else {
						gc.fillRect(j*elementQuant,i*elementQuant, (int)(elementQuant/4), elementQuant);
					}
				}
				charAt++;
			}
			charAt++;
		}
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane root = new BorderPane();
		VBox vbox = new VBox();
		root.setCenter(vbox);
		vbox.setStyle("-fx-background-color: green");

		Button nextStep = new Button("Iduci korak");
		nextStep.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (monitor != null)
					synchronized (monitor) {
						monitor.notify();
					}
			}
		});
		vbox.getChildren().add(nextStep);
		vbox.getChildren().add(canvas);

		Scene scene = new Scene(root, AlgConst.CANVAS_WIDTH, AlgConst.CANVAS_HEIGHT);
		stage.setTitle("AntTrail");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void monitorLink(BooleanW monitor2) {
		monitor = monitor2;
	}

	@Override
	public void stop() throws Exception {
		synchronized (monitor) {
			if (monitor != null) {
				try {
					monitor.notify();
					monitor.setB(true);
				} catch (IllegalMonitorStateException e) {
					
				}
			}
		}
		super.stop();
	}

}