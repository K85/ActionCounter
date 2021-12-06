package com.sakurawald.config;

import com.sakurawald.bean.ColorCondition;

import java.security.Key;
import java.util.ArrayList;

public class ApplicationConfigData implements ConfigData {

	public Base Base = new Base();
	public class Base {
		public String magic = "0xcafebabe";
	}

public 	Drawer Drawer = new Drawer();
	public class Drawer {
		public float xOffset = 25;
		public float yOffset = 25;
		public String name = "Microsoft YaHei";
		public int size = 48;
		public int style = 0;
		public ArrayList<ColorCondition> colorConditions = new ArrayList<>() {
			{
				this.add(new ColorCondition(500, 196, 55, 57));
				this.add(new ColorCondition(400, 103, 73, 80));
				this.add(new ColorCondition(300, 232, 133, 59));
				this.add(new ColorCondition(200, 233, 219, 57));
				this.add(new ColorCondition(100, 40, 167, 69));
				this.add(new ColorCondition(0, 176, 183, 172));
			}
		};
		public int colorR = 255;
		public int colorG = 0;
		public int colorB = 0;

	}

	public ActionHook ActionHook = new ActionHook();
	public class ActionHook {

		public boolean logActions = false;

		public KeyboardHook KeyboardHook = new KeyboardHook();
		public class KeyboardHook{
			public boolean enable = true;
		}

		public MouseHook MouseHook = new MouseHook();
		public class MouseHook {
			public boolean enable = true;
			public boolean log_mouse_left_down = false;
			public boolean log_mouse_left_up = true;
			public boolean log_mouse_right_down = false;
			public boolean log_mouse_right_up = true;
        }

	}

}
