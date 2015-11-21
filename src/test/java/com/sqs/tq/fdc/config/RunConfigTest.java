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

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class RunConfigTest {

    private RunConfig create(String cmdline) {
        return new RunConfig.ConfigurationBuilder(new CmdLineConfig(cmdline != null ? cmdline.split(" ") : null))
                .build();
    }

    @Test
    public void shouldSetHelpModeWhenNoParameterGiven() {
        RunConfig cut = create(null);
        assertTrue("Should recognize as help request", cut.isHelpMode());
    }

    @Test
    public void shouldRecognizeHelpRequest() {
        RunConfig cut = create("-h");
        assertTrue("Should recognize -h as help request", cut.isHelpMode());
    }

    @Test
    public void shouldRecognizeLongHelpRequest() {
        RunConfig cut = create("--help");
        assertTrue("Should recognize --help as help request", cut.isHelpMode());
    }

    @Test
    public void shouldRecognizeNoHelpRequest() {
        RunConfig cut = create("-x");
        assertFalse("-x is not a help request", cut.isHelpMode());
    }

    @Test
    public void shouldRecognizeRequiredOptionsForDirMode() {
        RunConfig cut = create("-d foo -n bar");
        assertTrue("Should recognize given required parameter", cut.isAnalyseDirMode());
    }

    @Test
    public void shouldRecognizeMissingRequiredOptionDir() {
        RunConfig cut = create("--name bar");
        assertFalse("Should recognize missing required parameter 'dir'", cut.isAnalyseDirMode());
    }

    @Test
    public void shouldRecognizeMissingRequiredOptionFile() {
        RunConfig cut = create("-d foo");
        assertFalse("Should recognize missing required parameter 'name'", cut.isAnalyseDirMode());
    }

    @Test
    public void shouldRecognizeRequiredOptionsForFileMode() {
        RunConfig cut = create("-f foo");
        assertTrue("Should recognize given required parameter", cut.isAnalyseFileMode());
    }

    @Test
    public void shouldSetErrorModeWhenRequiredOptionIsMissed() {
        RunConfig cut = create("-d foo");
        assertTrue("Should set error mode", cut.isErrorMode());
    }

    @Test
    public void shouldDeliverDir() {
        RunConfig cut = create("-d foo -n bar");
        assertEquals("foo", cut.startDir().toString());
    }

    @Test
    public void shouldPrintUsage() throws Exception {
        String nl = System.getProperty("line.separator");
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(b, true);

        RunConfig cut = create("");
        cut.showUsage(ps);

        String result = b.toString(java.nio.charset.StandardCharsets.UTF_8.name());
        assertThat(result,
                startsWith(
                // @formatter:off
                "Usage:" + nl +
                "  FileDistributionChecker -h" + nl +
                "  FileDistributionChecker -d <dir> -n <file name>" + nl +
                "  FileDistributionChecker -f <file>"
                // @formatter:on
        ));
    }

    @Test
    public void shouldPrintHelp() throws Exception {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(b, true);

        RunConfig cut = create("");
        cut.showHelp(ps);

        String result = b.toString(java.nio.charset.StandardCharsets.UTF_8.name());
        assertThat(result, startsWith("Missing required option"));
    }
}
