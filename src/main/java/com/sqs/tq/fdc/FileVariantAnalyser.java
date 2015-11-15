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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class FileVariantAnalyser implements Analyser {

    private class GroupData {
        final String hash;
        final int count;
        final List<FileData> data;

        public GroupData(String hash, List<FileData> data) {
            this.hash = hash;
            this.count = data.size();
            this.data = data;
        }
    }

    @Override
    public ObjectNode analyse(List<FileData> data) {
        List<GroupData> contentGroups = distinctContentGroups(data);

        sortGroups(contentGroups);

        return convertToJson(contentGroups);
    }

    private void sortGroups(List<GroupData> groups) {
        Collections.sort(groups, new Comparator<GroupData>() {
            @Override
            public int compare(GroupData o1, GroupData o2) {
                if (o1.count < o2.count) {
                    return 1;
                }
                if (o1.count > o2.count) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private List<GroupData> distinctContentGroups(List<FileData> allData) {
        Map<String, List<FileData>> hash2list = new HashMap<>();
        for (FileData fd : allData) {
            if (!hash2list.containsKey(fd.hash)) {
                hash2list.put(fd.hash, new ArrayList<FileData>());
            }
            hash2list.get(fd.hash).add(fd);
        }

        List<GroupData> groups = new ArrayList<GroupData>();
        for (Entry<String, List<FileData>> entry : hash2list.entrySet()) {
            groups.add(new GroupData(entry.getKey(), entry.getValue()));
        }

        return groups;
    }

    private ObjectNode convertToJson(List<GroupData> hashSortedByCount) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        ArrayNode groupsNode = result.arrayNode();

        result.put("name", "???");
        result.set("groups", groupsNode);

        for (GroupData gd : hashSortedByCount) {
            ObjectNode jgd = groupsNode.objectNode();
            ArrayNode filesNode = groupsNode.arrayNode();

            groupsNode.add(jgd);
            jgd.put("hash", gd.hash);
            jgd.set("files", filesNode);

            for (FileData fd : gd.data) {
                filesNode.add(fd.fqn);
            }
        }
        return result;
    }

}
