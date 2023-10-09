package edu.nju.mutest.visitor.collector.cond;

/**
 * A functional interface. To help collector determine whether a
 * node should be collected.
 *
 * @author Adian
 */
public interface CollectionCond<T> {
    boolean willCollect(T target);
}
