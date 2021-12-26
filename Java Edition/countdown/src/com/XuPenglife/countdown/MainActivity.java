package com.XuPenglife.countdown;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainActivity extends JFrame{
    /**
	 * 
	 */
	public int timerperiod = 15;
	public int chengetime = 0;
	public int changeSpeed=10;
	public int yx = 0;
	public int yy =0;
	public static File file = new File(System.getenv("APPDATA")+"/settingsaved.countdownsettings");
	public static File file2 = new File("./ruggedkey");
	JComboBox<String> jc = new JComboBox<>(new MyComboBox());
	Date now =new Date();
	public static String setsize1 = "FF0000";
	public static String setface1 = "����";
	public static Boolean onTop =false;
	public Color ylColor =new Color(0,0,0,0);
	public String ss = "";
	JWindow jframe=new JWindow();
	JLabel dayJlabel=new JLabel("",JLabel.CENTER);
	JLabel HmsJlabel=new JLabel("",JLabel.CENTER);
	public static Color allcolor = new Color(255,0,0,0);
	private static final long serialVersionUID = 1L;
	public void abcde(String settime,String title,int id) {
    	Container container=jframe.getContentPane();
    	Font font=new Font("����",Font.PLAIN,80);
        Font font3=new Font("����",Font.PLAIN,25);
        Font font2=new Font("����",Font.PLAIN,30);
        allcolor=new Color((int)Long.parseLong(setsize1.substring(0,2),  16),(int)Long.parseLong(setsize1.substring(2,4),  16),(int)Long.parseLong(setsize1.substring(4),  16),255); //���û����ʼֵ
		ylColor=allcolor;
        JProgressBar progress=new JProgressBar();
        progress.setMinimum(0);
        progress.setMaximum(10000);
        progress.setSize(250,20);
        progress.setLocation(0,240);
        container.add(progress);
    	container.add(dayJlabel);
    	container.add(HmsJlabel);
        jframe.setBackground(new Color(0,0,0,0));
        jframe.setAlwaysOnTop(false);
        JLabel b = new JLabel(title,JLabel.CENTER);
        dayJlabel.setSize(250,200);
        dayJlabel.setLocation(0, 0);
        HmsJlabel.setSize(250,50);
        HmsJlabel.setLocation(0, 150);
        HmsJlabel.setFont(font2);
        container.add(b);
        JLabel c = new JLabel("�������ģ��������߹���",JLabel.CENTER);
        c.setFont(font3);
        c.setSize(250,50);
        c.setLocation(0, 200);
        container.add(c);
        JLabel a = new JLabel();
        container.add(a);
    	c.setForeground(allcolor);
    	b.setForeground(allcolor);
    	b.setFont(font3);
    	a.setForeground(allcolor);
    	HmsJlabel.setForeground(allcolor);
    	dayJlabel.setForeground(allcolor);
    	jframe.setSize(250,300);
    	jframe.setVisible(true);
    	Timer timer = new Timer();
		dayJlabel.setFont(font);
    	timer.schedule(new TimerTask() {
    		public void run() {
        		now=new Date();
        		long result=0;
        		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        		try {
        	        b.setSize(250,50);
        	        b.setLocation(0, 0);
        			Date toDate1 = simpleFormat.parse(settime);  
            		long from1 = now.getTime();  
            		long to1 = toDate1.getTime();
            		result= to1- from1;
            		if(result<0) {
            			jframe.setVisible(false);
            		}
            		b.setText(new String(""+title+""));
            		dayJlabel.setText(new String(""+(result/86400000+1)+"��"));
    	      		long hour=result/3600000%24;
    	      		long minute=result/60000%60;
    	      		long second=result/1000%60;
    	      		
    	      		HmsJlabel.setText(new String((result/86400000)+"��"+pluszero(hour)+":"+pluszero(minute)+":"+pluszero(second)+""));// sleep()��ͬ���ӳ����ݣ����һ������߳�
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
        		if(ss!=setface1) {
        		b.setFont(new Font(setface1,Font.PLAIN,25));
                HmsJlabel.setFont(new Font(setface1,Font.PLAIN,30));
    			dayJlabel.setFont(new Font(setface1,Font.PLAIN,80));
    			ss=setface1;
        		}
    			b.setHorizontalAlignment(JLabel.CENTER);
    			HmsJlabel.setHorizontalAlignment(JLabel.CENTER);
    			dayJlabel.setHorizontalAlignment(JLabel.CENTER);
    			jframe.setAlwaysOnTop(onTop);
    			if(ylColor!=allcolor) {
    			b.setForeground(allcolor);
    			dayJlabel.setForeground(allcolor);
        		HmsJlabel.setForeground(allcolor);
        		ylColor=allcolor;
        		
    			}
    			Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        		int locatex=(int)screensize.getWidth()-250;
        		int locatey=(int)(screensize.getHeight()-40)/2*(id-1);
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
        }},0,timerperiod);
    	Timer timer2 = new Timer();
    	timer2.schedule(new TimerTask() {
    		public void run() {
        		now=new Date();
        		float result=0;
        		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        		try {
        			Date toDate1 = simpleFormat.parse("2022-06-06 00:00");
        			Date startDate1 = simpleFormat.parse("2021-06-10 00:00");
            		float from1 = now.getTime();
            		float to1 = toDate1.getTime();
            		float start = startDate1.getTime();
            		result= ((from1-start)/(to1-start))*100;
            		
            		c.setForeground(allcolor);
            		b.setForeground(allcolor);
                	a.setForeground(allcolor);
            		c.setText(new String("��սһ�꣺"+Math.rint(result*100)/100+"%"));
            		progress.setValue((int)(result*100));
            		progress.setString(""+(int)result+"%");
            		// sleep()��ͬ���ӳ����ݣ����һ������߳�
    			} catch (ParseException e) {
    				e.printStackTrace();
    	      		
    			}
        		c.setHorizontalAlignment(JLabel.CENTER);
        		 c.setFont(new Font(setface1,Font.PLAIN,25));
        }},0,100);
    	Timer timer3 = new Timer();
    	timer3.schedule(new TimerTask() {
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
    	if(file.exists()) {
    		try {
    		FileInputStream input = new FileInputStream(file);
    		byte byt[] = new byte[1024];
    		int len = input.read(byt);
    		String settings = new String(byt, 0, len);
    		input.close();
    		if(settings != null) {
    			int m = settings.indexOf("\n");
    			setface1 = new String(settings.substring(0,m));
    			setsize1 = new String(settings.substring(m+1));
    		}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	
    	}else {
    		try {
    			file.createNewFile();
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	if(file2.exists()==false) {
    		JOptionPane.showMessageDialog(null,"�������ǵ���������ܺ��ߣ�����ϵXuPeng Studio��2840772674����ȡ���档","������ʾ",JOptionPane.WARNING_MESSAGE);
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
        					JOptionPane.showMessageDialog(null,"�������ǵ���������ܺ��ߣ�����ϵXuPeng Studio��2840772674����ȡ���档","������ʾ",JOptionPane.WARNING_MESSAGE);
        		    		System.exit(0);
        				}
        			}
        			
        		}else {
        			JOptionPane.showMessageDialog(null,"�������ǵ���������ܺ��ߣ�����ϵxuPeng Studio��2840772674����ȡ���档","������ʾ",JOptionPane.WARNING_MESSAGE);
        			System.exit(0);
            	}
        		}catch(Exception e) {
        			e.printStackTrace();
        		}
    	}
    	new MainActivity().controller();
    	new MainActivity().abcde(new String("2022-06-07 00:00:00"),"����2022��߿�����",1);
    	new MainActivity().abcde(new String("2022-01-06 00:00:00"),"����2022��ѡ������",2);

    	   
    }
   public static int stringToAscii(String calue) {
	   char[] chars = calue.toCharArray(); // ���ַ���ת��Ϊ�ַ�����   
	   int lk =(int)chars[0];
  
     return lk;

   }
    public void controller() {
    	JDialog aboutFrame = new JDialog();
		Container aboutContainer = aboutFrame.getContentPane();
		String copyright = "";
			try {
	    		FileInputStream input = new FileInputStream(file2);
	    		byte byt[] = new byte[1024];
	    		int len = input.read(byt);
	    		String check = new String(byt, 0, len);
	    		input.close();
	    		String m = check.substring(0,1);
	    		if(m.equals("0")) {
	    			copyright=new String("����ɽ����������ʹ�ã�δ����ɣ����ø���");
	    		}else if(m.equals("1")) {
	    			copyright = new String("�����Ϊ���˹���δ����ɣ����ø���");
	    		}
	    				
	    		}catch(Exception e) {
	    			e.printStackTrace();
	    		}
			JLabel aboutText = new JLabel("<html><body><p>�㽭ʡ���Ե���ʱ2022��ר�ð�<br />Version 1.1 with Updated 1<br />Updated 2021-06-29 ����Ҫ���£�<br />��Ȩ����&copy;XuPeng Studio,2019~2020.<br /><br />��ɣ�"+copyright+"</p></body></html>");
			aboutText.setSize(300,200);
		aboutText.setLocation(0, 50);
		aboutContainer.add(aboutText);
		aboutFrame.setSize(250,200);
		aboutFrame.setResizable(false);
		aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		URL resource = MainActivity.class.getResource("/icon3.png");
		ImageIcon icon = new ImageIcon(resource);
		
		JFrame controlFrame = new JFrame("����ʱ����̨");
		jc.setSize(120, 25);
		controlFrame.setIconImage(icon.getImage());
		jc.setLocation(70, 0);
		jc.addItemListener(new ItemListener()
		  {
		   @Override
		   public void itemStateChanged(ItemEvent e)
		   {
		    //���ѡ����һ��Ϯ 
		    if (e.getStateChange() == ItemEvent.SELECTED)
		   {
		    //����д������� ������zdȡ�����ڵ�ֵ  
		       setface1 =new String((String)jc.getSelectedItem());
		       }
		   }
		  });
		JLabel x = new JLabel();
		Container controlContainer = controlFrame.getContentPane();
		JLabel controlText = new JLabel("��������");
		controlText.setSize(100,25);
		controlText.setLocation(0, 0);
		controlContainer.add(controlText);

		controlFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel warning = new JLabel("<html><body><p>���������ú���ɫ���ò��ܱ�֤�����������ɫ�����������ݴ˵���ʱ���򡣲��ҵ��³����ȶ�����������������ɫ����������ѡ��</p></body></html>");
        warning.setSize(200,100);
		warning.setLocation(0, 25);
		JLabel setcolor = new JLabel("<html><body><p>��<br /><br />��<br /><br />��</p></body></html>");
        setcolor.setSize(25,100);
		setcolor.setLocation(0, 110);
		JSlider sl1=new JSlider();
		sl1.setMaximum(255);
		sl1.setMinimum(0);//���û��������Сֵ
		JSlider sl2=new JSlider();
		sl2.setMaximum(255);
		sl2.setMinimum(0);
		JSlider sl3=new JSlider();
		sl3.setMaximum(255);
		sl2.setMinimum(0);
		controlContainer.add(setcolor);
		sl1.addChangeListener(new ChangeListener() {
		
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO �Զ����ɵķ������
				int s = sl1.getValue();	
				allcolor=new Color(s,sl2.getValue(),sl3.getValue(),255); //���û����ʼֵ
				
			}
			
		});
		sl2.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO �Զ����ɵķ������
				// TODO �Զ����ɵķ������
				int s = sl2.getValue();
				allcolor=new Color(sl1.getValue(),s,sl3.getValue(),255); //���û����ʼֵ
				
			}
			
		});
		sl3.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO �Զ����ɵķ������
				int s = sl3.getValue();
				allcolor=new Color(sl1.getValue(),sl2.getValue(),s,255); //���û����ʼֵ
				
			}});
		sl1.setValue((int)Long.parseLong(setsize1.substring(0,2),  16));
		sl2.setValue((int)Long.parseLong(setsize1.substring(2,4),  16));
		sl3.setValue((int)Long.parseLong(setsize1.substring(4),  16));
		allcolor=new Color(sl1.getValue(),sl2.getValue(),sl3.getValue(),255); //���û����ʼֵ
		sl1.setSize(180,25);
		sl2.setSize(180,25);
		sl3.setSize(180,25);
		sl1.setLocation(20,120);
		sl2.setLocation(20,150);
		sl3.setLocation(20,180);
		controlFrame.add(sl1);
		controlFrame.add(sl2);
		controlFrame.add(sl3);
		controlFrame.add(jc);
		controlFrame.add(warning);
		controlFrame.add(x);
		controlFrame.setSize(220,250);
		controlFrame.setResizable(false);
    	if(SystemTray.isSupported()) {
    		
    		URL resource2 = MainActivity.class.getResource("/icon3.png");
    		ImageIcon icon2 = new ImageIcon(resource2);
    		PopupMenu popupMenu = new PopupMenu();
    		MenuItem item = new MenuItem("�˳�");
    		item.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e) {
    				try {
    					FileOutputStream out = new FileOutputStream(file);
    					byte buy[] = new String(setface1+"\n"+setsize1).getBytes();
    					out.write(buy);
    					out.close();
    				}catch(Exception q) {
    					q.printStackTrace();}
    				System.exit(0);
    			}
    			});

    		MenuItem item2 = new MenuItem("����");
    		item2.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e) {
    				aboutFrame.setVisible(true);
    			}
    			});
    		MenuItem item4 = new MenuItem("�ö�");
    		item4.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e) {
    				if(jframe.isAlwaysOnTop()) {
    					jframe.setAlwaysOnTop(false);
    					onTop=false;
    					item4.setLabel("�ö�");
    				}
    				else {
    					jframe.setAlwaysOnTop(true);
    					onTop=true;
    					item4.setLabel("ȡ���ö�");
    				}
    			}
    			});
    		MenuItem item5 = new MenuItem("����");
    		item5.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e) {
    				if(Desktop.isDesktopSupported()) {
    				URI uri = URI.create("https://forms.office.com/Pages/ResponsePage.aspx?id=DQSIkWdsW0yxEjajBLZtrQAAAAAAAAAAAAO__RPuiSdUN1NTT0VBNEdBUVIxMkFFNTEyVUNNRElXTS4u");
    				Desktop dp = Desktop.getDesktop();
    				  if(dp.isSupported(Desktop.Action.BROWSE)){
    	                    //��ȡϵͳĬ�������������
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
    		MenuItem item3 = new MenuItem("����̨");
    		item3.addActionListener(new ActionListener(){
    			public void actionPerformed(ActionEvent e) {
    				controlFrame.setVisible(true);
    			}
    			});
    		popupMenu.add(item4);
    		popupMenu.add(item3);
    		popupMenu.add(item2);
    		popupMenu.add(item5);
    		popupMenu.add(item);
    		SystemTray systemTray = SystemTray.getSystemTray();
    		TrayIcon trayicon = new TrayIcon(icon2.getImage(),"2022���㽭���Ե���ʱ",popupMenu);
    			
    		try {
				systemTray.add(trayicon);
			} catch (AWTException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
    	}
    	
    	}
    String DECTOHEX(int dec) {
    	String abc = "0123456789ABCDEF";
		String result="";
		while(dec!=0){
			int r = dec%16;
			dec=dec/16;
			result=abc.charAt(r)+result;
		}
		if(result.length()==1) {
			result = "0"+result;
		}
		else if(result.length()==0) {
			result = "00";
		}
    	return result;
		
	}

    }
class MyComboBox extends AbstractListModel<String> implements ComboBoxModel<String> {
	

/**
	 * 
	 */
	private static final long serialVersionUID = -5112600988233812280L;
String selecteditem=new String(com.XuPenglife.countdown.MainActivity.setface1);
GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
String[] fontName = {"����","����","����","����"};
@Override
public String getElementAt(int index) {
	// TODO �Զ����ɵķ������
	return fontName[index];
}

@Override
public int getSize() {
	// TODO �Զ����ɵķ������
	return fontName.length;
}

@Override
public Object getSelectedItem() {
	// TODO �Զ����ɵķ������
	return selecteditem;
}

@Override
public void setSelectedItem(Object anItem) {
	selecteditem = (String)anItem;// TODO �Զ����ɵķ������
	
}
}