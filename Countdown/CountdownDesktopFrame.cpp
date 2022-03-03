// Countdown.cpp : ����Ӧ�ó������ڵ㡣
//

#include "stdafx.h"
#include "Countdown.h"
#include <windows.h>
#define MAX_LOADSTRING 100
#pragma comment (lib,"User32.lib")
#include <WinUser.h>
#include <WindowsX.h>
#include <time.h>
#include <stdio.h>
#include <string>
#include <comdef.h>
#include <sstream>
#include <commctrl.h> 
#include <CommDlg.h>
#include <ShellAPI.h>
#include <vector>
#define COMPILE_MULTIMON_STUBS
#include <winuser.h>
#define ID_SHOW		40001
#define IDM_SETTING  1000
#define ID_EXIT		40002
using namespace std;
#pragma comment(linker,"\"/manifestdependency:type='win32' \
name='Microsoft.Windows.Common-Controls' version='6.0.0.0' \
processorArchitecture='*' publicKeyToken='6595b64144ccf1df' language='*'\"")
// ȫ�ֱ���:
HINSTANCE hInst;								// ��ǰʵ��
TCHAR szTitle[MAX_LOADSTRING];					// �������ı�
TCHAR szWindowClass[MAX_LOADSTRING];			// ����������
TCHAR szWindowClass2[MAX_LOADSTRING];			// �Ӵ�������
string pluszero(int x);
HWND hStatic1,hStatic2,hStatic3,hStatic4,hProgressBar;
HWND hDlg_intab[5];	//����Ҫ���뵽TAB�ؼ��еĶԻ�����
HWND htabctrl;
HWND MAIN_LISTCONTROL,MAIN_LISTCONTROL2;
HMENU hPopupMenu;
NOTIFYICONDATA nid = {};
bool AboutDialogisopened = false;
bool SettingDialogisopened = false;
bool col2go,col3go;
vector<RECT> forms(0);
vector<LPTSTR> settimes(0);
vector<LPTSTR> formname(0);
vector<HWND> CountdownForm(0);
vector<HWND> FormStatic2s(0);
vector<HWND> FormStatic3s(0);
bool changed;
HWND MAIN_DATETIMECONTROL1,MAIN_DATETIMECONTROL2;
HWND BASIC_CHECKBOX1;
HWND BASIC_COMBOCONTROL;
// �˴���ģ���а����ĺ�����ǰ������:
ATOM				MyRegisterClass(HINSTANCE hInstance);
BOOL				InitInstance(HINSTANCE, int);
LRESULT CALLBACK	WndProc(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK	About(HWND, UINT, WPARAM, LPARAM);
INT_PTR CALLBACK	Setting(HWND, UINT, WPARAM, LPARAM);
BOOL				isBorderDrawed;
BOOL WINAPI PageA(HWND hwnd, UINT uMsg, WPARAM wParam, LPARAM lParam);//�����Ӵ��ڵĴ��ڴ�����̺�������
BOOL WINAPI PageB(HWND hwnd, UINT uMsg, WPARAM wParam, LPARAM lParam);
BOOL WINAPI PageC(HWND hwnd, UINT uMsg, WPARAM wParam, LPARAM lParam);
BOOL WINAPI PageD(HWND hwnd, UINT uMsg, WPARAM wParam, LPARAM lParam);
BOOL WINAPI PageE(HWND hwnd, UINT uMsg, WPARAM wParam, LPARAM lParam);
void InsertCountdownFrameinCountdownColumn(HWND hList);
void InsertFrameinBasicColumn(HWND hList);
HWND hWndMain;
LPCTSTR lpFileName = _T(".\\Settingsaved.ini");
BOOL CALLBACK MonitorEnumProc(HMONITOR hMonitor,HDC hdcMonitor,LPRECT lprcMonitor,LPARAM dwData);
wchar_t* calctime(struct tm settime,int num);
void InsertCountdowninMainColumn(HWND hList);
void InsertProgressinMainColumn(HWND hList);
typedef BOOL(WINAPI *DIALOGPROC)(HWND hDlg,UINT uMsg,WPARAM wParam,LPARAM lParam); //����һ�� ����ָ��
wchar_t* calctimeprogress(struct tm starttime,struct tm endtime);
HWND BASIC_LISTBOX1;
HWND COUNTDOWNTABLE_COUNTDOWN;
LPTSTR LongToLPTSTR(long number);
static LOGFONT lf; 
LPTSTR lpPath = new TCHAR[1024];
LRESULT CALLBACK WndProcSmall(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam);
int APIENTRY _tWinMain(HINSTANCE hInstance,
                     HINSTANCE hPrevInstance,
                     LPTSTR    lpCmdLine,
                     int       nCmdShow)
{
	UNREFERENCED_PARAMETER(hPrevInstance);
	UNREFERENCED_PARAMETER(lpCmdLine);
 	// TODO: �ڴ˷��ô��롣
	MSG msg;
	HACCEL hAccelTable;

	// ��ʼ��ȫ���ַ���
	LoadString(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
	LoadString(hInstance, IDC_COUNTDOWN, szWindowClass, MAX_LOADSTRING);
	LoadString(hInstance, IDS_APP_MDI, szWindowClass2, MAX_LOADSTRING);
	MyRegisterClass(hInstance);
	LPTSTR szBuffer = new TCHAR[2048];
	LPTSTR xa= new TCHAR[2048];
	GetEnvironmentVariable(_T("APPDATA"),xa,2048);
	wsprintf(szBuffer,_T("%s%s"),xa,_T("\\Settingsaved.ini"));
	lpFileName = szBuffer;
	WNDCLASSEX wcex2;

	wcex2.cbSize = sizeof(WNDCLASSEX);

	wcex2.style			= CS_HREDRAW | CS_VREDRAW;
	wcex2.lpfnWndProc	= WndProcSmall;
	wcex2.cbClsExtra	= 0;
	wcex2.cbWndExtra	= 0;
	wcex2.hInstance		= hInstance;
	wcex2.hIcon			= LoadIcon(hInstance, MAKEINTRESOURCE(IDI_COUNTDOWN));
	wcex2.hCursor		= LoadCursor(NULL, IDC_ARROW);
	wcex2.hbrBackground	= (HBRUSH)(COLOR_WINDOW+1);
	wcex2.lpszMenuName	= MAKEINTRESOURCE(IDC_COUNTDOWN);
	wcex2.lpszClassName	= szWindowClass2;
	wcex2.hIconSm		= LoadIcon(wcex2.hInstance, MAKEINTRESOURCE(IDI_SMALL));
	RegisterClassEx(&wcex2);
	// ִ��Ӧ�ó����ʼ��:
	if (!InitInstance (hInstance, nCmdShow))
	{
		
		return FALSE;
	}
	hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_COUNTDOWN));
	
    
	// ����Ϣѭ��:
	while (GetMessage(&msg, NULL, 0, 0))
	{
		if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
	}
	
	return (int) msg.wParam;
}

//
//  ����: MyRegisterClass()
//
//  Ŀ��: ע�ᴰ���ࡣ
//
//  ע��:
//
//    ����ϣ��
//    �˴�������ӵ� Windows 95 �еġ�RegisterClassEx��
//    ����֮ǰ�� Win32 ϵͳ����ʱ������Ҫ�˺��������÷������ô˺���ʮ����Ҫ��
//    ����Ӧ�ó���Ϳ��Ի�ù�����
//    ����ʽ��ȷ�ġ�Сͼ�ꡣ
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
	WNDCLASSEX wcex;

	wcex.cbSize = sizeof(WNDCLASSEX);

	wcex.style			= CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc	= WndProc;
	wcex.cbClsExtra		= 0;
	wcex.cbWndExtra		= 0;
	wcex.hInstance		= hInstance;
	wcex.hIcon			= LoadIcon(hInstance, MAKEINTRESOURCE(IDI_COUNTDOWN));
	wcex.hCursor		= LoadCursor(NULL, IDC_ARROW);
	wcex.hbrBackground	= (HBRUSH)(COLOR_WINDOW+1);
	wcex.lpszMenuName	= MAKEINTRESOURCE(IDC_COUNTDOWN);
	wcex.lpszClassName	= szWindowClass;
	wcex.hIconSm		= LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));
	
	
	return RegisterClassEx(&wcex);
}

//
//   ����: InitInstance(HINSTANCE, int)
//
//   Ŀ��: ����ʵ�����������������
//
//   ע��:
//
//        �ڴ˺����У�������ȫ�ֱ����б���ʵ�������
//        ��������ʾ�����򴰿ڡ�
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
   HWND hWnd;             
	hInst = hInstance; // ��ʵ������洢��ȫ�ֱ�����
	RECT rect;
	SystemParametersInfo(SPI_GETWORKAREA, 0, &rect, 0);
	int cx = rect.right - rect.left;
	int cy = rect.bottom - rect.top;
   hWnd = CreateWindow(szWindowClass, szTitle,NULL,
      cx-300,0, 300, 190, NULL, NULL, hInstance, NULL);
   hWndMain=hWnd;
   if (!hWnd)
   {
      return FALSE;
   }
   SetWindowLong(hWnd,GWL_STYLE,GetWindowLong(hWnd,GWL_STYLE) & ~WS_CAPTION);
   SetWindowLong(hWnd, GWL_EXSTYLE, WS_EX_LAYERED | WS_EX_TOOLWINDOW );
   SetLayeredWindowAttributes(hWnd,RGB(255,255,255),0,1);
   ShowWindow(hWnd, nCmdShow);
   
   UpdateWindow(hWnd);
   return TRUE;
}

//
//  ����: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  Ŀ��: ���������ڵ���Ϣ��
//
//  WM_COMMAND	- ����Ӧ�ó���˵�
//  WM_PAINT	- ����������
//  WM_DESTROY	- �����˳���Ϣ������
//
//


BOOL FindFirstFileExists(LPCTSTR lpPath, DWORD dwFilter)
{
    WIN32_FIND_DATA fd;
    HANDLE hFind = FindFirstFile(lpPath, &fd);
    BOOL bFilter = (FALSE == dwFilter) ? TRUE : fd.dwFileAttributes & dwFilter;
    BOOL RetValue = ((hFind != INVALID_HANDLE_VALUE) && bFilter) ? TRUE : FALSE;
    FindClose(hFind);
    return RetValue;
}
 
/*
 * ���һ��  ·�� �Ƿ���ڣ�����·�������·�����ļ����ļ��о��ɣ�
 * �����򷵻� 1 (TRUE)
 */
BOOL FilePathExists(LPCTSTR lpPath)
{
    return FindFirstFileExists(lpPath, FALSE);
}

void Updatecount(){
	bool seted2,seted3;
	struct tm settime={0};
	ostringstream osss;
	wchar_t* compare;
	compare = new wchar_t[64];
	while(true){
	seted2=false;seted3=false;
	
		settime.tm_year=2022-1900;settime.tm_mon=6-1;settime.tm_mday=7;
		GetWindowTextW(hStatic2,compare,64);
		if(wcscmp(compare,calctime(settime,1))!=0){
		SetWindowTextW(hStatic2,calctime(settime,1));
		seted2=true;
		}
		GetWindowTextW(hStatic3,compare,64);
		if(wcscmp(compare,calctime(settime,2))!=0){
		SetWindowTextW(hStatic3,calctime(settime,2));
		seted3=true;
		}
		osss.clear();
		osss.str("");
		if(seted3){
		ShowWindow(hStatic3,SW_HIDE);
		ShowWindow(hStatic3,SW_SHOW);
		}
		if(seted2){
		ShowWindow(hStatic2,SW_HIDE);
		ShowWindow(hStatic2,SW_SHOW);
		}
		
		Sleep(250);
	}
}
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	PAINTSTRUCT ps;
	HDC hdc;
	long timeleft;
	int leftday;
	struct tm settime={0};
	time_t endtime;
	ostringstream osss;
	string out;
	wchar_t* output ;
	int xx;
	time_t now;
	UINT WM_TASKBARCREATED;
    RECT main;
	   POINT pt;
	switch (message)
	{
		case WM_USER://����ʹ�øó���ʱ�����Ϣ.
			if (lParam == WM_LBUTTONDOWN&&!SettingDialogisopened){
				
				SettingDialogisopened=true;
				DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOGBAR), hWnd, Setting);
			}
            
        if (lParam == WM_RBUTTONDOWN)
        {
            GetCursorPos(&pt);//ȡ�������
            ::SetForegroundWindow(hWnd);//����ڲ˵��ⵥ������˵�����ʧ������
			xx=TrackPopupMenu(hPopupMenu,TPM_RETURNCMD,pt.x,pt.y,NULL,hWnd,NULL);//��ʾ�˵�����ȡѡ��ID
			if(xx==ID_SHOW&&!AboutDialogisopened){AboutDialogisopened=true;DialogBox(hInst, MAKEINTRESOURCE(IDD_ABOUTBOX), hWnd, About);}
			if(xx==IDM_SETTING&&!SettingDialogisopened){SettingDialogisopened=true;DialogBox(hInst, MAKEINTRESOURCE(IDD_DIALOGBAR), hWnd, Setting);}
			if(xx==ID_EXIT) {PostMessage(hWnd, WM_DESTROY, NULL, NULL);}
            //MessageBox(hwnd, TEXT("�Ҽ�"), szAppName, MB_OK);
        }
        break;

	case WM_CREATE:
		HFONT hFont;
		hStatic1 = CreateWindowEx(0,TEXT("static"),TEXT("����2022��߿�����"),WS_CHILD|WS_VISIBLE|SS_CENTER|SS_CENTERIMAGE,0,0,300,40,hWnd,(HMENU)1,hInst,NULL);
		settime.tm_year=2022-1900;settime.tm_mon=6-1;settime.tm_mday=7;
		hStatic2 = CreateWindow(TEXT("static"),calctime(settime,1),WS_CHILD|WS_VISIBLE|SS_CENTER|SS_CENTERIMAGE ,0,40,300,100,hWnd,(HMENU)1,hInst,NULL);
		osss.clear();
		osss.str("");
		now = time(NULL);
		endtime=mktime(&settime);
		timeleft=(long)difftime(endtime, now);
		leftday = (int)(timeleft / 24 / 60 / 60 +1);
		if(timeleft<0){leftday=0;}
		osss<<"����2022��߿�����"<<leftday<<"��";
		out=osss.str();
		nid.cbSize = sizeof(nid);
		nid.hWnd = hWnd;
		nid.uID=0;
		nid.uCallbackMessage = WM_USER;
		nid.uFlags = NIF_ICON | NIF_TIP | NIF_GUID| NIF_MESSAGE | NIF_INFO;
		nid.hIcon =  LoadIcon(hInst, MAKEINTRESOURCE(IDI_SMALL));
		lstrcpy(nid.szTip, szTitle);
		hPopupMenu = CreatePopupMenu();//�������̲˵�
		//Ϊ���̲˵��������ѡ��
		  WM_TASKBARCREATED = RegisterWindowMessage(TEXT("TaskbarCreated"));
		AppendMenu(hPopupMenu, MF_STRING, ID_SHOW, TEXT("����"));
		AppendMenu(hPopupMenu, MF_STRING, IDM_SETTING, TEXT("����"));
		AppendMenu(hPopupMenu, MF_STRING, ID_EXIT, TEXT("�˳�"));
		output= new wchar_t[osss.str().size()];
		MultiByteToWideChar( CP_ACP, 0, out.c_str(), -1, output, osss.str().size());
		osss.clear();
		osss.str("");
		Shell_NotifyIcon(NIM_ADD, &nid);
		lstrcpy(nid.szInfoTitle, TEXT("��ӭʹ�ø߿�����ʱ2022�汾"));
		lstrcpy(nid.szInfo, output);
		nid.uTimeout = 2000;
		Shell_NotifyIcon(NIM_MODIFY, &nid);
		hStatic3 = CreateWindow(TEXT("static"),calctime(settime,2),WS_CHILD|WS_VISIBLE|SS_CENTER|SS_CENTERIMAGE,0,140,300,50,hWnd,(HMENU)1,hInst,NULL);
		hFont =CreateFont(25,0,0,0,0,FALSE, FALSE,FALSE,DEFAULT_CHARSET,//ʹ��Ĭ���ַ���
                OUT_CHARACTER_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,//Ĭ���������
                FF_DONTCARE, TEXT("����")); //������
		SendMessage(hStatic1, WM_SETFONT, (WPARAM)hFont, NULL);
		hFont =CreateFont(80,0,0,0,0,FALSE, FALSE,FALSE,DEFAULT_CHARSET,//ʹ��Ĭ���ַ���
                OUT_CHARACTER_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,//Ĭ���������
                FF_DONTCARE, TEXT("����"));
		SendMessage(hStatic2, WM_SETFONT, (WPARAM)hFont, NULL);
		hFont =CreateFont(32,0,0,0,0,FALSE, FALSE,FALSE,DEFAULT_CHARSET,//ʹ��Ĭ���ַ���
                OUT_CHARACTER_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,//Ĭ���������
                FF_DONTCARE, TEXT("����"));
		SendMessage(hStatic3, WM_SETFONT, (WPARAM)hFont, NULL);
	osss.clear();
		osss.str("");
		SetWindowLong(hStatic1,GWL_EXSTYLE,WS_EX_TRANSPARENT);
		SetWindowLong(hStatic2,GWL_EXSTYLE,WS_EX_TRANSPARENT);
		SetWindowLong(hStatic3,GWL_EXSTYLE,WS_EX_TRANSPARENT);
		HANDLE h;
		h=CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)Updatecount, NULL, 1, 0); //�������߳�
		ResumeThread(h);
		GetWindowRect(hWnd,&main);
		forms.insert(forms.begin(),main);
		if(FilePathExists(lpFileName))
		{
			formname.clear();
			settimes.clear();
			int count = 0;
			LPTSTR AppName = new TCHAR[1024];
			wsprintf(AppName,_T("Formshow%d"),count);
			long left = GetPrivateProfileInt(AppName,_T("x"),0,lpFileName);
			long top = GetPrivateProfileInt(AppName,_T("y"),0,lpFileName);
			long right = left + 300;
			long bottom = right + 190;
			MoveWindow(hWnd,left,top,300,190,TRUE);
			RECT rect = {0};
			rect.left = left;rect.right = right;rect.top = top;rect.bottom = rect.bottom;
			forms[0] = rect;
			count++;
			while(true)
			{
				LPTSTR str = new TCHAR[1024];
				AppName = new TCHAR[1024];
				wsprintf(AppName,_T("Formshow%d"),count);
				GetPrivateProfileString(AppName,_T("Caption"),NULL,str,1024,lpFileName);
				if(_tcscmp(str,_T(""))==0){
					break;
				}
				LPTSTR hhh = new TCHAR[1024];
				wsprintf(hhh,_T("%s"),str);
				formname.push_back(hhh);
				GetPrivateProfileString(AppName,_T("settime"),NULL,str,1024,lpFileName);
				hhh = new TCHAR[1024];
				wsprintf(hhh,_T("%s"),str);
				settimes.push_back(hhh);
				left = GetPrivateProfileInt(AppName,_T("x"),0,lpFileName);
				top = GetPrivateProfileInt(AppName,_T("y"),0,lpFileName);
				rect.left = left;rect.right = right;rect.top = top;rect.bottom = rect.bottom;
				forms.push_back(rect);
				HWND hwndform = CreateWindow(szWindowClass2,(LPTSTR)_T("����ʱ����"),NULL,(int)left,(int)top,300,190,NULL,NULL,hInst,NULL);
				SetWindowLong(hwndform,GWL_STYLE,GetWindowLong(hwndform,GWL_STYLE) & ~WS_CAPTION);
				SetWindowLong(hwndform, GWL_EXSTYLE, WS_EX_LAYERED | WS_EX_TOOLWINDOW );
				SetLayeredWindowAttributes(hwndform,RGB(255,255,255),0,1);
				SendMessage(hwndform,WM_CREATE,(WPARAM)count,NULL);
				ShowWindow(hwndform, SW_SHOW);
				UpdateWindow(hwndform);
				CountdownForm.insert(CountdownForm.end(),hwndform);
				count++;
			}
		}
	case WM_PAINT:
		hdc = BeginPaint(hWnd, &ps);
		EndPaint(hWnd, &ps);

		break;
	case WM_DESTROY:
		Shell_NotifyIcon(NIM_DELETE, &nid);
		PostQuitMessage(0);
		break;
	case WM_CTLCOLORSTATIC:
        hdc = (HDC)wParam;
        SetTextColor(hdc, RGB(255, 0, 0));
        SetBkMode(hdc, TRANSPARENT);
        return (LRESULT)GetStockObject(HOLLOW_BRUSH);
		break;
	default:
		if (message == RegisterWindowMessage(TEXT("TaskbarCreated")))
		{
			//ϵͳExplorer��������ʱ�����¼�������
			Shell_NotifyIcon(NIM_ADD, &nid);
		}
		return DefWindowProc(hWnd, message, wParam, lParam);
	}
	return 0;
}
LRESULT CALLBACK WndProcSmall(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
	HWND FormStatic1,FormStatic2,FormStatic3;
	PAINTSTRUCT ps;
	HDC hdc;
	HFONT hFont;
	wchar_t* compare;
	
	compare = new wchar_t[512];
	struct tm settime={0};
	string out;
	unsigned int pos;
	wchar_t pItem[1024] = { 0 };
	char times[1024]={0};
	struct tm tm_ = {0};
	LPTSTR str;
	switch (message)
	{
	case WM_CREATE:
		if(wParam!=NULL){
		pos = (int)wParam - 1;
		FormStatic1 = CreateWindowEx(0,TEXT("static"),formname[pos],WS_CHILD|WS_VISIBLE|SS_CENTER|SS_CENTERIMAGE,0,0,300,40,hWnd,(HMENU)1,hInst,NULL);
		str = settimes[pos];
		strncpy_s(times,(LPCSTR)_bstr_t(str),1024);
		int year, month, day;
		sscanf_s(times,"%d-%d-%d", &year, &month, &day);
		tm_.tm_year  = year - 1900;
		tm_.tm_mon   = month - 1;
		tm_.tm_mday  = day;
		hFont =CreateFont(25,0,0,0,0,FALSE, FALSE,FALSE,DEFAULT_CHARSET,//ʹ��Ĭ���ַ���
                OUT_CHARACTER_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,//Ĭ���������
                FF_DONTCARE, TEXT("����")); //������
		SendMessage(FormStatic1, WM_SETFONT, (WPARAM)hFont, NULL);
		
		SetWindowLong(FormStatic1,GWL_EXSTYLE,WS_EX_TRANSPARENT);

		FormStatic2 = CreateWindow(TEXT("static"),calctime(tm_,1),WS_CHILD|WS_VISIBLE|SS_CENTER|SS_CENTERIMAGE ,0,40,300,100,hWnd,(HMENU)1,hInst,NULL);
		FormStatic3 = CreateWindow(TEXT("static"),calctime(tm_,2),WS_CHILD|WS_VISIBLE|SS_CENTER|SS_CENTERIMAGE,0,140,300,50,hWnd,(HMENU)1,hInst,NULL);
		SetWindowLong(FormStatic2,GWL_EXSTYLE,WS_EX_TRANSPARENT);
		SetWindowLong(FormStatic3,GWL_EXSTYLE,WS_EX_TRANSPARENT);
		FormStatic2s.push_back(FormStatic2);
		FormStatic3s.push_back(FormStatic3);
		hFont =CreateFont(80,0,0,0,0,FALSE, FALSE,FALSE,DEFAULT_CHARSET,//ʹ��Ĭ���ַ���
                OUT_CHARACTER_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,//Ĭ���������
                FF_DONTCARE, TEXT("����"));
		SendMessage(FormStatic2, WM_SETFONT, (WPARAM)hFont, NULL);
		hFont =CreateFont(32,0,0,0,0,FALSE, FALSE,FALSE,DEFAULT_CHARSET,//ʹ��Ĭ���ַ���
                OUT_CHARACTER_PRECIS,CLIP_CHARACTER_PRECIS,DEFAULT_QUALITY,//Ĭ���������
                FF_DONTCARE, TEXT("����"));
		SendMessage(FormStatic3, WM_SETFONT, (WPARAM)hFont, NULL);
		SetTimer(hWnd,WM_TIMER,250,NULL);
		}

	case WM_PAINT:
		hdc = BeginPaint(hWnd, &ps);
		EndPaint(hWnd, &ps);
		break;
	case WM_DESTROY:
		DestroyWindow(hWnd);
		break;
	case WM_CTLCOLORSTATIC:
        hdc = (HDC)wParam;
        SetTextColor(hdc, RGB(255, 0, 0));
        SetBkMode(hdc, TRANSPARENT);
        return (LRESULT)GetStockObject(HOLLOW_BRUSH);

		break;

	case WM_TIMER:
		pos = 0;
		for(pos = 0;pos<CountdownForm.size();pos++){
			if(hWnd == CountdownForm[pos]){
				break;
			}
		}
		str = settimes[pos];
		strncpy_s(times,(LPCSTR)_bstr_t(str),1024);
		int year, month, day;
		sscanf_s(times,"%d-%d-%d", &year, &month, &day);
		tm_.tm_year  = year - 1900;
		tm_.tm_mon   = month - 1;
		tm_.tm_mday  = day;
		GetWindowTextW(FormStatic2s[pos],compare,64);
		if(wcscmp(compare,calctime(tm_,1))!=0){
		SetWindowTextW(FormStatic2s[pos],calctime(tm_,1));
		ShowWindow(FormStatic2s[pos],SW_HIDE);
		ShowWindow(FormStatic2s[pos],SW_SHOW);
		}
		GetWindowTextW(FormStatic3s[pos],compare,64);
		if(wcscmp(compare,calctime(tm_,2))!=0){
		SetWindowTextW(FormStatic3s[pos],calctime(tm_,2));
		ShowWindow(FormStatic3s[pos],SW_HIDE);
		ShowWindow(FormStatic3s[pos],SW_SHOW);
		}
		SetWindowLong(FormStatic2s[pos],GWL_EXSTYLE,WS_EX_TRANSPARENT);
		SetWindowLong(FormStatic3s[pos],GWL_EXSTYLE,WS_EX_TRANSPARENT);

		default:
		return DefWindowProc(hWnd, message, wParam, lParam);
	}
	return 0;
}

// �����ڡ������Ϣ�������
INT_PTR CALLBACK About(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
	case WM_INITDIALOG:
		return (INT_PTR)TRUE;

	case WM_COMMAND:
		if (LOWORD(wParam) == IDOK || LOWORD(wParam) == IDCANCEL)
		{
			
			AboutDialogisopened=false;
			EndDialog(hDlg, LOWORD(wParam));
			return (INT_PTR)TRUE;
		}
		break;
	}
	return (INT_PTR)FALSE;
}
INT_PTR CALLBACK Setting(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	UNREFERENCED_PARAMETER(lParam);
	SYSTEMTIME st;
	wchar_t pItem[256] = { 0 };
	LPWSTR tabname[3]={L"��ҳ",L"��������",L"����ʱ��"}; //����һ����ά���� ���tab��ǩ����L"����",L"ʱ���"
	switch (message)
	{
	case WM_INITDIALOG:
		htabctrl=GetDlgItem(hDlg,IDC_TAB1);
		TCITEM tie;//����tab��ǩ������
		//���忪ʼ���� tie���ֶ� Mask psztext,ccxtextmax,image,lparam
		tie.mask=TCIF_TEXT|TCIF_IMAGE;//psztext�ֶ���Ч
		tie.iImage = -1; 
		for(INT i=0;i<3;i++)
		{
			tie.pszText= tabname[i];
			TabCtrl_InsertItem(htabctrl,i,&tie);
		}
		RECT rect;//���tab�ؼ�������λ��
		GetClientRect(htabctrl,&rect);
		// ������������ tab�ؼ�λ���ƶ�
			hDlg_intab[2]=CreateDialog(hInst,MAKEINTRESOURCE(IDD_COUNTDOWNTABLE),htabctrl,PageC);
			MoveWindow(hDlg_intab[2],2,29,rect.right - rect.left-6,rect.bottom - rect.top-35,FALSE);
			hDlg_intab[0]=CreateDialog(hInst,MAKEINTRESOURCE(IDD_MAIN),htabctrl,PageA);
			MoveWindow(hDlg_intab[0],2,29,rect.right - rect.left-6,rect.bottom - rect.top-35,FALSE);
			hDlg_intab[1]=CreateDialog(hInst,MAKEINTRESOURCE(IDD_BASIC),htabctrl,PageB);
			MoveWindow(hDlg_intab[1],2,29,rect.right - rect.left-6,rect.bottom - rect.top-35,FALSE);
			
			/* δ�����ӵ����ݡ�
			hDlg_intab[3]=CreateDialog(hInst,MAKEINTRESOURCE(IDD_CALENDAR),htabctrl,PageD);
			MoveWindow(hDlg_intab[3],2,29,rect.right - rect.left-6,rect.bottom - rect.top-35,FALSE);
			hDlg_intab[4]=CreateDialog(hInst,MAKEINTRESOURCE(IDD_TIMETABLE),htabctrl,PageE);
			MoveWindow(hDlg_intab[4],2,29,rect.right - rect.left-6,rect.bottom - rect.top-35,FALSE);
			*/
			ShowWindow(hDlg_intab[0],TRUE);


			SetForegroundWindow(hDlg);
			
		return (INT_PTR)TRUE;

	case WM_COMMAND:
		if (LOWORD(wParam) == IDC_CANCELBUTTON || LOWORD(wParam) == IDCANCEL)
		{
			
			SettingDialogisopened=false;
			EndDialog(hDlg, LOWORD(wParam));
			return (INT_PTR)TRUE;
		}
		if(LOWORD(wParam)==IDC_APPLYBUTTON || IDC_OKBUTTON)
		{
			LVITEM lvitem;
			int nCount=ListView_GetItemCount(COUNTDOWNTABLE_COUNTDOWN);
			lvitem.mask = LVIF_TEXT;
			if(changed){
				if(FilePathExists(lpFileName))
				{
				BOOL ret;
				ret = DeleteFile(lpFileName);
				if (ret == 0) {
					MessageBox(hDlg,_T("������Ӧ��ʧ�ܣ����������ļ���Ȩ��"),_T("�߿�����ʱ2022�汾"),MB_OK);
					return (INT_PTR)FALSE;
				}
					}
				for(unsigned int imm = 0;imm<CountdownForm.size();imm++){
					SendMessage(CountdownForm[imm],WM_DESTROY,NULL,NULL);
				}
				FormStatic3s.clear();
				FormStatic2s.clear();
				CountdownForm.clear();
				formname.clear();
				settimes.clear();
				SendMessage(BASIC_LISTBOX1,LVM_DELETEALLITEMS,0,0);
				SendMessage(MAIN_LISTCONTROL,LVM_DELETEALLITEMS,0,0);
				lvitem.mask = LVIF_TEXT;
				lvitem.iItem = 0;
				lvitem.iSubItem = 0;
				lvitem.pszText = (LPTSTR)_T("������");
				ListView_InsertItem(BASIC_LISTBOX1,&lvitem);
				lvitem.pszText = (LPTSTR)_T("2022�߿�����ʱ");
				ListView_InsertItem(MAIN_LISTCONTROL,&lvitem);
				lvitem.iSubItem = 1;
				lvitem.pszText = (LPTSTR)_T("2022-06-07");
				ListView_SetItem(BASIC_LISTBOX1, &lvitem);
				lvitem.iSubItem = 2;
				lvitem.pszText = LongToLPTSTR(forms[0].left);
				ListView_SetItem(BASIC_LISTBOX1, &lvitem);
				lvitem.iSubItem = 3;
				lvitem.pszText = LongToLPTSTR(forms[0].top);
				ListView_SetItem(BASIC_LISTBOX1, &lvitem);
				forms.clear();
				RECT MainRect;
				GetWindowRect(hWndMain,&MainRect);
				forms.insert(forms.begin(),MainRect);
				for(int is=1;is<nCount;is++)
				{
					lvitem.iItem = is;
					lvitem.iSubItem = 0;
					lvitem.cchTextMax = 1024;
					lvitem.pszText = pItem;
					SendMessage(COUNTDOWNTABLE_COUNTDOWN, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
					LPTSTR AppName = new TCHAR[1024];
					wsprintf(AppName,_T("Form%d"),is);
					WritePrivateProfileString(AppName,_T("Caption"),lvitem.pszText,lpFileName);
					ListView_InsertItem(MAIN_LISTCONTROL,&lvitem);
					lvitem.iSubItem = 1;
					SendMessage(COUNTDOWNTABLE_COUNTDOWN, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
					WritePrivateProfileString(AppName,_T("Settime"),lvitem.pszText,lpFileName);
					lvitem.iSubItem = 2;
					SendMessage(COUNTDOWNTABLE_COUNTDOWN, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
					WritePrivateProfileString(AppName,_T("ShowForm"),_tcscmp(lvitem.pszText,(LPTSTR)_T("��"))?_T("FALSE"):_T("TRUE"),lpFileName);
					if(!_tcscmp(lvitem.pszText,(LPTSTR)_T("��"))){
						LVITEM item;
						HWND hwndform;
						int x = forms.size();
						RECT rect;
						SystemParametersInfo(SPI_GETWORKAREA, 0, &rect, 0);
						int cx = rect.right - rect.left;
						int cy = rect.bottom - rect.top;
						long left = cx - 300*((x / 3) + 1 );
						long top = 190 * (x % ((int)cy/190));
						RECT rects;
						rects.left = left;
						rects.right = left + 300;
						rects.top = top;
						rects.bottom = top + 190;
						forms.push_back(rects);
						item.mask = LVIF_TEXT;
						item.iItem = ListView_GetItemCount(BASIC_LISTBOX1);
						item.iSubItem = 0;
						lvitem.iItem = is;
						lvitem.iSubItem = 0;
						lvitem.cchTextMax = 1024;
						lvitem.pszText = pItem;
						SendMessage(COUNTDOWNTABLE_COUNTDOWN, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
						item.pszText = lvitem.pszText;
						LPTSTR hhh = new TCHAR[1024];
						wsprintf(hhh,_T("%s"),lvitem.pszText);
						formname.push_back(hhh);
						ListView_InsertItem(BASIC_LISTBOX1, &item);
						lvitem.iSubItem = 1;
						lvitem.pszText = pItem;
						SendMessage(COUNTDOWNTABLE_COUNTDOWN, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
						item.iSubItem = 1;
						hhh = new TCHAR[1024];
						wsprintf(hhh,_T("%s"),lvitem.pszText);
						settimes.push_back(hhh);
						item.pszText = lvitem.pszText;
						ListView_SetItem(BASIC_LISTBOX1, &item);
						item.iSubItem = 2;
						item.pszText = LongToLPTSTR(forms[x].left);
						ListView_SetItem(BASIC_LISTBOX1, &item);
						item.iSubItem = 3;
						item.pszText = LongToLPTSTR(forms[x].top);
						ListView_SetItem(BASIC_LISTBOX1, &item);
						hwndform = CreateWindow(szWindowClass2,(LPTSTR)_T("����ʱ����"),NULL,(int)left,(int)top,300,190,NULL,NULL,hInst,NULL);
						SetWindowLong(hwndform,GWL_STYLE,GetWindowLong(hwndform,GWL_STYLE) & ~WS_CAPTION);
						SetWindowLong(hwndform, GWL_EXSTYLE, WS_EX_LAYERED | WS_EX_TOOLWINDOW );
						SetLayeredWindowAttributes(hwndform,RGB(255,255,255),0,1);
						SendMessage(hwndform,WM_CREATE,(WPARAM)x,NULL);
						ShowWindow(hwndform, SW_SHOW);
						UpdateWindow(hwndform);
						CountdownForm.insert(CountdownForm.end(),hwndform);
					}
				}
				changed=false;
			}
			nCount=ListView_GetItemCount(BASIC_LISTBOX1);
			lvitem.mask = LVIF_TEXT;
			for(int is=0;is<nCount;is++)
			{
				lvitem.iItem = is;
				lvitem.iSubItem = 0;
				lvitem.cchTextMax = 1024;
				lvitem.pszText = pItem;
				SendMessage(BASIC_LISTBOX1, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
				LPTSTR AppName = new TCHAR[1024];
				wsprintf(AppName,_T("Formshow%d"),is);
				WritePrivateProfileString(AppName,_T("Caption"),lvitem.pszText,lpFileName);
				lvitem.iSubItem = 1;
				SendMessage(BASIC_LISTBOX1, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
				WritePrivateProfileString(AppName,_T("settime"),lvitem.pszText,lpFileName);
				lvitem.iSubItem = 2;
				SendMessage(BASIC_LISTBOX1, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
				WritePrivateProfileString(AppName,_T("x"),lvitem.pszText,lpFileName);
				lvitem.iSubItem = 3;
				SendMessage(BASIC_LISTBOX1, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
				WritePrivateProfileString(AppName,_T("y"),lvitem.pszText,lpFileName);
			}
				int posleft,postop;
				lvitem.iItem = 0;
				lvitem.cchTextMax = 1024;
				lvitem.pszText = pItem;
				lvitem.iSubItem = 2;
				SendMessage(BASIC_LISTBOX1, LVM_GETITEMTEXT, (WPARAM)0, (LPARAM)&lvitem);
				char str[1024] = {};
				strncpy_s(str,(LPCSTR)_bstr_t(lvitem.pszText),1024);
				sscanf_s(str,"%d",&posleft );
				lvitem.iSubItem = 3;
				SendMessage(BASIC_LISTBOX1, LVM_GETITEMTEXT, (WPARAM)0, (LPARAM)&lvitem);
				strncpy_s(str,(LPCSTR)_bstr_t(lvitem.pszText),1024);
				sscanf_s(str,"%d",&postop);
				MoveWindow(hWndMain,posleft,postop,300,190,TRUE);
				for(int is = 1;is <nCount;is++){
					lvitem.iItem = is;
					lvitem.cchTextMax = 1024;
					lvitem.pszText = pItem;
					lvitem.iSubItem = 2;

					SendMessage(BASIC_LISTBOX1, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
					strncpy_s(str,(LPCSTR)_bstr_t(lvitem.pszText),1024);
					sscanf_s(str,"%d",&posleft);
					lvitem.iSubItem = 3;
					SendMessage(BASIC_LISTBOX1, LVM_GETITEMTEXT, (WPARAM)is, (LPARAM)&lvitem);
					strncpy_s(str,(LPCSTR)_bstr_t(lvitem.pszText),1024);
					sscanf_s(str,"%d",&postop);
					MoveWindow(CountdownForm[is-1],posleft,postop,300,190,FALSE);
				}
				if(col2go){
				SendMessage(MAIN_DATETIMECONTROL1,DTM_GETSYSTEMTIME,0,(LPARAM)&st);
				LPTSTR szBuffer=new TCHAR[10];//���岢�������뻺�����ռ�
				wsprintf(szBuffer,_T("%d-%d-%d"),st.wYear,st.wMonth,st.wDay);//Ӧ�ý�int xתΪ�ַ���
				WritePrivateProfileString(_T("Settings"),_T("starttime"),szBuffer,lpFileName);
				}
				if(col3go){
				SendMessage(MAIN_DATETIMECONTROL2,DTM_GETSYSTEMTIME,0,(LPARAM)&st);
				LPTSTR szBuffer=new TCHAR[10];//���岢�������뻺�����ռ�
				wsprintf(szBuffer,_T("%d-%d-%d"),st.wYear,st.wMonth,st.wDay);//Ӧ�ý�int xתΪ�ַ���
				WritePrivateProfileString(_T("Settings"),_T("endtime"),szBuffer,lpFileName);
				}
				LRESULT result  = SendMessage(BASIC_CHECKBOX1, BM_GETCHECK, 0, 0);
				SetWindowPos(hWndMain, result ==BST_CHECKED?HWND_TOPMOST:HWND_NOTOPMOST, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
				for(unsigned int num = 0;num<CountdownForm.size();num++)
				{
					SetWindowPos(CountdownForm[num], result ==BST_CHECKED?HWND_TOPMOST:HWND_NOTOPMOST, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE);
				}
				if(LOWORD(wParam)==IDC_OKBUTTON){
					SettingDialogisopened=false;
					EndDialog(hDlg,LOWORD(wParam));
					return (INT_PTR)TRUE;
				}
		}
		break;
	case WM_NOTIFY:		 //TAB�ؼ��л�����ʱ���͵���Ϣ
	{	
		if((INT)wParam==IDC_TAB1) //����Ҳ������һ��NMHDR *nm = (NMHDR *)lParam���ָ������ȡ ������¼�
		{					//���߿����в���NMHDR�ṹ
			if(((LPNMHDR)lParam)->code==TCN_SELCHANGE) //��TAB��ǩת����ʱ����TCN_SELCHANGE��Ϣ
			{	
				
				int sel=TabCtrl_GetCurSel(htabctrl);
				ShowWindow(hDlg_intab[0],sel==0); //��ʾ������ShowWindow����
				ShowWindow(hDlg_intab[1],sel==1);
				ShowWindow(hDlg_intab[2],sel==2);
				/*
				ShowWindow(hDlg_intab[3],sel==3);
				ShowWindow(hDlg_intab[4],sel==4);*/
			}
		}
		break;
		}
    
	}
	return (INT_PTR)FALSE;
}
string pluszero(int x)
{
	ostringstream oss;
	string s;
	if (x<10){
		oss<<"0"<<x;
	}else{
		oss<<x;
	}
	s=oss.str();
	oss.clear();
	oss.str("");
	return s;
}
BOOL WINAPI PageA(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)//MAIN���ں���
{
	long timeleft;
	int leftday,lefthour,leftminute,leftsecond;
	struct tm settime={0};
	struct tm starttime;
	struct tm theendtime;
	time_t endtime;
	ostringstream osss;
	string out;
	wchar_t* output ;
	int nCount;
	time_t now;
	UNREFERENCED_PARAMETER(lParam);
	HWND MAIN_STATIC;
	LV_ITEM   lvItem;
			SYSTEMTIME st={0};
	switch (message)
	{
	case WM_INITDIALOG:
		MAIN_STATIC = GetDlgItem(hDlg,IDC_STATIC);
		now = time(NULL);
		settime.tm_year=2022-1900;settime.tm_mon=6-1;settime.tm_mday=7;
		endtime=mktime(&settime);
		timeleft=(long)difftime(endtime, now);
		leftday = (int)(timeleft / 24 / 60 / 60 +1);
		lefthour = (int)(timeleft / 60 / 60 % 24);
		leftminute = (int)(timeleft / 60 % 60);
		leftsecond = (int)(timeleft % 60);
		osss<<"��ӭʹ��2022�汾�ĸ߿�����ʱ��2022��߿�����ʱ��"<<leftday-1<<"��"<<pluszero(lefthour)<<":"<<pluszero(leftminute)<<":"<<pluszero(leftsecond);
		output= new wchar_t[osss.str().size()];
		out=osss.str();
		MultiByteToWideChar( CP_ACP, 0, out.c_str(), -1, output, osss.str().size());
		SetWindowTextW(MAIN_STATIC,output);
		osss.clear();
		osss.str("");
		SetTimer(hDlg,WM_TIMER,250,NULL);
		MAIN_LISTCONTROL=GetDlgItem(hDlg,IDC_LIST1);
		InsertCountdowninMainColumn(MAIN_LISTCONTROL);
		LV_ITEM item;
		item.mask = LVIF_TEXT;
		item.iItem = 0;
		item.iSubItem = 0;
		item.pszText = (LPTSTR)_T("2022�߿�����ʱ");
		SendDlgItemMessage(hDlg, IDC_LIST1, LVM_INSERTITEM, 0, (LPARAM)&item);
		lvItem.mask = LVIF_TEXT;
		lvItem.iItem = 0;
		lvItem.iSubItem = 1;
		lvItem.pszText = calctime(settime,2);
		ListView_SetItem(MAIN_LISTCONTROL, &lvItem);

			if(FilePathExists(lpFileName))
		{
			int count = 1;
			while(true)
			{
				LPTSTR str = new TCHAR[1024];
				LPTSTR AppName = new TCHAR[1024];
				wsprintf(AppName,_T("Form%d"),count);
				GetPrivateProfileString(AppName,_T("Caption"),NULL,str,1024,lpFileName);
				if(_tcscmp(str,_T(""))==0){
					break;
				}
				LPTSTR hhh = new TCHAR[1024];
				wsprintf(hhh,_T("%s"),str);
				item.iItem = count;
				item.iSubItem = 0;
				item.pszText = hhh;
				ListView_InsertItem(MAIN_LISTCONTROL,&item);
				count++;
			}
			LPTSTR str = new TCHAR[1024];
			GetPrivateProfileString(_T("Settings"),_T("starttime"),NULL,str,1024,lpFileName);
			if(_tcscmp(str,_T(""))!=0){
				
				SYSTEMTIME tm_;
				char times[10]={0};
				strncpy_s(times,(LPCSTR)_bstr_t(str),10);
				int year, month, day;
				sscanf_s(times,"%d-%d-%d", &year, &month, &day);
				tm_.wYear  =year;
				tm_.wMonth   = month;
				tm_.wDay  = day;
				MAIN_DATETIMECONTROL1=GetDlgItem(hDlg,IDC_DATETIMEPICKER1);
				//�Ѿ�����8��ʱ��
				int x = SendMessage(MAIN_DATETIMECONTROL1,DTM_SETSYSTEMTIME,GDT_VALID,(LPARAM)&tm_);
				col2go = true;
			}
			str = new TCHAR[1024];
			GetPrivateProfileString(_T("Settings"),_T("endtime"),NULL,str,1024,lpFileName);
			if(_tcscmp(str,_T(""))!=0){
				SYSTEMTIME tm_;
				char times[10]={0};
				strncpy_s(times,(LPCSTR)_bstr_t(str),10);
				int year, month, day;
				sscanf_s(times,"%d-%d-%d", &year, &month, &day);
				tm_.wYear  = year;
				tm_.wMonth   = month;
				tm_.wDay  = day;
				MAIN_DATETIMECONTROL2=GetDlgItem(hDlg,IDC_DATETIMEPICKER2);
				//�Ѿ�����8��ʱ��
				int x = SendMessage(MAIN_DATETIMECONTROL2,DTM_SETSYSTEMTIME,GDT_VALID,(LPARAM)&tm_);
				col3go = true;
			}

		}

		MAIN_LISTCONTROL2=GetDlgItem(hDlg,IDC_LIST2);
		InsertProgressinMainColumn(MAIN_LISTCONTROL2);
		item.mask = LVIF_TEXT;
		item.iItem = 0;
		item.iSubItem = 0;
		item.pszText = (LPTSTR)_T("��һ���ѹ�ȥ");
		SendDlgItemMessage(hDlg, IDC_LIST2, LVM_INSERTITEM, 0, (LPARAM)&item);
		item.iItem = 0;
		item.iSubItem = 1;
		localtime_s(&starttime,&now);
		starttime.tm_mon=1-1;starttime.tm_mday=1;starttime.tm_hour=0;starttime.tm_min=0;starttime.tm_sec=0;
		localtime_s(&theendtime,&now);
		theendtime.tm_mon=12-1;theendtime.tm_mday=31;theendtime.tm_hour=23;theendtime.tm_min=59;theendtime.tm_sec=59;
		item.pszText = calctimeprogress(starttime,theendtime);
		ListView_SetItem(MAIN_LISTCONTROL2, &item);
		item.mask = LVIF_TEXT;
		item.iItem = 1;
		item.iSubItem = 0;
		item.pszText = (LPTSTR)_T("��һ���ѹ�ȥ");
		SendDlgItemMessage(hDlg, IDC_LIST2, LVM_INSERTITEM, 0, (LPARAM)&item);
		item.iItem = 1;
		item.iSubItem = 1;
		localtime_s(&starttime,&now);
		starttime.tm_hour=0;starttime.tm_min=0;starttime.tm_sec=0;
		localtime_s(&theendtime,&now);
		theendtime.tm_hour=23;theendtime.tm_min=59;theendtime.tm_sec=59;
		item.pszText = calctimeprogress(starttime,theendtime);
		ListView_SetItem(MAIN_LISTCONTROL2, &item);
		item.iItem = 2;
		item.iSubItem = 0;
		item.pszText = (LPTSTR)_T("�����ѹ���");
		SendDlgItemMessage(hDlg, IDC_LIST2, LVM_INSERTITEM, 0, (LPARAM)&item);
		item.iSubItem=1;
		item.pszText=(LPTSTR)_T("�ر�");
		ListView_SetItem(MAIN_LISTCONTROL2, &item);
		item.iItem = 3;
		item.iSubItem = 0;
		item.pszText = (LPTSTR)_T("�����ѹ���");
		SendDlgItemMessage(hDlg, IDC_LIST2, LVM_INSERTITEM, 0, (LPARAM)&item);
		item.iSubItem=1;
		item.pszText=(LPTSTR)_T("�ر�");
		ListView_SetItem(MAIN_LISTCONTROL2, &item);
		return TRUE;
	case WM_COMMAND:
		switch(LOWORD(wParam)){
		case(IDC_BUTTON4):
			MAIN_LISTCONTROL2=GetDlgItem(hDlg,IDC_LIST2);
			MAIN_DATETIMECONTROL1=GetDlgItem(hDlg,IDC_DATETIMEPICKER1);
			SendMessage(MAIN_DATETIMECONTROL1,DTM_GETSYSTEMTIME,0,(LPARAM)&st);
			starttime.tm_year=st.wYear-1900;starttime.tm_mon=st.wMonth-1;starttime.tm_mday=st.wDay;starttime.tm_hour=0;starttime.tm_min=0;starttime.tm_sec=0;
			theendtime.tm_year=2022-1900,theendtime.tm_mon=6-1;theendtime.tm_mday=10;theendtime.tm_hour=23;theendtime.tm_min=59;theendtime.tm_sec=59;
			item.mask = LVIF_TEXT;
			item.iItem = 2;
			item.iSubItem=1;
			item.pszText= calctimeprogress(starttime,theendtime);
			ListView_SetItem(MAIN_LISTCONTROL2, &item);
			col2go=true;
			break;
		case(IDC_BUTTON5):
			MAIN_LISTCONTROL2=GetDlgItem(hDlg,IDC_LIST2);
			MAIN_DATETIMECONTROL2=GetDlgItem(hDlg,IDC_DATETIMEPICKER2);
			SendMessage(MAIN_DATETIMECONTROL2,DTM_GETSYSTEMTIME,0,(LPARAM)&st);
			starttime.tm_year=st.wYear-1900;starttime.tm_mon=st.wMonth-1;starttime.tm_mday=st.wDay;starttime.tm_hour=0;starttime.tm_min=0;starttime.tm_sec=0;
			theendtime.tm_year=2022-1900,theendtime.tm_mon=6-1;theendtime.tm_mday=10;theendtime.tm_hour=23;theendtime.tm_min=59;theendtime.tm_sec=59;
			item.mask = LVIF_TEXT;
			item.iItem = 3;
			item.iSubItem=1;
			item.pszText= calctimeprogress(starttime,theendtime);
			ListView_SetItem(MAIN_LISTCONTROL2, &item);
			col3go=true;
			break;

		}
		break;
	case WM_TIMER:
		MAIN_STATIC = GetDlgItem(hDlg,IDC_STATIC);
		now = time(NULL);
		settime.tm_year=2022-1900;settime.tm_mon=6-1;settime.tm_mday=7;
		endtime=mktime(&settime);
		timeleft=(long)difftime(endtime, now);
		if(timeleft<=0){timeleft=0;}
		leftday = (int)(timeleft / 24 / 60 / 60 +1);
		if(timeleft<=0){leftday=0;}
		lefthour = (int)(timeleft / 60 / 60 % 24);
		leftminute = (int)(timeleft / 60 % 60);
		leftsecond = (int)(timeleft % 60);
		osss<<"��ӭʹ��2022�汾�ĸ߿�����ʱ��2022�߿�����ʱ��"<<leftday-1<<"��"<<pluszero(lefthour)<<":"<<pluszero(leftminute)<<":"<<pluszero(leftsecond);
		output= new wchar_t[osss.str().size()];
		out=osss.str();
		MultiByteToWideChar( CP_ACP, 0, out.c_str(), -1, output, osss.str().size());
			// TODO: �ڴ���������ͼ����...
		SetWindowTextW(MAIN_STATIC,output);
		osss.clear();
		osss.str("");
		MAIN_LISTCONTROL=GetDlgItem(hDlg,IDC_LIST1);
		lvItem.mask = LVIF_TEXT;
		lvItem.iItem = 0;
		lvItem.iSubItem = 1;
		lvItem.pszText = calctime(settime,2);
		ListView_SetItem(MAIN_LISTCONTROL, &lvItem);
		
		nCount =ListView_GetItemCount(COUNTDOWNTABLE_COUNTDOWN);
		for(int i = 1; i < nCount;i++)
		{
			wchar_t pItem[256] = { 0 };
			lvItem.iItem = i;
			lvItem.iSubItem = 1;
			lvItem.cchTextMax = 1024;
			lvItem.pszText = pItem;
			SendMessage(COUNTDOWNTABLE_COUNTDOWN, LVM_GETITEMTEXT, (WPARAM)i, (LPARAM)&lvItem);
			LPTSTR str = lvItem.pszText;
			struct tm tm_ = {0};
			char times[10]={0};
			strncpy_s(times,(LPCSTR)_bstr_t(str),10);
			int year, month, day;
			sscanf_s(times,"%d-%d-%d", &year, &month, &day);
			tm_.tm_year  = year-1900;
			tm_.tm_mon  = month-1;
			tm_.tm_mday = day;
			lvItem.pszText = calctime(tm_,2);
			ListView_SetItem(MAIN_LISTCONTROL,&lvItem);
		}

		MAIN_LISTCONTROL2=GetDlgItem(hDlg,IDC_LIST2);
		item.mask = LVIF_TEXT;
		item.iItem = 0;
		item.iSubItem = 1;
		localtime_s(&starttime,&now);
		starttime.tm_mon=1-1;starttime.tm_mday=1;starttime.tm_hour=0;starttime.tm_min=0;starttime.tm_sec=0;
		localtime_s(&theendtime,&now);
		theendtime.tm_mon=12-1;theendtime.tm_mday=31;theendtime.tm_hour=23;theendtime.tm_min=59;theendtime.tm_sec=59;
		item.pszText = calctimeprogress(starttime,theendtime);
		ListView_SetItem(MAIN_LISTCONTROL2, &item);
		item.iItem = 1;
		item.iSubItem = 1;
		localtime_s(&starttime,&now);
		starttime.tm_hour=0;starttime.tm_min=0;starttime.tm_sec=0;
		localtime_s(&theendtime,&now);
		theendtime.tm_hour=23;theendtime.tm_min=59;theendtime.tm_sec=59;
		item.pszText = calctimeprogress(starttime,theendtime);
		ListView_SetItem(MAIN_LISTCONTROL2, &item);
		if(col2go){
			MAIN_LISTCONTROL2=GetDlgItem(hDlg,IDC_LIST2);
			MAIN_DATETIMECONTROL1=GetDlgItem(hDlg,IDC_DATETIMEPICKER1);
			SendMessage(MAIN_DATETIMECONTROL1,DTM_GETSYSTEMTIME,0,(LPARAM)&st);
			starttime.tm_year=st.wYear-1900;starttime.tm_mon=st.wMonth-1;starttime.tm_mday=st.wDay;starttime.tm_hour=0;starttime.tm_min=0;starttime.tm_sec=0;
			theendtime.tm_year=2022-1900,theendtime.tm_mon=6-1;theendtime.tm_mday=10;theendtime.tm_hour=23;theendtime.tm_min=59;theendtime.tm_sec=59;
			item.mask = LVIF_TEXT;
			item.iItem = 2;
			item.iSubItem=1;
			item.pszText= calctimeprogress(starttime,theendtime);
			ListView_SetItem(MAIN_LISTCONTROL2, &item);}
		if(col3go){
			MAIN_LISTCONTROL2=GetDlgItem(hDlg,IDC_LIST2);
			MAIN_DATETIMECONTROL2=GetDlgItem(hDlg,IDC_DATETIMEPICKER2);
			SendMessage(MAIN_DATETIMECONTROL2,DTM_GETSYSTEMTIME,0,(LPARAM)&st);
			starttime.tm_year=st.wYear-1900;starttime.tm_mon=st.wMonth-1;starttime.tm_mday=st.wDay;starttime.tm_hour=0;starttime.tm_min=0;starttime.tm_sec=0;
			theendtime.tm_year=2022-1900,theendtime.tm_mon=6-1;theendtime.tm_mday=10;theendtime.tm_hour=23;theendtime.tm_min=59;theendtime.tm_sec=59;
			item.mask = LVIF_TEXT;
			item.iItem = 3;
			item.iSubItem=1;
			item.pszText= calctimeprogress(starttime,theendtime);
			ListView_SetItem(MAIN_LISTCONTROL2, &item);
		}
		break;
	}
	return FALSE;
}
BOOL WINAPI PageB(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	BASIC_LISTBOX1 = GetDlgItem(hDlg,IDC_LIST1);
	BASIC_CHECKBOX1 = GetDlgItem(hDlg,IDC_CHECK1);
	HWND BASIC_EDIT1 = GetDlgItem(hDlg,IDC_EDIT1);
	HWND BASIC_EDIT2 = GetDlgItem(hDlg,IDC_EDIT2);
	UNREFERENCED_PARAMETER(lParam);
	string TEXTNAME = "";
	LV_ITEM item;
	int nCount;
	switch (message)
	{
	case WM_INITDIALOG:
		InsertFrameinBasicColumn(BASIC_LISTBOX1);
		item.mask = LVIF_TEXT;
		item.iItem = 0;
		item.iSubItem = 0;
		item.pszText = (LPTSTR)_T("������");
		SendDlgItemMessage(hDlg, IDC_LIST1, LVM_INSERTITEM, 0, (LPARAM)&item);
		item.iSubItem = 1;
		item.pszText = (LPTSTR)_T("2022-06-07");
		ListView_SetItem(GetDlgItem(hDlg,IDC_LIST1), &item);
		item.iSubItem = 2;
		item.pszText = LongToLPTSTR(forms[0].left);
		ListView_SetItem(GetDlgItem(hDlg,IDC_LIST1), &item);
		item.iSubItem = 3;
		item.pszText = LongToLPTSTR(forms[0].top);
		ListView_SetItem(GetDlgItem(hDlg,IDC_LIST1), &item);
		SendDlgItemMessage(hDlg,IDC_EDIT1,EM_SETLIMITTEXT, 5,NULL);
		SendDlgItemMessage(hDlg,IDC_EDIT2,EM_SETLIMITTEXT, 5,NULL);
		if(FilePathExists(lpFileName))
		{
			LPTSTR str = new TCHAR[1024];
			GetPrivateProfileString(_T("FormShow0"),_T("x"),NULL,str,1024,lpFileName);
			LPTSTR hhh = new TCHAR[1024];
			wsprintf(hhh,_T("%s"),str);
			item.iItem = 0;
			item.iSubItem = 2;
			item.pszText = hhh;
			ListView_SetItem(BASIC_LISTBOX1,&item);
			GetPrivateProfileString(_T("FormShow0"),_T("y"),NULL,str,1024,lpFileName);
			hhh = new TCHAR[1024];
			wsprintf(hhh,_T("%s"),str);
			item.iSubItem = 3;
			item.pszText = hhh;
			ListView_SetItem(BASIC_LISTBOX1,&item);
			int count = 1;
			SendMessage(GetDlgItem(hDlg,IDC_CHECK1), BM_SETCHECK, GetWindowLong(hWndMain, GWL_EXSTYLE) & WS_EX_TOPMOST?1:0, 0);
			while(true)
			{
				LPTSTR str = new TCHAR[1024];
				LPTSTR AppName = new TCHAR[1024];
				wsprintf(AppName,_T("Formshow%d"),count);
				GetPrivateProfileString(AppName,_T("Caption"),NULL,str,1024,lpFileName);
				if(_tcscmp(str,_T(""))==0){
					break;
				}
				LPTSTR hhh = new TCHAR[1024];
				wsprintf(hhh,_T("%s"),str);
				item.iItem = count;
				item.iSubItem = 0;
				item.pszText = hhh;
				ListView_InsertItem(BASIC_LISTBOX1,&item);
				GetPrivateProfileString(AppName,_T("Settime"),NULL,str,1024,lpFileName);
				hhh = new TCHAR[1024];
				wsprintf(hhh,_T("%s"),str);
				item.iSubItem = 1;
				item.pszText = hhh;
				ListView_SetItem(BASIC_LISTBOX1,&item);
				GetPrivateProfileString(AppName,_T("x"),NULL,str,1024,lpFileName);
				hhh = new TCHAR[1024];
				wsprintf(hhh,_T("%s"),str);
				item.iSubItem = 2;
				item.pszText = hhh;
				ListView_SetItem(BASIC_LISTBOX1,&item);
				GetPrivateProfileString(AppName,_T("y"),NULL,str,1024,lpFileName);
				hhh = new TCHAR[1024];
				wsprintf(hhh,_T("%s"),str);
				item.iSubItem = 3;
				item.pszText = hhh;
				ListView_SetItem(BASIC_LISTBOX1,&item);
				count++;
			}
		}//�ǵö�ȡ���á�
		return TRUE;
	case WM_COMMAND:
		int iItem;
		LPTSTR szBuffer;//���岢�������뻺�����ռ�
		int textLength;
		
		switch(HIWORD(wParam))
		{
		case EN_CHANGE:
			switch (LOWORD(wParam))
			{
			case IDC_EDIT1:
				nCount=ListView_GetItemCount(BASIC_LISTBOX1);

				for(iItem =0;iItem <nCount;iItem ++)
				{
				  if(ListView_GetItemState(BASIC_LISTBOX1,iItem,LVIS_SELECTED|| LVIS_SELECTED))
				  {break;}
				}
				szBuffer=new TCHAR[1024];//���岢�������뻺�����ռ�
				item.mask = LVIF_TEXT;
				item.iItem = iItem;
				item.iSubItem = 2;
				textLength = GetWindowTextLength(BASIC_EDIT1);
				GetWindowText(BASIC_EDIT1,szBuffer,textLength + 1);
				item.pszText = szBuffer;
				ListView_SetItem(GetDlgItem(hDlg,IDC_LIST1),&item);
				break;
			case IDC_EDIT2:
				nCount=ListView_GetItemCount(BASIC_LISTBOX1);

				for(iItem =0;iItem <nCount;iItem ++)
				{
				  if(ListView_GetItemState(BASIC_LISTBOX1,iItem,LVIS_SELECTED|| LVIS_SELECTED))
				  {break;}
				}
				LPTSTR szBuffer=new TCHAR[1024];//���岢�������뻺�����ռ�
				item.mask = LVIF_TEXT;
				item.iItem = iItem;
				item.iSubItem = 3;
				textLength = GetWindowTextLength(BASIC_EDIT2);
				GetWindowText(BASIC_EDIT2,szBuffer,textLength + 1);
				item.pszText = szBuffer;
				ListView_SetItem(GetDlgItem(hDlg,IDC_LIST1),&item);
				
				break;
			}
		}

		return TRUE;
	case WM_NOTIFY:
		switch (((LPNMHDR)lParam)->code)
        {
        case NM_CLICK:
            if (((LPNMHDR)lParam)->idFrom == IDC_LIST1)
            {
				wchar_t pItem[256] = { 0 };
				LVITEM lvitem;
				lvitem.cchTextMax = 512;
				lvitem.iSubItem = 2;
				lvitem.pszText = pItem;
				nCount=ListView_GetItemCount(BASIC_LISTBOX1);

				for(iItem =0;iItem <nCount;iItem ++)
				{
				  if(ListView_GetItemState(BASIC_LISTBOX1,iItem,LVIS_SELECTED|| LVIS_SELECTED))
				  {break;}
				}
				lvitem.iItem = iItem;
				SendMessage(BASIC_LISTBOX1, LVM_GETITEMTEXT, (WPARAM)iItem, (LPARAM)&lvitem);
				SetWindowText(BASIC_EDIT1,lvitem.pszText);
				lvitem.iSubItem = 3;
				lvitem.pszText = pItem;
				SendMessage(BASIC_LISTBOX1, LVM_GETITEMTEXT, (WPARAM)iItem, (LPARAM)&lvitem);
				SetWindowText(BASIC_EDIT2,lvitem.pszText);
				return TRUE;
			break;
            } 
        // More cases on WM_NOTIFY switch.
        break;
        }
		break;

	}

	return FALSE;
}
BOOL WINAPI PageC(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	UNREFERENCED_PARAMETER(lParam);
	COUNTDOWNTABLE_COUNTDOWN= GetDlgItem(hDlg,IDC_LIST3);
	
	HWND COUNTDOWNTABLE_BUTTON1= GetDlgItem(hDlg,IDC_BUTTON1);
	HWND COUNTDOWNTABLE_BUTTON2= GetDlgItem(hDlg,IDC_BUTTON2);
	
	HWND COUNTDOWNTABLE_EDIT1= GetDlgItem(hDlg,IDC_EDIT3);

	HWND COUNTDOWNTABLE_DATETIMEPICKER1 = GetDlgItem(hDlg,IDC_DATETIMEPICKER1);

	HWND COUNTDOWNTABLE_CHECKBOX1 = GetDlgItem(hDlg,IDC_CHECK1);
	LVITEM item;
	LVITEM lvitem;
	SYSTEMTIME st;
	wchar_t pItem[256] = { 0 };;
		int iItem,nCount;
	switch (message)
	{
	case WM_INITDIALOG:
		COUNTDOWNTABLE_COUNTDOWN = GetDlgItem(hDlg,IDC_LIST3);
		COUNTDOWNTABLE_BUTTON1 = GetDlgItem(hDlg,IDC_BUTTON1);
		COUNTDOWNTABLE_BUTTON2 = GetDlgItem(hDlg,IDC_BUTTON2);
		COUNTDOWNTABLE_EDIT1 = GetDlgItem(hDlg,IDC_EDIT3);
		InsertCountdownFrameinCountdownColumn(COUNTDOWNTABLE_COUNTDOWN);

		item.mask = LVIF_TEXT;
		item.iItem = 0;
		item.iSubItem = 0;
		item.pszText = (LPTSTR)_T("����2022��߿�����");
		SendDlgItemMessage(hDlg, IDC_LIST3, LVM_INSERTITEM, 0, (LPARAM)&item);
		item.iSubItem = 1;
		item.pszText = (LPTSTR)_T("2022-06-07");
		ListView_SetItem(COUNTDOWNTABLE_COUNTDOWN, &item);
		item.iItem = 0;
		item.iSubItem = 2;
		item.pszText = (LPTSTR)_T("��");
		ListView_SetItem(COUNTDOWNTABLE_COUNTDOWN, &item);
		SendDlgItemMessage(hDlg,IDC_EDIT3,EM_SETLIMITTEXT, 22,NULL);
		
		
		if(FilePathExists(lpFileName))
		{
			int count = 1;
			while(true)
			{
				LPTSTR str = new TCHAR[1024];
				LPTSTR AppName = new TCHAR[1024];
				wsprintf(AppName,_T("Form%d"),count);
				GetPrivateProfileString(AppName,_T("Caption"),NULL,str,1024,lpFileName);
				if(_tcscmp(str,_T(""))==0){
					break;
				}
				LPTSTR hhh = new TCHAR[1024];
				wsprintf(hhh,_T("%s"),str);
				item.iItem = count;
				item.iSubItem = 0;
				item.pszText = hhh;
				ListView_InsertItem(COUNTDOWNTABLE_COUNTDOWN,&item);
				GetPrivateProfileString(AppName,_T("Settime"),NULL,str,1024,lpFileName);
				hhh = new TCHAR[1024];
				wsprintf(hhh,_T("%s"),str);
				item.iSubItem = 1;
				item.pszText = hhh;
				ListView_SetItem(COUNTDOWNTABLE_COUNTDOWN,&item);
				GetPrivateProfileString(AppName,_T("ShowForm"),NULL,str,1024,lpFileName);
				hhh = new TCHAR[1024];
				wsprintf(hhh,_T("%s"),str);
				item.iSubItem = 2;
				item.pszText = _tcscmp(hhh,_T("FALSE"))==0?_T("��"):_T("��");
				ListView_SetItem(COUNTDOWNTABLE_COUNTDOWN,&item);
				count++;
			}
		}//�ǵö�ȡ���á�
		return TRUE;

	case WM_COMMAND:
		
		switch(LOWORD(wParam))
		{
		case IDC_BUTTON1:
			
			lvitem.mask = LVIF_TEXT;
			lvitem.cchTextMax = 512;
			lvitem.iSubItem = 0;
			lvitem.pszText = pItem;
			nCount=ListView_GetItemCount(COUNTDOWNTABLE_COUNTDOWN);

				for(iItem =0;iItem <nCount;iItem ++)
				{
				  if(ListView_GetItemState(COUNTDOWNTABLE_COUNTDOWN,iItem,LVIS_SELECTED|| LVIS_SELECTED))
				  {break;}
				}
				if(iItem>0){
				lvitem.iItem = iItem;
				lvitem.iSubItem = 0;

				GetWindowText(COUNTDOWNTABLE_EDIT1,lvitem.pszText,GetWindowTextLength(COUNTDOWNTABLE_EDIT1)+1);
				ListView_SetItem(COUNTDOWNTABLE_COUNTDOWN,&lvitem);
				lvitem.iSubItem = 1;
				SendMessage(COUNTDOWNTABLE_DATETIMEPICKER1,DTM_GETSYSTEMTIME,0,(LPARAM)&st);
				LPTSTR szBuffer=new TCHAR[10];//���岢�������뻺�����ռ�
				wsprintf(szBuffer,_T("%d-%d-%d"),st.wYear,st.wMonth,st.wDay);//Ӧ�ý�int xתΪ�ַ���
				lvitem.pszText = szBuffer;
				ListView_SetItem(COUNTDOWNTABLE_COUNTDOWN,&lvitem);
				lvitem.iSubItem = 2;
				LRESULT result  = SendMessage(COUNTDOWNTABLE_CHECKBOX1, BM_GETCHECK, 0, 0);
				lvitem.pszText = result ==BST_CHECKED?_T("��"):_T("��");
			ListView_SetItem(COUNTDOWNTABLE_COUNTDOWN,&lvitem);
			changed = true;
			}
			return TRUE;
		case IDC_BUTTON3:
				LVITEM lvitem;
				nCount=ListView_GetItemCount(COUNTDOWNTABLE_COUNTDOWN);

				for(iItem =1;iItem <nCount;iItem ++)
				{
				  if(ListView_GetItemState(COUNTDOWNTABLE_COUNTDOWN,iItem,LVIS_SELECTED|| LVIS_SELECTED))
				  {break;}
				}
				lvitem.iItem = iItem>=nCount?-1:iItem;
				ListView_DeleteItem(COUNTDOWNTABLE_COUNTDOWN,iItem);
				EnableWindow(GetDlgItem(hDlg,IDC_BUTTON3),FALSE);
				changed = true;
			break;
		case IDC_BUTTON2:
			lvitem.mask = LVIF_TEXT;
			lvitem.cchTextMax = 512;
			lvitem.iSubItem = 0;
			lvitem.pszText = pItem;
			iItem = SendDlgItemMessage(hDlg, IDC_LIST3, LVM_GETITEMCOUNT, 0, 0);
			lvitem.iItem = iItem;
			lvitem.iSubItem = 0;
			GetWindowText(COUNTDOWNTABLE_EDIT1,lvitem.pszText,GetWindowTextLength(COUNTDOWNTABLE_EDIT1) + 1);
			SendDlgItemMessage(hDlg, IDC_LIST3, LVM_INSERTITEM, iItem, (LPARAM)&lvitem);
			lvitem.iSubItem = 1;
			SendMessage(COUNTDOWNTABLE_DATETIMEPICKER1,DTM_GETSYSTEMTIME,0,(LPARAM)&st);
			LPTSTR szBuffer=new TCHAR[10];//���岢�������뻺�����ռ�
			wsprintf(szBuffer,_T("%d-%d-%d"),st.wYear,st.wMonth,st.wDay);//Ӧ�ý�int xתΪ�ַ���
			lvitem.pszText = szBuffer;
			ListView_SetItem(COUNTDOWNTABLE_COUNTDOWN,&lvitem);
			lvitem.iSubItem = 2;
			LRESULT result  = SendMessage(COUNTDOWNTABLE_CHECKBOX1, BM_GETCHECK, 0, 0);
			lvitem.pszText = result ==BST_CHECKED?_T("��"):_T("��");
			ListView_SetItem(COUNTDOWNTABLE_COUNTDOWN,&lvitem);
			changed = true;
			break;

		}
		break;
		case WM_NOTIFY:
		switch (((LPNMHDR)lParam)->code)
        {
        case NM_CLICK:
            if (((LPNMHDR)lParam)->idFrom == IDC_LIST3)
            {
				
				lvitem.cchTextMax = 512;
				lvitem.iSubItem = 0;
				lvitem.pszText = pItem;
				SendMessage(hDlg,LVM_GETITEMPOSITION, (WPARAM)IDC_LIST3, (LPARAM)&iItem);
				nCount=ListView_GetItemCount(COUNTDOWNTABLE_COUNTDOWN);

				for(iItem =0;iItem <nCount;iItem ++)
				{
				  if(ListView_GetItemState(COUNTDOWNTABLE_COUNTDOWN,iItem,LVIS_SELECTED|| LVIS_SELECTED))
				  {break;}
				}
				SendMessage(COUNTDOWNTABLE_COUNTDOWN, LVM_GETITEMTEXT, (WPARAM)iItem, (LPARAM)&lvitem);
				if(iItem>0)
				{
					SetWindowText(COUNTDOWNTABLE_EDIT1,lvitem.pszText);
					lvitem.iSubItem = 1;
					lvitem.pszText = pItem;
					SendMessage(COUNTDOWNTABLE_COUNTDOWN, LVM_GETITEMTEXT, (WPARAM)iItem, (LPARAM)&lvitem);
					LPTSTR str = lvitem.pszText;
					SYSTEMTIME tm_ = {0};
					char times[10]={0};
					
					strncpy_s(times,(LPCSTR)_bstr_t(str),10);
					int year, month, day;
					sscanf_s(times,"%d-%d-%d", &year, &month, &day);
					tm_.wYear  = year;
					tm_.wMonth   = month;
					tm_.wDay  = day;
					//�Ѿ�����8��ʱ��
					int x = SendMessage(COUNTDOWNTABLE_DATETIMEPICKER1,DTM_SETSYSTEMTIME,GDT_VALID,(LPARAM)&tm_);
					lvitem.iSubItem = 2;
					lvitem.pszText = pItem;
					SendMessage(COUNTDOWNTABLE_COUNTDOWN, LVM_GETITEMTEXT, (WPARAM)iItem, (LPARAM)&lvitem);
					SendMessage(COUNTDOWNTABLE_CHECKBOX1, BM_SETCHECK, _tcscmp(lvitem.pszText,(LPTSTR)_T("��"))?0:1, 0);
					
					EnableWindow(GetDlgItem(hDlg,IDC_BUTTON3),TRUE);
					SetWindowText(COUNTDOWNTABLE_BUTTON1,_T("����"));
				}else{
					SetWindowText(COUNTDOWNTABLE_BUTTON1,_T("�޷�����"));
					
					EnableWindow(GetDlgItem(hDlg,IDC_BUTTON3),FALSE);
				}
				return TRUE;
			break;
            }
		}
        // More cases on WM_NOTIFY switch.
        break;
	}
	return FALSE;
}
/* δ�����ӵ����ݣ�
BOOL WINAPI PageD(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
	case WM_INITDIALOG:
		return TRUE;

	case WM_COMMAND:
		if (LOWORD(wParam) == IDC_CANCELBUTTON || LOWORD(wParam) == IDCANCEL)
		{
			
			EndDialog(hDlg, LOWORD(wParam));
			return TRUE;
		}
		break;
	}
	return FALSE;
}
BOOL WINAPI PageE(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
	UNREFERENCED_PARAMETER(lParam);
	switch (message)
	{
	case WM_INITDIALOG:
		return TRUE;

	case WM_COMMAND:
		if (LOWORD(wParam) == IDC_CANCELBUTTON || LOWORD(wParam) == IDCANCEL)
		{
			
			EndDialog(hDlg, LOWORD(wParam));
			return TRUE;
		}
		break;
	}
	return FALSE;
}
*/
void InsertCountdowninMainColumn(HWND hList)
{
    LV_COLUMN lvc;

    lvc.mask = LVCF_TEXT | LVCF_WIDTH;
    lvc.pszText = L"����";
    lvc.cx = 100;
    SendMessage(hList, LVM_INSERTCOLUMN, 0, (long)&lvc);
    lvc.pszText = L"ʣ��ʱ��";
    lvc.cx = 140;
    SendMessage(hList, LVM_INSERTCOLUMN, 1, (long)&lvc);
}
void InsertProgressinMainColumn(HWND hList)
{
    LV_COLUMN lvc;

    lvc.mask = LVCF_TEXT | LVCF_WIDTH;
    lvc.pszText = L"����";
    lvc.cx = 100;
    SendMessage(hList, LVM_INSERTCOLUMN, 0, (long)&lvc);
    lvc.pszText = L"����";
    lvc.cx = 200;
    SendMessage(hList, LVM_INSERTCOLUMN, 1, (long)&lvc);
}
void InsertFrameinBasicColumn(HWND hList)
{
    LV_COLUMN lvc;

    lvc.mask = LVCF_TEXT | LVCF_WIDTH;
    lvc.pszText = L"���ڱ���";
    lvc.cx = 100;
    SendMessage(hList, LVM_INSERTCOLUMN, 0, (long)&lvc);
	lvc.pszText = L"�趨ʱ��";
    lvc.cx = 100;
    SendMessage(hList, LVM_INSERTCOLUMN, 1, (long)&lvc);
	lvc.pszText = L"���ϽǺ�����";
    lvc.cx = 100;
    SendMessage(hList, LVM_INSERTCOLUMN, 2, (long)&lvc);
	lvc.pszText = L"���Ͻ�������";
    lvc.cx = 100;
    SendMessage(hList, LVM_INSERTCOLUMN, 3, (long)&lvc);
}
void InsertCountdownFrameinCountdownColumn(HWND hList)
{
    LV_COLUMN lvc;

    lvc.mask = LVCF_TEXT | LVCF_WIDTH;
    lvc.pszText = L"���ڱ���";
    lvc.cx = 100;
    SendMessage(hList, LVM_INSERTCOLUMN, 0, (long)&lvc);
	lvc.pszText = L"����ʱ����ʱ��";
    lvc.cx = 100;
    SendMessage(hList, LVM_INSERTCOLUMN, 1, (long)&lvc);
	lvc.pszText = L"�Ƿ���ʾ";
    lvc.cx = 100;
    SendMessage(hList, LVM_INSERTCOLUMN, 2, (long)&lvc);
}
wchar_t* calctime(struct tm settime,int num)//num=1:����,num=2,��ȷ��
{
	wchar_t *output;
	string out;
	ostringstream osss;
	time_t now,endtime;
	long timeleft;
	int leftday,lefthour,leftminute,leftsecond;
		now = time(NULL);
		endtime=mktime(&settime);
		timeleft=(long)difftime(endtime, now);
		if(timeleft<0){timeleft=0;}
		leftday = (int)(timeleft / 24 / 60 / 60 +1);
		switch(num){
		case 1:
			if(timeleft<=0){leftday=0;}
			osss<<leftday<<"��";
			out=osss.str();
			output= new wchar_t[osss.str().size()];
			MultiByteToWideChar( CP_ACP, 0, out.c_str(), -1, output, osss.str().size());
			osss.clear();
			osss.str("");
			return output;
			break;
		case 2:
			lefthour = (int)(timeleft / 60 / 60 % 24);
			leftminute = (int)(timeleft / 60 % 60);
			leftsecond = (int)(timeleft % 60);
			osss<<leftday-1<<"��"<<pluszero(lefthour)<<":"<<pluszero(leftminute)<<":"<<pluszero(leftsecond);
			output= new wchar_t[osss.str().size()];
			out=osss.str();
			MultiByteToWideChar( CP_ACP, 0, out.c_str(), -1, output, osss.str().size());
			osss.clear();
			osss.str("");
		return output;
			break;
		default:
			return L"��������ȷ";
			break;
		}
		
}
wchar_t* calctimeprogress(struct tm starttime,struct tm endtime)
{
	wchar_t *output;
	string out;
	ostringstream osss;
	time_t now,turnendtime,turnstarttime;
	SYSTEMTIME millseconds;
	GetLocalTime(&millseconds);
	double progress;
		now=time(NULL);
		turnendtime=mktime(&endtime);
		turnstarttime=mktime(&starttime);
		progress=(difftime(now,turnstarttime)*1000+(millseconds.wMilliseconds%1000))/(difftime(turnendtime,turnstarttime)*1000)*100;
		if(progress>100){progress=100;}
		if(progress<0){progress=0;}
			osss.precision(15);
			osss.setf(std::ios::fixed);
			osss<<progress<<"��"<<endl;
			out=osss.str();
			output= new wchar_t[osss.str().size()];
			MultiByteToWideChar( CP_ACP, 0, out.c_str(), -1, output, osss.str().size());
			osss.clear();
			osss.str("");
			return output;
		
}
LPTSTR LongToLPTSTR(long number){
	LPTSTR szBuffer=new TCHAR[32];//���岢�������뻺�����ռ�
    wsprintf(szBuffer,_T("%ld"),number);//Ӧ�ý�int xתΪ�ַ���
	return szBuffer;
}