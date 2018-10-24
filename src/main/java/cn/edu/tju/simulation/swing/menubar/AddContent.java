package cn.edu.tju.simulation.swing.menubar;

import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.tju.simulation.content.SingleContent;
import cn.edu.tju.simulation.content.SingleLocalHobby;

/**
 * 
 * @author Wenkai Li ,School of Computer Science and Technology ,Tianjin University 
 *
 */
public class AddContent extends JFrame{
	private static final long serialVersionUID = -4006765746285448949L;
	/**
	 * Label - name
	 */
	private JLabel nameLabel;
	/**
	 * Text filed - name
	 */
	private JTextField nameText;
	/**
	 * Label - size
	 */
	private JLabel sizeLabel;
	/**
	 * Text filed - size
	 */
	private JTextField sizeText;
	/**
	 * Label - popularity
	 */
	private JLabel popularityLabel;
	/**
	 * Text filed - popularity
	 */
	private JTextField popularityText;
	/**
	 * Button - OK
	 */
	private JButton ensure;
	/**
	 * Button - cancel
	 */
	private JButton cancel;

	private List<SingleLocalHobby> orderedMedia;
	private AddContent addContent;
	private ViewContent viewContent;
	
	
	public AddContent(ViewContent viewContent,List<SingleLocalHobby> orderedMedia){    
		this.viewContent = viewContent; 
		this.orderedMedia = orderedMedia;
        initial();
        addListener(); 
	}
	
	private class ActionListener extends MouseAdapter implements WindowListener{
		public void mousePressed(MouseEvent e) { // Press the left mouse button
			if(e.getSource().equals(ensure)){
				SingleLocalHobby media = clickEnsure();
				if(media != null){
					orderedMedia.add(media);
					viewContent.tableUpdate();
					addContent.dispose();
				}else{
					JOptionPane.showMessageDialog(null,"Parameter can not be empty!","Prompt",JOptionPane.ERROR_MESSAGE);
				}
			}else if(e.getSource().equals(cancel)){
				addContent.dispose();
			}
		}

		public void windowOpened(WindowEvent e) {
			viewContent.setEnabled(false);
		}
		public void windowClosed(WindowEvent e) {
			viewContent.setEnabled(true);
		}
		public void windowClosing(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
	}
	
	public void addListener(){
		ActionListener actionListener  = new ActionListener();
		this.addWindowListener(actionListener);
		this.ensure.addMouseListener(actionListener);
		this.cancel.addMouseListener(actionListener);
	}
	
	public SingleLocalHobby clickEnsure(){
		String name = nameText.getText();
		String size = sizeText.getText();
		String popularity = popularityText.getText();
		if(name != null && !name.trim().equals("") && size != null && !size.trim().equals("") && popularity != null && !popularity.trim().equals("")){
			SingleLocalHobby media = new SingleLocalHobby(new SingleContent(name,Integer.parseInt(popularity),Integer.parseInt(size)));
			return media;
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("static-access")
	public void initial(){
        this.setLayout(new GridLayout(4, 0, 0, 3));
		this.setTitle("Add content");
        this.setLocation(300,200);
        this.setSize(350, 180);
		this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
		
        {
        	nameLabel = new JLabel("Name£º");
            nameText = new JTextField();
            JPanel name = new JPanel();
            name.setLayout(new GridLayout(0, 4, 0, 3));
            name.add(new JLabel());
            name.add(nameLabel);
            name.add(nameText);
            this.add(name);
        }
        
        {
        	 sizeLabel = new JLabel("Size£º");
             sizeText = new JTextField();
             JPanel size = new JPanel();
             size.setLayout(new GridLayout(0, 4, 0, 3));
             size.add(new JLabel());
             size.add(sizeLabel);
             size.add(sizeText);
             this.add(size);
        }
       
        {
            popularityLabel = new JLabel("Popularity:");
            popularityText = new JTextField();     	
            JPanel popularity = new JPanel();
            popularity.setLayout(new GridLayout(0, 4, 0, 3));
            popularity.add(new JLabel());
            popularity.add(popularityLabel);
            popularity.add(popularityText);
            this.add(popularity);
        }

        {
        	ensure = new JButton("OK");
            cancel = new JButton("Cancle");
            JPanel option = new JPanel();
            option.setLayout(new GridLayout(0, 4, 0, 3));
            option.add(new Label());
            option.add(ensure);
            option.add(cancel);
            this.add(option);
        }
	}

	public AddContent getAddContent() {
		return addContent;
	}

	public void setAddContent(AddContent addContent) {
		this.addContent = addContent;
	}

}
