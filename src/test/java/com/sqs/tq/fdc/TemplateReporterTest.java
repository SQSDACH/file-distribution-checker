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

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.Test;

public class TemplateReporterTest {

    @Test
    public void shouldOutputFileName() throws Exception {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(b, true);

        TemplateReporter r = new TemplateReporter(ps);
        r.template(new File(TemplateReporterTest.class.getResource("/templates").toURI()).toPath(), "plain.ftl");
        r.report(Fixtures.ROOT_EXAMPLE);
        r.close();

        String result = b.toString(java.nio.charset.StandardCharsets.UTF_8.name());

        assertThat(result, containsString("File variants of '???'"));
    }

    @Test
    public void shouldOutputFileGroupHeader() throws Exception {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(b, true);

        TemplateReporter r = new TemplateReporter(ps);
        r.template(new File(TemplateReporterTest.class.getResource("/templates").toURI()).toPath(), "plain.ftl");
        r.report(Fixtures.ROOT_EXAMPLE);
        r.close();

        String result = b.toString(java.nio.charset.StandardCharsets.UTF_8.name());

        assertThat(result, containsString("1) #2 times (md5: 1)"));
    }

    @Test
    public void shouldOutputFoundFile() throws Exception {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(b, true);

        TemplateReporter r = new TemplateReporter(ps);
        r.template(new File(TemplateReporterTest.class.getResource("/templates").toURI()).toPath(), "plain.ftl");
        r.report(Fixtures.ROOT_EXAMPLE);
        r.close();

        String result = b.toString(java.nio.charset.StandardCharsets.UTF_8.name());

        assertThat(result, containsString("a/b/data.txt"));
    }

}
