import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private int N;

  // construct an empty randomized queue
  public RandomizedQueue() {
    N = 0;
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return N == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return N;
  }

  // add the item
  public void enqueue(Item item) {
    return;
  }

  // remove and return a random item
  public Item dequeue() {
    return null;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    return null;
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedQueueIterator();
  }

  private class RandomizedQueueIterator implements Iterator<Item> {

    public boolean hasNext() {
      return false;
    }

    public void remove() {
      throw new java.lang.UnsupportedOperationException("I don't do that");
    }

    public Item next() {
      if (!this.hasNext()) throw new java.util.NoSuchElementException("empty");
      return null;
    }
  }

  // unit testing (optional)
  public static void main(String[] args) {

  }
}
