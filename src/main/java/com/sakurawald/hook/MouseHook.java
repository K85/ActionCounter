package com.sakurawald.hook;

import com.sakurawald.config.ConfigManager;
import com.sakurawald.timer.ActionPerMinuteTimer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MouseHook extends Hook {

    @Override
    public void install() {
        super.hook(User32.WH_MOUSE_LL, (WinUser.LowLevelKeyboardProc) (nCode, wParam, event) -> {
            if (nCode >= 0) {
                /* Log Action */
                var config = ConfigManager.getSingleton().applicationConfigFile.getConfigDataInstance();
                if (config.ActionHook.logActions && wParam.intValue() != 512
                && wParam.intValue() != 522) {
                    log.debug("wParam {}, vkCode {}, scanCode {}, flags {}, time {}", wParam, event.vkCode, event.scanCode, event.flags, event.time);
                }

                /* Calc APM */
                if (!config.ActionHook.MouseHook.enable)
                    return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, null);

                if (wParam.intValue() == 513 && config.ActionHook.MouseHook.log_mouse_left_down) {
                    ActionPerMinuteTimer.getInstance().increaseAccumulateActions();
                }

                if (wParam.intValue() == 514 && config.ActionHook.MouseHook.log_mouse_left_up) {
                    ActionPerMinuteTimer.getInstance().increaseAccumulateActions();
                }

                if (wParam.intValue() == 516 && config.ActionHook.MouseHook.log_mouse_right_down) {
                    ActionPerMinuteTimer.getInstance().increaseAccumulateActions();
                }

                if (wParam.intValue() == 517 && config.ActionHook.MouseHook.log_mouse_right_up) {
                    ActionPerMinuteTimer.getInstance().increaseAccumulateActions();
                }
            }
            return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, null);
        });
    }

}
