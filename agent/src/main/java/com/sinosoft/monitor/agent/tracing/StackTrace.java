package com.sinosoft.monitor.agent.tracing;

import com.sinosoft.monitor.agent.JavaAgent;
import com.sinosoft.monitor.org.json.simple.JSONArray;
import com.sinosoft.monitor.org.json.simple.JSONStreamAware;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

public class StackTrace
		implements JSONStreamAware {
	private StackTraceItem[] stackTrace;

	public StackTrace(StackTraceElement[] traceElements) {
		if ((traceElements == null) || (traceElements.length == 0)) {
			this.stackTrace = new StackTraceItem[0];
			return;
		}
		int startIdx = 0;
		String agentPkg = JavaAgent.getInstance().getAgentConfig().getApminsightPkg();
		for (int i = 0; i < traceElements.length; i++) {
			if (traceElements[i].getClassName().startsWith(agentPkg)) {
				startIdx = i;
				break;
			}
		}
		for (int i = startIdx; i < traceElements.length; i++) {
			if (!traceElements[i].getClassName().startsWith(agentPkg)) {
				startIdx = i;
				break;
			}
		}
		this.stackTrace = new StackTraceItem[traceElements.length - startIdx];
		for (int i = 0; i < this.stackTrace.length; i++) {
			this.stackTrace[i] = new StackTraceItem(traceElements[(startIdx + i)]);
		}
	}

	public void writeJSONString(Writer out)
			throws IOException {
		JSONArray.writeJSONString(Arrays.asList(this.stackTrace), out);
	}

	class StackTraceItem implements JSONStreamAware {
		private final String declaringClass;
		private final String methodName;
		private final String fileName;
		private final int lineNumber;

		public StackTraceItem(StackTraceElement ste) {
			this(ste.getClassName(), ste.getMethodName(), ste.getFileName(), ste.getLineNumber());
		}

		public StackTraceItem(String declaringClass, String methodName, String fileName, int lineNumber) {
			this.declaringClass = declaringClass;
			this.methodName = methodName;
			this.fileName = fileName;
			this.lineNumber = lineNumber;
		}

		public void writeJSONString(Writer out)
				throws IOException {
			JSONArray.writeJSONString(Arrays.asList(new Object[]{this.declaringClass, this.methodName, this.fileName, Integer.valueOf(this.lineNumber)}), out);
		}

		public int hashCode() {
			int result = 31 * this.declaringClass.hashCode() + this.methodName.hashCode();
			result = 31 * result + (this.fileName == null ? 0 : this.fileName.hashCode());
			result = 31 * result + this.lineNumber;
			return result;
		}
	}
}
