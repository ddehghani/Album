import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class Album extends JFrame implements ActionListener {
	public static final int imageWidth = 200, imageHeight = 200, timeForEachPicInSec = 3;
	
	public ArrayList<Album> images = new ArrayList<Album>();

	public Album() {}

	public Album(String picturePath) {
		//resize image and add it
		ImageIcon imageIcon = new ImageIcon(picturePath); // load the image to a imageIcon
		Image image = imageIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(imageWidth, imageHeight,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imageIcon = new ImageIcon(newimg);  // transform it back
		add(new JLabel(imageIcon));
		
		//remove frame decorations and pack it
		setUndecorated (true);
    	setResizable(false);
    	pack();

    	//randomize location
    	Rectangle bounds = getGraphicsConfiguration().getBounds();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= insets.left + insets.right;
        bounds.height -= insets.top + insets.bottom;
        int minY = bounds.y, 
        	maxY = bounds.y + bounds.height - getHeight(),
        	minX = bounds.x,
        	maxX = bounds.x + bounds.width - getWidth();
        int randomX = (int)(Math.random() * (maxX - minX + 1)) + minX,
        	randomY = (int)(Math.random() * (maxY - minY + 1)) + minY;
        setLocation(randomX,randomY);

        //make it closable by 3-clicks
        addMouseListener(new MouseAdapter(){
    		public void mouseClicked(MouseEvent event){
       			if(event.getClickCount()==3)
            		System.exit(0);
    		}
		});

	}

	public void actionPerformed(ActionEvent e) {
		for (int i=0; i<images.size(); i++)
		{
			if (images.get(i).isShowing())
			{
				if ((i+1)<images.size())
					images.get(i+1).setVisible(true);
				else
					images.get(0).setVisible(true);
				
				images.get(i).setVisible(false);
				break;
			}
		}
	}

	public static void main(String[] args) {
		Album roller = new Album();
		Album defaultPic = new Album("default.png");
		roller.images.add(defaultPic);
		defaultPic.setVisible(true);
		
		loadAllImagesTo(roller.images);
    	
    	Timer timer = new Timer(timeForEachPicInSec * 1000, roller);
		timer.setInitialDelay(timeForEachPicInSec + 1500);
		timer.start();   
	}

	public static void loadAllImagesTo(ArrayList<Album> list)
	{	
		for (File file : (new File("./images")).listFiles())
		{
			if (file.isFile() && 
			(file.getName().substring(file.getName().length()-3).equalsIgnoreCase("png") || 
			file.getName().substring(file.getName().length()-3).equalsIgnoreCase("jpg") ||
			file.getName().substring(file.getName().length()-4).equalsIgnoreCase("jpeg"))) //if it's image (jpg,png,jpeg)
   			{
        		list.add(new Album(file.getPath()));
    		}
		}
	}
}