//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.roughsets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RoughSets {
    public static String DECISION_ATTR_NAME;
    private String filePath;
    private String[] attrNames;
    private ArrayList<String[]> totalDatas = new ArrayList();
    private ArrayList<Record> totalRecords = new ArrayList();
    private HashMap<String, ArrayList<String>> conditionAttr = new HashMap();
    private ArrayList<RecordCollection> collectionList;

    public RoughSets(ArrayList<String[]> dataArray) {
        this.attrNames = (String[])dataArray.get(0);
        DECISION_ATTR_NAME = this.attrNames[this.attrNames.length - 1];

        for(int j = 0; j < dataArray.size(); ++j) {
            String[] array = (String[])dataArray.get(j);
            this.totalDatas.add(array);
            if (j != 0) {
                HashMap<String, String> attrMap = new HashMap();

                for(int i = 0; i < this.attrNames.length; ++i) {
                    attrMap.put(this.attrNames[i], array[i]);
                    if (i > 0 && i < this.attrNames.length - 1) {
                        ArrayList attrList;
                        if (this.conditionAttr.containsKey(this.attrNames[i])) {
                            attrList = (ArrayList)this.conditionAttr.get(this.attrNames[i]);
                            if (!attrList.contains(array[i])) {
                                attrList.add(array[i]);
                            }
                        } else {
                            attrList = new ArrayList();
                            attrList.add(array[i]);
                        }

                        this.conditionAttr.put(this.attrNames[i], attrList);
                    }
                }

                Record tempRecord = new Record(array[0], attrMap);
                this.totalRecords.add(tempRecord);
            }
        }

    }

    private void recordSpiltToCollection() {
        this.collectionList = new ArrayList();
        Iterator var6 = this.conditionAttr.entrySet().iterator();

        while(var6.hasNext()) {
            Map.Entry entry = (Map.Entry)var6.next();
            String attrName = (String)entry.getKey();
            ArrayList<String> attrList = (ArrayList)entry.getValue();
            Iterator var8 = attrList.iterator();

            while(var8.hasNext()) {
                String s = (String)var8.next();
                ArrayList<Record> recordList = new ArrayList();
                Iterator var10 = this.totalRecords.iterator();

                while(var10.hasNext()) {
                    Record record = (Record)var10.next();
                    if (record.isContainedAttr(s)) {
                        recordList.add(record);
                    }
                }

                HashMap<String, String> collectionAttrValues = new HashMap();
                collectionAttrValues.put(attrName, s);
                RecordCollection collection = new RecordCollection(collectionAttrValues, recordList);
                this.collectionList.add(collection);
            }
        }

    }

    private HashMap<String, ArrayList<RecordCollection>> constructCollectionMap(ArrayList<String> reductAttr) {
        HashMap<String, ArrayList<RecordCollection>> collectionMap = new HashMap();

        for(int i = 1; i < this.attrNames.length - 1; ++i) {
            String currentAtttrName = this.attrNames[i];
            if (reductAttr == null || !reductAttr.contains(currentAtttrName)) {
                ArrayList<RecordCollection> cList = new ArrayList();
                Iterator var6 = this.collectionList.iterator();

                while(var6.hasNext()) {
                    RecordCollection c = (RecordCollection)var6.next();
                    if (c.isContainedAttrName(currentAtttrName)) {
                        cList.add(c);
                    }
                }

                collectionMap.put(currentAtttrName, cList);
            }
        }

        return collectionMap;
    }

    private ArrayList<RecordCollection> computeKnowledgeSystem(HashMap<String, ArrayList<RecordCollection>> collectionMap) {
        String attrName = null;
        ArrayList<RecordCollection> cList = null;
        ArrayList<RecordCollection> ksCollections = new ArrayList();
        Iterator var5 = collectionMap.entrySet().iterator();
        if (var5.hasNext()) {
            Map.Entry entry = (Map.Entry)var5.next();
            attrName = (String)entry.getKey();
            cList = (ArrayList)entry.getValue();
        }

        collectionMap.remove(attrName);
        var5 = cList.iterator();

        while(var5.hasNext()) {
            RecordCollection rc = (RecordCollection)var5.next();
            this.recurrenceComputeKS(ksCollections, collectionMap, rc);
        }

        return ksCollections;
    }

    private void recurrenceComputeKS(ArrayList<RecordCollection> ksCollections, HashMap<String, ArrayList<RecordCollection>> map, RecordCollection preCollection) {
        String attrName = null;
        ArrayList<RecordCollection> cList = null;
        HashMap<String, ArrayList<RecordCollection>> mapCopy = new HashMap();
        if (map.size() == 0) {
            ksCollections.add(preCollection);
        } else {
            Iterator var8 = map.entrySet().iterator();

            Map.Entry entry;
            while(var8.hasNext()) {
                entry = (Map.Entry)var8.next();
                cList = (ArrayList)entry.getValue();
                mapCopy.put((String)entry.getKey(), cList);
            }

            var8 = map.entrySet().iterator();
            if (var8.hasNext()) {
                entry = (Map.Entry)var8.next();
                attrName = (String)entry.getKey();
                cList = (ArrayList)entry.getValue();
            }

            mapCopy.remove(attrName);
            var8 = cList.iterator();

            while(var8.hasNext()) {
                RecordCollection rc = (RecordCollection)var8.next();
                RecordCollection tempCollection = preCollection.overlapCalculate(rc);
                if (tempCollection != null) {
                    if (mapCopy.size() == 0) {
                        ksCollections.add(tempCollection);
                    } else {
                        this.recurrenceComputeKS(ksCollections, mapCopy, tempCollection);
                    }
                }
            }

        }
    }

    public void findingReduct() {
        ArrayList<String> reductAttr = null;
        RecordCollection[] sameClassRcs = this.selectTheSameClassRC();
        this.recordSpiltToCollection();
        HashMap<String, ArrayList<RecordCollection>> collectionMap = this.constructCollectionMap(reductAttr);
        ArrayList<RecordCollection> ksCollections = this.computeKnowledgeSystem(collectionMap);
        KnowledgeSystem ks = new KnowledgeSystem(ksCollections);
        System.out.println("原始集合分类的上下近似集合");
        ks.getDownSimilarRC(sameClassRcs[0]).printRc();
        ks.getUpSimilarRC(sameClassRcs[0]).printRc();
        ks.getDownSimilarRC(sameClassRcs[1]).printRc();
        ks.getUpSimilarRC(sameClassRcs[1]).printRc();
        ArrayList<String> attrNameList = new ArrayList();

        for(int i = 1; i < this.attrNames.length - 1; ++i) {
            attrNameList.add(this.attrNames[i]);
        }

        ArrayList<ArrayList<String>> canReductAttrs = new ArrayList();
        new ArrayList();
        Iterator var9 = attrNameList.iterator();

        while(var9.hasNext()) {
            String s = (String)var9.next();
            ArrayList<String> remainAttr = (ArrayList)attrNameList.clone();
            remainAttr.remove(s);
            reductAttr = new ArrayList();
            reductAttr.add(s);
            this.recurrenceFindingReduct(canReductAttrs, reductAttr, remainAttr, sameClassRcs);
        }

        this.printRules(canReductAttrs);
    }

    private void recurrenceFindingReduct(ArrayList<ArrayList<String>> resultAttr, ArrayList<String> reductAttr, ArrayList<String> remainAttr, RecordCollection[] sameClassRc) {
        HashMap<String, ArrayList<RecordCollection>> collectionMap = this.constructCollectionMap(reductAttr);
        ArrayList<RecordCollection> ksCollections = this.computeKnowledgeSystem(collectionMap);
        KnowledgeSystem ks = new KnowledgeSystem(ksCollections);
        RecordCollection downRc1 = ks.getDownSimilarRC(sameClassRc[0]);
        RecordCollection upRc1 = ks.getUpSimilarRC(sameClassRc[0]);
        RecordCollection downRc2 = ks.getDownSimilarRC(sameClassRc[1]);
        RecordCollection upRc2 = ks.getUpSimilarRC(sameClassRc[1]);
        if (upRc1.isCollectionSame(sameClassRc[0]) && downRc1.isCollectionSame(sameClassRc[0])) {
            if (upRc2.isCollectionSame(sameClassRc[1]) && downRc2.isCollectionSame(sameClassRc[1])) {
                resultAttr.add(reductAttr);
                if (remainAttr.size() != 1) {
                    Iterator var14 = remainAttr.iterator();

                    while(var14.hasNext()) {
                        String s = (String)var14.next();
                        ArrayList<String> copyRemainAttr = (ArrayList)remainAttr.clone();
                        ArrayList<String> copyReductAttr = (ArrayList)reductAttr.clone();
                        copyRemainAttr.remove(s);
                        copyReductAttr.add(s);
                        this.recurrenceFindingReduct(resultAttr, copyReductAttr, copyRemainAttr, sameClassRc);
                    }

                }
            }
        }
    }

    private RecordCollection[] selectTheSameClassRC() {
        RecordCollection[] resultRc = new RecordCollection[]{new RecordCollection(), new RecordCollection()};
        String attrValue = this.totalRecords.get(0).getRecordDecisionClass();
        Iterator var3 = this.totalRecords.iterator();

        while(var3.hasNext()) {
            Record r = (Record)var3.next();
            if (attrValue.equals(r.getRecordDecisionClass())) {
                resultRc[0].getRecord().add(r);
            } else {
                resultRc[1].getRecord().add(r);
            }
        }

        return resultRc;
    }

    public void printRules(ArrayList<ArrayList<String>> reductAttrArray) {
        Iterator var4 = reductAttrArray.iterator();

        while(var4.hasNext()) {
            ArrayList<String> ra = (ArrayList)var4.next();
            ArrayList<String> rulesArray = new ArrayList();
            System.out.print("约简的属性：");
            Iterator var6 = ra.iterator();

            while(var6.hasNext()) {
                String s = (String)var6.next();
                System.out.print(s + ",");
            }

            System.out.println();
            var6 = this.totalRecords.iterator();

            while(var6.hasNext()) {
                Record r = (Record)var6.next();
                String rule = r.getDecisionRule(ra);
                if (!rulesArray.contains(rule)) {
                    rulesArray.add(rule);
                    System.out.println(rule);
                }
            }

            System.out.println();
        }

    }

    public void printRecordCollectionList(ArrayList<RecordCollection> rcList) {
        Iterator var2 = rcList.iterator();

        while(var2.hasNext()) {
            RecordCollection rc = (RecordCollection)var2.next();
            System.out.print("{");
            Iterator var4 = rc.getRecord().iterator();

            while(var4.hasNext()) {
                Record r = (Record)var4.next();
                System.out.print(r.getName() + ", ");
            }

            System.out.println("}");
        }

    }
}
