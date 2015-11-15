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

public class ConfigurationTest {

    private Configuration create(String cmdline) {
        return new Configuration.ConfigurationBuilder(new CmdLineConfig(cmdline != null ? cmdline.split(" ") : null))
                .build();
    }

    @Test
    public void shouldSetHelpModeWhenNoParameterGiven() {
        Configuration cut = create(null);
        assertTrue("Should recognize as help request", cut.isHelpMode());
    }

    @Test
    public void shouldRecognizeHelpRequest() {
        Configuration cut = create("-h");
        assertTrue("Should recognize -h as help request", cut.isHelpMode());
    }

    @Test
    public void shouldRecognizeLongHelpRequest() {
        Configuration cut = create("--help");
        assertTrue("Should recognize --help as help request", cut.isHelpMode());
    }

    @Test
    public void shouldRecognizeNoHelpRequest() {
        Configuration cut = create("-x");
        assertFalse("-x is not a help request", cut.isHelpMode());
    }

    @Test
    public void shouldRecognizeRequiredOptionsForDirMode() {
        Configuration cut = create("-d foo -n bar");
        assertTrue("Should recognize given required parameter", cut.isRunDirMode());
    }

    @Test
    public void shouldRecognizeMissingRequiredOptionDir() {
        Configuration cut = create("--name bar");
        assertFalse("Should recognize missing required parameter 'dir'", cut.isRunDirMode());
    }

    @Test
    public void shouldRecognizeMissingRequiredOptionFile() {
        Configuration cut = create("-d foo");
        assertFalse("Should recognize missing required parameter 'name'", cut.isRunDirMode());
    }

    @Test
    public void shouldRecognizeRequiredOptionsForFileMode() {
        Configuration cut = create("-f foo");
        assertTrue("Should recognize given required parameter", cut.isRunFileMode());
    }

    @Test
    public void shouldSetErrorModeWhenRequiredOptionIsMissed() {
        Configuration cut = create("-d foo");
        assertTrue("Should set error mode", cut.isErrorMode());
    }

    @Test
    public void shouldDeliverDir() {
        Configuration cut = create("-d foo -n bar");
        assertEquals("foo", cut.startDir().toString());
    }

    @Test
    public void shouldPrintUsage() throws Exception {
        String nl = System.getProperty("line.separator");
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(b, true);

        Configuration cut = create("");
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

        Configuration cut = create("");
        cut.showHelp(ps);

        String result = b.toString(java.nio.charset.StandardCharsets.UTF_8.name());
        assertThat(result, startsWith("Missing required option"));
    }
}
