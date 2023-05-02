///package MMS;

import java.util.Iterator;

/**
 * Represents a managed memory space (also called "heap"). The memory space is managed by three  
 * methods: <br> <b> malloc </b> allocates memory blocks, <br> <b> free </b> recycles memory blocks,
 * <br> <b> defrag </b> reorganizes the memory space, for better allocation and rescheduling.
 * <br> (Part of Homework 10 in the Intro to CS course, Efi Arazi School of CS)
 */
public class MemorySpace {
	
	// A list that keeps track of the memory blocks that are presently allocated
	private List allocatedList;

	// A list that keeps track of the memory blocks that are presently free
	private List freeList;

	/**
	 * Constructs a managed memory space ("heap") of a given maximal size.
	 * 
	 * @param maxSize The size of the memory space to be managed
	 */
	public MemorySpace(int maxSize) {
		// Constructs and intilaizes an empty list of allocated memory blocks, and a free list containing
		// a single memory block which represents the entire memory space. The base address of this single
		// memory block is zero, and its length is the given memory size (maxSize).
		allocatedList = new List();
		freeList = new List();
		freeList.addLast(new MemBlock(0, maxSize));
	}

	/**
	 * Allocates a memory block.
	 * 
	 * @param length The length (in words) of the memory block that has to be allocated
	 * @return the base address of the allocated block, or -1 if unable to allocate
	 */
	public int malloc(int length)
	{ 	
		ListIterator ls = freeList.iterator();
		while(ls.current!=null)
		{
			if(ls.current.block.length>=length)
			{
				if(ls.current.block.length==length)
				{
					freeList.remove(ls.current.block);
					allocatedList.addFirst(ls.current.block);
					break;
				}
				MemBlock mb= new MemBlock(ls.current.block.baseAddress, length);
				allocatedList.addLast(mb);
				ls.current.block.baseAddress+=length;
				ls.current.block.length-=length;
				return mb.baseAddress; 	
			}

			ls.next();
		}
		return -1;
	}
	    // Scans the freeList, looking for the first free memory block whose length equals at least
		// the given length. If such a block is found, the method performs the following operations:
        //
	    // (1) A new memory block is constructed. The base address of the new block is set to
	    //     the base address of the found free block. The length of the new block is set to the value 
	    //     of the method's length parameter.
	    //
	    // (2) The new memory block is appended to the end of the allocatedList.
        //
	    // (3) The base address and the length of the found free block are updated, to reflect the allocation.
	    //     For example, suppose that the requested block length is 17, and suppose that the base
	    //     address and length of the the found free block are 250 and 20, respectively.
	    //     In such a case, the base address and length of of the allocated block are set to 250 and 17, 
		//     respectively, and the base address and length of the found free block are updated to 267 and 3, respectively.
		//
	    // (4) The base address of the new memory block is returned.
	    // 
	    // If the length of the found block is exactly the same as the requested length, 
	    // then the found block is removed from the freeList, and appended to the allocatedList.
	
		//// Replace the following statement with your code


	/**
	 * Frees the memory block whose base address equals the given address
	 * 
	 * @param address The base address of the memory block to free
	 */
	public void free(int address) 
	{
		ListIterator ls = allocatedList.iterator();
		while(ls.current!=null)
		{
			if(ls.current.block.baseAddress==address)
			{
				allocatedList.remove(ls.current.block);
				freeList.addLast(ls.current.block);
			}
			ls.next();
		}
		// Adds the memory block to the free list, and removes it from the allocated list. 
		//// Write your code here
	}
	
	/**
	 * A textual representation of this memory space
	 * @return a string representation of this memory space.
	 */
	public String toString()
	{
		return freeList.toString() + "\n" + allocatedList.toString();
		// Returns the textual representation of the free list, a new line, and then
        // the textual representation of the allocated list, as one string
		//// Write your code here	
	}
	
	/**
	 * Performs a defragmantation of the memory space.
	 * Can be called periodically, or by malloc, when it fails to find a memory block of the requested size.
	 */
	public void defrag() 
	{
		List newL= new List();
		ListIterator ls1 = freeList.iterator();
		int myindex=0;
		newL=freeList;

		while (ls1.current!=null)
		{
			ListIterator ls2 = freeList.iterator();
			while(ls2.current!=null)
			{
				if(ls1.current.block.baseAddress+ls1.current.block.length==ls2.current.block.baseAddress)
				{
					MemBlock mb= new MemBlock(ls1.current.block.baseAddress, ls1.current.block.length+ls2.current.block.length);
					myindex=freeList.indexOf(ls1.current.block);
					newL.add(myindex, mb);
					newL.remove(newL.getBlock(myindex+1));
					newL.remove(ls2.current.block);
					freeList=newL;
					ls1=freeList.iterator();
				}
				ls2.next();
			}
			ls1.next();
		}
		

	}
}
