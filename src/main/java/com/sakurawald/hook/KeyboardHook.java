package com.sakurawald.hook;

import com.sakurawald.config.ConfigManager;
import com.sakurawald.timer.ActionPerMinuteTimer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyboardHook extends Hook {

    @Override
    public void install() {
        super.hook(User32.WH_KEYBOARD_LL, (WinUser.LowLevelKeyboardProc) (nCode, wParam, event) -> {
            if (nCode >= 0) {
                /* Log Action */
                var config = ConfigManager.getSingleton().applicationConfigFile.getConfigDataInstance();
                if (config.ActionHook.logActions) {
                    log.debug("vkCode {}, scanCode {}, flags {}, time {}", event.vkCode, event.scanCode, event.flags, event.time);
                }

                /* Calc APM */
                if (!config.ActionHook.KeyboardHook.enable)
                    return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, null);

                // Only if the key is release
                if (event.flags == 128)
                    ActionPerMinuteTimer.getInstance().increaseAccumulateActions();
            }

            return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, null);
        });

    }

}
