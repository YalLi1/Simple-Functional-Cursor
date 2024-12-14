import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MouseListenerExample extends JFrame implements MouseListener, MouseMotionListener {

    private BufferedImage image;
    private int xPosition = -1, yPosition = -1;
    private Cursor customCursor;
    private Cursor defaultCursor;
    private boolean dragging = false; // Флаг для отслеживания перетаскивания

    public MouseListenerExample() {
        // Загружаем изображение
        try {
            image = ImageIO.read(new File("C:\\Users\\New\\Pictures\\Sun.png"));
        } catch (IOException e) {
            System.out.println(e);
        }

        // Создаем пользовательский курсор
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorImage = toolkit.getImage("C:\\Users\\New\\Pictures\\Cursor.png"); // Замените на путь к вашему изображению курсора
        Point hotSpot = new Point(0, 0);
        customCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "Custom Cursor");
        defaultCursor = Cursor.getDefaultCursor();

        // Настройка панели
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null && xPosition >= 0 && yPosition >= 0) {
                    g.drawImage(image, xPosition, yPosition, this);
                }
            }
        };
        panel.setPreferredSize(new Dimension(800, 800));
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this); // Добавляем слушатель для движения мыши

        add(panel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            xPosition = e.getX();
            yPosition = e.getY();
            repaint(); // Перерисовываем панель для отображения изображения
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            xPosition = -1;
            yPosition = -1;
            repaint(); // Перерисовываем панель для удаления изображения
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Начинаем перетаскивание, если нажата правая кнопка мыши и изображение уже отображается
        if (SwingUtilities.isRightMouseButton(e) && xPosition >= 0 && yPosition >= 0) {
            dragging = true; // Начинаем перетаскивание
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Завершаем перетаскивание, если отпущена правая кнопка мыши
        if (SwingUtilities.isRightMouseButton(e)) {
            dragging = false; // Завершаем перетаскивание
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCursor(customCursor); // Устанавливаем пользовательский курсор при наведении
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(defaultCursor); // Возвращаем стандартный курсор при выходе
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Обновляем координаты при перетаскивании
        if (dragging) {
            xPosition = e.getX();
            yPosition = e.getY();
            repaint(); // Перерисовываем панель для отображения изображения
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Не используется
    }

}
