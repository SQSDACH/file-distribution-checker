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
import java.io.PrintWriter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CmdLineConfig {
    private static final String PROG_NAME = "FileDistributionChecker";

    private static final String NO_ERROR = "";

    private String error = NO_ERROR;

    private final Options hlpOptions = new Options();
    private final Options runOptions = new Options();
    private final String[] theArgs;

    private CommandLine cl;

    private ConfigMode cfgMode = ConfigMode.UNINITIALIZED;

    public CmdLineConfig(String[] args) {
        hlpOptions.addOption(createOption(CmdLineOption.HELP));

        for (CmdLineOption key : CmdLineOption.values()) {
            runOptions.addOption(createOption(key));
        }

        theArgs = args;
    }

    private Option createOption(CmdLineOption clo) {
        // @formatter:off
        Builder b = Option
                .builder(clo.opt())
                .longOpt(clo.longOpt())
                .desc(clo.description())
                .hasArg(clo.hasArg());
        // @formatter:on
        if (clo.hasArg()) {
            b.hasArg(true);
            b.argName(clo.argName());
        }
        return b.build();
    }

    public void init() {
        cl = parse(hlpOptions, true);
        if (isValid() && hasOption(CmdLineOption.HELP)) {
            cfgMode = ConfigMode.HELP;
            return;
        }

        cl = parse(runOptions, false);
        if (isValid() && hasOption(CmdLineOption.DIR) && hasOption(CmdLineOption.NAME)) {
            cfgMode = ConfigMode.RUN_DIR;
            return;
        }

        if (isValid() && hasOption(CmdLineOption.FILE)) {
            cfgMode = ConfigMode.RUN_FILE;
            return;
        }

        if (isValid() && (theArgs == null || theArgs.length == 0)) {
            cfgMode = ConfigMode.HELP;
            return;
        }

        if (isValid()) {
            cfgMode = ConfigMode.ERROR;
            error = "Missing required option, see usage.";
            return;
        }

        cfgMode = ConfigMode.ERROR;
    }

    private boolean isValid() {
        return cl != null;
    }

    private CommandLine parse(Options os, boolean allowOtherOptions) {
        try {
            CommandLineParser parser = new DefaultParser();
            return parser.parse(os, theArgs, allowOtherOptions);
        } catch (ParseException ex) {
            error = ex.getMessage();
            return null;
        }
    }

    private boolean hasOption(CmdLineOption key) {
        return cl.hasOption(key.longOpt());
    }

    public String dirName() {
        return getOptionValue(CmdLineOption.DIR);
    }

    public String name() {
        return getOptionValue(CmdLineOption.NAME);
    }

    public String fileName() {
        return getOptionValue(CmdLineOption.FILE);
    }

    private String getOptionValue(CmdLineOption key) {
        return cl.getOptionValue(key.longOpt());
    }

    public void showUsage(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        HelpFormatter hf = new HelpFormatter();
        hf.setSyntaxPrefix("Usage:" + hf.getNewLine());
        // @formatter:off
        hf.printHelp(pw, 80,
           "  " + PROG_NAME + printOption(CmdLineOption.HELP) + hf.getNewLine() +
           "  " + PROG_NAME + printOption(CmdLineOption.DIR) + printOption(CmdLineOption.NAME) + hf.getNewLine() +
           "  " + PROG_NAME + printOption(CmdLineOption.FILE) + hf.getNewLine(),
           "Analyze the distribution of a file a directory (recursive) or analyze a single file.",
           runOptions,
           2, 3,
           "Please report issues at https://github.com/sqs-dach/file-distribution-checker", false);
        // @formatter:on
        pw.close();
    }

    private String printOption(CmdLineOption c) {
        StringBuilder sb = new StringBuilder();
        sb.append(" -");
        sb.append(c.opt());
        if (c.hasArg()) {
            sb.append(" <");
            sb.append(c.argName());
            sb.append(">");
        }
        return sb.toString();
    }

    public void showHelp(PrintStream out) {
        out.println(error);
        out.println();

        showUsage(out);
    }

    public ConfigMode configMode() {
        return cfgMode;
    }

}
