package com.sakurawald.hook;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.util.Calendar;
import java.util.Date;

public abstract class Hook {
    /* Saved Hook ID */
    protected WinUser.HHOOK hhk;

    protected void hook(int hookType, WinUser.HOOKPROC hookProc) {
        WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
        hhk = User32.INSTANCE.SetWindowsHookEx(hookType, hookProc, hMod, 0);
        int result;
        WinUser.MSG msg = new WinUser.MSG();
        while ((result = User32.INSTANCE.GetMessage(msg, null, 0, 0)) != 0) {
            if (result== -1) {
                uninstall();
                break;
            } else {
                User32.INSTANCE.TranslateMessage(msg);
                User32.INSTANCE.DispatchMessage(msg);
            }
        }
    }

    public abstract void install();

    public void uninstall() {
        User32.INSTANCE.UnhookWindowsHookEx(this.hhk);
    }

}
