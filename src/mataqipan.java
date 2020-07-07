import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.*;

public class mataqipan {
	private static int X; // ���̵�����
	private static int Y; // ���̵�����
	private static boolean visited[]; // ������������ϸ���λ���Ƿ񱻷��ʹ�
	private static boolean finished; // ��������Ƿ���������λ�þ�������(��ζ���ѳɹ�)
	private static String result; //����������
	private static String time; //�㷨��Ҫʱ��
 
	
    public static void main(String[] args) {
    	result = "";

    	//���ڲ���
        JFrame f = new JFrame("��̤����");
        f.setSize(500, 290);
        f.setLocation(300, 200);
        
        f.setLayout(new FlowLayout());

        JLabel XZ = new JLabel("��ʼ��X�����᣺");
        // �����
        final JTextField XZZ = new JTextField("");
        //XZZ.setText("������X������");
        XZZ.setPreferredSize(new Dimension(100, 30));
        
        JLabel YZ = new JLabel("��ʼ��Y�����᣺");
        // �����
        final JTextField YZZ = new JTextField("");
        //YZZ.setText("������Y������");
        YZZ.setPreferredSize(new Dimension(100, 30));
        //��ʼ��
        JButton start = new JButton("��ʼ");
        
//        final JLabel test = new JLabel();
//        test.setPreferredSize(new Dimension(200, 400));
        
        final JTextArea textArea = new JTextArea(8, 8); 
        textArea.setPreferredSize(new Dimension(362,160));
	//�Զ�����
        textArea.setLineWrap(true);
        textArea.setFont(new Font("����",Font.BOLD,16));
        
        final JLabel TIME = new JLabel("");
        TIME.setPreferredSize(new Dimension(362, 30));
        
        f.add(XZ);
        f.add(XZZ);
        f.add(YZ);
        f.add(YZZ);
        f.add(start);
		f.add(TIME);
//        f.add(test);
        f.add(textArea);
        f.setVisible(true);
        
        //������ʾ����
        final JLabel label = new JLabel();
        final JDialog dialog = new JDialog(f,"��ʾ");
        dialog.setSize(220,150);
        dialog.setLocation(350,250);
        dialog.setLayout(new FlowLayout());
        final JButton ok = new JButton("ȷ��");
        
        start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TIME.setText("���Ե�....");
				String XZZS = XZZ.getText().trim();
				String YZZS = YZZ.getText().trim();
				
				for (int i = 0; i < XZZS.length(); i++){
					if (!Character.isDigit(XZZS.charAt(i))){
							//���öԻ���Ϊģ̬
							dialog.setModal(true);
							if(dialog.getComponents().length == 1){
								dialog.add(label);
							}
							//��ʾ����
							label.setText("X������Ĳ��Ǵ�����");
							//��Ӱ�ť
							dialog.add(ok);
							//��ʾ�Ի���
							dialog.setVisible(true);
							//System.out.println("X������Ĳ�������");
						   return;
					   }
				}
				
				int XZZI = Integer.parseInt(XZZS);
					if(XZZI > 8){
						//���öԻ���Ϊģ̬
						dialog.setModal(true);
						if(dialog.getComponents().length == 1){
							dialog.add(label);
						}
						//��ʾ����
						label.setText("X����������ִ�������");
						//��Ӱ�ť
						dialog.add(ok);
						//��ʾ�Ի���
						dialog.setVisible(true);
						//System.out.println("X����������ִ�������");
					}else{
						for(int j = 0; j < YZZS.length(); j++){
							if (!Character.isDigit(YZZS.charAt(j))){
								//���öԻ���Ϊģ̬
								dialog.setModal(true);
								if(dialog.getComponents().length == 1){
									dialog.add(label);
								}
								//��ʾ����
								label.setText("Y������Ĳ��Ǵ�����");
								//��Ӱ�ť
								dialog.add(ok);
								//��ʾ�Ի���
								dialog.setVisible(true);
								//System.out.println("Y������Ĳ�������");
								return;
							   }
						}
						
						int YZZI = Integer.parseInt(YZZS);
							if(YZZI > 8){
								//���öԻ���Ϊģ̬
								dialog.setModal(true);
								if(dialog.getComponents().length == 1){
									dialog.add(label);
								}
								//��ʾ����
								label.setText("Y����������ִ�������");
								//��Ӱ�ť
								dialog.add(ok);
								//��ʾ�Ի���
								dialog.setVisible(true);
								//System.out.println("Y����������ִ�������");
							}else{
									do{
										Y = 8;
										X = 8;
									}while(X <= 0 || Y <= 0);
									int row, column;
									do{
										row = XZZI;
										column = YZZI;
									}while(row <= 0 || row > Y || column <= 0 || column > X);
									int[][] chessBoard = new int[Y][X];
									visited = new boolean[X * Y];
									long start = System.currentTimeMillis();
									traversalChessBoard(chessBoard, row - 1, column - 1, 1);
									long end = System.currentTimeMillis();
									TIME.setText("����ʱ��" + (end - start)/1000 + "s");
//									System.out.println("����ʱ��" + (end - start)/1000 + "s");
									for(int[] rows : chessBoard){
										for(int columns : rows){
											if(columns < 10){
												result = result + columns + "    ";
//												System.out.print(columns + "\t");
											}else{
												result = result + columns + "   ";
//												System.out.print(columns + "\t");
											}
										}
//										System.out.println();
									} 
//									test.setText(result);
									textArea.setText(result);
							   }
							   return;
				   }
			}
        });
        
        ok.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		TIME.setText("");
        		dialog.dispose();
        	}
        });
        
     
    }
    
    public static void traversalChessBoard(int[][] chessBoard, int row, int column, int step) {
		chessBoard[row][column] = step;
		visited[row * X + column] = true; // ��λ���ѷ���
		ArrayList<Point> ps = next(new Point(column, row)); // �ɵ�ǰλ�õõ���һ������λ�õļ���
		while (!ps.isEmpty()) {
			Point p = ps.remove(0);
			if (!visited[p.y * X + p.x]) { // ���λ��û�з��ʣ���ô�ʹ����λ�ÿ�ʼ������һ�η���
				traversalChessBoard(chessBoard, p.y, p.x, step + 1);
			}
		}
		if(step < X * Y && !finished){ // (step < X * Y)��������������������������һ�֣����̵�ĿǰΪֹ��û������;�ڶ��֣������Ѿ����������ʱ�ڻ��ݵĹ�����
			chessBoard[row][column] = 0; // ���������������ȫ��Ϊ�㣬���ʾ�޽�
			visited[row * X + column] = false;
		}else{
			finished = true;
		}
	}
	
	// �ڵ�ǰλ��p������һ�ε�λ��(�����8��λ��)
	public static ArrayList<Point> next(Point p) {
		ArrayList<Point> ps = new ArrayList<Point>();
		Point p1 = new Point(p);
		if ((p1.x = p.x - 2) >= 0 && (p1.y = p.y - 1) >= 0) {
			ps.add(new Point(p1));
		}
		if ((p1.x = p.x - 1) >= 0 && (p1.y = p.y - 2) >= 0) {
			ps.add(new Point(p1));
		}
		if ((p1.x = p.x + 1) < X && (p1.y = p.y - 2) >= 0) {
			ps.add(new Point(p1));
		}
		if ((p1.x = p.x + 2) < X && (p1.y = p.y - 1) >= 0) {
			ps.add(new Point(p1));
		}
		if ((p1.x = p.x + 2) < X && (p1.y = p.y + 1) < Y) {
			ps.add(new Point(p1));
		}
		if ((p1.x = p.x + 1) < X && (p1.y = p.y + 2) < Y) {
			ps.add(new Point(p1));
		}
		if ((p1.x = p.x - 1) >= 0 && (p1.y = p.y + 2) < Y) {
			ps.add(new Point(p1));
		}
		if ((p1.x = p.x - 2) >= 0 && (p1.y = p.y + 1) < Y) {
			ps.add(new Point(p1));
		}
		return ps;
	}
}