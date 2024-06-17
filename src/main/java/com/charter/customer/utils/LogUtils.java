package com.charter.customer.utils;

import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

	private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

	private static Supplier<StackFrame> _stackFrame = () -> {
		List<StackFrame> frames = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE)
				.walk(s -> s.skip(2).limit(4).collect(Collectors.toList()));
		return frames != null && !frames.isEmpty() ? frames.get(0) : null;
	};

	public static void errorLog(Throwable e) {
		if (logger.isErrorEnabled()) {
			if (e != null && e.getStackTrace() != null && e.getStackTrace().length > 0) {
				StackTraceElement element = e.getStackTrace()[0];
				logger.error(" - [ERROR] - [" + element.getClassName() + "][" + element.getMethodName() + "]["
						+ element.getLineNumber() + "] :" + "========== [ " + " ] ==========" + System.lineSeparator()
						+ e.getLocalizedMessage());
			}
		}
	}

	public static void infoLog(String description) {
		if (logger.isInfoEnabled()) {
			StackFrame frame = _stackFrame.get();
			if (frame != null) {
				logger.info(" - [INFO] - [" + frame.getClassName() + "][" + frame.getMethodName() + "]["
						+ frame.getLineNumber() + "] :" + "========== [ " + description + " ] ==========");
			}
		}
	}

	public static void startInfoLog() {
		if (logger.isDebugEnabled()) {
			StackFrame frame = _stackFrame.get();
			if (frame != null) {
				logger.debug(" - [DEBUG] - [" + frame.getClassName() + "][" + frame.getMethodName() + "]["
						+ frame.getLineNumber() + "] : [STARTED]");
			}
		}
	}

	public static void endDeInfoLog() {
		if (logger.isDebugEnabled()) {
			StackFrame frame = _stackFrame.get();
			if (frame != null) {
				logger.debug(" - [DEBUG] - [" + frame.getClassName() + "][" + frame.getMethodName() + "]["
						+ frame.getLineNumber() + "] : [COMPLETED]");
			}
		}
	}

}
