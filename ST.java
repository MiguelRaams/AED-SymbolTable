

public class ST<Key extends Comparable<Key>, Value> {

	private class Node {
		private Key key;
		private Value value;
		private Node left;
		private Node right;
		private int size;

		public Node(Key key, Value value, int size) {
			this.key = key;
			this.value = value;
			this.size = size;
		}

		@Override
		public String toString() {
			return "(" + key + ")";
		}
	}

	private Node root;

	public void put(Key key, Value val) { // Put the key-value pair into this table
		root = put(root, key, val);

	}

	private Node put(Node x, Key key, Value value) {
		if (x == null) {
			return new Node(key, value, 1);
		}
		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			x.left = put(x.left, key, value);
		} else if (cmp > 0) {
			x.right = put(x.right, key, value);
		} else {
			x.value = value;
		}
		x.size = 1 + size(x.left) + size(x.right);
		return x;
	}

	public Value get(Key key) { // Get the value paired with key (or null)
		return get(root, key);
	}

	private Value get(Node x, Key key) {
		if (x == null) {
			return null;
		} else if (key.compareTo(x.key) > 0) {
			return get(x.right, key);
		} else if (key.compareTo(x.key) < 0) {
			return get(x.left, key);
		} else {
			return x.value;
		}
	}

	public void delete(Key key) { // Remove the pair that has this key
		if (key == null) {
			throw new IllegalArgumentException("");
		} else if (root == null) {
			throw new IllegalStateException("");
		}
		root = delete(root, key);
	}

	private Node delete(Node x, Key key) {
		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			x.left = delete(x.left, key);
		} else if (cmp > 0) {
			x.right = delete(x.right, key);
		} else {
			if (x.right == null) {
				return x.left;
			}
			if (x.left == null) {
				return x.right;
			}
			x.key = min(x.right);
			x.value = get(x.right, x.key);
			x.right = deleteMin(x.right);
		}
		x.size = size(x.left) + size(x.right) + 1;
		return x;

	}

	public boolean contains(Key key) { // Is there a value paired with the key?
		Value v = get(root, key);
		if (v == null) {
			return false;
		}
		return true;
	}

	public boolean isEmpty() { // Is this symbol table empty?
		return root == null;
	}

	public int size() { // Number of key-value pairs in this table
		return size(root);
	}

	private int size(Node x) {
		if (x == null) {
			return 0;
		} else {
			return x.size;
		}

	}

	public Key min() { // Smallest key
		if (root == null) {
			return null;
		}
		return min(root);
	}

	private Key min(Node x) {
		if (x.left == null) {
			return x.key;
		}
		return min(x.left);
	}

	public Key max() { // Largest key
		if (root == null) {
			return null;
		}
		return max(root);

	}

	private Key max(Node x) {
		if (x.right == null) {
			return x.key;
		}
		return max(x.right);
	}

	public Key floor(Key key) { // Largest key less than or equal to key
		Node x = floor(root, key);
		if (x == null) {
			return null;
		}
		return x.key;
	}

	private Node floor(Node x, Key key) {
		if (x == null) {
			return null;
		} else if (key.compareTo(x.key) == 0) {
			return x;
		} else if (key.compareTo(x.key) < 0) {
			return floor(x.left, key);
		}
		Node n = floor(x.right, key);
		if (n == null) {
			return x;
		}
		return n;
	}

	public Key ceiling(Key key) { // Smallest key greater than or equal to key
		Node x = ceiling(root, key);
		if (x == null) {
			return null;
		}
		return x.key;
	}

	private Node ceiling(Node x, Key key) {
		if (x == null) {
			return null;
		} else if (key.compareTo(x.key) == 0) {
			return x;
		} else if (key.compareTo(x.key) > 0) {
			return ceiling(x.right, key);
		}
		Node n = ceiling(x.left, key);
		if (n == null) {
			return x;
		}
		return n;
	}

	public int rank(Key key) {
		if (root == null) { // Number of keys less than key
			throw new IllegalStateException("");
		}
		return rank(root, key);
	}

	private int rank(Node x, Key key) {
		if (x == null) {
			return 0;
		} else if (key.compareTo(x.key) == 0) {
			return size(x.left);
		} else if (key.compareTo(x.key) < 0) {
			return rank(x.left, key);
		} else {
			return 1 + size(x.left) + rank(x.right, key);
		}
	}

	public Key select(int k) { // Get a key of rank k
		if (root == null) {
			throw new IllegalStateException("");
		}
		return select(root, k).key;

	}

	private Node select(Node x, int k) {
		if (x == null) {
			return null;
		}
		int t = size(x.left);
		if (t > k) {
			return select(x.left, k);
		} else if (t < k) {
			return select(x.right, k - t - 1);
		} else {
			return x;
		}
	}

	public void deleteMin() { // Delete the pair with the smallest key
		if (root == null) {
			throw new IllegalStateException("");
		}
		root = deleteMin(root);
	}

	private Node deleteMin(Node x) {
		if (x.left == null) {
			return x.right;
		} else {
			x.left = deleteMin(x.left);
			x.size = size(x.right) + size(x.left) + 1;
			return x;
		}

	}

	public void deleteMax() { // Delete the pair with the largest key
		if (root == null) {
			throw new IllegalStateException("");
		}
		root = deleteMax(root);
	}

	private Node deleteMax(Node x) {
		if (x.right == null) {
			return x.left;
		}
		x.right = deleteMax(x.right);
		x.size = size(x.left) + size(x.right) + 1;
		return x;
	}

	public int size(Key lo, Key hi) { // Number of keys in [lo, hi]
		return size(root, lo, hi);
	}

	private int size(Node x, Key lo, Key hi) {
		if (x == null) {
			return 0;
		} else if (x.key.compareTo(lo) < 0) {
			return size(x.right, lo, hi);
		} else if (x.key.compareTo(hi) > 0) {
			return size(x.left, lo, hi);
		} else {
			return 1 + size(x.left, lo, hi) + size(x.right, lo, hi);

		}

	}

	public Iterable<Key> keys(Key lo, Key hi) { // Keys in [lo, hi] in sorted order
		Queue<Key> q = new Queue<Key>();
		inorder(root, q, lo, hi);
		return q;
	}

	private void inorder(Node x, Queue<Key> q, Key lo, Key hi) {
		if (x == null) {
			return;
		}
		if (lo.compareTo(x.key) < 0) {
			inorder(x.left, q, lo, hi);
		} else if (x.key.compareTo(hi) <= 0 && x.key.compareTo(lo) >= 0) {
			q.enqueue(x.key);
		} else if (hi.compareTo(x.key) > 0) {
			inorder(x, q, lo, hi);
		}

	}

	public Iterable<Key> keys() { // All keys in the table, in sorted order
		Queue<Key> q = new Queue<Key>();
		inorder(root, q);
		return q;
	}

	private void inorder(Node x, Queue<Key> q) {
		if (x == null) {
			return;
		}
		inorder(x.left, q);
		q.enqueue(x.key);
		inorder(x.right, q);

	}

	public String toString() {
		return toString(root);
	}

	private String toString(Node node) {
		if (node == null) {
			return "";
		} else {
			return "[" + toString(node.left) + " " + node + " " + toString(node.right) + "]";
		}
	}

}
