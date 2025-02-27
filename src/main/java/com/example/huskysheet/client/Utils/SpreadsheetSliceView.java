package com.example.huskysheet.client.Utils;

import com.example.huskysheet.client.Model.ICell;
import com.example.huskysheet.client.Model.ISpreadsheet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Class providing a read only view of a row or column of an ISpreadsheet as a list.
 */
public class SpreadsheetSliceView implements List<ICell> {
    ISpreadsheet spreadsheet;
    Direction direction;

    public int getRowColNumber() {
        return rowColNumber;
    }

    public void setRowColNumber(int rowColNumber) {
        this.rowColNumber = rowColNumber;
    }

    int rowColNumber;

    public enum Direction {
        row, column
    }

    public SpreadsheetSliceView(ISpreadsheet spreadsheet, Direction direction, int rowColNumber) {
        this.spreadsheet = spreadsheet;
        this.direction = direction;
        this.rowColNumber = rowColNumber;
    }

    /**
     * Returns the number of cells in each row/col
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return direction == Direction.row ? spreadsheet.numColumns() : spreadsheet.numRows();
    }

    /**
     * Returns {@code true} if this list contains no elements.
     *
     * @return {@code true} if this list contains no elements
     */
    @Override
    public boolean isEmpty() {
        return spreadsheet.numRows() == 0 && spreadsheet.numColumns() == 0;
    }

    /**
     * Returns {@code true} if this list contains the specified element. More formally, returns
     * {@code true} if and only if this list contains at least one element {@code e} such that
     * {@code Objects.equals(o, e)}.
     *
     * @param o element whose presence in this list is to be tested
     * @return {@code true} if this list contains the specified element
     * @throws ClassCastException   if the type of the specified element is incompatible with this
     *                              list (<a
     *                              href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this list does not permit
     *                              null elements (<a
     *                              href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public boolean contains(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (o instanceof ICell cell) {
            return spreadsheet.getCell(cell.getCoordinate()).equals(cell);
        } else {
            throw new ClassCastException();
        }
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    public Iterator<ICell> iterator() {
        return new Iterator<>() {
            private int idx = 0;
            /**
             * Returns {@code true} if the iteration has more elements. (In other words,
             * returns {@code true} if {@link #next} would return an element rather than
             * throwing an exception.)
             *
             * @return {@code true} if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                return idx < (direction == Direction.row ? spreadsheet.numColumns() : spreadsheet.numRows());
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             * @throws NoSuchElementException if the iteration has no more elements
             */
            @Override
            public ICell next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Coordinate nextCoordinate;
                if (direction == Direction.row) {
                    nextCoordinate = new Coordinate(rowColNumber, idx++);
                } else {
                    nextCoordinate = new Coordinate(idx++, rowColNumber);
                }
                return spreadsheet.getCell(nextCoordinate);
            }
        };
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence (from first
     * to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must allocate a new array even if this
     * list is backed by an array). The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this list in proper sequence
     * @see Arrays#asList(Object[])
     */
    @Override
    public ICell[] toArray() {
        ICell[] array = new ICell[size()];
        int i = 0;
        for (ICell cell : this) {
            array[i] = cell;
        }
        return array;
    }

    /**
     * Returns an array containing all of the elements in this list in proper sequence (from first
     * to last element); the runtime type of the returned array is that of the specified array.  If
     * the list fits in the specified array, it is returned therein.  Otherwise, a new array is
     * allocated with the runtime type of the specified array and the size of this list.
     *
     * <p>If the list fits in the specified array with room to spare (i.e.,
     * the array has more elements than the list), the element in the array immediately following
     * the end of the list is set to {@code null}. (This is useful in determining the length of the
     * list
     * <i>only</i> if the caller knows that the list does not contain any null elements.)
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows precise control over the
     * runtime type of the output array, and may, under certain circumstances, be used to save
     * allocation costs.
     *
     * <p>Suppose {@code x} is a list known to contain only strings.
     * The following code can be used to dump the list into a newly allocated array of
     * {@code String}:
     *
     * <pre>{@code
     *     String[] y = x.toArray(new String[0]);
     * }</pre>
     * <p>
     * Note that {@code toArray(new Object[0])} is identical in function to {@code toArray()}.
     *
     * @param a the array into which the elements of this list are to be stored, if it is big
     *          enough; otherwise, a new array of the same runtime type is allocated for this
     *          purpose.
     * @return an array containing the elements of this list
     * @throws ArrayStoreException  if the runtime type of the specified array is not a supertype of
     *                              the runtime type of every element in this list
     * @throws NullPointerException if the specified array is null
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (!(a.getClass().getComponentType().isAssignableFrom(ICell[].class))) {
            throw new ArrayStoreException();
        }
        if (a.length < size()) {
            ICell[] cell;
            cell = toArray();
            return (T[]) cell;
        } else {
            int idx = 0;
            for (ICell cell : this) {
                a[idx] = (T) cell;
            }
            return a;
        }
    }

    /**
     * Appends the specified element to the end of this list (optional operation).
     *
     * <p>Lists that support this operation may place limitations on what
     * elements may be added to this list.  In particular, some lists will refuse to add null
     * elements, and others will impose restrictions on the type of elements that may be added.
     * List classes should clearly specify in their documentation any restrictions on what elements
     * may be added.
     *
     * @param cell element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the {@code add} operation is not supported by this
     *                                       list
     * @throws ClassCastException            if the class of the specified element prevents it from
     *                                       being added to this list
     * @throws NullPointerException          if the specified element is null and this list does not
     *                                       permit null elements
     * @throws IllegalArgumentException      if some property of this element prevents it from being
     *                                       added to this list
     */
    @Override
    public boolean add(ICell cell) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the first occurrence of the specified element from this list, if it is present
     * (optional operation).  If this list does not contain the element, it is unchanged.  More
     * formally, removes the element with the lowest index {@code i} such that
     * {@code Objects.equals(o, get(i))} (if such an element exists).  Returns {@code true} if this
     * list contained the specified element (or equivalently, if this list changed as a result of
     * the call).
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     * @throws ClassCastException            if the type of the specified element is incompatible
     *                                       with this list (<a
     *                                       href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if the specified element is null and this list does not
     *                                       permit null elements (<a
     *                                       href="Collection.html#optional-restrictions">optional</a>)
     * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
     *                                       this list
     */
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns {@code true} if this list contains all of the elements of the specified collection.
     *
     * @param c collection to be checked for containment in this list
     * @return {@code true} if this list contains all of the elements of the specified collection
     * @throws ClassCastException   if the types of one or more elements in the specified collection
     *                              are incompatible with this list (<a
     *                              href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one or more null elements
     *                              and this list does not permit null elements (<a
     *                              href="Collection.html#optional-restrictions">optional</a>), or
     *                              if the specified collection is null
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Appends all of the elements in the specified collection to the end of this list, in the order
     * that they are returned by the specified collection's iterator (optional operation).  The
     * behavior of this operation is undefined if the specified collection is modified while the
     * operation is in progress.  (Note that this will occur if the specified collection is this
     * list, and it's nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return {@code true} if this list changed as a result of the call
     * @throws UnsupportedOperationException if the {@code addAll} operation is not supported by
     *                                       this list
     * @throws ClassCastException            if the class of an element of the specified collection
     *                                       prevents it from being added to this list
     * @throws NullPointerException          if the specified collection contains one or more null
     *                                       elements and this list does not permit null elements,
     *                                       or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the specified
     *                                       collection prevents it from being added to this list
     */
    @Override
    public boolean addAll(Collection<? extends ICell> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Inserts all of the elements in the specified collection into this list at the specified
     * position (optional operation).  Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (increases their indices).  The new elements will appear
     * in this list in the order that they are returned by the specified collection's iterator.  The
     * behavior of this operation is undefined if the specified collection is modified while the
     * operation is in progress.  (Note that this will occur if the specified collection is this
     * list, and it's nonempty.)
     *
     * @param index index at which to insert the first element from the specified collection
     * @param c     collection containing elements to be added to this list
     * @return {@code true} if this list changed as a result of the call
     * @throws UnsupportedOperationException if the {@code addAll} operation is not supported by
     *                                       this list
     * @throws ClassCastException            if the class of an element of the specified collection
     *                                       prevents it from being added to this list
     * @throws NullPointerException          if the specified collection contains one or more null
     *                                       elements and this list does not permit null elements,
     *                                       or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the specified
     *                                       collection prevents it from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       ({@code index < 0 || index > size()})
     */
    @Override
    public boolean addAll(int index, Collection<? extends ICell> c) {
        throw new UnsupportedOperationException();
    }


    /**
     * Removes from this list all of its elements that are contained in the specified collection
     * (optional operation).
     *
     * @param c collection containing elements to be removed from this list
     * @return {@code true} if this list changed as a result of the call
     * @throws UnsupportedOperationException if the {@code removeAll} operation is not supported by
     *                                       this list
     * @throws ClassCastException            if the class of an element of this list is incompatible
     *                                       with the specified collection (<a
     *                                       href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this list contains a null element and the specified
     *                                       collection does not permit null elements (<a
     *                                       href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Retains only the elements in this list that are contained in the specified collection
     * (optional operation).  In other words, removes from this list all of its elements that are
     * not contained in the specified collection.
     *
     * @param c collection containing elements to be retained in this list
     * @return {@code true} if this list changed as a result of the call
     * @throws UnsupportedOperationException if the {@code retainAll} operation is not supported by
     *                                       this list
     * @throws ClassCastException            if the class of an element of this list is incompatible
     *                                       with the specified collection (<a
     *                                       href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException          if this list contains a null element and the specified
     *                                       collection does not permit null elements (<a
     *                                       href="Collection.html#optional-restrictions">optional</a>),
     *                                       or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes all of the elements from this list (optional operation). The list will be empty after
     * this call returns.
     *
     * @throws UnsupportedOperationException if the {@code clear} operation is not supported by this
     *                                       list
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index >= size()})
     */
    @Override
    public ICell get(int index) {
        return spreadsheet.getCell(direction == Direction.row ? new Coordinate(rowColNumber, index) : new Coordinate(index, rowColNumber));
    }

    /**
     * Replaces the element at the specified position in this list with the specified element
     * (optional operation).
     *
     * @param index   index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the {@code set} operation is not supported by this
     *                                       list
     * @throws ClassCastException            if the class of the specified element prevents it from
     *                                       being added to this list
     * @throws NullPointerException          if the specified element is null and this list does not
     *                                       permit null elements
     * @throws IllegalArgumentException      if some property of the specified element prevents it
     *                                       from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       ({@code index < 0 || index >= size()})
     */
    @Override
    public ICell set(int index, ICell element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     * Shifts the element currently at that position (if any) and any subsequent elements to the
     * right (adds one to their indices).
     *
     * @param index   index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws UnsupportedOperationException if the {@code add} operation is not supported by this
     *                                       list
     * @throws ClassCastException            if the class of the specified element prevents it from
     *                                       being added to this list
     * @throws NullPointerException          if the specified element is null and this list does not
     *                                       permit null elements
     * @throws IllegalArgumentException      if some property of the specified element prevents it
     *                                       from being added to this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       ({@code index < 0 || index > size()})
     */
    @Override
    public void add(int index, ICell element) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the element at the specified position in this list (optional operation).  Shifts any
     * subsequent elements to the left (subtracts one from their indices).  Returns the element that
     * was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws UnsupportedOperationException if the {@code remove} operation is not supported by
     *                                       this list
     * @throws IndexOutOfBoundsException     if the index is out of range
     *                                       ({@code index < 0 || index >= size()})
     */
    @Override
    public ICell remove(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the index of the first occurrence of the specified element in this list, or -1 if
     * this list does not contain the element. More formally, returns the lowest index {@code i}
     * such that {@code Objects.equals(o, get(i))}, or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in this list, or -1 if
     * this list does not contain the element
     * @throws ClassCastException   if the type of the specified element is incompatible with this
     *                              list (<a
     *                              href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this list does not permit
     *                              null elements (<a
     *                              href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int indexOf(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (o instanceof ICell cell) {
            int idx = 0;
            for (ICell c : this) {
                if (spreadsheet.getCell(c.getCoordinate()).equals(cell)) {
                    return idx;
                }
                idx++;
            }
            return -1;
        } else {
            throw new ClassCastException();
        }
    }

    /**
     * Returns the index of the last occurrence of the specified element in this list, or -1 if this
     * list does not contain the element. More formally, returns the highest index {@code i} such
     * that {@code Objects.equals(o, get(i))}, or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in this list, or -1 if this
     * list does not contain the element
     * @throws ClassCastException   if the type of the specified element is incompatible with this
     *                              list (<a
     *                              href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this list does not permit
     *                              null elements (<a
     *                              href="Collection.html#optional-restrictions">optional</a>)
     */
    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (o instanceof ICell cell) {
            int idx = size() - 1;
            for (ICell c : this.reversed()) {
                if (spreadsheet.getCell(c.getCoordinate()).equals(cell)) {
                    return idx;
                }
                idx--;
            }
            return -1;
        } else {
            throw new ClassCastException();
        }
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence).
     *
     * @return a list iterator over the elements in this list (in proper sequence)
     */
    @Override
    public ListIterator<ICell> listIterator() {
        return listIterator(0);
    }

    /**
     * Returns a list iterator over the elements in this list (in proper sequence), starting at the
     * specified position in the list. The specified index indicates the first element that would be
     * returned by an initial call to {@link ListIterator#next next}. An initial call to
     * {@link ListIterator#previous previous} would return the element with the specified index
     * minus one.
     *
     * @param index index of the first element to be returned from the list iterator (by a call to
     *              {@link ListIterator#next next})
     * @return a list iterator over the elements in this list (in proper sequence), starting at the
     * specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code index < 0 || index > size()})
     */
    @Override
    public ListIterator<ICell> listIterator(int index) {
        if (index > size()) {
            throw new IndexOutOfBoundsException();
        }
        return new ListIterator<>() {
            int position = index;

            @Override
            public boolean hasNext() {
                return position < size();
            }

            @Override
            public ICell next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return get(position++);
            }

            @Override
            public boolean hasPrevious() {
                return position > 0;
            }

            @Override
            public ICell previous() {
                if (!hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return get(--position);
            }

            @Override
            public int nextIndex() {
                return position;
            }

            @Override
            public int previousIndex() {
                return position - 1;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(ICell cell) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void add(ICell cell) {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Returns a view of the portion of this list between the specified {@code fromIndex},
     * inclusive, and {@code toIndex}, exclusive.  (If {@code fromIndex} and {@code toIndex} are
     * equal, the returned list is empty.)  The returned list is backed by this list, so
     * non-structural changes in the returned list are reflected in this list, and vice-versa. The
     * returned list supports all of the optional list operations supported by this list.<p>
     * <p>
     * This method eliminates the need for explicit range operations (of the sort that commonly
     * exist for arrays).  Any operation that expects a list can be used as a range operation by
     * passing a subList view instead of a whole list.  For example, the following idiom removes a
     * range of elements from a list:
     * <pre>{@code
     *      list.subList(from, to).clear();
     * }</pre>
     * Similar idioms may be constructed for {@code indexOf} and {@code lastIndexOf}, and all of the
     * algorithms in the {@code Collections} class can be applied to a subList.<p>
     * <p>
     * The semantics of the list returned by this method become undefined if the backing list (i.e.,
     * this list) is <i>structurally modified</i> in any way other than via the returned list.
     * (Structural modifications are those that change the size of this list, or otherwise perturb
     * it in such a fashion that iterations in progress may yield incorrect results.)
     *
     * @param fromIndex low endpoint (inclusive) of the subList
     * @param toIndex   high endpoint (exclusive) of the subList
     * @return a view of the specified range within this list
     * @throws IndexOutOfBoundsException for an illegal endpoint index value
     *                                   ({@code fromIndex < 0 || toIndex > size || fromIndex >
     *                                   toIndex})
     */
    @Override
    public List<ICell> subList(int fromIndex, int toIndex) {
        return new ArrayList<>(this).subList(fromIndex, toIndex);
    }
}
