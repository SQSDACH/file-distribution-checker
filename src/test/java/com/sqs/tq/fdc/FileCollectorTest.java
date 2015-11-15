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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FileCollectorTest {

    private Path fileInRoot;
    private Path fileInSub;
    private FileCollector fc;

    @Before
    public void setup() throws Exception {
        fileInRoot = Paths.get(getClass().getResource("/rootdir/data.txt").toURI());
        fileInSub = Paths.get(getClass().getResource("/rootdir/sub/data.txt").toURI());

        fc = new FileCollector();
    }

    @Test
    public void shouldReadFileAndReturnItsData() {
        List<FileData> data = fc.collect(fileInRoot);
        assertThat(data, hasSize(1));
        assertThat(data.get(0).name, equalTo("data.txt"));
    }

    @Test
    public void shouldCalculateSameHashForFilesWithSameContent() {
        FileData fdRoot = fc.collect(fileInRoot).get(0);
        FileData fdSub = fc.collect(fileInSub).get(0);

        assertThat(fdRoot.hash, equalTo(fdSub.hash));
    }
}
