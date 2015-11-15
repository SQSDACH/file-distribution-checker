/*******************************************************************************
 * The MIT License (MIT)  
 *
 * Copyright (c) 2015 SQS Software Quality Systems AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package com.sqs.tq.fdc.config;

public enum CmdLineOption {
    // @formatter:off
	DIR ("d", "dir" , "dir", "start dir for search"),
	NAME("n", "name", "file name", "file name to search for"),
    FILE("f", "file", "file", "check this file"),
	HELP("h", "help", "show usage message");
	// @formatter:on

    private final String shortOpt;
    private final String longOpt;
    private final String desc;
    private final boolean hasArg;
    private final String argName;

    CmdLineOption(String shortOpt, String longOpt, String desc) {
        this(shortOpt, longOpt, false, "", desc);
    }

    CmdLineOption(String shortOpt, String longOpt, String argName, String desc) {
        this(shortOpt, longOpt, true, argName, desc);
    }

    CmdLineOption(String shortOpt, String longOpt, boolean hasArg, String argName, String desc) {
        this.shortOpt = shortOpt;
        this.longOpt = longOpt;
        this.desc = desc;
        this.hasArg = hasArg;
        this.argName = argName;
    }

    public String opt() {
        return shortOpt;
    }

    public String longOpt() {
        return longOpt;
    }

    public String description() {
        return desc;
    }

    public boolean hasArg() {
        return hasArg;
    }

    public String argName() {
        return argName;
    }

}
