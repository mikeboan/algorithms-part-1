import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  // linked list
  private class Node {
    private Item item;
    private Node next;
    private Node prev;

    public Node(Item i) {
      item = i;
      next = null;
      prev = null;
    }
  }

  private Node head;
  private Node tail;
  private int n;

  // construct an empty deque
  public Deque() {
    head = new Node(null);
    tail = new Node(null);
    head.next = tail;
    tail.prev = head;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return n == 0;
  }

  // return the number of items on the deque
  public int size() {
    return n;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) throw new java.lang.IllegalArgumentException("cannot add null item");

    Node newNode = new Node(item);
    newNode.next = head.next;
    head.next.prev = newNode;
    head.next = newNode;
    newNode.prev = head;
    n++;
  }

  // add the item to the end
  public void addLast(Item item) {
    if (item == null) throw new java.lang.IllegalArgumentException("cannot add null item");

    Node newNode = new Node(item);
    newNode.prev = tail.prev;
    tail.prev.next = newNode;
    newNode.next = tail;
    tail.prev = newNode;
    n++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (this.isEmpty()) throw new java.util.NoSuchElementException("deque is empty");

    Node oldNode = head.next;
    head.next = oldNode.next;
    head.next.prev = head;
    oldNode.next = null;
    oldNode.prev = null;
    n--;
    return oldNode.item;
  }

  // remove and return the item from the end
  public Item removeLast() {
    if (this.isEmpty()) throw new java.util.NoSuchElementException("deque is empty");

    Node oldNode = tail.prev;
    tail.prev = oldNode.prev;
    tail.prev.next = tail;
    oldNode.prev = null;
    oldNode.next = null;
    n--;
    return oldNode.item;
  }

    // return an iterator over items in order from front to end
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  public Iterator<Item> iterator() { return new ListIterator(); }

  private class ListIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != tail;
    }

    public void remove() {
      throw new java.lang.UnsupportedOperationException("I don't do that");
    }

    public Item next() {
      if (!this.hasNext()) throw new java.util.NoSuchElementException("empty");

      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  // private class DequeIterator<Item> implements Iterator<Item> {
  //   private Node current = head.next;
  //
  //   public boolean hasNext() {
  //     return current != tail;
  //   }
  //
  //   public void remove() {
  //     throw new java.lang.UnsupportedOperationException("I don't do that");
  //   }
  //
  //   public Item next() {
  //     if (this.hasNext()) throw new java.util.NoSuchElementException("empty");
  //
  //     Item item = current.next;
  //     current = item;
  //     return item;
  //   }
  // }

  // unit testing (optional)
  public static void main(String[] args) {
    Deque<String> deck = new Deque<String>();

    deck.addFirst("order");
    deck.addFirst("this");
    deck.addFirst("in");
    deck.addFirst("print");

    while (!deck.isEmpty()) {
      System.out.println(deck.removeFirst());
    }

    deck.addFirst("print");
    deck.addFirst("in");
    deck.addFirst("this");
    deck.addFirst("order");

    while (!deck.isEmpty()) {
      System.out.println(deck.removeLast());
    }
  }

}
