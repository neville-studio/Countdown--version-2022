package com.XuPenglife.countdown.school.luofu;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.text.*;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class MainActivity extends JFrame{
    /**Copyright XuPeng Studio. Do not release.
	 * 
	 */
	public int timerperiod = 15;
	public int chengetime = 0;
	public int changeSpeed=10;
	public int yx = 0;
	public int yy =0;
	public static File file = new File("./start");
	public static File file2 = new File("./ruggedkey");
	public static boolean showFrameatTheSameTime=false;
	int lm =0;
	public static int total = 2;
	Date now =new Date();
	static boolean onTop=false;
	JWindow jframe=new JWindow();
	JLabel dayJlabel=new JLabel("",JLabel.CENTER);
	JLabel HmsJlabel=new JLabel("",JLabel.CENTER);
	private static final long serialVersionUID = 1L;
	public void abcde(String settime,String title,int id,String progressstring) {
		
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
        		now=new Date();
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
            			if(id==2) {showFrameatTheSameTime=true;}
            			timer.cancel();
            			return;
            		}
            		Date startDate1 = simpleFormat.parse("2021-06-10 00:00");
        			float start = startDate1.getTime();
            		abc= ((from1-start)/(to1-start))*100;
            		c.setText("决战到"+progressstring+"："+Math.rint(abc*100)/100+"%");
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
    				e.printStackTrace();
    			}
    			b.setHorizontalAlignment(JLabel.CENTER);
    			HmsJlabel.setHorizontalAlignment(JLabel.CENTER);
    			dayJlabel.setHorizontalAlignment(JLabel.CENTER);
    			
        		c.setHorizontalAlignment(JLabel.CENTER);
        		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        		int locatex=(int)screensize.getWidth()-250;
        		int locatey=(int)(screensize.getHeight()-40)/(total)*(id-1);
        		if((yx!=locatex||yy!=locatey)&&chengetime>=55) {
        			chengetime=0;
        			changeSpeed=10;
        			timerperiod=15;
        		}
        		if(chengetime<55) {
        			jframe.setLocation(yx+(locatex-yx)/55*chengetime, yy+(locatey-yy)/55*chengetime);
        			chengetime=chengetime+changeSpeed;
        			changeSpeed--;
        		}
        		if(chengetime>=55) {
        			yx=locatex;
        			yy=locatey;
        			jframe.setLocation(locatex, locatey);
        			timerperiod=100;
        			
        		}
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
					
			}
			},0,timerperiod);
    	Timer timer2 = new Timer();
    	timer2.schedule(new TimerTask() {
    		public void run() {
    			System.gc();

        }},0,30000);
	}    
   

    String pluszero(long a)
    {
    	String b=""+a;
    	if(a<10) {
    		b="0"+a;
    	}
    	return b;
    }
    public static void main(String args[]) {  
    	if(file2.exists()==false) {
    		JOptionPane.showMessageDialog(null,"您可能是盗版软件的受害者！请联系XuPeng Studio（QQ：2840772674）获取正版。","盗版提示",JOptionPane.WARNING_MESSAGE);
    		System.exit(0);
    	
    	}else {
    		try {
        		FileInputStream input = new FileInputStream(file2);
        		byte byt[] = new byte[1024];
        		int len = input.read(byt);
        		String check = new String(byt, 0, len);
        		input.close();
        		if(check.length()>=128) {
        			Boolean b[]=new Boolean[128];
        			for(int i = 0; i<128;i++) {
        				int m =  stringToAscii(check.substring(i,i+1));
        				if(m<128) {
        				b[m]=true;
        						}
        			}
        			for(int i = 48;i < 127;i++) {
        				if(b[i]==false) {
        					JOptionPane.showMessageDialog(null,"您可能是盗版软件的受害者！请联系XuPeng Studio（QQ：2840772674）获取正版。","盗版提示",JOptionPane.WARNING_MESSAGE);
        		    		System.exit(0);
        				}
        			}
        		}else {
        			JOptionPane.showMessageDialog(null,"您可能是盗版软件的受害者！请联系XuPeng Studio（QQ：2840772674）获取正版。","盗版提示",JOptionPane.WARNING_MESSAGE);
        			System.exit(0);
            	}
        		}catch(Exception e) {
        			e.printStackTrace();
        		}
    	}
    	
       if(file.exists()==false) {
    		try {
    	    	   file.createNewFile();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	
    	}
       else {
    	   try {
    		   @SuppressWarnings("resource")
    		   RandomAccessFile raf = new RandomAccessFile(file, "rw");
    		   FileChannel fc = raf.getChannel();
    		   FileLock fl = fc.tryLock();
    		   if(fl==null) {
    			   JOptionPane.showMessageDialog(null,"已经存在一个实例了","警告",JOptionPane.WARNING_MESSAGE);
    			   System.exit(0);
	    		}
    	   } catch (FileNotFoundException  e) {
	    		   // TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
				
    	   	
       }
    	file.deleteOnExit();
    	
		
		
    	new MainActivity().abcde(new String("2022-06-07 00:00:00"),"距离2022年高考还有",1,"高考");
    	new MainActivity().abcde(new String("2022-01-06 00:00:00"),"距离2022年选考还有",2,"选考");
    	new MainActivity().controller();
    	   
    }
    	public static int stringToAscii(String calue) {
 		   char[] chars = calue.toCharArray(); // 把字符中转换为字符数组   
 		   int lk =(int)chars[0];
 	  
 	     return lk;

 	   }
    public void controller() {
    	
    	JDialog aboutFrame = new JDialog();
    	String copyright = "";
    	try {
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse("kcb.xml");
		switch(doc.getElementsByTagName("showtheform").item(0).getFirstChild().getNodeValue()) {
			case "true":

				total=3;
				kcbActivity.kkk = 1;
				if(showFrameatTheSameTime) {kcbActivity.kkk=3;}	
				break;
			case "1":
				total=3;
				kcbActivity.kkk = 1;
				if(showFrameatTheSameTime) {kcbActivity.kkk=3;}
				break;
			case "2":
				total=3;
				if(showFrameatTheSameTime) {kcbActivity.kkk=3;}
				kcbActivity.kkk = 2;
				if(showFrameatTheSameTime) {kcbActivity.kkk=3;}
				break;
			case "3":
				total=1;
				kcbActivity.kkk=3;
		}
		}catch(Exception e) {
    		e.printStackTrace();
    	}
		kcbActivity.abcde();
		kcbActivity.kcbMain();
		try {
    		FileInputStream input = new FileInputStream(file2);
    		byte byt[] = new byte[1024];
    		int len = input.read(byt);
    		String check = new String(byt, 0, len);
    		input.close();
    		String m = check.substring(0,1);
    		if(m.equals("0")) {
    			copyright=new String("本许可仅供七人免费使用，未经许可，不得更改");
    		}else if(m.equals("1")) {
    			copyright = new String("该许可为单人购买，未经许可，不得更改");
    		}
    				
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
	
		Container aboutContainer = aboutFrame.getContentPane();
		JLabel aboutText = new JLabel("<html><body><p>浙江省考试倒计时2022年专用版<br />Version 1.1 with Updated 1<br />Updated 2021-06-29<br />版权所有&copy;XuPeng Studio,2019~2020.<br /><br />许可："+copyright+"<br />罗浮中学专用版 2021</p></body></html>");
		aboutText.setSize(300,200);
		aboutText.setLocation(0, 50);
		aboutContainer.add(aboutText);
		aboutFrame.setSize(250,200);
		aboutFrame.setResizable(false);
		aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    	if(SystemTray.isSupported()) {
    		
    		URL resource = MainActivity.class.getResource("/icon3.png");
    		ImageIcon icon = new ImageIcon(resource);
    		PopupMenu popupMenu = new PopupMenu();
    		MenuItem item = new MenuItem("退出");
    		item.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e) {
    				System.exit(0);
    			}
    			});

    		MenuItem item2 = new MenuItem("关于");
    		item2.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e) {
    				aboutFrame.setVisible(true);
    			}
    			});
    		MenuItem item3 = new MenuItem("置顶");
    		item3.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e) {
    				if(jframe.isAlwaysOnTop()) {
    					jframe.setAlwaysOnTop(false);
    					onTop=false;
    					kcbActivity.onTop=false;
    					kcbActivity.kcbMainframe.setAlwaysOnTop(true);
    					item3.setLabel("置顶");
    				}
    				else {
    					jframe.setAlwaysOnTop(true);
    					onTop=true;
    					kcbActivity.kcbMainframe.setAlwaysOnTop(true);
    					kcbActivity.onTop=true;
    					item3.setLabel("取消置顶");
    				}
    			}
    			});
    		MenuItem item4 = new MenuItem("设置");
    		item4.addActionListener(new ActionListener()
    			{
    			public void actionPerformed(ActionEvent e) {
    				kcbActivity.kcbsettings();
    			}});
    		
    		MenuItem item5 = new MenuItem("三号窗口");
    		item5.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e) {
    				try {
        				switch(kcbActivity.kkk) {
        				case 0:
        					
        						total=3;
        						kcbActivity.kkk = 1;
        						if(showFrameatTheSameTime) {kcbActivity.kkk=3;}
    						break;
        					case 1:
        						total=3;
        						kcbActivity.kkk = 2;
        						if(showFrameatTheSameTime) {kcbActivity.kkk=3;}
        						break;
        					case 2:	
        						total=2;
        						kcbActivity.kkk = 0;
        						if(showFrameatTheSameTime) {kcbActivity.kkk=3;}
        						break;
        					case 3:
        						total=1;
        						kcbActivity.kkk=0;
        						break;
        				}
        		}catch(Exception err) {
            		err.printStackTrace();
            	}
        		}
    			}
    			);
    		MenuItem item6 = new MenuItem("反馈");
    		item6.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e) {
    				if(Desktop.isDesktopSupported()) {
    				URI uri = URI.create("https://forms.office.com/Pages/ResponsePage.aspx?id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAAO__RPuiSdUN1NTT0VBNEdBUVIxMkFFNTEyVUNNRElXTS4u");
    				Desktop dp = Desktop.getDesktop();
    				  if(dp.isSupported(Desktop.Action.BROWSE)){
    	                    //获取系统默认浏览器打开链接
    	                    try {
								dp.browse(uri);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
    				  }
    			}
    			}
    			});
    		popupMenu.add(item5);
    		popupMenu.add(item4);
    		popupMenu.add(item3);
    		popupMenu.add(item2);
    		popupMenu.add(item6);
    		popupMenu.add(item);
    		TrayIcon trayicon = new TrayIcon(icon.getImage(),"2022年浙江考试倒计时\n状态正常",popupMenu);
    		SystemTray systemTray = SystemTray.getSystemTray();
    		try {
				systemTray.add(trayicon);
			} catch (AWTException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
    	}
    	
    }

    }
