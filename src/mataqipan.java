import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.*;

public class mataqipan {
	private static int X; // 棋盘的列数
	private static int Y; // 棋盘的行数
	private static boolean visited[]; // 用来标记棋盘上各个位置是否被访问过
	private static boolean finished; // 用来标记是否期盼所有位置均被访问(意味着已成功)
	private static String result; //框内输出结果
	private static String time; //算法需要时间
 
	
    public static void main(String[] args) {
    	result = "";

    	//窗口布局
        JFrame f = new JFrame("马踏棋盘");
        f.setSize(500, 290);
        f.setLocation(300, 200);
        
        f.setLayout(new FlowLayout());

        JLabel XZ = new JLabel("开始的X坐标轴：");
        // 输入框
        final JTextField XZZ = new JTextField("");
        //XZZ.setText("请输入X坐标轴");
        XZZ.setPreferredSize(new Dimension(100, 30));
        
        JLabel YZ = new JLabel("开始的Y坐标轴：");
        // 输入框
        final JTextField YZZ = new JTextField("");
        //YZZ.setText("请输入Y坐标轴");
        YZZ.setPreferredSize(new Dimension(100, 30));
        //开始键
        JButton start = new JButton("开始");
        
//        final JLabel test = new JLabel();
//        test.setPreferredSize(new Dimension(200, 400));
        
        final JTextArea textArea = new JTextArea(8, 8); 
        textArea.setPreferredSize(new Dimension(362,160));
	//自动换行
        textArea.setLineWrap(true);
        textArea.setFont(new Font("黑体",Font.BOLD,16));
        
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
        
        //设置提示弹窗
        final JLabel label = new JLabel();
        final JDialog dialog = new JDialog(f,"提示");
        dialog.setSize(220,150);
        dialog.setLocation(350,250);
        dialog.setLayout(new FlowLayout());
        final JButton ok = new JButton("确定");
        
        start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TIME.setText("请稍等....");
				String XZZS = XZZ.getText().trim();
				String YZZS = YZZ.getText().trim();
				
				for (int i = 0; i < XZZS.length(); i++){
					if (!Character.isDigit(XZZS.charAt(i))){
							//设置对话框为模态
							dialog.setModal(true);
							if(dialog.getComponents().length == 1){
								dialog.add(label);
							}
							//提示内容
							label.setText("X轴输入的不是纯数字");
							//添加按钮
							dialog.add(ok);
							//显示对话框
							dialog.setVisible(true);
							//System.out.println("X轴输入的不是数字");
						   return;
					   }
				}
				
				int XZZI = Integer.parseInt(XZZS);
					if(XZZI > 8){
						//设置对话框为模态
						dialog.setModal(true);
						if(dialog.getComponents().length == 1){
							dialog.add(label);
						}
						//提示内容
						label.setText("X轴输入的数字大于棋盘");
						//添加按钮
						dialog.add(ok);
						//显示对话框
						dialog.setVisible(true);
						//System.out.println("X轴输入的数字大于棋盘");
					}else{
						for(int j = 0; j < YZZS.length(); j++){
							if (!Character.isDigit(YZZS.charAt(j))){
								//设置对话框为模态
								dialog.setModal(true);
								if(dialog.getComponents().length == 1){
									dialog.add(label);
								}
								//提示内容
								label.setText("Y轴输入的不是纯数字");
								//添加按钮
								dialog.add(ok);
								//显示对话框
								dialog.setVisible(true);
								//System.out.println("Y轴输入的不是数字");
								return;
							   }
						}
						
						int YZZI = Integer.parseInt(YZZS);
							if(YZZI > 8){
								//设置对话框为模态
								dialog.setModal(true);
								if(dialog.getComponents().length == 1){
									dialog.add(label);
								}
								//提示内容
								label.setText("Y轴输入的数字大于棋盘");
								//添加按钮
								dialog.add(ok);
								//显示对话框
								dialog.setVisible(true);
								//System.out.println("Y轴输入的数字大于棋盘");
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
									TIME.setText("共耗时：" + (end - start)/1000 + "s");
//									System.out.println("共耗时：" + (end - start)/1000 + "s");
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
		visited[row * X + column] = true; // 此位置已访问
		ArrayList<Point> ps = next(new Point(column, row)); // 由当前位置得到下一次所有位置的集合
		while (!ps.isEmpty()) {
			Point p = ps.remove(0);
			if (!visited[p.y * X + p.x]) { // 这个位置没有访问，那么就从这个位置开始进行下一次访问
				traversalChessBoard(chessBoard, p.y, p.x, step + 1);
			}
		}
		if(step < X * Y && !finished){ // (step < X * Y)这个条件成立，有两种情况：第一种，棋盘到目前为止仍没有走完;第二种，棋盘已经走完过，此时在回溯的过程中
			chessBoard[row][column] = 0; // 如果整个棋盘最终全部为零，则表示无解
			visited[row * X + column] = false;
		}else{
			finished = true;
		}
	}
	
	// 在当前位置p处，下一次的位置(最多有8个位置)
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