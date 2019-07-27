package ru.romanov.hw02list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DIYArrayList<T> implements List<T> {

    private static final int DEFAULT_SIZE = 10;

    private int size = 0;

    private Object[] data;

    private int lastReturned = -1;

    public DIYArrayList() {
        this.data = new Object[] {};
    }

    public DIYArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.data = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.data = new Object[] {};
        } else {
            throw new IllegalArgumentException ("Illegal Capacity: " + initialCapacity);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return (indexOf(o) >= 0);
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        return copyToNewArray(new Object[size]);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("Method is not supported");
    }

    @Override
    public boolean add(T t) {
        if (data.length == 0) {
            data = new Object[DEFAULT_SIZE];
        } else if (data.length == size) {
            data = copyToNewArray(getBiggerArray(size + 1));
        }
        addSimple(t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        } else {
            int index = indexOf(o);
            if (index >= 0) {
                remove(index);
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null || c.isEmpty()) {
            return false;
        } else {
            int count = 0;
            for (Object o : c) {
                if (contains(o)) {
                    count++;
                }
            }
            return count == c.size();
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) {
            return false;
        } else {
            int newSize = size + c.size();
            if (newSize > data.length) {
                data = copyToNewArray(getBiggerArray(newSize));
            }
            for (T t : c) {
                addSimple(t);
            }
            return true;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null) {
            return false;
        } else {
            if (index > size || index < 0) {
                return false;
            } else {
                int newSize = size + c.size();
                Object[] dst = data;
                if (newSize > data.length) {
                    dst = getBiggerArray(newSize);
                } else if(index < size) {
                    dst = new Object[data.length];
                }
                if (index == size) {
                    data = copyToNewArray(dst);
                } else {
                    data = copyToNewArray(dst, index, c.size());
                }
                int i = index;
                for (T t : c) {
                    set(i, t);
                    i++;
                    size++;
                }
                return true;
            }
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (size == 0 || c == null || c.isEmpty()) {
            return false;
        } else {
            Object[] newData = new Object[data.length];
            int count = 0;
            for (int i = 0; i < size; i++) {
                if (!c.contains(data[i])) {
                    newData[count] = data[i];
                    count++;
                }
            }
            data = newData;
            size = count;
            return count > 0;
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (size == 0 || c == null || c.isEmpty()) {
            return false;
        } else {
            Object[] newData = new Object[data.length];
            int count = 0;
            for (int i = 0; i < size; i++) {
                if (c.contains(data[i])) {
                    newData[count] = data[i];
                    count++;
                }
            }
            data = newData;
            size = count;
            return count > 0;
        }
    }

    @Override
    public void clear() {
        data = new Object[]{};
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0 ) {
            return null;
        } else {
            return (T) data[index];
        }
    }

    @Override
    public T set(int index, T element) {
        if (index >= size || size < 0) {
            return null;
        } else {
            data[index] = element;
            return get(index);
        }
    }

    @Override
    public void add(int index, T element) {
        if (index <= size && index >= 0) {
            int newSize = size + 1;
            Object[] dst = data;
            if (newSize > data.length) {
                dst = getBiggerArray(newSize);
            } else if(index < size) {
                dst = new Object[data.length];
            }
            if (index == size) {
                data = copyToNewArray(dst);
            } else {
                data = copyToNewArray(dst, index, 1);
            }
            set(index, element);
            size++;
        }
    }

    @Override
    public T remove(int index) {
        T t = get(index);
        data = copyToNewArray(new Object[data.length], index);
        size--;
        return t;
    }

    @Override
    public int indexOf(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (data[i].equals(o)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (data[i] == null) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o != null) {
            for (int i = size; i > 0; i--) {
                if (data[i - 1].equals(o)) {
                    return i - 1;
                }
            }
        } else {
            for (int i = size; i > 0; i--) {
                if (data[i - 1] == null) {
                    return i - 1;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Method is not supported");
    }

    private class Itr implements Iterator<T> {
        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if (cursor >= size)
                throw new NoSuchElementException();
            Object element = data[cursor];
            lastReturned = cursor;
            cursor++;
            return (T) element;
        }

        @Override
        public void remove() {
            Object[] newData = new Object[data.length];
            System.arraycopy(data, 0, newData, 0, cursor);
            System.arraycopy(data, cursor + 1, newData, cursor, size - cursor - 1);
            data = newData;
            size--;
        }
    }

    private class ListItr extends Itr implements ListIterator<T> {

        public ListItr(int index) {
            super();
            cursor = index;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public T previous() {
            if (cursor == 0) {
                throw new NoSuchElementException();
            } else {
                Object element = data[cursor - 1];
                lastReturned = cursor - 1;
                cursor--;
                return (T) element;
            }
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void set(T t) {
            if (lastReturned < 0)
                throw new IllegalStateException();
            DIYArrayList.this.set(lastReturned, t);
        }

        @Override
        public void add(T t) {
            DIYArrayList.this.add(cursor, t);
            cursor++;
            lastReturned = -1;
        }
    }

    private Object[] getBiggerArray(int minNewSize) {
        int newSize = size;
        while (newSize < minNewSize) {
            newSize = newSize * 2;
        }
        return new Object[newSize];
    }

    private Object[] copyToNewArray(Object[] dst) {
        System.arraycopy(data, 0, dst, 0, size);
        return dst;
    }

    private Object[] copyToNewArray(Object[] dst, int index, int count) {
        System.arraycopy(data, 0, dst, 0, index);
        System.arraycopy(data, index, dst, index + count, size-index);
        return dst;
    }

    private Object[] copyToNewArray(Object[] dst, int index) {
        System.arraycopy(data, 0, dst, 0, index);
        System.arraycopy(data, index + 1, dst, index, size - index - 1);
        return dst;
    }

    private void addSimple(T t) {
        data[size] = t;
        size++;
    }
}
