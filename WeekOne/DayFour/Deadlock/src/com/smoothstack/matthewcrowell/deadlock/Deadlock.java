package com.smoothstack.matthewcrowell.deadlock;

/**
 * Class to demonstrate deadlock condition.
 *
 * @author matthew.crowell
 */
public class Deadlock {

	/**
	 * Create and run two threads with resources acquired in
	 * opposite order to deliberately trigger deadlock.
	 *
	 * @param args String[] of command line arguments
	 */
	public static void main(String[] args) {
		String firstResource = "junk";
		String secondResource = "knuj";

		Thread junkThread = new Thread() {
			public void run() {
				synchronized (firstResource) {
					System.out.println(this.getName() + " locked firstResource.");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println(this.getName() + " waiting on secondResource.");

					synchronized (secondResource) {
						System.out.println(this.getName() + " locked secondResource.");
					}
				}
			}
		};
		junkThread.setName("junkThread");

		Thread knujThread = new Thread() {
			public void run() {
				synchronized (secondResource) {
					System.out.println(this.getName() + " locked secondResource.");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					System.out.println(this.getName() + " waiting on firstResource.");

					synchronized (firstResource) {
						System.out.println(this.getName() + " locked firstResource.");
					}
				}
			}
		};
		knujThread.setName("knujThread");

		knujThread.start();
		junkThread.start();
	}
}