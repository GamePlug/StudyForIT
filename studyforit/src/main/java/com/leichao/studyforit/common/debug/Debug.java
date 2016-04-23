package com.leichao.studyforit.common.debug;

import android.util.Log;

import com.leichao.studyforit.common.util.FileUtil;
import com.leichao.studyforit.config.DebugConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * 日志输入打印类
 */
public class Debug {

	// 日志打印开关
	private static final boolean ENABLE = DebugConfig.DEBUG_ENABLE;

	// 日志保存目录
	private static final String SAVE_DIR = DebugConfig.DEBUG_SAVE_DIR;

	// 日志保存天数
	private static final int SAVE_DAYS = DebugConfig.DEBUG_SAVE_DAYS;

	// 日志文件名称
	private static final String SAVE_NAME = DebugConfig.DEBUG_SAVE_NAME;

	public static void w(String tag, Object msg) {
		log(tag, msg.toString(), 'w');
	}

	public static void e(String tag, Object msg) {
		log(tag, msg.toString(), 'e');
	}

	public static void d(String tag, Object msg) {
		log(tag, msg.toString(), 'd');
	}

	public static void i(String tag, Object msg) {
		log(tag, msg.toString(), 'i');
	}

	public static void v(String tag, Object msg) {
		log(tag, msg.toString(), 'v');
	}

	public static void w(String tag, String text) {
		log(tag, text, 'w');
	}

	public static void e(String tag, String text) {
		log(tag, text, 'e');
	}

	public static void d(String tag, String text) {
		log(tag, text, 'd');
	}

	public static void i(String tag, String text) {
		log(tag, text, 'i');
	}

	public static void v(String tag, String text) {
		log(tag, text, 'v');
	}

	/** 
	* 日志打印实现
	*/
	private static void log(String tag, String msg, char level) {
		// log print switcher
		if (ENABLE) {
			if ('e' == level) {
				Log.e(tag, msg);
			} else if ('w' == level) {
				Log.w(tag, msg);
			} else if ('d' == level) {
				Log.d(tag, msg);
			} else if ('i' == level) {
				Log.i(tag, msg);
			} else {
				Log.v(tag, msg);
			}
		}
	}

	/** 
	* 写入日志到文件中
	*/
	public static void writeLog(String tag, String text) {
		if (ENABLE) {
			String nowData = getDateFormat().format(new Date());
			String nowTime = getTimeFormat().format(new Date());
			FileWriter filerWriter = null;
			BufferedWriter bufWriter = null;
			String needWriteMessage = nowData + "    "
					+ nowTime + "    " + tag + "    " + text;
			File dirfile = new File(SAVE_DIR);
			try {
				if (!dirfile.exists()) {
					dirfile.mkdirs();
				}
				File logFile = new File(dirfile, nowData + "_" + SAVE_NAME);
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				filerWriter = new FileWriter(logFile, true);
				bufWriter = new BufferedWriter(filerWriter);
				bufWriter.write(needWriteMessage);
				bufWriter.newLine();
				bufWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(filerWriter != null) filerWriter.close();
					if(bufWriter != null) bufWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void delAllLog() {
		if (ENABLE) {
			File dir = new File(SAVE_DIR);
			if (dir.exists() && dir.isDirectory()) {
				FileUtil.delete(dir);
			}
		}
	}

	public static void delTimeoutLog() {
		if (ENABLE) {
			File dir = new File(SAVE_DIR);
			if (dir.exists() && dir.isDirectory()) {
				for(File file : dir.listFiles()) {
					if (needDel(file.getName())) {
						FileUtil.delete(file);
					}
				}
			}
		}
	}

	private static boolean needDel(String fileName) {
		try {
			SimpleDateFormat dateFormat = getDateFormat();
			String nowTime = dateFormat.format(new Date());
			String fileTime = fileName.split("_")[0];
			//得到指定模范的时间
			Date d1  = dateFormat.parse(nowTime);
			Date d2 = dateFormat.parse(fileTime);
			//比较
			return Math.abs(((d1.getTime() - d2.getTime())/(24*3600*1000))) > SAVE_DAYS;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//命名格式不对的log文件也需删除
		return true;
	}

	private static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
	}

	private static SimpleDateFormat getTimeFormat() {
		return new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
	}
}
