//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.roughsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RecordCollection {
    private final HashMap<String, String> attrValues;
    private final ArrayList<Record> recordList;

    public RecordCollection() {
        this.attrValues = new HashMap();
        this.recordList = new ArrayList();
    }

    public RecordCollection(HashMap<String, String> attrValues, ArrayList<Record> recordList) {
        this.attrValues = attrValues;
        this.recordList = recordList;
    }

    public ArrayList<Record> getRecord() {
        return this.recordList;
    }

    public ArrayList<String> getRecordNames() {
        ArrayList<String> names = new ArrayList();

        for(int i = 0; i < this.recordList.size(); ++i) {
            names.add(this.recordList.get(i).getName());
        }

        return names;
    }

    public boolean isContainedAttrName(String attrName) {
        boolean isContained = this.attrValues.containsKey(attrName);

        return isContained;
    }

    public boolean isCollectionSame(RecordCollection rc) {
        boolean isSame = false;
        Iterator var3 = this.recordList.iterator();

        while(var3.hasNext()) {
            Record r = (Record)var3.next();
            isSame = false;
            Iterator var5 = rc.recordList.iterator();

            while(var5.hasNext()) {
                Record r2 = (Record)var5.next();
                if (r.isRecordSame(r2)) {
                    isSame = true;
                    break;
                }
            }

            if (!isSame) {
                break;
            }
        }

        return isSame;
    }

    public RecordCollection overlapCalculate(RecordCollection rc) {
        RecordCollection resultCollection = null;
        HashMap<String, String> resultAttrValues = new HashMap();
        ArrayList<Record> resultRecords = new ArrayList();
        Iterator var7 = this.recordList.iterator();

        while(true) {
            while(var7.hasNext()) {
                Record record = (Record)var7.next();
                Iterator var9 = rc.recordList.iterator();

                while(var9.hasNext()) {
                    Record record2 = (Record)var9.next();
                    if (record.isRecordSame(record2)) {
                        resultRecords.add(record);
                        break;
                    }
                }
            }

            if (resultRecords.size() == 0) {
                return null;
            }

            var7 = this.attrValues.entrySet().iterator();

            String key;
            String value;
            Map.Entry entry;
            while(var7.hasNext()) {
                entry = (Map.Entry)var7.next();
                key = (String)entry.getKey();
                value = (String)entry.getValue();
                resultAttrValues.put(key, value);
            }

            var7 = rc.attrValues.entrySet().iterator();

            while(var7.hasNext()) {
                entry = (Map.Entry)var7.next();
                key = (String)entry.getKey();
                value = (String)entry.getValue();
                resultAttrValues.put(key, value);
            }

            resultCollection = new RecordCollection(resultAttrValues, resultRecords);
            return resultCollection;
        }
    }

    public RecordCollection unionCal(RecordCollection rc) {
        RecordCollection resultRc = null;
        ArrayList<Record> records = new ArrayList();
        Iterator var4 = this.recordList.iterator();

        Record r2;
        while(var4.hasNext()) {
            r2 = (Record)var4.next();
            records.add(r2);
        }

        var4 = rc.recordList.iterator();

        while(var4.hasNext()) {
            r2 = (Record)var4.next();
            records.add(r2);
        }

        resultRc = new RecordCollection(null, records);
        return resultRc;
    }

    public void printRc() {
        System.out.print("{");
        Iterator var1 = this.getRecord().iterator();

        while(var1.hasNext()) {
            Record r = (Record)var1.next();
            System.out.print(r.getName() + ", ");
        }

        System.out.println("}");
    }
}
