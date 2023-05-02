
public class Task {
	int priority;
	String name;

	/**
	 * A standard constructor for the task class
	 * 
	 * @param priority
	 * @param name
	 */

	public Task(int priority, String name) {
		this.name = name;
		this.priority = priority;
	}

	/**
	 * Compares this task to another task.
	 * This task is consider smaller than the other task if and only if
	 * The priority of this task is smaller than the other task or the priorities
	 * are equal
	 * and the name of this task is smaller in the lexicographic ordering than the
	 * name of the other task.
	 * 
	 * If this task is smaller returns a negative number. If this task is bigger
	 * return a positive number.
	 * If the tasks are equal return 0.
	 * 
	 * 
	 * @param other
	 * @return a negative/positive or zero number of this task is smaller/greater or
	 *         equal to other
	 */
	public int compareTo(Task other) {
		if (this.priority > other.priority)
			return 1;
		if (this.priority < other.priority)
			return -1;

		if (this.name.compareTo(other.name) < 0) {
			return 1;
		}
		if (this.name.compareTo(other.name) > 0) {
			return -1;
		}
		return 0;
	}

	public String toString() {
		return "task: " + this.name + ", priority: " + this.priority;
	}

	public static void main(String[] args) {
		Task a1 = new Task(3, "Shir");
		Task a2 = new Task(5, "Shir");
		Task a3 = new Task(10, "Shir");
		Task a4 = new Task(3, "Adan");
		Task a5 = new Task(10, "Shiru");
		Task a6 = new Task(10, "Shir");
		System.out.println(a2.compareTo(a4));
		System.out.println(a1.compareTo(a4));
		System.out.println(a4.compareTo(a1));
		System.out.println(a1.compareTo(a3));
		System.out.println(a5.compareTo(a3));
		System.out.println(a3.compareTo(a6));

	}
}
