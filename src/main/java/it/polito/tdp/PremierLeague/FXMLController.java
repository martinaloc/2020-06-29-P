/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Adiacenze;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<Month> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<?> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<?> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    
    	 List<Adiacenze> list =	this.model.connessioniMax(this.cmbMese.getValue().getValue());
    	 Collections.sort(list);
 	    if(list==null)
 	    {
 	    	this.txtResult.appendText("Errore lettura connessioni max\n");
 			return;
 	    }
 	    for(Adiacenze a : list)
 	    {
 	    	this.txtResult.appendText(a.toString()+"\n");
 	    	
 	    }
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	if(this.cmbMese.getValue()==null){
    		this.txtResult.appendText("Scegliere un mese!\n");
    		return;
    	}
    	
    	int mese = this.cmbMese.getValue().getValue();
    	this.txtResult.appendText("mese:"+ this.cmbMese.getValue().getValue()+"\n");
    	
    	String s = this.txtMinuti.getText();
    	if(s==null){
    		this.txtResult.appendText("Inserire i minuti!\n");
    		return;	
    	}
    	
    	int min;
    	try {
    		min= Integer.parseInt(s);
    	}
    	catch (NumberFormatException e) {
    		this.txtResult.appendText("Formato non valido!\n");
    		return;	
		}
    	this.model.creaGrafo(min,mese);
    	
    	if(this.model.setV().size()==0){
    		this.txtResult.appendText("Non sono state giocate partire in questo mese!\n");
    		return;		
    	}
		this.txtResult.appendText("VERTEX:"+ this.model.setV().size()+"\n");
    	
		this.txtResult.appendText("EDGE:"+ this.model.setE().size()+"\n");
		btnConnessioneMassima.setDisable(false);
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	 
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Month> list= new ArrayList<>();
        for(int i=1;i <=12;i++)
        {
        list.add(Month.of(i));	
        }
        this.cmbMese.getItems().addAll(list);
        btnConnessioneMassima.setDisable(true);
    }
    
    
}
