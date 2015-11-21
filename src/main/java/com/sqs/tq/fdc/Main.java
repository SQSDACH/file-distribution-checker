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
        Main app = new Main();

        RunConfig clp = new RunConfig.ConfigurationBuilder(new CmdLineConfig(args)).build();

        try {
            if (clp.isAnalyseDirMode()) {
                app.run(clp.startDir(), clp.fileFilter(), clp.reporter());
            } else if (clp.isAnalyseFileMode()) {
                app.run(clp.file(), clp.reporter());
            } else if (clp.isHelpMode()) {
                clp.showHelp(System.out);
            } else {
                clp.showUsage(System.out);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void run(Path root, PlainFileFilter ff, Reporter r) throws Exception {
        Collector fc = new DirectoryCollector(ff);
        List<FileData> data = fc.collect(root);

        Analyser a = new FileVariantAnalyser();
        ObjectNode reportData = a.analyse(data);
        reportData.put("name", ff.name());

        r.report(reportData);
        r.close();
    }

    public void run(Path file, Reporter r) throws Exception {
        Collector fc = new FileCollector();
        List<FileData> data = fc.collect(file);

        Analyser a = new FileVariantAnalyser();
        ObjectNode reportData = a.analyse(data);
        reportData.put("name", file.getFileName().toString());

        r.report(reportData);
        r.close();
    }
}
