/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mines;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * http://drow.today
 *
 * @author datampq
 */
public class frame extends JFrame {

    private int width;
    private int height;
    private Dimension dim;
    public Color text = Color.decode("0x512b58");
    public Color primary = Color.decode("0x2d8a9c");
    public Color secondary = Color.decode("0x2a7886");
    public Color bg = Color.decode("0xffffff");
    private Font big;

    public LinkedList<square> squares;
    public int _numsquares = 24;
    public int _chance = 25;
    public frame ref;
    public JLabel score;
    public int _revealed=0;

    //12 18 24
    public frame() {
        ref = this;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        width = dim.width / 4;
        height = width;
        big = new Font("Arial", Font.BOLD, 22);
        setUndecorated(true);

        FrameDragListener frameDragListener = new FrameDragListener(this);
        this.addMouseListener(frameDragListener);
        this.addMouseMotionListener(frameDragListener);

        this.setLocation(dim.width / 4, dim.height / 4);
        add(content());
        setVisible(true);
        pack();

    }

    public frame(int num, int chance, frame prev) {
        _numsquares = num;
        _chance = chance;
        prev.dispose();
        ref = this;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        width = dim.width / 4;
        height = width;
        big = new Font("Arial", Font.BOLD, 22);
        setUndecorated(true);

        FrameDragListener frameDragListener = new FrameDragListener(this);
        this.addMouseListener(frameDragListener);
        this.addMouseMotionListener(frameDragListener);

        this.setLocation(dim.width / 4, dim.height / 4);
        add(content());
        setVisible(true);
        pack();

    }

    private JPanel content() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        content.setBackground(bg);
        JLabel l = new JLabel("Mines Game");
        l.setFont(big);
        JPanel x = new JPanel();
        x.setLayout(new BoxLayout(x, BoxLayout.X_AXIS));
        x.setBackground(bg);

        l.setForeground(text);
        btn b = new btn(60, 50, "Exit");

        btn b2 = new btn(80, 50, "New ez");
        btn b3 = new btn(80, 50, "New");
        btn b4 = new btn(80, 50, "New Hard");
        x.add(b2);
        x.add(b3);
        x.add(b4);
        x.add(b);

        JPanel x1 = new JPanel();
        x1.setLayout(new BoxLayout(x1, BoxLayout.X_AXIS));
        x1.setBackground(bg);
        x1.add(l);
        content.add(x1);
        content.add(x);
        content.add(topPanel());

        score = new JLabel("Score:0/" + squares.size());
        score.setFont(big);
        score.setForeground(text);
        content.add(score);
        return content;

    }
    private int squareWidth = 36;
    public JPanel container;
    public int numMines=0;
    private JPanel topPanel() {
        squares = new LinkedList();
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        //vertical
        for (int cool = 0; cool < _numsquares; cool++) {
            //horiz
            JPanel x = new JPanel();
            x.setLayout(new BoxLayout(x, BoxLayout.X_AXIS));

            for (int j = 0; j < _numsquares; j++) {
                //horiz
                // public square(int w, int h, int size, boolean hasMine) {
                double random = Math.random() * 100;
                square s;
                if (random < _chance) {
                    s = new square(cool, j, squareWidth, true);
                    numMines++;
                } else {
                    s = new square(cool, j, squareWidth, false);
                }
                squares.add(s);
                x.add(s);
            }
            container.add(x);
        }
        return container;
    }

    public void revealAllSquares() {
        //TODO: reveal all squares
        for (int i = 0; i < squares.size(); i++) {
            squares.get(i).reveal();
        }
    }

    private class btn extends JPanel implements MouseListener {

        private final String action;

        public btn(int w, int h, String title) {
            action = title;
            this.setPreferredSize(new Dimension(w, h));
            JLabel l = new JLabel(title);
            l.setForeground(text);
            l.setFont(big);
            this.setBackground(Color.white);
            add(l);
            addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent me) {
            switch (action) {
                case "Exit":
                    System.exit(0);
                case "New ez":
                    _numsquares = 8;
                    _chance = 20;
                    new frame(_numsquares, _chance, ref);
                    break;
                case "New":
                    _numsquares = 14;
                    _chance = 25;
                    new frame(_numsquares, _chance, ref);
                    break;
                case "New Hard":
                    _numsquares = 24;
                    _chance = 35;
                    new frame(_numsquares, _chance, ref);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void mousePressed(MouseEvent me) {

        }

        @Override
        public void mouseReleased(MouseEvent me) {

        }

        @Override
        public void mouseEntered(MouseEvent me) {
            this.setBackground(primary);
        }

        @Override
        public void mouseExited(MouseEvent me) {
            this.setBackground(Color.white);
        }

    }

    public int getOtherSquares(int _i, int _j) {
        int val = 0;

        for (int i = 0; i < squares.size(); i++) {
            square s = squares.get(i);
            if (s.w == _i - 1 && s.h == _j - 1
                    || s.w == _i - 1 && s.h == _j
                    || s.w == _i - 1 && s.h == _j + 1
                    || s.w == _i && s.h == _j - 1
                    || s.w == _i + 1 && s.h == _j
                    || s.w == _i + 1 && s.h == _j - 1
                    || s.w == _i && s.h == _j + 1
                    || s.w == _i + 1 && s.h == _j + 1) {
                if (s.hasMine) {
                    val++;

                }
            }

        }
        return val;

    }

    public class square extends JPanel implements MouseListener {

        public boolean opened;
        public boolean hasMine;
        public int w;
        public int h;

        public square(int w, int h, int size, boolean hasMine) {
            this.w = w;
            this.h = h;
            this.hasMine = hasMine;
            this.setPreferredSize(new Dimension(size, size));
            this.setBackground(secondary);
            addMouseListener(this);

        }

        public void reveal() {
            if (!opened) {
                opened = true;
                if (hasMine) {
                    JLabel l = new JLabel("x");
                    l.setForeground(Color.white);
                    add(l);
                    revalidate();
                    this.setBackground(Color.red);
                } else {
                    JLabel l = new JLabel("" + getOtherSquares(w, h));
                    l.setForeground(Color.black);
                    add(l);
                    revalidate();

                    this.setBackground(bg);

                }
            }

        }

        @Override
        public void mouseClicked(MouseEvent me) {
            if (!opened) {
                opened = true;
                _revealed++;
                score.setText("Score: "+_revealed+"/"+squares.size()+"("+numMines+" mines)");
                if (hasMine) {
                    JLabel l = new JLabel("x");
                    l.setForeground(Color.white);
                    add(l);
                    revalidate();
                    this.setBackground(Color.red);
                    System.out.println("you ded m8!");
                    revealAllSquares();
                } else {
                    JLabel l = new JLabel("" + getOtherSquares(w, h));
                    l.setForeground(Color.black);
                    add(l);
                    revalidate();

                    this.setBackground(bg);
                    System.out.println("you shall live another day, pitty!");
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent me) {

        }

        @Override
        public void mouseReleased(MouseEvent me) {

        }

        @Override
        public void mouseEntered(MouseEvent me) {

            this.setBackground(primary);

        }

        @Override
        public void mouseExited(MouseEvent me) {
            if (opened) {
                if (hasMine) {
                    this.setBackground(Color.red);
                } else {
                    this.setBackground(bg);
                }

            } else {
                this.setBackground(secondary);
            }

        }

    }

}
