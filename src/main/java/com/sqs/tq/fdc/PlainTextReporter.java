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

import java.io.PrintStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PlainTextReporter implements Reporter {

    private final PrintStream out;

    public PlainTextReporter(PrintStream out) {
        this.out = out;
    }

    @Override
    public void close() throws Exception {
    }

    @Override
    public void report(ObjectNode reportData) {
        out.println();
        out.println(String.format("File variants of '%s'", reportData.get("name").textValue()));
        out.println("---------------------------------------------------");

        int idx = 1;
        for (JsonNode group : (ArrayNode) reportData.get("groups")) {
            String hash = group.get("hash").textValue();
            ArrayNode files = (ArrayNode) group.get("files");
            out.println(String.format("%d) #%d times (md5: %s)", idx, files.size(), hash));
            for (JsonNode fd : files) {
                out.println(String.format("  %s", fd.textValue()));
            }
            ++idx;
        }

    }

}
