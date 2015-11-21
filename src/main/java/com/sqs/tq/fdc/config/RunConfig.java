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

import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sqs.tq.fdc.PlainFileFilter;

public class RunConfig {

    public static class ConfigurationBuilder {

        private final CmdLineConfig src;

        public ConfigurationBuilder(CmdLineConfig clc) {
            src = clc;
        }

        public RunConfig build() {
            src.init();
            RunConfig cfg = new RunConfig(src);
            return cfg;
        }
    }

    private final CmdLineConfig cfgSource;

    private RunConfig(CmdLineConfig src) {
        cfgSource = src;
    }

    public boolean isAnalyseDirMode() {
        return RunMode.ANALYSE_DIR.equals(cfgSource.configMode());
    }

    public boolean isAnalyseFileMode() {
        return RunMode.ANALYSE_FILE.equals(cfgSource.configMode());
    }

    public boolean isHelpMode() {
        return RunMode.HELP.equals(cfgSource.configMode());
    }

    public boolean isErrorMode() {
        return RunMode.ERROR.equals(cfgSource.configMode());
    }

    public Path startDir() {
        return Paths.get(cfgSource.dirName());
    }

    public PlainFileFilter fileFilter() {
        return new PlainFileFilter(cfgSource.name());
    }

    public void showUsage(PrintStream out) {
        cfgSource.showUsage(out);
    }

    public void showHelp(PrintStream out) {
        cfgSource.showHelp(out);
    }

    public Path file() {
        return Paths.get(cfgSource.fileName());
    }

}
