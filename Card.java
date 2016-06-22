package hellotvxlet;

import java.awt.Image;
import java.awt.MediaTracker;
import org.havi.ui.HGraphicButton;
import org.havi.ui.HVisible;

public class Card extends HGraphicButton
{
    Image img, imgback;
    String strimg;
    boolean clickable;
    
     Card(String strimg, int x, int y, int i1, int i2) {
         super();
         
        this.strimg = strimg; 
        img= this.getToolkit().getImage(strimg);
        imgback=  this.getToolkit().getImage("back.jpg");
        clickable = true;
        
         MediaTracker mt=new MediaTracker(this);
         mt.addImage(img, 1);
         mt.addImage(imgback, 2);
         
        try {
            mt.waitForAll();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
         
         this.setBounds(x, y, i1, i2);
      
         showBack();
    }
     
     public void showBack()
     {
            this.setGraphicContent(imgback, HVisible.NORMAL_STATE);
     }
     
     public void showFront()
     {
            this.setGraphicContent(img, HVisible.NORMAL_STATE);
     }
     
     public String getStrimg()
     {
            return this.strimg;
     }     
     
      public void setClickable(boolean i)
     {
            this.clickable = i;
     }   
     

}
