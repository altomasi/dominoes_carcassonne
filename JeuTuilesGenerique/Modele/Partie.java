package JeuTuilesGenerique.Modele;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import JeuCarcassonne.PartieCarcassonne;

public class Partie implements Serializable {

    public Joueurs joueurs ;
    public Plateau plateau;
    public Pioche pioche;
    public Tuile aJouer;
    public String nomPartie ;

    public Partie(Joueurs joueurs, Plateau plateau, Pioche pioche, String nomPartie) {
        this.joueurs = joueurs;
        this.plateau = plateau;
        this.pioche = pioche;
        this.nomPartie = nomPartie;
        nouvelleTuileAjouer();
    }

    public Partie(String nomPartie){
        joueurs = new Joueurs() ;
        this.nomPartie = nomPartie ;
        plateau = new Plateau(5,5);
    }

    public void nouvelleTuileAjouer() {
        aJouer = pioche.pickOne();
        if (aJouer == null) aJouer = new Tuile();
    }

    public boolean check(Tuile t, int x, int y) {
        Bord bordAuNord = getBordAuNord(t, x, y);
        Bord bordAlOuest = getBordAuNord(t, x, y);
        Bord bordAuSud = getBordAuNord(t, x, y);
        Bord bordAlEst = getBordAuNord(t, x, y);
        // On ne peut pas poser une tuile si elle n'est adjacente à aucune autre tuile.
        if (bordAuNord == null && bordAlOuest == null && bordAuSud == null && bordAlEst == null)
            return false;
        if (!(t.nord.estCompatibleAvec(bordAuNord))) return false;
        if (!(t.ouest.estCompatibleAvec(bordAlOuest))) return false;
        if (!(t.sud.estCompatibleAvec(bordAuSud))) return false;
        if (!(t.est.estCompatibleAvec(bordAlEst))) return false;
        return true;
    }

    public void jouer(Tuile t, int x, int y) {
        if (check(t, x, y)) plateau.plateau[x][y] = t;
    }

    public boolean partieFinie() {
        return pioche.pioche.isEmpty();
    }

    public Joueurs getJoueurs() {return joueurs;}
    public String getNomPartie() {return nomPartie;}

    public Bord getBordAuNord(Tuile t, int x, int y) {
        return plateau.plateau[x-1][y].sud;
    }

    public Bord getBordAlOuest(Tuile t, int x, int y) {
        return plateau.plateau[x][y+1].est;
    }

    public Bord getBordAuSud(Tuile t, int x, int y) {
        return plateau.plateau[x+1][y].nord;
    }

    public Bord getBordAlEst(Tuile t, int x, int y) {
        return plateau.plateau[x][y-1].ouest;
    }

    public void save(){
        String path = "Sauvegarde/" + (this instanceof PartieCarcassonne ? "Carcassonne/" : "Domino/") ;
                    // enregistrer un objet
            try {  
                //Saving of object in a file
                FileOutputStream file = new FileOutputStream(path+ this.getNomPartie());
                ObjectOutputStream out = new ObjectOutputStream(file);
                
                // Method for serialization of object
                out.writeObject(this);
                out.close();
                file.close();
                
                System.out.println("Object has been serialized");
            }
            
            catch(IOException ex) {
                System.out.println(ex); 
            }
    }
}