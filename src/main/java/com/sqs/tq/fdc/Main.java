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
package com.sqs.tq.fdc;

import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sqs.tq.fdc.config.CmdLineConfig;
import com.sqs.tq.fdc.config.RunConfig;

public class Main {

    public static void main(String[] args) {

        RunConfig clp = new RunConfig.ConfigurationBuilder(new CmdLineConfig(args)).build();

        if (needAssistance(clp)) {
            assist(clp);
        } else if (canAnalyze(clp)) {
            analyze(clp);
        } else {
            clp.showUsage(System.out);
        }
    }

    private static void assist(RunConfig clp) {
        if (clp.isHelpMode()) {
            clp.showHelp(System.out);
        } else {
            clp.showUsage(System.out);
        }
    }

    private static boolean needAssistance(RunConfig clp) {
        return clp.isHelpMode() || clp.isErrorMode();
    }

    private static boolean canAnalyze(RunConfig clp) {
        return clp.isAnalyseDirMode() || clp.isAnalyseFileMode();
    }

    private static void analyze(RunConfig clp) {
        try (Reporter reporter = clp.reporter()) {
            Main app = new Main();
            if (clp.isAnalyseDirMode()) {
                app.analyzeDirectory(clp.startDir(), clp.fileFilter(), reporter);
            } else {
                app.analyzeSingleFile(clp.file(), reporter);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void analyzeDirectory(Path root, PlainFileFilter ff, Reporter r) throws Exception {
        Collector fc = new DirectoryCollector(ff);
        List<FileData> data = fc.collect(root);

        Analyser a = new FileVariantAnalyser();
        ObjectNode reportData = a.analyse(data);
        reportData.put("name", ff.name());

        r.report(reportData);
    }

    public void analyzeSingleFile(Path file, Reporter r) throws Exception {
        Collector fc = new FileCollector();
        List<FileData> data = fc.collect(file);

        Analyser a = new FileVariantAnalyser();
        ObjectNode reportData = a.analyse(data);
        reportData.put("name", file.getFileName().toString());

        r.report(reportData);
    }
}
