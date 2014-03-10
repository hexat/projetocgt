package gui;

/*
 * package GUI;
 * 
 * import javax.swing.*;
 * 
 * import CPN_Classes.ElementosCPN;
 * 
 * import estruturaRP.Place;
 * 
 * import java.awt.*; import java.net.*; import java.applet.*; import
 * java.util.*;
 * 
 * 
 * public class DrawImage extends Applet {
 * 
 * private Image imagePlace[]; Vector p; ElementosCPN eCPN;
 * 
 * private URL getURL (String filename){ URL url = null;
 * 
 * try { url = this.getClass().getResource(filename); } catch (Exception e) {
 * System.out.println(e.toString()); }
 * 
 * return url; }
 * 
 * public void init() {
 * 
 * eCPN = new ElementosCPN (); p = eCPN.getPlaces(); imagePlace = new
 * Image[p.size()];
 * 
 * Place currentPlace = null; for (int i = 0; i<imagePlace.length; i++){
 * currentPlace = (Place) p.get(i); imagePlace[i] =
 * this.getImage(getURL(currentPlace.getText()+".png"));
 * 
 * System.out.println(currentPlace.getText()+".png");
 * 
 * }
 * 
 * }
 * 
 * public void paint (Graphics g) { Graphics2D g2d = (Graphics2D) g;
 * 
 * g2d.setColor(Color.BLACK); g2d.fillRect(0, 0, getSize().width,
 * getSize().height);
 * 
 * Place currentPlace = null; int x = 0, y = 0; for (int i = 0;
 * i<imagePlace.length; i++){ currentPlace = (Place) p.get(i); x =
 * currentPlace.getRelativeLeft()*150; y = currentPlace.getRelativeTop()*150; //
 * System.out.println("(teste no paint)-> x:"+x+" | y:"+y);
 * 
 * g2d.drawImage(imagePlace[i], x, y, this); }
 * 
 * 
 * 
 * 
 * } }
 */