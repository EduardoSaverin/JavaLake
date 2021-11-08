package disjointset;

import disjointset.exceptions.DuplicateElementException;
import disjointset.exceptions.ElementNotFoundException;

import java.util.*;

public class DisJointSet {
    private HashMap<Integer, Set<Integer>> elementIndexMap;

    public DisJointSet() {
        this.elementIndexMap = new HashMap<>();
    }

    public boolean insert(Integer number) throws DuplicateElementException {
        if (elementIndexMap.containsKey(number)) {
            throw new DuplicateElementException("Duplicate Element/Null Element " + number.toString());
        }
        elementIndexMap.put(number, new HashSet<Integer>(1) {{
            add(number);
        }});
        return Boolean.TRUE;
    }
    
    public Set<? extends Object> findPartition(Integer number) throws ElementNotFoundException {
        if (Objects.isNull(number) || !elementIndexMap.containsKey(number)) {
            throw new ElementNotFoundException();
        }
        return elementIndexMap.get(number);
    }

    public boolean isDisJoint(Integer firstNumber, Integer secondNumber) throws ElementNotFoundException {
        Set<Integer> p1 = (Set<Integer>) this.findPartition(firstNumber);
        Set<Integer> p2 = (Set<Integer>) this.findPartition(secondNumber);
        return p1 != p2;
    }

    public boolean merge(Integer firstNumber, Integer secondNumber) throws ElementNotFoundException {
        Set<Integer> p1 = (Set<Integer>) this.findPartition(firstNumber);
        Set<Integer> p2 = (Set<Integer>) this.findPartition(secondNumber);
        if (p1 == p2) {
            return Boolean.FALSE;
        }
        Set<Integer> pointerChangingSet = p1.size() < p2.size() ? p1 : p2;
        Set<Integer> finalSet = p1.size() < p2.size() ? p2 : p1;
        for (Integer number : pointerChangingSet) {
            finalSet.add(number);
            elementIndexMap.put(number, finalSet);
        }
        return Boolean.TRUE;
    }
}
