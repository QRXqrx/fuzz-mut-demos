//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package edu.nju.ise.roughsets;

import java.util.ArrayList;
import java.util.Iterator;

public class KnowledgeSystem {
    ArrayList<RecordCollection> ksCollections;

    public KnowledgeSystem(ArrayList<RecordCollection> ksCollections) {
        this.ksCollections = ksCollections;
    }

    public RecordCollection getUpSimilarRC(RecordCollection rc) {
        RecordCollection resultRc = null;
        ArrayList<RecordCollection> copyRcs = new ArrayList();
        ArrayList<RecordCollection> deleteRcs = new ArrayList();
        ArrayList<String> targetArray = rc.getRecordNames();
        Iterator var7 = this.ksCollections.iterator();

        RecordCollection recordCollection;
        while(var7.hasNext()) {
            recordCollection = (RecordCollection)var7.next();
            copyRcs.add(recordCollection);
        }

        var7 = copyRcs.iterator();

        ArrayList nameArray;
        while(var7.hasNext()) {
            recordCollection = (RecordCollection)var7.next();
            nameArray = recordCollection.getRecordNames();
            if (this.strIsContained(targetArray, nameArray)) {
                this.removeOverLaped(targetArray, nameArray);
                deleteRcs.add(recordCollection);
                if (resultRc == null) {
                    resultRc = recordCollection;
                } else {
                    resultRc = resultRc.unionCal(recordCollection);
                }

                if (targetArray.size() == 0) {
                    break;
                }
            }
        }

        copyRcs.removeAll(deleteRcs);
        if (targetArray.size() > 0) {
            var7 = copyRcs.iterator();

            while(var7.hasNext()) {
                recordCollection = (RecordCollection)var7.next();
                nameArray = recordCollection.getRecordNames();
                if (this.strHasOverlap(targetArray, nameArray)) {
                    this.removeOverLaped(targetArray, nameArray);
                    if (resultRc == null) {
                        resultRc = recordCollection;
                    } else {
                        resultRc = resultRc.unionCal(recordCollection);
                    }

                    if (targetArray.size() == 0) {
                        break;
                    }
                }
            }
        }

        return resultRc;
    }

    public RecordCollection getDownSimilarRC(RecordCollection rc) {
        RecordCollection resultRc = null;
        ArrayList<String> targetArray = rc.getRecordNames();
        Iterator var5 = this.ksCollections.iterator();

        while(var5.hasNext()) {
            RecordCollection recordCollection = (RecordCollection)var5.next();
            ArrayList<String> nameArray = recordCollection.getRecordNames();
            if (this.strIsContained(targetArray, nameArray)) {
                this.removeOverLaped(targetArray, nameArray);
                if (resultRc == null) {
                    resultRc = recordCollection;
                } else {
                    resultRc = resultRc.unionCal(recordCollection);
                }

                if (targetArray.size() == 0) {
                    break;
                }
            }
        }

        return resultRc;
    }

    public boolean strHasOverlap(ArrayList<String> str1, ArrayList<String> str2) {
        boolean hasOverlap = false;
        Iterator var4 = str1.iterator();

        while(var4.hasNext()) {
            String s1 = (String)var4.next();
            Iterator var6 = str2.iterator();

            while(var6.hasNext()) {
                String s2 = (String)var6.next();
                if (s1.equals(s2)) {
                    hasOverlap = true;
                    break;
                }
            }

            if (hasOverlap) {
                break;
            }
        }

        return hasOverlap;
    }

    public boolean strIsContained(ArrayList<String> str1, ArrayList<String> str2) {
        boolean isContained = false;
        int count = 0;
        Iterator var5 = str2.iterator();

        while(var5.hasNext()) {
            String s = (String)var5.next();
            if (str1.contains(s)) {
                ++count;
            }
        }

        if (count == str2.size()) {
            isContained = true;
        }

        return isContained;
    }

    public void removeOverLaped(ArrayList<String> str1, ArrayList<String> str2) {
        ArrayList<String> deleteStrs = new ArrayList();
        Iterator var4 = str1.iterator();

        while(true) {
            while(var4.hasNext()) {
                String s1 = (String)var4.next();
                Iterator var6 = str2.iterator();

                while(var6.hasNext()) {
                    String s2 = (String)var6.next();
                    if (s1.equals(s2)) {
                        deleteStrs.add(s1);
                        break;
                    }
                }
            }

            str1.removeAll(deleteStrs);
            return;
        }
    }
}
