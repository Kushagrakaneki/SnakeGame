import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class Board extends JPanel implements ActionListener {

    private int dots;
    private Image apple,head,dot;
    private final int ALL_DOT=900;
    private final int DOT_SIZE=10;
    private final int x[]=new int[ALL_DOT];
    private final int y[]=new int[ALL_DOT];
    private final int RANDOM_POSITION=29;
    private int apple_x;
    private int apple_y;
    private Timer timer;

    private boolean rightDirection=true;
    private boolean leftDirection=false;

    private boolean upDirection=false;
    private boolean downDirection=false;

    private boolean inGame=true;
    Random random=new Random();



    Board(){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(300,300));
        setFocusable(true);//This makes sure our Board can "listen" for keyboard presses.
        // If you want to move your snake with arrow keys, this line makes it possible!

        loadImage();
        initGame();

    }

    public void loadImage(){
        ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("icons/apple.png"));//So, i1 now holds a "receipt" for our apple picture.


        apple=i1.getImage();//We take the actual picture (getImage()) from the i1 receipt
        ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("icons/dot.png"));
        dot=i2.getImage();
        ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("icons/head.png"));
        head=i3.getImage();
    }
    public void initGame(){
        dots =3;
        for(int i=0;i<dots;i++){
            y[i]=50;
            x[i]=50-i*DOT_SIZE;

        }
        locateApple();
        timer=new Timer(140,this);
        timer.start();
    }
    public void locateApple(){
        //int r=(int)(Math.random()*RANDOM_POSITION);
        int r= random.nextInt(1,30);
        apple_x=r*DOT_SIZE;
        r= random.nextInt(1,30);
        apple_y=r*DOT_SIZE;
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for(int i=0;i<dots;i++){
                if(i==0){
                    g.drawImage(head,x[i],y[i],this);
                }
                else{
                    g.drawImage(dot,x[i],y[i],this);
                }

            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);
        }
    }
    public void gameOver(Graphics g){
        String msg="Game Over!!";
        Font font=new Font("SAN_SERIF",Font.BOLD,20);
        g.setColor(Color.WHITE);
        g.setFont(font);
        FontMetrics metrics=getFontMetrics(font);
        g.drawString(msg,(300-metrics.stringWidth(msg))/2,300/2);
    }
    public void move(){
        for(int i=dots;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        if(rightDirection){
            x[0]+=DOT_SIZE;
        }
        if(leftDirection){
            x[0]-=DOT_SIZE;
        }
        if(upDirection){
            y[0]-=DOT_SIZE;
        }
        if(downDirection){
            y[0]+=DOT_SIZE;
        }
//        x[0]+=DOT_SIZE;
//        y[0]+=DOT_SIZE;
    }
    public void checkApple(){
        if(x[0]==apple_x && y[0]==apple_y){
            dots++;
            locateApple();
        }
    }
    public void checkCollision(){
        for(int i=dots;i>0;i--){
            if(i>4 && x[0]==x[i] && y[0]==y[i]){
                inGame=false;
            }
        }
        if(x[0]>=300){
            inGame=false;
        }
        if(y[0]>=300){
            inGame=false;
        }
        if(x[0]<0){
            inGame=false;
        }
        if(y[0]<0){
            inGame=false;
        }
        if(!inGame){
            timer.stop();
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(inGame){
            checkCollision();
            checkApple();
            move();
        }

        repaint();//or pack() or setVisible() but we already used setVisible so cannot write it again

    }

    public class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            int key=e.getKeyCode();

            if(key==KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection=true;
                upDirection=false;
                downDirection=false;

            }
            if(key==KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection=true;
                upDirection=false;
                downDirection=false;

            }
            if(key==KeyEvent.VK_UP && (!downDirection)){
                upDirection=true;
                rightDirection=false;
                leftDirection=false;

            }
            if(key==KeyEvent.VK_DOWN && (!upDirection)){
                downDirection=true;
                rightDirection=false;
                leftDirection=false;

            }
        }
    }
}

