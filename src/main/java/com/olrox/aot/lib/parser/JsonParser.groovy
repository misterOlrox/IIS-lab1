package com.olrox.aot.lib.parser

import com.olrox.aot.lib.logic.Attribute
import com.olrox.aot.lib.logic.Rule
import com.olrox.aot.lib.logic.RuleSet
import org.codehaus.jackson.map.ObjectMapper

class JsonParser {

    static ArrayList<RuleSet> parseRules(String text) {
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

        parsedRuleSets
    }

    static ArrayList<Attribute> parseAttrs(String text) {
        ArrayList allAttributes = new ObjectMapper().readValue(text, ArrayList.class);
        ArrayList<Attribute> parsedAttrs = []
        allAttributes.forEach(x-> {
            Attribute attribute = new Attribute()
            attribute.name = x["name"]
            attribute.values = x["values"]
            parsedAttrs.add(attribute)
        })

        allAttributes
    }
}
