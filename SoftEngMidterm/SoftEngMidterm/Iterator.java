// J17
class IntSet {
    // J18
    private Hashtable ht = new Hashtable();

    // 1. Design an internal "iterator" class for the "collection" class
    // J19
    public static class Iterator {
        private IntSet set;
        private Enumeration e;
        private Integer current;
        
        // J20
        public Iterator(IntSet in) {
            set = in;
        }

        // J21
        public void first() {
            e = set.ht.keys();
            next();
        }

        // J22
        public boolean isDone() {
            return current == null;
        }

        // J23
        public int currentItem() {
            return current;
        }

        // J24
        public void  next() {
            try {
                current = (Integer)e.nextElement();
            } catch (NoSuchElementException e) {
                current = null;
            }
        }
    }

    // J25
    public void add(int in) {
        ht.put(in, "null");
    }

    // J26
    public boolean isMember(int i) {
        return ht.containsKey(i);
    }

    // J27
    public Hashtable getHashtable() {
        return ht;
    }

    // 2. Add a createIterator() member to the collection class
    // J28
    public Iterator createIterator()  {
        return new Iterator(this);
    }
}

public class IteratorDemo {
    public static void main( String[] args ) {
        IntSet set = new IntSet();
        for (int i=2; i < 10; i += 2) set.add( i );
        for (int i=1; i < 9; i++)
            System.out.print( i + "-" + set.isMember( i ) + "  " );

        // 3. Clients ask the collection object to create many iterator objects
        IntSet.Iterator it1 = set.createIterator();
        IntSet.Iterator it2 = set.createIterator();

        // 4. Clients use the first(), isDone(), next(), currentItem() protocol
        System.out.print( "\nIterator:    " );
        for ( it1.first(), it2.first();  ! it1.isDone();  it1.next(), it2.next() )
            System.out.print( it1.currentItem() + " " + it2.currentItem() + "  " );

        // Java uses a different collection traversal "idiom" called Enumeration
        System.out.print( "\nEnumeration: " );
        for (Enumeration e = set.getHashtable().keys(); e.hasMoreElements(); )
            System.out.print( e.nextElement() + "  " );
        System.out.println();
    }
}