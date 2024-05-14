import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private int size;

	public class Node {
		Item item;
		Node next;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return size;
	}

	public void enqueue(Item item) {
		Node n = new Node();
		n.item = item;
		if (last == null) {
			first = n;
			last = n;
		} else {
			last.next = n;
		}
		last = n;
		size++;
	}

	public Item dequeue() {
		if (isEmpty()) {
			throw new IllegalStateException();
		}
		Item i = first.item;
		if (first == last) {
			first = null;
			last = null;
		} else {
			first = first.next;
		}
		size--;
		return i;
	}

	public Iterator<Item> iterator() {
		return new QueueIterator();
	}

	public class QueueIterator implements Iterator<Item> {

		Node node = first;

		public boolean hasNext() {
			return node != null;
		}

		public Item next() {
			Item item = node.item;
			node = node.next;
			return item;
		}

	}
}
