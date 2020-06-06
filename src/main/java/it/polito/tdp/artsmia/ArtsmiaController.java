package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.db.Adiacenze;
import it.polito.tdp.artsmia.model.Artists;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi\n");
    	for (Adiacenze a : model.archi(boxRuolo.getValue()))
    		txtResult.appendText(a.toString());
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso\n");
   
    		int id = Integer.parseInt(txtArtista.getText());
    		if (model.vertici(boxRuolo.getValue()).contains(model.artisti().get(id))) {
    			for (Artists a : model.cerca(id)) {
    				txtResult.appendText(a.getName()+"\n");
    			}
    			txtResult.appendText("il peso è: "+model.peso());
    		}
    		else {
    			txtResult.appendText("inserire un numero corrispondente ad un artista");
    		}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo\n");
    	if(boxRuolo.getValue()==null) {
    		txtResult.appendText("Seleziona un ruolo");
    		return;
    	}
    	model.creaGrafo(boxRuolo.getValue());
    	txtResult.appendText(model.numVertici()+" vertici e "+model.numArchi()+" archi");
    }

    public void setModel(Model model) {
    	this.model = model;
    	boxRuolo.getItems().addAll(model.ruoli());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
