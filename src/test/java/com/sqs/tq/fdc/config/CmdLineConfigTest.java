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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class CmdLineConfigTest {

    private static CmdLineConfig create(String args) {
        return new CmdLineConfig(args.split(" "));
    }

    @Test
    public void shouldAcceptHelpOption() {
        CmdLineConfig cut = create("-h");
        cut.init();

        assertEquals("Help is requested", RunMode.HELP, cut.configMode());
    }

    @Test
    public void shouldSetErrorModeWhenRequiredOptionIsMissed() {
        CmdLineConfig cut = create("-d foo");
        cut.init();

        assertEquals("Should set error mode", RunMode.ERROR, cut.configMode());
    }

    @Test
    public void shouldAcceptTemplateReport() {
        CmdLineConfig cut = create("-f foo -t abc.ftl");
        cut.init();

        assertEquals("Should set analyse_file mode", RunMode.ANALYSE_FILE, cut.configMode());
    }

    @Test
    public void shouldPrintTemplateInfo() throws Exception {
        CmdLineConfig cut = create("-h");
        cut.init();

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(b, true);
        cut.showUsage(ps);
        String result = b.toString(UTF_8.name());
        assertThat(result, containsString("report template"));
    }

}
