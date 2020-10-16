package dad.javafx.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application{
		
		//PROPERTIES
		private StringProperty tx_peso_Property = new SimpleStringProperty(this,"peso");
		private StringProperty tx_altura_Property = new SimpleStringProperty(this,"altura");
		private StringProperty lb_imc_Property = new SimpleStringProperty(this,"imc");
		private StringProperty lb_resultado_Property = new SimpleStringProperty(this,"resultado");
		
		private DoubleProperty peso_Property = new SimpleDoubleProperty(this,"valor_peso");
		private DoubleProperty altura_Property = new SimpleDoubleProperty(this,"valor_altura");
		private DoubleProperty imc_Property = new SimpleDoubleProperty(this,"valor_imc");
		
		//TEXTFIELD
		private TextField tx_peso = new TextField();
		private TextField tx_altura = new TextField();
		
		//LABELS
		private Label lb_peso = new Label();
		private Label lb_altura = new Label();
		private Label lb_kg = new Label();
		private Label lb_cm = new Label();
		private Label lb_formula = new Label();
		private Label lb_resultado = new Label();
		
		
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//TEXTFIELD
		tx_peso.setAlignment(Pos.CENTER);
		tx_peso.setMaxWidth(100);
		
		tx_altura.setAlignment(Pos.CENTER);
		tx_altura.setMaxWidth(100);
		
		//LABELS
		lb_resultado.setText("Bajo peso | Normal | Sobrepeso | Obeso");
		lb_peso.setAlignment(Pos.CENTER);
		lb_peso.setLayoutX(20);
		lb_peso.setLayoutY(80);
		////////////////////////////////////////
		lb_formula.setText("IMC: peso(kg) / altura^2 (m)");
		lb_peso.setAlignment(Pos.CENTER);
		lb_peso.setLayoutX(20);
		lb_peso.setLayoutY(80);
		////////////////////////////////////////
		lb_peso.setText("Peso: ");
		lb_peso.setAlignment(Pos.CENTER);
		lb_peso.setLayoutX(20);
		lb_peso.setLayoutY(80);
		////////////////////////////////////////
		lb_altura.setText("Altura: ");
		lb_altura.setAlignment(Pos.CENTER);
		lb_altura.setLayoutX(20);
		lb_altura.setLayoutY(80);
		////////////////////////////////////////
		lb_kg.setText(" kg");
		lb_cm.setText(" cm");
		
		//BINDINGS/////////////////
		tx_peso_Property.bindBidirectional(tx_peso.textProperty());
		Bindings.bindBidirectional(tx_peso_Property, peso_Property, new NumberStringConverter());
		peso_Property.addListener((o, ov, nv) -> calcular_IMC());
		
		tx_altura_Property.bindBidirectional(tx_altura.textProperty());
		Bindings.bindBidirectional(tx_altura_Property, altura_Property, new NumberStringConverter());
		altura_Property.addListener((o, ov, nv) -> calcular_IMC());
		
		
		lb_imc_Property.bindBidirectional(lb_formula.textProperty());
		lb_resultado_Property.bindBidirectional(lb_resultado.textProperty());
		imc_Property.addListener((o, ov, nv) -> actualizar_IMC());
		///////////////////////////
		
		
		HBox hb_peso = new HBox();
		HBox hb_altura = new HBox();
		HBox hb_formula = new HBox();
		HBox hb_resultado = new HBox();
		
		hb_peso.setSpacing(1);
		hb_altura.setSpacing(1);
		hb_formula.setSpacing(1);
		hb_resultado.setSpacing(1);
		
		
		hb_peso.setAlignment(Pos.BASELINE_CENTER);
		hb_altura.setAlignment(Pos.BASELINE_CENTER);
		hb_formula.setAlignment(Pos.BASELINE_CENTER);
		hb_resultado.setAlignment(Pos.BASELINE_CENTER);
		
		hb_peso.getChildren().addAll(lb_peso,tx_peso,lb_kg);
		hb_altura.getChildren().addAll(lb_altura,tx_altura,lb_cm);
		hb_formula.getChildren().addAll(lb_formula);
		hb_resultado.getChildren().addAll(lb_resultado);
		
		VBox root = new VBox();
		root.setSpacing(5);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(hb_peso,hb_altura,hb_formula,hb_resultado);
		
		Scene escena = new Scene(root, 300, 250);
		primaryStage.setScene(escena);
		primaryStage.setTitle("IMC");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void main (String[] ar) {
		launch(ar);
	}
	
	private void calcular_IMC() {
		if((peso_Property.get() == 0d) || (altura_Property.get() == 0d)) { 
			imc_Property.set(0d);
		}
		
		else {
			double peso = peso_Property.get();
			double altura = altura_Property.get();
			double imc = (peso / (altura * altura) ) * 10000d;
			imc_Property.set(imc);
		}
	}
	
	private void actualizar_IMC() {
		if(imc_Property.get()==0d) {
			lb_imc_Property.set("IMC: peso(kg) / altura^2 (m)");
			lb_resultado_Property.set("Bajo peso | Normal | Sobrepeso | Obeso");
		}
		
		else {
			lb_imc_Property.set("IMC: "+imc_Property.get());
			if(imc_Property.get()<18.5)
				lb_resultado_Property.set("Bajo Peso");
			else if(imc_Property.get()>=18.5 && imc_Property.get()<25)
				lb_resultado_Property.set("Normal");
			else if(imc_Property.get()>=25 && imc_Property.get()<30)
				lb_resultado_Property.set("Sobrepeso");
			else
				lb_resultado_Property.set("Obeso");
		}
	}
	
}
