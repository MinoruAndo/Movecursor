package application;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;

public class SampleController implements Initializable, NativeKeyListener{

	@FXML
	private RadioButton shift;

	@FXML
	private RadioButton control;

	@FXML
	private RadioButton alt;

	private boolean shiftPressed = false;

	private boolean controlPressed = false;

	private boolean ALTPressed = false;

	private int pointX = 0;
	private int pointY = 0;

	private Robot robot = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ログを抑制
		LogManager.getLogManager().reset();
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.OFF);

		GlobalScreen.setEventDispatcher(new JavaFxDispatchService());
		// JNativeHookフックしてなかったらフック
		if (!GlobalScreen.isNativeHookRegistered()) {
			try {
				GlobalScreen.registerNativeHook();
			} catch (NativeHookException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		// ハンドラを登録
		GlobalScreen.addNativeKeyListener(this);

	}

	public void mouseMove(NativeKeyEvent e) {
		PointerInfo pi = MouseInfo.getPointerInfo();
		Point p = pi.getLocation();
		pointX = p.x;
		pointY = p.y;

		try {
			robot = new Robot();
		} catch (AWTException ee) {
			ee.printStackTrace();
		}


		if(e.getKeyCode() == NativeKeyEvent.VC_RIGHT) {
			pointX++;
		}
		else if(e.getKeyCode() == NativeKeyEvent.VC_LEFT) {
			pointX--;
		}
		else if(e.getKeyCode() == NativeKeyEvent.VC_UP) {
			pointY--;
		}
		else if(e.getKeyCode() == NativeKeyEvent.VC_DOWN) {
			pointY++;
		}

		//for(int i=0;i<3000;i++) {
			robot.mouseMove(pointX , pointY);
		//}
		System.out.println("x= " + pointX + " y= "+ pointY );
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent arg0) {

		if (arg0.getKeyCode() == NativeKeyEvent.VC_SHIFT ) {
			shiftPressed = true;
			//System.out.println("ShiftPressed");
		}
		else if (arg0.getKeyCode() == NativeKeyEvent.VC_CONTROL ) {
			controlPressed = true;
			//System.out.println("ControlPressed");
		}
		else if (arg0.getKeyCode() == NativeKeyEvent.VC_ALT ) {
			ALTPressed = true;
			//System.out.println("ALTPressed");
		}

		if(shiftPressed && shift.isSelected()) {
			mouseMove(arg0);
		}
		else if(controlPressed && control.isSelected()) {
			mouseMove(arg0);
		}
		else if(ALTPressed && alt.isSelected()) {
			mouseMove(arg0);
		}
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent arg0) {
		if (arg0.getKeyCode() == NativeKeyEvent.VC_SHIFT ) {
			shiftPressed = false;
			System.out.println("ShiftReleased");
		}
		else if (arg0.getKeyCode() == NativeKeyEvent.VC_CONTROL ) {
			controlPressed = false;
			System.out.println("ControlReleased");
		}
		else if (arg0.getKeyCode() == NativeKeyEvent.VC_ALT ) {
			ALTPressed = false;
			System.out.println("ALTReleased");
		}
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
