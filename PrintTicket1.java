import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.util.Random;

public class PrintTicket1 extends JFrame implements Printable {
	private static final String[] BACKGROUND_IMAGES = {
			"flight_image1.jpg", "flight_image2.jpg", "flight_image3.jpg"
	};

	public PrintTicket1(String sFrom, String sTo, String sClass, Integer iAdult, Integer iChildren, Integer iInfant,
			String sBookingDate, Integer iPrice, String sTime) {
		Container c = getContentPane();
		c.setLayout(new BorderLayout());

		// Select a random background image
		String selectedImage = BACKGROUND_IMAGES[new Random().nextInt(BACKGROUND_IMAGES.length)];

		// Custom panel for background image with reduced transparency
		JPanel Panel2 = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon bgImage = new ImageIcon(selectedImage);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f)); // Reduced transparency
				g.drawImage(bgImage.getImage(), 0, 0, getWidth(), getHeight(), this);
			}
		};
		Panel2.setPreferredSize(new Dimension(700, 650));

		// Create a panel for centered content with shiny border
		JPanel borderPanel = new JPanel(null) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(new BasicStroke(8));
				g2d.setColor(Color.CYAN);
				g2d.drawRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 20, 20);
				g2d.setColor(Color.MAGENTA);
				g2d.drawRoundRect(15, 15, getWidth() - 30, getHeight() - 30, 20, 20);
			}
		};
		borderPanel.setBounds(50, 100, 600, 400);
		borderPanel.setOpaque(false);

		// Font style
		Font labelFont = new Font("Arial", Font.BOLD, 16);

		// Ticket information labels with bold and aligned text
		JLabel LTitle = new JLabel("<html><b><font color=\"#C71585\" size=\"7\">AirLine Ticket</font></b></html>",
				SwingConstants.CENTER);
		JLabel LFrom = new JLabel("From: " + sFrom, SwingConstants.CENTER);
		JLabel LTo = new JLabel("To: " + sTo, SwingConstants.CENTER);
		JLabel LClass = new JLabel("Class: " + sClass, SwingConstants.CENTER);
		JLabel LBookingDate = new JLabel("Traveling Date: " + sBookingDate, SwingConstants.CENTER);
		JLabel LPrice = new JLabel("Total Price: ₹" + iPrice, SwingConstants.CENTER);
		JLabel LTime = new JLabel("Departure Time: " + sTime, SwingConstants.CENTER);
		JLabel LAdult = new JLabel("Adult: " + iAdult, SwingConstants.CENTER);
		JLabel LChildren = new JLabel("Children: " + iChildren, SwingConstants.CENTER);
		JLabel LInfant = new JLabel("Infant: " + iInfant, SwingConstants.CENTER);
		JLabel LWishes = new JLabel("<html><i><font color=\"#D2B48C\">Wish you a happy journey!</font></i></html>",
				SwingConstants.CENTER);

		// Apply the font to all labels
		JLabel[] labels = { LFrom, LTo, LClass, LBookingDate, LPrice, LTime, LAdult, LChildren, LInfant, LWishes };
		for (JLabel label : labels) {
			label.setFont(labelFont);
			label.setForeground(Color.BLACK);
		}

		// Set bounds for labels (aligned in the center)
		LTitle.setBounds(100, 20, 400, 50);
		LFrom.setBounds(100, 80, 400, 30);
		LTo.setBounds(100, 120, 400, 30);
		LClass.setBounds(100, 160, 400, 30);
		LBookingDate.setBounds(100, 200, 400, 30);
		LPrice.setBounds(100, 240, 400, 30);
		LTime.setBounds(100, 280, 400, 30);
		LAdult.setBounds(100, 320, 400, 30);
		LChildren.setBounds(100, 360, 400, 30);
		LInfant.setBounds(100, 400, 400, 30);
		LWishes.setBounds(100, 440, 400, 30);

		// Add labels to the border panel
		borderPanel.add(LTitle);
		borderPanel.add(LFrom);
		borderPanel.add(LTo);
		borderPanel.add(LClass);
		borderPanel.add(LBookingDate);
		borderPanel.add(LPrice);
		borderPanel.add(LTime);
		borderPanel.add(LAdult);
		borderPanel.add(LChildren);
		borderPanel.add(LInfant);
		borderPanel.add(LWishes);

		// Print button with red border and black foreground
		JButton printButton = new JButton("Print Ticket");
		printButton.setBounds(275, 550, 150, 30);
		printButton.setForeground(Color.BLACK);
		printButton.setBackground(Color.WHITE);
		printButton.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		printButton.setFocusPainted(false);

		// Button hover effect
		printButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				printButton.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				printButton.setBackground(Color.WHITE);
			}
		});

		printButton.addActionListener(e -> printTicket());
		Panel2.add(printButton);

		// Add panels to the main container
		Panel2.add(borderPanel);
		c.add(Panel2, BorderLayout.CENTER);

		setSize(700, 650);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void printTicket() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		if (job.printDialog()) {
			try {
				job.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		if (page > 0) {
			return NO_SUCH_PAGE;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		this.printAll(g);
		return PAGE_EXISTS;
	}

	public static void main(String[] args) {
		new PrintTicket1("Mumbai", "Delhi", "Economy", 2, 1, 0, "2024-11-21", 5000, "10:30 AM");
	}
}
