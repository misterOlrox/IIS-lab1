import com.olrox.aot.lib.logic.Attribute
import com.olrox.aot.lib.logic.Rule
import com.olrox.aot.lib.logic.RuleSet
import org.codehaus.jackson.map.ObjectMapper
import org.junit.jupiter.api.Test

class TestNlp {

    public static String text = "[\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"Manchester\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"number of championship cups\",\n" +
            "      \"value\": \"1+\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"years of foundation\",\n" +
            "        \"value\": \"1890+\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"red\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"number of championship cups\",\n" +
            "      \"value\": \"0\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"years of foundation\",\n" +
            "        \"value\": \"1890+\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"blue\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"number of championship cups\",\n" +
            "      \"value\": \"1+\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"white\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"years of foundation\",\n" +
            "        \"value\": \"<1890\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"number of championship cups\",\n" +
            "      \"value\": \"0\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"1+\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"red\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"Manchester\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Manchester United\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"1+\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"red\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"London\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Arsenal\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"1+\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"blue\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"London\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Chelsea\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"1+\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"blue\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"other\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Leicester City\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"1+\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"blue-white\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"other\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Blackburn Rovers\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"1+\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"blue-white\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"Manchester\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Manchester City\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"0\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"white\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"London\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Tottenham Hotspur\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"0\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"other\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"London\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"West Ham United\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"0\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"red\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"Liverpool\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Liverpool\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"0\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"blue\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"Liverpool\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Everton\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"0\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"white\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"other\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Swansea City\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"if\": [\n" +
            "      {\n" +
            "        \"attr\": \"number of championship cups\",\n" +
            "        \"value\": \"0\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"color\",\n" +
            "        \"value\": \"red\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"attr\": \"city\",\n" +
            "        \"value\": \"other\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"then\": {\n" +
            "      \"attr\": \"club\",\n" +
            "      \"value\": \"Stoke City\"\n" +
            "    }\n" +
            "  }\n" +
            "]";

    @Test
    void test() {
        ArrayList allRuleSets = new ObjectMapper().readValue(text, ArrayList.class);
        ArrayList<RuleSet> parsedRuleSets = []
        allRuleSets.forEach((ruleSet) -> {
            RuleSet newRuleSet = new RuleSet()
            def ifs = ruleSet["if"]
            ifs.forEach((_if_) -> {
                Rule rule = new Rule()
                rule.attr = _if_["attr"]
                rule.value = _if_["value"]
                newRuleSet.ifs.add(rule)
            })
            def then = ruleSet["then"]
            Rule thenRule = new Rule()
            thenRule.attr = then["attr"]
            thenRule.value = then["value"]
            newRuleSet.then = thenRule

            parsedRuleSets.add(newRuleSet)
        })
        println(parsedRuleSets)
    }

    @Test
    void test1() {
        String json = "[\n" +
                "  {\n" +
                "    \"name\": \"city\",\n" +
                "    \"values\": [\n" +
                "      \"London\",\n" +
                "      \"Liverpool\",\n" +
                "      \"Manchester\",\n" +
                "      \"other\"\n" +
                "    ]\n" +
                "  },\n" +
                "  {\n" +
                "    \"name\": \"color\",\n" +
                "    \"values\": [\n" +
                "      \"white\",\n" +
                "      \"red\",\n" +
                "      \"blue\",\n" +
                "      \"blue-white\"\n" +
                "    ]\n" +
                "  }" +
                "]"
        ArrayList allAttributes = new ObjectMapper().readValue(json, ArrayList.class);
        ArrayList<Attribute> parsedAttrs = []
        allAttributes.forEach(x-> {
            Attribute attribute = new Attribute()
            attribute.name = x["name"]
            attribute.values = x["values"]
            parsedAttrs.add(attribute)
        })
        println(parsedAttrs)
    }
}
