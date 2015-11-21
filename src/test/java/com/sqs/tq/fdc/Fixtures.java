package com.sqs.tq.fdc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sqs.tq.fdc.helper.JSONHelper;

public class Fixtures {
    // @formatter:off
    public static final JsonNode expected = build(""
        + "{"
        + "  'name': '???',"
        + "  'groups': ["
        + "    {"
        + "      'hash': '1',"
        + "      'files': ["
        + "        'a/data.txt',"
        + "        'a/b/data.txt'"
        + "      ]"
        + "    },"
        + "    {"
        + "      'hash': '2',"
        + "      'files': ["
        + "        'a/c/data.txt'"
        + "      ]"
        + "    }"
        + "  ]"
        + "}"
    );
    // @formatter:on

    private static JsonNode build(String s) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(JSONHelper.sq2dq(s));
        } catch (Exception e) {
            throw new RuntimeException("invalid json? " + e.getMessage(), e);
        }
    }
}
