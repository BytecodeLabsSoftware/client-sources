// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.config;

import de.Hero.settings.SettingsManager;
import java.util.ArrayList;
import intent.AquaDev.aqua.modules.Module;
import java.util.Iterator;
import java.net.URLConnection;
import java.util.List;
import intent.AquaDev.aqua.gui.ConfigScreen;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.Aqua;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import intent.AquaDev.aqua.notifications.NotificationManager;
import intent.AquaDev.aqua.notifications.Notification;
import java.io.File;
import intent.AquaDev.aqua.utils.FileUtil;

public class Config
{
    private static final String FILE_ENDING = ".txt";
    private final String fileName;
    private boolean github;
    private boolean defaultSettings;
    
    public Config(final String fileName) {
        this(fileName, false);
    }
    
    public Config(final String fileName, final boolean github) {
        this(fileName, github, false);
    }
    
    public Config(final String fileName, final boolean github, final boolean defaultSettings) {
        this.github = false;
        this.defaultSettings = false;
        this.fileName = fileName;
        this.github = github;
        this.defaultSettings = defaultSettings;
    }
    
    public boolean load() {
        final File confDir = new File(FileUtil.DIRECTORY, this.defaultSettings ? "" : "configs/");
        if (!confDir.exists()) {
            confDir.mkdirs();
            return false;
        }
        final File confFile = new File(confDir, this.fileName.endsWith(".txt") ? this.fileName : (this.fileName + ".txt"));
        final String configName = this.fileName;
        NotificationManager.addNotificationToQueue(new Notification("Config", "loaded §a" + this.fileName, 1000L, Notification.NotificationType.INFO));
        sendChatMessageWithPrefix("Loaded Config : " + this.fileName);
        final List<String> lines = FileUtil.readFile(confFile);
        if (this.github) {
            try {
                final URLConnection urlConnection = new URL("https://raw.githubusercontent.com/LCAMODZ/FantaX-Configs/main/" + (this.fileName.endsWith(".txt") ? this.fileName : (this.fileName + ".txt"))).openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();
                try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        lines.add(text);
                    }
                }
            }
            catch (Exception ex) {}
        }
        else if (!confFile.exists()) {
            return false;
        }
        for (final String string : lines) {
            final String[] args = string.split(" ");
            if (args[0].equalsIgnoreCase("MODULE")) {
                if (args[1].equalsIgnoreCase("TOGGLE")) {
                    final String modName = args[2];
                    final boolean val = Boolean.parseBoolean(args[3]);
                    try {
                        final Module m = Aqua.moduleManager.getModuleByName(modName);
                        if (m.getCategory() == Category.Visual) {
                            continue;
                        }
                        m.setState(val);
                    }
                    catch (Exception ex2) {}
                }
                else {
                    if (!args[1].equalsIgnoreCase("SET")) {
                        continue;
                    }
                    final String settingName = args[2];
                    try {
                        final Setting.Type settingType = Setting.Type.valueOf(args[3]);
                        final String value = args[4];
                        if (!ConfigScreen.loadVisuals && Aqua.setmgr.getSetting(settingName).getModule().getCategory() == Category.Visual) {
                            continue;
                        }
                        switch (settingType) {
                            case BOOLEAN: {
                                Aqua.setmgr.getSetting(settingName).setState(Boolean.parseBoolean(value));
                            }
                            case NUMBER: {
                                Aqua.setmgr.getSetting(settingName).setCurrentNumber(Double.parseDouble(value));
                            }
                            case STRING: {
                                Aqua.setmgr.getSetting(settingName).setCurrentMode(value);
                            }
                            case COLOR: {
                                Aqua.setmgr.getSetting(settingName).color = Integer.parseInt(value);
                                continue;
                            }
                        }
                    }
                    catch (Exception ignored) {}
                }
            }
        }
        return lines != null && !lines.isEmpty();
    }
    
    public boolean loadOnStart() {
        final File confDir = new File(FileUtil.DIRECTORY, this.defaultSettings ? "" : "configs/");
        if (!confDir.exists()) {
            confDir.mkdirs();
            return false;
        }
        final File confFile = new File(confDir, this.fileName.endsWith(".txt") ? this.fileName : (this.fileName + ".txt"));
        final String configName = this.fileName;
        NotificationManager.addNotificationToQueue(new Notification("Config", "loaded §a" + this.fileName, 1000L, Notification.NotificationType.INFO));
        sendChatMessageWithPrefix("Loaded Config : " + this.fileName);
        final List<String> lines = FileUtil.readFile(confFile);
        if (this.github) {
            try {
                final URLConnection urlConnection = new URL("https://raw.githubusercontent.com/LCAMODZ/FantaX-Configs/main/" + (this.fileName.endsWith(".txt") ? this.fileName : (this.fileName + ".txt"))).openConnection();
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();
                try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        lines.add(text);
                    }
                }
            }
            catch (Exception ex) {}
        }
        else if (!confFile.exists()) {
            return false;
        }
        for (final String string : lines) {
            final String[] args = string.split(" ");
            if (args[0].equalsIgnoreCase("MODULE")) {
                if (args[1].equalsIgnoreCase("TOGGLE")) {
                    final String modName = args[2];
                    final boolean val = Boolean.parseBoolean(args[3]);
                    try {
                        Aqua.moduleManager.getModuleByName(modName).setState(val);
                    }
                    catch (Exception ex2) {}
                }
                else {
                    if (!args[1].equalsIgnoreCase("SET")) {
                        continue;
                    }
                    final String settingName = args[2];
                    try {
                        final Setting.Type settingType = Setting.Type.valueOf(args[3]);
                        final String value = args[4];
                        for (final Module module : Aqua.moduleManager.getModules()) {
                            switch (settingType) {
                                case BOOLEAN: {
                                    Aqua.setmgr.getSetting(settingName).setState(Boolean.parseBoolean(value));
                                }
                                case NUMBER: {
                                    Aqua.setmgr.getSetting(settingName).setCurrentNumber(Double.parseDouble(value));
                                }
                                case STRING: {
                                    Aqua.setmgr.getSetting(settingName).setCurrentMode(value);
                                }
                                case COLOR: {
                                    Aqua.setmgr.getSetting(settingName).color = Integer.parseInt(value);
                                    continue;
                                }
                            }
                        }
                    }
                    catch (Exception ignored) {}
                }
            }
        }
        return lines != null && !lines.isEmpty();
    }
    
    public boolean saveCurrent() {
        final File confDir = new File(FileUtil.DIRECTORY, this.defaultSettings ? "" : "configs/");
        if (!confDir.exists()) {
            confDir.mkdirs();
        }
        final File confFile = new File(confDir, this.fileName.endsWith(".txt") ? this.fileName : (this.fileName + ".txt"));
        final String configName = this.fileName;
        sendChatMessageWithPrefix("Saved Config : " + this.fileName);
        NotificationManager.addNotificationToQueue(new Notification("Config", "saved §a" + this.fileName, 1000L, Notification.NotificationType.INFO));
        if (!confFile.exists()) {
            try {
                confFile.createNewFile();
            }
            catch (Exception ex) {}
        }
        final List<String> lines = new ArrayList<String>();
        for (final Module module : Aqua.moduleManager.getModules()) {
            final String TOGGLE = "MODULE TOGGLE " + module.getName() + " " + module.isToggled();
            lines.add(TOGGLE);
            final SettingsManager setmgr = Aqua.setmgr;
            for (final Setting setting : SettingsManager.getSettings()) {
                if (setting.getModule() == module) {
                    final String SET = "MODULE SET " + setting.getName() + " " + setting.getType() + " " + setting.getValue();
                    lines.add(SET);
                }
            }
        }
        FileUtil.writeFile(confFile, lines);
        return true;
    }
    
    public String getConfigName() {
        return this.fileName.replaceAll(".txt", "");
    }
    
    public static void sendChatMessageWithPrefix(final String message) {
    }
}
