package core.basesyntax;

import java.util.NoSuchElementException;

public class ArrayList<T> implements List<T> {
    private static final int DEFAULT_SIZE = 10;
    private int localSize;
    private Object[] values;

    public ArrayList() {
        localSize = 0;
        values = new Object[DEFAULT_SIZE];
    }

    public ArrayList(int newSize) {
        localSize = newSize;
        values = new Object[localSize];
    }

    @Override
    public void add(T value) {
        if (values.length == localSize) {
            values = grow(0, 0, localSize);
        }
        values[localSize++] = value;
    }

    @Override
    public void add(T value, int index) {
        if (index == localSize) {
            add(value);
        } else if (index >= 0 && index < localSize) {
            values = insertAdd(index);
            values[index] = value;
            localSize++;

        } else {
            throw new ArrayListIndexOutOfBoundsException("Error add"
                    + System.lineSeparator()
                    + "Size = " + localSize
                    + " but index = " + index);
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (values.length < localSize + list.size()) {
            values = grow(list.size());
        }
        for (int i = localSize, j = 0; j < list.size(); i++, j++) {
            values[i] = list.get(j);
        }

        localSize += list.size();
    }

    @Override
    public T get(int index) {
        if (index < localSize && index >= 0) {
            return (T) values[index];
        } else {
            throw new ArrayListIndexOutOfBoundsException("Error get"
                    + System.lineSeparator()
                    + "Size = " + localSize
                    + " but index = " + index);
        }
    }

    @Override
    public void set(T value, int index) {
        if (index < localSize && index >= 0) {
            values[index] = value;
        } else {
            throw new ArrayListIndexOutOfBoundsException("Error set"
                    + System.lineSeparator()
                    + "Size = " + localSize
                    + " but index = " + index);
        }
    }

    @Override
    public T remove(int index) {
        if (index < localSize && index >= 0) {
            localSize--;
            T obj = (T) values[index];
            Object[] newValues = new Object[values.length];
            System.arraycopy(values, 0, newValues, 0, index);
            System.arraycopy(values, index + 1, newValues, index, localSize - index);
            values = newValues;
            return obj;
        } else {
            throw new NoSuchElementException("Error remove by index"
                    + System.lineSeparator()
                    + "Size = " + localSize
                    + " but index = " + index);
        }
    }

    @Override
    public T remove(T element) {
        int index = getIndex(element);
        if (index == -1) {
            throw new NoSuchElementException("Error remove by value"
                    + System.lineSeparator()
                    + "Size = " + localSize
                    + " but index = " + index);
        } else {
            return remove(index);
        }
    }

    @Override
    public int size() {
        return localSize;
    }

    @Override
    public boolean isEmpty() {
        return localSize == 0;
    }

    private Object[] grow(int srcPos, int destPos, int length) {
        Object[] newValues = new Object[values.length + DEFAULT_SIZE / 2];
        System.arraycopy(values, srcPos, newValues, destPos, length);
        return newValues;
    }

    private Object[] grow(int sizeCollection) {
        int newLength = values.length + DEFAULT_SIZE / 2;
        while (newLength - localSize < sizeCollection) {
            newLength += DEFAULT_SIZE / 2;
        }
        Object[] newValues = new Object[newLength];
        System.arraycopy(values, 0, newValues, 0, localSize);
        return newValues;
    }

    private Object[] insertAdd(int index) {
        int rightDistance = localSize - index;
        int newLength = localSize == values.length ? localSize + DEFAULT_SIZE / 2 : values.length;
        Object[] newValues = new Object[newLength];
        System.arraycopy(values, 0, newValues, 0, index);
        System.arraycopy(values, index, newValues, index + 1, rightDistance);
        return newValues;
    }

    private int getIndex(T value) {
        for (int i = 0; i < localSize; i++) {
            if (value == values[i] || value != null && value.equals(values[i])) {
                return i;
            }
        }
        return -1;
    }
}
