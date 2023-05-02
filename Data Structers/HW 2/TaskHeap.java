
/**
 * A heap, implemented as an array.
 * The elements in the heap are instances of the class TaskElement,
 * and the heap is ordered according to the Task instances wrapped by those
 * objects.
 * 
 * IMPORTANT: Except the percolation (private) functions and the constructors,
 * no single function may loop/recurse through all elements in the heap.
 * 
 * 
 *
 */
public class TaskHeap {

	public static int capacity = 200; // the maximum number of elements in the heap
	/*
	 * The array in which the elements are kept according to the heap order.
	 * The following must always hold true:
	 * if i < size then heap[i].heapIndex == i
	 */
	TaskElement[] heap;
	int size; // the number of elements in the heap, it is required that size <= heap.length

	/**
	 * Creates an empty heap which can contain 'capacity' elements.
	 */
	public TaskHeap() {
		this.heap = new TaskElement[capacity];
		this.size = 0;
	}

	/**
	 * Constructs a heap that may contain 'capacity' many elements, from a given
	 * array of TaskElements, of size at most 'capacity'.
	 * This should be done according to the "build-heap" function studied in class.
	 * NOTE: the heapIndex field of each TaskElement might be -1 (or incorrect).
	 * You may NOT use the insert function of heap.
	 * In this function you may use loops.
	 * 
	 */
	public TaskHeap(TaskElement[] arr) { // save
		this.heap = new TaskElement[capacity];
		size = arr.length;
		for (int i = 0; i < arr.length; i++) {
			this.heap[i] = arr[i];
			this.heap[i].heapIndex = i;
		}
		for (int i = (int) (size / 2); i >= 0; i--) {
			PercDown(i);
		}
	}

	private void swap(int firstIndex, int secondIndex) {
		TaskElement temp = this.heap[firstIndex];
		this.heap[firstIndex] = this.heap[secondIndex];
		this.heap[secondIndex] = temp;
		this.heap[firstIndex].heapIndex = firstIndex;
		this.heap[secondIndex].heapIndex = secondIndex;
	}

	private void PercDown(int i) {
		if (2 * i >= size - 1) {
			return;
		}

		if (2 * i + 2 >= size) {
			if (this.heap[i].t.compareTo(this.heap[2 * i + 1].t) < 0) {
				this.swap(i, 2 * i + 1);
				this.PercDown(2 * i + 1);
			}
		} else {
			int maxChildIndex;
			if (this.heap[2 * i + 1].t.compareTo(this.heap[2 * i + 2].t) > 0) {
				maxChildIndex = 2 * i + 1;
			} else {
				maxChildIndex = 2 * i + 2;
			}
			if (this.heap[i].t.compareTo(this.heap[maxChildIndex].t) < 0) {
				this.swap(i, maxChildIndex);
				this.PercDown(maxChildIndex);
			}
		}
	}

	/**
	 * Returns the size of the heap.
	 *
	 * @return the size of the heap
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Inserts a given element into the heap.
	 *
	 * @param e - the element to be inserted.
	 */
	public void insert(TaskElement e) {
		heap[size] = e;
		heap[size].heapIndex = size;
		size++;
		PercUp(size - 1);
	}

	private void PercUp(int i) {
		if (i == 0) {
			return;
		}
		int p = (int) ((i - 1) / 2);
		if (heap[p].t.compareTo(heap[i].t) < 0) {
			swap(p, i);
			PercUp(p);
		}
	}

	/**
	 * Returns and does not remove the element which wraps the task with maximal
	 * priority.
	 * 
	 * @return the element which wraps the task with maximal priority.
	 */
	public TaskElement findMax() {
		return this.heap[0];
	}

	/**
	 * Returns and removes the element which wraps the task with maximal priority.
	 * 
	 * @return the element which wraps the task with maximal priority.
	 */
	public TaskElement extractMax() {
		TaskElement root = this.heap[0];
		this.heap[0] = this.heap[this.size - 1];
		this.heap[0].heapIndex = 0;
		this.heap[size - 1] = null;
		size--;
		PercDown(0);
		return root;
	}

	/**
	 * Removes the element located at the given index.
	 * 
	 * Note: this function is not part of the standard heap API.
	 * Make sure you understand how to implement it, and why it is required.
	 * There are several ways this function could be implemented.
	 * No matter how you choose to implement it, you need to consider the different
	 * possible edge cases.
	 * 
	 * @param index
	 */
	public void remove(int index) {
		if ((index >= this.size) || (index < 0)) {
			return;
		}
		swap(index, size - 1);
		heap[size-1] = null;
		this.size--;
		PercDown(index);
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < size; i++) {
			str += heap[i].t.name + ", " + heap[i].t.priority;
			str += "\n";
		}
		return str;
	}

	public static void main(String[] args) {
		Task a = new Task(10, "Add a new feature");
		Task b = new Task(3, "Code Review");
		Task c = new Task(2, "Move to the new Kafka server");
		TaskElement[] arr = { new TaskElement(a), new TaskElement(b), new TaskElement(c) };
		TaskHeap heap = new TaskHeap(arr);
		System.out.println(heap.findMax());

		Task d = new Task(100, "Solve a problem in production");
		heap.insert(new TaskElement(d));
		System.out.println(heap.findMax());
		System.out.println(heap.extractMax());
		System.out.println(heap.extractMax());
		System.out.println(heap.extractMax());
		System.out.println(heap.extractMax());
	}
}
