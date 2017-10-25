/*
 *     Orbit, a versatile image analysis software for biological image-based quantification.
 *     Copyright (C) 2009 - 2017 Idorsia Pharmaceuticals Ltd., Hegenheimermattweg 91, CH-4123 Allschwil, Switzerland.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.actelion.research.orbit.utils;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Simple logger. Needs no config file.<br>
 * Use it like Log4J and set logLevel to the logging level you want.
 */
public class Logger implements ILogListener {

    @SuppressWarnings("unchecked")
    private Class c = null;
    private static final ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
        }
    };

    public static final int LOG_LEVEL_TRACE = 0;
    public static final int LOG_LEVEL_DEBUG = 1;
    public static final int LOG_LEVEL_INFO = 2;
    public static final int LOG_LEVEL_ERROR = 3;
    public static final int LOG_FORMAT_FULL = 0;
    public static final int LOG_FORMAT_SMALL = 1;
    private int logLevel = LOG_LEVEL_TRACE;
    private final static List<WeakReference<ILogListener>> logListeners = new ArrayList<WeakReference<ILogListener>>();
    private final static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


    @SuppressWarnings("unchecked")
    public static Logger getLogger(Class c) {
        return new Logger(c);
    }

    @SuppressWarnings("unchecked")
    private Logger(Class c) {
        this.c = c;
    }

    public static void AddLogListener(ILogListener logListener) {
        lock.writeLock().lock();
        try {
            logListeners.add(new WeakReference<ILogListener>(logListener));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static boolean RemoveLogListener(ILogListener logListener) {
        boolean r = false;
        lock.writeLock().lock();
        try {
            List<Integer> toDel = new ArrayList<Integer>();
            int i = 0;
            for (WeakReference<ILogListener> logListenerRef : logListeners) {
                ILogListener ll = logListenerRef.get();
                if (ll != null) {
                    if (ll.equals(logListener)) {
                        toDel.add(i);
                    }
                }
                i++;
            }

            if (toDel.size() > 0) {
                r = true;
                for (int del : toDel) {
                    logListeners.remove(del);
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
        return r;
    }

    private void logout(int level, String message) {
        lock.readLock().lock();
        try {
            String levelStr = "";
            switch (level) {
                case LOG_LEVEL_ERROR:
                    levelStr = "ERROR";
                    break;
                case LOG_LEVEL_INFO:
                    levelStr = "INFO";
                    break;
                case LOG_LEVEL_DEBUG:
                    levelStr = "DEBUG";
                    break;
                default:
                    levelStr = "TRACE";
            }

            String sFull = (dateStr() + " " + levelStr + " " + c.getName() + ": " + message);
            String sSmall = (dateStr() + " " + levelStr + " " + message);

            for (WeakReference<ILogListener> logListenerRef : logListeners) {
                ILogListener logListener = logListenerRef.get();
                if (logListener != null) {
                    if (logListener.getLogLevel() <= level) {
                        if (logListener.getLogFormat() == LOG_FORMAT_FULL)
                            logListener.outputLog(level, sFull);
                        else logListener.outputLog(level, sSmall);
                    }
                }
            }
        } finally {
            lock.readLock().unlock();
        }

    }

    public void logStackTrace(StackTraceElement[] stackTraceElements) {
        if (logLevel <= LOG_LEVEL_ERROR) {
            StringBuilder sb = new StringBuilder("Stacktrace:\n");
            for (StackTraceElement se : stackTraceElements) {
                sb.append(se.toString() + "\n");
            }
            logout(LOG_LEVEL_ERROR, sb.toString());
        }
    }

    public void error(String s) {
        if (logLevel <= LOG_LEVEL_ERROR)
            logout(LOG_LEVEL_ERROR, s);
    }

    public void info(String s) {
        if (logLevel <= LOG_LEVEL_INFO)
            logout(LOG_LEVEL_INFO, s);
    }

    public void debug(String s) {
        if (logLevel <= LOG_LEVEL_DEBUG)
            logout(LOG_LEVEL_DEBUG, s);
    }

    public void trace(String s) {
        if (logLevel <= LOG_LEVEL_TRACE)
            logout(LOG_LEVEL_TRACE, s);
    }

    private String dateStr() {
        return dateFormat.get().format(new Date(System.currentTimeMillis()));
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat.get();
    }

    public int getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public boolean isTraceEnabled() {
        return logLevel <= LOG_LEVEL_TRACE;
    }

    public boolean isDebugEnabled() {
        return logLevel <= LOG_LEVEL_DEBUG;
    }

    public boolean isInfoEnabled() {
        return logLevel <= LOG_LEVEL_INFO;
    }

    public boolean isErrorEnabled() {
        return logLevel <= LOG_LEVEL_ERROR;
    }

    public void outputLog(int level, String s) {
        System.out.println(s);
    }

    public int getLogFormat() {
        return LOG_FORMAT_FULL;
    }


}