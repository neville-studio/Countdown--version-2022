package com.XuPenglife.countdown.school.luofu;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.text.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



public class kcbActivity {
	public static int kkk = 0;
	public static boolean onTop=false;
	public static JFrame kcbsettings = new JFrame("课程表安排");
	public static JWindow kcbMainframe = new JWindow();
	public static Boolean exists = false;
	public static File file = new File("kcb.xml");
	public static Boolean setexists = false;
	public static int cycle = 21;
	public static int sst = 0;
	public static String startdate = "";
	public static void kcbMain() {
		Container container = kcbMainframe.getContentPane();
		JLabel kcbtitle = new JLabel("下一节课是",JLabel.CENTER);
		JLabel kcbClass = new JLabel("",JLabel.CENTER);
		JLabel kcbClass2 = new JLabel("",JLabel.CENTER);
		JLabel kcbClass3 = new JLabel("临时变动的课程仅在即将上课时更改",JLabel.CENTER);
		JLabel x = new JLabel();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int locatex=(int)screensize.getWidth()-250;
		int locatey=(int)(screensize.getHeight()-40)/3*2;
		exists=!exists;
		kcbMainframe.setLocation(locatex, locatey);
		kcbMainframe.setSize(250,250);
		kcbMainframe.setVisible(true);
		kcbMainframe.setBackground(new Color(0,0,0,0));
		kcbMainframe.addWindowListener(new MyWindowListener());
		kcbtitle.setFont(new Font("黑体",Font.PLAIN,30));
		kcbtitle.setSize(250,50);
		kcbtitle.setLocation(0,0);
		kcbtitle.setForeground(new Color(0,255,0,255));
		kcbClass.setFont(new Font("黑体",Font.PLAIN,45));
		kcbClass.setSize(250,50);
		kcbClass.setLocation(0,50);
		kcbClass.setForeground(new Color(0,255,0,255));
		kcbClass2.setFont(new Font("黑体",Font.PLAIN,30));
		kcbClass2.setSize(250,50);
		kcbClass2.setLocation(0,100);
		kcbClass2.setForeground(new Color(0,255,0,255));
		kcbClass3.setFont(new Font("黑体",Font.PLAIN,15));
		kcbClass3.setSize(250,50);
		kcbClass3.setLocation(0,150);
		kcbClass3.setForeground(new Color(0,255,0,255));
		container.add(kcbtitle);
		container.add(kcbClass);
		container.add(kcbClass2);
		container.add(kcbClass3);
		container.add(x);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Timer changetimer =new Timer(); 
		changetimer.schedule(new TimerTask() {
			public void run() {
				kcbMainframe.setSize(250,250);
				kcbMainframe.setLocation(locatex, locatey);
				kcbMainframe.setAlwaysOnTop(onTop);
				if(file.exists()) {
				try {
					int k=0;
					DocumentBuilder builder = factory.newDocumentBuilder();
					
					Document doc = builder.parse(file);
					int[] total = new int[25];
					NodeList nlst1 =doc.getElementsByTagName("timetable");
					NodeList nlst2 =doc.getElementsByTagName("lessonchange");
					
					int i = 0;
					while(i < ((org.w3c.dom.NodeList)nlst1).getLength()) {
						if(doc.getElementsByTagName("lt"+i).item(0).getFirstChild().getNodeValue().equals("null")){
							break;
						}
						total[i] = Integer.valueOf(doc.getElementsByTagName("lt"+i).item(0).getFirstChild().getNodeValue());
						i=i+1;
					}
	         		String startdate = doc.getElementsByTagName("startdate").item(0).getFirstChild().getNodeValue();
	         		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
	         		long kcbstart = simpleFormat.parse(startdate).getTime();
	      			Date now = new Date();
	      			cycle = Integer.valueOf(doc.getElementsByTagName("cycle").item(0).getFirstChild().getNodeValue());
	       			long n = (now.getTime()-kcbstart)/86400000%cycle;
	       			int zh = (int)n;
	       			i = 0;
         			while(n>=0) {
         				n = n - total[i];
	         			i=i+1;
	         		}
         			
         			SimpleDateFormat simpleFormat2 = new SimpleDateFormat("H:mm");
	         		for(int x = 0;x < nlst1.getLength();x++) {
	         			if(!doc.getElementsByTagName("st1"+(i-1)).item(x).getFirstChild().getNodeValue().equals("null")) {
	         			long comp1 = simpleFormat2.parse(doc.getElementsByTagName("st1"+(i-1)).item(x).getFirstChild().getNodeValue()).getTime();
	         			long comp2 = simpleFormat2.parse(doc.getElementsByTagName("st2"+(i-1)).item(x).getFirstChild().getNodeValue()).getTime();
	         			long comp3 = simpleFormat2.parse(simpleFormat2.format(now)).getTime();
	         			if(comp3<comp1) {
	         				kcbtitle.setText(doc.getElementsByTagName("st1"+(i-1)).item(x).getFirstChild().getNodeValue()+"～"+doc.getElementsByTagName("st2"+(i-1)).item(x).getFirstChild().getNodeValue());
	         				kcbClass.setText(doc.getElementsByTagName("day"+zh).item(x).getFirstChild().getNodeValue());
	         				kcbClass2.setText("Next:"+doc.getElementsByTagName("day"+zh).item(x+1).getFirstChild().getNodeValue());
	         				if((doc.getElementsByTagName("day"+zh).item(x+1).getFirstChild().getNodeValue())==null||(doc.getElementsByTagName("day"+zh).item(x+1).getFirstChild().getNodeValue()).equals("null")) {
	         					kcbClass2.setText("");
	         				}
	         				break;
	         			}else if(comp2>comp3) {
	         				sst=sst%6+1;
	         				k=1;
	         				if(sst<=3) {
	         				kcbtitle.setText("正在进行");}else {
	         					int lst = (int)((comp2-comp3)/60000);
	         					kcbtitle.setText(""+lst+"分钟后结束");
	         				}
	         				kcbClass.setText(doc.getElementsByTagName("day"+zh).item(x).getFirstChild().getNodeValue());
	         				kcbClass2.setText("Next:"+doc.getElementsByTagName("day"+zh).item(x+1).getFirstChild().getNodeValue());
	         				if((doc.getElementsByTagName("day"+zh).item(x+1).getFirstChild().getNodeValue())==null||(doc.getElementsByTagName("day"+zh).item(x+1).getFirstChild().getNodeValue()).equals("null")) {
	         					kcbClass2.setText("");
	         					}
	         				break;
	         			}
	         			}else{
	         				kcbtitle.setText("");
	         				kcbClass.setText("课已结束");
	         			}
	         		}
	         		SimpleDateFormat simpleFormat3 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	         		for(int x = 0;x < nlst2.getLength();x++) {
	         			if(!doc.getElementsByTagName("changestart").item(x).getFirstChild().getNodeValue().equals("null")) {
		         		
	         			long comp1 = simpleFormat3.parse(doc.getElementsByTagName("changestart").item(x).getFirstChild().getNodeValue()).getTime();
	         			long comp2 = simpleFormat3.parse(doc.getElementsByTagName("changeend").item(x).getFirstChild().getNodeValue()).getTime();
	         			long comp3 = now.getTime();
	         			if(comp3<comp1&&comp3>comp1-600000) {
	         				kcbtitle.setText(simpleFormat2.format(simpleFormat3.parse(doc.getElementsByTagName("changestart").item(x).getFirstChild().getNodeValue()).getTime())+"～"+simpleFormat2.format(simpleFormat3.parse(doc.getElementsByTagName("changeend").item(x).getFirstChild().getNodeValue()).getTime()));
	         				kcbClass.setText(doc.getElementsByTagName("changelesson").item(x).getFirstChild().getNodeValue());
	         				break;
	         			}
	         			else if(comp2>comp3&&comp3>comp1) {
	         				if(k==0) {
	         				sst=sst%6+1;}
	         				k=1;
	         				if(sst<=3) {
	         				kcbtitle.setText("正在进行");}else {
	         					int lst = (int)((comp2-comp3)/60000+1);
	         					kcbtitle.setText(""+lst+"分钟后结束");
	         				}
	         				kcbClass.setText(doc.getElementsByTagName("changelesson").item(x).getFirstChild().getNodeValue());
	         				break;
	         			}
	         		}}
				}
				catch(Exception error) {
						error.printStackTrace();
					}
				
				}
			}	
		},0,2000);
		Timer timer1 = new Timer();
		timer1.schedule(new TimerTask(){
			public void run() {
				if((kkk!=1&&kkk!=3)&&kcbMainframe.isVisible()) {
					kcbMainframe.setVisible(false);
				}else if((kkk==1||kkk==3)&&!kcbMainframe.isVisible()) {
					kcbMainframe.setVisible(true);
				}
				
			}
		},0,100);
	}
	public static void kcbsettings() {
		URL resource = kcbActivity.class.getResource("/icon3.png");
		ImageIcon icon = new ImageIcon(resource);
		Container container = kcbsettings.getContentPane();
		//课程表选项卡的显示
		JLabel x = new JLabel();
		JLabel attention = new JLabel("课表周期");
		JLabel shs = new JLabel("周期开始日");
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		Vector<String> columnNameV=new Vector<>();
		Vector<Vector<String>> tableValueV=new Vector<>();
		JTextField jt2 = new JTextField();
		JTextField jt = new JTextField();
		final JTabbedPane tab1 = new JTabbedPane();
		tab1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		container.add(tab1,BorderLayout.CENTER);
		Container container1 = new Container();
		if(file.exists()) {
			try {
				
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document doc = builder.parse(file);
	            NodeList nlst =doc.getElementsByTagName("kcb");
	            cycle = Integer.parseInt(doc.getElementsByTagName("cycle").item(0).getFirstChild().getNodeValue());
		        startdate = ""+doc.getElementsByTagName("startdate").item(0).getFirstChild().getNodeValue();
		        jt2.setText(startdate);
		        jt.setText(""+cycle);
		        int total =((org.w3c.dom.NodeList) nlst).getLength();
	            for(int column = 0;column <cycle;column++) {
					columnNameV.add("第"+(column+1)+"日");
				}
	            for (int i = 0; i < 25; i++) {
	           		if(i < total) {
	           			Vector <String> rowV = new Vector<>();
	           			for(int column = 0;column < cycle;column++) {
	           				String lesson = doc.getElementsByTagName("day"+(column)).item(i).getFirstChild().getNodeValue();
	           				if(lesson.equals("null")) {
	           					lesson="";
	           				}
	           				rowV.add(lesson);
	           			}
	           			tableValueV.add(rowV);
	           		}else {
	           			Vector <String> rowV = new Vector<>();
	           			for(int column = 0;column < cycle;column++) {
	           				String lesson="";
	           				rowV.add(lesson);
	           			}
	           			tableValueV.add(rowV);
	           		}
	           		
	            }
	        } catch (Exception e) {
	            System.err.println("读取该xml文件失败");
	            e.printStackTrace();
	        }
		}else {	
				for(int column = 1;column <=21;column++) {
				columnNameV.add("第"+column+"日");
			}
			for(int row = 1;row <=13;row++) {
				Vector <String> rowV = new Vector<>();
				for(int column = 1;column <= 23;column++) {
					rowV.add("");
				}
				tableValueV.add(rowV);
			}
		}
		
		JTable kcbtable = new JTable(tableValueV,columnNameV);
		JScrollPane scollPane = new JScrollPane(kcbtable);
		kcbtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int locatex=(int)screensize.getWidth()/2;
		int locatey=(int)screensize.getHeight()/2;
		kcbsettings.setLocation(locatex-250,locatey-200);
		kcbtable.getTableHeader().setReorderingAllowed(false);
		kcbsettings.setSize(500,400);
		kcbsettings.setVisible(true);
		kcbsettings.setResizable(false);
		kcbsettings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		kcbsettings.setIconImage(icon.getImage());
		jt.setSize(100,25);
		jt.setLocation(70,30);
		jt.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				int key = arg0.getKeyChar();
				if (key <KeyEvent.VK_0 && key > KeyEvent.VK_9||jt.getText().length()>2) {
				arg0.consume();  //取消输入事件
				}// TODO Auto-generated method stub
				
			}});
		jt2.setSize(100,25);
		jt2.setLocation(70,0);
		jt2.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				int key = arg0.getKeyChar();
				if (key < KeyEvent.VK_0 || key > KeyEvent.VK_9) {
				arg0.consume();  //取消输入事件
				}
				for(int i = 0;i < jt2.getText().length();i++) {
					if(jt2.getText().substring(i,i+1).equals("-")&&i!=4&&i!=7) {
						jt2.setText(jt2.getText().substring(0,i)+jt2.getText().substring(i+1));
					}
					else if(i==4) {
						jt2.setText(jt2.getText().substring(0,i)+"-"+jt2.getText().substring(i));
						if(Integer.parseInt(jt2.getText().substring(0,4))<2018) {
							jt2.setText("2018"+jt2.getText().substring(4));
						}else if(Integer.parseInt(jt2.getText().substring(0,4))>2021) {
							jt2.setText("2021"+jt2.getText().substring(4));
							
						}
					}
					else if(i==7) {
						jt2.setText(jt2.getText().substring(0,i)+"-"+jt2.getText().substring(i));
						if(Integer.parseInt(jt2.getText().substring(5,7))>12) {
							jt2.setText(jt2.getText().substring(0,5)+12+jt2.getText().substring(7));
						}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
							jt2.setText(jt2.getText().substring(0,5)+01+jt2.getText().substring(7));
						}
					}
					else if(i>=9) {
						arg0.consume();
						jt2.setText(jt2.getText().substring(0,10));
						switch(Integer.parseInt(jt2.getText().substring(5,7))) {
						case 1:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 2:
							if(Integer.parseInt(jt2.getText().substring(8,10))>29) {
								jt2.setText(jt2.getText().substring(0,8)+29);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							if(Integer.parseInt(jt2.getText().substring(8,10))>28&&((Integer.parseInt(jt2.getText().substring(0,4))%4!=0&&Integer.parseInt(jt2.getText().substring(0,4))%400!=0)||Integer.parseInt(jt2.getText().substring(0,4))%4!=0)) {
								jt2.setText(jt2.getText().substring(0,8)+28);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1&&((Integer.parseInt(jt2.getText().substring(0,4))%4!=0&&Integer.parseInt(jt2.getText().substring(0,4))%400!=0)||Integer.parseInt(jt2.getText().substring(0,4))%4!=0)) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 3:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 4:
							if(Integer.parseInt(jt2.getText().substring(8,10))>30) {
								jt2.setText(jt2.getText().substring(0,8)+30);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 5:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 6:
							if(Integer.parseInt(jt2.getText().substring(8,10))>30) {
								jt2.setText(jt2.getText().substring(0,8)+30);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 7:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 9:
							if(Integer.parseInt(jt2.getText().substring(8,10))>30) {
								jt2.setText(jt2.getText().substring(0,8)+30);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 8:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 11:
							if(Integer.parseInt(jt2.getText().substring(8,10))>30) {
								jt2.setText(jt2.getText().substring(0,8)+30);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 10:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 12:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						}
						
					}
				}
				
			}});
		jt2.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				for(int i = 0;i < jt2.getText().length();i++) {
					if(jt2.getText().substring(i,i+1).equals("-")&&i!=4&&i!=7) {
						jt2.setText(jt2.getText().substring(0,i)+jt2.getText().substring(i+1));
					}
					else if(i==4) {
						jt2.setText(jt2.getText().substring(0,i)+"-"+jt2.getText().substring(i));
						if(Integer.parseInt(jt2.getText().substring(0,4))<2018) {
							jt2.setText("2018"+jt2.getText().substring(4));
						}else if(Integer.parseInt(jt2.getText().substring(0,4))>2021) {
							jt2.setText("2021"+jt2.getText().substring(4));
							
						}
					}
					else if(i==7) {
						jt2.setText(jt2.getText().substring(0,i)+"-"+jt2.getText().substring(i));
						if(Integer.parseInt(jt2.getText().substring(5,7))>12) {
							jt2.setText(jt2.getText().substring(0,5)+12+jt2.getText().substring(7));
						}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
							jt2.setText(jt2.getText().substring(0,5)+01+jt2.getText().substring(7));
						}
					}
					else if(i>=9) {
						jt2.setText(jt2.getText().substring(0,10));
						switch(Integer.parseInt(jt2.getText().substring(5,7))) {
						case 1:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 2:
							if(Integer.parseInt(jt2.getText().substring(8,10))>29) {
								jt2.setText(jt2.getText().substring(0,8)+29);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							if(Integer.parseInt(jt2.getText().substring(8,10))>28&&((Integer.parseInt(jt2.getText().substring(0,4))%4!=0&&Integer.parseInt(jt2.getText().substring(0,4))%400!=0)||Integer.parseInt(jt2.getText().substring(0,4))%4!=0)) {
								jt2.setText(jt2.getText().substring(0,8)+28);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1&&((Integer.parseInt(jt2.getText().substring(0,4))%4!=0&&Integer.parseInt(jt2.getText().substring(0,4))%400!=0)||Integer.parseInt(jt2.getText().substring(0,4))%4!=0)) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 3:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 4:
							if(Integer.parseInt(jt2.getText().substring(8,10))>30) {
								jt2.setText(jt2.getText().substring(0,8)+30);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 5:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 6:
							if(Integer.parseInt(jt2.getText().substring(8,10))>30) {
								jt2.setText(jt2.getText().substring(0,8)+30);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 7:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 9:
							if(Integer.parseInt(jt2.getText().substring(8,10))>30) {
								jt2.setText(jt2.getText().substring(0,8)+30);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 8:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 11:
							if(Integer.parseInt(jt2.getText().substring(8,10))>30) {
								jt2.setText(jt2.getText().substring(0,8)+30);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 10:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						case 12:
							if(Integer.parseInt(jt2.getText().substring(8,10))>31) {
								jt2.setText(jt2.getText().substring(0,8)+31);
							}else if(Integer.parseInt(jt2.getText().substring(0,4))<1) {
								jt2.setText(jt2.getText().substring(0,8)+01);
							}
							break;
						}
					}
				}
			}
			
		});
		attention.setSize(70,25);
		attention.setLocation(0,30);
		scollPane.setSize(485,335);
		scollPane.setLocation(0,0);
		shs.setSize(75,25);
		shs.setLocation(0,0);
		
		container1.add(scollPane);
		//时间表选项卡的设计
		Container container3 = new Container();
		Vector<String> columnNameV2=new Vector<>();
		Vector<Vector<String>> tableValueV2=new Vector<>();
		if(file.exists()) {
			try {	
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document doc = builder.parse(file);
	            NodeList nlst =doc.getElementsByTagName("timetable");
	            int total =((org.w3c.dom.NodeList) nlst).getLength();
	            for(int column = 0;column <cycle;column++) {
					columnNameV2.add("第"+(column+1)+"个开始时间 ");
					columnNameV2.add("第"+(column+1)+"个结束时间 ");
					columnNameV2.add("第"+(column+1)+"个持续天数 ");
				}
	            for (int i = 0; i < 25; i++) {
	           		if(i < total) {
	           			Vector <String> rowV = new Vector<>();
	           			for(int column = 0;column < cycle;column++) {
	           				String st1 = doc.getElementsByTagName("st1"+column).item(i).getFirstChild().getNodeValue();
	           				String st2 = doc.getElementsByTagName("st2"+column).item(i).getFirstChild().getNodeValue();
	           				String lt = doc.getElementsByTagName("lt"+column).item(i).getFirstChild().getNodeValue();
	           				if(st1.equals("null")) {
	           					st1="";
	           				}
	           				if(st2.equals("null")) {
	           					st2="";
	           				}
	           				if(lt.equals("null")) {
	           					lt="";
	           				}
	           				rowV.add(st1);
	           				rowV.add(st2);
	           				rowV.add(lt);
	           				
	           			}tableValueV2.add(rowV);
	           		}else {
	           			Vector <String> rowV = new Vector<>();
	           			for(int column = 0;column < cycle;column++) {
	           				String st1 = "";
	           				String st2 = "";
	           				String lt ="";
	           				rowV.add(st1);
	           				rowV.add(st2);
	           				rowV.add(lt);
	           			}
		           		tableValueV2.add(rowV);
	           		}
	            }
	        } catch (Exception e) {
	            System.err.println("读取该xml文件失败");
	            e.printStackTrace();
	        }
		}else {	
				for(int column = 0;column <21;column++) {
					columnNameV2.add("第"+(column+1)+"个开始时间 ");
					columnNameV2.add("第"+(column+1)+"个结束时间 ");
					columnNameV2.add("第"+(column+1)+"个持续天数 ");
			}
			for(int row = 1;row <=25;row++) {
				Vector <String> rowV = new Vector<>();
				for(int column = 1;column <= 23;column++) {
					String st1 = "";
       				String st2 = "";
       				String lt ="";
       				rowV.add(st1);
       				rowV.add(st2);
       				rowV.add(lt);
				}
				tableValueV2.add(rowV);
			}
		}
		JTable kcbtable2 = new JTable(tableValueV2,columnNameV2);
		JScrollPane scollPane2 = new JScrollPane(kcbtable2);
		scollPane2.setSize(485,335);
		scollPane2.setLocation(0,0);
		kcbtable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		kcbtable2.getTableHeader().setReorderingAllowed(false);
		container3.add(scollPane2);
		//特殊换课情况的选项卡的设计
		Container container4 = new Container();
		Vector<String> columnNameV3=new Vector<>();
		Vector<Vector<String>> tableValueV3 =new Vector<>();
		if(file.exists()) {
			try {	
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder builder = factory.newDocumentBuilder();
	            Document doc = builder.parse(file);
	            NodeList nlst =doc.getElementsByTagName("lessonchange");
	            int total =((org.w3c.dom.NodeList) nlst).getLength();
					columnNameV3.add("开始时间");
					columnNameV3.add("结束时间 ");
					columnNameV3.add("课程 ");
	            for (int i = 0; i < 25; i++) {
	           		if(i < total) {
	           			    Vector <String> rowV = new Vector<>();
	           				String st1 = doc.getElementsByTagName("changestart").item(i).getFirstChild().getNodeValue();
	           				String st2 = doc.getElementsByTagName("changeend").item(i).getFirstChild().getNodeValue();
	           				String lt = doc.getElementsByTagName("changelesson").item(i).getFirstChild().getNodeValue();
	           				if(st1.equals("null")) {
	           					st1="";
	           				}
	           				if(st2.equals("null")) {
	           					st2="";
	           				}
	           				if(lt.equals("null")) {
	           					lt="";
	           				}
	           				rowV.add(st1);
	           				rowV.add(st2);
	           				rowV.add(lt);
	           				tableValueV3.add(rowV);
	           			}
	           		else {
	           			Vector <String> rowV = new Vector<>();
	           			for(int column = 0;column < cycle;column++) {
	           				String st1 = "";
	           				String st2 = "";
	           				String lt ="";
	           				rowV.add(st1);
	           				rowV.add(st2);
	           				rowV.add(lt);
	           			}
		           		tableValueV3.add(rowV);
	           		}
	            }
			} catch (Exception e) {
	            System.err.println("读取该xml文件失败");
	            e.printStackTrace();
	        }
		}else {	
				
					columnNameV3.add("开始时间 ");
					columnNameV3.add("结束时间 ");
					columnNameV3.add("课程");
			
			for(int row = 1;row <=25;row++) {
				Vector <String> rowV = new Vector<>();
				String st1 = "";
       			String st2 = "";
       			String lt ="";
       			rowV.add(st1);
       			rowV.add(st2);
       			rowV.add(lt);
				tableValueV3.add(rowV);
			}
		}
		JTable kcbtable3 = new JTable(tableValueV3,columnNameV3);
		JScrollPane scollPane3 = new JScrollPane(kcbtable3);
		scollPane3.setSize(485,335);
		scollPane3.setLocation(0,0);
		kcbtable3.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		kcbtable3.getTableHeader().setReorderingAllowed(false);
		container4.add(scollPane3);
		//其他设置安排的选项卡的设计
		Container container2 = new Container();
		JTextField setthirdtime = new JTextField();
		JLabel third = new JLabel("三号倒计时安排");
		JLabel thirdtt = new JLabel("三号倒计时的标题");
		JLabel thirdti = new JLabel("三号倒计时的截止时间");
		JLabel thirdpp = new JLabel("三号倒计时的简称（两个汉字）");
		third.setSize(100,25);
		thirdtt.setSize(115,25);
		thirdti.setSize(130,25);
		thirdpp.setSize(185,25);
		third.setLocation(0,75);
		thirdtt.setLocation(0,100);
		thirdti.setLocation(0,125);
		thirdpp.setLocation(0,150);
		container2.add(third);
		container2.add(thirdtt);
		container2.add(thirdti);
		container2.add(thirdpp);
		setthirdtime.setSize(100,25);
		setthirdtime.setLocation(130,123);
		JTextField setthirdtt = new JTextField();
		setthirdtt.setSize(200,25);
		setthirdtt.setLocation(130,95);
		JTextField setthirdpp = new JTextField();
		setthirdpp.setSize(100,25);
		setthirdpp.setLocation(180,148);
		container2.add(setthirdtt);
		
		container2.add(setthirdpp);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("kcb.xml");
			setthirdtime.setText( doc.getElementsByTagName("thirdtime").item(0).getFirstChild().getNodeValue());
			setthirdtt.setText(doc.getElementsByTagName("thirdtitle").item(0).getFirstChild().getNodeValue());
			setthirdpp.setText(doc.getElementsByTagName("thirdpp").item(0).getFirstChild().getNodeValue());
		}catch(Exception e) {
			e.printStackTrace();
		}
		setthirdtt.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if (setthirdtt.getText().length()>=11) {
				arg0.consume();  //取消输入事件
				}
				
			}});
		setthirdpp.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if (setthirdpp.getText().length()>=2) {
				arg0.consume();  //取消输入事件
				}
				
			}});
		setthirdtime.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				int key = arg0.getKeyChar();
				if (key < KeyEvent.VK_0 || key > KeyEvent.VK_9) {
				arg0.consume();  //取消输入事件
				}
				for(int i = 0;i < setthirdtime.getText().length();i++) {
					if(setthirdtime.getText().substring(i,i+1).equals("-")&&i!=4&&i!=7) {
						setthirdtime.setText(setthirdtime.getText().substring(0,i)+setthirdtime.getText().substring(i+1));
					}
					else if(i==4) {
						setthirdtime.setText(setthirdtime.getText().substring(0,i)+"-"+setthirdtime.getText().substring(i));
						if(Integer.parseInt(setthirdtime.getText().substring(0,4))<2018) {
							setthirdtime.setText("2018"+setthirdtime.getText().substring(4));
						}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))>2021) {
							setthirdtime.setText("2021"+setthirdtime.getText().substring(4));
							
						}
					}
					else if(i==7) {
						setthirdtime.setText(setthirdtime.getText().substring(0,i)+"-"+setthirdtime.getText().substring(i));
						if(Integer.parseInt(setthirdtime.getText().substring(5,7))>12) {
							setthirdtime.setText(setthirdtime.getText().substring(0,5)+12+setthirdtime.getText().substring(7));
						}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
							setthirdtime.setText(setthirdtime.getText().substring(0,5)+01+setthirdtime.getText().substring(7));
						}
					}
					else if(i>=9) {
						arg0.consume();
						setthirdtime.setText(setthirdtime.getText().substring(0,10));
						switch(Integer.parseInt(setthirdtime.getText().substring(5,7))) {
						case 1:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 2:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>29) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+29);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>28&&((Integer.parseInt(setthirdtime.getText().substring(0,4))%4!=0&&Integer.parseInt(setthirdtime.getText().substring(0,4))%400!=0)||Integer.parseInt(setthirdtime.getText().substring(0,4))%4!=0)) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+28);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1&&((Integer.parseInt(setthirdtime.getText().substring(0,4))%4!=0&&Integer.parseInt(setthirdtime.getText().substring(0,4))%400!=0)||Integer.parseInt(setthirdtime.getText().substring(0,4))%4!=0)) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 3:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 4:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>30) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+30);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 5:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 6:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>30) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+30);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 7:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 9:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>30) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+30);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 8:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 11:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>30) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+30);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 10:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 12:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						}
						
					}
				}
				
			}});
		setthirdtime.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				for(int i = 0;i < setthirdtime.getText().length();i++) {
					if(setthirdtime.getText().substring(i,i+1).equals("-")&&i!=4&&i!=7) {
						setthirdtime.setText(setthirdtime.getText().substring(0,i)+setthirdtime.getText().substring(i+1));
					}
					else if(i==4) {
						setthirdtime.setText(setthirdtime.getText().substring(0,i)+"-"+setthirdtime.getText().substring(i));
						if(Integer.parseInt(setthirdtime.getText().substring(0,4))<2018) {
							setthirdtime.setText("2018"+setthirdtime.getText().substring(4));
						}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))>2021) {
							setthirdtime.setText("2021"+setthirdtime.getText().substring(4));
							
						}
					}
					else if(i==7) {
						setthirdtime.setText(setthirdtime.getText().substring(0,i)+"-"+setthirdtime.getText().substring(i));
						if(Integer.parseInt(setthirdtime.getText().substring(5,7))>12) {
							setthirdtime.setText(setthirdtime.getText().substring(0,5)+12+setthirdtime.getText().substring(7));
						}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
							setthirdtime.setText(setthirdtime.getText().substring(0,5)+01+setthirdtime.getText().substring(7));
						}
					}
					else if(i>=9) {
						setthirdtime.setText(setthirdtime.getText().substring(0,10));
						switch(Integer.parseInt(setthirdtime.getText().substring(5,7))) {
						case 1:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 2:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>29) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+29);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>28&&((Integer.parseInt(setthirdtime.getText().substring(0,4))%4!=0&&Integer.parseInt(setthirdtime.getText().substring(0,4))%400!=0)||Integer.parseInt(setthirdtime.getText().substring(0,4))%4!=0)) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+28);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1&&((Integer.parseInt(setthirdtime.getText().substring(0,4))%4!=0&&Integer.parseInt(setthirdtime.getText().substring(0,4))%400!=0)||Integer.parseInt(setthirdtime.getText().substring(0,4))%4!=0)) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 3:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 4:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>30) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+30);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 5:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 6:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>30) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+30);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 7:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 9:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>30) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+30);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 8:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 11:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>30) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+30);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 10:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						case 12:
							if(Integer.parseInt(setthirdtime.getText().substring(8,10))>31) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+31);
							}else if(Integer.parseInt(setthirdtime.getText().substring(0,4))<1) {
								setthirdtime.setText(setthirdtime.getText().substring(0,8)+01);
							}
							break;
						}
					}
				}}});
		container2.add(setthirdtime);
		JButton save = new JButton("保存");
		container2.add(jt);
		container2.add(attention);
		container2.add(shs);
		container2.add(save);
		container2.add(jt2);
		container2.add(x);
		save.setBounds(250,30,100,40);
		save.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try{	
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document document=db.newDocument();
					Element main=document.createElement("root");
					Element go = document.createElement("kcbsetting");
					Element kcbstyle=document.createElement("cycle");
					Element kcbstartdate=document.createElement("startdate");
					Element kcbshow = document.createElement("showtheform");
					Element kcbtt=document.createElement("thirdtitle");
					Element kcbti=document.createElement("thirdtime");
					Element kcbpp=document.createElement("thirdpp");
					kcbtt.setTextContent(setthirdtt.getText());
					kcbti.setTextContent(setthirdtime.getText());
					kcbpp.setTextContent(setthirdpp.getText());
					
					go.appendChild(kcbtt);
					go.appendChild(kcbti);
					go.appendChild(kcbpp);
					kcbshow.setTextContent(""+kkk);
					kcbstyle.setTextContent(jt.getText());
					if(jt.getText().equals("")) {
						kcbstyle.setTextContent(""+21);
					}
					kcbstartdate.setTextContent(jt2.getText());
					go.appendChild(kcbstyle);
					go.appendChild(kcbstartdate);
					go.appendChild(kcbshow);
					main.appendChild(go);
					for (int i = 0; i < kcbtable.getRowCount(); i++) {
						Element item = document.createElement("kcb");
						int column = 0 ;
						int end = Integer.valueOf(kcbstyle.getTextContent());
						while(column < kcbtable.getColumnCount()) {
							Element lesson = document.createElement("day"+column);
							if(kcbtable.getValueAt(i,column).toString().equals("")) {
								lesson.setTextContent("null");
							}else {
								lesson.setTextContent(kcbtable.getValueAt(i,column).toString());
							}
						item.appendChild(lesson);
						column++;
					}
							
						while(column<end) {
							Element lesson = document.createElement("day"+column);
							lesson.setTextContent("null");
							item.appendChild(lesson);
							column++;
						}
					main.appendChild(item);
			    }
					for (int i = 0; i < kcbtable2.getRowCount(); i++) {
						Element item = document.createElement("timetable");
						int column=0;
						int totals = 0;
						int end = Integer.valueOf(kcbstyle.getTextContent());
						while(column< kcbtable.getColumnCount()) {
							
							Element st1 = document.createElement("st1"+column);
							Element st2 = document.createElement("st2"+column);
							Element lt = document.createElement("lt"+column);
							if(!(kcbtable2.getValueAt(i,column*3).toString().equals("")&&kcbtable2.getValueAt(i,column*3+1).toString().equals("")&&kcbtable2.getValueAt(i,column*3+2).toString().equals(""))&&!(!kcbtable2.getValueAt(i,column*3).toString().equals("")&&(!kcbtable2.getValueAt(i,column*3+1).toString().equals(""))&&(!kcbtable2.getValueAt(i,column*3+2).toString().equals("")))) {
								JOptionPane.showMessageDialog(null,"保存失败，位于表“时间表”第"+(i+1)+"行，第"+(column*3+1)+"至"+(column*3+3)+"列的数据输入错误：\n数据输入不完整。","倒计时保存程序",JOptionPane.WARNING_MESSAGE);
								return;
							}
							if(kcbtable2.getValueAt(i,column*3).toString().equals("")) {
								st1.setTextContent("null");
							}else {
								st1.setTextContent(kcbtable2.getValueAt(i,column*3).toString());
							}
							if(kcbtable2.getValueAt(i,column*3+1).toString().equals("")) {
								st2.setTextContent("null");
							}else {
								st2.setTextContent(kcbtable2.getValueAt(i,column*3+1).toString());
							}
							if(kcbtable2.getValueAt(i,column*3+2).toString().equals("")) {
								lt.setTextContent("null");
							}else {
								lt.setTextContent(kcbtable2.getValueAt(i,column*3+2).toString());
							}
							
							item.appendChild(st1);
							SimpleDateFormat simpleFormat = new SimpleDateFormat("H:mm");
							
							int col = 0;
							String mod = "";
							try {
							if(!st1.getTextContent().equals("null")) {
								col = column * 3 + 1;
								mod = "仅支持H:mm格式（如8:00，12:00，18:00）数据输入";
								Date toDate1 = simpleFormat.parse(st1.getTextContent());
								toDate1.getTime();
								if(i!=0&&simpleFormat.parse(kcbtable2.getValueAt(i-1,column*3+1).toString()).getTime() >toDate1.getTime()) {
									mod = "时间有交叉或时间表没有按照从早到晚的顺序排列好。";
									JOptionPane.showMessageDialog(null,"保存失败，位于表“时间表”第"+(i+1)+"行，第"+(col)+"列的数据输入错误：\n"+mod,"倒计时保存程序",JOptionPane.WARNING_MESSAGE);
									return;
								}
								
							}
							if(!st2.getTextContent().equals("null")) {
								col = column * 3 + 2;
								mod = "仅支持H:mm格式（如8:00，12:00，18:00）数据输入";
								Date toDate2 = simpleFormat.parse(st2.getTextContent());
								
								toDate2.getTime();
								if(simpleFormat.parse(st1.getTextContent()).getTime() >toDate2.getTime()) {
									mod = "终止时间早于起始时间。";
									JOptionPane.showMessageDialog(null,"保存失败，位于表“时间表”第"+(i+1)+"行，第"+(col)+"列的数据输入错误：\n"+mod,"倒计时保存程序",JOptionPane.WARNING_MESSAGE);
									return;}
							}
							if(!lt.getTextContent().equals("null")) {
								col = column * 3 + 3;
								mod = "仅允许输入阿拉伯数字0～9。";
								int en = Integer.valueOf(lt.getTextContent());
								if(i==0) {
									totals = totals + en;
								}
							}
							}catch(ParseException error) {
								JOptionPane.showMessageDialog(null,"保存失败，位于表“时间表”第"+(i+1)+"行，第"+(col)+"列的数据输入错误：\n"+mod,"倒计时保存程序",JOptionPane.WARNING_MESSAGE);
								error.printStackTrace();
								return;
							}catch(NumberFormatException error1) {
								JOptionPane.showMessageDialog(null,"保存失败，位于表“时间表”第"+(i+1)+"行，第"+(col)+"列的数据输入错误：\n"+mod,"倒计时保存程序",JOptionPane.WARNING_MESSAGE);
								return;
							}
							
							item.appendChild(st2);
							item.appendChild(lt);
							column++;
						}
						if(totals != Integer.valueOf(jt.getText())&&i==0) {
							JOptionPane.showMessageDialog(null,"保存失败，位于表“时间表”第"+(i+1)+"行的数据输入错误：\n时间表周期时长不等于预定拟定周期时长","倒计时保存程序",JOptionPane.WARNING_MESSAGE);
							return;
						}
						while(column<end) {
							Element st1 = document.createElement("st1"+column);
							Element st2 = document.createElement("st2"+column);
							Element lt = document.createElement("lt"+column);
							st1.setTextContent("null");
							st2.setTextContent("null");
							lt.setTextContent("null");
							item.appendChild(st1);
							item.appendChild(st2);
							item.appendChild(lt);
						}
					main.appendChild(item);
			    }
					for (int i = 0; i < kcbtable3.getRowCount(); i++) {
						if(!(kcbtable3.getValueAt(i,0).toString().equals("")&&kcbtable3.getValueAt(i,1).toString().equals("")&&kcbtable3.getValueAt(i,2).toString().equals(""))&&!(!kcbtable3.getValueAt(i,0).toString().equals("")&&(!kcbtable3.getValueAt(i,1).toString().equals(""))&&(!kcbtable3.getValueAt(i,2).toString().equals("")))) {
							JOptionPane.showMessageDialog(null,"保存失败，位于表“临时变动”第"+(i+1)+"行的数据输入错误：\n数据输入不完整。","倒计时保存程序",JOptionPane.WARNING_MESSAGE);
							return;
						}
						Element item = document.createElement("lessonchange");
						Element start = document.createElement("changestart");
						Element end = document.createElement("changeend");
						Element lesson= document.createElement("changelesson");
						if(kcbtable3.getValueAt(i,0).toString().equals("")) {
							start.setTextContent("null");
						}else {
							start.setTextContent(kcbtable3.getValueAt(i,0).toString());
						}
						if(kcbtable3.getValueAt(i,1).toString().equals("")) {
							end.setTextContent("null");
						}else {
							end.setTextContent(kcbtable3.getValueAt(i,1).toString());
						}
						if(kcbtable3.getValueAt(i,2).toString().equals("")) {
							lesson.setTextContent("null");
						}else {
							lesson.setTextContent(kcbtable3.getValueAt(i,2).toString());
						}
						SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
						int col = 0;
						String mod = "";
						try {
						if(!start.getTextContent().equals("null")) {
							col = 1;
							mod = "仅支持yyyy-MM-dd hh:mm格式（如2020-07-30 07:00）数据输入";
							Date toDate1 = simpleFormat.parse(start.getTextContent());
							toDate1.getTime();
							
						}
						if(!end.getTextContent().equals("null")) {
							col = 2;
							mod = "仅支持yyyy-MM-dd hh:mm格式（如2020-07-30 07:00）数据输入";
							Date toDate2 = simpleFormat.parse(end.getTextContent());
							if(simpleFormat.parse(start.getTextContent()).getTime() >toDate2.getTime()) {
								mod = "终止时间早于起始时间。";
								JOptionPane.showMessageDialog(null,"保存失败，位于表“临时变动”第"+(i+1)+"行，第"+(col)+"列的数据输入错误：\n"+mod,"倒计时保存程序",JOptionPane.WARNING_MESSAGE);
								return;
							}
							toDate2.getTime();
						}
						
						}catch(ParseException error) {
							JOptionPane.showMessageDialog(null,"保存失败，位于表“临时变动”第"+(i+1)+"行，第"+(col)+"列的数据输入错误：\n"+mod,"倒计时保存程序",JOptionPane.WARNING_MESSAGE);
							error.printStackTrace();
							return;
						}
						
						item.appendChild(start);
						item.appendChild(end);
						item.appendChild(lesson);
						main.appendChild(item);
			    }
		        document.appendChild(main);   
		        //创建TransformerFactory对象
		        TransformerFactory tff=TransformerFactory.newInstance();
		        //创建Transformer对象
		        Transformer tf=tff.newTransformer();
		        //换行文件内容
		        tf.setOutputProperty(OutputKeys.INDENT, "yes");
		        tf.transform(new DOMSource(document), new StreamResult(file));
		        JOptionPane.showMessageDialog(null,"保存完成，部分设置将在您下次启动倒计时程序时启动。","保存成功",JOptionPane.INFORMATION_MESSAGE);
		        System.gc();
				}catch(Exception e1) {	
					e1.printStackTrace();
				}
				
			}
		});
		tab1.addTab("课程表",container1);
		tab1.addTab("时间表",container3);
		tab1.addTab("临时变动",container4);
		tab1.addTab("其他设置",container2);
		
	}
	public static String settime="";
	public static String title = "";
	public static String progressstring = "";
	public static void abcde() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse("kcb.xml");
			settime = doc.getElementsByTagName("thirdtime").item(0).getFirstChild().getNodeValue()+" 00:00";
			title = doc.getElementsByTagName("thirdtitle").item(0).getFirstChild().getNodeValue();
			progressstring = doc.getElementsByTagName("thirdpp").item(0).getFirstChild().getNodeValue();
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		JWindow jframe=new JWindow();
		JLabel dayJlabel=new JLabel("",JLabel.CENTER);
		JLabel HmsJlabel=new JLabel("",JLabel.CENTER);
    	Container container=jframe.getContentPane();
    	Font font=new Font("黑体",Font.PLAIN,75);
        Font font3=new Font("黑体",Font.PLAIN,25);
        Font font2=new Font("黑体",Font.PLAIN,32);
        JProgressBar progress=new JProgressBar();
        progress.setMinimum(0);
        progress.setMaximum(10000);
        progress.setSize(250,20);
        progress.setLocation(0,200);
        container.add(progress);
    	container.add(dayJlabel);
    	dayJlabel.setForeground(Color.RED);
    	HmsJlabel.setForeground(Color.RED);
    	container.add(HmsJlabel);
        jframe.setBackground(new Color(0,0,0,0));
        jframe.setAlwaysOnTop(false);
        JLabel b = new JLabel(title,JLabel.CENTER);
        b.setSize(250,50);
        b.setLocation(0, 0);
        dayJlabel.setSize(250,120);
        dayJlabel.setLocation(0, 20);
        HmsJlabel.setSize(250,50);
        HmsJlabel.setLocation(0, 120);
        HmsJlabel.setFont(font2);
        container.add(b);
        JLabel c = new JLabel("高中生涯，我们已走过了",JLabel.CENTER);
        c.setSize(250,50);
        c.setFont(new Font("黑体",Font.PLAIN,20));
        c.setLocation(0, 160);
        container.add(c);
        JLabel a = new JLabel();
        container.add(a);
    	c.setForeground(Color.RED);
    	b.setForeground(Color.RED);
    	b.setFont(font3);
    	a.setForeground(Color.RED);
    	jframe.setSize(250,250);
    	jframe.setVisible(true);
    	Timer timer = new Timer();
    	timer.schedule(new TimerTask() {
    		public void run() {
    			if((kkk!=2&&kkk!=3)&&jframe.isVisible()) {
					jframe.setVisible(false);
				}else if((kkk==2||kkk==3)&&!jframe.isVisible()) {
					jframe.setVisible(true);
				}
        		Date now=new Date();
        		long result=0;
        		float abc=0;
        		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        		try {        			
        			dayJlabel.setFont(font);
       				Date toDate1 = simpleFormat.parse(settime);
            		long from1 = now.getTime();  
            		long to1 = toDate1.getTime();
            		result= to1- from1;
            		if(result<0) {
            			jframe.setVisible(false);
            			timer.cancel();
            			return;
            			
            		}
            		Date startDate1 = simpleFormat.parse("2020-08-20 00:00");
        			float start = startDate1.getTime();
            		abc= ((from1-start)/(to1-start))*100;
            		c.setText("高三到"+progressstring+"已过："+Math.rint(abc*100)/100+"%");
            		progress.setValue((int)(abc*100));
            		jframe.setAlwaysOnTop(onTop);
            		float ese = (from1-start)/(to1 -start)*100;
            		Color sc = new Color(pertoColorR(ese),pertoColorG(ese),0,255);
            		c.setForeground(sc);
                	b.setForeground(sc);
                	a.setForeground(sc);
                	dayJlabel.setForeground(sc);
                	HmsJlabel.setForeground(sc);
                	progress.setForeground(sc);
            		dayJlabel.setText(""+(result/86400000+1)+"天");
    	      		long hour=result/3600000%24;
    	      		long minute=result/60000%60;
    	      		long second=result/1000%60;
    	      		if(result/86400000<1) {
    	      			dayJlabel.setVisible(false);
               			dayJlabel.setSize(250,100);
               			dayJlabel.setFont(font2);
               			HmsJlabel.setLocation(0,50);
               			HmsJlabel.setSize(250,150);
               			HmsJlabel.setFont(new Font("黑体",Font.PLAIN,45));
               			HmsJlabel.setText(""+pluszero(hour)+":"+pluszero(minute)+":"+pluszero(second));// sleep()：同步延迟数据，并且会阻塞线程
            		
    	      		}else {
            			HmsJlabel.setText(""+result/86400000+"天"+pluszero(hour)+":"+pluszero(minute)+":"+pluszero(second));// sleep()：同步延迟数据，并且会阻塞线程
            		}
        		} catch (ParseException e) {
        			timer.cancel();
    				e.printStackTrace();
    			}
    			b.setHorizontalAlignment(JLabel.CENTER);
    			HmsJlabel.setHorizontalAlignment(JLabel.CENTER);
    			dayJlabel.setHorizontalAlignment(JLabel.CENTER);
    			
        		c.setHorizontalAlignment(JLabel.CENTER);
        		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        		int locatex=(int)screensize.getWidth()-250;
        		int locatey=0;
        		if(MainActivity.showFrameatTheSameTime) {locatey=(int)(screensize.getHeight()-40)/3*1;}else {
        		locatey=(int)(screensize.getHeight()-40)/3*2;}
        		jframe.setLocation(locatex,locatey);
    		}	
			private int pertoColorG(float abc) {
				int out =0;
					if(abc > 50){
						out =(int)((100-abc)/50*255);
					}else if(abc<100){
						out=255;
					}// TODO 自动生成的方法存根
				return out;			
			}
			private int pertoColorR(float abc) {
				 int out =0;
					if(abc<50) {
						out =(int)((abc)/50*255);
					}else if(abc<100){
						out=255;
					}// TODO 自动生成的方法存根
					return out;
			}},0,100);
	}    
   

    static String pluszero(long a)
    {
    	String b=""+a;
    	if(a<10) {
    		b="0"+a;
    	}
    	return b;
    }
}
class MyWindowListener extends WindowAdapter{
	@Override
	public void windowClosing(WindowEvent e) {
		super.windowClosing(e);
		kcbActivity.exists = false;
	}
}


