package com.fada.sellsteward.utils;

import android.util.Log;

public class Logger {
	private static final int VERBOSE = 5;
	private static final int DEBUG = 4;
	private static final int INFO = 3;
	private static final int WARN = 2;
	private static final int ERROR = 1;
	private static final int DEFEAT = 6;// 默认的
	private static String TAG = "FADA:";
	// 注:如果不要打印某一类log,那么只要把LOG_LEVEL值调到与其相等就可以了.当LOG_LEVEL为0时全部不打印
	public static int LOG_LEVEL = 7;

	/**
	 * 默认Log,级别最低
	 */
	public static void a() {
		if (LOG_LEVEL > DEFEAT) {

			Log.d(TAG, "--------log打印了---------");
		}
	}

	/**
	 * 级别为5,总共为6个等级,越大表示越不重要,越容易关闭.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, String msg) {
		if (LOG_LEVEL > VERBOSE) {
			if (tag == null)
				tag = TAG;
			Log.v(tag, msg);
		}
	}

	/**
	 * 级别为4,总共为6个等级,越大表示越不重要,越容易关闭.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if (LOG_LEVEL > DEBUG) {
			if (tag == null)
				tag = TAG;
			Log.d(tag, msg);
		}
	}

	/**
	 * 级别为3,总共为6个等级,越大表示越不重要,越容易关闭.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (LOG_LEVEL > INFO) {
			if (tag == null)
				tag = TAG;
			Log.i(tag, msg);
		}
	}

	/**
	 * 级别为2,总共为6个等级,越大表示越不重要,越容易关闭.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void w(String tag, String msg) {
		if (LOG_LEVEL > WARN) {
			if (tag == null)
				tag = TAG;
			Log.w(tag, msg);
		}
	}

	/**
	 * 级别为1,总共为6个等级,越大表示越不重要,越容易关闭.
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		if (LOG_LEVEL > ERROR) {
			if (tag == null)
				tag = TAG;
			Log.e(tag, msg);
		}
	}
}