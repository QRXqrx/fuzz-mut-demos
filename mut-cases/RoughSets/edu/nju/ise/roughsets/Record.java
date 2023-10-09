//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.roughsets;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Record {
    private final String name;
    private final HashMap<String, String> attrValues;

    public Record(String name, HashMap<String, String> attrValues) {
        this.name = name;
        this.attrValues = attrValues;
    }

    public String getName() {
        return this.name;
    }

    public boolean isContainedAttr(String attr) {
        boolean isContained = this.attrValues.containsValue(attr);

        return isContained;
    }

    public boolean isRecordSame(Record record) {
        boolean isSame = this.name.equals(record.name);

        return isSame;
    }

    public String getRecordDecisionClass() {
        String value = null;
        value = this.attrValues.get(RoughSets.DECISION_ATTR_NAME);
        return value;
    }

    public String getDecisionRule(ArrayList<String> reductAttr) {
        String ruleStr = "";
        String attrName = null;
        String value = null;
        String decisionValue = this.attrValues.get(RoughSets.DECISION_ATTR_NAME);
        ruleStr = ruleStr + "属性";
        Iterator var6 = this.attrValues.entrySet().iterator();

        while(var6.hasNext()) {
            Map.Entry entry = (Map.Entry)var6.next();
            attrName = (String)entry.getKey();
            value = (String)entry.getValue();
            if (!attrName.equals(RoughSets.DECISION_ATTR_NAME) && !reductAttr.contains(attrName) && !value.equals(this.name)) {
                ruleStr = ruleStr + MessageFormat.format("{0}={1},", attrName, value);
            }
        }

        ruleStr = ruleStr + "他的分类为" + decisionValue;
        return ruleStr;
    }
}
