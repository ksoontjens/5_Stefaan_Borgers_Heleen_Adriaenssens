package hellotvxlet;

import java.util.Random;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.event.*;
import java.awt.event.*;




public class HelloTVXlet implements Xlet, HActionListener {
    
   
    Card FirstCard, SecondCard;
    boolean showfront13= false;
    boolean gameOver = false;
    Card CardSelection[] = new Card[2];
    int CardState=0;
        // 0: niks ongedraaid
        // 1: één kaart omgedraaid
        // 2: twee kaarten omgedraaid
    HScene scene;
    Card CardArray[][] = new Card[4][4];

    
    
    
    public void initXlet(XletContext ctx) throws XletStateChangeException {
        
   
    scene= HSceneFactory.getInstance().getDefaultHScene();
    int x,y;
    int completed = 0;
    Random R = new Random();
    
    for (int j=0; j<2; j++)
        {
            for ( int i=1;i<9;i++)
            {
        
                do {    
                x = R.nextInt(4);
                y = R.nextInt(4);
                }while (CardArray[x][y] != null);
   
            CardArray[x][y] = new Card("Card" + i + ".jpg", x*100, y*100, 100, 100);
            scene.add(CardArray[x][y]); 
            }
    
        }
    
     for (int X = 0; X<4; X++)
     for (int Y=0;Y<4;Y++)
        {
          Card links=null, rechts=null,boven=null, onder=null;
      
          if (X>0) links=CardArray[X-1][Y];
          if (X<3) rechts=CardArray[X+1][Y];
          if (Y>0) boven=CardArray[X][Y-1];
          if (Y<3) onder=CardArray[X][Y+1];
      
          CardArray[X][Y].setFocusTraversal(boven, onder, links, rechts);
      
          scene.validate();
          scene.setVisible(true);
    
          CardArray[X][Y].requestFocus();

          CardArray[X][Y].setActionCommand("card"+X+Y+"_clicked");
          CardArray[X][Y].addHActionListener(this);
    
                while (gameOver)
                {
                       System.out.println("please select your first card:");
                       int xcoord = 0, ycoord = 0;
                       
                       //WACHTEN OP ENTER
                       while(CardArray[X][Y].getActionCommand() == null)
                       {
                            xcoord = Integer.parseInt(CardArray[X][Y].getActionCommand().substring(4,5));
                            ycoord = Integer.parseInt(CardArray[X][Y].getActionCommand().substring(5,6));
                       }
                       
                        
                       CardSelection[0] = CardArray[xcoord][ycoord];
                       System.out.println("Selection 1 = " + CardSelection[0].strimg);
                       
                       //WACHTEN OP TWEEDE ENTER
                       while(CardArray[X][Y].getActionCommand() == null)
                       {  
                            System.out.println("please select your second card:");
                            int xcoord2 = Integer.parseInt(CardArray[X][Y].getActionCommand().substring(4,5));
                            int ycoord2 = Integer.parseInt(CardArray[X][Y].getActionCommand().substring(5,6));

                            CardSelection[1] = CardArray[xcoord2][ycoord2];
                            System.out.println("Selection 2 = " + CardSelection[1].strimg);
                       }
                       
                        boolean result = compare(CardSelection[0], CardSelection[1]);
           
                        if(result == false)
                        {
                            CardSelection[0].showBack();
                            CardSelection[1].showBack();
                
                            //Clear card selection
                            CardSelection[0] = null;
                            CardSelection[1] = null;
                        }
                        else
                        {
                            CardSelection[0].setClickable(false);
                            CardSelection[1].setClickable(false);
                            CardSelection[0].removeHActionListener(this);
                            CardSelection[1].removeHActionListener(this);
                            completed++;
                
                            //Clear card selection
                            CardSelection[0] = null;
                            CardSelection[1] = null;
                        }
           
                       gameOver = false;
                }
               
            }

       
    }

    public void pauseXlet() {

    }

    public void startXlet() throws XletStateChangeException {

    }
    
    public boolean compare(Card firstcard, Card secondcard)
    {
        if(firstcard.getStrimg().equalsIgnoreCase(secondcard.getStrimg()))
        {
            return true;   
        }
        
        else
        {
            return false;   
        }      
    }
            

    public void actionPerformed(ActionEvent e) {
        int X;
        int Y;
        
        System.out.println("HIER");
        System.out.println(e.toString());
        
        X = Integer.parseInt(e.getActionCommand().substring(4,5));
        Y = Integer.parseInt(e.getActionCommand().substring(5,6));
       System.out.println("X =" + X + "Y = " + Y);

       
          switch (CardState)
          {
                case  0:  CardArray[X][Y].showFront();
                        FirstCard=CardArray[X][Y];
                        CardState=1; 
                      break;
                case  1:  CardArray[X][Y].showFront();
                        SecondCard=CardArray[X][Y];
                        if (FirstCard.strimg.equals(SecondCard.strimg))
                        {
                            System.out.println("De kaarten zijn gelijk");
                            CardState=2;
                        }
                        else
                        {
                            System.out.println("De kaarten zijn niet gelijk");
                            CardState=3;
                        }
                        break;
              case 2:
                        CardState=0;
                        break;
                        
              case 3:
                        FirstCard.showBack();
                        SecondCard.showBack();
                        CardState=0;
                        break;
          }
          
       System.out.println("Nieuwe CardState="+CardState);
       }

    public void destroyXlet(boolean unconditional) throws XletStateChangeException {
     
    }

      
    }

