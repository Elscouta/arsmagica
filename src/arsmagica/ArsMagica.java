/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arsmagica;

import gui.AdminScreen;
import gui.SetupScreen;
import gui.TitleScreen;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFileChooser;
import model.Ability;
import model.Art;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import model.Action;
import model.CovenantOption;
import model.Effect;
import model.Event;

import model.GameData;
import model.Situation;
import model.Variable;
import model.VirtueFlaw;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Chaha
 */
public class ArsMagica {

    static TitleScreen titleScreen;
    static SetupScreen setupScreen;
    static ArrayList<GameData> gameData;
    public HashSet<CovenantOption> covOpts;
    public HashSet<VirtueFlaw> virtuesFlaws;
    public HashSet<Event> events;
    public HashSet<Effect> effects;
    public HashSet<Action> actions;
    public HashSet<Situation> situations;
    static AdminScreen adminScreen;
    public DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();;
    public DocumentBuilder builder;
    public Document document;
    public File dataFile = new File("gameData.xml");
    
    XPathFactory xpf = XPathFactory.newInstance();
    XPath path = xpf.newXPath();
        
    

    public ArsMagica() {
        titleScreen = new TitleScreen(this);
        covOpts = new HashSet<>();
        virtuesFlaws = new HashSet<>();
        events = new HashSet<>();
        effects = new HashSet<>();
        actions = new HashSet<>();
        situations = new HashSet<>();
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(dataFile);
        } catch (final ParserConfigurationException e){
            e.printStackTrace();
        } catch (final SAXException e){
            e.printStackTrace();
        } catch (final IOException e){
            e.printStackTrace();
        }
        initializeXML();
        
        System.out.print("Les options d'Alliance :");
        for(CovenantOption co : covOpts){
            System.out.print("\n "+co.toString()+",");
        } System.out.print("\n");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArsMagica main = new ArsMagica();
        titleScreen.setVisible(true);
        gameData = new ArrayList<>();
        
    }
    
    private void initializeXML(){
        try{
            Node root = document.getDocumentElement();
            
            NodeList covOptNodes = ((Node)path.evaluate("CovenantOptions", root, XPathConstants.NODE)).getChildNodes();
            System.out.print("il y a "+covOptNodes.getLength()+" Options d'Alliance dans le XML.\n");
            for(int i = 0;i<covOptNodes.getLength();i++){
                saveCovOpt(covOptNodes.item(i));
            }
            
            NodeList virtuesFlawsNodes = ((Node)path.evaluate("VirtuesFlaws", root, XPathConstants.NODE)).getChildNodes();
            System.out.print("il y a "+virtuesFlawsNodes.getLength()+" VertusVices dans le XML.\n");
            for(int i = 0;i<virtuesFlawsNodes.getLength();i++){
                saveVirtueFlaw(virtuesFlawsNodes.item(i));
            }
            
            NodeList effectNodes = ((Node)path.evaluate("Effects", root, XPathConstants.NODE)).getChildNodes();
            for(int i = 0;i<effectNodes.getLength();i++){
                saveEffect(effectNodes.item(i));
            }

            NodeList eventNodes = ((Node)path.evaluate("Events", root, XPathConstants.NODE)).getChildNodes();
            System.out.print("il y a "+eventNodes.getLength()+" Evenements dans le XML.\n");
            for(int i = 0;i<eventNodes.getLength();i++){
                saveEvent(eventNodes.item(i));
            }
            
            NodeList actionNodes = ((Node)path.evaluate("Actions", root, XPathConstants.NODE)).getChildNodes();
            for(int i = 0;i<actionNodes.getLength();i++){
                saveAction(actionNodes.item(i));
            }
            
            NodeList situationNodes = ((Node)path.evaluate("Situations", root, XPathConstants.NODE)).getChildNodes();
            for(int i = 0;i<situationNodes.getLength();i++){
                saveSituation(situationNodes.item(i));
            }


            } catch (XPathExpressionException e){
                e.printStackTrace();
            }
    }
    
    public void saveCovOpt(Node element){
        try{
            String name = (String)path.evaluate("@name", element);
            String cost = (String)path.evaluate("@cost", element);
            String type = (String)path.evaluate("@type", element);
            covOpts.add(new CovenantOption(name,cost,type));
        } catch(XPathExpressionException e){ e.printStackTrace();}
    }
    
    public void saveVirtueFlaw(Node element){
        try{
            String name = (String)path.evaluate("@name", element);
            String cost = (String)path.evaluate("@cost", element);
            String type = (String)path.evaluate("@type", element);
            virtuesFlaws.add(new VirtueFlaw(name,cost,type));
        } catch(XPathExpressionException e){ e.printStackTrace();}
    }
    
    public void saveEffect(Node element){
         try{
            String name = (String)path.evaluate("@name", element);
            String type = (String)path.evaluate("@type", element);
            boolean displayed = (Boolean)path.evaluate("@displayed", element,XPathConstants.BOOLEAN);
            switch(type){
                case "ActionChoice":
                    break;
                case "ChoiceEffect":
                    break;
                case "EffectAmongN":
                    break;
                case "EffectList":
                    break;
                case "EventChange":
                    break;
                case "SituationChange":
                    break;
                case "TestEffect":
                    break;
                case "ValueChange":
                    break;
                case "VariableChange":
                    break;
            }
        } catch(XPathExpressionException e){ e.printStackTrace();}
    }
    
    public void saveSituation (Node element){
        try{
            String name = (String)path.evaluate("@name", element);
            Situation situation = new Situation(name);
            situations.add(situation);
            NodeList actions = (NodeList)path.evaluate("actions", element, XPathConstants.NODESET);
            for(int i = 0;i<actions.getLength();i++){
                situation.possibleActions.add(findAction((String)path.evaluate("@name", actions.item(i), XPathConstants.STRING)));
            }
            NodeList freqEvents = (NodeList)path.evaluate("freqEvents", element, XPathConstants.NODESET);
            for(int i = 0;i<freqEvents.getLength();i++){
                int meanDayInterval = (Integer)path.evaluate("@frequence", freqEvents.item(i), XPathConstants.NUMBER);
                Event event = findEvent((String)path.evaluate("@event", freqEvents.item(i), XPathConstants.STRING));
                situation.addFreqEvent(meanDayInterval, event);
            }
            NodeList variables = (NodeList)path.evaluate("variables", element, XPathConstants.NODESET);
            for(int i = 0;i<variables.getLength();i++){
                situation.variables.add(new Variable((String)path.evaluate("@name", variables.item(i), XPathConstants.STRING)));
            }
        } catch(XPathExpressionException e){ e.printStackTrace();}
    }
    
    public void saveAction (Node element){
        try{
            String name = (String)path.evaluate("@name", element);
            String text = (String)path.evaluate("@text", element);
            Action action = new Action(name,text);
            actions.add(action);
            NodeList consequences = (NodeList)path.evaluate("consequences", element, XPathConstants.NODESET);
            for(int i = 0;i<consequences.getLength();i++){
                action.consequences.add(findEffect((String)path.evaluate("@name", consequences.item(i), XPathConstants.STRING)));
            }
            NodeList costs = (NodeList)path.evaluate("costs", element, XPathConstants.NODESET);
            for(int i = 0;i<costs.getLength();i++){
                action.costs.add(findEffect((String)path.evaluate("@name", costs.item(i), XPathConstants.STRING)));
            }
        } catch(XPathExpressionException e){ e.printStackTrace();}
    }
    
    public void saveEvent(Node element){
        try{
            String name = (String)path.evaluate("@name", element);
            String effect = (String)path.evaluate("@effect", element);
            events.add(new Event(name,findEffect(effect)));
        } catch(XPathExpressionException e){ e.printStackTrace();}
    }
    
    public Effect findEffect(String name){
        for(Effect e :effects){
            if(e.name.equals(name)){
                return e;
            }
        }
        return null;
    }
    
    public Action findAction(String name){
        for(Action a : actions){
            if(a.name.equals(name)){
                return a;
            }
        }
        return null;
    }
    
    public Event findEvent(String name){
        for(Event e : events){
            if(e.name.equals(name)){
                return e;
            }
        }
        return null;
    }
    
    public void newGame (){
        GameData currentGameData = new GameData();
        gameData.add(currentGameData);
        initializeAbilities(currentGameData);
        initializeArts(currentGameData);
        titleScreen.setVisible(false);
        setupScreen = new SetupScreen(currentGameData);
        setupScreen.setVisible(true);
    }
    
    public void adminStart(){
        adminScreen = new AdminScreen();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int returnVal = fileChooser.showOpenDialog(titleScreen);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            dataFile = fileChooser.getSelectedFile();
        }
        adminScreen.setVisible(true);
    }
    
    // initialisation des Compétences existantes
    private void initializeAbilities(GameData gameData){
        gameData.abilities.add(new Ability("Artisanat spécifique",true ,"Générale"));
        gameData.abilities.add(new Ability("Athlétisme", true,"Générale"));
        gameData.abilities.add(new Ability("Attention", true,"Générale"));
        gameData.abilities.add(new Ability("Bagarre", true,"Générale"));
        gameData.abilities.add(new Ability("Charme", true,"Générale"));
        gameData.abilities.add(new Ability("Chasse", true,"Générale"));
        gameData.abilities.add(new Ability("Chirurugie", false ,"Générale"));
        gameData.abilities.add(new Ability("Commandement", true,"Générale"));
        gameData.abilities.add(new Ability("Concentration", true,"Générale"));
        gameData.abilities.add(new Ability("Connaissance d'une organisation", true,"Générale"));
        gameData.abilities.add(new Ability("Connaissance des animaux", true,"Générale"));
        gameData.abilities.add(new Ability("Connaissance des gens", true,"Générale"));
        gameData.abilities.add(new Ability("Connaissance d'un domaine", false ,"Générale"));
        gameData.abilities.add(new Ability("Discrétion", true,"Générale"));
        gameData.abilities.add(new Ability("Doigts agiles", false ,"Générale"));
        gameData.abilities.add(new Ability("Enseignement", true,"Générale"));
        gameData.abilities.add(new Ability("Equitation", true,"Générale"));
        gameData.abilities.add(new Ability("Etiquette", true,"Générale"));
        gameData.abilities.add(new Ability("Intrigue", true,"Générale"));
        gameData.abilities.add(new Ability("Langue spécifique", false ,"Générale"));
        gameData.abilities.add(new Ability("Marchandage", true,"Générale"));
        gameData.abilities.add(new Ability("Musique", true,"Générale"));
        gameData.abilities.add(new Ability("Natation", true,"Générale"));
        gameData.abilities.add(new Ability("Profession spécifique", true,"Générale"));
        gameData.abilities.add(new Ability("Ripaille", true,"Générale"));
        gameData.abilities.add(new Ability("Survie", true,"Générale"));
        gameData.abilities.add(new Ability("Tromperie", true,"Générale"));
        gameData.abilities.add(new Ability("Arts Libéraux", false ,"Académique"));
        gameData.abilities.add(new Ability("Droit Civil", false ,"Académique"));
        gameData.abilities.add(new Ability("Droit Canon", false ,"Académique"));
        gameData.abilities.add(new Ability("Droit Commun", false ,"Académique"));
        gameData.abilities.add(new Ability("Langue ancienne spécifique", false ,"Académique"));
        gameData.abilities.add(new Ability("Médecine", false ,"Académique"));
        gameData.abilities.add(new Ability("Philosophies", false ,"Académique"));
        gameData.abilities.add(new Ability("Théologies", false ,"Académique"));
        gameData.abilities.add(new Ability("Connaissance du démoniaque", false ,"Mystique"));
        gameData.abilities.add(new Ability("Connaissance du féérique",false ,"Mystique"));
        gameData.abilities.add(new Ability("Connaissance du magique",false ,"Mystique"));
        gameData.abilities.add(new Ability("Connaissance du divin",false ,"Mystique"));
        gameData.abilities.add(new Ability("Droit Hermétique",false ,"Mystique"));
        gameData.abilities.add(new Ability("Finesse",true ,"Mystique"));
        gameData.abilities.add(new Ability("Parma Magica",false ,"Mystique"));
        gameData.abilities.add(new Ability("Pénétration",true ,"Mystique"));
        gameData.abilities.add(new Ability("Théorie de la Magie",false ,"Mystique"));
        gameData.abilities.add(new Ability("Archerie",true ,"Martial"));
        gameData.abilities.add(new Ability("Armes à deux mains",true ,"Martial"));
        gameData.abilities.add(new Ability("Armes à une main",true ,"Martial"));
        gameData.abilities.add(new Ability("Armes de jet",true ,"Martial"));
        gameData.abilities.add(new Ability("Changeforme", false ,"Surnaturelle"));
        gameData.abilities.add(new Ability("Double Vue", false ,"Surnaturelle"));
        gameData.abilities.add(new Ability("Empathie avec les animaux", false ,"Surnaturelle"));
        gameData.abilities.add(new Ability("Hypnotisme", false ,"Surnaturelle"));
        gameData.abilities.add(new Ability("Musique enchanteresse", false ,"Surnaturelle"));
        gameData.abilities.add(new Ability("Perception du divin", false ,"Surnaturelle"));
        gameData.abilities.add(new Ability("Prémonitions", false ,"Surnaturelle"));
        gameData.abilities.add(new Ability("Sens de la nature", false ,"Surnaturelle"));
        gameData.abilities.add(new Ability("Sensibilité à la magie", false ,"Surnaturelle"));
        gameData.abilities.add(new Ability("Sourcier", false ,"Surnaturelle"));
    }
    
    // initialisation des Arts magiques existants
    private void initializeArts(GameData gameData){
        gameData.arts.add(new Art("Creo","Technique"));
        gameData.arts.add(new Art("Intellego","Technique"));
        gameData.arts.add(new Art("Muto","Technique"));
        gameData.arts.add(new Art("Perdo","Technique"));
        gameData.arts.add(new Art("Rego","Technique"));
        gameData.arts.add(new Art("Animal","Forme"));
        gameData.arts.add(new Art("Aquam","Forme"));
        gameData.arts.add(new Art("Auram","Forme"));
        gameData.arts.add(new Art("Corpus","Forme"));
        gameData.arts.add(new Art("Herbam","Forme"));
        gameData.arts.add(new Art("Ignem","Forme"));
        gameData.arts.add(new Art("Imaginem","Forme"));
        gameData.arts.add(new Art("Mentem","Forme"));
        gameData.arts.add(new Art("Terram","Forme"));
        gameData.arts.add(new Art("Vim","Forme"));
    }
}
